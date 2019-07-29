/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.github.gwttemplate.templates.widgets;

import java.util.ArrayList;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosureEvent;
import com.google.gwt.user.client.ui.DisclosureHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasAnimation;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * <p>
 * A widget that consists of a header and a content panel that discloses the
 * content when a user clicks on the header.
 * </p>
 * <h3>CSS Style Rules</h3>
 * <ul>
 * <li>.gwt-DisclosurePanel { the panel's primary style }</li>
 * <li>.gwt-DisclosurePanel-open { dependent style set when panel is open }</li>
 * <li>.gwt-DisclosurePanel-closed { dependent style set when panel is closed }</li>
 * <li>.header { the header section }</li>
 * <li>.content { the content section }</li>
 * </ul>
 * <p>
 * <img class='gallery' src='DisclosurePanel.png'/>
 * </p>
 * <p>
 * The header and content sections can be easily selected using css with a child
 * selector:<br/>
 * .gwt-DisclosurePanel-open .header { ... }
 * </p>
 */
public final class SimpleDisclosurePanel extends Composite
    implements
    HasAnimation,
    SourcesClickEvents {

    /**
     * An {@link Animation} used to open the content.
     */
    private static class ContentAnimation extends Animation {
        /**
         * The {@link SimpleDisclosurePanel} being affected.
         */
        private SimpleDisclosurePanel curPanel;

        /**
         * Whether the item is being opened or closed.
         */
        private boolean opening;

        @Override
        protected void onComplete() {
            if (!opening) {
                curPanel.contentWrapper.setVisible(false);
            }
            DOM.setStyleAttribute(
                curPanel.contentWrapper.getElement(),
                "height",
                "auto");
            curPanel = null;
        }

        @Override
        protected void onStart() {
            super.onStart();
            if (opening) {
                curPanel.contentWrapper.setVisible(true);
            }
        }

        @Override
        protected void onUpdate(double progress) {
            int scrollHeight = DOM.getElementPropertyInt(
                curPanel.contentWrapper.getElement(),
                "scrollHeight");
            int height = (int) (progress * scrollHeight);
            if (!opening) {
                height = scrollHeight - height;
            }
            height = Math.max(height, 1);
            DOM.setStyleAttribute(
                curPanel.contentWrapper.getElement(),
                "height",
                height + "px");
            DOM.setStyleAttribute(
                curPanel.contentWrapper.getElement(),
                "width",
                "auto");
        }

        /**
         * Open or close the content.
         * 
         * @param panel the panel to open or close
         * @param animate true to animate, false to open instantly
         */
        public void setOpen(SimpleDisclosurePanel panel, boolean animate) {
            // Immediately complete previous open
            cancel();

            // Open the new item
            if (animate) {
                curPanel = panel;
                opening = panel.isOpen;
                run(ANIMATION_DURATION);
            } else {
                panel.contentWrapper.setVisible(panel.isOpen);
            }
        }
    }

    /**
     * This class is used to wrap click listeners associated with this panel.
     * 
     * 
     */
    private class SimpleDisclosureClickListener
        implements
        SimpleDisclosureHandler {

        /**
         * This click listener is notified every time when this panel changes
         * its state.
         */
        private ClickListener fListener;

        /**
         * @param listener
         */
        public SimpleDisclosureClickListener(ClickListener listener) {
            fListener = listener;
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof SimpleDisclosureClickListener))
                return false;
            SimpleDisclosureClickListener l = (SimpleDisclosureClickListener) obj;
            return l.fListener == fListener;
        }

        /**
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return fListener.hashCode();
        }

        /**
         * @see gwt.templates.widgets.SimpleDisclosureHandler#onClose(gwt.templates.widgets.SimpleDisclosureEvent)
         */
        public void onClose(SimpleDisclosureEvent event) {
            fListener.onClick(SimpleDisclosurePanel.this);
        }

        /**
         * @see gwt.templates.widgets.SimpleDisclosureHandler#onOpen(gwt.templates.widgets.SimpleDisclosureEvent)
         */
        public void onOpen(SimpleDisclosureEvent event) {
            fListener.onClick(SimpleDisclosurePanel.this);
        }

    }

    /**
     * The duration of the animation.
     */
    private static final int ANIMATION_DURATION = 350;

    /**
     * The {@link Animation} used to open and close the content.
     */
    private static ContentAnimation contentAnimation;

    private static final String STYLENAME_CONTENT = "content";

    // Stylename constants.
    private static final String STYLENAME_DEFAULT = "gwt-DisclosurePanel";

    private static final String STYLENAME_HEADER = "header";

    private static final String STYLENAME_SUFFIX_CLOSED = "closed";

    private static final String STYLENAME_SUFFIX_OPEN = "open";

    /**
     * the content widget, this can be null.
     */
    private Widget content;

    /**
     * The wrapper around the content widget.
     */
    private final SimplePanel contentWrapper = new SimplePanel();

    /**
     * The header of the panel.
     */
    private Widget fHeader;

    /**
     * The container of the header.
     */
    private SimplePanel fHeaderContainer = new SimplePanel();

    /**
     * This listener is used by headers to switch the visibility of the main
     * content in this panel.
     */
    private ClickListener fListener = new ClickListener() {
        public void onClick(Widget sender) {
            setOpen(!isOpen);
        }
    };

    /**
     * null until #{@link #addEventHandler(DisclosureHandler)} is called (lazily
     * initialized).
     */
    private ArrayList<SimpleDisclosureHandler> handlers;

    private boolean isAnimationEnabled = false;

    private boolean isOpen = false;

    /**
     * top level widget. The first child will be a reference to {@link #header}.
     * The second child will either not exist or be a non-null to reference to
     * {@link #content}.
     */
    private final FlowPanel mainPanel = new FlowPanel();

    /**
     * Creates a DisclosurePanel that will be initially closed using a widget as
     * the header.
     * 
     * @param header the widget to be used as a header
     */
    public SimpleDisclosurePanel() {
        this(null, null, false);
    }

    /**
     * Creates a DisclosurePanel using a widget as the header and an initial
     * open/close state.
     * 
     * @param header the widget to be used as a header
     * @param isOpen the initial open/close state of the content panel
     */
    public SimpleDisclosurePanel(Widget header, boolean isOpen) {
        this(header, null, isOpen);
    }

    /**
     * Creates a DisclosurePanel using a widget as the header and an initial
     * open/close state.
     * 
     * @param header the widget to be used as a header
     * @param switchElementClass the CSS class active/clickable element(s) in
     *        the header
     * @param isOpen the initial open/close state of the content panel
     */
    public SimpleDisclosurePanel(
        Widget header,
        String switchElementClass,
        boolean isOpen) {
        fHeaderContainer.setStyleName("header");
        init(isOpen);
        setHeader(header, switchElementClass);
    }

    public void add(Widget w) {
        if (this.getContent() == null) {
            setContent(w);
        } else {
            throw new IllegalStateException(
                "A DisclosurePanel can only contain two Widgets.");
        }
    }

    /**
     * @see com.google.gwt.user.client.ui.SourcesClickEvents#addClickListener(com.google.gwt.user.client.ui.ClickListener)
     */
    public void addClickListener(ClickListener listener) {
        SimpleDisclosureClickListener handler = new SimpleDisclosureClickListener(
            listener);
        addEventHandler(handler);
    }

    /**
     * Attaches an event handler to the panel to receive {@link DisclosureEvent}
     * notification.
     * 
     * @param handler the handler to be added (should not be null)
     */
    public void addEventHandler(SimpleDisclosureHandler handler) {
        if (handlers == null) {
            handlers = new ArrayList<SimpleDisclosureHandler>();
        }
        handlers.add(handler);
    }

    public void clear() {
        setContent(null);
    }

    private void fireEvent() {
        if (handlers == null) {
            return;
        }
        SimpleDisclosureEvent event = new SimpleDisclosureEvent(this);
        for (SimpleDisclosureHandler handler : handlers) {
            if (isOpen) {
                handler.onOpen(event);
            } else {
                handler.onClose(event);
            }
        }
    }

    /**
     * Gets the widget that was previously set in {@link #setContent(Widget)}.
     * 
     * @return the panel's current content widget
     */
    public Widget getContent() {
        return content;
    }

    /**
     * Gets the widget that is currently being used as a header.
     * 
     * @return the widget currently being used as a header
     */
    public Widget getHeader() {
        return fHeader;
    }

    /**
     * Gets a {@link HasText} instance to provide access to the headers's text,
     * if the header widget does provide such access.
     * 
     * @return a reference to the header widget if it implements {@link HasText}
     *         , <code>null</code> otherwise
     */
    public HasText getHeaderTextAccessor() {
        return (fHeader instanceof HasText) ? (HasText) fHeader : null;
    }

    private void init(boolean isOpen) {
        initWidget(mainPanel);
        mainPanel.add(fHeaderContainer);
        mainPanel.add(contentWrapper);
        DOM.setStyleAttribute(contentWrapper.getElement(), "padding", "0px");
        DOM
            .setStyleAttribute(
                contentWrapper.getElement(),
                "overflow",
                "hidden");
        this.isOpen = isOpen;
        setStyleName(STYLENAME_DEFAULT);
        setContentDisplay(false);
    }

    public boolean isAnimationEnabled() {
        return isAnimationEnabled;
    }

    /**
     * Determines whether the panel is open.
     * 
     * @return <code>true</code> if panel is in open state
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * <b>Affected Elements:</b>
     * <ul>
     * <li>-header = the clickable header.</li>
     * </ul>
     * 
     * @see UIObject#onEnsureDebugId(String)
     */
    @Override
    protected void onEnsureDebugId(String baseID) {
        super.onEnsureDebugId(baseID);
        fHeaderContainer.ensureDebugId(baseID + "-header");
    }

    public boolean remove(Widget w) {
        if (w == getContent()) {
            setContent(null);
            return true;
        }
        return false;
    }

    /**
     * @see com.google.gwt.user.client.ui.SourcesClickEvents#removeClickListener(com.google.gwt.user.client.ui.ClickListener)
     */
    public void removeClickListener(ClickListener listener) {
        SimpleDisclosureClickListener handler = new SimpleDisclosureClickListener(
            listener);
        removeEventHandler(handler);
    }

    /**
     * Removes an event handler from the panel.
     * 
     * @param handler the handler to be removed
     */
    public void removeEventHandler(SimpleDisclosureHandler handler) {
        if (handlers == null) {
            return;
        }
        handlers.remove(handler);
    }

    public void setAnimationEnabled(boolean enable) {
        isAnimationEnabled = enable;
    }

    /**
     * Sets the content widget which can be opened and closed by this panel. If
     * there is a preexisting content widget, it will be detached.
     * 
     * @param content the widget to be used as the content panel
     */
    public void setContent(Widget content) {
        final Widget currentContent = this.content;

        // Remove existing content widget.
        if (currentContent != null) {
            contentWrapper.setWidget(null);
            currentContent.removeStyleName(STYLENAME_CONTENT);
        }

        // Add new content widget if != null.
        this.content = content;
        if (content != null) {
            contentWrapper.setWidget(content);
            content.addStyleName(STYLENAME_CONTENT);
            setContentDisplay(false);
        }
    }

    private void setContentDisplay(boolean animate) {
        if (isOpen) {
            removeStyleDependentName(STYLENAME_SUFFIX_CLOSED);
            addStyleDependentName(STYLENAME_SUFFIX_OPEN);
        } else {
            removeStyleDependentName(STYLENAME_SUFFIX_OPEN);
            addStyleDependentName(STYLENAME_SUFFIX_CLOSED);
        }

        if (content != null) {
            if (contentAnimation == null) {
                contentAnimation = new ContentAnimation();
            }
            contentAnimation.setOpen(this, animate && isAnimationEnabled);
        }
    }

    /**
     * Sets the widget used as the header for the panel.
     * 
     * @param headerWidget the widget to be used as the header
     */
    public void setHeader(Widget headerWidget) {
        setHeader(headerWidget, null);
    }

    /**
     * Sets the widget used as the header for the panel.
     * 
     * @param headerWidget the widget to be used as the header
     */
    public void setHeader(Widget headerWidget, String switchElementClass) {
        SourcesClickEvents oldHeader = (SourcesClickEvents) fHeaderContainer
            .getWidget();
        if (oldHeader != null) {
            oldHeader.removeClickListener(fListener);
        }
        if (headerWidget == null)
            return;
        SourcesClickEvents source = null;
        if ((headerWidget instanceof SourcesClickEvents)
            && (switchElementClass == null)) {
            source = (SourcesClickEvents) headerWidget;
        }
        if (source == null) {
            source = new ClickableWrapper(headerWidget, switchElementClass);
        }
        fHeaderContainer.setWidget((Widget) source);
        source.addClickListener(fListener);
    }

    /**
     * Changes the visible state of this <code>DisclosurePanel</code>.
     * 
     * @param isOpen <code>true</code> to open the panel, <code>false</code> to
     *        close
     */
    public void setOpen(boolean isOpen) {
        if (this.isOpen != isOpen) {
            this.isOpen = isOpen;
            setContentDisplay(true);
            fireEvent();
        }
    }

    /**
     * Transforms the given widget into a clickable one by activating elements
     * with the specified CSS class names.
     * 
     * @param widget a widget to wrap
     * @param switchElementClass the name of the CSS class of elements to
     *        activate
     * @return a clickable widget
     */
    protected Widget wrapWidget(Widget widget, String switchElementClass) {
        return new ClickableWrapper(widget, switchElementClass);
    }
}
