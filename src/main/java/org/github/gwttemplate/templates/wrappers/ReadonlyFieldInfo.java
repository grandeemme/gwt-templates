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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;

/**
 * A field wrapper corresponding to a "read only" (immutable) form element. It
 * contains an internal hidden field and an HTML panel showing the content of
 * this field.
 * 
 * 
 */
public class ReadonlyFieldInfo extends FieldInfo<FlowPanel> {

    /**
     * The hidden field managed by this widget wrapper. This hidden field
     * contains the value to commit.
     */
    Hidden fHidden;

    /**
     * The flow panel containing the hidden value and the corresponding label.
     */
    FlowPanel fPanel;

    /**
     * This panel contains the visual label corresponding to the hidden field
     * defined by the {@link #fHidden} panel.
     */
    HTML fView;

    /**
     * This constructor initializes internal fields of this widget wrapper.
     * 
     * @param panel the form panel owning this widget wrapper
     * @param attributes a list of configuration attributes for this widget
     */
    public ReadonlyFieldInfo(
        TemplateTopPanel panel,
        Map<String, String> attributes) {
        super(panel, attributes);
    }

    /**
     * @see gwt.templates.FieldInfo#clear()
     */
    public void clear() {
        setValue("");
    }

    /**
     * @see gwt.templates.FieldInfo#getValue()
     */
    public Object getValue() {
        return fHidden.getValue();
    }

    /**
     * @see gwt.templates.FieldInfo#newWidget()
     */
    protected FlowPanel newWidget() {
        fPanel = new FlowPanel();
        fHidden = new Hidden();
        fView = new HTML();
        fPanel.add(fHidden);
        fPanel.add(fView);
        return fPanel;
    }

    /**
     * @see gwt.templates.FieldInfo#setValue(Object)
     */
    public boolean setValue(Object value) {
        String str = value != null ? value.toString() : "";
        fHidden.setValue(str);
        fView.setHTML(str);
        return true;
    }

}