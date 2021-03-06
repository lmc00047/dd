//***********************************************************************
//
//   GUAJE: Generating Understandable and Accurate Fuzzy Models in a Java Environment
//
//   Contact: guajefuzzy@gmail.com
//
//   Copyright (C) 2007 - 2015  Jose Maria Alonso Moral
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program. If not, see <http://www.gnu.org/licenses/>.
//
//***********************************************************************

//***********************************************************************
//
//
//                             FuzzyTreeTableModel.java
//
//
//**********************************************************************

package kbct;

import util.treetable.AbstractTreeTableModel;
import util.treetable.TreeTableModel;

//***********************************************************************

//
//
//                              FuzzyTreeTableModel.java
//
//
// Author(s) : Jean-Luc LABLEE
// FISPRO Version : 1.0
// Contact : fispro@ensam.inra.fr
// Last modification date : December 25, 2002
// File :

//**********************************************************************

/**
 * kbct.FuzzyTreeTableModel
 *
 *@author     Jose Maria Alonso Moral
 *@version    3.0 , 04/06/13
 */

//------------------------------------------------------------------------------
public class FuzzyTreeTableModel extends AbstractTreeTableModel {
  public FuzzyTree fuzzy_tree;
//------------------------------------------------------------------------------
  public FuzzyTreeTableModel( FuzzyTree fuzzy_tree ) {
    super(fuzzy_tree.getModel().getRoot());
    this.fuzzy_tree = fuzzy_tree;
  }
//------------------------------------------------------------------------------
  protected Object[] getChildren(Object node) {
    FuzzyNode fn = (FuzzyNode)node;
    Object [] objs = new Object[fn.getChildCount()];
    for( int i=0 ; i<objs.length ; i++ )
      objs[i] = fn.getChildAt(i);
    return objs;
  }
//------------------------------------------------------------------------------
  public int getChildCount(Object node) {
    Object[] children = getChildren(node);
    return (children == null) ? 0 : children.length;
  }
//------------------------------------------------------------------------------
  public Object getChild(Object node, int i) { return getChildren(node)[i]; }
//------------------------------------------------------------------------------
// retourne le nombre de colonnes
  public int getColumnCount() {
    int count = 1;  // la premi�re colonne est toujours affich�e
    for( int i=0 ; i<this.fuzzy_tree.VariablesGroups.size() ; i++ ) {
      // parmis tous les groupes de variable
      DisplayVariableGroup dvg = (DisplayVariableGroup)this.fuzzy_tree.VariablesGroups.elementAt(i);
      if( dvg.Display() == true ) // si le groupe de variable est affichable
        count += dvg.VariablesNamesSize();  // ajout du groupe au nombre de colonnes
    }
    return count;
  }
//------------------------------------------------------------------------------
// retourne l enom des colonnes
  public String getColumnName(int column) {
    if( column == 0 ) // la premi�re colonne n'a pas de nom
      return new String();

    int column_index = 0;
    for( int i=0 ; i<this.fuzzy_tree.VariablesGroups.size() ; i++ ) {
      // parmis tous les groupes de variables
      DisplayVariableGroup dvg = (DisplayVariableGroup)this.fuzzy_tree.VariablesGroups.elementAt(i);
      if( dvg.Display() == true ) {
        // si le groupe est affichable
        for( int j=0 ; j<dvg.VariablesNamesSize() ; j++ ) {
          column_index++;
          if( column_index == column )
            return dvg.VariableName(j); // retourne le nom de la colonne
        }
      }
    }
    return new String();
  }
//------------------------------------------------------------------------------
  public Class getColumnClass(int column) {
    if( column == 0 )   // seule la premi�re colonne est de type TreeTable
      return TreeTableModel.class;

    return null;
  }
//------------------------------------------------------------------------------
// retourne la valeur de la colonne column
  public Object getValueAt(Object node, int column) {
    int variable_index = 0;
    int column_index = 0;
    // si le noeud n'est pas une feuille et qu'on n'affiche que les feuilles
    if( (((FuzzyNode)node).isLeaf() == false) && (this.fuzzy_tree.DisplayAllNodes == false) )
      return new String();  // retourne une chaine vide
    else
      for( int i=0 ; i<this.fuzzy_tree.VariablesGroups.size() ; i++ ) {
        // parmis tous les groupes de variables
        DisplayVariableGroup dvg = (DisplayVariableGroup)this.fuzzy_tree.VariablesGroups.elementAt(i);
        for( int j=0 ; j<dvg.VariablesNamesSize() ; j++ ) {
          variable_index++;
          if( dvg.Display() == true )
            column_index++;
          if( column_index == column )
            return ((FuzzyNode)node).Variables.elementAt(variable_index); // retourne la variable
        }
      }
    return new String();
  }
}
