/*******************************************************************************
 * Copyright (c) 2005,2008 Cognium Systems SA and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Contributors:
 *     Cognium Systems SA - initial API and implementation
 *******************************************************************************/
package org.github.gwttemplate.templates;

import org.github.gwttemplate.templates.cache.CacheBuilder;
import org.github.gwttemplate.templates.cache.CacheNode;
import org.github.gwttemplate.templates.cache.CompositeCacheNode;
import org.github.gwttemplate.templates.cache.CacheNodeVisitor;
import org.github.gwttemplate.templates.cache.TextCacheNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;

/**
 * This is a registry of {@link FieldInfoFactory} instances. It maps individual
 * "tag names" to the corresponding {@link FieldInfoFactory} objects used to
 * create {@link FieldInfo} wrappers for real widgets.
 * 
 * 
 */
public class TemplatePanelBuilder implements TemplateNamespaces {

    /**
     * Instances of this type are used to keep in the cache XML elements
     * defining widgets in templates
     */
    static class ElementCache extends CompositeCacheNode {

        /**
         * The cached XML element
         */
        public final Element element;

        /**
         * The field wrapper factory corresponding to the XML element.
         */
        public final FieldInfoFactory factory;

        /**
         * The default constructor used to initialize the internal fields
         * 
         * @param factory the field wrapper factory
         * @param e the XML element to cache
         */
        public ElementCache(FieldInfoFactory factory, Element e) {
            this.factory = factory;
            this.element = e;
        }

    }

    /**
     * The cache node visitor used to build real widgets. When this visitor
     * visits cached static text nodes it just append the text to the template
     * using the {@link TemplatePanelGenerator#addText(String)} method. When
     * this visitor enters in dynamic nodes it calls the
     * {@link TemplatePanelGenerator#beginField(FieldInfoFactory, Map)} /
     * {@link TemplatePanelGenerator#endField()} methods creating new dynamic
     * GWT widgets.
     */
    static class PanelGeneratingVisitor extends TemplatePanelGenerator
        implements
        CacheNodeVisitor {

        /**
         * This constructor initializes the panel which should be filled with
         * HTML and widgets
         * 
         * @param panel the topmost panel to initialize
         */
        public PanelGeneratingVisitor(TemplateTopPanel panel) {
            super(panel);
        }

        /**
         * This method calls the
         * {@link TemplatePanelGenerator#beginField(FieldInfoFactory, Map)} /
         * {@link TemplatePanelGenerator#endField()} methods creating new
         * dynamic GWT widgets.
         * 
         * @see CacheNodeVisitor.templates.cache.ICacheNodeVisitor#visit(gwt.templates.cache.CompositeCacheNode)
         */
        public void visit(CompositeCacheNode node) {
            ElementCache e = (ElementCache) node;
            Map<String, String> attributes = XMLUtil.toParameters(e.element);
            String tagURI = XMLUtil.getURL(e.element);
            attributes.put(TAG_URI, tagURI);
            beginField(e.factory, attributes);
            try {
                for (CacheNode child : node) {
                    child.accept(this);
                }
            } finally {
                endField();
            }
        }

        /**
         * This method just append static cached HTML to the panel's template
         * using the {@link TemplatePanelGenerator#addText(String)} method.
         * 
         * @see CacheNodeVisitor.templates.cache.ICacheNodeVisitor#visit(gwt.templates.cache.TextCacheNode)
         */
        public void visit(TextCacheNode node) {
            addText(node.getText());
        }

    }

    private static final Map<String, String> EMPTY_ATTRIBUE_MAP = null;

