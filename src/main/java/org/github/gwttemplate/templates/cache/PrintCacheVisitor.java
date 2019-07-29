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
 * This is a simple implementation of the {@link CacheNodeVisitor} interface
 * which is used to just print out the content of text blocks.
 * 
 * 
 */
public abstract class PrintCacheVisitor implements CacheNodeVisitor {

    /**
     * This method should be overloaded in subclasses to really print the given
     * string.
     * 
     * @param str the string to print
     */
    protected abstract void print(String str);

    /**
     * @see CacheNodeVisitor.templates.cache.ICacheNodeVisitor#visit(gwt.templates.cache.CompositeCacheNode)
     */
    public void visit(CompositeCacheNode node) {
        for (CacheNode child : node) {
            child.accept(this);
        }
    }

    /**
     * @see CacheNodeVisitor.templates.cache.ICacheNodeVisitor#visit(gwt.templates.cache.TextCacheNode)
     */
    public void visit(TextCacheNode node) {
        String str = node.getText();
        print(str);
    }

}
