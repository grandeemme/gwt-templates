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

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * This wrapper is used to create submit buttons in templates. When user clicks
 * on this button then the internal listener calls
 * {@link TemplateTopPanel#onSubmit(Widget)} method which dispatches this call
 * to all submit listeners.
 * 
 * 
 */
public class SubmitButtonInfo extends ButtonInfo {

    /**
     * The default constructor
     * 
     * @param panel
     * @param attributes
     */
    public SubmitButtonInfo(
        TemplateTopPanel panel,
        Map<String, String> attributes) {
        super(panel, attributes);
    }

    /**
     * @see gwt.templates.wrappers.ButtonInfo#newWidget()
     */
    @Override
    protected Button newWidget() {
        Button button = super.newWidget();
        button.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                fPanel.onSubmit(sender);
            }
        });
        return button;
    }

}
