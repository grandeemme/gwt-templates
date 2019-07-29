package org.github.gwttemplate.templates.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * <p>
 * A panel that formats its child widgets using the default HTML layout
 * behavior.
 * </p>
 * <p>
 * Implementation of this panel was copied from the Google's implementation of
 * the {@link com.google.gwt.user.client.ui.FlowPanel}.
 * </p>
 */
public class InlineFlowPanel extends ComplexPanel {

    /**
     * Creates an empty flow panel.
     */
    public InlineFlowPanel() {
        setElement(DOM.createSpan());
    }

    /**
     * Adds a new child widget to the panel.
     * 
     * @param w the widget to be added
     */
    @Override
    public void add(Widget w) {
        super.add(w, getElement());
    }

    /**
     * Inserts a widget before the specified index.
     * 
     * @param w the widget to be inserted
     * @param beforeIndex the index before which it will be inserted
     * @throws IndexOutOfBoundsException if <code>beforeIndex</code> is out of
     *         range
     */
    public void insert(Widget w, int beforeIndex) {
        super.insert(w, getElement(), beforeIndex, true);
    }
}
