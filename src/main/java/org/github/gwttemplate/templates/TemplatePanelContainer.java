package org.github.gwttemplate.templates;

/**
 * This is a marker interface used to mark widgets which can contain template
 * panels.
 * 
 * 
 */
public interface TemplatePanelContainer {

    /**
     * Sets the template panel associated with the widget implementing this
     * interface.
     * 
     * @param templatePanel the template panel to set
     */
    void setTemplatePanel(TemplatePanel templatePanel);

}