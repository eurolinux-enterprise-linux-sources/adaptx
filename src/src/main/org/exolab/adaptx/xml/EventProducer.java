/*
 * (C) Copyright Keith Visco 1998, 1999  All rights reserved.
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
 * $Id: EventProducer.java 3633 2003-03-01 07:38:44Z kvisco $
 */

package org.exolab.adaptx.xml;

import org.xml.sax.*;

/**
 * A interface which abstracts anything which can produce SAX events.
 * This allows any EventProducer to be used with the Unmarshaller
 * @author <a href="mailto:kvisco@exoffice.com">Keith Visco</a>
 * @version $Revision: 3633 $ $Date: 2003-03-01 02:38:44 -0500 (Sat, 01 Mar 2003) $
**/
public interface EventProducer {
    
    /**
     * Sets the DocumentHandler to send SAX events to
    **/
    public void setDocumentHandler(DocumentHandler handler);
    
    /**
     * Signals to start producing events.
    **/
    public void start() throws org.xml.sax.SAXException;
    
} //-- EventProducer