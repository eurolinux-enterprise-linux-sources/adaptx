/*
 * (C) Copyright Keith Visco 1999  All rights reserved.
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
 * $Id: IdRefFunctionCall.java 3736 2003-05-13 07:57:04Z kvisco $
 */


package org.exolab.adaptx.xpath.functions;


import java.util.StringTokenizer;
import org.exolab.adaptx.xpath.XPathNode;
import org.exolab.adaptx.xpath.XPathResult;
import org.exolab.adaptx.xpath.XPathContext;
import org.exolab.adaptx.xpath.XPathExpression;
import org.exolab.adaptx.xpath.StringResult;
import org.exolab.adaptx.xpath.XPathException;
import org.exolab.adaptx.xpath.NodeSet;


/**
 * This class represents the XPath idref() function call
 *
 * @author <a href="mailto:kvisco@intalio.com">Keith Visco</a>
 * @version $Revision: 3736 $
 */
public class IdRefFunctionCall
    extends FunctionCallImpl
{
    
    
    private static final String IDREF = "idref";

      //----------------/
     //- Constructors -/
    //----------------/
    
    /**
     * Creates an IdFunctionCall
    **/
    public IdRefFunctionCall()
    {
        super( IDREF );
    } //-- IdRefFunctionCall
    
    
    /**
     * Evaluates the expression and returns the XPath result.
     *
     * @param context The XPathContext to use during evaluation.
     * @return The XPathResult (not null).
     * @exception XPathException if an error occured while 
     * evaluating this expression.
    **/
    public XPathResult evaluate( XPathContext context )
        throws XPathException 
    {
        NodeSet nodeSet = context.newNodeSet();
        
        XPathNode tmpNode;
        if ( getParameterCount() == 1 ) {
            StringBuffer ids = new StringBuffer();
            XPathExpression expr = getParameter( 0 );
            XPathResult result = expr.evaluate( context );
            if ( result.getResultType() == XPathResult.NODE_SET ) {
                NodeSet nodes = (NodeSet) result;
                for ( int i = 0 ; i < nodes.size() ; i++ ) {
                    ids.append( ' ' );
                    ids.append( nodes.item( i ).getStringValue() );
                }
            } else {
                throw new XPathException
                    ( "invalid parameter for idref() function ->" + expr );
            }
            StringTokenizer st = new StringTokenizer( ids.toString() );
            XPathNode node = context.getNode();
            while ( st.hasMoreTokens() ) {
                tmpNode = context.getElementById( node, st.nextToken() );
                if ( tmpNode != null )
                    nodeSet.add( tmpNode );
            }
        }
        return nodeSet;
    } //-- evaluate

    
} //-- IdFunctionCall
