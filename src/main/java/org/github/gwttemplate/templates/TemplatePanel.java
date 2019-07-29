package org.github.gwttemplate.templates;

import java.util.Map;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * The internal HTML panel used as a container for widgets.
 * 
 * 
 */
public class TemplatePanel extends Composite {

    /**
     * The internal panel containing the HTML layout and the embedded widgets.
     * 
     * 
     */
    private static class InternalPanel extends ComplexPanel {

        /**
         * Creates the internal div element.
         */
        public InternalPanel() {
            setElement(DOM.createDiv());
        }

        /**
         * Sets the internal HTML and replace all widget placeholders by the
         * corresponding widgets. The given HTML can contain HTML elements
         * marked by unique identifiers; If for such elements there are
         * corresponding widgets in the given map then these elements will be
         * replaced by widgets.
         * 
         * @param html the HTML to set; this HTML contains placeholders marked
         *        by unique identifiers; these placeholders will be replaced by
         *        the corresponding widgets
         * @param fieldInfoMap this map contains element identifiers and the
         *        corresponding to them {@link FieldInfo} objects; these field
         *        info objects are used to instantiate widgets which are
         *        inserted in the content instead placeholders
         */
        public void setPanelContent(
            String html,
            Map<String, FieldInfo<?>> fieldInfoMap) {
            if (html != null && !"".equals(html)) {
                if (fieldInfoMap == null || fieldInfoMap.isEmpty()) {
                    DOM.setInnerHTML(getElement(), html);
                } else {
                    DOM.setInnerHTML(getElement(), html);
                    if (fieldInfoMap != null && !fieldInfoMap.isEmpty()) {
                        setWidgets(getElement(), fieldInfoMap);
                    }
                }
            }
        }

        /**
         * Replace DOM elements by the corresponding widgets defined in the
         * specified field info map;
         * 
         * @param elem the top element
         * @param fieldInfoMap this map contains element ids and the
         *        corresponding {@link FieldInfo} objects used to get widgets to
         *        insert in the panel
         */
        private void setWidgets(
            Element elem,
            Map<String, FieldInfo<?>> fieldInfoMap) {
            Element child = DOM.getFirstChild(elem);
            while (child != null) {
                String id = DOM.getElementProperty(child, "id");
                Element next = DOM.getNextSibling(child);
                FieldInfo<?> field = id != null ? fieldInfoMap.get(id) : null;
                if (field != null) {
                    // The child element is a marker element; it will be
                    // replaced by the corresponding widget
                    Widget widget = field.getWidget();
                    // Detach new child.
                    widget.removeFromParent();
                    // Logical attach.
                    getChildren().add(widget);
                    // Physical attach.
                    DOM.insertBefore(elem, widget.getElement(), child);
                    // Adopt.
                    adopt(widget);
                    // Removes the marker element
                    DOM.removeChild(elem, child);
                } else {
                    setWidgets(child, fieldInfoMap);
                }
                child = next;
            }
        }
    }

    /**
     * The field context containing mapping between field names and
     * corresponding fields
     */
    private TemplateTopPanel fTemplateTopPanel;

    /**
     * This constructor sets the parent top panel owning all fields
     * 
     * @param parent the top panel
     */
    protected TemplatePanel(TemplateTopPanel parent) {
        fTemplateTopPanel = parent;
        InternalPanel panel = new InternalPanel();
        initWidget(panel);
    }

    /**
     * The default constructor initializing the internal HTML and replacing all
     * widget placeholders by the corresponding widgets. The given HTML can
     * contain HTML elements marked by unique identifiers; If for such elements
     * there are corresponding widgets in the given map then these elements will
     * be replaced by widgets.
     * 
     * @param panel the panel which owns this template panel
     * @param html the HTML to set; this HTML contains placeholders marked by
     *        unique identifiers; these placeholders will be replaced by the
     *        corresponding widgets
     * @param fieldInfoMap this map contains element identifiers and the
     *        corresponding to them {@link FieldInfo} objects; these field info
     *        objects are used to instantiate widgets which are inserted in the
     *        content instead placeholders
     */
    protected TemplatePanel(
        TemplateTopPanel parent,
        String html,
        Map<String, FieldInfo<?>> fieldInfoMap) {
        this(parent);
        setPanelContent(html, fieldInfoMap);
    }

    /**
     * Returns the field panel managing the mapping between field names and the
     * corresponding widget wrappers
     * 
     * @return the panel owning the fields.
     */
    public TemplateTopPanel getTemplateTopPanel() {
        return fTemplateTopPanel;
    }

    /**
     * Sets the internal HTML and replaces all widget placeholders by the
     * corresponding widgets. The given HTML should contain HTML elements marked
     * by unique identifiers; If for such elements there are corresponding
     * widgets in the given map then these elements will be replaced by widgets.
     * 
     * @param html the HTML to set; this HTML contains placeholders marked by
     *        unique identifiers; these placeholders will be replaced by the
     *        corresponding widgets
     * @param fieldInfoMap this map contains element identifiers and the
     *        corresponding to them {@link FieldInfo} objects; these field info
     *        objects are used to instantiate widgets which are inserted in the
     *        content instead placeholders
     */
    void setPanelContent(String html, Map<String, FieldInfo<?>> fieldInfoMap) {
        InternalPanel panel = (InternalPanel) getWidget();
        panel.setPanelContent(html, fieldInfoMap);
    }

}