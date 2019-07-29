/**
 * 
 */
package org.github.gwttemplate.templates.wrappers;

import org.github.gwttemplate.templates.FieldInfo;
import org.github.gwttemplate.templates.TemplateTopPanel;

import java.util.Map;

import com.google.gwt.user.client.ui.FileUpload;

/**
 * This field wrapper is used to manage file fields.
 * 
 * 
 */
public class FileUploadFieldInfo extends FieldInfo<FileUpload> {

    /**
     * @param panel
     * @param attributes
     */
    public FileUploadFieldInfo(
        TemplateTopPanel panel,
        Map<String, String> attributes) {
        super(panel, attributes);
    }

    /**
     * @see gwt.templates.FieldInfo#newWidget()
     */
    @Override
    protected FileUpload newWidget() {
        FileUpload widget = new FileUpload();
        String name = getName();
        widget.setName(name);
        return widget;
    }

}
