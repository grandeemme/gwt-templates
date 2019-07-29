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

import java.util.Map;

/**
 * Field info factories are used to create new {@link FieldInfo} objects. To extend the functionalities of templates it
 * is possible to register instances of this type in the {@link FieldInfoFactoryRegistry} using the
 * {@link FieldInfoFactoryRegistry#addFieldInfoFactory(String, IFieldInfoFactory)} method.
 * 
 * 
 */
public interface FieldInfoFactory {

	/**
	 * Returns a new field wrapper corresponding to the given template node
	 * 
	 * @param panel
	 *            the top panel where this field should be registered
	 * @param attributes
	 *            the XML element defining the widget
	 * @return a new field wrapper corresponding to the given template node
	 */
	FieldInfo<?> newFieldInfo(TemplateTopPanel panel, Map<String, String> attributes);
}