/**
 * 
 */
package org.github.gwttemplate.templates.ioutil;

import java.util.List;

import org.github.gwttemplate.commons.io.ResourceLoader;
import org.github.gwttemplate.commons.io.ResourceLoaderBarrier;
import org.github.gwttemplate.commons.io.XMLLoader;
import org.github.gwttemplate.templates.FieldI18NImpl;
import org.github.gwttemplate.templates.TemplatePanelBuilder;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;

/**
 * Template loader class is used to asynchroniously load templates and
 * internationalization messages from the server.
 * 
 * 
 */
public class TemplateLoader {

    /**
     * The template builder to initialize.
     */
    private TemplatePanelBuilder fBuilder;

    /**
     * The constructor initializing internal field.
     * 
     * @param builder the template builder which will be activated using this
     *        loader object
     */
    public TemplateLoader(TemplatePanelBuilder builder) {
        fBuilder = builder;
    }

    /**
     * Returns the internal template builder object
     * 
     * @return the internal template builder object
     */
    public TemplatePanelBuilder getBuilder() {
        return fBuilder;
    }

    /**
     * This method loads the given internationalization and template files and
     * activates the internal template builder using this information.
     * 
     * @param i18n the name of the internationalization file to load
     * @param templates the name of the XML template file to load
     * @param callback the callback used to notify when the internationalization
     *        file and templates are loaded
     */
    public void load(
        String i18n,
        String templates,
        final AsyncCallback<TemplatePanelBuilder> callback) {
        final ResourceLoaderBarrier barrier = new ResourceLoaderBarrier() {
            @Override
            protected void onFinish(
                List<Object> successList,
                List<Throwable> failureList) {
                callback.onSuccess(fBuilder);
            }
        };

        barrier.add(new ResourceLoader(i18n), new AsyncCallback<Response>() {
            public void onFailure(Throwable caught) {
                callback.onFailure(caught);
            }

            public void onSuccess(Response response) {
                String text = response.getText();
                FieldI18NImpl constants = new FieldI18NImpl(text);
                fBuilder.setConstants(constants);
            }
        });
        barrier.add(new XMLLoader(templates), new AsyncCallback<Document>() {
            public void onFailure(Throwable caught) {
                callback.onFailure(caught);
            }

            public void onSuccess(Document doc) {
                fBuilder.addTemplates(doc);
            }
        });
        barrier.load();
    }
}
