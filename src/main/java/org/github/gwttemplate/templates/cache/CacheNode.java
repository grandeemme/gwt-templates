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
 * The common super-class for all cache nodes
 * 
 * 
 */
public abstract class CacheNode {

    /**
     * The default constructor.
     */
    public CacheNode() {
        super();
    }

    /**
     * Accept the given visitor and dispatchs the call to the
     * {@link CacheNodeVisitor#visit(CompositeCacheNode)} or
     * {@link CacheNodeVisitor#visit(TextCacheNode)} methods depending on the
     * type of this node.
     * 
     * @param visitor the visitor to accept
     */
    public void accept(CacheNodeVisitor visitor) {
        if (this instanceof CompositeCacheNode) {
            visitor.visit((CompositeCacheNode) this);
        } else {
            visitor.visit((TextCacheNode) this);
        }
    }

}