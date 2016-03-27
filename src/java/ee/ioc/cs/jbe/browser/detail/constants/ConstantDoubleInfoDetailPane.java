/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package ee.ioc.cs.jbe.browser.detail.constants;

import org.gjt.jclasslib.structures.InvalidByteCodeException;
import org.gjt.jclasslib.structures.constants.ConstantDoubleInfo;
import org.gjt.jclasslib.util.ExtendedJLabel;

import ee.ioc.cs.jbe.browser.BrowserServices;


import javax.swing.tree.TreePath;

/**
    Detail pane showing a <tt>CONSTANT_Double</tt> constant pool entry.
 
    @author <a href="mailto:jclasslib@ej-technologies.com">Ingo Kegel</a>
    @version $Revision: 1.4 $ $Date: 2006/09/25 16:00:58 $
*/
public class ConstantDoubleInfoDetailPane extends AbstractConstantInfoDetailPane {

    // Visual components
    
    private ExtendedJLabel lblHighBytes;
    private ExtendedJLabel lblLowBytes;
    private ExtendedJLabel lblDouble;
    private ExtendedJLabel lblComment;
    
    /**
        Constructor.
        @param services the associated browser services.
     */
    public ConstantDoubleInfoDetailPane(BrowserServices services) {
        super(services);
    }
    
    protected void setupLabels() {
        
        addDetailPaneEntry(normalLabel("High bytes:"),
                           lblHighBytes = highlightLabel());

        addDetailPaneEntry(normalLabel("Low bytes:"),
                           lblLowBytes = highlightLabel());
        
        addDetailPaneEntry(normalLabel("Double:"),
                           lblDouble = highlightLabel(),
                           lblComment = highlightLabel());

    }

    public void show(TreePath treePath) {
        
        int constantPoolIndex = constantPoolIndex(treePath);

        try {
            ConstantDoubleInfo entry = (ConstantDoubleInfo)services.getClassFile().getConstantPoolEntry(constantPoolIndex, ConstantDoubleInfo.class);
            lblHighBytes.setText(entry.getFormattedHighBytes());
            lblLowBytes.setText(entry.getFormattedLowBytes());
            lblDouble.setText(entry.getDouble());
        } catch (InvalidByteCodeException ex) {
            lblComment.setText(MESSAGE_INVALID_CONSTANT_POOL_ENTRY);
        }
        
        super.show(treePath);
    }
    
}

