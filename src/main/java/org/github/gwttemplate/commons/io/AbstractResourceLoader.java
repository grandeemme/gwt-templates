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

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is a common interface for resource loaded. This class tries to load the
 * resource specified by the local path with the given content and calls the
 * given callback object when the resource is loaded or the load process is
 * failed. This class takes the local path to the resource to load and build the
 * full URL using the {@link GWT#getModuleBaseURL()} method:
 * <code>String URL = GWT.getModuleBaseURL() + path</code>
 * 
 * 
 * @param <T> the type of the loaded resource
 */
public abstract class AbstractResourceLoader<T> {

    /**
     * The full URL to the local resource to load.
     */
    protected String fUrl;

    /**
     * The default constructor initializing the full URL to the resource to
     * load. This constructor adds the specified path to the base URL of the
     * current application module (it uses {@link GWT#getModuleBaseURL()} method
     * for that).
     * 
     * @param path the local path to the resource to load
     */
    public AbstractResourceLoader(String path) {
        this(GWT.getModuleBaseURL(), path);
    }

    /**
     * The default constructor initializing the full URL to the resource to
     * load.
     * 
     * @param baseURL the base URL used to add the specified path
     * @param path the local path to the resource to load
     */
    public AbstractResourceLoader(String baseURL, String path) {
        fUrl = baseURL + path;
    }

    /**
     * This method transforms the given HTTP request and responses into a final
     * result
     * 
     * @param request the initial HTTP request object
     * @param response the HTTP response from the server
     * @return the result corresponding to the given HTTP request and response
     * @throws ResourceLoaderException
     */
    protected abstract T getResult(Request request, Response response)
        throws ResourceLoaderException;

    /**
     * Loads the local resource and calls the given callback object when the
     * resource is loaded or loading process is failed. This method uses the
     * POST HTTP method to submit the request.
     * 
     * @param callback the callback method used to notify about results of the
     *        loading process
     */
    public final void load(AsyncCallback<T> callback) {
        load("", callback);
    }

    /**
     * Loads the local resource and calls the given callback object when the
     * resource is loaded or loading process is failed. This method uses the
     * POST HTTP method to submit the given content and to retrieve the
     * response.
     * 
     * @param content the content to submit to the server
     * @param callback the callback method used to notify about results of the
     *        loading process
     */
    public final void load(String content, final AsyncCallback<T> callback) {
        RequestBuilder request = newRequest();
        try {
            request.sendRequest(content, new RequestCallback() {

                /**
                 * @see com.google.gwt.http.client.RequestCallback#onError(com.google.gwt.http.client.Request,
                 *      java.lang.Throwable)
                 */
                public void onError(Request request, Throwable exception) {
                    callback.onFailure(exception);
                }

                /**
                 * @see com.google.gwt.http.client.RequestCallback#onResponseReceived(com.google.gwt.http.client.Request,
                 *      com.google.gwt.http.client.Response)
                 */
                public void onResponseReceived(
                    Request request,
                    Response response) {
                    try {
                        T result = getResult(request, response);
                        callback.onSuccess(result);
                    } catch (ResourceLoaderException e) {
                        callback.onFailure(e);
                    } catch (Exception e) {
                        callback.onFailure(new ResourceLoaderException(
                            request,
                            response,
                            e));
                    }
                }
            });
        } catch (RequestException e) {
            callback.onFailure(e);
        }

    }

    /**
     * Returns a new HTTP request object
     * 
     * @return a new HTTP request object
     */
    protected RequestBuilder newRequest() {
        return new RequestBuilder(RequestBuilder.GET, fUrl);
    }

}
