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
package org.github.gwttemplate.templates.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasHTML;

/**
 * A widget that can contain arbitrary HTML.
 * <p>
 * If you only need a simple label (text, but not HTML), then the
 * {@link com.google.gwt.user.client.ui.Label} widget is more appropriate, as it
 * disallows the use of HTML, which can lead to potential security issues if not
 * used properly.
 * </p>
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.gwt-HTML { }</li>
 * </ul>
 * <p>
 * <h3>Example</h3>
 * {@example com.google.gwt.examples.HTMLExample}
 * </p>
 */
public class InlineHTML extends InlineLabel implements HasHTML {

    /**
     * Creates an empty HTML widget.
     */
    public InlineHTML() {
        super();
        setStyleName("gwt-HTML");
    }

    /**
     * Creates an HTML widget with the specified HTML contents.
     * 
     * @param html the new widget's HTML contents
     */
    public InlineHTML(String html) {
        this();
        setHTML(html);
    }

    /**
     * Creates an HTML widget with the specified contents, optionally treating
     * it as HTML, and optionally disabling word wrapping.
     * 
     * @param html the widget's contents
     * @param wordWrap <code>false</code> to disable word wrapping
     */
    public InlineHTML(String html, boolean wordWrap) {
        this(html);
        setWordWrap(wordWrap);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasHTML#getHTML()
     */
    public String getHTML() {
        return DOM.getInnerHTML(getElement());
    }

    /**
     * @see com.google.gwt.user.client.ui.HasHTML#setHTML(java.lang.String)
     */
    public void setHTML(String html) {
        DOM.setInnerHTML(getElement(), html);
    }
}
