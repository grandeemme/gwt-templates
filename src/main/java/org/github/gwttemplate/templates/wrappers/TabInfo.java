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
import org.github.gwttemplate.templates.TemplatePanelContainer;
import org.github.gwttemplate.templates.TemplatePanel;
import org.github.gwttemplate.templates.TemplateTopPanel;

import java.util.Map;

import com.google.gwt.user.client.ui.SimplePanel;

/**
 * This is a wrapper for an individual tab view in a tab panel. Widgets of this
 * expect instances of the {@link TabPanelInfo} as their parents. The internal
 * content of this wrappers is transformed in an internal HTML panel and it will
 * be added to this panel as a content (see {@link TemplatePanelContainer}
 * interface)
 * 
 * 
 */
public class TabInfo extends FieldInfo<SimplePanel>
    implements
    TemplatePanelContainer {

    /**
     * This constructor initializes internal fields of this widget wrapper.
     * 
     * @param panel the form panel owning this widget wrapper
     * @param attributes a list of configuration attributes for this widget
     */
    public TabInfo(TemplateTopPanel panel, Map<String, String> attributes) {
        super(panel, attributes);
    }

    /**
     * @see gwt.templates.FieldInfo#newWidget()
     */
    protected SimplePanel newWidget() {
        return new SimplePanel();
    }

    /**
     * @see TemplatePanelContainer.templates.ITemplatePanelContainer#setTemplatePanel(gwt.templates.TemplatePanel)
     */
    public void setTemplatePanel(TemplatePanel templatePanel) {
        SimplePanel panel = getWidget();
        panel.setWidget(templatePanel);
    }

}
