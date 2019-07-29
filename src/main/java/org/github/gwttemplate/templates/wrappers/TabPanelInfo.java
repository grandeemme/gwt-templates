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

import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This field wrapper corresponds to a {@link TabPanel} widget. It expects the
 * {@link SimplePanelInfo} fields as their children. Such children define
 * individual tabs in this tab panel.
 * 
 * 
 */
public class TabPanelInfo extends FieldInfo<TabPanel> {

    /**
     * This constructor initializes internal fields of this widget wrapper.
     * 
     * @param panel the form panel owning this widget wrapper
     * @param attributes a list of configuration attributes for this widget
     */
    public TabPanelInfo(TemplateTopPanel panel, Map<String, String> attributes) {
        super(panel, attributes);
    }

    /**
     * @see gwt.templates.FieldInfo#addChildFieldInfo(gwt.templates.FieldInfo)
     */
    public boolean addChildFieldInfo(FieldInfo<?> child) {
        boolean add = super.addChildFieldInfo(child);
        if (child instanceof SimplePanelInfo) {
            TabPanel panel = getWidget();
            SimplePanelInfo i = (SimplePanelInfo) child;
            String label = i.getLabelFromAttributes();
            Widget w = i.getWidget();
            panel.add(w, label);
            boolean selected = i.getAttributeAsBoolean(NS_TEMPLATES
                + "selected", false);
            if (selected) {
                int count = panel.getTabBar().getTabCount();
                panel.selectTab(count - 1);
            }
            add = false;
        }
        return add;
    }

    /**
     * @see gwt.templates.FieldInfo#newWidget()
     */
    protected TabPanel newWidget() {
        TabPanel panel = new TabPanel();
        return panel;
    }

}
