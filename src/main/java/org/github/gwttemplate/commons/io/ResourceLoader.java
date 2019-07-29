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

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

/**
 * A simple sub-class of the {@link AbstractResourceLoader} class just returning
 * the {@link Response} object to the asynchronous listener.
 * 
 * 
 */
public class ResourceLoader extends AbstractResourceLoader<Response> {

    /**
     * @param path the local path realative to the current application module
     */
    public ResourceLoader(String path) {
        super(path);
    }

    /**
     * @param baseURL the base URL used to build the full URL
     * @param path the local path relative to the base URL
     */
    public ResourceLoader(String baseURL, String path) {
        super(baseURL, path);
    }

    /**
     * @see gwt.commons.io.AbstractResourceLoader#getResult(com.google.gwt.http.client.Request,
     *      com.google.gwt.http.client.Response)
     */
    @Override
    protected Response getResult(Request request, Response response) {
        return response;
    }

}