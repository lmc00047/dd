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
//                            JOpenFileChooser.java
//
//
//**********************************************************************

package kbctAux;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import kbct.LocaleKBCT;
import kbctFrames.JChildFrame;

/**
 * kbctAux.JOpenFileChooser is a class used to define types of files in open dialog.
 *
 *@author     Jose Maria Alonso Moral
 *@version    3.0 , 03/08/15
 */

public class JOpenFileChooser extends JFileChooser {
  static final long serialVersionUID=0;	
  private int Option = JFileChooser.CANCEL_OPTION;
  private JDialog Dialog;
  private Frame frame;
  private Vector<JComponent> AddedComponents = new Vector<JComponent>();
  private ImageIcon icon_kbct= LocaleKBCT.getIconGUAJE();
//------------------------------------------------------------------------------
  public JOpenFileChooser( String currentDirectoryPath ) {
    super(currentDirectoryPath);
  }
//------------------------------------------------------------------------------
  public int showOpenDialog(Component parent) {
    this.frame = new Frame();
    this.frame.setIconImage(icon_kbct.getImage());
    this.Dialog = new JDialog(frame);
    this.Dialog.setTitle(LocaleKBCT.GetString("Open"));
    this.Dialog.getContentPane().setLayout(new GridBagLayout());
    this.Dialog.getContentPane().add(this, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
           ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    for( int i=0 ; i<this.AddedComponents.size() ; i++ )
      this.Dialog.getContentPane().add((Component)this.AddedComponents.elementAt(i), new GridBagConstraints(0, i+1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

    this.Dialog.setModal(true);
    this.Dialog.pack();
    this.Dialog.setLocation(JChildFrame.ChildPosition(parent, this.Dialog.getSize()));
    this.Dialog.setVisible(true);
    return this.Option;
  }
//------------------------------------------------------------------------------
  public void approveSelection() {
    if( this.getSelectedFile() != null ) {
      this.Option = JFileChooser.APPROVE_OPTION;
      this.Dialog.dispose();
    }
  }
//------------------------------------------------------------------------------
  public void cancelSelection() { this.Dialog.dispose(); }
//------------------------------------------------------------------------------
  public void AddComponent( JComponent comp ) { this.AddedComponents.add(comp); }
}