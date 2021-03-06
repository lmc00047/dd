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
//                         JOntologyFrame.java
//
//
//**********************************************************************

package kbctFrames;

import kbct.KBCTListener;
import kbct.JKBCT;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
//import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;

//import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
//import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
//import javax.swing.JTree;
//import javax.swing.ListSelectionModel;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.DefaultTreeCellRenderer;

//import kbct.KBCTListener;
import kbct.LocaleKBCT;
import kbct.MainKBCT;

import kbctAux.MessageKBCT;
//import kbctAux.Translatable;
import kbctAux.JOpenFileChooser;
import kbctAux.JFileFilter;

import org.freehep.util.export.ExportDialog;
import org.positif.core.eventmanager.client.ClientConstants;
//import org.umu.ore.action.ontology.LoadOntologyFromFileAction;
import org.umu.ore.action.ontology.LoadOntologyFromURLAction;
//import org.umu.ore.action.rule.EditRuleAction;
//import org.umu.ore.action.rule.listBrowser.DataPropertyListMouseListener;
//import org.umu.ore.action.rule.listBrowser.InstanceListMouseListener;
//import org.umu.ore.action.rule.listBrowser.ObjectPropertyListMouseListener;
//import org.umu.ore.action.rule.listBrowser.OntologyTreeMouseListener;
//import org.umu.ore.action.rule.listBrowser.VariableListMouseListener;
import org.umu.ore.gui.MainForm;
import org.umu.ore.gui.RuleForm;
//import org.umu.ore.gui.item.dataProperty.DataPropertyListModel;
//import org.umu.ore.gui.item.dataProperty.DataPropertyListRenderer;
//import org.umu.ore.gui.item.individual.IndividualListCellRenderer;
//import org.umu.ore.gui.item.individual.IndividualListModel;
//import org.umu.ore.gui.item.objectProperty.ObjectPropertyListModel;
//import org.umu.ore.gui.item.objectProperty.ObjectPropertyListRenderer;
//import org.umu.ore.gui.item.variable.VariableListCellRenderer;
//import org.umu.ore.gui.item.variable.VariableListModel;
//import org.umu.ore.gui.onotologyForm.OnotologyCellRenderer;
//import org.umu.ore.gui.onotologyForm.OntologyTreeModel;
//import org.umu.ore.data.Rule;
//import org.umu.ore.data.RuleCollection;
import org.umu.ore.util.*;

import print.JPrintPreview;

/**
 * Display an ontology (from file or from URL).
 * User can select ontlogy entries as inputs/outputs of the knowledge base.
 *
 *@author     Jose Maria Alonso Moral
 *@version    3.0 , 03/08/15
 */
public class JOntologyFrame extends JChildFrame {
  static final long serialVersionUID=0;
  private ImageIcon icon_kbct= LocaleKBCT.getIconGUAJE();
  JTabbedPane ontologyPanel;
  JScrollPane infoPanel;
  JTextPane textInfoPanel;
  protected KBCTListener KBCTListener;
  protected JKBCT kbct;
  private JMenu jMenuOpen = new JMenu();
  private JMenuItem jMenuLoadOntologyFile = new JMenuItem();
  private JMenuItem jMenuPrint = new JMenuItem();
  private JMenuItem jMenuExport = new JMenuItem();
  private JMenuItem jMenuClose = new JMenuItem();
  private boolean warning;

