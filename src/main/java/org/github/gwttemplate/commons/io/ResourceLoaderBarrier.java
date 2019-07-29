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
package org.github.gwttemplate.commons.io;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is a synchronization tool used to wait a set of resource loader and
 * launch a user-specific code when all resources are loaded.
 * 
 * 
 */
public abstract class ResourceLoaderBarrier {

    /**
     * List of callback objects for each resource loader. Each item in this list
     * corresponds to a resource loader from the {@link #fLoaders} list.
     */
    private List<AsyncCallback<?>> fCallbacks = new ArrayList<AsyncCallback<?>>();

    /**
     * List of all failures ({@link Throwable} instances) returned by loaders.
     */
    private List<Throwable> fFailureList;

    /**
     * List of resource loaders. Each item in this list corresponds to a
     * callback object stored in the {@link #fCallbacks} list.
     */
    private List<AbstractResourceLoader<?>> fLoaders = new ArrayList<AbstractResourceLoader<?>>();

    /**
     * List of successfull results returned by loaders.
     */
    private List<Object> fSuccessList;

    /**
     * Adds a new loader to the internal list. This loader is activated (the
     * method {@link ResourceLoader#load(String, AsyncCallback)} is called) with
     * the given callback object only when the {@link #load()} method is called.
     * 
     * @param loader the loader to add to this barrier
     * @param callback the callback object used to notify about results of the
     *        loading process
     */
    public <T> void add(
        AbstractResourceLoader<T> loader,
        AsyncCallback<T> callback) {
        checkActivation();
        fLoaders.add(loader);
        fCallbacks.add(callback);
    }

    /**
     * Adds a new loader to the internal list. This loader is activated (the
     * method {@link ResourceLoader#load(AsyncCallback)} or
     * {@link ResourceLoader#load(String, AsyncCallback)} methods are called)
     * only when the {@link #load()} method is called.
     * 
     * @param loader the loader to add to this barrier
     */
    public void add(ResourceLoader loader) {
        add(loader, null);
    }

    /**
     * Checks that this barrier was not activated yet - that the {@link #load()}
     * was not called yet. If the {@link #load()} method was already called then
     * this method throws a runtime exception.
     */
    private void checkActivation() {
        if (fSuccessList != null)
            throw new RuntimeException("The barrieer is  already activated");
    }

    /**
     * This method activates all resource loaders from the internal list. When
     * all loaders finish their work (they are finished successfully or failed)
     * then this method asynchronously calls the {@link #onFinish(List, List)}
     * method.
     * 
     * @see #onFinish(List, List)
     */
    @SuppressWarnings("unchecked")
    public void load() {
        checkActivation();
        fSuccessList = new ArrayList<Object>();
        fFailureList = new ArrayList<Throwable>();
        final int len = fLoaders.size();
        for (int i = 0; i < len; i++) {
            AbstractResourceLoader<Object> loader = (AbstractResourceLoader<Object>) fLoaders
                .get(i);
            final AsyncCallback<Object> callback = (AsyncCallback<Object>) fCallbacks
                .get(i);
            loader.load(new AsyncCallback<Object>() {

                /**
                 * This method checks if all loaders finish their work and if so
                 * it calls the
                 * {@link ResourceLoaderBarrier#onFinish(List, List)} method
                 */
                private void checkEnd() {
                    if (fSuccessList.size() + fFailureList.size() == len) {
                        onFinish(fSuccessList, fFailureList);
                    }
                }

                /**
                 * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
                 */
                public void onFailure(Throwable caught) {
                    fFailureList.add(caught);
                    try {
                        if (callback != null) {
                            callback.onFailure(caught);
                        }
                    } finally {
                        checkEnd();
                    }
                }

                /**
                 * @see com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
                 */
                public void onSuccess(Object result) {
                    fSuccessList.add(result);
                    try {
                        if (callback != null) {
                            callback.onSuccess(result);
                        }
                    } finally {
                        checkEnd();
                    }
                }
            });
        }
    }

    /**
     * This method is called asynchronously by the {@link #load()} method when
     * all resource loaders finished their work. This method is called with two
     * parameters: the first is a list of successful results and the second is a
     * list of loader failures. The sum number of elements in both lists is
     * always equals to the number of registered resource loaders. <br /> This
     * method should be defined in subclasses
     * 
     * @param successList a list of all successful results returned by resource
     *        loader (in the order of operation terminations)
     * @param failureList a list of exceptions ({@link Throwable} instances)
     *        rised by resource loader (in the order of operation terminations)
     */
    protected abstract void onFinish(
        List<Object> successList,
        List<Throwable> failureList);
}