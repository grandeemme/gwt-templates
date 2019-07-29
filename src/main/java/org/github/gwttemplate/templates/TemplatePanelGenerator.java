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
 * This is an utility class used to initialize {@link FieldInfoContext}
 * instances. It builds the HTML layout for the panel from individual parts of
 * the HTML, adds new widgets which should be inserted in this HTML template and
 * when all operations are done it binds the widgets to the template.
 * 
 * 
 */
class TemplatePanelGenerator {

    /**
     * Instances of this type are used to collect all child fields and add them
     * to the managed field using the
     * {@link FieldInfo#addChildFieldInfo(FieldInfo)} method.
     * 
     * 
     */
    private static class Placeholder {

        /**
         * The field info object corresponding to this object
         */
        protected FieldInfo<?> fField;

        /**
         * The unique identifier of the field wrapper (and the corresponding GWT
         * widget) in the HTML template. This identifier is associated with a
         * span element and it is used to replace these span elements by widgets
         * when the building of the HTML template is finished.
         */
        protected String fId;

        /**
         * The parent placeholder. This parent manages the widget corresponding
         * to this placeholder.
         */
        protected Placeholder fParent;

        /**
         * This constructor adds this placeholder in the parent.
         * 
         * @param parent the parent placeholder
         * @param field the field
         */
        public Placeholder(Placeholder parent, FieldInfo<?> field) {
            fId = "__tmps_" + fIdCounter++;
            fParent = parent;
            fField = field;
            if (fParent != null) {
                fParent.addChildField(fId, fField);
            }
        }

        /**
         * Adds the given child field to the internal managed field using the
         * {@link FieldInfo#addChildFieldInfo(FieldInfo)} method.
         * 
         * @param id the temporary unique identifier of the field
         * @param field the field to add to the internal managed field
         */
        public void addChildField(String id, FieldInfo<?> field) {
            if (fField != null)
                fField.addChildFieldInfo(field);
        }

        /**
         * If this object deals with HTML templates then this method appends a
         * new peace of the HTML to the internal template; otherwise it does
         * nothing.
         * 
         * @param str the text to add to the template
         */
        public void addText(String str) {
        }

        /**
         * Finalizes the building of this template and returns a new
         * {@link TemplatePanel} instance. This method creates a new
         * {@link TemplatePanel} instance and initializes it with the merged
         * HTML template and with the corresponding {@link FieldInfo} objects.
         * The placeholders in the HTML template are replaced by the widgets.
         */
        public void done() {
            // 
        }

        /**
         * Returns the parent
         * 
         * @return the parent object
         */
        public Placeholder getParent() {
            return fParent;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return fField + "";
        }
    }

    /**
     * Instances of this type are used to merge all text blocks together in one
     * HTML where all {@link FieldInfo} objects (and the corresponding GWT
     * widgets) are replaced by placeholder <code>span</code> elements with
     * unique identifiers. When the whole HTML template is finished then this
     * class instantiates widgets and replaces the placeholder spans by the
     * corresponding widgets.
     * 
     * 
     */
    private class TemplatePlaceholder extends Placeholder {

        /**
         * The string buffer used to merge all peaces of HTML and span
         * placeholders in one template.
         */
        private StringBuffer fBuf = new StringBuffer();

        /**
         * This map is used to put in relation identifiers of child widgets and
         * the corresponding placeholders in the HTML template
         */
        private Map<String, FieldInfo<?>> fMap = new HashMap<String, FieldInfo<?>>();

        /**
         * This constructor adds this placeholder in the parent.
         * 
         * @param parent the parent placeholder
         * @param field the field
         */
        public TemplatePlaceholder(Placeholder parent, FieldInfo<?> field) {
            super(parent, field);
        }

        /**
         * This method inserts a new span placeholder with the id of the given
         * object in the HTML template and adds the widget to the internal map
         * of widgets. This map is used to replace span placeholders by real
         * widgets when the template is finished.
         * 
         * @param id the identifier of the child field
         * @param field the child field to add
         */
        @Override
        public void addChildField(String id, FieldInfo<?> field) {
            boolean add = true;
            if (fField != null) {
                add = fField.addChildFieldInfo(field);
            }
            if (add) {
                fMap.put(id, field);
                fBuf.append("<span id='" + id + "'></span>");
            }
        }

        /**
         * Appends a new peace of the HTML to the internal template
         * 
         * @param str the text to add to the template
         */
        @Override
        public void addText(String str) {
            fBuf.append(str);
        }

        /**
         * Finalizes the building of this template and returns a new
         * {@link TemplatePanel} instance. This method creates a new
         * {@link TemplatePanel} instance and initializes it with the merged
         * HTML template and with the corresponding {@link FieldInfo} objects.
         * The placeholders in the HTML template are replaced by the widgets.
         */
        @Override
        public void done() {
            if (fBuf.length() == 0)
                return;
            if (fParent == null) {
                fTopPanel.setPanelContent(fBuf.toString(), fMap);
            } else if (fField instanceof TemplatePanelContainer) {
                TemplatePanel panel = new TemplatePanel(fTopPanel);
                panel.setPanelContent(fBuf.toString(), fMap);
                ((TemplatePanelContainer) fField).setTemplatePanel(panel);
            }
        }

    }

    /**
     * The global counter used to generate unique placeholder identifiers
     */
    private static int fIdCounter;

    /**
     * The current placeholder object. It is used to create the real HTML
     * template and accumulate all widgets (with their respective wrappers)
     * which should be placed in the HTML template.
     */
    private Placeholder fPeek;

    /**
     * The topmost field info object.
     */
    private TemplatePlaceholder fTop;

    /**
     * The panel to initialize
     */
    private TemplateTopPanel fTopPanel;

    /**
     * This constructor is used to set the field panel which should be
     * initialize
     * 
     * @param panel the panel to initialize
     */
    public TemplatePanelGenerator(TemplateTopPanel panel) {
        fTopPanel = panel;
    }

    /**
     * Appends the text to the HTML template
     * 
     * @param str the text to append to the template
     */
    public void addText(String str) {
        fPeek.addText(str);
    }

    /**
     * Marks the beginning of the position where this field should be inserted
     * in the template. To define the end of this field in the template the
     * method {@link #endField()} HAVE to be called. If this field implements
     * the {@link TemplatePanelContainer} interface then all text blocks and
     * new fields will be transformed into a new widget which will be appended
     * to this field using the
     * {@link TemplatePanelContainer#setTemplatePanel(TemplatePanel)} method.
     * Otherwise all calls before the {@link #endField()} call will be ignored.
     * 
     * @param fieldFactory the field factory used to create a new field wrapper
     * @param attributes all attributes used to create the field
     */
    public void beginField(
        FieldInfoFactory fieldFactory,
        Map<String, String> attributes) {
        FieldInfo<?> field = fieldFactory != null ? fieldFactory.newFieldInfo(
            fTopPanel,
            attributes) : null;
        if (field == null || field instanceof TemplatePanelContainer) {
            fPeek = new TemplatePlaceholder(fPeek, field);
            if (fTop == null) {
                fTop = (TemplatePlaceholder) fPeek;
            }
        } else {
            fPeek = new Placeholder(fPeek, field);
        }
    }

    /**
     * Finalizes the creation of the current field and returns . It puts the
     * HTML template of field and binds all children GWT widgets to it.
     */
    public void endField() {
        fPeek.done();
        Placeholder parent = fPeek.getParent();
        fPeek = parent;
    }

}