    /**
     * The cache builder. This object is used to build new panel cache structure
     * corresponding to an XML elements. The resulting structure contains cached
     * parts of the HTML template used to visualize the panel and dynamic nodes
     * (see {@link ElementCache}) corresponding to GWT widgets which should be
     * inserted in the panel.
     */
    private CacheBuilder<Node> fBuilder = new CacheBuilder<Node>() {

        /**
         * @see gwt.templates.cache.CacheBuilder#getChildren(java.lang.Object,
         *      CompositeCacheNode)
         */
        protected Iterator<Node> getChildren(
            Node node,
            CompositeCacheNode compositeNode) {
            if (!(node instanceof Element))
                return null;
            final Element e = (Element) node;
            return new Iterator<Node>() {

                Node node = e.getFirstChild();

                public boolean hasNext() {
                    return node != null;
                }

                public Node next() {
                    Node n = node;
                    node = node != null ? node.getNextSibling() : null;
                    return n;
                }

                public void remove() {
                    throw new RuntimeException();
                }

            };
        }

        /**
         * @see gwt.templates.cache.CacheBuilder#getCompositeNode(java.lang.Object)
         */
        protected CompositeCacheNode getCompositeNode(Node object) {
            if (!(object instanceof Element))
                return null;
            Element e = (Element) object;
            String tagURI = XMLUtil.getURL(e);
            String ns = XMLUtil.getNamespace(tagURI);
            String name = XMLUtil.getLocalName(tagURI);
            FieldInfoFactory factory = fFieldInfoFactoryRegistry
                .getFieldInfoFactoryNS(ns, name);
            CompositeCacheNode result = null;
            if (factory != null) {
                result = new ElementCache(factory, e);
            }
            return result;
        }

        /**
         * @see gwt.templates.cache.CacheBuilder#getTextPrefix(java.lang.Object,
         *      CompositeCacheNode)
         */
        protected String getTextPrefix(
            Node node,
            CompositeCacheNode compositeNode) {
            if (compositeNode != null)
                return null;
            if (node instanceof Element) {
                StringBuffer buf = new StringBuffer();
                String url = XMLUtil.getURL(node);
                if (!url.startsWith(NS_TEMPLATES)) {
                    XMLUtil.serializeOpenTag((Element) node, buf);
                }
                return buf.toString();
            } else if (node instanceof Text) {
                StringBuffer buf = new StringBuffer();
                XMLUtil.serializeText((Text) node, buf);
                return buf.toString();
            }
            return null;
        }

        /**
         * @see gwt.templates.cache.CacheBuilder#getTextSuffix(java.lang.Object,
         *      CompositeCacheNode)
         */
        protected String getTextSuffix(
            Node node,
            CompositeCacheNode compositeNode) {
            if (compositeNode != null)
                return null;
            if (node instanceof Element) {
                StringBuffer buf = new StringBuffer();
                String url = XMLUtil.getURL(node);
                if (!url.startsWith(NS_TEMPLATES)) {
                    XMLUtil.serializeCloseTag((Element) node, buf);
                }
                return buf.toString();
            }
            return null;
        }

        /**
         * @see gwt.templates.cache.CacheBuilder#newTopNode(java.lang.Object)
         */
        protected CompositeCacheNode newTopNode(Node object) {
            Element e = (Element) object;
            return new ElementCache(null, e);
        }

    };

    /**
     * This object provides access to internationalized messages used by
     * widgets.
     */
    private FieldI18N fConstants;

    /**
     * The registry of field wrapper factories. It is used when a new panel is
     * constructed as a provider of field wrappers.
     */
    private FieldInfoFactoryRegistry fFieldInfoFactoryRegistry;

    /**
     * This object maps template names to corresponding attributes defined in
     * the initial configuration document.
     */
    private Map<String, Map<String, String>> fTemplateAttributes = new HashMap<String, Map<String, String>>();

    /**
     * This object maps template names to corresponding cached templates. For
     * each element this class builds a cache and puts it in this map. These
     * cached nodes are used to build {@link TemplateTopPanel} instances.
     */
    private Map<String, CompositeCacheNode> fTemplates = new HashMap<String, CompositeCacheNode>();

    /**
     * This constructor initializes the internal widget registry.
     * 
     * @param registry the registry to add
     */
    public TemplatePanelBuilder(FieldInfoFactoryRegistry registry) {
        this(registry, FieldI18N.NULL);
    }

