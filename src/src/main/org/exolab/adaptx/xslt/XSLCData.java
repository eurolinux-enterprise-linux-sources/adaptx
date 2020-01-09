/*
 * (C) Copyright Keith Visco 1999  All rights reserved.
 *
 * The contents of this file are released under an Open Source 
 * Definition (OSD) compliant license; you may not use this file 
 * execpt in compliance with the license. Please see license.txt, 
 * distributed with this file. You may also obtain a copy of the
 * license at http://www.clc-marketing.com/xslp/license.txt
 *
 * The program is provided "as is" without any warranty express or
 * implied, including the warranty of non-infringement and the implied
 * warranties of merchantibility and fitness for a particular purpose.
 * The Copyright owner will not be liable for any damages suffered by
 * you as a result of using the Program. In no event will the Copyright
 * owner be liable for any special, indirect or consequential damages or
 * lost profits even if the Copyright owner has been advised of the
 * possibility of their occurrence.
 *
 * $Id: XSLCData.java 3633 2003-03-01 07:38:44Z kvisco $
 */
 
package org.exolab.adaptx.xslt;


/**
 * Represents the an xsl:cdata in the XSLStylesheet. This
 * is currently a proprietary element
 * @author <a href="mailto:kvisco@ziplink.net">Keith Visco</a>
 * @version $Revision: 3633 $ $Date: 2003-03-01 02:38:44 -0500 (Sat, 01 Mar 2003) $
**/
public class XSLCData extends XSLText {
    
      //----------------/
     //- Constructors -/
    //----------------/
    
    /**
     * Creates a new XSLCData 
    **/
    public XSLCData() {
        super(XSLObject.CDATA);
    } //-- XSLCData
    
    /**
     * Creates a new XSLCData with the given data
     * @param data the value of this CDATA section
    **/
    public XSLCData(String data) {
        super(XSLObject.CDATA);
        if (data != null) appendText(data);
    } //-- XSLCData
    
    
} //-- XSLCData
