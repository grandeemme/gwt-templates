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

import org.github.gwttemplate.templates.TemplateTopPanel;

import java.util.Map;

import com.google.gwt.user.client.ui.TextArea;

/**
 * A field wrapper corresponding to a text area.
 * 
 * @see TextArea
 * 
 */
public class TextAreaInfo extends TextFieldInfo<TextArea> {

    /**
     * This constructor initializes internal fields of this widget wrapper.
     * 
     * @param panel the form panel owning this widget wrapper
     * @param attributes a list of configuration attributes for this widget
     */
    public TextAreaInfo(TemplateTopPanel panel, Map<String, String> attributes) {
        super(panel, attributes);
    }

    /**
     * @see gwt.templates.FieldInfo#newWidget()
     */
    @Override
    protected TextArea newWidget() {
        TextArea widget = new TextArea();
        String name = getName();
        widget.setName(name);
        String value = getDefaultValue();
        widget.setText(value);
        return widget;
    }
}