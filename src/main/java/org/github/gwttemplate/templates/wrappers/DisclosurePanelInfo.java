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
import org.github.gwttemplate.templates.widgets.InlineHTML;
import org.github.gwttemplate.templates.widgets.SimpleDisclosurePanel;

import java.util.Map;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a wrapper for the {@link DisclosurePanel} widgets which are used to create a disclosure panel with a
 * clickable header and openable/closable panels. This panel shows/hides the HTML defined by the template with all
 * containing child fields.
 * 
 * @see DisclosurePanel
 * 
 */
public class DisclosurePanelInfo extends FieldInfo<SimpleDisclosurePanel> implements TemplatePanelContainer {

	/**
	 * The URI of the field used as a header of the disclosure panel.
	 */
	private static final String TAG_HEADER = NS_TEMPLATES + "header";

	/**
	 * The header widget of this disclosure panel.
	 */
	private Widget fHeader;

	/**
	 * This constructor initializes internal fields of this widget wrapper.
	 * 
	 * @param panel
	 *            the form panel owning this widget wrapper
	 * @param attributes
	 *            a list of configuration attributes for this widget
	 */
	public DisclosurePanelInfo(TemplateTopPanel panel, Map<String, String> attributes) {
		super(panel, attributes);
	}

	/**
	 * @see gwt.templates.FieldInfo#addChildFieldInfo(gwt.templates.FieldInfo)
	 */
	@Override
	public boolean addChildFieldInfo(FieldInfo<?> child) {
		boolean add = super.addChildFieldInfo(child);
		String tagURI = child.getTagURI();
		if (fHeader == null && TAG_HEADER.equals(tagURI)) {
			SimpleDisclosurePanel panel = getWidget();
			fHeader = child.getWidget();
			panel.setHeader(fHeader);
			add = false;
		}
		return add;
	}

	/**
	 * @see gwt.templates.FieldInfo#initializeWidget(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	protected void initializeWidget(SimpleDisclosurePanel w) {
		super.initializeWidget(w);
		if (fHeader == null) {
			String label = getLabelFromAttributes();
			fHeader = new Label(label);
			SimpleDisclosurePanel panel = getWidget();
			panel.setHeader(fHeader);
		}
	}

	/**
	 * @see gwt.templates.FieldInfo#newWidget()
	 */
	@Override
	protected SimpleDisclosurePanel newWidget() {
		SimpleDisclosurePanel panel = new SimpleDisclosurePanel();
		panel.setAnimationEnabled(true);
		boolean open = getAttributeAsBoolean(NS_TEMPLATES + "open", false);
		panel.setOpen(open);
		return panel;
	}

	/**
	 * @see TemplatePanelContainer.templates.ITemplatePanelContainer#setTemplatePanel(gwt.templates.TemplatePanel)
	 */
	public void setTemplatePanel(TemplatePanel templatePanel) {
		SimpleDisclosurePanel panel = getWidget();
		panel.setContent(templatePanel);
	}

	/**
	 * @see gwt.templates.FieldInfo#setValue(java.lang.Object)
	 */
	@Override
	public boolean setValue(Object value) {
		String str = value != null ? value.toString() : getLabelFromAttributes();
		SimpleDisclosurePanel panel = getWidget();
		InlineHTML header = new InlineHTML(str);
		panel.setHeader(header);
		return true;
	}

}
