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

import org.github.gwttemplate.templates.wrappers.BlockLabelInfo;
import org.github.gwttemplate.templates.wrappers.ButtonInfo;
import org.github.gwttemplate.templates.wrappers.CancelButtonInfo;
import org.github.gwttemplate.templates.wrappers.CheckboxFieldInfo;
import org.github.gwttemplate.templates.wrappers.ClearButtonInfo;
import org.github.gwttemplate.templates.wrappers.DisclosurePanelInfo;
import org.github.gwttemplate.templates.wrappers.FileUploadFieldInfo;
import org.github.gwttemplate.templates.wrappers.HTMLPanelInfo;
import org.github.gwttemplate.templates.wrappers.HeaderPanelInfo;
import org.github.gwttemplate.templates.wrappers.HiddenFieldInfo;
import org.github.gwttemplate.templates.wrappers.HyperlinkFieldInfo;
import org.github.gwttemplate.templates.wrappers.InlineHTMLLabelInfo;
import org.github.gwttemplate.templates.wrappers.InlineHTMLPanelInfo;
import org.github.gwttemplate.templates.wrappers.InlineLabelInfo;
import org.github.gwttemplate.templates.wrappers.InlinePanelInfo;
import org.github.gwttemplate.templates.wrappers.PasswordFieldInfo;
import org.github.gwttemplate.templates.wrappers.RadioFieldInfo;
import org.github.gwttemplate.templates.wrappers.ReadonlyFieldInfo;
import org.github.gwttemplate.templates.wrappers.SimplePanelInfo;
import org.github.gwttemplate.templates.wrappers.SubmitButtonInfo;
import org.github.gwttemplate.templates.wrappers.TabPanelInfo;
import org.github.gwttemplate.templates.wrappers.TextAreaInfo;
import org.github.gwttemplate.templates.wrappers.TextBoxInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a registry of {@link FieldInfoFactory} instances. It maps individual
 * "tag names" to the corresponding {@link FieldInfoFactory} objects used to
 * create {@link FieldInfo} for real widgets.
 * 
 * 
 */
public class FieldInfoFactoryRegistry implements TemplateNamespaces {

    /**
     * This map contains {@link FieldInfoFactory} objects. The keys are "name"
     * string where "name" is the name of the template tag.
     */
    private Map<String, FieldInfoFactory> fFactories = new HashMap<String, FieldInfoFactory>();

    /**
     * 
     */
    public FieldInfoFactoryRegistry() {
        super();
        initFielInfoFactories();
    }

    /**
     * Adds the given {@link FieldInfoFactory} object and associates it with
     * the specified template tag.
     * 
     * @param name the tag name
     * @param factory the factory to add
     */
    private void addFieldInfoFactory(String name, FieldInfoFactory factory) {
        addFieldInfoFactoryNS(NS_TEMPLATES, name, factory);
    }

    /**
     * Adds the given {@link FieldInfoFactory} object and associates it with
     * the specified template tag .
     * 
     * @param ns the namespace of the tag (URL)
     * @param name the local name
     * @param factory the factory to add
     */
    public void addFieldInfoFactoryNS(
        String ns,
        String name,
        FieldInfoFactory factory) {
        String uri = getURI(ns, name);
        fFactories.put(uri, factory);
    }

    /**
     * Returns a field factory corresponding to the specified tag name
     * 
     * @param ns the namespace of the widget to retrieve
     * @param name the URL of the tag
     * @return a field factory corresponding to the specified tag name
     */
    public FieldInfoFactory getFieldInfoFactoryNS(String ns, String name) {
        String uri = getURI(ns, name);
        return fFactories.get(uri);
    }

    /**
     * Returns the full URI corresponding to the given namespace and local name
     * 
     * @param ns the namespace of the URI
     * @param name the local name of the URI
     * @return the full URI corresponding to a field factory
     */
    private String getURI(String ns, String name) {
        return ns != null ? ns + name : name;
    }

    /**
     * Adds {@link FieldInfoFactory} objects to this factory. Fills the given
     * map with the {@link FieldInfoFactory} objects. The keys are "name#type"
     * string where "name" is the name of the template tag and the "type" is the
     * value of the "type" attribute in the tag
     */
    protected void initFielInfoFactories() {
        // HTML panel
        addFieldInfoFactory("html", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new HTMLPanelInfo(panel, attributes);
            }
        });

        // Inline HTML panel
        addFieldInfoFactory("inlineHTML", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new InlineHTMLPanelInfo(panel, attributes);
            }
        });

        // A simple text input
        addFieldInfoFactory("input", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new TextBoxInfo(panel, attributes);
            }
        });

        // Checkbox
        addFieldInfoFactory("checkbox", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new CheckboxFieldInfo(panel, attributes);
            }
        });

        // Radiobutton
        addFieldInfoFactory("radio", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new RadioFieldInfo(panel, attributes);
            }
        });

        // Hidden field
        addFieldInfoFactory("hidden", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new HiddenFieldInfo(panel, attributes);
            }
        });

        // "Readonly" field - just an HTML block
        addFieldInfoFactory("readonly", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new ReadonlyFieldInfo(panel, attributes);
            }
        });

        // Password field
        addFieldInfoFactory("password", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new PasswordFieldInfo(panel, attributes);
            }
        });

        // Simple button buttons
        addFieldInfoFactory("button", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new ButtonInfo(panel, attributes);
            }
        });
        // Submit buttons
        addFieldInfoFactory("submit", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new SubmitButtonInfo(panel, attributes);
            }
        });
        // Clear buttons
        addFieldInfoFactory("clear", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new ClearButtonInfo(panel, attributes);
            }
        });
        // Cancel buttons
        addFieldInfoFactory("cancel", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new CancelButtonInfo(panel, attributes);
            }
        });

        // File upload form element
        addFieldInfoFactory("file", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new FileUploadFieldInfo(panel, attributes);
            }
        });
        // Textarea
        addFieldInfoFactory("textarea", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new TextAreaInfo(panel, attributes);
            }
        });

        // Flow panel
        FieldInfoFactory simplePanelFactory = new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new SimplePanelInfo(panel, attributes);
            }
        };
        addFieldInfoFactory("panel", simplePanelFactory);
        // Inline panel
        addFieldInfoFactory("inlinePanel", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new InlinePanelInfo(panel, attributes);
            }
        });

        // Radiobutton
        addFieldInfoFactory("view", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new TemplatePanelInfo(panel, attributes);
            }
        });
        // Disclosure panel
        addFieldInfoFactory("disclosure", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new DisclosurePanelInfo(panel, attributes);
            }
        });
        // Header panel used to provider headers for disclosure panels
        FieldInfoFactory headerFactory = new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new HeaderPanelInfo(panel, attributes);
            }
        };
        addFieldInfoFactory("header", headerFactory);

        // Tab widgets
        addFieldInfoFactory("tabs", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new TabPanelInfo(panel, attributes);
            }
        });
        addFieldInfoFactory("tab", simplePanelFactory);

        // Label
        addFieldInfoFactory("blockLabel", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new BlockLabelInfo(panel, attributes);
            }
        });
        addFieldInfoFactory("label", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new InlineLabelInfo(panel, attributes);
            }
        });
        addFieldInfoFactory("htmlLabel", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new InlineHTMLLabelInfo(panel, attributes);
            }
        });
        // Hyperlink
        addFieldInfoFactory("link", new FieldInfoFactory() {
            public FieldInfo<?> newFieldInfo(
                TemplateTopPanel panel,
                Map<String, String> attributes) {
                return new HyperlinkFieldInfo(panel, attributes);
            }
        });

    }
}
