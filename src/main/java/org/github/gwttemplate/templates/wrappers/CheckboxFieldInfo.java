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

import com.google.gwt.user.client.ui.CheckBox;

/**
 * A field wrapper for checkbox widgets
 * 
 * @see CheckBox
 * 
 */
public class CheckboxFieldInfo extends FieldInfo<CheckBox> {

    /**
     * The default checked state of the widget
     */
    boolean fChecked;

    /**
     * The value associated with this checkbox
     */
    String fValue;

    /**
     * This constructor initializes internal fields of this widget wrapper.
     * 
     * @param panel the form panel owning this widget wrapper
     * @param attributes a list of configuration attributes for this widget
     */
    public CheckboxFieldInfo(
        TemplateTopPanel panel,
        Map<String, String> attributes) {
        super(panel, attributes);
    }

    /**
     * @see gwt.templates.FieldInfo#clear()
     */
    public void clear() {
        CheckBox w = getWidget();
        w.setChecked(fChecked);
    }

    /**
     * @see gwt.templates.FieldInfo#getValue()
     */
    public Object getValue() {
        CheckBox w = getWidget();
        return w.isChecked() ? fValue : null;
    }

    /**
     * Returns a new {@link CheckBox} widget. This method can be overloaded in
     * subclasses.
     * 
     * @param label the label of the checkbox
     * @return a new {@link CheckBox} widget
     */
    protected CheckBox newCheckBox(String label) {
        return new CheckBox(label, true);
    }

    /**
     * @see gwt.templates.FieldInfo#newWidget()
     */
    protected CheckBox newWidget() {
        String label = getLabelFromAttributes();
        fValue = getDefaultValue();
        if (fValue == null) {
            fValue = label;
        }
        fChecked = getAttributeAsBoolean(NS_TEMPLATES + "checked", false);
        CheckBox w = newCheckBox(label);
        String name = getName();
        w.setName(name);
        w.setChecked(fChecked);
        return w;
    }

    /**
     * @see gwt.templates.FieldInfo#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled) {
        CheckBox checkBox = getWidget();
        checkBox.setEnabled(enabled);
    }

    /**
     * @see gwt.templates.FieldInfo#setValue(Object)
     */
    public boolean setValue(Object value) {
        if (equals(fValue, value)) {
            CheckBox checkBox = getWidget();
            checkBox.setChecked(true);
            return true;
        }
        return false;
    }
}