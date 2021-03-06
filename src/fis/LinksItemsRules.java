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
//                            LinksItemsRules.java
//
//
// Author(s) : Jean-Luc LABLEE
// FISPRO Version : 2.1
// Contact : fispro@ensam.inra.fr
// Last modification date:  September 15, 2004
// File :

//**********************************************************************
package fis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import kbct.LocaleKBCT;
import kbctAux.MessageKBCT;

/**
 * fis.LinksItemsRules.
 *
 *@author     Jose Maria Alonso Moral
 *@version    3.0 , 04/06/13
 */

public class LinksItemsRules {
  private Vector<String> RulesVector ;
//------------------------------------------------------------------------------
  public LinksItemsRules() {}
//------------------------------------------------------------------------------
  public void LoadFile(String new_name) {
   BufferedReader br = null;
   try {
     File resultfile = new File(new_name);
     String str;
     RulesVector = new Vector<String>() ;
     br = new BufferedReader(new FileReader(resultfile)) ; //to read the file
     while ( (str = br.readLine()) != null) {
       try{ RulesVector.add( (new String(str.substring(3)))); }
       catch (Throwable t) {RulesVector.add(new String(LocaleKBCT.GetString("NoActivatedRules")));}
     }
    } catch (IOException e) { MessageKBCT.Error(null, e); }
    finally{ try{ if( br!= null) br.close(); } catch (IOException e) { MessageKBCT.Error(null, e); } }
  }
//------------------------------------------------------------------------------
  public String GetRules (int example_number) {
    return (String)(this.RulesVector.elementAt(example_number)) ;
  }
}
