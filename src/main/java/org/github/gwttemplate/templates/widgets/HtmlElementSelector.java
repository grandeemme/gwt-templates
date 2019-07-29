package org.github.gwttemplate.templates.widgets;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * Selectors are used to collect all children of a given HTML element
 * corresponding to a specific criteria. For example instances of this type can
 * be used to collect all HTML elements with a specific CSS class name.
 * 
 * 
 */
public abstract class HtmlElementSelector {

    /**
     * Returns <code>true</code> if the given element corresponds to the
     * selection criteria and it should be added to the final element set.
     * 
     * @param element the element to check
     * @return <code>true</code> if the given element corresponds to the
     *         selection criteria and it should be added to the final element
     *         set
     */
    protected abstract boolean checkElement(Element element);

    /**
     * Loads and returns all child elements corresponding to the internal
     * selection criteria.
     * 
     * @param element the root element; children of this element corresponding
     *        to the internal criteria will be added to the resulting set
     * @returns a set of elements corresponding to the internal criteria
     */
    public Set<Element> loadSelectedChildren(Element element) {
        Set<Element> set = new HashSet<Element>();
        loadSelectedChildren(element, set);
        return set;
    }

    /**
     * Loads all child elements corresponding to the internal selection
     * criteria.
     * 
     * @param element the root element; children of this element corresponding
     *        to the internal criteria will be added to the resulting set
     * @param set the resulting set of elements where found children should be
     *        added
     */
    public void loadSelectedChildren(Element element, Set<Element> set) {
        int childCount = DOM.getChildCount(element);
        for (int i = 0; i < childCount; i++) {
            Element child = DOM.getChild(element, i);
            if (checkElement(child)) {
                set.add(child);
            } else {
                loadSelectedChildren(child, set);
            }
        }
    }
}