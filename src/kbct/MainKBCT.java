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
//                              MainKBCT.java
//
//
//**********************************************************************

package kbct;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Random;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.sun.msv.datatype.xsd.StringType;
import xml.XMLParser;
import kbctAux.MessageKBCT;
import kbctFrames.JBeginnerFrame;
import kbctFrames.JExpertFrame;
import kbctFrames.JTutorialFrame;
import kbctFrames.JKBCTFrame;
import kbctFrames.JMainFrame;
import kbctFrames.JPanelSetToolsPathFrame;
import fis.jnifis;

/**
 * kbct.MainKBCT contains the main method which generate objects LocaleKBCT,
 * ConfigKBCT, jnikbct, jnifis, JBeginnerFrame and JKBCTIntegrationFrame
 *
 *@author     Jose Maria Alonso Moral
 *@version    3.0 , 03/08/15
 */
//------------------------------------------------------------------------------
public class MainKBCT {
  private static LocaleKBCT locale;
  private static ConfigKBCT config;
  protected static jnikbct jnikbct;
  protected static jnifis jnifis;
  private static JMainFrame f;
  private static JBeginnerFrame jbf;
  private static JTutorialFrame jtf;
  public static boolean flagHalt=false;
  public static JExpertFrame je =new JExpertFrame();   //SIIIIIIIIII
//------------------------------------------------------------------------------
  public MainKBCT(boolean visible, String[] args) throws Throwable {
	  String path;
	  path="C:/Program Files (x86)/Graphviz2.38/bin";
	  // System.out.println("GRAPHVIZ: "+path);
        	if (true) { 
        	this.jbInit();
        	
        	//Quitar primera ventana (la de las rutas de fispro y graphviz)
        	JPanelSetToolsPathFrame view1 = new JPanelSetToolsPathFrame();
            view1.jbApply_actionPerformed();
            JMainFrame JM = new JMainFrame();
            
           JM.jMenuDataOpen_actionPerformed();
           JM.jMenuExpertOpen_actionPerformed(); //SIIIIIIIIII
           JM.jbInit();
         
   	        }
   }
//------------------------------------------------------------------------------
  public MainKBCT() throws Throwable {
    try { 
    	this.jbInit();
    }
    catch(Throwable t) {
      MessageKBCT.Error(null, t);
      if (t.getMessage().equals("no jnifis in java.library.path")) {
    	  
    	  Frame frame = new Frame();
         frame.setIconImage(LocaleKBCT.getIconGUAJE().getImage());
         new JPanelSetToolsPathFrame(frame);
         JMainFrame JM = new JMainFrame();
         JM.jbInit();
      } else
         System.exit(-1);
    }
  }
//------------------------------------------------------------------------------
  public MainKBCT(boolean noFrames) {
	  try { 
        //generate config object
        config= new ConfigKBCT();
        locale= new LocaleKBCT(config.GetLanguage(), config.GetCountry());
        //generate objects JNI in order to access to c++ libraries of FisPro
        jnikbct= new jnikbct();
        jnifis= new jnifis();
        config.SetTESTautomatic(LocaleKBCT.DefaultTESTautomatic());
    }
    catch(Throwable t) {
      t.printStackTrace();
      if (t.getMessage().equals("no jnifis in java.library.path")) {
          System.out.println("error in running GUAJE without frames");
      } else
    	  System.exit(-1);
    }
  }
//------------------------------------------------------------------------------
  /**
   * Main method of the application KBCT.
   * @param args arguments aren't used
 * @throws Throwable 
   */
  public static void main(String[] args) throws Throwable { 
   	  if (args.length==0) {
   	      new MainKBCT(true, null);
      } else {
    	  //Aqui entra
    	  
         // args[0] -> file IKB
    	 // args[i], i=1..N -> N data file for learning
   	      new MainKBCT(false, args);  	      
      }
  }
//------------------------------------------------------------------------------
  public void Init(String[] args) throws Throwable {
      try {
          MainKBCT.getConfig().SetTESTautomatic(true);
          
             if (args[0].equals("FISfiles")) {

              System.out.println("BEGIN");
              String dir= args[1];
        	  String ikbName= args[2];        	  
              String fisName= args[3];
              System.out.println("OPEN IKB");
              f.jMenuOpen_actionPerformed(dir+System.getProperty("file.separator")+ikbName);
              f.jef.setVisible(false);
              f.setVisible(false);
              System.out.println("New FIS file= "+dir+System.getProperty("file.separator")+fisName);
              System.out.println("SAVE FIS");
              f.jef.Temp_kbct.SaveFIS(dir+System.getProperty("file.separator")+fisName);
              f.jMenuSaveAs_actionPerformed(dir+System.getProperty("file.separator")+ikbName);
              System.out.println("EXIT");
              System.out.println("END");
              
          } else if (args[0].equals("IKBfiles")) {
              System.out.println("BEGIN");
              String dir= args[1];
              if (args.length <= 3) {
                String confFile= args[2];
                LineNumberReader lnr= new LineNumberReader(new InputStreamReader(new FileInputStream(dir+System.getProperty("file.separator")+confFile)));
                int init= (new Integer(lnr.readLine())).intValue();
                int end= (new Integer(lnr.readLine())).intValue();
                int NbIKBs= (new Integer(lnr.readLine())).intValue();
                String[] ikbName= new String[NbIKBs];
                String[] kbName= new String[NbIKBs];
                String[] dfName= new String[NbIKBs];
                for (int n=0; n<NbIKBs; n++) {
            	  String l1= lnr.readLine();
            	  int aux1= l1.indexOf(";");
            	  ikbName[n]= l1.substring(0,aux1);
            	  String l2= l1.substring(aux1+1);
            	  int aux2= l2.indexOf(";");
            	  kbName[n]= l2.substring(0,aux2);
            	  dfName[n]= l2.substring(aux2+1);
                }
                for (int n=init; n<=end; n++) {
                  for (int m=0; m<NbIKBs; m++) {
                    System.out.println("NEW IKB");
                    f.jMenuNew_actionPerformed();
                    System.out.println("OPEN KB");
                    System.out.println("OPEN KB: "+dir+System.getProperty("file.separator")+"conf"+n+System.getProperty("file.separator")+kbName[m]);
                    f.jMenuExpertOpen_actionPerformed(dir+System.getProperty("file.separator")+"conf"+n+System.getProperty("file.separator")+kbName[m]);
                    f.jef.setVisible(false);
                    f.setVisible(false);
                    System.out.println("OPEN DATA");
                    f.jMenuDataOpen_actionPerformed(dir+System.getProperty("file.separator")+"conf"+n+System.getProperty("file.separator")+dfName[m]);
                    f.IKBFile= dir+System.getProperty("file.separator")+"conf"+n+System.getProperty("file.separator")+ikbName[m];
                    System.out.println("New IKB file= "+dir+System.getProperty("file.separator")+"conf"+n+System.getProperty("file.separator")+ikbName[m]);
                    JKBCTFrame.KBExpertFile= dir+System.getProperty("file.separator")+"conf"+n+System.getProperty("file.separator")+kbName[m];
                    System.out.println("SAVE IKB");
                    f.jMenuSaveAs_actionPerformed(dir+System.getProperty("file.separator")+"conf"+n+System.getProperty("file.separator")+ikbName[m]);
                  }
                }
              } else {
                  String ikbName= args[2];        	  
                  String kbName= args[3];
                  String dfName= args[4];
                 
                  System.out.println("NEW IKB");
                  f.jMenuNew_actionPerformed();
                  System.out.println("OPEN KB");
                  System.out.println("OPEN KB: "+dir+System.getProperty("file.separator")+kbName);
                  f.jMenuExpertOpen_actionPerformed(dir+System.getProperty("file.separator")+kbName);//aquiiiiiiiiiii experto
                  f.jef.setVisible(false);
                  f.setVisible(false);
                  System.out.println("OPEN DATA");
                  f.jMenuDataOpen_actionPerformed(dir+System.getProperty("file.separator")+dfName);
                  f.IKBFile= dir+System.getProperty("file.separator")+ikbName;
                  System.out.println("New IKB file= "+dir+System.getProperty("file.separator")+ikbName);
                  JKBCTFrame.KBExpertFile= dir+System.getProperty("file.separator")+kbName;
                  System.out.println("SAVE IKB");
                  f.jMenuSaveAs_actionPerformed(dir+System.getProperty("file.separator")+ikbName);
              }
              System.out.println("EXIT");
              System.out.println("END");
          } else if (args[1].equals("BOOTSTRAP")) {
              System.out.println("BEGIN");
              String BootstrapLogFile1= (JKBCTFrame.BuildFile("LogBootstrap1")).getAbsolutePath();
              System.out.println("BootstrapLogFile1="+BootstrapLogFile1);
              MessageKBCT.BuildLogFile(BootstrapLogFile1,null,null,null,"Bootstrap1");
              MessageKBCT.WriteLogFile("BEGIN","Bootstrap1");
              MessageKBCT.WriteLogFile("----------------------------------","Bootstrap1");
              String BootstrapLogFile2= (JKBCTFrame.BuildFile("LogBootstrap2")).getAbsolutePath();
              System.out.println("BootstrapLogFile2="+BootstrapLogFile2);
              MessageKBCT.BuildLogFile(BootstrapLogFile2,null,null,null,"Bootstrap2");
              MessageKBCT.WriteLogFile("BEGIN","Bootstrap2");
              MessageKBCT.WriteLogFile("----------------------------------","Bootstrap2");
              String input= args[0];
              File f_new = JKBCTFrame.BuildFile(input);
              System.out.println("f_new="+f_new.getAbsolutePath());
              this.readBootstrap(f_new.getAbsolutePath());
              Date d= new Date(System.currentTimeMillis());
              MessageKBCT.WriteLogFile("----------------------------------","Bootstrap1");
              MessageKBCT.WriteLogFile(DateFormat.getDateTimeInstance().format(d),"Bootstrap1");
              MessageKBCT.WriteLogFile("END","Bootstrap1");
              MessageKBCT.CloseLogFile("Bootstrap1");
              MessageKBCT.WriteLogFile("----------------------------------","Bootstrap2");
              MessageKBCT.WriteLogFile(DateFormat.getDateTimeInstance().format(d),"Bootstrap2");
              MessageKBCT.WriteLogFile("END","Bootstrap2");
              MessageKBCT.CloseLogFile("Bootstrap2");
              System.out.println("END");
          } else if (args[1].equals("CHECK")) {
              System.out.println("BEGIN");
              String CheckLogFile= (JKBCTFrame.BuildFile("LogCheck")).getAbsolutePath();
              System.out.println("CheckLogFile="+CheckLogFile);
              MessageKBCT.BuildLogFile(CheckLogFile,null,null,null,"Check");
              MessageKBCT.WriteLogFile("BEGIN","Check");
              MessageKBCT.WriteLogFile("----------------------------------","Check");
              String pattern= args[0];
              File f_new = JKBCTFrame.BuildFile("");
              if (f_new.exists()) {
                String[] files = f_new.list();
                for (int n = 0; n < files.length; n++) {
                  if (files[n].startsWith(pattern)) {
                      MessageKBCT.WriteLogFile("   File="+files[n],"Check");
                      File f_check = JKBCTFrame.BuildFile(files[n]);
                      LineNumberReader lnr= new LineNumberReader(new InputStreamReader(new FileInputStream(f_check)));
                      boolean end= false; 
                      String l;
                      while((l= lnr.readLine())!=null) {
                          if ( (l.equals("Java heap space")) ||
                        	   (l.equals(LocaleKBCT.GetString("WarningSWHalted"))) ||
                        	   (l.equals(LocaleKBCT.GetString("WarningSimplificationHalted"))) ||
                        	   (l.equals(LocaleKBCT.GetString("WarningConsistencySolvingConflictsHalted"))) ) {
                              MessageKBCT.WriteLogFile("   -> "+l,"Check");
                              break;
                          }
                          if ( (l.equals(LocaleKBCT.GetString("AutomaticCompletenessEnded"))) ||
                        	   (l.equals(LocaleKBCT.GetString("AutomaticConsistencyEnded"))) ||
                        	   (l.equals(LocaleKBCT.GetString("AutomaticSimplificationEnded"))) || 
                        	   (l.equals(LocaleKBCT.GetString("AutomaticOptimizationEnded"))) ) {
                        	  end= true;
                          }
                      }
                      if (!end)
                          MessageKBCT.WriteLogFile("   -> No END","Check");
                  }
                }
              }
              Date d= new Date(System.currentTimeMillis());
              MessageKBCT.WriteLogFile("----------------------------------","Check");
              MessageKBCT.WriteLogFile(DateFormat.getDateTimeInstance().format(d),"Check");
              MessageKBCT.WriteLogFile("END","Check");
              MessageKBCT.CloseLogFile("Check");
              System.out.println("END");
          } else {        	  
            String name=args[0];
            String options=args[2];
            String IKBfile=name;
            if ( (!options.equals("-1")) && (!options.equals("SR")) )
                IKBfile= IKBfile.replace(".ikb.xml","_"+options+".ikb.xml");
            	
            //System.out.println("IKBfile="+IKBfile);
            if (args[1].equals("TEST")) {
                System.out.println("LOADING CONFIG");
        	    XMLParser theParser = new XMLParser();
        		File fxml= new File(MainKBCT.getConfig().GetKbctPath()+System.getProperty("file.separator")+"config");
        		if (!fxml.exists()) {
        		    System.out.println("ERROR WHEN READING CONF FILE: "+fxml.getAbsolutePath());
        		} else {
        		    File confFile= new File(fxml, args[4]);
        	        theParser.getXMLinfo(confFile.getAbsolutePath(), "AutoConfParameters");
                    System.out.println("OPEN");
                    System.out.println("IKB file= "+IKBfile);
                    f.jMenuOpen_actionPerformed(IKBfile);
                    f.jef.setVisible(false);
                    f.setVisible(false);
                    System.out.println("BUILD");
                    f.jMenuDataBuildKB_actionPerformed(args[3]);
                    String newIKBfileName= IKBfile;
                    if (!args[3].equals("SR"))
            	        newIKBfileName= newIKBfileName.replace(".ikb.xml","_"+args[3]+".ikb.xml");
                    else
            	        newIKBfileName= newIKBfileName.replace(".ikb.xml","_"+args[2]+".ikb.xml");
              
                    if (MainKBCT.config.GetSMOTE()) {
                    	f.KBDataFile= f.KBDataFile+".smote.txt";
                    } 
                    String newKBfile= newIKBfileName.replace(".ikb.xml",".kb.xml");
                    System.out.println("New KB file= "+newKBfile);
                    f.jef.Temp_kbct.Save(newKBfile, true);
                    f.IKBFile= newIKBfileName;
                    System.out.println("New IKB file= "+newIKBfileName);
                    JKBCTFrame.KBExpertFile= newKBfile;
                    f.jMenuSave_actionPerformed();
                    System.out.println("EXIT");
                }
            } else if (args[1].equals("QUALITY")) { 
                System.out.println("OPEN");
                f.jMenuOpen_actionPerformed(IKBfile);
                f.jef.setVisible(false);
                f.setVisible(false);
                System.out.println("QUALITY");
                f.jef.jButtonQuality_actionPerformed(true,false);
                System.out.println("CLOSE");
                f.jef.jMenuClose_actionPerformed();
                f.jMenuClose_actionPerformed();
                System.out.println("EXIT");
                f.jMenuExit_actionPerformed();
            } else if (args[1].equals("INTERPRETABILITY")) { 
                System.out.println("OPEN");
                f.jMenuOpen_actionPerformed(IKBfile);
                f.jef.setVisible(false);
                f.setVisible(false);
                System.out.println("INTERPRETABILITY");
                f.jef.flagOnlyInterpretability= true;
                f.jef.jButtonQuality_actionPerformed(true,false);
                // parser
                System.out.println("CLOSE");
                f.jef.jMenuClose_actionPerformed();
                f.jMenuClose_actionPerformed();
                System.out.println("EXIT");
                f.jMenuExit_actionPerformed();
            } else if (args[1].equals("INFERENCE")) {
                MainKBCT.getConfig().SetINFERautomatic(true);
            	System.out.println("OPEN");
                f.jMenuOpen_actionPerformed(IKBfile);
                f.jef.setVisible(false);
                f.setVisible(false);
                System.out.println("INFERENCE");
                f.jef.jButtonInfer_actionPerformed();
                System.out.println("CLOSE");
                f.jef.jMenuClose_actionPerformed();
                f.jMenuClose_actionPerformed();
                System.out.println("EXIT");
                f.jMenuExit_actionPerformed();
                MainKBCT.getConfig().SetINFERautomatic(false);
            } else if (args[1].equals("EVAL")) {
              System.out.println("OPEN");
              MessageKBCT.BuildCVLogFile(name.replace("Quality","Eval")+"_"+args[3]+"_"+options);
              System.out.println("EVAL");
              int Nb= (new Integer(args[4])).intValue();
              MessageKBCT.WriteCVLogFile(name, options, args[3], "LRN", Nb);
              MessageKBCT.WriteCVLogFile(name, options, args[3], "TST", Nb);
              System.out.println("CLOSE");
              MessageKBCT.CloseLogFile("CV");
              System.out.println("EXIT");
            } else if (args[1].equals("SELECT")) {
              System.out.println("OPEN");
              String evName= name+"_"+options;
              System.out.println("SELECT");
              MessageKBCT.SelectBestOption(evName+"_LRN");
              MessageKBCT.SelectBestOption(evName+"_TST");
              System.out.println("CLOSE");
              System.out.println("EXIT");
            } else if (args[1].equals("interpBATS")) {
                System.out.println("BEGIN");
                String path=args[0];
                String problem= args[2];
                MessageKBCT.buildBATfile(path, "interp.bat", problem, "interpCONF");
                System.out.println("END");
            } else if (args[1].equals("BATS")) {
              System.out.println("BEGIN");
              String path=args[0];
              String problem= args[2];
              MessageKBCT.buildBATfile(path, "EVAL.bat", problem, "EVAL", null);
              MessageKBCT.buildBATfile(path, "EXCEL30.bat", problem, "EXCEL30", null);
              MessageKBCT.buildBATfile(path, "QUALITY.bat", problem, "QUALITY", null);
              MessageKBCT.buildBATfile(path, "QUALITY30.bat", problem, "QUALITY30", null);
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT.bat", problem, "call QUALITY30 RP_CL_FDT");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT.bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S.bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC.bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S.bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_CA.bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_CA_S.bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM.bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S.bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC.bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S.bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_CA.bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_CA_S.bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM.bat", problem, "call QUALITY30 RP_CL_WM");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT.bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S.bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC.bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S.bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_CA.bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_CA_S.bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM.bat", problem, "call QUALITY30 RP_CL_WM_DS_WM");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S.bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC.bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S.bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_CA.bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_CA_S.bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT.bat", problem, "call QUALITY30 RP_FDT");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S.bat", problem, "call QUALITY30 RP_FDT_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 RP_FDT_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 RP_FDT_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 RP_FDT_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 RP_FDT_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 RP_FDT_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 RP_FDT_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_CA.bat", problem, "call QUALITY30 RP_FDT_S_CA");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_CA_S.bat", problem, "call QUALITY30 RP_FDT_S_CA_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_CA_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 RP_FDT_S_CA_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_CA_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 RP_FDT_S_CA_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 RP_FDT_S_CA_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 RP_FDT_S_CA_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 RP_FDT_S_CA_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_FDT_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 RP_FDT_S_CA_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM.bat", problem, "call QUALITY30 RP_WM");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S.bat", problem, "call QUALITY30 RP_WM_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 RP_WM_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 RP_WM_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 RP_WM_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 RP_WM_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 RP_WM_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 RP_WM_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_CA.bat", problem, "call QUALITY30 RP_WM_S_CA");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_CA_S.bat", problem, "call QUALITY30 RP_WM_S_CA_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_CA_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 RP_WM_S_CA_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_CA_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 RP_WM_S_CA_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 RP_WM_S_CA_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 RP_WM_S_CA_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 RP_WM_S_CA_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_RP_WM_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 RP_WM_S_CA_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT.bat", problem, "call QUALITY30 BP_CL_FDT");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT.bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S.bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC.bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S.bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_CA.bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_CA_S.bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM.bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S.bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC.bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S.bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_CA.bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_CA_S.bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM.bat", problem, "call QUALITY30 BP_CL_WM");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT.bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S.bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC.bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S.bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_CA.bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_CA_S.bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM.bat", problem, "call QUALITY30 BP_CL_WM_DS_WM");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S.bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC.bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S.bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_CA.bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_CA_S.bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT.bat", problem, "call QUALITY30 BP_FDT");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S.bat", problem, "call QUALITY30 BP_FDT_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 BP_FDT_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 BP_FDT_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 BP_FDT_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 BP_FDT_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 BP_FDT_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 BP_FDT_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_CA.bat", problem, "call QUALITY30 BP_FDT_S_CA");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_CA_S.bat", problem, "call QUALITY30 BP_FDT_S_CA_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_CA_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 BP_FDT_S_CA_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_CA_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 BP_FDT_S_CA_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 BP_FDT_S_CA_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 BP_FDT_S_CA_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 BP_FDT_S_CA_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_FDT_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 BP_FDT_S_CA_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM.bat", problem, "call QUALITY30 BP_WM");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S.bat", problem, "call QUALITY30 BP_WM_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 BP_WM_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 BP_WM_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 BP_WM_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 BP_WM_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 BP_WM_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 BP_WM_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_CA.bat", problem, "call QUALITY30 BP_WM_S_CA");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_CA_S.bat", problem, "call QUALITY30 BP_WM_S_CA_S");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_CA_S_O_SW_SI_L_10.bat", problem, "call QUALITY30 BP_WM_S_CA_S_O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_CA_S_O_SW_EI_L_10.bat", problem, "call QUALITY30 BP_WM_S_CA_S_O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call QUALITY30 BP_WM_S_CA_S_O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call QUALITY30 BP_WM_S_CA_S_O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call QUALITY30 BP_WM_S_CA_S_O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "QUALITYcv_BP_WM_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call QUALITY30 BP_WM_S_CA_S_O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TEST30.bat", problem, "TEST30", null);
              MessageKBCT.buildBATfile(path, "TEST30_RP_FDT.bat", problem, "TEST30", "_RP_FDT");
              MessageKBCT.buildBATfile(path, "TEST30_RP_FDT_S.bat", problem, "TEST30", "_RP_FDT_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_FDT_S_CA.bat", problem, "TEST30", "_RP_FDT_S_CA");
              MessageKBCT.buildBATfile(path, "TEST30_RP_FDT_S_CA_S.bat", problem, "TEST30", "_RP_FDT_S_CA_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_WM.bat", problem, "TEST30", "_RP_WM");
              MessageKBCT.buildBATfile(path, "TEST30_RP_WM_S.bat", problem, "TEST30", "_RP_WM_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_WM_S_CA.bat", problem, "TEST30", "_RP_WM_S_CA");
              MessageKBCT.buildBATfile(path, "TEST30_RP_WM_S_CA_S.bat", problem, "TEST30", "_RP_WM_S_CA_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP.bat", problem, "TEST30", "_BP");
              MessageKBCT.buildBATfile(path, "TEST30_BP_FDT.bat", problem, "TEST30", "_BP_FDT");
              MessageKBCT.buildBATfile(path, "TEST30_BP_FDT_S.bat", problem, "TEST30", "_BP_FDT_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_FDT_S_CA.bat", problem, "TEST30", "_BP_FDT_S_CA");
              MessageKBCT.buildBATfile(path, "TEST30_BP_FDT_S_CA_S.bat", problem, "TEST30", "_BP_FDT_S_CA_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_WM.bat", problem, "TEST30", "_BP_WM");
              MessageKBCT.buildBATfile(path, "TEST30_BP_WM_S.bat", problem, "TEST30", "_BP_WM_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_WM_S_CA.bat", problem, "TEST30", "_BP_WM_S_CA");
              MessageKBCT.buildBATfile(path, "TEST30_BP_WM_S_CA_S.bat", problem, "TEST30", "_BP_WM_S_CA_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_FDT.bat", problem, "TEST30", "_BP_CL_FDT");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_FDT_DS_FDT.bat", problem, "TEST30", "_BP_CL_FDT_DS_FDT");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_FDT_DS_FDT_S.bat", problem, "TEST30", "_BP_CL_FDT_DS_FDT_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_FDT_DS_FDT_S_SC.bat", problem, "TEST30", "_BP_CL_FDT_DS_FDT_S_SC");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_FDT_DS_FDT_S_SC_S.bat", problem, "TEST30", "_BP_CL_FDT_DS_FDT_S_SC_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_FDT_DS_FDT_S_SC_S_CA.bat", problem, "TEST30", "_BP_CL_FDT_DS_FDT_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S.bat", problem, "TEST30", "_BP_CL_FDT_DS_FDT_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_FDT_DS_WM.bat", problem, "TEST30", "_BP_CL_FDT_DS_WM");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_FDT_DS_WM_S.bat", problem, "TEST30", "_BP_CL_FDT_DS_WM_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_FDT_DS_WM_S_SC.bat", problem, "TEST30", "_BP_CL_FDT_DS_WM_S_SC");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_FDT_DS_WM_S_SC_S.bat", problem, "TEST30", "_BP_CL_FDT_DS_WM_S_SC_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_FDT_DS_WM_S_SC_S_CA.bat", problem, "TEST30", "_BP_CL_FDT_DS_WM_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_FDT_DS_WM_S_SC_S_CA_S.bat", problem, "TEST30", "_BP_CL_FDT_DS_WM_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_WM.bat", problem, "TEST30", "_BP_CL_WM");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_WM_DS_FDT.bat", problem, "TEST30", "_BP_CL_WM_DS_FDT");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_WM_DS_FDT_S.bat", problem, "TEST30", "_BP_CL_WM_DS_FDT_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_WM_DS_FDT_S_SC.bat", problem, "TEST30", "_BP_CL_WM_DS_FDT_S_SC");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_WM_DS_FDT_S_SC_S.bat", problem, "TEST30", "_BP_CL_WM_DS_FDT_S_SC_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_WM_DS_FDT_S_SC_S_CA.bat", problem, "TEST30", "_BP_CL_WM_DS_FDT_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_WM_DS_FDT_S_SC_S_CA_S.bat", problem, "TEST30", "_BP_CL_WM_DS_FDT_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_WM_DS_WM.bat", problem, "TEST30", "_BP_CL_WM_DS_WM");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_WM_DS_WM_S.bat", problem, "TEST30", "_BP_CL_WM_DS_WM_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_WM_DS_WM_S_SC.bat", problem, "TEST30", "_BP_CL_WM_DS_WM_S_SC");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_WM_DS_WM_S_SC_S.bat", problem, "TEST30", "_BP_CL_WM_DS_WM_S_SC_S");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_WM_DS_WM_S_SC_S_CA.bat", problem, "TEST30", "_BP_CL_WM_DS_WM_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "TEST30_BP_CL_WM_DS_WM_S_SC_S_CA_S.bat", problem, "TEST30", "_BP_CL_WM_DS_WM_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_FDT.bat", problem, "TEST30", "_RP_CL_FDT");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_FDT_DS_FDT.bat", problem, "TEST30", "_RP_CL_FDT_DS_FDT");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_FDT_DS_FDT_S.bat", problem, "TEST30", "_RP_CL_FDT_DS_FDT_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_FDT_DS_FDT_S_SC.bat", problem, "TEST30", "_RP_CL_FDT_DS_FDT_S_SC");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_FDT_DS_FDT_S_SC_S.bat", problem, "TEST30", "_RP_CL_FDT_DS_FDT_S_SC_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_FDT_DS_FDT_S_SC_S_CA.bat", problem, "TEST30", "_RP_CL_FDT_DS_FDT_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S.bat", problem, "TEST30", "_RP_CL_FDT_DS_FDT_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_FDT_DS_WM.bat", problem, "TEST30", "_RP_CL_FDT_DS_WM");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_FDT_DS_WM_S.bat", problem, "TEST30", "_RP_CL_FDT_DS_WM_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_FDT_DS_WM_S_SC.bat", problem, "TEST30", "_RP_CL_FDT_DS_WM_S_SC");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_FDT_DS_WM_S_SC_S.bat", problem, "TEST30", "_RP_CL_FDT_DS_WM_S_SC_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_FDT_DS_WM_S_SC_S_CA.bat", problem, "TEST30", "_RP_CL_FDT_DS_WM_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_FDT_DS_WM_S_SC_S_CA_S.bat", problem, "TEST30", "_RP_CL_FDT_DS_WM_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_WM.bat", problem, "TEST30", "_RP_CL_WM");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_WM_DS_FDT.bat", problem, "TEST30", "_RP_CL_WM_DS_FDT");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_WM_DS_FDT_S.bat", problem, "TEST30", "_RP_CL_WM_DS_FDT_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_WM_DS_FDT_S_SC.bat", problem, "TEST30", "_RP_CL_WM_DS_FDT_S_SC");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_WM_DS_FDT_S_SC_S.bat", problem, "TEST30", "_RP_CL_WM_DS_FDT_S_SC_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_WM_DS_FDT_S_SC_S_CA.bat", problem, "TEST30", "_RP_CL_WM_DS_FDT_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_WM_DS_FDT_S_SC_S_CA_S.bat", problem, "TEST30", "_RP_CL_WM_DS_FDT_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_WM_DS_WM.bat", problem, "TEST30", "_RP_CL_WM_DS_WM");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_WM_DS_WM_S.bat", problem, "TEST30", "_RP_CL_WM_DS_WM_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_WM_DS_WM_S_SC.bat", problem, "TEST30", "_RP_CL_WM_DS_WM_S_SC");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_WM_DS_WM_S_SC_S.bat", problem, "TEST30", "_RP_CL_WM_DS_WM_S_SC_S");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_WM_DS_WM_S_SC_S_CA.bat", problem, "TEST30", "_RP_CL_WM_DS_WM_S_SC_S_CA");
              MessageKBCT.buildBATfile(path, "TEST30_RP_CL_WM_DS_WM_S_SC_S_CA_S.bat", problem, "TEST30", "_RP_CL_WM_DS_WM_S_SC_S_CA_S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP.bat", problem, "call TEST30 "+"BP");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT.bat", problem, "call TEST30_BP CL_FDT");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT.bat", problem, "call TEST30_BP_CL_FDT DS_FDT");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S.bat", problem, "call TEST30_BP_CL_FDT_DS_FDT S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC.bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S SC");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S.bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_O_SW_SI_L_10.bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_O_SW_EI_L_10.bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_CA.bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S CA");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S.bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S_CA S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_FDT_S_SC_S_CA_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM.bat", problem, "call TEST30_BP_CL_FDT DS_WM");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S.bat", problem, "call TEST30_BP_CL_FDT_DS_WM S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC.bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S SC");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S.bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_O_SW_SI_L_10.bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_O_SW_EI_L_10.bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_CA.bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S CA");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_CA_S.bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S_CA S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S_CA_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S_CA_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S_CA_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S_CA_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S_CA_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_BP_CL_FDT_DS_WM_S_SC_S_CA_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM.bat", problem, "call TEST30_BP CL_WM");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT.bat", problem, "call TEST30_BP_CL_WM DS_FDT");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S.bat", problem, "call TEST30_BP_CL_WM_DS_FDT S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC.bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S SC");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S.bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_O_SW_SI_L_10.bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_O_SW_EI_L_10.bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_CA.bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S CA");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_CA_S.bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S_CA S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S_CA_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S_CA_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S_CA_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S_CA_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S_CA_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_FDT_S_SC_S_CA_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM.bat", problem, "call TEST30_BP_CL_WM DS_WM");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S.bat", problem, "call TEST30_BP_CL_WM_DS_WM S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC.bat", problem, "call TEST30_BP_CL_WM_DS_WM_S SC");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S.bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_O_SW_SI_L_10.bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_O_SW_EI_L_10.bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_CA.bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S CA");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_CA_S.bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S_CA S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S_CA_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S_CA_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S_CA_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S_CA_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S_CA_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_BP_CL_WM_DS_WM_S_SC_S_CA_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT.bat", problem, "call TEST30_BP FDT");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S.bat", problem, "call TEST30_BP_FDT S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_O_SW_SI_L_10.bat", problem, "call TEST30_BP_FDT_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_O_SW_EI_L_10.bat", problem, "call TEST30_BP_FDT_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_BP_FDT_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_BP_FDT_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_BP_FDT_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_BP_FDT_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_CA.bat", problem, "call TEST30_BP_FDT_S CA");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_CA_S.bat", problem, "call TEST30_BP_FDT_S_CA S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_CA_S_O_SW_SI_L_10.bat", problem, "call TEST30_BP_FDT_S_CA_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_CA_S_O_SW_EI_L_10.bat", problem, "call TEST30_BP_FDT_S_CA_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_BP_FDT_S_CA_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_BP_FDT_S_CA_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_BP_FDT_S_CA_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_FDT_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_BP_FDT_S_CA_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM.bat", problem, "call TEST30_BP WM");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S.bat", problem, "call TEST30_BP_WM S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_O_SW_SI_L_10.bat", problem, "call TEST30_BP_WM_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_O_SW_EI_L_10.bat", problem, "call TEST30_BP_WM_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_BP_WM_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_BP_WM_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_BP_WM_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_BP_WM_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_CA.bat", problem, "call TEST30_BP_WM_S CA");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_CA_S.bat", problem, "call TEST30_BP_WM_S_CA S");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_CA_S_O_SW_SI_L_10.bat", problem, "call TEST30_BP_WM_S_CA_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_CA_S_O_SW_EI_L_10.bat", problem, "call TEST30_BP_WM_S_CA_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_BP_WM_S_CA_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_BP_WM_S_CA_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_BP_WM_S_CA_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_BP_WM_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_BP_WM_S_CA_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT.bat", problem, "call TEST30 RP_CL_FDT");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT.bat", problem, "call TEST30_RP_CL_FDT DS_FDT");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S.bat", problem, "call TEST30_RP_CL_FDT_DS_FDT S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC.bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S SC");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S.bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_O_SW_SI_L_10.bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_O_SW_EI_L_10.bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_CA.bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S CA");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S.bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S_CA S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_FDT_S_SC_S_CA_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM.bat", problem, "call TEST30_RP_CL_FDT DS_WM");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S.bat", problem, "call TEST30_RP_CL_FDT_DS_WM S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC.bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S SC");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S.bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_O_SW_SI_L_10.bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_O_SW_EI_L_10.bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_CA.bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S CA");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_CA_S.bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S_CA S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S_CA_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S_CA_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S_CA_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S_CA_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S_CA_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_RP_CL_FDT_DS_WM_S_SC_S_CA_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM.bat", problem, "call TEST30 RP_CL_WM");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT.bat", problem, "call TEST30_RP_CL_WM DS_FDT");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S.bat", problem, "call TEST30_RP_CL_WM_DS_FDT S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC.bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S SC");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S.bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_O_SW_SI_L_10.bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_O_SW_EI_L_10.bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_CA.bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S CA");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_CA_S.bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S_CA S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S_CA_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S_CA_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S_CA_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S_CA_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S_CA_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_FDT_S_SC_S_CA_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM.bat", problem, "call TEST30_RP_CL_WM DS_WM");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S.bat", problem, "call TEST30_RP_CL_WM_DS_WM S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC.bat", problem, "call TEST30_RP_CL_WM_DS_WM_S SC");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S.bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_O_SW_SI_L_10.bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_O_SW_EI_L_10.bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_CA.bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S CA");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_CA_S.bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S_CA S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10.bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S_CA_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10.bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S_CA_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S_CA_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S_CA_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S_CA_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_RP_CL_WM_DS_WM_S_SC_S_CA_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT.bat", problem, "call TEST30 RP_FDT");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S.bat", problem, "call TEST30_RP_FDT S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_O_SW_SI_L_10.bat", problem, "call TEST30_RP_FDT_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_O_SW_EI_L_10.bat", problem, "call TEST30_RP_FDT_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_RP_FDT_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_RP_FDT_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_RP_FDT_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_RP_FDT_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_CA.bat", problem, "call TEST30_RP_FDT_S CA");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_CA_S.bat", problem, "call TEST30_RP_FDT_S_CA S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_CA_S_O_SW_SI_L_10.bat", problem, "call TEST30_RP_FDT_S_CA_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_CA_S_O_SW_EI_L_10.bat", problem, "call TEST30_RP_FDT_S_CA_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_RP_FDT_S_CA_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_RP_FDT_S_CA_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_RP_FDT_S_CA_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_FDT_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_RP_FDT_S_CA_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM.bat", problem, "call TEST30 RP_WM");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S.bat", problem, "call TEST30_RP_WM S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_O_SW_SI_L_10.bat", problem, "call TEST30_RP_WM_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_O_SW_EI_L_10.bat", problem, "call TEST30_RP_WM_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_RP_WM_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_RP_WM_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_RP_WM_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_RP_WM_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_CA.bat", problem, "call TEST30_RP_WM_S CA");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_CA_S.bat", problem, "call TEST30_RP_WM_S_CA S");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_CA_S_O_SW_SI_L_10.bat", problem, "call TEST30_RP_WM_S_CA_S O_SW_SI_L_10");
              MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_CA_S_O_SW_EI_L_10.bat", problem, "call TEST30_RP_WM_S_CA_S O_SW_EI_L_10");
              for (int n=1; n<=6; n++) {
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_CA_S_O_GT_SI_100_"+n+".bat", problem, "call TEST30_RP_WM_S_CA_S O_GT_SI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_CA_S_O_GT_EI_100_"+n+".bat", problem, "call TEST30_RP_WM_S_CA_S O_GT_EI_100_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_CA_S_O_GT_SI_1000_"+n+".bat", problem, "call TEST30_RP_WM_S_CA_S O_GT_SI_1000_"+n);
                  MessageKBCT.buildBATfile(path, "TESTcv30_RP_WM_S_CA_S_O_GT_EI_1000_"+n+".bat", problem, "call TEST30_RP_WM_S_CA_S O_GT_EI_1000_"+n);
              }
              MessageKBCT.buildBATfile(path, "BOOTSTRAP.bat", problem, "BOOTSTRAP", null);
              MessageKBCT.buildBATfile(path, "CHECK.bat", problem, "CHECK", null);
              MessageKBCT.buildBATfile(path, "SELECT.bat", problem, "SELECT", null);
              MessageKBCT.buildBATfile(path, "TESTSIMP.bat", problem, "TESTSIMP", null);
              MessageKBCT.buildBATfile(path, "TESTSIMPSC.bat", problem, "TESTSIMPSC", null);
              MessageKBCT.buildBATfile(path, "TESTSIMPSCSIMP.bat", problem, "TESTSIMPSCSIMP", null);
              MessageKBCT.buildBATfile(path, "TESTOSWsi.bat", problem, "TESTOSWsi", null);
              MessageKBCT.buildBATfile(path, "TESTOSWei.bat", problem, "TESTOSWei", null);
              MessageKBCT.buildBATfile(path, "TESTOGTsi100.bat", problem, "TESTOGTsi100", null);
              MessageKBCT.buildBATfile(path, "TESTOGTsi1000.bat", problem, "TESTOGTsi1000", null);
              MessageKBCT.buildBATfile(path, "TESTOGTei100.bat", problem, "TESTOGTei100", null);
              MessageKBCT.buildBATfile(path, "TESTOGTei1000.bat", problem, "TESTOGTei1000", null);
              MessageKBCT.buildBATfile(path, "TESTCA.bat", problem, "TESTCA", null);
              MessageKBCT.buildBATfile(path, "TESTCASIMP.bat", problem, "TESTCASIMP", null);
              MessageKBCT.buildBATfile(path, "TESTCASOSWsi.bat", problem, "TESTCASOSWsi", null);
              MessageKBCT.buildBATfile(path, "TESTCASOSWei.bat", problem, "TESTCASOSWei", null);
              MessageKBCT.buildBATfile(path, "TESTCASOGTsi100.bat", problem, "TESTCASOGTsi100", null);
              MessageKBCT.buildBATfile(path, "TESTCASOGTsi1000.bat", problem, "TESTCASOGTsi1000", null);
              MessageKBCT.buildBATfile(path, "TESTCASOGTei100.bat", problem, "TESTCASOGTei100", null);
              MessageKBCT.buildBATfile(path, "TESTCASOGTei1000.bat", problem, "TESTCASOGTei1000", null);
              System.out.println("END");
            } else if (args[1].equals("BATFILE")) {
              System.out.println("BEGIN");
              String path=args[0];
              String problem= args[2];
              String batName= args[3];
              String bat= args[4];
              String opt= args[5];
              MessageKBCT.buildBATfile(path, batName, problem, "call "+bat+" "+opt);
              System.out.println("END");
            } else if (args[1].equals("PARSERQ")) {
              System.out.println("OPEN");
              MessageKBCT.BuildExcelLogFile(name.replace("Quality","QualityExcel"), options);
              System.out.println("PARSER");
              System.out.println("args[0]="+args[0]);
              System.out.println("args[1]="+args[1]);
              System.out.println("args[2]="+args[2]);
              System.out.println("args[3]="+args[3]);
              MessageKBCT.WriteExcelLogFile(name, options, new Integer(args[3]).intValue());
              System.out.println("CLOSE");
              MessageKBCT.CloseLogFile("QualityExcel");
              System.out.println("EXIT");
            } else if (args[1].equals("PARSERQQ")) {
                System.out.println("OPEN");
                MessageKBCT.BuildExcelLogFile(name.replace("Quality","QualityExcel"), options);
                System.out.println("PARSER");
                System.out.println("args[0]="+args[0]);
                System.out.println("args[1]="+args[1]);
                System.out.println("args[2]="+args[2]);
                MessageKBCT.WriteAccExcelLogFile(name, new Integer(args[2]).intValue());
                System.out.println("CLOSE");
                MessageKBCT.CloseLogFile("QualityExcel");
                System.out.println("EXIT");
              }
          }
             
      } catch (Exception e) {/*e.printStackTrace();*/}  
      MainKBCT.getConfig().SetTESTautomatic(false);
      
      System.exit(0);
  }
