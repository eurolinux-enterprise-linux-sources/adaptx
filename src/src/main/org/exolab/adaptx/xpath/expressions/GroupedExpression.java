/*
 * (C) Copyright Keith Visco 1999-2003  All rights reserved.
 *
 * The contents of this file are released under an Open Source 
 * Definition (OSD) compliant license; you may not use this file 
 * execpt in compliance with the license. Please see license.txt, 
 * distributed with this file. You may also obtain a copy of the
 * license at http://www.kvisco.com/xslp/license.txt
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
 * $Id: GroupedExpression.java 3949 2003-10-07 05:02:41Z kvisco $
 */


package org.exolab.adaptx.xpath.expressions;


import org.exolab.adaptx.xpath.XPathExpression;
import org.exolab.adaptx.xpath.XPathException;


/**
 * Represents an XPath 1.0 primary expression
 * that is grouped inside parenthesis: '(' Expr ')'
 *
 * <PRE>
 * from XPath 1.0 Recommendation:
 * [15] PrimaryExpr ::= VariableReference
 *                      | '(' Expr ')'
 *                      | Literal
 *                      | Number
 *                      | FunctionCall
 * </PRE>
 *
 * @author <a href="mailto:kvisco@intalio.com">Keith Visco</a>
 * @version $Revision: 3949 $ $Date: 2003-10-07 01:02:41 -0400 (Tue, 07 Oct 2003) $
 */
public interface GroupedExpression extends XPathExpression
{

    /**
     * Returns the underlying expression of this grouping
     * 
     * @param returns the underlying XPath expresion 
     */
    public XPathExpression getExpression();

} //-- GroupedExpression
