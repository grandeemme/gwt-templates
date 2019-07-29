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

import com.google.gwt.user.client.ui.Button;

/**
 * This is a common wrapper for buttons
 * 
 * @see Button
 * 
 */
public class ButtonInfo extends FieldInfo<Button> {

    /**
     * The internal counter used to give unique button name
     */
    private static int fCounter;

    /**
     * This constructor initializes internal fields of this widget wrapper.
     * 
     * @param panel the form panel owning this widget wrapper
     * @param attributes a list of configuration attributes for this widget
     */
    public ButtonInfo(TemplateTopPanel panel, Map<String, String> attributes) {
        super(panel, attributes);
    }

    /**
     * @see gwt.templates.FieldInfo#getName()
     */
    public String getName() {
        String name = super.getName();
        return name != null ? name : "Button-" + (fCounter++);
    }

    /**
     * @see gwt.templates.FieldInfo#newWidget()
     */
    protected Button newWidget() {
        String label = getLabelFromAttributes();
        if (label == null) {
            label = getDefaultValue();
        }
        Button b = new Button(label);
        return b;
    }

    /**
     * @see gwt.templates.FieldInfo#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled) {
        Button b = getWidget();
        b.setEnabled(enabled);
    }

}