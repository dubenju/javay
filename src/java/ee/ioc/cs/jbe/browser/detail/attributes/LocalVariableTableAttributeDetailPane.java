/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/
package ee.ioc.cs.jbe.browser.detail.attributes;

import org.gjt.jclasslib.structures.AttributeInfo;

import ee.ioc.cs.jbe.browser.BrowserServices;



/**
 * Detail pane showing a <tt>LocalVariableTable</tt> attribute.
 *
 * @author <a href="mailto:jclasslib@ej-technologies.com">Ingo Kegel</a>, <a href="mailto:vitor.carreira@gmail.com">Vitor Carreira</a>
 * @version $Revision: 1.1 $ $Date: 2005/11/01 13:18:23 $
 */
public class LocalVariableTableAttributeDetailPane extends LocalVariableCommonAttributeDetailPane {
    /**
     * Constructor.
     *
     * @param services the associated browser services.
     */
    public LocalVariableTableAttributeDetailPane(BrowserServices services) {
        super(services);
    }

    protected AbstractAttributeTableModel createTableModel(AttributeInfo attribute) {
        return createTableModel(attribute, "descriptor");
    }
}