//------------------------------------------------------------------------------
  private void jbInit(boolean visible) throws Throwable {
    // generate config object
    config= new ConfigKBCT();
    locale= new LocaleKBCT( config.GetLanguage(), config.GetCountry() );
    locale.SetIconImages();
    // generate objets JNI in order to access to c++ libraries of FisPro
    jnikbct= new jnikbct();
    jnifis= new jnifis();
    config.SetTESTautomatic(LocaleKBCT.DefaultTESTautomatic());
    f= new JMainFrame();
    // load look and feel configuration
    String look = MainKBCT.config.GetLookAndFeel();
    UIManager.setLookAndFeel(look);

    SwingUtilities.updateComponentTreeUI(f);
    // make visible main window
    f.setVisible(visible);
    String user = MainKBCT.config.GetUser();
    jbf= new JBeginnerFrame("index.html");
    if (user.equals(LocaleKBCT.GetString("Beginner")))
       jbf.setVisible(visible);
  }
//------------------------------------------------------------------------------
  private void jbInit() throws Throwable {
    // generate config object
    config= new ConfigKBCT();
    locale= new LocaleKBCT( config.GetLanguage(), config.GetCountry() );
    locale.SetIconImages();
    // generate objets JNI in order to access to c++ libraries of FisPro
    jnikbct= new jnikbct();
    jnifis= new jnifis();
    config.SetTESTautomatic(LocaleKBCT.DefaultTESTautomatic());
    
  }
