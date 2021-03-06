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
//                           JFISConsole.java
//
//
//**********************************************************************

//***********************************************************************

//
//
//                              JFISConsole.java
//
//
// Author(s) : Jean-Luc LABLEE
// FISPRO Version : 2.1
// Contact : fispro@ensam.inra.fr
// Last modification date:  September 15, 2004
// File :

//**********************************************************************

package kbctFrames;

/**
 * kbctFrames.JFISConsole.
 *
 *@author     Jose Maria Alonso Moral
 *@version    3.0 , 03/08/15
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;

import kbct.LocaleKBCT;
import kbctAux.MessageKBCT;

import org.freehep.util.export.ExportDialog;

import print.JPrintPreview;

//------------------------------------------------------------------------------
public class JFISConsole extends JChildFrame {
  static final long serialVersionUID=0;	
  private JTextArea textArea;
  private JMenuBar jMenuBar;
  private JMenuItem jMenuPrint= new JMenuItem();
  private JMenuItem jMenuExport= new JMenuItem();
  private JMenuItem jMenuClose= new JMenuItem();
  JScrollPane jScrollPanel= new JScrollPane();
  JTextPane jTextPanel= new JTextPane();
  private boolean optionHTML= false;
//------------------------------------------------------------------------------
  public JFISConsole( JKBCTFrame parent, String file_name, boolean HTML ) throws Throwable {
    super(parent);
    this.setTitle(file_name);
    this.optionHTML= HTML;
    this.Init();
    this.ShowFile(new File(file_name));
  }
//------------------------------------------------------------------------------
  public JFISConsole( JKBCTFrame parent, boolean HTML ) throws Throwable {
    super(parent);
    this.setTitle(LocaleKBCT.GetString("JavaConsole"));
    this.optionHTML= HTML;
    this.Init();
  }
//------------------------------------------------------------------------------
  public void Init() throws Throwable {
    this.jMenuBar = new JMenuBar();
    this.setJMenuBar(this.jMenuBar);
    this.setJMenuBar(this.jMenuBar);
    this.jMenuPrint = new JMenuItem();
    this.jMenuExport = new JMenuItem();
    this.jMenuClose = new JMenuItem();
    this.jMenuBar.add(this.jMenuPrint);
    this.jMenuBar.add(this.jMenuExport);
    this.jMenuBar.add(this.jMenuClose);
    Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize=new Dimension((int)(screenSize.width/2),(int)(screenSize.height/2));
    int x=(int)(frameSize.width/2);
    int y=(int)(frameSize.height/2);
    this.setBounds(x,y,frameSize.width,frameSize.height);
    if (!this.optionHTML) {
      textArea=new JTextArea();
      textArea.setEditable(false);
      this.jScrollPanel= new JScrollPane(textArea);
      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add(this.jScrollPanel,BorderLayout.CENTER); //CENTER
    } else {
        this.getContentPane().add(this.jScrollPanel, BorderLayout.CENTER);
    }
    jMenuPrint.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { jMenuPrint_actionPerformed(); } });
    jMenuExport.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { jMenuExport_actionPerformed(); } });
    jMenuClose.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { jMenuClose_actionPerformed(); } });
    this.Translate();
    this.setLocation(this.ChildPosition(this.getSize()));
    this.setVisible(true);
    JKBCTFrame.AddTranslatable(this);
  }
//------------------------------------------------------------------------------
  public void dispose() {
	if (!super.isActive())
        super.dispose();
  }
//------------------------------------------------------------------------------
  public void Translate() throws Throwable {
    this.jMenuPrint.setText(LocaleKBCT.GetString("Print"));
    this.jMenuExport.setText(LocaleKBCT.GetString("Export"));
    this.jMenuClose.setText(LocaleKBCT.GetString("Close"));
    this.repaint();
  }
//------------------------------------------------------------------------------
  public void ShowFile( File file ) {
    try {
        if (this.optionHTML) {
            if (file.getAbsolutePath().endsWith(".html"))
        	    jTextPanel.setBackground(Color.GREEN);
            else if (file.getAbsolutePath().endsWith("log.txt"))
        	    jTextPanel.setBackground(Color.cyan);
            else if (file.getAbsolutePath().endsWith(".txt"))
        	    jTextPanel.setBackground(Color.lightGray);
            	
            this.jScrollPanel.getViewport().add(jTextPanel, null);
            String PageToShow="";
            if (LocaleKBCT.isWindowsPlatform())
              PageToShow= "file:\\"+file.getAbsolutePath();
            else
              PageToShow= "file://"+file.getAbsolutePath();

           	jTextPanel.setEditorKit(new HTMLEditorKit());
           	//System.out.println("PageToShow="+PageToShow);
            jTextPanel.setPage(PageToShow);
            this.jScrollPanel.setWheelScrollingEnabled(true);
            jTextPanel.setSelectedTextColor(Color.RED);
            jTextPanel.setEditable(false);
            this.repaint();
        } else {
            FileInputStream pin= new FileInputStream(file);
            while( pin.available() !=0 ) {
        	   String input = this.readLine(pin);
               textArea.append(input);
            }
            pin.close();
        }
    } catch( Exception e ) {
    	e.printStackTrace();
    	this.textArea.append(e.getLocalizedMessage());
    }
  }
//------------------------------------------------------------------------------
  public String readLine(FileInputStream in) throws IOException {
    String input="";
    do {
      int available=in.available();
      if (available==0) break;
      byte b[]=new byte[available];
      in.read(b);
      input=input+new String(b,0,b.length);
    } while( !input.endsWith("\n") &&  !input.endsWith("\r\n") );
    return input;
  }
//------------------------------------------------------------------------------
  void jMenuPrint_actionPerformed() {
    try {
      Printable p= new Printable() {
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
          if (pageIndex >= 1)
              return Printable.NO_SUCH_PAGE;
          else
              return JFISConsole.this.print(graphics, pageFormat);
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
          if (!JFISConsole.this.optionHTML) {
              JFISConsole.this.textArea.paint(g);
              g.translate(0, JFISConsole.this.textArea.getHeight());
              JFISConsole.this.textArea.paint(g);
              g.setColor(Color.gray);
              g.drawLine(0,0,0, JFISConsole.this.textArea.getHeight()-1);
          } else {
              JFISConsole.this.jTextPanel.paint(g);
              g.translate(0, JFISConsole.this.jTextPanel.getHeight());
              JFISConsole.this.jTextPanel.paint(g);
              g.setColor(Color.gray);
              g.drawLine(0,0,0, JFISConsole.this.jTextPanel.getHeight()-1);
          }
        }
      };
      if (!this.optionHTML)
          panel.setSize(new Dimension(this.textArea.getWidth(), this.textArea.getHeight()));
      else
          panel.setSize(new Dimension(this.jTextPanel.getWidth(), this.jTextPanel.getHeight()));
    	  
      export.showExportDialog( panel, "Export view as ...", panel, "export" );
    } catch (Exception ex) { MessageKBCT.Error(null, ex); }
  }
//------------------------------------------------------------------------------
 public int print(java.awt.Graphics g, PageFormat pageFormat) throws PrinterException {
     java.awt.Graphics2D  g2 = ( java.awt.Graphics2D )g;
     double scalew=1;
     double scaleh=1;
     double pageHeight = pageFormat.getImageableHeight();
     double pageWidth = pageFormat.getImageableWidth();
     if(  getWidth() >= pageWidth )
       scalew =  pageWidth / getWidth();

     if(  getHeight() >= pageHeight)
       scaleh =  pageWidth / getHeight();

     g2.translate( pageFormat.getImageableX(), pageFormat.getImageableY() );
     g2.scale( scalew, scaleh );
     if (!this.optionHTML)
         this.textArea.print( g2 );
     else
         this.jTextPanel.print( g2 );
    	 
     return Printable.PAGE_EXISTS;
 }
//------------------------------------------------------------------------------
  public void jMenuClose_actionPerformed() {
	  //System.out.println("close");
	  super.dispose();
      this.dispose();
 }
}