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
package org.github.gwttemplate.templates.cache;

/**
 * This is a text node containing the cached static text information.
 * 
 * 
 */
public class TextCacheNode extends CacheNode {

    /**
     * The cached static text
     */
    private String fText;

    /**
     * This constructor initializes the internal field with the cached text
     * content
     * 
     * @param text the text content to cache
     */
    public TextCacheNode(String text) {
        super();
        fText = text;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof TextCacheNode))
            return false;
        TextCacheNode node = (TextCacheNode) obj;
        return fText.equals(node.fText);
    }

    /**
     * Returns the cached text information corresponding to this node
     * 
     * @return the cached text information
     */
    public String getText() {
        return fText;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return fText != null ? fText.hashCode() : 0;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return fText;
    }

}