//------------------------------------------------------------------------------
  private void readBootstrap(String file) throws Throwable {
	  DecimalFormat df= new DecimalFormat();
	  df.setMaximumFractionDigits(MainKBCT.getConfig().GetNumberOfDecimals());
	  df.setMinimumFractionDigits(MainKBCT.getConfig().GetNumberOfDecimals());
	  DecimalFormatSymbols dfs= df.getDecimalFormatSymbols();
	  dfs.setDecimalSeparator((new String(",").charAt(0)));
	  df.setDecimalFormatSymbols(dfs);
	  df.setGroupingSize(20);
	  String method=null;
	  Double[] dd= new Double[30];
      PrintStream logEXCELboot= new PrintStream(new FileOutputStream("LogEXCEL-Bootstrap.xls", true));
	  LineNumberReader lnr= new LineNumberReader(new InputStreamReader(new FileInputStream(file)));
      String l;
	  while((l= lnr.readLine())!=null) {
         int ind1= l.indexOf(",");
         method= l.substring(0,ind1);
         MessageKBCT.WriteLogFile("method="+method,"Bootstrap1");
         MessageKBCT.WriteLogFile("method="+method,"Bootstrap2");
         String aux= l.substring(ind1+1);
         for (int n=0; n<29; n++) {
        	 int ind2= aux.indexOf(",");
        	 dd[n]= new Double(aux.substring(0,ind2));
        	 aux= aux.substring(ind2+1);
             MessageKBCT.WriteLogFile("   dd["+n+"]="+dd[n],"Bootstrap1");
         }
    	 dd[29]= new Double(aux);
         MessageKBCT.WriteLogFile("   dd[29]="+dd[29],"Bootstrap1");
         double[] res= this.computeBootstrap(dd);
         MessageKBCT.WriteLogFile("------confidence 95%","Bootstrap1");
         MessageKBCT.WriteLogFile("------min="+res[0],"Bootstrap1");
         MessageKBCT.WriteLogFile("------max="+res[1],"Bootstrap1");
         MessageKBCT.WriteLogFile("------mean="+res[2],"Bootstrap1");
         MessageKBCT.WriteLogFile("--------------------","Bootstrap1");
         MessageKBCT.WriteLogFile("------confidence 95%","Bootstrap2");
         MessageKBCT.WriteLogFile("------min="+res[0],"Bootstrap2");
         MessageKBCT.WriteLogFile("------max="+res[1],"Bootstrap2");
         MessageKBCT.WriteLogFile("------mean="+res[2],"Bootstrap2");
         MessageKBCT.WriteLogFile("--------------------","Bootstrap2");
         System.out.println("LogEXCEL-Bootstrap-"+method+".xls");
         PrintStream logEXCEL= new PrintStream(new FileOutputStream("LogEXCEL-Bootstrap-"+method+".xls", true));
         for (int n=0; n<res.length; n++) {
    	     logEXCEL.print(df.format(res[n])+"	");
    	     logEXCELboot.print(df.format(res[n])+"	");
         }
 	     logEXCEL.println();
 	     logEXCELboot.println();
 	     logEXCEL.flush();
 	     logEXCEL.close();
	  }
	  logEXCELboot.flush();
 	  logEXCELboot.close();
  }