  //------------------------------------------------------------------------------
  public JOntologyFrame(JExpertFrame parent, JKBCT kbct) {
	super( parent );
	this.kbct= kbct;
    try {
        this.jbInit();
        if (!this.warning) {
          jMenuLoadOntologyFile.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { jMenuLoadOntologyFile_actionPerformed(); } });
          jMenuPrint.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { jMenuPrint_actionPerformed(); } });
          jMenuExport.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { jMenuExport_actionPerformed(); } });
          jMenuClose.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { JOntologyFrame.this.dispose(); } });
        } else {
        	this.dispose();
        }
    } catch(Exception e) {
        e.printStackTrace();
    	MessageKBCT.Error(this, LocaleKBCT.GetString("Error"), "Error en constructor en JOntologyFrame: "+e);
    }
  }
  //------------------------------------------------------------------------------
  //Component initialization
  private void jbInit() {
    this.setIconImage(icon_kbct.getImage());
    //this.setState(this.NORMAL);
    JMenuBar jmb= new JMenuBar();
    jmb.add(this.jMenuOpen);
    this.jMenuOpen.add(this.jMenuLoadOntologyFile);
    jmb.add(this.jMenuPrint);
    jmb.add(this.jMenuExport);
    jmb.add(this.jMenuClose);
    this.setJMenuBar(jmb);
    this.LoadOntologyFromFileAction(false);
    if (!this.warning) {    	
      this.printOntology();
      this.getContentPane().setLayout(new GridBagLayout());
      this.printPanels();
    }
  }
  //----------------------------------------------------------------------------
  public void Translate() {
    this.setTitle(LocaleKBCT.GetString("LoadOntology"));
    this.jMenuOpen.setText(LocaleKBCT.GetString("Open"));
    this.jMenuLoadOntologyFile.setText(LocaleKBCT.GetString("LoadOntologyFile"));
    this.jMenuPrint.setText(LocaleKBCT.GetString("Print"));
    this.jMenuExport.setText(LocaleKBCT.GetString("Export"));
    this.jMenuClose.setText(LocaleKBCT.GetString("Close"));
    this.repaint();
  }
  //------------------------------------------------------------------------------
  public void this_windowClosing() {
    JExpertFrame.jowlf= null;
    this.dispose();
    JKBCTFrame.RemoveTranslatable(this);
  }
//------------------------------------------------------------------------------
  void jMenuLoadOntologyFile_actionPerformed() {
    //System.out.println("LoadOntologyFromFileAction: 1");
    this.LoadOntologyFromFileAction(true);
    // repaint panel
    if (!this.warning) {    	
        //System.out.println("LoadOntologyFromFileAction: 2");
        this.getContentPane().remove(this.ontologyPanel);
        this.getContentPane().remove(this.infoPanel);
        this.printOntology();
        this.printPanels();
    }
  }
//------------------------------------------------------------------------------
  private void LoadOntologyFromFileAction(boolean reload) {
      //System.out.println("LoadOntologyFromFileAction: 1");
	  ClientConstants.loadProperties(Behavior.FILE_CONFIG);
	  Constants.lastDirectory= MainKBCT.getConfig().GetOntologyPath();
      String canonicalPath="";
      if ( (!reload) && (JKBCTFrame.OntologyFile!=null) && (!JKBCTFrame.OntologyFile.equals("")) ) {
          //System.out.println("LoadOntologyFromFileAction: 2");
          //System.out.println("OntFile= "+JKBCTFrame.OntologyFile);
    	  File f= new File(JKBCTFrame.OntologyFile);
          if (f.exists()) {
              try {
    	          canonicalPath= f.getCanonicalPath();
              } catch (IOException e1) {
                  e1.printStackTrace();
                  MessageKBCT.Error("Error 1 in the loading process of the ontology", e1);
              }
        	  this.warning= false;
          } else {
        	  this.warning= true;
          }
      } else {
    	  this.warning= true;
      }
      if (this.warning || reload) {
          //System.out.println("LoadOntologyFromFileAction: 3");
          JOpenFileChooser file_chooser = new JOpenFileChooser(MainKBCT.getConfig().GetOntologyPath());
    	  file_chooser.setAcceptAllFileFilterUsed(true);
    	  JFileFilter filter = new JFileFilter(("OWL").toLowerCase(), LocaleKBCT.GetString("FilterOWLFile"));
    	  file_chooser.addChoosableFileFilter(filter);
    	  file_chooser.setCurrentDirectory(new File(MainKBCT.getConfig().GetOntologyPath()));
          //System.out.println("LoadOntologyFromFileAction: 4");
          if( file_chooser.showOpenDialog(this.parent) == JFileChooser.APPROVE_OPTION ) {
              try {
                  canonicalPath = file_chooser.getSelectedFile().getCanonicalPath();
              } catch (IOException e1) {
                  e1.printStackTrace();
                  MessageKBCT.Error("Error 2 in the loading process of the ontology", e1);
              }
              MainKBCT.getConfig().SetOntologyPath(file_chooser.getSelectedFile().getParent());
              Constants.lastDirectory= file_chooser.getSelectedFile().getParent();
              this.warning= false;
          } else {
        	  this.warning= true;
          }
      }
      if (!this.warning) {
          String url = "file:///" + canonicalPath;
          JKBCTFrame.OntologyFile= canonicalPath;
          //System.out.println("LoadOntologyFromFileAction: "+JKBCTFrame.OntologyFile);
          OntologiesLocationCollection.reset();
          org.umu.ore.util.OntologiesLocationCollection.getOntologiesLocationCollection().addOntLocation(url);
          //System.out.println("LoadOntologyFromFileAction: 6");
          LoadOntologyFromURLAction.loadOntologyModel(url);
          //System.out.println("LoadOntologyFromFileAction: 7");
      }
  }
