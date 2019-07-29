package org.github.gwttemplate.templates.widgets;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * Selectors of this type are used to collect all HTML elements containing a
 * specific CSS class name.
 * 
 * 
 */
public class StyledElementSelector extends HtmlElementSelector {

    /**
     * Returns a set of all style classes associated with the given element
     * 
     * @param element the element for which a list of styles should be
     *        returned
     * @return a set of all style classes associated with the given element
     */
    public static Set<String> getStyles(Element element) {
        Set<String> styles = new HashSet<String>();
        String str = DOM.getElementProperty(element, "className");
        if (str != null) {
            str = str.trim();
            String[] array = str.split("[ ]+");
            for (String style : array) {
                styles.add(style);
            }
        }
        return styles;
    }

    private Set<String> fStyles;

    public StyledElementSelector(Set<String> styles) {
        fStyles = styles;
    }

    @Override
    protected boolean checkElement(Element element) {
        Set<String> styles = getStyles(element);
        boolean result = false;
        for (String style : fStyles) {
            result = styles.contains(style);
            if (result)
                break;
        }
        return result;
    }

}