//------------------------------------------------------------------------------
  private double[] computeBootstrap(Double[] dd) throws Throwable {
	  double[] result= new double[3];
	  double[] aux= new double[30];
	  double[] average= new double[1000];
	  Random r= new Random();
	  for (int n=0; n<average.length; n++) {
		  for (int k=0; k<30; k++) {
              int rand= r.nextInt(30);
			  aux[k]= dd[rand].doubleValue();
		  }
		  average[n]= this.Average(aux);
	  }
	  double[] ord= this.Ordering(average);
	  for (int n=0; n<ord.length; n++) {
	         MessageKBCT.WriteLogFile("      ord["+n+"]="+ord[n],"Bootstrap1");
	  }
	  result[0]=ord[973];
	  result[1]=ord[25];
	  result[2]=this.Average(ord);
  	  return result;
  }
//------------------------------------------------------------------------------
  private double Average(double[] d) {
    double result=0.0;
    int N= d.length;
    for (int n=0; n<N; n++) {
      result= result + d[n];
    }
    result= result/N;
    return result;
  }
//------------------------------------------------------------------------------
  private double[] Ordering(double[] d) {
	double[] result= new double[d.length];
	int[] order= new int[d.length];
    for (int i=0; i<order.length; i++) {
	    order[i]= i+1;
    }
    for (int i=1; i<order.length; i++) {
            for (int n=0; n<i; n++) {
            	if (d[order[i]-1]>d[order[n]-1]) {
            		for (int m=i; m>n; m--) {
            			order[m]=order[m-1];
            		}
            		order[n]=i+1;
                    break;
            	}
            }
    }
    for (int i=0; i<order.length; i++) {
    	result[i]= d[order[i]-1];
    }
    return result;
  }
