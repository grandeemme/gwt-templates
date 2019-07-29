/**
 * 
 */
package org.github.gwttemplate.templates.widgets;

import java.util.EventObject;

import com.google.gwt.user.client.ui.DisclosureHandler;

/**
 * Event object containing information about {@link SimpleDisclosurePanel}
 * changes.
 */
public class SimpleDisclosureEvent extends EventObject {
    /**
     * Creates a new instance of the event object.
     * 
     * @param sender the panel from which the event is originating.
     * @see DisclosureHandler
     */
    public SimpleDisclosureEvent(SimpleDisclosurePanel sender) {
        super(sender);
    }
}
