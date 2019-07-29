# GWT Template

this is a porting of https://code.google.com/archive/p/gwt-templates project

# Description

This is a template system for the GWT framework. The main idea is that you have a special widget where the content is defined in a separate XML document. This XML document defines the HTML layout and it reference all internal GWT widgets. It is very easy to register a new XML tag corresponding to client-defined widgets. The main panel provides access to all managed internal widgets and their respective names.

gwt-templates was developped within the the European project Nepomuk.

How To...
How to define templates?

Templates are defined in an XML file. This XML file is used to initialize template system. Template names are used to create panels with the content defined by the template. 


How to define internationalization messages?

```
btn.apply=Apply 
btn.cancel=Cancel 
btn.clearAll=Clear all fields 
label.firstName=First Name: 
label.lastName=Last Name: 
label.header=<em>Personal Info</em>
```


# How to use the template system?

Creation of a panel with the HTML content defined by the template above:

```
// This XML should contain template declarations 
String templateXML = ...; Document templateDoc = XMLParser.parse(templateXML);

// The internationalization messages String i18n = ...;

// The registry of widgets used in templates. 
// This object can be used to register new user-defined widgets 
FieldInfoFactoryRegistry registry = new FieldInfoFactoryRegistry();

// The template panel builder. It is used as a factory for template panels TemplatePanelBuilder builder = new TemplatePanelBuilder(registry);

// Register internationalization messages used in templates 
FieldI18N constants = new FieldI18N(i18n); 
builder.setConstants(constants);

// Registers all templates defined in the given XML document 
builder.addTemplates(templateDoc);

// Now we are ready to use our template system!

// Create a new panel corresponding with the "main" template: TemplateTopPanel panel = builder.buildPanel("main"); 
// See below how to use this panel 
```

# How to access to individual fields?

```
// Sets first and last names 
panel.setFieldValue("firstName", "John"); 
panel.setFieldValue("lastName", "Smith");

// The code below is used to notify clients when submit or cancel buttons are called

// Adds a new submit listener 
panel.addSubmitListener(new ClickListener() {
	public void onClick(Widget sender) { 
		panel.setEnabled(false); 
		String firstName = (String) panel.getFieldValue("firstName"); 
		String lastName = (String) panel.getFieldValue("lastName"); 
		String str = "First Name: " + firstName + "\n" + "Last Name: " + lastName; 
		Window.alert(str); panel.setEnabled(true); 
	} 
});

// Adds a new cancel listener 
panel.addCancelListener(new ClickListener() { 
	public void onClick(Widget sender) { 
		panel.setEnabled(false); 
		Window.alert("Cancelled..."); 
		panel.setEnabled(true); 
	} 
});

```
# How to load templates and internationalization messages from the server?

The template system can be considered as activated only when both template and internationalization files are loaded. To be sure that multiple resources are loaded from the server the TemplateLoader class can be used.

The full code of the initialization of the template builder: 

```
TemplatePanelBuilder builder = new TemplatePanelBuilder(registry); 
TemplateLoader loader = new TemplateLoader(builder); 
loader.load( "TemplateExample-template.i18n", "TemplateExample-template.xml", new AsyncCallback() { 
	public void onSuccess(TemplatePanelBuilder result) { 
	// Here we can use the initialized template builder ... 
	} 
	public void onFailure(Throwable t) { 
		Window.alert("Error! " + t.getMessage()); 
	}
}); 
```