//------------------------------------------------------------------------------
  /**
   * Return object with default configuration.
   * @return locale object
   */
  public static LocaleKBCT getLocale() { return locale; }
//------------------------------------------------------------------------------
  /**
   * Save object with default configuration.
   * @param l value assigned to locale object
   */
  public static void setLocale(LocaleKBCT l) { locale= l; }
//------------------------------------------------------------------------------
  /**
   * Return object with current configuration.
   * @return config object
   */
  public static ConfigKBCT getConfig() { return config; }
//------------------------------------------------------------------------------
  /**
   * Return frame which is used in order to show help files for non expert users.
   * @return jbf object
   */
  public static JBeginnerFrame getJB() { return jbf; }
//------------------------------------------------------------------------------
  /**
   * Save frame which is used in order to show help files for non expert users.
   * With current page.
   * @param JB value assigned to jbf object
   */
  public static void setJB(JBeginnerFrame JB) {
	  if (jbf!=null) {
		  jbf.dispose();
	  }
	  jbf= JB;
	  getConfig().SetUser(LocaleKBCT.GetString("Beginner"));
      getJMF().updateHelpMenu(true, false, false);
  }
//------------------------------------------------------------------------------
  /**
   * Return frame which is used in order to show Quick Start tutorials for non expert users.
   * @return jtf object
   */
  public static JTutorialFrame getJT() { return jtf; }
//------------------------------------------------------------------------------
  /**
   * Save frame which is used in order to show Quick Start tutorials for non expert users.
   * With current page.
   * @param JT value assigned to jtf object
   */
  public static void setJT(JTutorialFrame JT) { jtf= JT; }
//------------------------------------------------------------------------------
  /**
   * Return main frame.
   * @return f object
   */
  public static JMainFrame getJMF() { return MainKBCT.f; }
//------------------------------------------------------------------------------
  /**
   * exit all frames and close
   */
  public void hideMainFrame() { 
	if (MainKBCT.f!=null) {
		MainKBCT.f.setVisible(false);
	}
	if (MainKBCT.jbf!=null) {
		MainKBCT.jbf.setVisible(false);
	}
	if (MainKBCT.jtf!=null) {
		MainKBCT.jtf.setVisible(false);
	}
  }
//------------------------------------------------------------------------------
  /**
   * exit all frames and close
   */
  public void close() { 
	if (MainKBCT.f!=null) {
		MainKBCT.f.dispose();
	}
	if (MainKBCT.jbf!=null) {
		MainKBCT.jbf.dispose();
	}
	if (MainKBCT.jtf!=null) {
		MainKBCT.jtf.dispose();
	}
  }
}