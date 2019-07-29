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

import java.util.Iterator;

/**
 * The cache builder is used to transform tree structures into a tree of
 * continuous text blocks ({@link TextCacheNode}) and composite nodes ({@link CompositeCacheNode}).
 * Such a cache is very useful for serialization of hierarchical structures
 * containing embedded dynamic elements. A sequence of multiple static elements
 * is transformed directly into a text block ({@link TextCacheNode} instances)
 * and dynamic elements are transformed into {@link CompositeCacheNode} blocks.
 * To transform this cache into the final content the corresponding
 * {@link CacheNodeVisitor} visitors are used. These visitors just takes the
 * content of text nodes and they can perform some actions for composite nodes.
 * 
 * @param <T> the type of tree nodes for which the cache is build
 * 
 */
public abstract class CacheBuilder<T> {

    /**
     * The intermediate buffer used to collect the text content generated for
     * tree elements.
     */
    private StringBuffer fHtml = new StringBuffer();

    /**
     * The topmost composite node; the whole cached structure is appended to
     * this node
     */
    private CompositeCacheNode fTop;

    /**
     * @param object the object
     * @return the dynamic cache node
     */
    public CompositeCacheNode build(T object) {
        fTop = newTopNode(object);
        doBuild(object);
        flushText();
        CompositeCacheNode top = fTop;
        fTop = null;
        return top;
    }

    /**
     * @param object
     */
    private void doBuild(T object) {
        CompositeCacheNode top = fTop;
        CompositeCacheNode compositeNode = getCompositeNode(object);
        String str = getTextPrefix(object, compositeNode);
        if (str != null) {
            fHtml.append(str);
        }
        if (compositeNode != null) {
            flushText();
            fTop.addNode(compositeNode);
            fTop = compositeNode;
        }
        Iterator<T> iterator = getChildren(object, compositeNode);
        while (iterator != null && iterator.hasNext()) {
            T n = iterator.next();
            doBuild(n);
        }
        str = getTextSuffix(object, compositeNode);
        if (str != null) {
            fHtml.append(str);
        }
        if (compositeNode != null) {
            flushText();
        }
        fTop = top;
    }

    private void flushText() {
        if (fHtml != null && fHtml.length() > 0) {
            TextCacheNode node = new TextCacheNode(fHtml.toString());
            fTop.addNode(node);
        }
        fHtml.delete(0, fHtml.length());
    }

    /**
     * @param object the object for which a list of children should be returned
     * @param compositeNode the composite node corresponding to the given object
     *        returned by the {@link #getCompositeNode(Object)} method; it can
     *        be <code>null</code>
     * @return an iterator over all child nodes of the specified object
     */
    protected abstract Iterator<T> getChildren(
        T object,
        CompositeCacheNode compositeNode);

    /**
     * Returns a dynamic node corresponding to the specified object. This method
     * returns <code>null</code> if this object does not correspond to a
     * dynamic node.
     * 
     * @param object for this object the corresponding dynamic node should be
     *        created and returned
     * @return a dynamic node corresponding to the specified object
     */
    protected abstract CompositeCacheNode getCompositeNode(T object);

    /**
     * Returns the text prefix corresponding to the specified object; this
     * method can return <code>null</code>
     * 
     * @param object for this object the corresponding text prefix should be
     *        returned
     * @param compositeNode the composite node corresponding to the given object
     *        returned by the {@link #getCompositeNode(Object)} method; it can
     *        be <code>null</code>
     * @return the text prefix corresponding to the specified object; this
     *         method can return <code>null</code>
     */
    protected abstract String getTextPrefix(
        T object,
        CompositeCacheNode compositeNode);

    /**
     * Returns the text suffix corresponding to the specified object; this
     * method can return <code>null</code>
     * 
     * @param object for this object the corresponding text suffix should be
     *        returned
     * @param compositeNode the composite node corresponding to the given object
     *        returned by the {@link #getCompositeNode(Object)} method; it can
     *        be <code>null</code>
     * @return the text suffix corresponding to the specified object; this
     *         method can return <code>null</code>
     */
    protected abstract String getTextSuffix(
        T object,
        CompositeCacheNode compositeNode);

    /**
     * Creates and returns the top composite cache node corresponding to the
     * specified object
     * 
     * @param object for this object the corresponding cache node will be
     *        returned
     * @return the top composite cache node corresponding to the specified
     *         object
     */
    protected abstract CompositeCacheNode newTopNode(T object);
}
