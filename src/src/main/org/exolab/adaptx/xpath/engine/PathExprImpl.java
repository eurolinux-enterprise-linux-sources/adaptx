/*
 * (C) Copyright Keith Visco 1999-2002  All rights reserved.
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
 * $Id: PathExprImpl.java 4059 2004-01-13 06:11:35Z kvisco $
 */


package org.exolab.adaptx.xpath.engine;


import org.exolab.adaptx.xpath.XPathNode;
import org.exolab.adaptx.xpath.XPathResult;
import org.exolab.adaptx.xpath.XPathContext;
import org.exolab.adaptx.xpath.XPathExpression;
import org.exolab.adaptx.xpath.XPathException;
import org.exolab.adaptx.xpath.NodeSet;
import org.exolab.adaptx.xpath.expressions.LocationStep;
import org.exolab.adaptx.xpath.expressions.MatchExpression;
import org.exolab.adaptx.xpath.expressions.PathComponent;
import org.exolab.adaptx.xpath.expressions.PathExpr;


/**
 * This class represents an XPath 1.0 location path expression.
 *
 * @author <a href="mailto:kvisco@intalio.com">Keith Visco</a>
 * @version $Revision: 4059 $ $Date: 2004-01-13 01:11:35 -0500 (Tue, 13 Jan 2004) $
 */
