/**
 * 
 */
package org.github.gwttemplate.templates;

import java.util.Map;

/**
 * 
 */
public class TemplatePanelInfo extends FieldInfo<TemplatePanel> {

    /**
     * @param panel
     * @param attributes
     */
    public TemplatePanelInfo(
        TemplateTopPanel panel,
        Map<String, String> attributes) {
        super(panel, attributes);
    }

    @Override
    protected TemplatePanel newWidget() {
        String templateName = getAttribute(NS_TEMPLATES + "template");
        TemplateTopPanel panel = fPanel.getPanelBuilder().buildPanel(
            templateName);
        return panel;
    }
}
