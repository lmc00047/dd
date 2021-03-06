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
//                         JTreeGenerateFrame.java
//
//
//**********************************************************************

package kbctFrames;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kbct.jnikbct;
import kbct.JFIS;
import kbct.JKBCT;
import kbct.LocaleKBCT;
import kbct.MainKBCT;
import kbctAux.DoubleField;
import kbctAux.IntegerField;
import kbctAux.MessageKBCT;
import fis.JExtendedDataFile;
import fis.JPanelValidationFile;
import fis.JSemaphore;

/**
 * kbctFrames.JTreeGenerateFrame generate a frame for rule induction with
 * fuzzy decision trees algorithm
 *
 *@author     Jose Maria Alonso Moral
 *@version    3.0 , 03/08/15
 */
//------------------------------------------------------------------------------
public class JTreeGenerateFrame extends JDialog {
  static final long serialVersionUID=0;	
  private JMainFrame parent;
  private JFIS fis;
  private JExtendedDataFile data;
  private JKBCT kbct;
  // Default values
  static protected String TreeFile;
  JLabel jLabelTreeFile = new JLabel();
  JLabel jLabelMinSignificantLevel = new JLabel();
  JLabel jLabelLeafMinCard = new JLabel();
  JLabel jLabelAmbiguityThresold = new JLabel();
  JLabel jLabelTreeDepth= new JLabel();
  JLabel jLabelPerfLoss= new JLabel();
  JLabel jLabelMinEDGain = new JLabel();
  JLabel jLabelCovThresh = new JLabel();
  JTextField jTFTreeFile = new JTextField();
  DoubleField jDFMinSignificantLevel = new DoubleField();
  IntegerField jIFLeafMinCard = new IntegerField();
  DoubleField jDFAmbiguityThreshold = new DoubleField();
  DoubleField jDFPerfLoss = new DoubleField();
  DoubleField jDFMinEDGain = new DoubleField();
  DoubleField jDFCovThresh = new DoubleField();
  IntegerField jIFTreeDepth = new IntegerField();
  JCheckBox jCBPrune = new JCheckBox();
  JCheckBox jCBSplit = new JCheckBox();
  JCheckBox jCBDisplay = new JCheckBox();
  JCheckBox jCBRelgain = new JCheckBox();
  JPanel jPanelOutput = new JPanel(new GridBagLayout());
  ButtonGroup jbgOutput = new ButtonGroup();
  protected JPanelValidationFile jpValidationFile;
  private boolean setFDTparameters= false;
//------------------------------------------------------------------------------
  public JTreeGenerateFrame(JMainFrame parent, JFIS fis, JExtendedDataFile data, JKBCT kbct, String DefaultTreeFile) {
    super(parent);
    this.parent = parent;
    this.fis = fis;
    this.data = data;
    this.kbct= kbct;
    JTreeGenerateFrame.TreeFile= DefaultTreeFile;
    setFDTparameters= false;
	    //System.out.println("Induce Rules: FDT 1");
    try { jbInit();}
    catch(Throwable t) {
   	  t.printStackTrace();
      MessageKBCT.Error(this, LocaleKBCT.GetString("Error"), "Error en constructor 1 de JTreeGenerateFrame: "+t);
    }
  }
//------------------------------------------------------------------------------
  public JTreeGenerateFrame(JMainFrame parent, JFIS fis, JExtendedDataFile data, JKBCT kbct, String TreeFile, boolean automatic) {
    try {
      this.parent = parent;
      this.fis = fis;
      this.data = data;
      this.kbct= kbct;
      JTreeGenerateFrame.TreeFile= TreeFile;
      setFDTparameters= false;
	    //System.out.println("Induce Rules: FDT 2");
      JFIS [] generated_fis = this.fis.NewTreeFIS(this.data, JTreeGenerateFrame.TreeFile, this.data.ActiveFileName(), this.data.ActiveFileName() + ".result.fistree", this.data.ActiveFileName() + ".perf.fistree", MainKBCT.getConfig().GetMaxTreeDepth(), MainKBCT.getConfig().GetMinEDGain(), MainKBCT.getConfig().GetMinSignificantLevel(), Math.min(MainKBCT.getConfig().GetLeafMinCard(),this.data.GetData().length * 10 / 100), MainKBCT.getConfig().GetToleranceThreshold(), MainKBCT.getConfig().GetSelectedOutput()-1, MainKBCT.getConfig().GetPrune(), MainKBCT.getConfig().GetSplit(), MainKBCT.getConfig().GetPerfLoss(), false, MainKBCT.getConfig().GetDisplay(), MainKBCT.getConfig().GetCovThresh());
      this.jPaintRules(generated_fis, automatic);
    } catch( Throwable t ) {
    	t.printStackTrace();
        MessageKBCT.Error(this, LocaleKBCT.GetString("Error"), "Error en constructor 2 de JTreeGenerateFrame: "+t);
    }
  }
//------------------------------------------------------------------------------
  public JTreeGenerateFrame(JMainFrame parent) {
    super(parent);
    this.parent = parent;
    this.fis=null;
    JTreeGenerateFrame.TreeFile= MainKBCT.getConfig().GetTreeFile();
    setFDTparameters= true;
    //System.out.println("Induce Rules: FDT 3");
    try { jbInit();}
    catch(Throwable t) {
        t.printStackTrace();
    	MessageKBCT.Error(this, LocaleKBCT.GetString("Error"), "Error en constructor 3 de JTreeGenerateFrame: "+t);
    }
  }
//------------------------------------------------------------------------------
  private void jbInit() throws Throwable {
    this.setTitle(LocaleKBCT.GetString("TreeGenerate"));
	this.setIconImage(LocaleKBCT.getIconGUAJE().getImage());
    this.getContentPane().setLayout(new GridBagLayout());
    JPanel jPanelSaisie = new JPanel(new GridBagLayout());
    JPanel jPanelValidation = new JPanel(new GridBagLayout());
    JButton jButtonApply = new JButton(LocaleKBCT.GetString("Apply"));
    JButton jButtonCancel = new JButton(LocaleKBCT.GetString("Cancel"));
    JButton jButtonDefault = new JButton(LocaleKBCT.GetString("DefaultValues"));
    jLabelTreeFile.setText(LocaleKBCT.GetString("TreeFile") + ":");
    jLabelMinSignificantLevel.setText(LocaleKBCT.GetString("MinSignificantLevel") + ":");
    jLabelLeafMinCard.setText(LocaleKBCT.GetString("LeafMinCard") + ":");
    jLabelAmbiguityThresold.setText(LocaleKBCT.GetString("AmbiguityThreshold") + ":");
    jLabelTreeDepth.setText(LocaleKBCT.GetString("TreeDepth")+ ":");
    jLabelPerfLoss.setText(LocaleKBCT.GetString("RelPerfLoss")+ ":");
    jLabelMinEDGain.setText(LocaleKBCT.GetString("MinEDGain")+ ":");
    jLabelCovThresh.setText(LocaleKBCT.GetString("CovThresh")+ ":");
    jCBPrune.setText(LocaleKBCT.GetString("Prune"));
    jCBSplit.setText(LocaleKBCT.GetString("Split"));
    jCBDisplay.setText(LocaleKBCT.GetString("Display"));
    jCBRelgain.setText(LocaleKBCT.GetString("Relgain"));
    jPanelSaisie.add(jLabelTreeFile,    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    jPanelSaisie.add(jLabelTreeDepth,    new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    jPanelSaisie.add(jLabelMinSignificantLevel,    new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    jPanelSaisie.add(jLabelLeafMinCard,    new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    jPanelSaisie.add(jLabelAmbiguityThresold,   new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    jPanelSaisie.add(jLabelMinEDGain,   new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    jPanelSaisie.add(jLabelCovThresh,   new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    this.jTFTreeFile.setText(JTreeGenerateFrame.TreeFile);
    jPanelSaisie.add(jTFTreeFile, new GridBagConstraints(1, 0, 4, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 20, 0));
    this.jPanelOutput.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),LocaleKBCT.GetString("Outputs")));
    int lim=1;
    if (this.fis!=null)
    	lim=this.fis.NbOutputs();
    else if ( (this.parent.jef!=null) && (this.parent.jef.Temp_kbct!=null) )
    	lim=this.parent.jef.Temp_kbct.GetNbOutputs();
    
    for( int i=0 ; i<lim ; i++ ) {
    	JRadioButton jrb;
    	if (this.fis!=null)
    	    jrb= new JRadioButton(this.fis.GetOutput(i).GetName());
        else if ( (this.parent.jef!=null) && (this.parent.jef.Temp_kbct!=null) )
    	    jrb= new JRadioButton(this.parent.jef.Temp_kbct.GetOutput(i+1).GetName());
        else
    	    jrb= new JRadioButton(LocaleKBCT.GetString("Output"));
        jrb.addItemListener(new java.awt.event.ItemListener() { public void itemStateChanged(ItemEvent e) {
        try {
          JTreeGenerateFrame.this.jLabelAmbiguityThresold.setEnabled(true);
          JTreeGenerateFrame.this.jDFAmbiguityThreshold.setEnabled(true);
        } catch( Throwable t ) { 
        	t.printStackTrace();
        }
      } } );
      this.jbgOutput.add(jrb);
      if( i == MainKBCT.getConfig().GetSelectedOutput()-1 )
        jrb.setSelected(true);
      
      this.jPanelOutput.add(jrb, new GridBagConstraints(i%2==0?0:1, i/2, 1, 1, 1.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 0, 0, 0, 0), 0, 0));
    }
    jPanelSaisie.add(this.jPanelOutput, new GridBagConstraints(0, 1, 4, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));

    this.jIFTreeDepth.setValue(MainKBCT.getConfig().GetMaxTreeDepth());
    jPanelSaisie.add(jIFTreeDepth, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 30, 0));
    this.jDFMinSignificantLevel.setValue(MainKBCT.getConfig().GetMinSignificantLevel());
    jPanelSaisie.add(jDFMinSignificantLevel, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 30, 0));
    //if (data==null)
    	//System.out.println("<--- data NULL --->");
    if (data !=null) {
    	//System.out.println(" -> LMC= "+MainKBCT.getConfig().GetLeafMinCard());
    	//System.out.println(" -> data.length= "+String.valueOf(data.GetData().length));
    	//System.out.println(" -> data= "+String.valueOf(data.GetData().length*0.1));
    	//System.out.println(" -> min= "+Math.min(MainKBCT.getConfig().GetLeafMinCard(), data.GetData().length * 10 / 100));
        this.jIFLeafMinCard.setValue(Math.min(MainKBCT.getConfig().GetLeafMinCard(), data.GetData().length * 10/100));
    } else {
        this.jIFLeafMinCard.setValue(MainKBCT.getConfig().GetLeafMinCard());
    }
    jPanelSaisie.add(jIFLeafMinCard,  new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 30, 0));
    this.jDFAmbiguityThreshold.setValue(MainKBCT.getConfig().GetToleranceThreshold());
    jPanelSaisie.add(jDFAmbiguityThreshold, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 30, 0));
    this.jDFMinEDGain.setValue(MainKBCT.getConfig().GetMinEDGain());
    jPanelSaisie.add(jDFMinEDGain, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 30, 0));
    this.jDFCovThresh.setValue(MainKBCT.getConfig().GetCovThresh());
    jPanelSaisie.add(jDFCovThresh,    new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 30, 0));

    jPanelSaisie.add(jCBRelgain, new GridBagConstraints(0, 8, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.jCBRelgain.setSelected(MainKBCT.getConfig().GetRelgain());
    jPanelSaisie.add(jCBPrune, new GridBagConstraints(0, 9, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.jCBPrune.setSelected(MainKBCT.getConfig().GetPrune());
    jPanelSaisie.add(jCBSplit, new GridBagConstraints(0, 10, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.jCBSplit.setSelected(MainKBCT.getConfig().GetSplit());
    jPanelSaisie.add(jCBDisplay, new GridBagConstraints(0, 11, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.jCBDisplay.setSelected(MainKBCT.getConfig().GetDisplay());
    jPanelSaisie.add(jLabelPerfLoss, new GridBagConstraints(0, 12, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    this.jDFPerfLoss.setValue(MainKBCT.getConfig().GetPerfLoss());
    jPanelSaisie.add(jDFPerfLoss, new GridBagConstraints(2, 12, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 30, 0));
    if (!JTreeGenerateFrame.this.setFDTparameters) {
      this.jpValidationFile = new JPanelValidationFile(this.data);
      jPanelSaisie.add(this.jpValidationFile, new GridBagConstraints(0, 13, 3, 1, 1.0, 0.0
             ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    }
    this.getContentPane().add(jPanelSaisie, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanelValidation.add(jButtonApply, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanelValidation.add(jButtonCancel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanelValidation.add(jButtonDefault, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(jPanelValidation, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
    this.jCBPrune.addItemListener(new java.awt.event.ItemListener() { public void itemStateChanged(ItemEvent e) {
      boolean enable = JTreeGenerateFrame.this.jCBPrune.isSelected();
      JTreeGenerateFrame.this.jCBSplit.setEnabled(enable);
      JTreeGenerateFrame.this.jLabelPerfLoss.setEnabled(enable);
      JTreeGenerateFrame.this.jDFPerfLoss.setEnabled(enable);
      if (!JTreeGenerateFrame.this.setFDTparameters)
        JTreeGenerateFrame.this.jpValidationFile.setEnabled(enable);
    } });
    jButtonApply.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(ActionEvent e) { jButtonApply_actionPerformed(); } });
    jButtonCancel.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(ActionEvent e) { jButtonCancel_actionPerformed(); } });
    jButtonDefault.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(ActionEvent e) { jButtonDefault_actionPerformed(); } });
    this.pack();
    this.setResizable(false);
    this.setModal(true);
    this.setLocation(JChildFrame.ChildPosition(this.parent, this.getSize()));
    this.setVisible(true);
  }
//------------------------------------------------------------------------------
  private int OutputSelected() {
  int retour = 0;
  for( Enumeration e = this.jbgOutput.getElements() ; e.hasMoreElements() ; retour++ )
    if( ((AbstractButton)e.nextElement()).isSelected() == true )
      return retour;

  return retour;
  }
//------------------------------------------------------------------------------
  void jButtonApply_actionPerformed() {
    try {
  	  boolean display = this.jCBDisplay.isSelected();
      boolean prune= this.jCBPrune.isSelected();
      if (!this.setFDTparameters) {
        JFIS [] generated_fis = this.fis.NewTreeFIS(this.data, this.jTFTreeFile.getText(), this.jpValidationFile.GetValidationFile(), this.data.ActiveFileName() + ".result.fistree", this.data.ActiveFileName() + ".perf.fistree", this.jIFTreeDepth.getValue(), this.jDFMinEDGain.getValue(), this.jDFMinSignificantLevel.getValue(), this.jIFLeafMinCard.getValue(), this.jDFAmbiguityThreshold.getValue(), MainKBCT.getConfig().GetSelectedOutput()-1, prune, this.jCBSplit.isSelected(), this.jDFPerfLoss.getValue(), this.jCBRelgain.isSelected(), display, this.jDFCovThresh.getValue());
        if( display == true ) {
          new JFuzzyTreeFrame(this.parent, this.jTFTreeFile.getText());
          if (prune)
            new JFuzzyTreeFrame(this.parent, this.jTFTreeFile.getText()+".pruned");
        }
        this.jPaintRules(generated_fis, false);
      }
      MainKBCT.getConfig().SetSelectedOutput(this.OutputSelected()+1);
      MainKBCT.getConfig().SetTreeFile(this.jTFTreeFile.getText());
      MainKBCT.getConfig().SetMaxTreeDepth(this.jIFTreeDepth.getValue());
      MainKBCT.getConfig().SetMinSignificantLevel(this.jDFMinSignificantLevel.getValue());
      MainKBCT.getConfig().SetLeafMinCard(this.jIFLeafMinCard.getValue());
      MainKBCT.getConfig().SetToleranceThreshold(this.jDFAmbiguityThreshold.getValue());
      MainKBCT.getConfig().SetMinEDGain(this.jDFMinEDGain.getValue());
      MainKBCT.getConfig().SetCovThresh(this.jDFCovThresh.getValue());
      MainKBCT.getConfig().SetPerfLoss(this.jDFPerfLoss.getValue());
      MainKBCT.getConfig().SetSplit(this.jCBSplit.isSelected());
      MainKBCT.getConfig().SetPrune(prune);
      MainKBCT.getConfig().SetDisplay(display);
      MainKBCT.getConfig().SetRelgain(this.jCBRelgain.isSelected());
    } catch( Throwable t ) {
    	t.printStackTrace();
        MessageKBCT.Error(this, LocaleKBCT.GetString("Error"), "Error en JTreeGenerateFrame en jButtonApply_actionPerformed: "+t);
    }
    File f = new File("treefis.out");
    f.delete();
    this.dispose();
  }
//------------------------------------------------------------------------------
  void jPaintRules(JFIS[] generated_fis, boolean automatic) {
      try {
  	    //System.out.println("Induce Rules: FDT: Paint Rules fisnumber: "+generated_fis.length);
        if (JMainFrame.JRFs == null)
          JMainFrame.JRFs= new Vector();

        boolean warning= false;
        if ( (generated_fis.length > 1) && (generated_fis[1] == null) )
        	warning=true;
        	
        for (int m=0; m<generated_fis.length; m++){
          JFIS fis_aux = generated_fis[m];
          if (fis_aux != null) {
            JKBCT kbct_fis = new JKBCT(this.kbct);
            long ptr= kbct_fis.GetPtr();
            int kbct_NbRules = kbct_fis.GetNbRules();
      	    //System.out.println("Induce Rules: FDT: Paint Rules: "+kbct_NbRules);
            for (int k = 0; k < kbct_NbRules; k++)
              kbct_fis.RemoveRule(0);

            this.parent.TranslateRules_FIS2KBCT(fis_aux, kbct_fis);
      	    //System.out.println("Induce Rules: FDT: Paint Rules: "+fis_aux.NbActiveRules());
            JRuleFrame jrf;
            if ( (m>0) || (warning) )
              jrf= new JRuleFrame(this.parent, kbct_fis, new JSemaphore(), false, true, false, !automatic);
            else
              jrf= new JRuleFrame(this.parent, kbct_fis, new JSemaphore(), false, false, false, !automatic);

            if ( (automatic) && (m>0 || warning) ) {
            	jrf.jMenuSave_actionPerformed(false);
                jrf.jMenuClose_actionPerformed();
                MessageKBCT.WriteLogFile("   "+LocaleKBCT.GetString("NumberOfRules")+"= "+kbct_fis.GetNbRules(), "InduceRules");
            } else {
            	JMainFrame.JRFs.add(jrf);
            }
            kbct_fis.Close();
            kbct_fis.Delete();
            jnikbct.DeleteKBCT(ptr+1);
          }
        }
    } catch( Throwable t ) {
    	t.printStackTrace();
        MessageKBCT.Error(this, LocaleKBCT.GetString("Error"), "Error en JTreeGenerateFrame en jPaintRules: "+t);
    }
  }
//------------------------------------------------------------------------------
  void jButtonCancel_actionPerformed() { this.dispose(); }
//------------------------------------------------------------------------------
  void jButtonDefault_actionPerformed() { 
	    this.jTFTreeFile.setText(LocaleKBCT.DefaultTreeFile());
        int cont=0;
	    for( Enumeration e = this.jbgOutput.getElements() ; e.hasMoreElements() ; cont++ )
		    if( cont==LocaleKBCT.DefaultSelectedOutput() )
		    	((AbstractButton)e.nextElement()).setSelected(true);
		    else
		    	((AbstractButton)e.nextElement()).setSelected(false);

	    this.jIFTreeDepth.setValue(LocaleKBCT.DefaultMaxTreeDepth());
	    this.jDFMinSignificantLevel.setValue(LocaleKBCT.DefaultMinSignificantLevel());
	    this.jIFLeafMinCard.setValue(LocaleKBCT.DefaultLeafMinCard());
	    this.jDFAmbiguityThreshold.setValue(LocaleKBCT.DefaultToleranceThreshold());
	    this.jDFMinEDGain.setValue(LocaleKBCT.DefaultMinEDGain());
	    this.jDFCovThresh.setValue(LocaleKBCT.DefaultCovThresh());
	    this.jCBSplit.setSelected(LocaleKBCT.DefaultSplit());
	    this.jDFPerfLoss.setValue(LocaleKBCT.DefaultPerfLoss());
	    this.jCBDisplay.setSelected(LocaleKBCT.DefaultDisplay());
	    this.jCBPrune.setSelected(LocaleKBCT.DefaultPrune());
	    this.jCBRelgain.setSelected(LocaleKBCT.DefaultRelgain());
  }
}