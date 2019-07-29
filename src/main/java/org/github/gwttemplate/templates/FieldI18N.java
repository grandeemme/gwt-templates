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
 * Instances of this type are used to provide internationalized messages and
 * labels used by templates
 * 
 * 
 */
public interface FieldI18N {

    /**
     * "null-implementation" of this interface.
     */
    FieldI18N NULL = new FieldI18N() {
        public String getString(String name) {
            return null;
        }
    };

    /**
     * Returns a localized value corresponding to the specified name
     * 
     * @param name the name of the localized parameter
     * @return a localized value corresponding to the specified name
     */
    String getString(String name);

}
