/**
 * 
 */
package org.github.gwttemplate.templates.wrappers;

import org.github.gwttemplate.templates.FieldInfo;
import org.github.gwttemplate.templates.TemplateTopPanel;
import org.github.gwttemplate.templates.widgets.InlineHyperlink;

import java.util.Map;

import com.google.gwt.user.client.ui.Hyperlink;

/**
 * This field wrapper is used to manage hyperlinks. The following parameters are
 * used by this wrapper:
 * <dl>
 * <dt>t:inline (true/false; default - true)</dt>
 * <dd>if this flag is true then this wrapper creates an inline hyperlink
 * (wrapped in a span element; see {@link InlineHyperlink})</dd>
 * <dt>t:token</dt>
 * <dd>this is a mandatory parameter used to define the token of this hyperlink</dd>
 * <dt>t:label/t:labelKey</dt>
 * <dd>this parameter defines a label used by the hyperlink or the label key
 * used to retrieve the label from the list of internationalized messages</dd>
 * </dl>
 * 
 * 
 */
public class HyperlinkFieldInfo extends FieldInfo<Hyperlink> {

    /**
     * @param panel
     * @param attributes
     */
    public HyperlinkFieldInfo(
        TemplateTopPanel panel,
        Map<String, String> attributes) {
        super(panel, attributes);
    }

    /**
     * @see gwt.templates.FieldInfo#newWidget()
     */
    @Override
    protected Hyperlink newWidget() {
        boolean inline = getAttributeAsBoolean(NS_TEMPLATES + "inline", true);
        String label = getLabelFromAttributes();
        String token = getAttribute(NS_TEMPLATES + "token");
        return inline ? new InlineHyperlink(label, token) : new Hyperlink(
            label,
            token);
    }

}
