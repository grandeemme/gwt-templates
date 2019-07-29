/**
 * 
 */
package org.github.gwttemplate.templates;


/**
 * This is a default implementation of the {@link FieldInfoVIsitor} interface.
 * By default it just visits all child nodes of the field.
 * 
 * 
 */
public abstract class FieldInfoVisitorImpl implements FieldInfoVIsitor {

    /**
     * 
     */
    public FieldInfoVisitorImpl() {
        super();
    }

    /**
     * This is an utility method used to visit all children of the specified
     * field.
     * 
     * @param info the field wrapper for which all children will be visited.
     */
    protected void visitChildren(FieldInfo<?> info) {
        for (FieldInfo<?> child : info) {
            child.accept(this);
        }
    }

}
