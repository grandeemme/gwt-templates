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
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

/**
 * This is a simple sub-class of the {@link TextLoader} interpreting loaded
 * resources as XML documents. This method parses the loaded text into into
 * {@link Document} objects and sends them to the corresponding callback.
 * 
 * 
 */
public class XMLLoader extends AbstractResourceLoader<Document> {

    /**
     * The default constructor.
     * 
     * @param path the path of the text resource to load
     */
    public XMLLoader(String path) {
        super(path);
    }

    /**
     * @param baseURL the base URL used to build the full URL
     * @param path the local path relative to the base URL
     */
    public XMLLoader(String baseURL, String path) {
        super(baseURL, path);
    }

    /**
     * This method transforms the text from the given response into an XML
     * document ({@link Document} instance).
     * 
     * @throws ResourceLoaderException
     * @see gwt.commons.io.AbstractResourceLoader#getResult(com.google.gwt.http.client.Request,
     *      com.google.gwt.http.client.Response)
     */
    @Override
    protected Document getResult(Request request, Response response)
        throws ResourceLoaderException {
        int statusCode = response.getStatusCode();
        if (statusCode != 200)
            throw new ResourceLoaderException(request, response);
        String text = response.getText();
        Document doc = XMLParser.parse(text);
        return doc;
    }

}