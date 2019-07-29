/**
 * 
 */
package org.github.gwttemplate.templates.wrappers;

import org.github.gwttemplate.templates.FieldInfo;
import org.github.gwttemplate.templates.TemplatePanelContainer;
import org.github.gwttemplate.templates.TemplatePanel;
import org.github.gwttemplate.templates.TemplateTopPanel;
import org.github.gwttemplate.templates.widgets.ClickableWrapper;

import java.util.Map;

import com.google.gwt.user.client.ui.SimplePanel;

/**
 * This is a wrapper widget used as a header by various panels like
 * {@link DisclosurePanelInfo}.
 * 
 * 
 */
public class HeaderPanelInfo extends FieldInfo<ClickableWrapper>
    implements
    TemplatePanelContainer {

    /**
     * Attributes with this name are used to define active elements in the
     * header leading to switching disclosure panels.
     */
    private static final String ATTR_SWITCH_STYLE = NS_TEMPLATES + "switch";

    private SimplePanel fContainer;

    /**
     * This constructor initializes internal fields of this widget wrapper.
     * 
     * @param panel the form panel owning this widget wrapper
     * @param attributes a list of configuration attributes for this widget
     */
    public HeaderPanelInfo(
        TemplateTopPanel panel,
        Map<String, String> attributes) {
        super(panel, attributes);
    }

    /**
     * @see gwt.templates.FieldInfo#clear()
     */
    @Override
    public void clear() {
    }

    /**
     * @see gwt.templates.FieldInfo#newWidget()
     */
    @Override
    protected ClickableWrapper newWidget() {
        String switcher = getAttribute(ATTR_SWITCH_STYLE);
        fContainer = new SimplePanel();
        return new ClickableWrapper(fContainer, switcher);
    }

    /**
     * @see TemplatePanelContainer.templates.ITemplatePanelContainer#setTemplatePanel(gwt.templates.TemplatePanel)
     */
    public void setTemplatePanel(TemplatePanel templatePanel) {
        fContainer.setWidget(templatePanel);
        ClickableWrapper panel = getWidget();
        panel.updateActivation();
    }

}
