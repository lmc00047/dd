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
//                          JScalingMFsFrame.java
//
//
//**********************************************************************

package kbctFrames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import kbct.JVariable;
import kbct.LocaleKBCT;
import kbctAux.DoubleField;
import kbctAux.MessageKBCT;

/**
 * kbctFrames.JScalingMFsFrame displays a frame which let user selects the right scaling function.
 *
 *@author     Jose Maria Alonso Moral
 *@version    3.0 , 03/08/15
 */
//------------------------------------------------------------------------------
public class JScalingMFsFrame extends JDialog {
  static final long serialVersionUID=0;	
  private GridBagLayout gridBLDisplayRange = new GridBagLayout();
  private JPanel jPanelAll = new JPanel();
  private JPanel jPanelPicture = new JPanel();
  private JPanel jPanelParameters = new JPanel();
  private JPanel jPanelValidation = new JPanel();
  private GridBagLayout gridBagLayoutValidation = new GridBagLayout();
  private JButton jButtonCancel = new JButton();
  private JButton jButtonApply = new JButton();
  private ImageIcon icon_SF;
  private JLabel jLabelSF;
  private GridBagLayout gridBLSaisie = new GridBagLayout();
  private JButton jButtonMin;
  private JButton jButtonCenter;
  private JButton jButtonMax;
  private JButton jButtonExpA;
  private JButton jButtonExpB;
  private DoubleField dfMin;
  private DoubleField dfCenter;
  private DoubleField dfMax;
  private DoubleField dfExpA;
  private DoubleField dfExpB;
  private double[] values;
  private JVariable input;
//------------------------------------------------------------------------------
  public JScalingMFsFrame( JAdvOptFrame parent, JVariable input, String title ) {
    super(parent);
    try {
      this.input= input;
      this.setTitle(LocaleKBCT.GetString(title));
      this.icon_SF= LocaleKBCT.getIconSF();
      this.jLabelSF= new JLabel(icon_SF,JLabel.CENTER);
      this.jButtonMin= new JButton();
      this.jButtonCenter= new JButton();
      this.jButtonMax= new JButton();
      this.jButtonExpA= new JButton();
      this.jButtonExpB= new JButton();
      this.dfMin= new DoubleField();
      this.dfCenter= new DoubleField();
      this.dfMax= new DoubleField();
      this.dfExpA= new DoubleField();
      this.dfExpB= new DoubleField();
      jbInit();
    } catch( Throwable t) {
       MessageKBCT.Error(this, LocaleKBCT.GetString("Error"), "Error en constructor de JModalPointsFrame: "+t);
    }
  }
//------------------------------------------------------------------------------
  private void jbInit() throws Throwable {
    jPanelParameters.setLayout(gridBLDisplayRange);
    jPanelValidation.setLayout(gridBagLayoutValidation);
    jButtonCancel.setText(LocaleKBCT.GetString("Cancel"));
    jButtonCancel.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(ActionEvent e) { jButtonCancel_actionPerformed(); } });
    jButtonApply.setText(LocaleKBCT.GetString("Apply"));
    jButtonApply.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(ActionEvent e) { jButtonApply_actionPerformed(); } });
    jPanelAll.setLayout(gridBLSaisie);
    this.values= new double[5];
    double[] inrange= this.input.GetInputInterestRange();
    this.values[0]= inrange[0];
    this.values[1]= (inrange[0]+inrange[1])/2;
    this.values[2]= inrange[1];
    this.values[3]= 1;
    this.values[4]= 1;
    this.dfMin.setValue(values[0]);
    this.dfCenter.setValue(values[1]);
    this.dfMax.setValue(values[2]);
    this.dfExpA.setValue(values[3]);
    this.dfExpB.setValue(values[4]);
    this.dfMin.setEnabled(false);
    this.dfCenter.setEnabled(false);
    this.dfMax.setEnabled(false);
    this.dfExpA.setEnabled(false);
    this.dfExpB.setEnabled(false);
    this.jButtonMin.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(ActionEvent e) { jButton_actionPerformed(e); }});
    this.jButtonCenter.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(ActionEvent e) { jButton_actionPerformed(e); }});
    this.jButtonMax.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(ActionEvent e) { jButton_actionPerformed(e); }});
    this.jButtonExpA.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(ActionEvent e) { jButton_actionPerformed(e); }});
    this.jButtonExpB.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(ActionEvent e) { jButton_actionPerformed(e); }});
    this.jButtonMin.setText(LocaleKBCT.GetString("min"));
    this.jButtonCenter.setText(LocaleKBCT.GetString("ParameterC"));
    this.jButtonMax.setText(LocaleKBCT.GetString("max"));
    this.jButtonExpA.setText(LocaleKBCT.GetString("ParExpA"));
    this.jButtonExpB.setText(LocaleKBCT.GetString("ParExpB"));
    this.getContentPane().add(jPanelAll);
    jPanelAll.add(jPanelPicture, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelAll.add(jPanelParameters, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelAll.add(jPanelValidation, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
    jPanelPicture.add(jLabelSF, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    jPanelParameters.add(jButtonMin, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    jPanelParameters.add(dfMin, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 50, 0));
    jPanelParameters.add(jButtonCenter, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    jPanelParameters.add(dfCenter, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 50, 0));
    jPanelParameters.add(jButtonMax, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    jPanelParameters.add(dfMax, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 50, 0));
    jPanelParameters.add(jButtonExpA, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    jPanelParameters.add(dfExpA, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 50, 0));
    jPanelParameters.add(jButtonExpB, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    jPanelParameters.add(dfExpB, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 50, 0));
    jPanelValidation.add(jButtonApply, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    jPanelValidation.add(jButtonCancel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));

    this.setModal(true);
    this.pack();
  }
//------------------------------------------------------------------------------
  private void jButton_actionPerformed(ActionEvent e) {
    //System.out.println("e.getActionCommand()="+e.getActionCommand());
    if (e.getActionCommand().equals(LocaleKBCT.GetString("min"))) {
        this.dfMin.setEnabled(true);
    } else if (e.getActionCommand().equals(LocaleKBCT.GetString("ParameterC"))) {
        this.dfCenter.setEnabled(true);
    } else if (e.getActionCommand().equals(LocaleKBCT.GetString("max"))) {
        this.dfMax.setEnabled(true);
    } else if (e.getActionCommand().equals(LocaleKBCT.GetString("ParExpA"))) {
        this.dfExpA.setEnabled(true);
    } else if (e.getActionCommand().equals(LocaleKBCT.GetString("ParExpB"))) {
        this.dfExpB.setEnabled(true);
    }
    this.repaint();
  }
//------------------------------------------------------------------------------
  private void jButtonCancel_actionPerformed() {
    this.dispose();
  }
//------------------------------------------------------------------------------
  private double [] ReadParameters() {
    double[] result = new double[5];
    result[0]= this.values[0];
    result[1]= this.values[1];
    result[2]= this.values[2];
    result[3]= this.values[3];
    result[4]= this.values[4];
    //System.out.println("result[0]="+result[0]);    
    //System.out.println("result[1]="+result[1]);    
    //System.out.println("result[2]="+result[2]);    
    //System.out.println("result[3]="+result[3]);    
    //System.out.println("result[4]="+result[4]);    
    try {
        double[] inrange= this.input.GetInputInterestRange();
        if (dfMin.isEnabled()) {
        	result[0]= dfMin.getValue();
            if ( (result[0] < inrange[0]) || (result[0] > inrange[1]) ) {
                throw new Throwable("T1Min_"+inrange[0]+"_"+inrange[1]);
            } else if (result[0] > result[1]) {
                throw new Throwable("T2");
            }
        }
        if (dfCenter.isEnabled()) {
        	result[1]= dfCenter.getValue();
            if ( (result[1] < inrange[0]) || (result[1] > inrange[1]) ) {
                throw new Throwable("T1Cen_"+inrange[0]+"_"+inrange[1]);
            } else if (result[0] > result[1]) {
                throw new Throwable("T2");
            } else if (result[1] > result[2]) {
                throw new Throwable("T3");
            }
        }
        if (dfMax.isEnabled()) {
        	result[2]= dfMax.getValue();
            if ( (result[2] < inrange[0]) || (result[2] > inrange[1]) ) {
                throw new Throwable("T1Max_"+inrange[0]+"_"+inrange[1]);
            } else if (result[1] > result[2]) {
                throw new Throwable("T3");
            }
        }
        if (dfExpA.isEnabled()) {
        	result[3]= dfExpA.getValue();
            if (result[3] < 0) {
                throw new Throwable("T4");
            }
        }
        if (dfExpB.isEnabled()) {
        	result[4]= dfExpB.getValue();
            if (result[4] < 0) {
                throw new Throwable("T5");
            }
        }
    } catch (Throwable t) {
      if (t.getMessage().startsWith("T1")) {
          String tmsg= t.getMessage();
          String p1= tmsg.substring(6, tmsg.lastIndexOf("_"));
          String p2= tmsg.substring(tmsg.lastIndexOf("_")+1);
          String msg="'";
          if (t.getMessage().startsWith("T1Min"))
              msg= msg+LocaleKBCT.GetString("min")+"' "+LocaleKBCT.GetString("ParameterMustBeIncludedIntoRange")+"[   "+p1+" , "+p2+"  ]";
          else if (t.getMessage().startsWith("T1Max"))
              msg= msg+LocaleKBCT.GetString("max")+"' "+LocaleKBCT.GetString("ParameterMustBeIncludedIntoRange")+"[   "+p1+" , "+p2+"  ]";
          else if (t.getMessage().startsWith("T1Cen"))
              msg= msg+LocaleKBCT.GetString("ParameterC")+"' "+LocaleKBCT.GetString("ParameterMustBeIncludedIntoRange")+"[   "+p1+" , "+p2+"  ]";
        	  
          MessageKBCT.Error(this, LocaleKBCT.GetString("IncorrectParameter"), msg);
      } else if (t.getMessage().equals("T2")) {
          MessageKBCT.Error(this,LocaleKBCT.GetString("IncorrectParameter"), LocaleKBCT.GetString("ParameterCMustBeBiggerThanMinimum"));
      } else if (t.getMessage().equals("T3")) {
          MessageKBCT.Error(this,LocaleKBCT.GetString("IncorrectParameter"), LocaleKBCT.GetString("ParameterCMustBeSmallerThanMaximum"));
      } else if (t.getMessage().equals("T4")) {
          MessageKBCT.Error(this,LocaleKBCT.GetString("IncorrectParameter"), LocaleKBCT.GetString("ParExpAMustBePositive"));
      } else if (t.getMessage().equals("T5")) {
          MessageKBCT.Error(this,LocaleKBCT.GetString("IncorrectParameter"), LocaleKBCT.GetString("ParExpBMustBePositive"));
      } else {
          t.printStackTrace();
          MessageKBCT.Error(LocaleKBCT.GetString("IncorrectParameter"), t);
      }
      //t.printStackTrace();
      result= null;
    }
    //System.out.println("result[0]="+result[0]);    
    //System.out.println("result[1]="+result[1]);    
    //System.out.println("result[2]="+result[2]);    
    //System.out.println("result[3]="+result[3]);    
    //System.out.println("result[4]="+result[4]);    
    return result;
  }
//------------------------------------------------------------------------------
  private void jButtonApply_actionPerformed() {
    try {
      double[] res= new double[5];
      res = this.ReadParameters();
      if (res != null) {
    	for (int n=0; n<res.length; n++) {
    		this.values[n]= res[n];
    	}
        this.dispose();
      }
    } catch( Exception ex ) {
        MessageKBCT.Error(this, LocaleKBCT.GetString("Error"), "Error en JModalPointsFrame en jButtonApply_actionPerformed: "+ex);
        //ex.printStackTrace();
    }
  }
//------------------------------------------------------------------------------
  public double [] Show() {
    this.setVisible(true);
    return this.values;
  }
}