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
 * $Id: NumberFunctionCall.java 3736 2003-05-13 07:57:04Z kvisco $
 */


package org.exolab.adaptx.xpath.functions;


import org.exolab.adaptx.xpath.XPathNode;
import org.exolab.adaptx.xpath.XPathResult;
import org.exolab.adaptx.xpath.XPathContext;
import org.exolab.adaptx.xpath.NumberResult;
import org.exolab.adaptx.xpath.XPathException;
import org.exolab.adaptx.xpath.engine.Names;


/**
 * A class that represents the following Function calls:<BR /> 
 * number(); floor(); round(); ceiling();
 *
 * @author <a href="mailto:kvisco@intalio.com">Keith Visco</a>
 * @version $Revision: 3736 $ $Date: 2003-05-13 03:57:04 -0400 (Tue, 13 May 2003) $
 */
public class NumberFunctionCall
    extends FunctionCallImpl
{

    
    public static final short NUMBER   = 0;
    public static final short ROUND    = 1;
    public static final short FLOOR    = 2;
    public static final short CEILING  = 3;

    
    short functionType = NUMBER;
    
    
    /**
     * Creates a new NumberFunctionCall
    **/
    public NumberFunctionCall()
    {
        super( Names.NUMBER_FN );
    } //-- NumberFunctionCall
    

    /**
     * Creates a new NumberFunctionCall using the given type
    **/
    public NumberFunctionCall( short type )
    {
        super( NumberFunctionCall.getFunctionName( type ) );
        this.functionType = type;
    } //-- NumberFunctionCall
    

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
        if ( getParameterCount() != 1 )
            throw new XPathException( INVALID_NUMBER_PARAMS + this );
            
        double value = getParameter( 0 ).evaluate( context ).numberValue();
        
        switch ( functionType ) {
            case FLOOR:
                value = Math.floor( value );
                break;
            case ROUND: {
                if (Double.isNaN(value)) {
                    return NumberResult.NaN;
                }
                else if (value == Double.NEGATIVE_INFINITY) {
                    break;
                }
                else if (value == Double.POSITIVE_INFINITY) {
                    break;
                }
                else {
                    value = Math.round( value );
                }
                break;
            }
            case CEILING:
                value = Math.ceil( value );
                break;
            default: //-- NUMBER
                break;
        }
        return new NumberResult( value );
    } //-- evaluate
    

    private static String getFunctionName( short type )
    {
        switch ( type ) {
        case FLOOR:
            return Names.FLOOR_FN;
        case ROUND:
            return Names.ROUND_FN;
        case CEILING:
            return Names.CEILING_FN;
        default:
            return Names.NUMBER_FN;
        }
    } //-- getFunctionName
    

} //-- NumberFunctionCall
