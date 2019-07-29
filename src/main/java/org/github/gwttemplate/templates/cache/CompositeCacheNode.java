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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The composite node containing text nodes and other composite cache nodes
 * 
 * 
 */
public class CompositeCacheNode extends CacheNode
    implements
    Iterable<CacheNode> {

    /**
     * The list of children (text and composite nodes)
     */
    private List<CacheNode> fList = new ArrayList<CacheNode>();

    /**
     * 
     */
    public CompositeCacheNode() {
        super();
    }

    /**
     * Adds the given cache node as a children to this node
     * 
     * @param node the node to add
     */
    void addNode(CacheNode node) {
        fList.add(node);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof CompositeCacheNode))
            return false;
        CompositeCacheNode node = (CompositeCacheNode) obj;
        return fList.equals(node.fList);
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        int l = fList.hashCode();
        return l;
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<CacheNode> iterator() {
        return fList.iterator();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return fList.toString();
    }

}