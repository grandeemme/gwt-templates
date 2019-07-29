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

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.xml.client.Attr;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;

/**
 * This is an utility class containing methods used to work with XML (like XML
 * serialization / deserialization).
 * 
 * 
 */
public class XMLUtil {

    /**
     * Escapes (if required) all special symbols in the given string and appends
     * the result to the given buffer
     * 
     * @param e the value to escape
     * @param buf the buffer where the escaped value should be appended
     */
    static void escape(String e, StringBuffer buf) {
        char[] array = e.toCharArray();
        for (int i = 0; i < array.length; i++) {
            char ch = array[i];
            switch (ch) {
                case '&':
                    buf.append("&amp;");
                    break;
                case '<':
                    buf.append("&lt;");
                    break;
                case '>':
                    buf.append("&gt;");
                    break;
                default:
                    buf.append(ch);
                    break;
            }
        }
    }

    /**
     * This method returns an attribute of the given node corresponding to the
     * specified full URL of the node.
     * 
     * @param e the element where the attribute should be loaded
     * @param attributeUrl the full URL of the attribute to search
     * @return the attribute corresponding to the given full URL
     */
    public static Attr getAttributeNodeNS(Element e, String attributeUrl) {
        Attr result = null;
        NamedNodeMap attributes = e.getAttributes();
        for (int i = 0; result == null && i < attributes.getLength(); i++) {
            Attr attr = (Attr) attributes.item(i);
            String url = getURL(attr);
            if (attributeUrl.equals(url))
                result = attr;
        }
        return result;
    }

    /**
     * Returns a map containing all attributes of the given element. This map
     * contains full URLs of attributes with the corresponding attribute values.
     * 
     * @param e the element used as a source of attributes and the corresponding
     *        values
     * @return a map containing all attributes of the given element
     */
    public static Map<String, String> getAttributes(Element e) {
        Map<String, String> result = new HashMap<String, String>();
        NamedNodeMap attributes = e.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Attr attr = (Attr) attributes.item(i);
            String url = getURL(attr);
            result.put(url, attr.getValue());
        }
        return result;
    }

    /**
     * This method returns a value of the element attribute with the specified
     * URL.
     * 
     * @param e the element where the attribute should be loaded
     * @param attributeUrl the full URL of the attribute to search
     * @return the attribute value corresponding to the given full URL
     */
    public static String getAttributeValueNS(Element e, String attributeUrl) {
        Attr attr = getAttributeNodeNS(e, attributeUrl);
        return attr != null ? attr.getValue() : null;
    }

    /**
     * Returns the local name of the given URI
     * 
     * @param tagURI the URI to split into the parts
     * @return the local name of the given URI
     */
    public static String getLocalName(String tagURI) {
        int idx = getNamespaceIndex(tagURI);
        return idx > 0 ? tagURI.substring(idx + 1) : null;
    }

    /**
     * Returns the namespace URI for the given full URI
     * 
     * @param tagURI the URI to split into the parts
     * @return the namespace URI corresponding to the given full URI
     */
    public static String getNamespace(String tagURI) {
        int idx = getNamespaceIndex(tagURI);
        return idx > 0 ? tagURI.substring(0, idx + 1) : null;
    }

    /**
     * Returns the index of the last character of the namespace in the given URI
     * 
     * @param tagURI the URI where the last index of the namespace should be
     *        found
     * @return the index of the last character of the namespace in the given URI
     */
    private static int getNamespaceIndex(String tagURI) {
        if (tagURI == null)
            return -1;
        int idx = tagURI.lastIndexOf('#');
        if (idx < 0) {
            idx = tagURI.lastIndexOf('/');
        }
        return idx;
    }

    /**
     * This method returns the URL corresponding to the given XML node using the
     * namespace of the node and the local name.
     * 
     * @param node the node for which the corresponding should be returned
     * @return an URL corresponding to the given XML node
     */
    public static String getURL(Node node) {
        if (node == null)
            return null;
        String prefix = node.getPrefix();
        String name = node.getNodeName();
        if (prefix != null) {
            name = name.substring(prefix.length() + 1);
        }
        String ns = node.getNamespaceURI();
        if (ns != null && !ns.endsWith("#") && !ns.endsWith("/")) {
            ns += "#";
        }
        return ns != null ? ns + name : name;
    }

    /**
     * Generates a closing tag corresponding to the specified XML node and
     * appends it to the given buffer
     * 
     * @param node the node for which the closing tag should be generated
     * @param buf the buffer where the result is appended
     */
    public static void serializeCloseTag(Element node, StringBuffer buf) {
        Element e = node;
        String name = e.getNodeName();
        buf.append("</");
        buf.append(name);
        buf.append(">");
    }

    /**
     * Serializes the given element as XML and appends it to the given string
     * buffer
     * 
     * @param e the element to serialize
     * @param buf the string buffer used to accumulate the content of the node
     */
    public static void serializeNode(Element e, StringBuffer buf) {
        serializeOpenTag(e, buf);
        serializeNodeContent(e, buf);
        serializeCloseTag(e, buf);
    }

    /**
     * Serializes all children of the given element as XML and appends this XML
     * to the given string buffer
     * 
     * @param e the element to serialize
     * @param buf the string buffer used to accumulate the string content of the
     *        node
     */
    public static void serializeNodeContent(Element e, StringBuffer buf) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element) {
                Element c = (Element) child;
                serializeOpenTag(c, buf);
                serializeNodeContent(c, buf);
                serializeCloseTag(c, buf);
            } else if (child instanceof Text) {
                Text text = (Text) child;
                serializeText(text, buf);
            }
            child = child.getNextSibling();
        }
    }

    /**
     * Generates an opening tag
     * correspohttp://88.191.47.84/repos/dev/sandbox/trunknding to the specified
     * XML node and appends it to the buffer
     * 
     * @param e the XML element for which an opening tag should be generated
     * @param buf the buffer where the result is appended
     */
    public static void serializeOpenTag(Element e, StringBuffer buf) {
        String name = e.getTagName();
        buf.append("<");
        buf.append(name);
        NamedNodeMap attributes = e.getAttributes();
        int len = attributes.getLength();
        for (int i = 0; i < len; i++) {
            Attr attr = (Attr) attributes.item(i);
            String attrName = attr.getName();
            String attrValue = attr.getValue();
            buf.append(" ");
            buf.append(attrName);
            buf.append("='");
            escape(attrValue, buf);
            buf.append("'");
        }
        buf.append(">");
    }

    /**
     * Serializes the given text node and appends its content to the given
     * buffer
     * 
     * @param node the text node to serialize
     * @param buf the buffer where the result is appended
     */
    public static void serializeText(Text node, StringBuffer buf) {
        escape(node.getData(), buf);
    }

    /**
     * This method maps URLs of element attributes corresponding string values.
     * 
     * @param e the element used as a source of parameters
     * @return a map of attribute URLs with corresponding values
     */
    public static Map<String, String> toParameters(Element e) {
        Map<String, String> result = new HashMap<String, String>();
        NamedNodeMap attributes = e.getAttributes();
        int len = attributes.getLength();
        for (int i = 0; i < len; i++) {
            Attr attr = (Attr) attributes.item(i);
            String key = getURL(attr);
            String value = attr.getValue();
            result.put(key, value);
        }
        return result;
    }

    /**
     * 
     */
    private XMLUtil() {
        super();
    }

}
