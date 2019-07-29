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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesKeyboardEvents;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * Instances of this type are used as common contexts for multiple fields. This
 * context gives access to individual fields, their values and corresponding
 * widgets by their names. This context can manage multiple fields with the same
 * name. The context does not define how wrapped widgets are used and how they
 * are attached to the DOM of the browser documents.
 * 
 * 
 */
public class TemplateTopPanel extends TemplatePanel
    implements
    Iterable<FieldInfo<?>> {

    /**
     * Container of cancel listeners
     */
    private ClickListenerCollection fCancelListeners;

    /**
     * This map contains field names (keys) and lists of corresponding
     * {@link FieldInfo} objects (values).
     */
    Map<String, List<FieldInfo<?>>> fFieldInfoMap = new HashMap<String, List<FieldInfo<?>>>();

    /**
     * The panel builder used as a factory for this panel.
     */
    private TemplatePanelBuilder fPanelBuilder;

    /**
     * Container of submit listeners
     */
    private ClickListenerCollection fSubmitListeners;

    /**
     * The default constructor.
     * 
     * @param panelBuilder the factory of this panel
     */
    public TemplateTopPanel(TemplatePanelBuilder panelBuilder) {
        super(null);
        fPanelBuilder = panelBuilder;
    }

    /**
     * Accept the field visitor; This method just calls
     * {@link FieldInfoVIsitor#visit(FieldInfo)} method for all internal
     * fields.
     * 
     * @param visitor the visitor
     */
    public void accept(FieldInfoVIsitor visitor) {
        for (List<FieldInfo<?>> fields : fFieldInfoMap.values()) {
            for (FieldInfo<?> field : fields) {
                visitor.visit(field);
            }
        }
    }

    /**
     * Adds a new click listener for cancel buttons
     * 
     * @param listener the listener to add
     */
    public void addCancelListener(ClickListener listener) {
        if (fCancelListeners == null) {
            fCancelListeners = new ClickListenerCollection();
        }
        fCancelListeners.add(listener);
    }

    /**
     * Adds the given click listener to all fields with the specified name
     * implementing the {@link SourcesClickEvents} interface
     * 
     * @param name the name of the field where the click listener should be
     *        added
     * @param listener the listener to add
     */
    public void addClickListener(String name, ClickListener listener) {
        List<FieldInfo<?>> fields = getFieldInfo(name);
        if (fields != null) {
            for (FieldInfo<?> field : fields) {
                Widget widget = field.getWidget();
                if (widget instanceof SourcesClickEvents) {
                    ((SourcesClickEvents) widget).addClickListener(listener);
                }
            }
        }
    }

    /**
     * Adds the given widget as a children of a field with the specified name.
     * This method adds the widget only if the target field implements the
     * {@link HasWidgets} interface. If there are multiple fields with the
     * specified name then this method adds the widget to the first field
     * implementing the {@link HasWidgets} interface.
     * 
     * @param fieldName the name of the panel (widget)
     * @param widget the widget to set
     */
    public void addFieldWidget(String fieldName, Widget widget) {
        List<Widget> widgets = new ArrayList<Widget>();
        widgets.add(widget);
        setFieldWidgets(fieldName, widgets, false);
    }

    /**
     * Adds a new click listener for submit buttons
     * 
     * @param listener the listener to add
     */
    public void addSubmitListener(ClickListener listener) {
        if (fSubmitListeners == null) {
            fSubmitListeners = new ClickListenerCollection();
            KeyboardListener keyboardListener = null;
            for (List<FieldInfo<?>> fields : fFieldInfoMap.values()) {
                for (FieldInfo<?> field : fields) {
                    Widget widget = field.getWidget();
                    if (widget instanceof SourcesKeyboardEvents
                        && !(widget instanceof TextArea)) {
                        if (keyboardListener == null) {
                            keyboardListener = new KeyboardListenerAdapter() {
                                @Override
                                public void onKeyPress(
                                    Widget sender,
                                    char keyCode,
                                    int modifiers) {
                                    if (keyCode == KEY_ENTER) {
                                        onSubmit(sender);
                                    }
                                }
                            };
                        }
                        ((SourcesKeyboardEvents) widget)
                            .addKeyboardListener(keyboardListener);
                    }
                }
            }
        }
        fSubmitListeners.add(listener);
    }

    /**
     * Cleans up all internal fields
     */
    public void clear() {
        for (List<FieldInfo<?>> fields : fFieldInfoMap.values()) {
            for (FieldInfo<?> field : fields) {
                field.clear();
            }
        }
    }

    /**
     * Cleans up all fields with the specified names. If the given flag
     * <code>children</code> is <code>true</code> then all child fields are
     * cleaned up as well.
     * 
     * @param name the name of the field to clean up
     * @param children if this flag is <code>true</code> then all children of
     *        the field with the specified name will be cleaned up as well.
     */
    public void clear(String name, boolean children) {
        List<FieldInfo<?>> fields = getFieldInfo(name);
        if (fields != null) {
            if (children) {
                FieldInfoVIsitor visitor = new FieldInfoVisitorImpl() {
                    public void visit(FieldInfo<?> info) {
                        info.clear();
                        visitChildren(info);
                    }
                };
                for (FieldInfo<?> field : fields) {
                    field.accept(visitor);
                }
            } else {
                for (FieldInfo<?> field : fields) {
                    field.clear();
                }
            }
        }
    }

    /**
     * Returns an interface providing access to internationalized messages and
     * labels
     * 
     * @return an interface providing access to internationalized messages and
     *         labels
     */
    public FieldI18N getConstants() {
        return fPanelBuilder.getConstants();
    }

    /**
     * Returns an attribute value with the specified name from the field defined
     * by the <code>fieldName</code> parameter.
     * 
     * @param fieldName the name of the field containing the attribute to return
     * @param attributeURL the URL of the attribute to load
     * @return the value of the attribute with the specified URL loaded from the
     *         field defined by the field name parameter
     */
    public String getFieldAttribute(String fieldName, String attributeURL) {
        List<FieldInfo<?>> list = getFieldInfo(fieldName);
        if (list == null || list.isEmpty())
            return null;
        FieldInfo<?> info = list.get(0);
        return info != null ? info.getAttribute(attributeURL) : null;
    }

    /**
     * Returns a field with the specified name corresponding to the given
     * widget.
     * 
     * @param name the name of the field
     * @param widget the widget used to search a field
     * @return a field with the specified name containing the given widget
     */
    public FieldInfo<?> getFieldByWidget(String name, Widget widget) {
        FieldInfo<?> result = null;
        List<FieldInfo<?>> fields = getFieldInfo(name);
        if (fields != null) {
            for (FieldInfo<?> field : fields) {
                Widget w = field.getWidget();
                if (widget != w)
                    continue;
                result = field;
                break;
            }
        }
        return result;
    }

    /**
     * Returns a field corresponding to the given widget.
     * 
     * @param widget the widget used to search a field
     * @return a field containing the given widget
     */
    public FieldInfo<?> getFieldByWidget(Widget widget) {
        Set<String> names = getFieldNames();
        FieldInfo<?> result = null;
        for (String name : names) {
            result = getFieldByWidget(name, widget);
            if (result != null)
                break;
        }
        return result;
    }

    /**
     * Returns a list of field info objects ({@link FieldInfo}) corresponding to
     * the specified name
     * 
     * @param fieldName the name of the field
     * @return a list of field info objects corresponding to the specified name
     */
    public List<FieldInfo<?>> getFieldInfo(String fieldName) {
        return fFieldInfoMap.get(fieldName);
    }

    /**
     * Returns a set of all field names.
     * 
     * @return a set of all field names
     */
    public Set<String> getFieldNames() {
        return fFieldInfoMap.keySet();
    }

    /**
     * Returns the number of fields with the specified name
     * 
     * @param fieldName the name of the fields
     * @return the number of fields with the specified name
     */
    public int getFieldNumber(String fieldName) {
        List<FieldInfo<?>> list = getFieldInfo(fieldName);
        return list != null ? list.size() : 0;
    }

    /**
     * Returns the value of the first field with the specified name; if there is
     * no such a field then this method returns <code>null</code>;
     * 
     * @param fieldName the name of the field
     * @return the value of the first field with the specified name
     */
    public Object getFieldValue(String fieldName) {
        List<FieldInfo<?>> list = getFieldInfo(fieldName);
        if (list == null || list.size() == 0)
            return null;
        Object result = null;
        for (FieldInfo<?> field : list) {
            result = field.getValue();
            if (result != null)
                break;
        }
        return result;
    }

    /**
     * Returns a list of values of fields with the specified name; if there is
     * no such a field then this method returns <code>null</code>;
     * 
     * @param fieldName the name of fields
     * @return the value of all fields with the specified name
     */
    public Collection<?> getFieldValues(String fieldName) {
        List<FieldInfo<?>> fields = getFieldInfo(fieldName);
        if (fields == null || fields.size() == 0)
            return null;
        int len = fields.size();
        List<Object> result = new ArrayList<Object>(len);
        for (FieldInfo<?> field : fields) {
            Collection<Object> values = field.getValues();
            if (values != null) {
                result.addAll(values);
            }
        }
        return result;
    }

    /**
     * Returns the first form widget corresponding to the specified name (if
     * any)
     * 
     * @param name the name of the field
     * @return the first form widget corresponding to the specified name (if
     *         any)
     */
    public Widget getFieldWidget(String name) {
        List<FieldInfo<?>> fields = getFieldInfo(name);
        if (fields == null)
            return null;
        int len = fields.size();
        if (len == 0)
            return null;
        FieldInfo<?> field = fields.get(0);
        return field.getWidget();
    }

    /**
     * Returns a list of all form widgets corresponding to the specified name
     * 
     * @param name the name of the field for which the corresponding widget
     *        should be returned
     * @return a list of all widgets corresponding to the specified name
     */
    public List<Widget> getFieldWidgets(String name) {
        List<FieldInfo<?>> fields = getFieldInfo(name);
        if (fields == null)
            return null;
        int len = fields.size();
        List<Widget> result = new ArrayList<Widget>(len);
        for (FieldInfo<?> field : fields) {
            Widget w = field.getWidget();
            result.add(w);
        }
        return result;
    }

    /**
     * Returns the panel builder used as a factory for this panel
     * 
     * @return the panel builder used as a factory for this panel
     */
    public TemplatePanelBuilder getPanelBuilder() {
        return fPanelBuilder;
    }

    /**
     * Returns an iterator over all field wrappers in this panels
     * 
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<FieldInfo<?>> iterator() {
        List<FieldInfo<?>> list = new ArrayList<FieldInfo<?>>();
        for (List<FieldInfo<?>> fields : fFieldInfoMap.values()) {
            for (FieldInfo<?> field : fields) {
                list.add(field);
            }
        }
        return list.iterator();
    }

    /**
     * This method is called to notify that the operations in this form was
     * canceled.
     * 
     * @param sender the widget activating this operation; normally it is a
     *        button
     */
    public void onCancel(Widget sender) {
        if (fCancelListeners != null) {
            fCancelListeners.fireClick(sender);
        }
    }

    /**
     * This method is called to notify that the operations in this form was
     * submitted.
     * 
     * @param sender the widget activating this operation; normally it is a
     *        button
     */
    public void onSubmit(Widget sender) {
        if (fSubmitListeners != null) {
            fSubmitListeners.fireClick(sender);
        }
    }

    /**
     * Adds the given field info object to the internal map
     * 
     * @param field the field info to add
     */
    public void registerFieldInfo(FieldInfo<?> field) {
        String name = field.getName();
        List<FieldInfo<?>> fields = getFieldInfo(name);
        if (fields == null) {
            fields = new ArrayList<FieldInfo<?>>();
            fFieldInfoMap.put(name, fields);
        }
        fields.add(field);
    }

    /**
     * Removes the specified click listener from the list of cancel listeners
     * 
     * @param listener the listener to remove
     */
    public void removeCancelListener(ClickListener listener) {
        if (fCancelListeners != null) {
            fCancelListeners.remove(listener);
        }
    }

    /**
     * Removes the given click listener from all fields with the specified name
     * implementing the {@link SourcesClickEvents} interface
     * 
     * @param name the name of the field where the click listener should be
     *        removed
     * @param listener the listener to remove
     */
    public void removeClickListener(String name, ClickListener listener) {
        List<FieldInfo<?>> fields = getFieldInfo(name);
        if (fields != null) {
            for (FieldInfo<?> field : fields) {
                Widget widget = field.getWidget();
                if (widget instanceof SourcesClickEvents) {
                    ((SourcesClickEvents) widget).removeClickListener(listener);
                }
            }
        }
    }

    /**
     * Removes all child widgets from the field with the specified name.
     * 
     * @param fieldName the name of the field from which all children should be
     *        removed
     */
    public void removeFieldWidgets(String fieldName) {
        List<FieldInfo<?>> fields = getFieldInfo(fieldName);
        if (fields != null) {
            for (FieldInfo<?> field : fields) {
                Widget w = field.getWidget();
                if (w instanceof HasWidgets) {
                    HasWidgets h = (HasWidgets) w;
                    h.clear();
                }
            }
        }
    }

    /**
     * Removes the specified click listener from the list of submit listeners
     * 
     * @param listener the listener to remove
     */
    public void removeSubmitListener(ClickListener listener) {
        if (fSubmitListeners != null) {
            fSubmitListeners.remove(listener);
        }
    }

    /**
     * Resets the values of all fields.
     */
    public void reset() {
        for (List<FieldInfo<?>> fields : fFieldInfoMap.values()) {
            for (FieldInfo<?> field : fields) {
                field.clear();
            }
        }
    }

    /**
     * Enables/disables all fields in this form
     * 
     * @param enabled this flag is used to define if fields should be enabled or
     *        to disabled
     */
    public void setEnabled(boolean enabled) {
        for (List<FieldInfo<?>> fields : fFieldInfoMap.values()) {
            setEnabled(fields, enabled);
        }
    }

    /**
     * Enables/disables all fields from the specified list.
     * 
     * @param fields a list of fields to enable or to disable
     * @param enabled if this flag is <code>true</code> then fields from the
     *        given list will be enabled; otherwise they are disabled
     */
    private void setEnabled(List<FieldInfo<?>> fields, boolean enabled) {
        if (fields == null)
            return;
        for (FieldInfo<?> field : fields) {
            field.setEnabled(enabled);
        }
    }

    /**
     * Enables/disables all fields with the specified name
     * 
     * @param fieldName the name of fields to enable/disable
     * @param enabled this flag is used to define if fields should be enabled or
     *        disabled
     */
    public void setEnabled(String fieldName, boolean enabled) {
        List<FieldInfo<?>> fields = getFieldInfo(fieldName);
        setEnabled(fields, enabled);
    }

    /**
     * Explicitly focus/unfocus field widget with the specified name.
     * 
     * @param field the name of the field to focus/unfocus
     */
    public void setFieldFocus(String field, boolean focused) {
        Widget widget = getFieldWidget(field);
        if (widget != null && (widget instanceof HasFocus)) {
            ((HasFocus) widget).setFocus(focused);
        }
    }

    /**
     * Sets the specified value of the field with the given name and returns
     * <code>true</code> if the the value was successfully stored
     * 
     * @param fieldName the name of the field for which the value should be
     *        stored
     * @param value the value to set
     * @return <code>true</code> if the specified value was successfully stored
     */
    public boolean setFieldValue(String fieldName, Object value) {
        List<FieldInfo<?>> fields = getFieldInfo(fieldName);
        if (fields == null)
            return false;
        boolean result = false;
        for (FieldInfo<?> field : fields) {
            result = field.setValue(value);
            if (result)
                break;
        }
        return result;
    }

    /**
     * Sets the specified values in the fields with the specified name
     * 
     * @param fieldName the name of the field for which values should be stored
     * @param values an array of values to set
     * @return the list of not-setted values; this returned value can be used to
     *         check how much values was really accepted by fields and what was
     *         rejected/not setted.
     */
    public Collection<?> setFieldValues(String fieldName, Collection<?> values) {
        List<FieldInfo<?>> fields = getFieldInfo(fieldName);
        if (fields != null) {
            for (FieldInfo<?> field : fields) {
                values = field.setValues(values);
            }
        }
        return values;
    }

    /**
     * Shows/hides all fields with the specified name and returns
     * <code>true</code> if the specified visibility flag was successfully
     * applied.
     * 
     * @param fieldName the name of the field to show/hide
     * @param visible if this value is <code>true</code> the all fields with the
     *        specified name will be shown. Otherwise they will be hidden.
     * @return <code>true</code> if the specified visibility flag was
     *         successfully applied; it
     */
    public boolean setFieldVisible(String fieldName, boolean visible) {
        List<FieldInfo<?>> fields = getFieldInfo(fieldName);
        if (fields == null)
            return false;
        for (FieldInfo<?> field : fields) {
            field.setVisible(visible);
        }
        return true;
    }

    /**
     * Sets the given widget as a children of a field with the specified name.
     * This method adds the widget only if the target field implements the
     * {@link HasWidgets} interface. If there are multiple fields with the
     * specified name then this method adds the widget to the first field
     * implementing the {@link HasWidgets} interface.
     * 
     * @param fieldName the name of the panel (widget)
     * @param widget the widget to set
     */
    public void setFieldWidget(String fieldName, Widget widget) {
        List<Widget> widgets = new ArrayList<Widget>();
        widgets.add(widget);
        setFieldWidgets(fieldName, widgets, true);
    }

    /**
     * Adds widgets from the given list to fields with the specified name.
     * Widgets are added to fields only if the field widget implements the
     * {@link HasWidgets} interface.
     * 
     * @param fieldName the name of the fields where the given widgets should be
     *        added
     * @param widgets the list of widgets to set
     * @param clear if this flag is <code>true</code> then this method cleans up
     *        the field using the {@link HasWidgets#clear()} method before
     *        adding a new widget
     */
    public void setFieldWidgets(
        String fieldName,
        List<Widget> widgets,
        boolean clear) {
        List<FieldInfo<?>> fields = getFieldInfo(fieldName);
        if (fields != null) {
            int pos = 0;
            for (FieldInfo<?> field : fields) {
                if (pos >= widgets.size())
                    break;
                Widget w = field.getWidget();
                if (w instanceof HasWidgets) {
                    Widget widget = widgets.get(pos++);
                    HasWidgets h = (HasWidgets) w;
                    if (clear) {
                        h.clear();
                    }
                    h.add(widget);
                }
            }
        }
    }

    /**
     * Removes the given field from the context.
     * 
     * @param field the field to remove from this context.
     */
    public void unregisterFieldInfo(FieldInfo<?> field) {
        String name = field.getName();
        List<FieldInfo<?>> fields = getFieldInfo(name);
        if (fields != null) {
            fields.remove(field);
            if (fields.isEmpty()) {
                fFieldInfoMap.remove(name);
            }
        }
    }
}
