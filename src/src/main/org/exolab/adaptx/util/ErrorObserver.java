/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Intalio, Inc.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Intalio, Inc. Exolab is a registered
 *    trademark of Intalio, Inc.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY INTALIO, INC. AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * INTALIO, INC. OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The Original Code is XSL:P XSLT processor.
 * 
 * The Initial Developer of the Original Code is Keith Visco.
 * Portions created by Keith Visco (C) 1999-2001 Keith Visco.
 * All Rights Reserved..
 *
 * Contributor(s): 
 * Keith Visco, kvisco@ziplink.net
 *    -- original author. 
 *
 * Copyright 2001 (C) Intalio, Inc. All Rights Reserved.
 *
 * $Id: ErrorObserver.java 3829 2003-09-09 04:42:42Z kvisco $
 */

package org.exolab.adaptx.util;

/**
 * A simple interface that allows warnings and errors to
 * be reported.
 *
 * @author <a href="mailto:keith@kvisco.com">Keith Visco</a>
 * @version $Revision: 3829 $ $Date: 2003-09-09 00:42:42 -0400 (Tue, 09 Sep 2003) $
**/
public interface ErrorObserver {
    
    public static final int FATAL   = 0;
    public static final int NORMAL  = 1;
    public static final int WARNING = 2;
    

    /**
     * Signals an error with normal level
     *
     * @param exception the Exception that caused the error
    **/
    public void receiveError(Exception exception);

    /**
     * Signals an error with normal level
     *
     * @param exception the Exception that caused the error
     * @param message an option message, used when additional information
     * can be provided.
    **/
    public void receiveError(Exception exception, String message);

    /**
     * Signals an error with the given error level
     *
     * @param exception the Exception that caused the error
     * @param level the error level
    **/
    public void receiveError(Exception exception, int level);

    /**
     * Signals an error with the given error level
     *
     * @param exception the Exception that caused the error
     * @param message an option message, used when additional information
     * can be provided.
     * @param level the error level
    **/
    public void receiveError(Exception exception, String message, int level);
    
    /**
     * Signals an error with normal level
     *
     * @param message the error message
    **/
    public void receiveError(String message);
    
    /**
     * Signals an error with the given error level
     *
     * @param message the error message
     * @param level the error level
    **/
    public void receiveError(String message, int level);
    
} //-- ErrorObserver
