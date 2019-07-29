package org.github.gwttemplate.templates.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Hyperlink;

/**
 * This is a simple subclass of the {@link Hyperlink} which uses a span tag as a
 * container instead of the div defined by the original hyperlink widget.
 * 
 * 
 */
public class InlineHyperlink extends Hyperlink {

    /**
     * Creates an empty hyperlink.
     */
    public InlineHyperlink() {
        super();
    }

    /**
     * Creates a hyperlink with its text and target history token specified.
     * 
     * @param text the hyperlink's text
     * @param asHTML <code>true</code> to treat the specified text as html
     * @param targetHistoryToken the history token to which it will link
     * @see #setTargetHistoryToken
     */
    public InlineHyperlink(
        String text,
        boolean asHTML,
        String targetHistoryToken) {
        super(text, asHTML, targetHistoryToken);
    }

    /**
     * Creates a hyperlink with its text and target history token specified.
     * 
     * @param text the hyperlink's text
     * @param targetHistoryToken the history token to which it will link
     */
    public InlineHyperlink(String text, String targetHistoryToken) {
        super(text, targetHistoryToken);
    }

    /**
     * This method overloads the parent method and explicitly creates and sets
     * the span element instead of the given element.
     * 
     * @see com.google.gwt.user.client.ui.UIObject#setElement(com.google.gwt.dom.client.Element)
     */
    @Override
    protected final void setElement(Element elem) {
        super.setElement(DOM.createSpan());
    }

}