    /**
     * This constructor initializes the internal widget registry.
     * 
     * @param registry the registry to add
     * @param constants this object gives access to internationalized messages
     *        used by widgets
     */
    public TemplatePanelBuilder(
        FieldInfoFactoryRegistry registry,
        FieldI18N constants) {
        fFieldInfoFactoryRegistry = registry;
        fConstants = constants;
    }

    /**
     * Registers a new template defined by the given XML element. This method
     * builds an internal cache containing serialized HTML and dynamic nodes
     * corresponding to GWT widgets. This cache is used to build individual
     * {@link TemplateTopPanel} instances.
     * 
     * @param templateName the name of the template
     * @param templateNode the XML element defining the template
     * @param attributes
     */
    public void addTemplate(
        String templateName,
        Element templateNode,
        Map<String, String> attributes) {
        CompositeCacheNode template = fBuilder.build(templateNode);
        fTemplates.put(templateName, template);
        if (attributes != null) {
            fTemplateAttributes.put(templateName, attributes);
        } else {
            fTemplateAttributes.remove(templateName);
        }
    }

    /**
     * Reads all templates declared in the given document and registers them.
     * 
     * @param doc the document containing template declarations.
     */
    public void addTemplates(Document doc) {
        Element e = doc.getDocumentElement();
        Node child = e.getFirstChild();
        String url = NS_TEMPLATES + "template";
        while (child != null) {
            if (child instanceof Element) {
                Element element = (Element) child;
                String elementURL = XMLUtil.getURL(element);
                if (url.equals(elementURL)) {
                    String name = XMLUtil.getAttributeValueNS(
                        element,
                        X_ATTR_NAME);
                    if (name != null) {
                        Map<String, String> attributes = XMLUtil
                            .getAttributes(element);
                        addTemplate(name, element, attributes);
                    }
                }
            }
            child = child.getNextSibling();
        }
    }

    /**
     * This method creates and returns a new {@link FieldInfo} instance with a
     * template corresponding to the given name; this method uses the given
     * message provider for the the panel.
     * 
     * @param templateURI the URI of the template to build
     * @return a newly created {@link TemplateTopPanel} instance corresponding
     *         to the given template name
     */
    public TemplateTopPanel buildPanel(String templateURI) {
        CompositeCacheNode template = fTemplates.get(templateURI);
        if (template == null)
            return null;
        TemplateTopPanel panel = new TemplateTopPanel(this);
        PanelGeneratingVisitor generator = new PanelGeneratingVisitor(panel);
        template.accept(generator);
        return panel;
    }

    /**
     * Returns a provider of internationalized messages used by widgets
     * 
     * @return a provider of internationalized messages used by widgets
     */
    public FieldI18N getConstants() {
        return fConstants;
    }

    /**
     * Returns the internal registry of factories used to create field info
     * objects
     * 
     * @return the internal registry of factories used to create field info
     *         objects
     */
    public FieldInfoFactoryRegistry getFieldInfoFactoryRegistry() {
        return fFieldInfoFactoryRegistry;
    }

    /**
     * Returns a map of attributes associated with the template with the given
     * name. The returned map contains the full attribute URIs as keys with the
     * corresponding values. These values are taken directly from the
     * configuration document used to build the template.
     * 
     * @param templateName the name of the template for which a map of
     *        attributes should be returned
     * @return a map of all attributes associated with the template with the
     *         given name
     */
    public Map<String, String> getTemplateAttributes(String templateName) {
        Map<String, String> result = fTemplateAttributes.get(templateName);
        return result != null ? result : EMPTY_ATTRIBUE_MAP;
    }

    /**
     * Returns a set of all templates registered in this builder
     * 
     * @return a set of all templates registered in this builder
     */
    public Set<String> getTemplateNames() {
        return fTemplateAttributes.keySet();
    }

    /**
     * Removes a template corresponding to the given template name
     * 
     * @param templateName the name of the template to remove
     */
    public void removeTemplate(String templateName) {
        fTemplates.remove(templateName);
    }

    /**
     * Sets a new provider of internationalized messages used by widgets
     * 
     * @param constants the constants to set
     */
    public void setConstants(FieldI18N constants) {
        fConstants = constants;
    }

}