class PathExprImpl
    extends PathExpr
{
    
    /**
     * A PathExpr can be used to wrap an ErrorExpr
     */
    private ErrorExpr error = null;

    
    /**
     * The local PathComponent of this PathExpr
     */
    private AbstractPathComponent _filter = null;
    
    
    /**
     * Indicates that this PathExpr is an "absolute"
     * expression. Note that any PathExpr with a
     * parent cannot be absolute, but just because
     * a parent doesn't exist, doesn't mean it
     * is absolute. The expression must start
     * with "/" to be absolute.
     */
    private boolean _absolute = false;

    
    /**
     * The subpath of this PathExpr
     */
    private PathExprImpl _subPath = null;

    
    /**
     * The parent PathExpr (if this PathExpr is a
     * sub-path of another PathExpr).
     */
    private PathExprImpl _parent  = null;
    

    /**
     * Creates a new PathExpr with will not match any node, 
     * and will evaluate to an empty NodeSet
     */
    public PathExprImpl()
    {
        super();
    } //-- PathExprImpl
    

    /**
     * Creates a new PathExpr wrapper for an ErrorExpr.
     *
     * This allows deferred errors until run-time.
     * @param error the ErrorExpr to wrap
     */
    public PathExprImpl( ErrorExpr error )
    {
        this.error = error;
    } //-- PathExprImpl
    

    /**
     * Creates a new PathExpr with the given PathComponent
     *
     * @param pathComponent the PathComponent to create the 
     * PathExpr for
     */
    public PathExprImpl( AbstractPathComponent pathComponent )
    {
        super();
        _filter = pathComponent;
    } //-- PathExprImpl


    /**
     * Creates a new PathExpr with the given PathComponent
     *
     * @param pathComponent the PathComponent to create the 
     * PathExpr for
     */
    public PathExprImpl( AbstractPathComponent pathComponent, PathExprImpl pathExpr )
    {
        _filter = pathComponent;
        _subPath = pathExpr;
        _subPath._parent = this;
    } //-- PathExprImpl
    
    
      //------------------/    
     //- Public Methods -/
    //------------------/    

    
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
        if ( error != null )
            error.evaluate( context );
            
        return evaluate( context, true );
        
    } //-- evaluate
    

    /**
     * Evaluates this Expr using the given context Node and ExprContext
     * @param context the current context Node
     * @param exprContext the ExprContext which has additional
     * information sometimes needed for evaluating XPath expressions
     * environment
     * @return the ExprResult
     * @exception InvalidExprException when an invalid expression is
     * encountered during evaluation
    **/
    protected NodeSet evaluate( XPathContext context, boolean start )
        throws XPathException 
    {
        XPathNode node = context.getNode();
        
        if ( _filter == null || node == null) {
            return context.newNodeSet(0);
        }
        
        XPathContext xpContext = context;
        if (start) {
            if ((isAbsolute()) && (node.getNodeType() != XPathNode.ROOT)) {
                node = node.getRootNode();
                xpContext = context.newContext(node);
            }
        }
        
        NodeSet nodes = (NodeSet) _filter.evaluate( xpContext );
        
        if ( _subPath != null && nodes.size() > 0 ) {
            
            NodeSet tmpNodes = xpContext.newNodeSet();
            
            XPathContext tmpContext = xpContext.newContext(nodes, 0);
            for ( int j = 0 ; j < nodes.size() ; j++ ) {
                tmpContext.setPosition(j);
                tmpNodes.add(_subPath.evaluate( tmpContext, false ));
            }
            nodes = tmpNodes;
        }
        
        if ( nodes == null )
            nodes = context.newNodeSet(0);
            
        return nodes;
    } //-- evaluate(context, ExprContext, boolean)
    
    /**
     * Returns the PathComponent for the this PathExpr, either
     * a FilterExpr or LocationStep.
     *
     * @return the PathComponent for this PathExpr
     */
    public PathComponent getPathComponent() {
        return _filter;
    }
    
    /**
     * Sets the "filter" or PathComponent for this 
     * PathExpression (for backward compatibility)
     *
     * @param pathComponent
     */
    public void setFilter( AbstractPathComponent pathComponent )
    {
        this._filter = pathComponent;
    } //-- setFilter
    
    /**
     * Sets the PathComponent for this PathExpression 
     * the PathComponent may be a LocationStep or 
     * FilterExpr.
     *
     * @param pathComponent
     */
    public void setPathComponent( AbstractPathComponent pathComponent )
    {
        this._filter = pathComponent;
    } //-- setPathComponent
    
    

    public PathExpr getSubPath() {
        return _subPath;
    } //-- setSubPath

    public void setSubPath( PathExprImpl pathExpr )
    {
        //-- clear existing subpath
        if ( _subPath != null )
            _subPath._parent = null;
       
       //-- set new subpath
        _subPath = pathExpr;
        if ( _subPath != null )
            _subPath._parent = this;
            
    } //-- setSubPath
    

    /**
     * Returns the String representation of this PathExpr
     * @return the String representation of this PathExpr
    **/
    public String toString()
    {
        return toString( null );
    } //-- toString


    private String toString( StringBuffer sb )
    {
        if ( sb == null )
            sb = new StringBuffer();
        if ( _filter != null ) {
            if (_parent != null) {
                sb.append('/');
            }            
            /*
            switch ( _filter.getAncestryOp( )) {
                case AbstractPathComponent.PARENT_OP:
                    break;
                default:
                    break;                
            }
            */
            
            sb.append( _filter.toString() );
        }
        if ( _subPath != null )
            _subPath.toString( sb );
        return sb.toString();
    } //-- toString
    
    
    
    /**
     * Determines the priority of a PatternExpr as follows:
     * <PRE>
     *  From the 19991116 XSLT 1.0 Recommendation:
     *  + If the pattern has the form of a QName preceded by a
     *    ChildOrAttributeAxisSpecifier or has the form 
     *    processing-instruction(Literal) then the priority is 0.
     *  + If the pattern has the form NCName:* preceded by a 
     *    ChildOrAttributeAxisSpecifier, then the priority is -0.25
     *  + Otherwise if the pattern consists of just a NodeTest 
     *    preceded by a ChildOrAttributeAxisSpecifier then the
     *    priority is -0.5
     *  + Otherwise the priority is 0.5
     * </PRE>
     * @return the priority for this PatternExpr
    **/
    public double getDefaultPriority()
    {
        if ( _subPath != null )
            return 0.5;
        return _filter.getDefaultPriority();
    } //-- getDefaultPriority
    
    
    /**
     * Determines if this PathExpr Represents an absolute PathExpr or not.
     * An absolute path expression is any PathExpr that starts with "/" 
     * indicating that it's context node for evaluation is the root node 
     * and not the current context node.
     *
     * @return true if the PathExpr is an absolute PathExpr otherwise false.
     */
    public boolean isAbsolute()
    {
        return _absolute;
    } //-- isAbsolute
    

    /**
     * Determines if the given node is matched by this MatchExpr with
     * respect to the given context.
     * @param node the node to determine a match for
     * @param context the XPathContext
     * @return true if the given node is matched by this MatchExpr
     * @exception XPathException when an error occurs during
     * evaluation
    **/
    public boolean matches( XPathNode node, XPathContext context )
        throws XPathException 
    {
        
        if ( error != null ) error.evaluate( context );
        
        if ( node == null || _filter == null )
            return false;
        
        XPathContext tmpContext = context.newContext(node);
        
        //-- for performance reasons I've duplicated some
        //-- code here. If we don't have any subpaths,
        //-- there is no reason to create NodeSets and
        //-- go through the while loop below. This results
        //-- in significant performance gains. :-)
        
        if ( _subPath == null ) {
            
            boolean useAncestors = hasDescendantsAxis(_filter);
            
            if (_parent != null) {
                
                XPathNode parent = node.getParentNode();
                NodeSet nodes = tmpContext.getNodeSet();
                while ( parent != null ) {
                    nodes.clear();
                    nodes.add(parent);
                    if (_filter.matches( node, tmpContext )) {
                        return true;
                    }
                    parent = (useAncestors) ? parent.getParentNode() : null;
                }
            }
            else {
                //-- This case checks to see if there is
                //-- any ancestor-or-self context which
                //-- would allow the given node to be matched.
                XPathNode ancestor = node;
                NodeSet nodes = tmpContext.getNodeSet();
                while ( ancestor != null )  {
                    nodes.clear();
                    nodes.add(ancestor);
                    if ( _filter.matches( node, tmpContext ) ) 
                        return true;
                    ancestor = ancestor.getParentNode();
                }
            }
            return false;
        }
        
        //-- we have subpaths...
        
        PathExprImpl px = this;
        while ( px._subPath != null ) 
            px = px._subPath;
        
        //-- After much testing, on my stylesheets,
        //-- i've realized just how expensive creating
        //-- and destroying objects are...so I am
        //-- using a small number for the NodeSets, because
        //-- on the typical case, a NodeSet never needs
        //-- to be larger than 1-3 nodes. I was using
        //-- a value of 7 which took about 33% longer
        //-- on average to process this method. I might
        //-- rewrite it to use no NodeSets if possible.
        NodeSet current   = context.newNodeSet( 2 );
        NodeSet ancestors = context.newNodeSet( 2 );
        NodeSet tmp       = null;
        
        current.add(node);

        while ( px != null ) {
            
            AbstractPathComponent filter = px._filter; 
            
            boolean useAncestors = hasDescendantsAxis(filter);
            
            for ( int i = 0 ; i < current.size() ; i++ ) {
                XPathNode tnode = current.item(i);

                if (px._parent != null) {
                    XPathNode parent = tnode.getParentNode();
                    while ( parent != null ) {
                        NodeSet nodes = tmpContext.getNodeSet();
                        nodes.clear();
                        nodes.add(parent);
                        if ( filter.matches( tnode, tmpContext ) )
                            ancestors.add( parent );
                                
                        parent = (useAncestors) ? parent.getParentNode() : null;
                    }
                }
                else {
                    if ( px == this ) {
                        //-- This case checks to see if there is
                        //-- any ancestor-or-self context which
                        //-- would allow the given node to be matched.
                        XPathNode ancestor = tnode;
                        NodeSet nodes = tmpContext.getNodeSet();
                        while ( ancestor != null )  {
                            nodes.clear();
                            nodes.add(ancestor);
                            if ( filter.matches( tnode, tmpContext ) ) 
                                return true;
                            ancestor = ancestor.getParentNode();
                        }
                    }
                    else {
                        return false; //-- error in expression
                    }
                } //-- if (parent != null)
            } //-- for
            
            if ( ancestors.size() == 0 ) 
                return false;
                
            //-- swap
            current.clear();
            tmp       = current;
            current   = ancestors;
            ancestors = tmp;
            
            if ( px == this )
                break;
            else
                px = px._parent;
        } //-- while

        if ( isAbsolute() ) {
            //-- make sure owning document exists in
            //-- set of context nodes
            XPathNode doc = node.getRootNode();
            return current.contains( doc );
        }
        return ( current.size() > 0 );
    } //-- matches
    
    /**
     * Sets whether or not this PathExpr is an absolute
     * path expression
     *
     * @param absolute a flag indicating whether or not
     * the this PathExpr is absolute
     */
    protected final void setAbsolute(boolean absolute) {
        if ((_parent != null) && (absolute)) {
            String err = "A PathExpr with a parent expression cannot be absolute.";
            throw new IllegalStateException(err);
        }
        _absolute = absolute;
    } //-- setAbsolute
    
    //-------------------//
    //- Private Methods -//
    //-------------------//
    
    /**
     * Returns true if the given filter is a LocationStep
     * with a from-descendants or from-descendants-or-self axis
     * identifier.
     *
     * @return true if the given filter has an axis of
     * from-descendants or from-descendants-or-self.
    **/
    private boolean hasDescendantsAxis(AbstractPathComponent filter) {
        if (filter.getExprType() == XPathExpression.STEP) {
            LocationStepImpl step = (LocationStepImpl)filter;
            int axis = step.getAxisIdentifier();
            return ((axis == LocationStep.DESCENDANTS_AXIS) ||
                    (axis == LocationStep.DESCENDANTS_OR_SELF_AXIS));
        }
        return false;
    } //-- hasDescendantAxis
    
} // -- PathExpr
