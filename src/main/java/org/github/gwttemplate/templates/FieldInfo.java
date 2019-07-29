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
package org.github.gwttemplate.templates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

/**
 * A common superclass for all wrappers of form widgets. The goal of this wrapper is to provide a common set of actions
 * for various form widgets.
 * 
 * 
 * @param <W>
 *            the type of the widget managed by this field wrapper
 */
public abstract class FieldInfo<W extends Widget> implements Iterable<FieldInfo<?>>, TemplateNamespaces {

	/**
	 * The counter used to generate unique names. It is used only for fields without explicit names.
	 */
	private static int fNameCounter;

	/**
	 * Compares the given values and returns <code>true</code> if they are equal.
	 * 
	 * @param first
	 *            the first value to compare
	 * @param second
	 *            the second value to compare
	 * @return <code>true</code> if both values are equals
	 */
	protected static boolean equals(Object first, Object second) {
		return first != null && second != null ? first.equals(second) : first == second;
	}

	/**
	 * Configuration attributes of this field
	 */
	private Map<String, String> fAttributes;

	/**
	 * This list contains all children ({@link FieldInfo} instances)
	 */
	private List<FieldInfo<?>> fChildren;

	/**
	 * The name of this widget.
	 */
	private String fName;

	/**
	 * The topmost panel owning this info
	 */
	protected TemplateTopPanel fPanel;

	/**
	 * The parent field
	 */
	protected FieldInfo<?> fParent;

	/**
	 * The widget corresponding to this field
	 */
	private W fWidget;

	/**
	 * This constructor initializes internal fields of this widget wrapper.
	 * 
	 * @param panel
	 *            the form panel owning this widget wrapper
	 * @param attributes
	 *            a map of attributes (parameters) for this field info
	 */
	public FieldInfo(TemplateTopPanel panel, Map<String, String> attributes) {
		fAttributes = attributes;
		fPanel = panel;
		fPanel.registerFieldInfo(this);
	}

