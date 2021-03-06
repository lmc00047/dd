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
//                              JInputHFP.java
//
//
//**********************************************************************
package kbct;

import fis.jnifis;

/**
 * <p align="left">
 * kbct.JInputHFP generate a particular case of JVariable
 *
 *@author     Jose Maria Alonso Moral
 *@version    3.0 , 03/08/15
 */

//------------------------------------------------------------------------------
public class JInputHFP extends JVariable {
//------------------------------------------------------------------------------
JInputHFP( long ptr ) throws Throwable { super((int)ptr,""); }
//------------------------------------------------------------------------------
public int GetNumberOfMF() throws Throwable { return jnifis.GetHFPInputNumberOfMF(this.ptr); }
//------------------------------------------------------------------------------
public void SetNumberOfMF( int number ) throws Throwable { jnifis.SetHFPInputNumberOfMF(this.ptr, number); }
//------------------------------------------------------------------------------
public void SetRangeHFP( double[] range ) throws Throwable { jnifis.SetInputRange(this.ptr, range[0], range[1]); }
//------------------------------------------------------------------------------
public void SetRangeHFP( double range_min, double range_max ) throws Throwable { jnifis.SetInputRange(this.ptr, range_min, range_max); }
}
