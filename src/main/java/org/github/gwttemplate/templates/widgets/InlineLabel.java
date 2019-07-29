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
package org.github.gwttemplate.templates.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWordWrap;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.MouseWheelListenerCollection;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.SourcesMouseWheelEvents;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a simple copy of the {@link com.google.gwt.user.client.ui.Label}
 * class. <br />
 * A widget that contains arbitrary text, <i>not</i> interpreted as HTML.
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.gwt-Label { }</li>
 * </ul>
 * <p>
 * <h3>Example</h3>
 * {@example com.google.gwt.examples.HTMLExample}
 * </p>
 */
public class InlineLabel extends Widget
    implements
    SourcesClickEvents,
    SourcesMouseEvents,
    SourcesMouseWheelEvents,
    HasHorizontalAlignment,
    HasText,
    HasWordWrap {

    private ClickListenerCollection clickListeners;

    private HorizontalAlignmentConstant horzAlign;

    private MouseListenerCollection mouseListeners;

    private MouseWheelListenerCollection mouseWheelListeners;

    /**
     * Creates an empty label.
     */
    public InlineLabel() {
        setElement(DOM.createSpan());
        setStyleName("gwt-Label");
    }

    /**
     * This constructor is used to let the HTML constructors avoid work.
     * 
     * @param element element
     */
    InlineLabel(Element element) {
        setElement(element);
    }

    /**
     * Creates a label with the specified text.
     * 
     * @param text the new label's text
     */
    public InlineLabel(String text) {
        this();
        setText(text);
    }

    /**
     * Creates a label with the specified text.
     * 
     * @param text the new label's text
     * @param wordWrap <code>false</code> to disable word wrapping
     */
    public InlineLabel(String text, boolean wordWrap) {
        this(text);
        setWordWrap(wordWrap);
    }

    /**
     * @see com.google.gwt.user.client.ui.SourcesClickEvents#addClickListener(com.google.gwt.user.client.ui.ClickListener)
     */
    public void addClickListener(ClickListener listener) {
        if (clickListeners == null) {
            clickListeners = new ClickListenerCollection();
            sinkEvents(Event.ONCLICK);
        }
        clickListeners.add(listener);
    }

    /**
     * @see com.google.gwt.user.client.ui.SourcesMouseEvents#addMouseListener(com.google.gwt.user.client.ui.MouseListener)
     */
    public void addMouseListener(MouseListener listener) {
        if (mouseListeners == null) {
            mouseListeners = new MouseListenerCollection();
            sinkEvents(Event.MOUSEEVENTS);
        }
        mouseListeners.add(listener);
    }

    /**
     * @see com.google.gwt.user.client.ui.SourcesMouseWheelEvents#addMouseWheelListener(com.google.gwt.user.client.ui.MouseWheelListener)
     */
    public void addMouseWheelListener(MouseWheelListener listener) {
        if (mouseWheelListeners == null) {
            mouseWheelListeners = new MouseWheelListenerCollection();
            sinkEvents(Event.ONMOUSEWHEEL);
        }
        mouseWheelListeners.add(listener);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasHorizontalAlignment#getHorizontalAlignment()
     */
    public HorizontalAlignmentConstant getHorizontalAlignment() {
        return horzAlign;
    }

    /**
     * @see com.google.gwt.user.client.ui.HasText#getText()
     */
    public String getText() {
        return DOM.getInnerText(getElement());
    }

    /**
     * @see com.google.gwt.user.client.ui.HasWordWrap#getWordWrap()
     */
    public boolean getWordWrap() {
        return !DOM.getStyleAttribute(getElement(), "whiteSpace").equals(
            "nowrap");
    }

    /**
     * @see com.google.gwt.user.client.ui.Widget#onBrowserEvent(com.google.gwt.user.client.Event)
     */
    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
            case Event.ONCLICK:
                if (clickListeners != null) {
                    clickListeners.fireClick(this);
                }
                break;

            case Event.ONMOUSEDOWN:
            case Event.ONMOUSEUP:
            case Event.ONMOUSEMOVE:
            case Event.ONMOUSEOVER:
            case Event.ONMOUSEOUT:
                if (mouseListeners != null) {
                    mouseListeners.fireMouseEvent(this, event);
                }
                break;

            case Event.ONMOUSEWHEEL:
                if (mouseWheelListeners != null) {
                    mouseWheelListeners.fireMouseWheelEvent(this, event);
                }
                break;
        }
    }

    /**
     * @see com.google.gwt.user.client.ui.SourcesClickEvents#removeClickListener(com.google.gwt.user.client.ui.ClickListener)
     */
    public void removeClickListener(ClickListener listener) {
        if (clickListeners != null) {
            clickListeners.remove(listener);
        }
    }

    /**
     * @see com.google.gwt.user.client.ui.SourcesMouseEvents#removeMouseListener(com.google.gwt.user.client.ui.MouseListener)
     */
    public void removeMouseListener(MouseListener listener) {
        if (mouseListeners != null) {
            mouseListeners.remove(listener);
        }
    }

    /**
     * @see com.google.gwt.user.client.ui.SourcesMouseWheelEvents#removeMouseWheelListener(com.google.gwt.user.client.ui.MouseWheelListener)
     */
    public void removeMouseWheelListener(MouseWheelListener listener) {
        if (mouseWheelListeners != null) {
            mouseWheelListeners.remove(listener);
        }
    }

    /**
     * @see com.google.gwt.user.client.ui.HasHorizontalAlignment#setHorizontalAlignment(com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant)
     */
    public void setHorizontalAlignment(HorizontalAlignmentConstant align) {
        horzAlign = align;
        DOM.setStyleAttribute(getElement(), "textAlign", align
            .getTextAlignString());
    }

    /**
     * @see com.google.gwt.user.client.ui.HasText#setText(java.lang.String)
     */
    public void setText(String text) {
        DOM.setInnerText(getElement(), text);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasWordWrap#setWordWrap(boolean)
     */
    public void setWordWrap(boolean wrap) {
        DOM.setStyleAttribute(getElement(), "whiteSpace", wrap
            ? "normal"
            : "nowrap");
    }
}
