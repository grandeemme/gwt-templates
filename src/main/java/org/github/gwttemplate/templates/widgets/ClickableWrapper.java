/**
 * 
 */
package org.github.gwttemplate.templates.widgets;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

/**
 * This wrapper is used to transform a widget into a clickable one. This class
 * uses a CSS class to activate individual HTML elements within the widget and
 * make them clickable. If the CSS class is not defined then the whole widget
 * becames clickable.
 * 
 * 
 */
public class ClickableWrapper extends Composite
    implements
    SourcesMouseEvents,
    SourcesClickEvents,
    EventListener {

    private ClickListenerCollection clickListeners;

    private Set<Element> fActiveElementSet;

    private Set<String> fActiveStyleNames = new HashSet<String>();

    private MouseListenerCollection mouseListeners;

    public ClickableWrapper(Widget widget, Set<String> classNames) {
        if (classNames != null) {
            for (String style : classNames) {
                if (style != null)
                    fActiveStyleNames.add(style);
            }
        }
        initWidget(widget);
        updateActivation();
    }

    public ClickableWrapper(Widget widget, String... classNames) {
        for (String style : classNames) {
            if (style != null)
                fActiveStyleNames.add(style);
        }
        initWidget(widget);
        updateActivation();
    }

    public void addClickListener(ClickListener listener) {
        if (clickListeners == null) {
            clickListeners = new ClickListenerCollection();
        }
        clickListeners.add(listener);
    }

    public void addMouseListener(MouseListener listener) {
        if (mouseListeners == null) {
            mouseListeners = new MouseListenerCollection();
        }
        mouseListeners.add(listener);
    }

    private boolean checkEventTarget(Event event) {
        boolean isTarget = false;
        Element target = DOM.eventGetTarget(event);
        for (Element e : fActiveElementSet) {
            isTarget = DOM.isOrHasChild(e, target);
            if (isTarget)
                break;
        }
        return isTarget;
    }

    @Override
    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEDOWN:
            case Event.ONMOUSEUP:
            case Event.ONMOUSEMOVE:
            case Event.ONMOUSEOVER:
            case Event.ONMOUSEOUT:
                if (mouseListeners != null) {
                    if (checkEventTarget(event)) {
                        mouseListeners.fireMouseEvent(this, event);
                    }
                }
                break;
            case Event.ONCLICK:
                if (clickListeners != null) {
                    if (checkEventTarget(event)) {
                        clickListeners.fireClick(this);
                    }
                }
                break;
        }
    }

    public void removeClickListener(ClickListener listener) {
        if (clickListeners != null)
            clickListeners.remove(listener);
    }

    public void removeMouseListener(MouseListener listener) {
        if (mouseListeners != null)
            mouseListeners.remove(listener);
    }

    public void updateActivation() {
        int mask = Event.ONCLICK | Event.MOUSEEVENTS;
        Element element = getElement();
        if (fActiveElementSet == null) {
            fActiveElementSet = new HashSet<Element>();
        } else {
            for (Element e : fActiveElementSet) {
                DOM.sinkEvents(e, DOM.getEventsSunk(e) & (~mask));
            }
        }
        fActiveElementSet.clear();
        StyledElementSelector selector = new StyledElementSelector(
            fActiveStyleNames);
        selector.loadSelectedChildren(element, fActiveElementSet);
        DOM.sinkEvents(element, mask | DOM.getEventsSunk(element));
        if (fActiveElementSet.isEmpty()) {
            fActiveElementSet.add(element);
        }
        for (Element e : fActiveElementSet) {
            DOM.sinkEvents(e, mask | DOM.getEventsSunk(e));
        }
    }

}