//------------------------------------------------------------------------------
  private void printOntology() {
      // crear una regla
      MainForm.getMainForm().getVentana().setCursor(new Cursor(Cursor.WAIT_CURSOR));
      RuleForm form = new RuleForm();
      Behavior.setRuleForm(form);
      this.ontologyPanel= form.getOntologyBrowerTabbedPane();
      this.textInfoPanel= form.getInformationArea();
      MainForm.getMainForm().getVentana().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }
//------------------------------------------------------------------------------
  private void printPanels() {
      this.getContentPane().add(this.ontologyPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
              ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 400, 400));
      this.infoPanel= new JScrollPane(this.textInfoPanel);
      this.getContentPane().add(this.infoPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
              ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 150, 150));
      this.pack();
      this.setSize(470,570);
      this.setResizable(false);
      this.setLocation(this.ChildPosition(this.getSize()));
      this.Translate();
  }
//------------------------------------------------------------------------------
  protected boolean getWarning() {
      return this.warning;
  }
//------------------------------------------------------------------------------
  void jMenuPrint_actionPerformed() {
    try {
      Printable p= new Printable() {
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
          if (pageIndex >= 1)
              return Printable.NO_SUCH_PAGE;
          else
              return JOntologyFrame.this.print(graphics, pageFormat);
        }
      };
      new JPrintPreview(this, p);
    } catch (Exception ex) { MessageKBCT.Error(null, ex); }
  }
//------------------------------------------------------------------------------
  void jMenuExport_actionPerformed() {
    try {
      ExportDialog export= new ExportDialog();
      JPanel panel = new JPanel() {
   	    static final long serialVersionUID=0;
        public void paint(Graphics g) {
          JOntologyFrame.this.ontologyPanel.paint(g);
          g.translate(0, JOntologyFrame.this.ontologyPanel.getHeight());
          JOntologyFrame.this.ontologyPanel.paint(g);
          g.setColor(Color.gray);
          g.drawLine(0,0,0, JOntologyFrame.this.ontologyPanel.getHeight()-1);
        }
      };
      panel.setSize(new Dimension(this.ontologyPanel.getWidth(), this.ontologyPanel.getHeight()));
      export.showExportDialog( panel, "Export view as ...", panel, "export" );
    } catch (Exception ex) { MessageKBCT.Error(null, ex); }
  }
//------------------------------------------------------------------------------
  public int print(java.awt.Graphics g, PageFormat pageFormat) throws PrinterException {
    java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
    double scalew = 1;
    double scaleh = 1;
    double pageHeight = pageFormat.getImageableHeight();
    double pageWidth = pageFormat.getImageableWidth();
    if (getWidth() >= pageWidth)
      scalew = pageWidth / getWidth();

    if (getHeight() >= pageHeight)
      scaleh = pageWidth / getHeight();

    g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
    g2.scale(scalew, scaleh);
    this.ontologyPanel.print(g2);
    return Printable.PAGE_EXISTS;
  }
}