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
 * Loads a block of text and returns it in the specified callback object.
 * 
 * 
 */
public class TextLoader extends AbstractResourceLoader<String> {

    /**
     * The default constructor.
     * 
     * @param path the local path realative to the current application module
     */
    public TextLoader(String path) {
        super(path);
    }

    /**
     * @param baseURL the base URL used to build the full URL
     * @param path the local path relative to the base URL
     */
    public TextLoader(String baseURL, String path) {
        super(baseURL, path);
    }

    /**
     * @throws ResourceLoaderException
     * @see gwt.commons.io.ResourceLoader#getResult(com.google.gwt.http.client.Request,
     *      com.google.gwt.http.client.Response)
     */
    @Override
    protected String getResult(Request request, Response response)
        throws ResourceLoaderException {
        int statusCode = response.getStatusCode();
        if (statusCode != 200)
            throw new ResourceLoaderException(request, response);
        String text = response.getText();
        return text;
    }
}
