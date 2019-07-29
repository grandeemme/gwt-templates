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
 * This is a button wrapper generating buttons used to clean forms. When user
 * clicks on this button then the internal listener calls
 * {@link TemplateTopPanel#clear()} or
 * {@link TemplateTopPanel#clear(String, boolean)} methods - it depends on the
 * "for" parameter of the tag defining this widget. <br />
 * Parameters of the tag defining this field:
 * <dl>
 * <dt>"for" (optional)</dt>
 * <dd>This parameter defines the name of the field to clear. If this field is
 * defined then this button cleans up only the specified field. Otherwise it
 * cleans up the whole parent field panel.</dd>
 * <dt>"children" (optional)</dt>
 * <dd>If this parameter is <code>true</code> then the button cleans up all
 * children of the widget with the name defined in the "for" parameter. This
 * parameter is taken into account only if the "for" parameter is defined. The
 * default value of this parameter is <code>true</code>.</dd>
 * </dl>
 * 
 * @see Button
 * 
 */
public class ClearButtonInfo extends ButtonInfo {

    /**
     * The main constructor.
     * 
     * @param panel the top panel owning this field wrapper
     * @param attributes the configuration attributes of this field wrapper
     */
    public ClearButtonInfo(
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
                String name = getAttribute(NS_TEMPLATES + "for");
                if (name != null) {
                    boolean children = getAttributeAsBoolean(NS_TEMPLATES
                        + "children", true);
                    fPanel.clear(name, children);
                } else {
                    fPanel.clear();
                }
            }
        });
        return button;
    }

}
