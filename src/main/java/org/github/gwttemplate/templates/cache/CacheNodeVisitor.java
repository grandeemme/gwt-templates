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
 * Instances of this type are used to visit cache nodes and perform some
 * specific actions
 * 
 * 
 */
public interface CacheNodeVisitor {

    /**
     * This method is called when this visitors is accepted by a
     * {@link CompositeCacheNode} node
     * 
     * @param node the node to visit
     */
    void visit(CompositeCacheNode node);

    /**
     * This method is called when this visitors is accepted by a
     * {@link TextCacheNode} node. Normally it just appends the content of this
     * node to the final result.
     * 
     * @param node the node to visit
     */
    void visit(TextCacheNode node);
}