	/**
	 * Accept the specified visitor. By default this method just calls the {@link FieldInfoVIsitor#visit(FieldInfo)}
	 * method.
	 * 
	 * @param visitor
	 *            the visitor to accept
	 */
	public void accept(FieldInfoVIsitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Registers the given field in the internal map of children and returns <code>true</code> if this field should be
	 * added to the generated HTML.
	 * 
	 * @param child
	 *            a new child to add
	 * @return <code>true</code> if the given field should be added in the generated HTML
	 */
	public boolean addChildFieldInfo(FieldInfo<?> child) {
		if (child.fParent != null) {
			child.fParent.fChildren.remove(child);
		}
		child.fParent = this;
		if (fChildren == null)
			fChildren = new ArrayList<FieldInfo<?>>();
		fChildren.add(child);
		return true;
	}

	/**
	 * Cleans up all internal fields
	 */
	public void clear() {
	}

	/**
	 * This method transforms the given name of the field attribute into a name of the widget attribute. This method can
	 * transform the full URL of the field attribute into a specific widget attribute name. If this method returns
	 * <code>null</code> then the attribute will not be added to the widget.
	 * 
	 * @param key
	 *            the name of the attribute to filter
	 * @return the name of the widget attribute corresponding to the specified field attribute (for example it can be an
	 *         attribute defined in the initial template).
	 */
	protected String filterWidgetAttributeName(String key) {
		if (key.startsWith(NS_XHTML)) {
			key = key.substring(NS_XHTML.length());
		} else {
			int idx = key.lastIndexOf('#');
			if (idx < 0)
				idx = key.lastIndexOf('/');
			if (idx >= 0) {
				// Filters out all parameters with URLs at the beginning
				key = null;
			}
		}
		return key;
	}

	/**
	 * Returns the attribute value corresponding to the specified name
	 * 
	 * @param attrURL
	 *            the full URL of the attribute to return
	 * @return the attribute value corresponding to the specified name
	 */
	public String getAttribute(String attrURL) {
		return fAttributes.get(attrURL);
	}

	/**
	 * Returns the specified attribute as a boolean value
	 * 
	 * @param attributeURL
	 *            the full URL of the attribute
	 * @param defaultValue
	 *            the default value of the attribute
	 * @return a boolean value of the specified attributed
	 */
	public boolean getAttributeAsBoolean(String attributeURL, boolean defaultValue) {
		String value = getAttribute(attributeURL);
		boolean result = defaultValue;
		if (value != null) {
			result = "true".equals(value) || "yes".equals(value) || "1".equals(value) || "checked".equals(value);
		}
		return result;
	}

	/**
	 * Returns all child field wrappers
	 * 
	 * @return the children (all child field wrappers)
	 */
	public List<FieldInfo<?>> getChildren() {
		return fChildren;
	}

	/**
	 * Returns an interface providing access to internationalized messages and labels
	 * 
	 * @return an interface providing access to internationalized messages and labels
	 */
	public FieldI18N getConstants() {
		return fPanel.getConstants();
	}

	/**
	 * Returns the default value of this field defined in the "adn:value" attribute of the tag
	 * 
	 * @return the default value of this field defined in the "adn:value" attribute of the tag
	 */
	public String getDefaultValue() {
		return getAttribute(X_ATTR_VALUE);
	}

	/**
	 * This is an utility method returning a label associated with this widget. This method tries to get a label key
	 * defined by the "labelKey" attribute and if it is found then it loads an internationalized message with this key.
	 * If the corresponding label was not found (or if the "labelKey" attribute is not defined) then this method tries
	 * to load the value of the "label" attribute.
	 * 
	 * @return a label associated with the current widget
	 */
	public String getLabelFromAttributes() {
		String labelKey = getAttribute(X_ATTR_LABEL_KEY);
		String label = null;
		if (labelKey != null) {
			FieldI18N constants = getConstants();
			label = constants.getString(labelKey);
		}
		if (label == null) {
			label = getAttribute(X_ATTR_LABEL);
		}
		if (label == null) {
			label = getAttribute(X_ATTR_TITLE);
		}
		return label;
	}

	/**
	 * Returns the name of the form element
	 * 
	 * @return the name of the form element
	 */
	public String getName() {
		if (fName == null) {
			fName = getAttribute(X_ATTR_NAME);
		}
		if (fName == null) {
			fName = "field" + (fNameCounter++);
		}
		return fName;
	}

	/**
	 * Returns the URI of the tag in the template corresponding to this field.
	 * 
	 * @return the URI of the tag in the template corresponding to this field
	 */
	public String getTagURI() {
		return getAttribute(TAG_URI);
	}

	/**
	 * Returns the string value extracted from the widget
	 * 
	 * @return the string value extracted from the widget
	 */
	public Object getValue() {
		return null;
	}

	/**
	 * Returns a list of values corresponding to this field. By default this method just returns a list containing the
	 * value returned by the {@link #getValue()} method (if this value is not <code>null</code>). This method can be
	 * overloaded in subclasses.
	 * 
	 * @return a list of objects corresponding to this field
	 */
	public Collection<Object> getValues() {
		List<Object> values = new ArrayList<Object>();
		Object value = getValue();
		if (value != null) {
			values.add(value);
		}
		return values;
	}

	/**
	 * @return the widget associated with this wrapper
	 */
	public W getWidget() {
		if (fWidget == null) {
			fWidget = newWidget();
			initializeWidget(fWidget);
		}
		return fWidget;
	}

	/**
	 * Returns the browser DOM element to which all attributes of the widget should be applied
	 * 
	 * @param widget
	 *            the widget for which attributes should be applied
	 * @return the browser DOM element to which all attributes of the widget should be applied
	 */
	protected com.google.gwt.user.client.Element getWidgetElement(Widget widget) {
		return widget.getElement();
	}

	/**
	 * Initialize the widget. This method is called just after creation of the widget (when it is not attached to the
	 * DOM). This method can initialize attributes of the widget using the internal attributes of this field.
	 * 
	 * @param w
	 *            the widget to initialize with attributes.
	 */
	protected void initializeWidget(W w) {
		com.google.gwt.user.client.Element widgetElement = getWidgetElement(w);
		for (Map.Entry<String, String> attr : fAttributes.entrySet()) {
			String key = attr.getKey();
			key = filterWidgetAttributeName(key);
			if (key != null) {
				String value = attr.getValue();
				DOM.setElementAttribute(widgetElement, key, value);
			}
		}
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<FieldInfo<?>> iterator() {
		return fChildren != null ? fChildren.iterator() : new ArrayList<FieldInfo<?>>().iterator();
	}

	/**
	 * Creates and returns a new widget corresponding to the given XML element
	 * 
	 * @return a newly created widget corresponding to the field
	 */
	protected abstract W newWidget();

	/**
	 * This method is used to un-register this field wrapper from the owner panel.
	 * 
	 * @see TemplateTopPanel#unregisterFieldInfo(FieldInfo)
	 */
	public void remove() {
		fPanel.unregisterFieldInfo(this);
	}

	/**
	 * Enables or disables the controlled form element. This method can be overloaded in subclasses.
	 * 
	 * @param enabled
	 *            if this flag is <code>true</code> then the controlled element will be enabled; otherwise it will be
	 *            disabled
	 */
	public void setEnabled(boolean enabled) {
		if (fChildren != null) {
			for (FieldInfo<?> info : fChildren) {
				info.setEnabled(enabled);
			}
		}
	}

	/**
	 * Sets the specified value in the controlled field
	 * 
	 * @param value
	 *            the value of the controlled field
	 * @return <code>true</code> if the specified value was successfully setted
	 */
	public boolean setValue(Object value) {
		return false;
	}

	/**
	 * Sets some values from the beginning of the given list and returns the rest of the list
	 * 
	 * @param values
	 *            the values to set
	 * @return a list of not used values
	 */
	public Collection<?> setValues(Collection<?> values) {
		Object value = null;
		List<Object> result = new ArrayList<Object>();
		if (values != null && values.size() > 0) {
			result.addAll(values);
			value = result.remove(0);
		}
		setValue(value);
		return result;
	}

	/**
	 * Makes the underlying field visible or hides it.
	 * 
	 * @param visible
	 *            if this flag is <code>true</code> then the field is shown otherwise it is hidden.
	 */
	public void setVisible(boolean visible) {
		Widget widget = getWidget();
		widget.setVisible(visible);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return fWidget != null ? fWidget.toString() : super.toString();
	}

}