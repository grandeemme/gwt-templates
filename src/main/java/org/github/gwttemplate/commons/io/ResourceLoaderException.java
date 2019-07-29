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
 * This is a wrapper exception used to deliver to the caller not only the
 * initial exception but also the HTTP request/response.
 * 
 * 
 */
public class ResourceLoaderException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -3813361916158553078L;

    /**
     * The original exception
     */
    private Throwable fException;

    /**
     * The HTTP request.
     */
    private Request fRequest;

    /**
     * The HTTP response
     */
    private Response fResponse;

    /**
     * This constructor activates internal fields
     * 
     * @param request the HTTP request corresponding to this exception
     * @param response the HTTP response corresponding to this exception
     */
    public ResourceLoaderException(Request request, Response response) {
        fRequest = request;
        fResponse = response;
    }

    /**
     * This constructor activates internal fields
     * 
     * @param request the HTTP request corresponding to this exception
     * @param response the HTTP response corresponding to this exception
     * @param e the wrapped exception/error
     */
    public ResourceLoaderException(
        Request request,
        Response response,
        Throwable e) {
        fRequest = request;
        fResponse = response;
        fException = e;
    }

    /**
     * Returns a wrapped exception/error
     * 
     * @return a wrapped exception/error
     */
    public Throwable getException() {
        return fException;
    }

    /**
     * Returns the HTTP request corresponding to this exception
     * 
     * @return the HTTP request corresponding to this exception
     */
    public Request getRequest() {
        return fRequest;
    }

    /**
     * Returns the HTTP response corresponding to this exception
     * 
     * @return the HTTP response corresponding to this exception
     */
    public Response getResponse() {
        return fResponse;
    }

}
