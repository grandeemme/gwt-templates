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

import java.util.HashMap;
import java.util.Map;

/**
 * This is a default map-based implementation of the {@link FieldI18N}
 * interface. It provides a simple way to register key/value pairs. The
 * {@link FieldI18NImpl#setValues(String)} method can be used to set serialized
 * key/value pairs. The given string should represent a list of key/value pairs
 * where each pair is on its own line. Keys and values should be separated by
 * the "=" or space (" ") symbols.
 * 
 * 
 */
public class FieldI18NImpl implements FieldI18N {

    /**
     * This map contains internationalized values for keys
     */
    private Map<String, String> fMap = new HashMap<String, String>();

    /**
     * 
     */
    public FieldI18NImpl() {
        super();
    }

    /**
     * This constructor is used to set serialized key/value pairs
     * 
     * @param text the serialized key/value pairs
     */
    public FieldI18NImpl(String text) {
        setValues(text);
    }

    /**
     * @see FieldI18N.templates.IFieldI18N#getString(java.lang.String)
     */
    public String getString(String name) {
        return fMap.get(name);
    }

    /**
     * @param key
     * @param value
     */
    public void setValue(String key, String value) {
        fMap.put(key, value);
    }

    /**
     * This method is used to set serialized key/value pairs. The given string
     * should represent a list of key/value pairs where each pair is on its own
     * line. Keys and values should be separated by one of the following
     * symbols: '=', '\t' (tabulation symbol) or ' ' (space).
     * 
     * @param text the serialized key/value pairs
     */
    public void setValues(String text) {
        fMap.clear();
        String[] lines = text.split("[\n\r]+");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line == null)
                continue;
            line = line.trim();
            if ("".equals(line) || line.startsWith("#"))
                continue;
            int idx = line.indexOf('=');
            if (idx < 0) {
                idx = line.indexOf(' ');
                if (idx < 0) {
                    idx = line.indexOf('\t');
                }
            }
            String key = line;
            String value = "";
            if (idx >= 0) {
                key = line.substring(0, idx);
                value = line.substring(idx + 1);
                key = key.trim();
                value = value.trim();
            }
            fMap.put(key, value);
        }
    }

}
