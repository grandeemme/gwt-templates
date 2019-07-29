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
package org.github.gwttemplate.templates.wrappers;

import org.github.gwttemplate.templates.FieldInfo;
import org.github.gwttemplate.templates.TemplateTopPanel;

import java.util.Map;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * A common wrapper for HTML panels.
 * 
 * 
 */
public class HTMLPanelInfo extends FieldInfo<Widget> {

    /**
     * This constructor initializes internal fields of this widget wrapper.
     * 
     * @param panel the form panel owning this widget wrapper
     * @param attributes a list of configuration attributes for this widget
     */
    public HTMLPanelInfo(TemplateTopPanel panel, Map<String, String> attributes) {
        super(panel, attributes);
    }

    /**
     * @see gwt.templates.FieldInfo#clear()
     */
    public void clear() {
        HasHTML html = (HasHTML) getWidget();
        html.setHTML("");
    }

    /**
     * @see gwt.templates.FieldInfo#getValue()
     */
    public Object getValue() {
        HasHTML html = (HasHTML) getWidget();
        return html.getHTML();
    }

    /**
     * @see gwt.templates.FieldInfo#newWidget()
     */
    protected Widget newWidget() {
        return new HTML();
    }

    /**
     * @see gwt.templates.FieldInfo#setValue(Object)
     */
    public boolean setValue(Object value) {
        HasHTML html = (HasHTML) getWidget();
        String str = value != null ? value.toString() : "";
        html.setHTML(str);
        return true;
    }
}