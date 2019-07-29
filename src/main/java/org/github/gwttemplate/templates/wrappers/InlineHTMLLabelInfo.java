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
import org.github.gwttemplate.templates.widgets.InlineHTML;

import java.util.Map;

/**
 * This widget wrapper is used to visualize HTML labels (label content is
 * handled as HTML).
 * 
 * @see InlineHTML
 * 
 */
public class InlineHTMLLabelInfo extends FieldInfo<InlineHTML> {

    /**
     * This constructor initializes internal fields of this widget wrapper.
     * 
     * @param panel the form panel owning this widget wrapper
     * @param attributes a list of configuration attributes for this widget
     */
    public InlineHTMLLabelInfo(
        TemplateTopPanel panel,
        Map<String, String> attributes) {
        super(panel, attributes);
    }

    /**
     * @see gwt.templates.FieldInfo#newWidget()
     */
    @Override
    protected InlineHTML newWidget() {
        String label = getLabelFromAttributes();
        return new InlineHTML(label);
    }

}
