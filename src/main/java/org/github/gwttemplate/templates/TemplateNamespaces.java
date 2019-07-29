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

/**
 * This interface enumerates commonly used constants (namespaces, tag names,
 * attributes...)
 * 
 * 
 */
public interface TemplateNamespaces {

    /**
     * The default namespace for widgets.
     */
    String NS_TEMPLATES = "http://www.adnotatio.org/ns/templates#";

    /**
     * The XHTML namespace is used as the default namespace for template
     * elements.
     */
    String NS_XHTML = "http://www.w3.org/1999/xhtml/";

    /**
     * An attribute with this key corresponds to the tag in the template used to
     * declare the widget.
     */
    String TAG_URI = NS_TEMPLATES + "tag";

    /**
     * The name of the attribute used by clear buttons. It defines the name of
     * the field which should be cleaned up when the button is pushed.
     */
    String X_ATTR_FOR = NS_TEMPLATES + "for";

    /**
     * This attribute defines the label used for the widget.
     */
    String X_ATTR_LABEL = NS_TEMPLATES + "label";

    /**
     * This attribute defines the key used to load internationalized label from
     * a constant list.
     */
    String X_ATTR_LABEL_KEY = NS_TEMPLATES + "labelKey";

    /**
     * This attribute defines the name of the widget in a template.
     */
    String X_ATTR_NAME = NS_TEMPLATES + "name";

    /**
     * This attribute defines the title for a widget.
     */
    String X_ATTR_TITLE = NS_TEMPLATES + "title";

    /**
     * This attribute defines a default value used by a widget.
     */
    String X_ATTR_VALUE = NS_TEMPLATES + "value";
}
