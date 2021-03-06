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
//                       DisplayVariableGroup.java
//
//
//**********************************************************************

package kbct;

import java.util.Vector;

/**
 * kbct.DisplayVariableGroup
 *
 *@author     Jose Maria Alonso Moral
 *@version    3.0 , 03/08/15
 */

//------------------------------------------------------------------------------
// classe de gestion des noms et de l'affichage des groupes de variables
public class DisplayVariableGroup {
  private String GroupName; // nom du groupe de variable
  private boolean Display;  // flag groupe de variable affichable
  private Vector<String> VariablesNames = new Vector<String>();  // vecteur des noms de variables
//------------------------------------------------------------------------------
// � utiliser lorsque le nom de la variable est le m�me que le nom du groupe
  public DisplayVariableGroup( String group_name ) {
    this.GroupName = group_name;
    this.Display = true;
    this.VariablesNames.add(group_name);
  }
//------------------------------------------------------------------------------
// � utiliser lorsque le nom de la variable est diff�rent du nom du groupe
  public DisplayVariableGroup( String group_name, String name ) {
    this.GroupName = group_name;
    this.Display = true;
    this.VariablesNames.add(name);
  }
//------------------------------------------------------------------------------
// retourne le nom du groupe de variable
  public String GroupName() { return this.GroupName; }
//------------------------------------------------------------------------------
// retourne le flag affichable
  public boolean Display() { return this.Display; }
//------------------------------------------------------------------------------
// modifie le flag affichable
  public void Display( boolean display ) { this.Display = display; }
//------------------------------------------------------------------------------
// ajoute une variable au groupe
  void AddVariableName( String name ) { this.VariablesNames.add(name); }
//------------------------------------------------------------------------------
// retourne le nom de la variable index
  public String VariableName( int index ) { return (String)this.VariablesNames.elementAt(index); }
//------------------------------------------------------------------------------
// retourne la taille du groupe
  public int VariablesNamesSize() { return this.VariablesNames.size(); }
}
