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

import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * A common wrapper for text widgets.
 * 
 * @see TextBoxBase
 * 
 * @param <T> the type of the text field widget
 */
public abstract class TextFieldInfo<T extends TextBoxBase> extends FieldInfo<T> {

    /**
     * This constructor initializes internal fields of this widget wrapper.
     * 
     * @param panel the form panel owning this widget wrapper
     * @param attributes a list of configuration attributes for this widget
     */
    public TextFieldInfo(TemplateTopPanel panel, Map<String, String> attributes) {
        super(panel, attributes);
    }

    /**
     * @see gwt.templates.FieldInfo#clear()
     */
    public void clear() {
        String value = getDefaultValue();
        getWidget().setText(value);
    }

    /**
     * @see gwt.templates.FieldInfo#getValue()
     */
    public Object getValue() {
        return getWidget().getText();
    }

    /**
     * @see gwt.templates.FieldInfo#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled) {
        getWidget().setEnabled(enabled);
    }

    /**
     * @see gwt.templates.FieldInfo#setValue(Object)
     */
    public boolean setValue(Object value) {
        String str = value != null ? value.toString() : "";
        getWidget().setText(str);
        return true;
    }

}