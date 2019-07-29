/**
 * 
 */
package org.github.gwttemplate.templates;

/**
 * This is a visitor interface used to visit individual fields in templates.
 * 
 * 
 */
public interface FieldInfoVIsitor {

    /**
     * Visits the specified field.
     * 
     * @param info the wrapper for a field
     */
    void visit(FieldInfo<?> info);

}
