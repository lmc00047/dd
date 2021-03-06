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
//                              MessageKBCT.java
//
//
//**********************************************************************
package kbctAux;

import java.awt.Component;
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
import java.util.Vector;

import javax.swing.JOptionPane;

import kbct.JKBCT;
import kbct.LocaleKBCT;
import kbct.MainKBCT;

/**
 * kbctAux.MessageKBCT launch:<br>
 *  ERROR, WARNING, CONFIRMATION, INFORMATION, QUESTION messages<br>
 *
 *@author     Jose Maria Alonso Moral
 *@version    3.0 , 03/08/15
 */
//------------------------------------------------------------------------------
public class MessageKBCT {
    private static String fileLogConsistency= null;
	private static PrintStream LogSelectPartitions= null;
	private static PrintStream LogInduceRules= null;
	private static PrintStream LogConsistency= null;
    private static PrintStream LogSimplify= null;
    private static PrintStream LogOptimization= null;
    private static PrintStream LogCompleteness= null;
    private static PrintStream LogInterpretability= null;
    private static PrintStream LogRanking= null;
    private static PrintStream LogQuality= null;
    private static PrintStream LogQualityCVLRN= null;
    private static PrintStream LogQualityCVTST= null;
    private static PrintStream LogQualityCVassessLRN= null;
    private static PrintStream LogQualityCVassessTST= null;
    private static PrintStream LogQualityExcel= null;
	private static PrintStream LogCheck = null;
	private static PrintStream LogBootstrap1 = null;
	private static PrintStream LogBootstrap2 = null;
	private static PrintStream Log = null;
    
//------------------------------------------------------------------------------
  static {
    try { Log = new PrintStream(new FileOutputStream("exception")); }
    catch( Throwable t ) { Log = null; MessageKBCT.Error(null, t); }
  }
//------------------------------------------------------------------------------
  /**
   * Launch a ERROR message defined by the user.
   */
  public static void Error( Component parent, String title, String message ) {
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    JOptionPane.showConfirmDialog( parent, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE );
  }
//------------------------------------------------------------------------------
  /**
   * Launch a ERROR message describing the catched exception.
   */
  public static void Error( String s, Throwable t ) {
    String message = new String();
    if( s != null )
      message += s;

    message += "\n" + t.getLocalizedMessage();
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    String title= t.getClass().getName();
    JOptionPane.showConfirmDialog( null, message , LocaleKBCT.GetStringNoException(title), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE );
    if( Log != null )
      t.printStackTrace(Log);
  }
//------------------------------------------------------------------------------
  /**
   * Launch a WARNING message defined by the user.
   */
  public static void Warning( Component parent, String title, String message ) {
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    JOptionPane.showConfirmDialog( parent, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE );
  }
//------------------------------------------------------------------------------
  /**
   * Launch a CONFIRM message defined by the user.<br>
   * <p align="left">
   *   option: selected option by default.
   * <ul>
   *   <li>  0 -> Yes </li>
   *   <li>  1 -> No </li>
   * </ul>
   * </p>
   */
  public static int Confirm( Component parent, String message, int option, boolean simplify, boolean reduction, boolean configuration ) {
	    //System.out.println("no entraaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

	  String opt3= null;
    if (simplify)
      opt3= "Cancel";
    else if (reduction)
      opt3= "END";
    else if (configuration)
        opt3= "DefaultValues";

    Object[] options;
    if (opt3!=null) {

      options= new Object[3];
      options[0]= LocaleKBCT.GetString("Yes");
      options[1]= LocaleKBCT.GetString("No");
      options[2]= LocaleKBCT.GetString(opt3);
    } else { //Solo entra aqui

      options= new Object[2];
      options[0]= LocaleKBCT.GetString("Yes");
      options[1]= LocaleKBCT.GetString("No");

    }
    
    //System.out.println("option -> "+option);
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
	return JOptionPane.showOptionDialog(parent, message, LocaleKBCT.GetString("Confirmation"),
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[option]);
	
  }
//------------------------------------------------------------------------------
  /**
   * Launch a CONFIRM message defined by the user.<br>
   * <p align="left">
   *   option: selected option by default.
   * <ul>
   *   <li>  0 -> OK </li>
   *   <li>  1 -> END </li>
   * </ul>
   * </p>
   */
  public static int Confirm( Component parent, String message, int option ) {
	    //System.out.println("no entraaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    
    Object[] options= {LocaleKBCT.GetString("OK"),LocaleKBCT.GetString("ENDInfer")};
    System.out.println(options);
    return JOptionPane.showOptionDialog(parent, message, LocaleKBCT.GetString("Confirmation"),
                               JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                               null, options, options[option]);
  }
//------------------------------------------------------------------------------
  /**
   * Launch a CONFIRM message for data selection in WM rule induction.<br>
   * <p align="left">
   * <ul>
   *   <li>  0 -> YES </li>
   *   <li>  1 -> NO </li>
   * </ul>
   * </p>
   */
  public static int Confirm( Component parent, String message ) {
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showConfirmDialog(parent, message, LocaleKBCT.GetString("Confirmation"), JOptionPane.YES_NO_OPTION);
  }
//------------------------------------------------------------------------------
  /**
   * Launch a INFORMATION message defined by the user.
   */
  public static int Information( Component parentComponent, String message ) {
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    if (message.contains(LocaleKBCT.GetString("Processing")+" ..."+"\n"+"   "+LocaleKBCT.GetString("TakeLongTime")+"\n"+"... "+LocaleKBCT.GetString("PleaseWait")+" ...")) { 
    	Object[] options = { LocaleKBCT.GetString("START") };
    	return JOptionPane.showOptionDialog(parentComponent, message, LocaleKBCT.GetString("Information"),
    	        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
    	        null, options, options[0]);
    } else
        return JOptionPane.showConfirmDialog( parentComponent, message, LocaleKBCT.GetString("Information"), JOptionPane.DEFAULT_OPTION , JOptionPane.INFORMATION_MESSAGE );
  }
//------------------------------------------------------------------------------
  /**
   * Launch a QUESTION message defined by the user.<br>
   * For example, it is used in order to ask how many variables will be used as outputs.
   */

  public static String DataQuestion( Component parentComponent, String message) {
	  //System.out.println("no uyvhg entraaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showInputDialog( parentComponent, message, LocaleKBCT.GetString("Question"), JOptionPane.QUESTION_MESSAGE);
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with two options to make induction:<br>
   * <ul>
   *    <li> Partitions: launch a window with the list of variables. </li>
   *    <li> Rules: launch a window with the options of the algorithm. </li>
   * </ul>
   */
  public static int InduceData( Component parent ) {
    Object[] options = { LocaleKBCT.GetString("Partitions"), LocaleKBCT.GetString("Rules"), LocaleKBCT.GetString("KBCT"), LocaleKBCT.GetString("Cancel") };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, LocaleKBCT.GetString("Choose_one_option"),
                               LocaleKBCT.GetString("Data")+": "+LocaleKBCT.GetString("Induce"),
                               JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                               null, options, options[0]);
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of variables. When the user selects one,
   * the window with information about this variable is displayed.
   */
  public static Object InducePartitions( Component parentComponent, JKBCT kbct_Data, JKBCT kbct) {
      int InMin= Math.min(kbct.GetNbInputs(),kbct_Data.GetNbInputs());
      int OutMin= Math.min(kbct.GetNbOutputs(),kbct_Data.GetNbOutputs());
      Object[] possibleValues = new Object[InMin+OutMin+1];
      int output=0;
      for (int n=0; n<possibleValues.length-1; n++) {
        String NameExpert= null;
        String NameData= null;
        if (n<InMin) {
           NameExpert= kbct.GetInput(n+1).GetName();
           NameData= kbct_Data.GetInput(n+1).GetName();
        } else {
           output++;
           NameExpert= kbct.GetOutput(output).GetName();
           NameData= kbct_Data.GetOutput(output).GetName();
        }
        if (NameExpert.equals(NameData))
            possibleValues[n]=NameData;
        else
            possibleValues[n]=NameData+" => "+NameExpert;
      }
      possibleValues[possibleValues.length-1]= LocaleKBCT.GetString("AllInputs");
      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
                   LocaleKBCT.GetString("Choose_one_variable"), LocaleKBCT.GetString("Induce_Partitions"),
                   JOptionPane.QUESTION_MESSAGE, null,
                   possibleValues, possibleValues[0]);
      return selectedValue;
 }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of algorithms for partition induction:
   * <ul>
   *    <li> regular: uniform fuzzy partitions </li>
   *    <li> hfp: Hierarchical Fuzzy Partitioning </li>
   *    <li> kmeans </li>
   * </ul>
   */
  public static Object InductionType( Component parentComponent ) {
	  Object[] possibleValues= new Object[3];
	  possibleValues[0]="regular";
      possibleValues[1]="hfp";
      possibleValues[2]="kmeans";
      
      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
        LocaleKBCT.GetString("Choose_one_algorithm_for_partition_induction"),
        LocaleKBCT.GetString("InducePartitions"),
        JOptionPane.QUESTION_MESSAGE,
        null,
        possibleValues,
        possibleValues[0]);
      return selectedValue;
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of allowed NbLabels for partition induction: [2, 9]
   */
  public static Object InductionNbLabels( Component parentComponent ) {
	  Object[] possibleValues= new Object[8];
      for (int n=0; n<8; n++) {
    	  possibleValues[n]=n+2;
      }
      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
        LocaleKBCT.GetString("Choose_number_of_labels_for_partition_induction"),
        LocaleKBCT.GetString("InducePartitions"),
        JOptionPane.QUESTION_MESSAGE,
        null,
        possibleValues,
        possibleValues[0]);
      return selectedValue;
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of algorithms for rule induction:
   * <ul>
   *    <li> FPA: Fast Prototyping Algorithm (Default)</li>
   *    <li> Fuzzy Decision Trees </li>
   *    <li> Wang and Mendel </li>
   * </ul>
   */
  public static Object InduceRules( Component parentComponent, boolean Prototype ) {
	  Object[] possibleValues;
	  if (Prototype)
		  possibleValues= new Object[4];
	  else
		  possibleValues= new Object[3];

      possibleValues[0]="Fuzzy Decision Trees";
	  possibleValues[1]="Wang and Mendel";
      possibleValues[2]="Fast Prototyping Algorithm";
      
	  if (Prototype)
          possibleValues[3]=LocaleKBCT.GetString("PrototypeRules");
	  
      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
        LocaleKBCT.GetString("Choose_one_algorithm_for_rule_induction"),
        LocaleKBCT.GetString("Induce_Rules"),
        JOptionPane.QUESTION_MESSAGE,
        null,
        possibleValues,
        possibleValues[0]);
      return selectedValue;
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of Simplification options:
   * <ul>
   *    <li> Reduce Data Base ( Linguistic Variables )</li>
   *    <li> Reduce Rule Base ( Linguistic Analysis ) </li>
   *    <li> Simplify Induced Rules </li>
   * </ul>
   */
  public static Object SimplifyOptions( Component parentComponent) {
      Object selectedValue;
      Object[] possibleValues = new Object[2];
      possibleValues[0]=LocaleKBCT.GetString("ReduceRuleBase");
      possibleValues[1]=LocaleKBCT.GetString("ReduceDataBase");
      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      selectedValue= JOptionPane.showInputDialog(parentComponent,
            LocaleKBCT.GetString("Choose_one_option"),
            LocaleKBCT.GetString("SimplifyMsg"),
            JOptionPane.QUESTION_MESSAGE,
            null,
            possibleValues,
            possibleValues[0]);
      return selectedValue;
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of Completeness options:
   * <ul>
   *    <li> Rule Base Completeness Analysis</li>
   *    <li> Complete the Rule Base</li>
   * </ul>
   */
  public static Object CompletenessOptions(Component parentComponent) {
      Object[] possibleValues = new Object[2];
      possibleValues[0]=LocaleKBCT.GetString("RuleBaseCompletenessAnalysis");
      possibleValues[1]=LocaleKBCT.GetString("CompleteRuleBase");
      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
            LocaleKBCT.GetString("Choose_one_option"),
            LocaleKBCT.GetString("CompletenessMsg"),
            JOptionPane.QUESTION_MESSAGE,
            null,
            possibleValues,
            possibleValues[0]);
      return selectedValue;
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of metrics for fingrams generation.
   */
  public static Object SelectFingramsMetric(Component parentComponent) {
      /*Object[] possibleValues = new Object[5];
      possibleValues[0]= "MS";
      possibleValues[1]= "MSFD";
      possibleValues[2]= "MA";
      possibleValues[3]= "MAFD";
      possibleValues[4]= LocaleKBCT.GetString("All");

      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
                   LocaleKBCT.GetString("Choose_one_metric"), LocaleKBCT.GetString("Fingrams"),
                   JOptionPane.QUESTION_MESSAGE, null,
                   possibleValues, possibleValues[0]);
      return selectedValue;*/
	  Object[] possibleValues = new Object[3];
      possibleValues[0]= LocaleKBCT.GetString("MS");
      possibleValues[1]= LocaleKBCT.GetString("MSFD");
      possibleValues[2]= LocaleKBCT.GetString("MA");

      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
                   LocaleKBCT.GetString("Choose_one_metric"), LocaleKBCT.GetString("Fingrams"),
                   JOptionPane.QUESTION_MESSAGE, null,
                   possibleValues, possibleValues[0]);
      return selectedValue;	  
      //return "MS";
 }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of Graphviz layout methods for fingrams graphical representation.
   */
  public static Object SelectFingramsLayout(Component parentComponent) {
      Object[] possibleValues = new Object[6];
      possibleValues[0]= "neato";
      possibleValues[1]= "fdp";
      possibleValues[2]= "sfdp";
      possibleValues[3]= "dot";
      possibleValues[4]= "twopi";
      possibleValues[5]= "circo";

      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
                   LocaleKBCT.GetString("Choose_one_graph_layout"), LocaleKBCT.GetString("Fingrams"),
                   JOptionPane.QUESTION_MESSAGE, null,
                   possibleValues, possibleValues[0]);
      return selectedValue;
 }
//------------------------------------------------------------------------------
  /**
   * Display a window with two options to make induction:<br>
   * <ul>
   *    <li> Partitions: launch a window with the list of variables. </li>
   *    <li> Rules: launch a window with the options of the algorithm. </li>
   * </ul>
   */
  public static int ChooseDataFile( Component parent, String OrigDataFile, String CurrentDataFile ) {
    Object[] options = { OrigDataFile, CurrentDataFile };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, LocaleKBCT.GetString("Choose_one_data_file_To_generate_FIS"),
                               LocaleKBCT.GetString("GenerateFIS"),
                               JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                               null, options, options[0]);
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with one message and two options:<br>
   * <ul>
   *    <li> Supervised Mode. </li>
   *    <li> Automatic Mode. </li>
   * </ul>
   */
  public static int SelectMode( Component parent, String message ) {
    Object[] options = { LocaleKBCT.GetString("Supervised"), LocaleKBCT.GetString("Automatic") };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, message, LocaleKBCT.GetString("Information"),
                               JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                               null, options, options[1]);
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with one message and two options:<br>
   * <ul>
   *    <li> Remove: remove selected element (variable or rule). </li>
   *    <li> Cancel: Do nothing. </li>
   * </ul>
   */
  public static int ReduceDataBase( Component parent, String message, boolean GroupLabels ) {
    String opt1= "Remove";
    if (GroupLabels)
      opt1= "Group";
    Object[] options = { LocaleKBCT.GetString(opt1), LocaleKBCT.GetString("Cancel") };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, message, LocaleKBCT.GetString("Information"),
                               JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                               null, options, options[0]);
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with one message and three options:<br>
   * <ul>
   *    <li> Remove Rule 1. </li>
   *    <li> Remove Rule 2. </li>
   *    <li> Cancel: Do nothing. </li>
   * </ul>
   */
  public static int RemoveRule2( Component parent, String message, int Rule1, int Rule2 ) {
    Object[] options = { LocaleKBCT.GetString("Remove")+" "+LocaleKBCT.GetString("Rule")+" "+Rule1,
                       LocaleKBCT.GetString("Remove")+" "+LocaleKBCT.GetString("Rule")+" "+Rule2,
                       LocaleKBCT.GetString("Cancel") };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, message, LocaleKBCT.GetString("Information"),
                               JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                               null, options, options[2]);
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with one message and four options:<br>
   * <ul>
   *    <li> Define conclusion. </li>
   *    <li> Remove Rule. </li>
   *    <li> Cancel: Do nothing. </li>
   * </ul>
   */
  public static int SolveConclusionNotDefined( Component parent, String message ) {
    Object[] options = { LocaleKBCT.GetString("DefineConclusion")+" "+LocaleKBCT.GetString("Rule").toLowerCase(), LocaleKBCT.GetString("RemoveThe")+" "+LocaleKBCT.GetString("Rule").toLowerCase(), LocaleKBCT.GetString("Cancel") };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, message, LocaleKBCT.GetString("Information"),
                               JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                               null, options, options[0]);
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with one message and four options:<br>
   * <ul>
   *    <li> Remove Rule 1. </li>
   *    <li> Remove Rule 2. </li>
   *    <li> Change rules. </li>
   *    <li> Cancel: Do nothing. </li>
   * </ul>
   */
  public static int SolveSamePremiseDifferentConclusions( Component parent, String message ) {
    Object[] options = { LocaleKBCT.GetString("SelectConclusion"), LocaleKBCT.GetString("Cancel") };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, message, LocaleKBCT.GetString("Information"),
                               JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                               null, options, options[0]);
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with one message and four options:<br>
   * <ul>
   *    <li> Remove Rule 1. </li>
   *    <li> Remove Rule 2. </li>
   *    <li> Change rules. </li>
   *    <li> Cancel: Do nothing. </li>
   * </ul>
   */
  public static int SolveInclusionDifferentConclusions( Component parent, String message, int Rule1, int Rule2 ) {
    Object[] options = { LocaleKBCT.GetString("Remove")+" "+LocaleKBCT.GetString("Rule")+" "+Rule1,
                       LocaleKBCT.GetString("Remove")+" "+LocaleKBCT.GetString("Rule")+" "+Rule2,
                       ""+2,
                       LocaleKBCT.GetString("Cancel") };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, message, LocaleKBCT.GetString("Information"),
                               JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                               null, options, options[2]);
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with two message and four options:<br>
   * <ul>
   *    <li> Change rules. </li>
   *    <li> Cancel: Do nothing. </li>
   * </ul>
   */
  public static int SolveNoEmptyIntersectionDifferentConclusions( Component parent, String message ) {
    Object[] options = { ""+3, LocaleKBCT.GetString("Cancel") };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, message, LocaleKBCT.GetString("Information"),
                               JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                               null, options, options[0]);
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with two message and four options:<br>
   * <ul>
   *    <li> Change rules. </li>
   *    <li> Cancel: Do nothing. </li>
   * </ul>
   */
  public static int SolveNoEmptyIntersectionSameConclusions( Component parent, String message ) {
    Object[] options = { ""+2, LocaleKBCT.GetString("Cancel") };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, message, LocaleKBCT.GetString("Information"),
                               JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                               null, options, options[0]);
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of data from data file
   */
  public static Object SelectDataValue( Component parentComponent, int NbData ) {
      Object[] possibleValues = new Object[NbData];
      for (int n=0; n<NbData; n++)
        possibleValues[n]=new Integer(n+1);

      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
        LocaleKBCT.GetString("Choose_one_data_value_from_data_file"),
        LocaleKBCT.GetString("setDataValue"),
        JOptionPane.QUESTION_MESSAGE,
        null,
        possibleValues,
        possibleValues[0]);
      return selectedValue;
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of data from data file
   */
  public static Object SelectSampleForInstanceBasedFingrams( Component parentComponent, int NbData ) {
      Object[] possibleValues = new Object[NbData];
      for (int n=0; n<NbData; n++)
        possibleValues[n]=new Integer(n+1);

      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
        LocaleKBCT.GetString("SelectSampleForInstanceBasedFingrams"),
        LocaleKBCT.GetString("setDataValue"),
        JOptionPane.QUESTION_MESSAGE,
        null,
        possibleValues,
        possibleValues[0]);
      return selectedValue;
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of conjunction options in FisPro
   */
  public static Object SelectFisProConjunction( Component parentComponent ) {
      Object[] possibleValues = new Object[3];
      possibleValues[0]=LocaleKBCT.GetString("min");
      possibleValues[1]=LocaleKBCT.GetString("prod");
      possibleValues[2]=LocaleKBCT.GetString("luka");

      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
        LocaleKBCT.GetString("Choose_one_Fispro_conjunction"),
        LocaleKBCT.GetString("setFisProConjunction"),
        JOptionPane.QUESTION_MESSAGE,
        null,
        possibleValues,
        possibleValues[0]);
      return selectedValue;
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of disjunction options in FisPro
   */
  public static Object SelectFisProDisjunction( Component parentComponent ) {
      Object[] possibleValues = new Object[2];
      possibleValues[0]=LocaleKBCT.GetString("max");
      possibleValues[1]=LocaleKBCT.GetString("sum");

      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
        LocaleKBCT.GetString("Choose_one_Fispro_disjunction"),
        LocaleKBCT.GetString("setFisProDisjunction"),
        JOptionPane.QUESTION_MESSAGE,
        null,
        possibleValues,
        possibleValues[0]);
      return selectedValue;
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of disjunction options in FisPro
   */
  public static Object SelectFisProDefuzzification( Component parentComponent, String OutputNature ) {
      Object[] possibleValues=null;
      if (OutputNature.equals("Fuzzy")) {
          possibleValues = new Object[3];
          possibleValues[0]=LocaleKBCT.GetString("area");
          possibleValues[1]=LocaleKBCT.GetString("MeanMax");
          possibleValues[2]=LocaleKBCT.GetString("sugeno");
      } else {
          possibleValues = new Object[2];
          possibleValues[0]=LocaleKBCT.GetString("MaxCrisp");
          possibleValues[1]=LocaleKBCT.GetString("sugeno");
      }

      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
        LocaleKBCT.GetString("Choose_one_Fispro_defuzzification"),
        LocaleKBCT.GetString("setFisProDefuzzification"),
        JOptionPane.QUESTION_MESSAGE,
        null,
        possibleValues,
        possibleValues[0]);
      return selectedValue;
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of data variables from data file
   */
  public static Object SelectDataVar( Component parentComponent, String msg, int NbData ) {
	  Object[] possibleValues = new Object[NbData];
      for (int n=0; n<NbData; n++)
        possibleValues[n]=new Integer(n+1);

      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
        msg,
        LocaleKBCT.GetString("linkDataVars"),
        JOptionPane.QUESTION_MESSAGE,
        null,
        possibleValues,
        possibleValues[0]);
      return selectedValue;
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with two options to make quality evaluation:<br>
   * <ul>
   *    <li> Accuracy: launch a window with KB quality according to several accuracy indexes. </li>
   *    <li> Interpretability: launch a window with KB quality according to an interpretability index. </li>
   * </ul>
   */
  //Aquiiiiiiiiiiiiiiiiiii opciones
  public static int QualityOptions( Component parent ) {
    Object[] options = { LocaleKBCT.GetString("Accuracy"), LocaleKBCT.GetString("Interpretability"), LocaleKBCT.GetString("Cancel") };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, LocaleKBCT.GetString("Choose_one_option"),
                               LocaleKBCT.GetString("QualityMsg"),
                               JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                               null, options, options[0]);
  }
//------------------------------------------------------------------------------
  /**
   * Build a log file
   */
  public static void BuildLogFile(String file_name, String IKBFile, String ExpertFile, String DataFile, String option) {
    try { 
        if (option.equals("Check")) {
        	LogCheck = new PrintStream(new FileOutputStream(file_name, true));
        } else if (option.equals("Bootstrap1")) {
        	LogBootstrap1 = new PrintStream(new FileOutputStream(file_name, true));
        } else if (option.equals("Bootstrap2")) {
        	LogBootstrap2 = new PrintStream(new FileOutputStream(file_name, true));
        } else if (option.equals("SelectPartitions")) {
        	if (MainKBCT.getConfig().GetTESTautomatic())
        	    LogSelectPartitions = new PrintStream(new FileOutputStream(file_name, true));
        	else
        	    LogSelectPartitions = new PrintStream(new FileOutputStream(file_name, false));

        } else if (option.equals("InduceRules")) {
        	if (MainKBCT.getConfig().GetTESTautomatic())
        	    LogInduceRules = new PrintStream(new FileOutputStream(file_name, true));
        	else
            	LogInduceRules = new PrintStream(new FileOutputStream(file_name, false));

        } else if (option.equals("Consistency")) {
            fileLogConsistency= file_name;
        	if (MainKBCT.getConfig().GetTESTautomatic())
        	    LogConsistency = new PrintStream(new FileOutputStream(fileLogConsistency, true));
        	else
            	LogConsistency = new PrintStream(new FileOutputStream(fileLogConsistency, false));
        		
        } else if (option.equals("Simplify")) {
        	if (MainKBCT.getConfig().GetTESTautomatic())
        	    LogSimplify = new PrintStream(new FileOutputStream(file_name, true));
        	else
            	LogSimplify = new PrintStream(new FileOutputStream(file_name, false));
        		
        } else if (option.equals("Optimization")) {
        	if (MainKBCT.getConfig().GetTESTautomatic())
        	    LogOptimization = new PrintStream(new FileOutputStream(file_name, true));
        	else
            	LogOptimization = new PrintStream(new FileOutputStream(file_name, false));
        		
        } else if (option.equals("Completeness")) {
        	if (MainKBCT.getConfig().GetTESTautomatic())
        	    LogCompleteness = new PrintStream(new FileOutputStream(file_name, true));
        	else
            	LogCompleteness = new PrintStream(new FileOutputStream(file_name, false));

        } else if (option.equals("RuleRanking")) {
        	if (MainKBCT.getConfig().GetTESTautomatic())
        	    LogRanking = new PrintStream(new FileOutputStream(file_name, true));
        	else
            	LogRanking = new PrintStream(new FileOutputStream(file_name, false));
        		
        } else if (option.equals("Interpretability")) {
        	if (MainKBCT.getConfig().GetTESTautomatic())
        	    LogInterpretability = new PrintStream(new FileOutputStream(file_name, true));
        	else
            	LogInterpretability = new PrintStream(new FileOutputStream(file_name, false));
        		
        } else if (option.equals("Quality")) {
            File fex= new File(ExpertFile);
            String exName=fex.getName();
            int begin= exName.indexOf("_");
            String cvName="";
            if (exName.contains("CV")) {
        	  cvName=file_name+"_CV_"+exName.substring(0,begin)+exName.substring(begin+4,exName.length()-7);
        	  LogQualityCVLRN = new PrintStream(new FileOutputStream(cvName+"_LRN", true));
        	  LogQualityCVTST = new PrintStream(new FileOutputStream(cvName+"_TST", true));
            }
        	if (MainKBCT.getConfig().GetTESTautomatic())
        	    LogQuality = new PrintStream(new FileOutputStream(file_name, true));
        	else
            	LogQuality = new PrintStream(new FileOutputStream(file_name, false));
        }
        if (!option.equals("Interpretability")) {
          WriteLogFile("++++++++++++++++++++++++++++++++++", option);
          Date d= new Date(System.currentTimeMillis());
          WriteLogFile(DateFormat.getDateTimeInstance().format(d), option);
          if (!option.equals("Check")) {
            WriteLogFile(LocaleKBCT.GetString("TheKBCTFile")+"= "+IKBFile, option);
            WriteLogFile(LocaleKBCT.GetString("FilterKBFile")+"= "+ExpertFile, option);
            if (DataFile!=null)
              WriteLogFile(LocaleKBCT.GetString("TheDataFile")+"= "+DataFile, option);
          }
        }
    } catch( Throwable t ) { 
        if (option.equals("Check")) {
    	    LogCheck = null;
        } else if (option.equals("Bootstrap1")) {
    	    LogBootstrap1 = null;
        } else if (option.equals("Bootstrap2")) {
    	    LogBootstrap2 = null;
        } else if (option.equals("SelectPartitions")) {
    	    LogSelectPartitions = null;
        } else if (option.equals("InduceRules")) {
      		LogInduceRules = null;
        } else if (option.equals("Consistency")) {
      		LogConsistency = null;
        } else if (option.equals("Simplify")) {
      		LogSimplify = null;
        } else if (option.equals("Optimization")) {
      		LogOptimization = null;
        } else if (option.equals("Completeness")) {
      		LogCompleteness = null;
        } else if (option.equals("RuleRanking")) {
      		LogRanking = null;
        } else if (option.equals("Interpretability")) {
      		LogInterpretability = null;
        } else if (option.equals("Quality")) {
      		LogQuality = null;
        }
    	t.printStackTrace();
    	MessageKBCT.Error(null, t);
    }
  }
//------------------------------------------------------------------------------
  /**
   * Write a message to the end of the log file
   */
  public static void WriteLogFile(String message, String option) {
      if ( (option.equals("Check")) && (LogCheck != null) ) {
	      LogCheck.println(message);
          LogCheck.flush();
      } else if ( (option.equals("Bootstrap1")) && (LogBootstrap1 != null) ) {
	      LogBootstrap1.println(message);
          LogBootstrap1.flush();
      } else if ( (option.equals("Bootstrap2")) && (LogBootstrap2 != null) ) {
	      LogBootstrap2.println(message);
          LogBootstrap2.flush();
      } else if ( (option.equals("SelectPartitions")) && (LogSelectPartitions != null) ) {
	      LogSelectPartitions.println(message);
          LogSelectPartitions.flush();
      } else if ( (option.equals("InduceRules")) && (LogInduceRules != null) ) {
          LogInduceRules.println(message);
          LogInduceRules.flush();
      } else if ( (option.equals("Consistency")) && (LogConsistency != null) ) {
          LogConsistency.println(message);
          LogConsistency.flush();
      } else if ( (option.equals("Simplify")) && (LogSimplify != null) ) {
   	      LogSimplify.println(message);
          LogSimplify.flush();
      } else if ( (option.equals("Optimization")) && (LogOptimization != null) ) {
   	      LogOptimization.println(message);
          LogOptimization.flush();
      } else if ( (option.equals("Completeness")) && (LogCompleteness != null) ) {
   	      LogCompleteness.println(message);
          LogCompleteness.flush();
      } else if ( (option.equals("RuleRanking")) && (LogRanking != null) ) {
    	  LogRanking.println(message);
          LogRanking.flush();
      } else if ( (option.equals("Interpretability")) && (LogInterpretability != null) ) {
    	  LogInterpretability.println(message);
          LogInterpretability.flush();
      } else if ( (option.equals("Quality")) && (LogQuality != null) ) {
  	      LogQuality.println(message);
          LogQuality.flush();
      }
  }
//------------------------------------------------------------------------------
  /**
   * Close the log file
   */
  public static void CloseLogFile(String option) {
      if (option.equals("Check")) {
          if (LogCheck!=null) {
    	      LogCheck.close();
              LogCheck= null;
          }
      } else if (option.equals("Bootstrap1")) {
          if (LogBootstrap1!=null) {
  	          LogBootstrap1.close();
              LogBootstrap1= null;
          }
      } else if (option.equals("Bootstrap2")) {
          if (LogBootstrap2!=null) {
 	          LogBootstrap2.close();
              LogBootstrap2= null;
          }
      } else if (option.equals("SelectPartitions")) {
          if (LogSelectPartitions!=null) {
	          LogSelectPartitions.close();
              LogSelectPartitions= null;
          }
      } else if (option.equals("InduceRules")) {
          if (LogInduceRules!=null) {
              LogInduceRules.close();
              LogInduceRules= null;
          }
      } else if (option.equals("Consistency")) {
          if (LogConsistency!=null) {
              LogConsistency.close();
              LogConsistency= null;
          }
      } else if (option.equals("Simplify")) {
          if (LogSimplify!=null) {
              LogSimplify.close();
              LogSimplify= null;
          }
      } else if (option.equals("Optimization")) {
          if (LogOptimization!=null) {
              LogOptimization.close();
              LogOptimization= null;
          }
      } else if (option.equals("Completeness")) {
          if (LogCompleteness!=null) {
              LogCompleteness.close();
              LogCompleteness= null;
          }
      } else if (option.equals("RuleRanking")) {
          if (LogRanking!=null) {
              LogRanking.close();
              LogRanking= null;
          }
      } else if (option.equals("Interpretability")) {
          if (LogInterpretability!=null) {
              LogInterpretability.close();
              LogInterpretability= null;
          }
      } else if (option.equals("Quality")) {
          if (LogQualityCVLRN !=null) {
      	      LogQualityCVLRN.close();
              LogQualityCVLRN= null;
            }
            if (LogQualityCVTST !=null) {
        	    LogQualityCVTST.close();
                LogQualityCVTST= null;
            }
            if (LogQuality !=null) {
                LogQuality.close();
                LogQuality= null;
            }
      } else if (option.equals("CV")) {
          if (LogQualityCVassessLRN !=null) {
              LogQualityCVassessLRN.close();
              LogQualityCVassessLRN= null;
          }
          if (LogQualityCVassessTST !=null) {
              LogQualityCVassessTST.close();
              LogQualityCVassessTST= null;
          }
      } else if (option.equals("QualityExcel")) {
          if (LogQualityExcel !=null) {
              LogQualityExcel.close();
              LogQualityExcel= null;
          }
      }
  }
//------------------------------------------------------------------------------
  /**
   * Remove the last iteration message
   */
  public static void RemoveLastIteration() {
      try {
    	  LineNumberReader lnr= new LineNumberReader(new InputStreamReader(new FileInputStream(fileLogConsistency)));
          String l;
          Vector<String> v= new Vector<String>();
    	  while((l= lnr.readLine())!=null) {
            v.add(l);
          }
          Object[] obj= v.toArray();
          CloseLogFile("Consistency");
      	  LogConsistency = new PrintStream(new FileOutputStream(fileLogConsistency, false));
          for (int n=0; n<obj.length-2; n++) {
               LogConsistency.println(obj[n]);        	  
          }
          LogConsistency.flush();
          CloseLogFile("Consistency");
      	  LogConsistency = new PrintStream(new FileOutputStream(fileLogConsistency, true));
      } catch (Throwable t) {
     	  //t.printStackTrace();
    	  MessageKBCT.Error(null, t);
      }
  }
//------------------------------------------------------------------------------
  /**
   * Write a message to the end of Quality log file
   */
  public static void WriteQualityLogFileCV(double quality, double K, double Acc, double Int, boolean LRN) {
      if ( (LRN) && (LogQualityCVLRN != null) ) {
	    DecimalFormat df= new DecimalFormat();
	    df.setMaximumFractionDigits(MainKBCT.getConfig().GetNumberOfDecimals());
	    df.setMinimumFractionDigits(MainKBCT.getConfig().GetNumberOfDecimals());
	    DecimalFormatSymbols dfs= df.getDecimalFormatSymbols();
	    dfs.setDecimalSeparator((new String(".").charAt(0)));
	    df.setDecimalFormatSymbols(dfs);
	    df.setGroupingSize(20);
	    LogQualityCVLRN.println("Q="+df.format(quality)+", K="+df.format(K)+", A="+df.format(Acc)+", I="+df.format(Int));
        LogQualityCVLRN.flush();
      } else if ( (!LRN) && (LogQualityCVTST != null) ) {
  	    DecimalFormat df= new DecimalFormat();
	    df.setMaximumFractionDigits(MainKBCT.getConfig().GetNumberOfDecimals());
	    df.setMinimumFractionDigits(MainKBCT.getConfig().GetNumberOfDecimals());
	    DecimalFormatSymbols dfs= df.getDecimalFormatSymbols();
	    dfs.setDecimalSeparator((new String(".").charAt(0)));
	    df.setDecimalFormatSymbols(dfs);
	    df.setGroupingSize(20);
	    LogQualityCVTST.println("Q="+df.format(quality)+", K="+df.format(K)+", A="+df.format(Acc)+", I="+df.format(Int));
        LogQualityCVTST.flush();
      }
  }
//------------------------------------------------------------------------------
  /**
   * Build a log file for Simplification Process
   */
  public static void BuildCVLogFile(String file_name) {
    try { 
    	LogQualityCVassessLRN = new PrintStream(new FileOutputStream(file_name+"_LRN", true));
    	LogQualityCVassessTST = new PrintStream(new FileOutputStream(file_name+"_TST", true));
    } catch( Throwable t ) { 
  		LogQualityCVassessLRN = null;
  		LogQualityCVassessTST = null;
  		MessageKBCT.Error(null, t);
    }
  }
//------------------------------------------------------------------------------
  /**
   * Write a message to the end of Simplify log file
   */
  public static void WriteCVLogFile(String QualityCV, String options, String name, String opt, int Nb) {
	  DecimalFormat df= new DecimalFormat();
	  df.setMaximumFractionDigits(MainKBCT.getConfig().GetNumberOfDecimals());
	  df.setMinimumFractionDigits(MainKBCT.getConfig().GetNumberOfDecimals());
	  DecimalFormatSymbols dfs= df.getDecimalFormatSymbols();
	  dfs.setDecimalSeparator((new String(".").charAt(0)));
	  df.setDecimalFormatSymbols(dfs);
	  df.setGroupingSize(20);
	  double quality=0, K=0, Acc=0, Int=0;
      try {
        Vector<Double> vQ= new Vector<Double>();
        Vector<Double> vK= new Vector<Double>();
        Vector<Double> vA= new Vector<Double>();
        Vector<Double> vI= new Vector<Double>();
        for (int k=0; k<Nb; k++) {
          String fileName= QualityCV+"_CV"+k+"_"+options+"_"+name+"_"+options+"_"+opt;
          //System.out.println("fileName="+fileName);
          File file = new File(fileName);
    	  LineNumberReader lnr= new LineNumberReader(new InputStreamReader(new FileInputStream(file)));
          String l;
          while((l= lnr.readLine())!=null) {
        	  Double dQ= new Double(l.substring(2,7));
        	  vQ.add(dQ);
        	  Double dK= new Double(l.substring(11,16));
        	  vK.add(dK);
        	  Double dA= new Double(l.substring(20,25));
        	  vA.add(dA);
        	  Double dI= new Double(l.substring(29,34));
        	  vI.add(dI);
          }
        }
        Object[] QQ= vQ.toArray();
        Object[] KK= vK.toArray();
        Object[] AA= vA.toArray();
        Object[] II= vI.toArray();
        int lim=QQ.length;
        for (int n=0; n<lim; n++) {
      	   quality=quality+((Double)QQ[n]).doubleValue();
      	   K=K+((Double)KK[n]).doubleValue();
      	   Acc=Acc+((Double)AA[n]).doubleValue();
      	   Int=Int+((Double)II[n]).doubleValue();
        }
        quality= quality/lim;
        K= K/lim;
        Acc= Acc/lim;
        Int= Int/lim;
      } catch (Throwable t) {
     	  //t.printStackTrace();
    	  MessageKBCT.Error(null, t);
      }
      String message=options+" ->"+" Q="+df.format(quality)+", K="+df.format(K)+", A="+df.format(Acc)+", I="+df.format(Int);
      if (opt.equals("LRN")) {
          LogQualityCVassessLRN.println(message);
          LogQualityCVassessLRN.flush();
      } else {
          LogQualityCVassessTST.println(message);
          LogQualityCVassessTST.flush();
      }
  }
//------------------------------------------------------------------------------
  /**
   * Write a message to the end of Simplify log file
   */
  public static void SelectBestOption(String EvalCV) {
      String BestOption="?";
	  double BestQuality=0, BestAcc=0, BestInt=0;
      File file = new File(EvalCV);
      try {
          LineNumberReader lnr= new LineNumberReader(new InputStreamReader(new FileInputStream(file)));
          String l;
          Vector<String> vO= new Vector<String>();
          Vector<Double> vQ= new Vector<Double>();
          Vector<Double> vA= new Vector<Double>();
          Vector<Double> vI= new Vector<Double>();
          while((l= lnr.readLine())!=null) {
              int index= l.indexOf("->");
              String opt= l.substring(0,index-1);
              vO.add(opt);
              index= l.indexOf("Q=");
        	  Double dQ= new Double(l.substring(index+2,index+7));
        	  vQ.add(dQ);
        	  Double dA= new Double(l.substring(index+20,index+25));
        	  vA.add(dA);
        	  Double dI= new Double(l.substring(index+29,index+34));
        	  vI.add(dI);
          }
          Object[] OO= vO.toArray();
          Object[] QQ= vQ.toArray();
          Object[] AA= vA.toArray();
          Object[] II= vI.toArray();
          int lim=QQ.length;
          for (int n=0; n<lim; n++) {
        	   if ( (n==0) || 
        			((n>0) && ( ((Double)QQ[n] > BestQuality) || 
           				 (((Double)QQ[n] == BestQuality) && ((Double)II[n] > BestInt)) )) ) {
        		   BestOption=(String)OO[n];
        	       BestQuality= (Double)QQ[n];
        	       BestAcc= (Double)AA[n];
        	       BestInt= (Double)II[n];
        	   } 
          }
      	  PrintStream ps= new PrintStream(new FileOutputStream(EvalCV+"_selection", true));
          ps.println("The best option is "+BestOption);
          ps.println("  -> Quality= "+BestQuality);
          ps.println("  -> Accuracy= "+BestAcc);
          ps.println("  -> Interpretability= "+BestInt);
          ps.flush();
          ps.close();
      } catch (Throwable t) {
     	  //t.printStackTrace();
    	  MessageKBCT.Error(null, t);
      }
  }
//------------------------------------------------------------------------------
  /**
   * Build a quality log file for Excel
   */
  public static void BuildExcelLogFile(String file_name, String opt) {
    try { 
    	LogQualityExcel = new PrintStream(new FileOutputStream(file_name+"_"+opt+".xls", true));
    } catch( Throwable t ) { 
  		LogQualityExcel = null;
  		MessageKBCT.Error(null, t);
    }
  }
//------------------------------------------------------------------------------
  /**
   * Write a message to the end of quality log file for Excel
   */
  public static void WriteExcelLogFile(String QualityCV, String opt, int Nb) {
      //System.out.println("Q="+QualityCV+"  opt="+opt+"  Nb="+Nb);
	  DecimalFormat df= new DecimalFormat();
	  df.setMaximumFractionDigits(MainKBCT.getConfig().GetNumberOfDecimals());
	  df.setMinimumFractionDigits(MainKBCT.getConfig().GetNumberOfDecimals());
	  DecimalFormatSymbols dfs= df.getDecimalFormatSymbols();
	  dfs.setDecimalSeparator((new String(",").charAt(0)));
	  df.setDecimalFormatSymbols(dfs);
	  df.setGroupingSize(20);
	  double[] errorLRN= new double[Nb];
	  double[] ambiguityLRN= new double[Nb];
	  double[] unclassifiedLRN= new double[Nb];
	  double[] performanceLRN= new double[Nb];
	  double[] errorTST= new double[Nb];
	  double[] ambiguityTST= new double[Nb];
	  double[] unclassifiedTST= new double[Nb];
	  double[] performanceTST= new double[Nb];
	  double[] rules= new double[Nb];
	  double[] premises= new double[Nb];
	  double[] rbdim= new double[Nb];
	  double[] pr1in= new double[Nb];
	  double[] pr2in= new double[Nb];
	  double[] pr3in= new double[Nb];
	  double[] rbcomplex= new double[Nb];
	  double[] rbInterp= new double[Nb];
	  double[] inputs= new double[Nb];
	  double[] labelsDef= new double[Nb];
	  double[] labelsUsed= new double[Nb];
	  double[] dbdim= new double[Nb];
	  double[] pelab= new double[Nb];
	  double[] por= new double[Nb];
	  double[] pnot= new double[Nb];
	  double[] dbcomplex= new double[Nb];
	  double[] dbInterp= new double[Nb];
	  double[] Interp= new double[Nb];
	  String[] LingTerm= new String[Nb];
      int index=0;
      int cont=1;
      try {
        for (int k=0; k<Nb; k++) {
          //String fileName= QualityCV+"_CV"+k+"_"+opt;
          String fileName= QualityCV+"_"+opt;
          //System.out.println("fileName="+fileName);
          File file = new File(fileName);
    	  LineNumberReader lnr= new LineNumberReader(new InputStreamReader(new FileInputStream(file)));
          String l;
          while((l= lnr.readLine())!=null) {
              if (cont<4) {
            	  if (l.equals("ACCURACY")) {
            		  for (int n=0; n<5; n++)
            		     lnr.readLine();

                      if (cont==2) {
                    	  labelsDef[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("Labels").length()+2)).doubleValue();
                    	  errorLRN[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("ErrorCases").length()+2)).doubleValue();
                    	  ambiguityLRN[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("AmbiguityCases").length()+2)).doubleValue();
                    	  unclassifiedLRN[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("NoClassifCases").length()+2)).doubleValue();
                    	  performanceLRN[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("Performance").length()+2)).doubleValue();
                      } else if (cont==3) {
             		      lnr.readLine();
                    	  errorTST[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("ErrorCases").length()+2)).doubleValue();
                    	  ambiguityTST[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("AmbiguityCases").length()+2)).doubleValue();
                    	  unclassifiedTST[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("NoClassifCases").length()+2)).doubleValue();
                    	  performanceTST[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("Performance").length()+2)).doubleValue();
                      }
            		  cont++;
            		  //AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII
            	  } else if (l.equals("INTERPRETABILITY")) {
            		  //lnr.readLine();
            		  lnr.readLine();
            		  rules[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("NbTotalRules").length()+2)).doubleValue();
            		  premises[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("NbTotalPremises").length()+2)).doubleValue();
            		  rbdim[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("outputKBRuleBaseDimension").length()+2)).doubleValue();
            		  pr1in[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("PercentageNbRulesWithLessThanLPercentInputs").length()+2)).doubleValue();
            		  pr2in[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("PercentageNbRulesWithBetweenLAndMPercentInputs").length()+2)).doubleValue();
            		  pr3in[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("PercentageNbRulesWithMoreThanMPercentInputs").length()+2)).doubleValue();
            		  rbcomplex[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("outputKBRuleBaseComplexity").length()+2)).doubleValue();
            		  rbInterp[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("outputKBRuleBaseInterpretability").length()+2)).doubleValue();
            		  inputs[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("NbTotalInputs").length()+2)).doubleValue();
            		  labelsUsed[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("NbTotalUsedLabels").length()+2)).doubleValue();
            		  dbdim[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("outputKBDataBaseDimension").length()+2)).doubleValue();
            		  pelab[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("PercentageNbTotalElementaryUsedLabels").length()+2)).doubleValue();
            		  pnot[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("PercentageNbTotalNOTCompositeUsedLabels").length()+2)).doubleValue();
            		  por[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("PercentageNbTotalORCompositeUsedLabels").length()+2)).doubleValue();
            		  dbcomplex[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("outputKBDataBaseComplexity").length()+2)).doubleValue();
            		  dbInterp[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("outputKBDataBaseInterpretability").length()+2)).doubleValue();
            		  Interp[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("InterpretabilityIndex").length()+2)).doubleValue();
            		  LingTerm[index]= lnr.readLine().substring(LocaleKBCT.GetString("Label").length()+2);
            		  cont++;
            	  }
              } else {
            	  cont=1;
                  //System.out.println("index="+index);
            	  index++;
            	  if (index==Nb) {
            		  index=0;
       		          //LogQualityExcel.println(options[contOpt++]);
       		          LogQualityExcel.println(opt);
       		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
            		       LogQualityExcel.print(df.format(errorLRN[n])+"	");
                      }
       		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
              		       LogQualityExcel.print(df.format(ambiguityLRN[n])+"	");
                      }
        		      //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
                		   LogQualityExcel.print(df.format(unclassifiedLRN[n])+"	");
                      }
           		      //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
              		       LogQualityExcel.print(df.format(performanceLRN[n])+"	");
                      }
         		      //LogQualityExcel.println();
         		      //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
           		           LogQualityExcel.print(df.format(errorTST[n])+"	");
                      }
      		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
             		       LogQualityExcel.print(df.format(ambiguityTST[n])+"	");
                      }
        		      //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
               		       LogQualityExcel.print(df.format(unclassifiedTST[n])+"	");
                      }
          		      //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
             		       LogQualityExcel.print(df.format(performanceTST[n])+"	");
                      }
        		      //LogQualityExcel.println();
        		      //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
          		           LogQualityExcel.print(df.format(rules[n])+"	");
                      }
     		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
            		       LogQualityExcel.print(df.format(premises[n])+"	");
                      }
       		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
              		       LogQualityExcel.print(df.format(rbdim[n])+"	");
                      }
         		      //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
            		       LogQualityExcel.print(df.format(pr1in[n])+"	");
                      }
       		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
           		           LogQualityExcel.print(df.format(pr2in[n])+"	");
                      }
     		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
           		           LogQualityExcel.print(df.format(pr3in[n])+"	");
                      }
      		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
           		           LogQualityExcel.print(df.format(rbcomplex[n])+"	");
                      }
      		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
           		           LogQualityExcel.print(df.format(rbInterp[n])+"	");
                      }
      		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
          		           LogQualityExcel.print(df.format(inputs[n])+"	");
                      }
     		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
         		           LogQualityExcel.print(df.format(labelsUsed[n])+"	");
                      }
    		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
             		       LogQualityExcel.print(df.format(dbdim[n])+"	");
                      }
        		      //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
           		           LogQualityExcel.print(df.format(pelab[n])+"	");
                      }
      		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
          		           LogQualityExcel.print(df.format(pnot[n])+"	");
                      }
    		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
          		           LogQualityExcel.print(df.format(por[n])+"	");
                      }
     		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
          		           LogQualityExcel.print(df.format(dbcomplex[n])+"	");
                      }
     		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
          		           LogQualityExcel.print(df.format(dbInterp[n])+"	");
                      }
      		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
          		           LogQualityExcel.print(df.format(Interp[n])+"	");
                      }
     		          //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
        		           LogQualityExcel.print(LingTerm[n]+"	");
                      }
   		              //LogQualityExcel.println();
                      for (int n=0; n<Nb; n++) {
        		           LogQualityExcel.print(df.format(labelsDef[n])+"	");
                      }
   		              LogQualityExcel.println();
       		          LogQualityExcel.println();
       		          LogQualityExcel.flush();
             	  }
              }
          }
    	}
      } catch (Throwable t) {
     	  t.printStackTrace();
    	  MessageKBCT.Error(null, t);
      }
  }
//------------------------------------------------------------------------------
  /**
   * Write a message to the end of quality log file for Excel
   */
  public static void WriteAccExcelLogFile(String QualityCV, int Nb) {
      //System.out.println("Q="+QualityCV+"  Nb="+Nb);
	  DecimalFormat df= new DecimalFormat();
	  df.setMaximumFractionDigits(MainKBCT.getConfig().GetNumberOfDecimals());
	  df.setMinimumFractionDigits(MainKBCT.getConfig().GetNumberOfDecimals());
	  DecimalFormatSymbols dfs= df.getDecimalFormatSymbols();
	  dfs.setDecimalSeparator((new String(",").charAt(0)));
	  df.setDecimalFormatSymbols(dfs);
	  df.setGroupingSize(20);
	  double[] rules= new double[Nb];
	  double[] inputs= new double[Nb];
	  double[] labels= new double[Nb];
	  double[] error= new double[Nb];
	  double[] ambiguity= new double[Nb];
	  double[] unclassified= new double[Nb];
	  double[] performance= new double[Nb];
	  double[] coverage= new double[Nb];
	  double[] maxerror= new double[Nb];
      int index=0;
      try {
          //String fileName= QualityCV+"_CV"+k+"_"+opt;
          String fileName= QualityCV;
          //System.out.println("fileName="+fileName);
          File file = new File(fileName);
    	  LineNumberReader lnr= new LineNumberReader(new InputStreamReader(new FileInputStream(file)));
          String l;
          while((l= lnr.readLine())!=null) {
           	  if (l.equals("ACCURACY")) {
            		  for (int n=0; n<3; n++)
            		     lnr.readLine();

                      rules[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("Rules").length()+2)).doubleValue();
                      inputs[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("Inputs").length()+2)).doubleValue();
                      labels[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("Labels").length()+2)).doubleValue();
                      error[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("ErrorCases").length()+2)).doubleValue();
                      ambiguity[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("AmbiguityCases").length()+2)).doubleValue();
                   	  unclassified[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("NoClassifCases").length()+2)).doubleValue();
                      performance[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("Performance").length()+2)).doubleValue();
                      coverage[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("Coverage").length()+2)).doubleValue();
                      maxerror[index]= new Double(lnr.readLine().substring(LocaleKBCT.GetString("MaxError").length()+2)).doubleValue();
                      index++;
           	  }
          }
	          //LogQualityExcel.println();
              for (int n=0; n<Nb; n++) {
   		           LogQualityExcel.print(df.format(rules[n])+"	");
              }
		          LogQualityExcel.println();
              for (int n=0; n<Nb; n++) {
  		           LogQualityExcel.print(df.format(inputs[n])+"	");
              }
		          LogQualityExcel.println();
              for (int n=0; n<Nb; n++) {
  		           LogQualityExcel.print(df.format(labels[n])+"	");
              }
		          LogQualityExcel.println();
              for (int n=0; n<Nb; n++) {
    		       LogQualityExcel.print(df.format(error[n])+"	");
              }
		          LogQualityExcel.println();
              for (int n=0; n<Nb; n++) {
      		       LogQualityExcel.print(df.format(ambiguity[n])+"	");
              }
		      LogQualityExcel.println();
              for (int n=0; n<Nb; n++) {
        		   LogQualityExcel.print(df.format(unclassified[n])+"	");
              }
   		      LogQualityExcel.println();
              for (int n=0; n<Nb; n++) {
      		       LogQualityExcel.print(df.format(performance[n])+"	");
              }
 		      LogQualityExcel.println();
              for (int n=0; n<Nb; n++) {
   		           LogQualityExcel.print(df.format(coverage[n])+"	");
              }
		          LogQualityExcel.println();
              for (int n=0; n<Nb; n++) {
     		       LogQualityExcel.print(df.format(maxerror[n])+"	");
              }
              LogQualityExcel.println();
	          LogQualityExcel.println();
	          LogQualityExcel.flush();
      } catch (Throwable t) {
     	  t.printStackTrace();
    	  MessageKBCT.Error(null, t);
      }
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with two options to make automatic configuration:<br>
   * <ul>
   *    <li> Modeling: set parameters for induction and simplification. </li>
   *    <li> Interpretability: set Comp parameter. </li>
   * </ul>
   */
  public static int setAutomaticConfiguration( Component parent ) {
	Object[] options = { LocaleKBCT.GetString("Modeling"), LocaleKBCT.GetString("Interpretability"), LocaleKBCT.GetString("Save"), LocaleKBCT.GetString("Cancel") };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, LocaleKBCT.GetString("Choose_one_option"),
                               LocaleKBCT.GetString("SetAutomaticConfiguration_msg"),
                               JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                               null, options, options[0]);
  }
//------------------------------------------------------------------------------
  /**
   * Write a bat file
   */
  public static void buildBATfile(String path, String batFileName, String problem, String type, String option) {
      try {
    	  String[] opts= {"RP_WM","RP_WM_S","RP_WM_S_O_SW_SI_L_10","RP_WM_S_O_SW_EI_L_10","RP_WM_S_O_GT_SI_100_1","RP_WM_S_O_GT_SI_100_2","RP_WM_S_O_GT_SI_100_3","RP_WM_S_O_GT_SI_100_4","RP_WM_S_O_GT_SI_100_5","RP_WM_S_O_GT_SI_100_6","RP_WM_S_O_GT_EI_100_1","RP_WM_S_O_GT_EI_100_2","RP_WM_S_O_GT_EI_100_3","RP_WM_S_O_GT_EI_100_4","RP_WM_S_O_GT_EI_100_5","RP_WM_S_O_GT_EI_100_6","RP_WM_S_O_GT_SI_1000_1","RP_WM_S_O_GT_SI_1000_2","RP_WM_S_O_GT_SI_1000_3","RP_WM_S_O_GT_SI_1000_4","RP_WM_S_O_GT_SI_1000_5","RP_WM_S_O_GT_SI_1000_6","RP_WM_S_O_GT_EI_1000_1","RP_WM_S_O_GT_EI_1000_2","RP_WM_S_O_GT_EI_1000_3","RP_WM_S_O_GT_EI_1000_4","RP_WM_S_O_GT_EI_1000_5","RP_WM_S_O_GT_EI_1000_6","RP_FDT","RP_FDT_S","RP_FDT_S_O_SW_SI_L_10","RP_FDT_S_O_SW_EI_L_10","RP_FDT_S_O_GT_SI_100_1","RP_FDT_S_O_GT_SI_100_2","RP_FDT_S_O_GT_SI_100_3","RP_FDT_S_O_GT_SI_100_4","RP_FDT_S_O_GT_SI_100_5","RP_FDT_S_O_GT_SI_100_6","RP_FDT_S_O_GT_EI_100_1","RP_FDT_S_O_GT_EI_100_2","RP_FDT_S_O_GT_EI_100_3","RP_FDT_S_O_GT_EI_100_4","RP_FDT_S_O_GT_EI_100_5","RP_FDT_S_O_GT_EI_100_6","RP_FDT_S_O_GT_SI_1000_1","RP_FDT_S_O_GT_SI_1000_2","RP_FDT_S_O_GT_SI_1000_3","RP_FDT_S_O_GT_SI_1000_4","RP_FDT_S_O_GT_SI_1000_5","RP_FDT_S_O_GT_SI_1000_6","RP_FDT_S_O_GT_EI_1000_1","RP_FDT_S_O_GT_EI_1000_2","RP_FDT_S_O_GT_EI_1000_3","RP_FDT_S_O_GT_EI_1000_4","RP_FDT_S_O_GT_EI_1000_5","RP_FDT_S_O_GT_EI_1000_6","BP_WM","BP_WM_S","BP_WM_S_O_SW_SI_L_10","BP_WM_S_O_SW_EI_L_10","BP_WM_S_O_GT_SI_100_1","BP_WM_S_O_GT_SI_100_2","BP_WM_S_O_GT_SI_100_3","BP_WM_S_O_GT_SI_100_4","BP_WM_S_O_GT_SI_100_5","BP_WM_S_O_GT_SI_100_6","BP_WM_S_O_GT_EI_100_1","BP_WM_S_O_GT_EI_100_2","BP_WM_S_O_GT_EI_100_3","BP_WM_S_O_GT_EI_100_4","BP_WM_S_O_GT_EI_100_5","BP_WM_S_O_GT_EI_100_6","BP_WM_S_O_GT_SI_1000_1","BP_WM_S_O_GT_SI_1000_2","BP_WM_S_O_GT_SI_1000_3","BP_WM_S_O_GT_SI_1000_4","BP_WM_S_O_GT_SI_1000_5","BP_WM_S_O_GT_SI_1000_6","BP_WM_S_O_GT_EI_1000_1","BP_WM_S_O_GT_EI_1000_2","BP_WM_S_O_GT_EI_1000_3","BP_WM_S_O_GT_EI_1000_4","BP_WM_S_O_GT_EI_1000_5","BP_WM_S_O_GT_EI_1000_6","BP_FDT","BP_FDT_S","BP_FDT_S_O_SW_SI_L_10","BP_FDT_S_O_SW_EI_L_10","BP_FDT_S_O_GT_SI_100_1","BP_FDT_S_O_GT_SI_100_2","BP_FDT_S_O_GT_SI_100_3","BP_FDT_S_O_GT_SI_100_4","BP_FDT_S_O_GT_SI_100_5","BP_FDT_S_O_GT_SI_100_6","BP_FDT_S_O_GT_EI_100_1","BP_FDT_S_O_GT_EI_100_2","BP_FDT_S_O_GT_EI_100_3","BP_FDT_S_O_GT_EI_100_4","BP_FDT_S_O_GT_EI_100_5","BP_FDT_S_O_GT_EI_100_6","BP_FDT_S_O_GT_SI_1000_1","BP_FDT_S_O_GT_SI_1000_2","BP_FDT_S_O_GT_SI_1000_3","BP_FDT_S_O_GT_SI_1000_4","BP_FDT_S_O_GT_SI_1000_5","BP_FDT_S_O_GT_SI_1000_6","BP_FDT_S_O_GT_EI_1000_1","BP_FDT_S_O_GT_EI_1000_2","BP_FDT_S_O_GT_EI_1000_3","BP_FDT_S_O_GT_EI_1000_4","BP_FDT_S_O_GT_EI_1000_5","BP_FDT_S_O_GT_EI_1000_6",
		          "RP_CL_WM_DS_WM","RP_CL_WM_DS_WM_S_SC_S","RP_CL_WM_DS_WM_S_SC_S_O_SW_SI_L_10","RP_CL_WM_DS_WM_S_SC_S_O_SW_EI_L_10","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_1","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_2","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_3","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_4","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_5","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_6","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_1","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_2","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_3","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_4","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_5","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_6","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_1","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_2","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_3","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_4","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_5","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_6","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_1","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_2","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_3","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_4","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_5","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_6",
		          "RP_CL_WM_DS_FDT","RP_CL_WM_DS_FDT_S_SC_S","RP_CL_WM_DS_FDT_S_SC_S_O_SW_SI_L_10","RP_CL_WM_DS_FDT_S_SC_S_O_SW_EI_L_10","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_1","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_2","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_3","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_4","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_5","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_6","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_1","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_2","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_3","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_4","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_5","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_6","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_1","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_2","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_3","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_4","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_5","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_6","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_1","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_2","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_3","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_4","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_5","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_6",
		          "RP_CL_FDT_DS_WM","RP_CL_FDT_DS_WM_S_SC_S","RP_CL_FDT_DS_WM_S_SC_S_O_SW_SI_L_10","RP_CL_FDT_DS_WM_S_SC_S_O_SW_EI_L_10","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_1","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_2","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_3","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_4","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_5","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_6","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_1","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_2","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_3","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_4","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_5","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_6","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_1","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_2","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_3","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_4","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_5","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_6","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_1","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_2","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_3","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_4","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_5","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_6",
		          "RP_CL_FDT_DS_FDT","RP_CL_FDT_DS_FDT_S_SC_S","RP_CL_FDT_DS_FDT_S_SC_S_O_SW_SI_L_10","RP_CL_FDT_DS_FDT_S_SC_S_O_SW_EI_L_10","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_1","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_2","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_3","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_4","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_5","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_6","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_1","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_2","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_3","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_4","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_5","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_6","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_1","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_2","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_3","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_4","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_5","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_6","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_1","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_2","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_3","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_4","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_5","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_6",
		          "BP_CL_WM_DS_WM","BP_CL_WM_DS_WM_S_SC_S","BP_CL_WM_DS_WM_S_SC_S_O_SW_SI_L_10","BP_CL_WM_DS_WM_S_SC_S_O_SW_EI_L_10","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_1","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_2","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_3","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_4","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_5","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_6","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_1","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_2","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_3","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_4","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_5","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_6","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_1","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_2","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_3","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_4","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_5","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_6","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_1","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_2","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_3","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_4","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_5","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_6",
		          "BP_CL_WM_DS_FDT","BP_CL_WM_DS_FDT_S_SC_S","BP_CL_WM_DS_FDT_S_SC_S_O_SW_SI_L_10","BP_CL_WM_DS_FDT_S_SC_S_O_SW_EI_L_10","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_1","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_2","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_3","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_4","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_5","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_6","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_1","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_2","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_3","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_4","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_5","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_6","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_1","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_2","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_3","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_4","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_5","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_6","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_1","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_2","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_3","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_4","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_5","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_6",
		          "BP_CL_FDT_DS_WM","BP_CL_FDT_DS_WM_S_SC_S","BP_CL_FDT_DS_WM_S_SC_S_O_SW_SI_L_10","BP_CL_FDT_DS_WM_S_SC_S_O_SW_EI_L_10","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_1","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_2","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_3","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_4","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_5","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_6","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_1","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_2","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_3","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_4","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_5","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_6","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_1","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_2","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_3","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_4","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_5","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_6","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_1","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_2","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_3","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_4","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_5","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_6",
		          "BP_CL_FDT_DS_FDT","BP_CL_FDT_DS_FDT_S_SC_S","BP_CL_FDT_DS_FDT_S_SC_S_O_SW_SI_L_10","BP_CL_FDT_DS_FDT_S_SC_S_O_SW_EI_L_10","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_1","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_2","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_3","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_4","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_5","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_6","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_1","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_2","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_3","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_4","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_5","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_6","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_1","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_2","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_3","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_4","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_5","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_6","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_1","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_2","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_3","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_4","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_5","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_6"};
    	  String[] options= {"BP_CL_FDT_DS_FDT","BP_CL_FDT_DS_FDT_S_SC_S","BP_CL_FDT_DS_FDT_S_SC_S_O_SW_SI_L_10","BP_CL_FDT_DS_FDT_S_SC_S_O_SW_EI_L_10","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_1","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_2","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_3","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_4","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_5","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_6","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_1","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_2","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_3","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_4","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_5","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_6","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_1","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_2","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_3","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_4","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_5","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_6","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_1","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_2","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_3","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_4","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_5","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_6",
    			                 "BP_CL_FDT_DS_FDT_S_SC_S_CA","BP_CL_FDT_DS_FDT_S_SC_S_CA_S","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_1","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_2","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_3","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_4","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_5","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_6","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_1","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_2","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_3","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_4","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_5","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_6","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_1","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_2","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_3","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_4","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_5","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_6","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_1","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_2","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_3","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_4","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_5","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_6",	                             
    			                 "BP_CL_FDT_DS_WM","BP_CL_FDT_DS_WM_S_SC_S","BP_CL_FDT_DS_WM_S_SC_S_O_SW_SI_L_10","BP_CL_FDT_DS_WM_S_SC_S_O_SW_EI_L_10","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_1","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_2","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_3","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_4","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_5","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_6","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_1","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_2","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_3","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_4","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_5","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_6","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_1","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_2","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_3","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_4","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_5","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_6","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_1","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_2","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_3","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_4","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_5","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_6",
    			                 "BP_CL_FDT_DS_WM_S_SC_S_CA","BP_CL_FDT_DS_WM_S_SC_S_CA_S","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_1","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_2","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_3","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_4","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_5","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_6","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_1","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_2","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_3","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_4","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_5","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_6","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_1","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_2","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_3","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_4","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_5","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_6","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_1","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_2","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_3","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_4","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_5","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_6",
    			                 "BP_CL_WM_DS_FDT","BP_CL_WM_DS_FDT_S_SC_S","BP_CL_WM_DS_FDT_S_SC_S_O_SW_SI_L_10","BP_CL_WM_DS_FDT_S_SC_S_O_SW_EI_L_10","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_1","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_2","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_3","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_4","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_5","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_6","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_1","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_2","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_3","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_4","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_5","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_6","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_1","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_2","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_3","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_4","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_5","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_6","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_1","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_2","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_3","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_4","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_5","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_6",
	                             "BP_CL_WM_DS_FDT_S_SC_S_CA","BP_CL_WM_DS_FDT_S_SC_S_CA_S","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_1","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_2","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_3","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_4","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_5","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_6","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_1","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_2","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_3","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_4","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_5","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_6","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_1","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_2","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_3","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_4","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_5","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_6","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_1","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_2","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_3","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_4","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_5","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_6",
	                             "BP_CL_WM_DS_WM","BP_CL_WM_DS_WM_S_SC_S","BP_CL_WM_DS_WM_S_SC_S_O_SW_SI_L_10","BP_CL_WM_DS_WM_S_SC_S_O_SW_EI_L_10","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_1","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_2","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_3","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_4","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_5","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_6","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_1","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_2","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_3","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_4","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_5","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_6","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_1","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_2","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_3","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_4","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_5","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_6","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_1","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_2","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_3","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_4","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_5","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_6",
	                             "BP_CL_WM_DS_WM_S_SC_S_CA","BP_CL_WM_DS_WM_S_SC_S_CA_S","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_1","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_2","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_3","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_4","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_5","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_6","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_1","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_2","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_3","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_4","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_5","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_6","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_1","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_2","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_3","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_4","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_5","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_6","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_1","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_2","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_3","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_4","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_5","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_6",
	                             "BP_FDT","BP_FDT_S","BP_FDT_S_O_SW_SI_L_10","BP_FDT_S_O_SW_EI_L_10","BP_FDT_S_O_GT_SI_100_1","BP_FDT_S_O_GT_SI_100_2","BP_FDT_S_O_GT_SI_100_3","BP_FDT_S_O_GT_SI_100_4","BP_FDT_S_O_GT_SI_100_5","BP_FDT_S_O_GT_SI_100_6","BP_FDT_S_O_GT_EI_100_1","BP_FDT_S_O_GT_EI_100_2","BP_FDT_S_O_GT_EI_100_3","BP_FDT_S_O_GT_EI_100_4","BP_FDT_S_O_GT_EI_100_5","BP_FDT_S_O_GT_EI_100_6","BP_FDT_S_O_GT_SI_1000_1","BP_FDT_S_O_GT_SI_1000_2","BP_FDT_S_O_GT_SI_1000_3","BP_FDT_S_O_GT_SI_1000_4","BP_FDT_S_O_GT_SI_1000_5","BP_FDT_S_O_GT_SI_1000_6","BP_FDT_S_O_GT_EI_1000_1","BP_FDT_S_O_GT_EI_1000_2","BP_FDT_S_O_GT_EI_1000_3","BP_FDT_S_O_GT_EI_1000_4","BP_FDT_S_O_GT_EI_1000_5","BP_FDT_S_O_GT_EI_1000_6",
	                             "BP_FDT_S_CA","BP_FDT_S_CA_S","BP_FDT_S_CA_S_O_SW_SI_L_10","BP_FDT_S_CA_S_O_SW_EI_L_10","BP_FDT_S_CA_S_O_GT_SI_100_1","BP_FDT_S_CA_S_O_GT_SI_100_2","BP_FDT_S_CA_S_O_GT_SI_100_3","BP_FDT_S_CA_S_O_GT_SI_100_4","BP_FDT_S_CA_S_O_GT_SI_100_5","BP_FDT_S_CA_S_O_GT_SI_100_6","BP_FDT_S_CA_S_O_GT_EI_100_1","BP_FDT_S_CA_S_O_GT_EI_100_2","BP_FDT_S_CA_S_O_GT_EI_100_3","BP_FDT_S_CA_S_O_GT_EI_100_4","BP_FDT_S_CA_S_O_GT_EI_100_5","BP_FDT_S_CA_S_O_GT_EI_100_6","BP_FDT_S_CA_S_O_GT_SI_1000_1","BP_FDT_S_CA_S_O_GT_SI_1000_2","BP_FDT_S_CA_S_O_GT_SI_1000_3","BP_FDT_S_CA_S_O_GT_SI_1000_4","BP_FDT_S_CA_S_O_GT_SI_1000_5","BP_FDT_S_CA_S_O_GT_SI_1000_6","BP_FDT_S_CA_S_O_GT_EI_1000_1","BP_FDT_S_CA_S_O_GT_EI_1000_2","BP_FDT_S_CA_S_O_GT_EI_1000_3","BP_FDT_S_CA_S_O_GT_EI_1000_4","BP_FDT_S_CA_S_O_GT_EI_1000_5","BP_FDT_S_CA_S_O_GT_EI_1000_6",
	                             "BP_WM","BP_WM_S","BP_WM_S_O_SW_SI_L_10","BP_WM_S_O_SW_EI_L_10","BP_WM_S_O_GT_GT_SI_100_1","BP_WM_S_O_GT_GT_SI_100_2","BP_WM_S_O_GT_GT_SI_100_3","BP_WM_S_O_GT_GT_SI_100_4","BP_WM_S_O_GT_GT_SI_100_5","BP_WM_S_O_GT_GT_SI_100_6","BP_WM_S_O_GT_GT_EI_100_1","BP_WM_S_O_GT_GT_EI_100_2","BP_WM_S_O_GT_GT_EI_100_3","BP_WM_S_O_GT_GT_EI_100_4","BP_WM_S_O_GT_GT_EI_100_5","BP_WM_S_O_GT_GT_EI_100_6","BP_WM_S_O_GT_SI_1000_1","BP_WM_S_O_GT_SI_1000_2","BP_WM_S_O_GT_SI_1000_3","BP_WM_S_O_GT_SI_1000_4","BP_WM_S_O_GT_SI_1000_5","BP_WM_S_O_GT_SI_1000_6","BP_WM_S_O_GT_EI_1000_1","BP_WM_S_O_GT_EI_1000_2","BP_WM_S_O_GT_EI_1000_3","BP_WM_S_O_GT_EI_1000_4","BP_WM_S_O_GT_EI_1000_5","BP_WM_S_O_GT_EI_1000_6",
	                             "BP_WM_S_CA","BP_WM_S_CA_S","BP_WM_S_CA_S_O_SW_SI_L_10","BP_WM_S_CA_S_O_SW_EI_L_10","BP_WM_S_CA_S_O_GT_GT_SI_100_1","BP_WM_S_CA_S_O_GT_GT_SI_100_2","BP_WM_S_CA_S_O_GT_GT_SI_100_3","BP_WM_S_CA_S_O_GT_GT_SI_100_4","BP_WM_S_CA_S_O_GT_GT_SI_100_5","BP_WM_S_CA_S_O_GT_GT_SI_100_6","BP_WM_S_CA_S_O_GT_GT_EI_100_1","BP_WM_S_CA_S_O_GT_GT_EI_100_2","BP_WM_S_CA_S_O_GT_GT_EI_100_3","BP_WM_S_CA_S_O_GT_GT_EI_100_4","BP_WM_S_CA_S_O_GT_GT_EI_100_5","BP_WM_S_CA_S_O_GT_GT_EI_100_6","BP_WM_S_CA_S_O_GT_SI_1000_1","BP_WM_S_CA_S_O_GT_SI_1000_2","BP_WM_S_CA_S_O_GT_SI_1000_3","BP_WM_S_CA_S_O_GT_SI_1000_4","BP_WM_S_CA_S_O_GT_SI_1000_5","BP_WM_S_CA_S_O_GT_SI_1000_6","BP_WM_S_CA_S_O_GT_EI_1000_1","BP_WM_S_CA_S_O_GT_EI_1000_2","BP_WM_S_CA_S_O_GT_EI_1000_3","BP_WM_S_CA_S_O_GT_EI_1000_4","BP_WM_S_CA_S_O_GT_EI_1000_5","BP_WM_S_CA_S_O_GT_EI_1000_6",
	                             "RP_CL_FDT_DS_FDT","RP_CL_FDT_DS_FDT_S_SC_S","RP_CL_FDT_DS_FDT_S_SC_S_O_SW_SI_L_10","RP_CL_FDT_DS_FDT_S_SC_S_O_SW_EI_L_10","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_1","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_2","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_3","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_4","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_5","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_6","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_1","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_2","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_3","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_4","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_5","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_6","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_1","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_2","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_3","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_4","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_5","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_6","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_1","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_2","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_3","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_4","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_5","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_6",
	                             "RP_CL_FDT_DS_FDT_S_SC_S_CA","RP_CL_FDT_DS_FDT_S_SC_S_CA_S","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_1","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_2","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_3","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_4","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_5","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_6","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_1","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_2","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_3","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_4","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_5","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_6","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_1","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_2","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_3","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_4","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_5","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_6","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_1","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_2","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_3","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_4","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_5","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_6",
	                             "RP_CL_FDT_DS_WM","RP_CL_FDT_DS_WM_S_SC_S","RP_CL_FDT_DS_WM_S_SC_S_O_SW_SI_L_10","RP_CL_FDT_DS_WM_S_SC_S_O_SW_EI_L_10","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_1","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_2","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_3","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_4","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_5","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_6","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_1","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_2","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_3","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_4","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_5","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_6","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_1","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_2","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_3","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_4","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_5","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_6","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_1","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_2","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_3","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_4","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_5","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_6",
	                             "RP_CL_FDT_DS_WM_S_SC_S_CA","RP_CL_FDT_DS_WM_S_SC_S_CA_S","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_1","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_2","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_3","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_4","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_5","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_6","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_1","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_2","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_3","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_4","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_5","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_6","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_1","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_2","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_3","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_4","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_5","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_6","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_1","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_2","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_3","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_4","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_5","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_6",
	                             "RP_CL_WM_DS_FDT","RP_CL_WM_DS_FDT_S_SC_S","RP_CL_WM_DS_FDT_S_SC_S_O_SW_SI_L_10","RP_CL_WM_DS_FDT_S_SC_S_O_SW_EI_L_10","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_1","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_2","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_3","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_4","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_5","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_6","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_1","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_2","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_3","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_4","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_5","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_6","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_1","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_2","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_3","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_4","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_5","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_6","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_1","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_2","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_3","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_4","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_5","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_6",
	                             "RP_CL_WM_DS_FDT_S_SC_S_CA","RP_CL_WM_DS_FDT_S_SC_S_CA_S","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_1","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_2","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_3","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_4","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_5","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_6","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_1","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_2","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_3","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_4","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_5","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_6","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_1","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_2","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_3","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_4","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_5","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_6","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_1","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_2","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_3","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_4","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_5","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_6",
	                             "RP_CL_WM_DS_WM","RP_CL_WM_DS_WM_S_SC_S","RP_CL_WM_DS_WM_S_SC_S_O_SW_SI_L_10","RP_CL_WM_DS_WM_S_SC_S_O_SW_EI_L_10","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_1","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_2","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_3","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_4","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_5","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_6","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_1","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_2","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_3","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_4","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_5","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_6","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_1","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_2","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_3","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_4","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_5","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_6","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_1","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_2","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_3","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_4","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_5","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_6",
	                             "RP_CL_WM_DS_WM_S_SC_S_CA","RP_CL_WM_DS_WM_S_SC_S_CA_S","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_1","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_2","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_3","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_4","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_5","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_6","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_1","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_2","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_3","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_4","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_5","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_6","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_1","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_2","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_3","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_4","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_5","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_6","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_1","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_2","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_3","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_4","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_5","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_6",
	                             "RP_FDT","RP_FDT_S","RP_FDT_S_O_SW_SI_L_10","RP_FDT_S_O_SW_EI_L_10","RP_FDT_S_O_GT_SI_100_1","RP_FDT_S_O_GT_SI_100_2","RP_FDT_S_O_GT_SI_100_3","RP_FDT_S_O_GT_SI_100_4","RP_FDT_S_O_GT_SI_100_5","RP_FDT_S_O_GT_SI_100_6","RP_FDT_S_O_GT_EI_100_1","RP_FDT_S_O_GT_EI_100_2","RP_FDT_S_O_GT_EI_100_3","RP_FDT_S_O_GT_EI_100_4","RP_FDT_S_O_GT_EI_100_5","RP_FDT_S_O_GT_EI_100_6","RP_FDT_S_O_GT_SI_1000_1","RP_FDT_S_O_GT_SI_1000_2","RP_FDT_S_O_GT_SI_1000_3","RP_FDT_S_O_GT_SI_1000_4","RP_FDT_S_O_GT_SI_1000_5","RP_FDT_S_O_GT_SI_1000_6","RP_FDT_S_O_GT_EI_1000_1","RP_FDT_S_O_GT_EI_1000_2","RP_FDT_S_O_GT_EI_1000_3","RP_FDT_S_O_GT_EI_1000_4","RP_FDT_S_O_GT_EI_1000_5","RP_FDT_S_O_GT_EI_1000_6",
	                             "RP_FDT_S_CA","RP_FDT_S_CA_S","RP_FDT_S_CA_S_O_SW_SI_L_10","RP_FDT_S_CA_S_O_SW_EI_L_10","RP_FDT_S_CA_S_O_GT_SI_100_1","RP_FDT_S_CA_S_O_GT_SI_100_2","RP_FDT_S_CA_S_O_GT_SI_100_3","RP_FDT_S_CA_S_O_GT_SI_100_4","RP_FDT_S_CA_S_O_GT_SI_100_5","RP_FDT_S_CA_S_O_GT_SI_100_6","RP_FDT_S_CA_S_O_GT_EI_100_1","RP_FDT_S_CA_S_O_GT_EI_100_2","RP_FDT_S_CA_S_O_GT_EI_100_3","RP_FDT_S_CA_S_O_GT_EI_100_4","RP_FDT_S_CA_S_O_GT_EI_100_5","RP_FDT_S_CA_S_O_GT_EI_100_6","RP_FDT_S_CA_S_O_GT_SI_1000_1","RP_FDT_S_CA_S_O_GT_SI_1000_2","RP_FDT_S_CA_S_O_GT_SI_1000_3","RP_FDT_S_CA_S_O_GT_SI_1000_4","RP_FDT_S_CA_S_O_GT_SI_1000_5","RP_FDT_S_CA_S_O_GT_SI_1000_6","RP_FDT_S_CA_S_O_GT_EI_1000_1","RP_FDT_S_CA_S_O_GT_EI_1000_2","RP_FDT_S_CA_S_O_GT_EI_1000_3","RP_FDT_S_CA_S_O_GT_EI_1000_4","RP_FDT_S_CA_S_O_GT_EI_1000_5","RP_FDT_S_CA_S_O_GT_EI_1000_6",
	                             "RP_WM","RP_WM_S","RP_WM_S_O_SW_SI_L_10","RP_WM_S_O_SW_EI_L_10","RP_WM_S_O_GT_SI_100_1","RP_WM_S_O_GT_SI_100_2","RP_WM_S_O_GT_SI_100_3","RP_WM_S_O_GT_SI_100_4","RP_WM_S_O_GT_SI_100_5","RP_WM_S_O_GT_SI_100_6","RP_WM_S_O_GT_EI_100_1","RP_WM_S_O_GT_EI_100_2","RP_WM_S_O_GT_EI_100_3","RP_WM_S_O_GT_EI_100_4","RP_WM_S_O_GT_EI_100_5","RP_WM_S_O_GT_EI_100_6","RP_WM_S_O_GT_SI_1000_1","RP_WM_S_O_GT_SI_1000_2","RP_WM_S_O_GT_SI_1000_3","RP_WM_S_O_GT_SI_1000_4","RP_WM_S_O_GT_SI_1000_5","RP_WM_S_O_GT_SI_1000_6","RP_WM_S_O_GT_EI_1000_1","RP_WM_S_O_GT_EI_1000_2","RP_WM_S_O_GT_EI_1000_3","RP_WM_S_O_GT_EI_1000_4","RP_WM_S_O_GT_EI_1000_5","RP_WM_S_O_GT_EI_1000_6",
	                             "RP_WM_S_CA","RP_WM_S_CA_S","RP_WM_S_CA_S_O_SW_SI_L_10","RP_WM_S_CA_S_O_SW_EI_L_10","RP_WM_S_CA_S_O_GT_SI_100_1","RP_WM_S_CA_S_O_GT_SI_100_2","RP_WM_S_CA_S_O_GT_SI_100_3","RP_WM_S_CA_S_O_GT_SI_100_4","RP_WM_S_CA_S_O_GT_SI_100_5","RP_WM_S_CA_S_O_GT_SI_100_6","RP_WM_S_CA_S_O_GT_EI_100_1","RP_WM_S_CA_S_O_GT_EI_100_2","RP_WM_S_CA_S_O_GT_EI_100_3","RP_WM_S_CA_S_O_GT_EI_100_4","RP_WM_S_CA_S_O_GT_EI_100_5","RP_WM_S_CA_S_O_GT_EI_100_6","RP_WM_S_CA_S_O_GT_SI_1000_1","RP_WM_S_CA_S_O_GT_SI_1000_2","RP_WM_S_CA_S_O_GT_SI_1000_3","RP_WM_S_CA_S_O_GT_SI_1000_4","RP_WM_S_CA_S_O_GT_SI_1000_5","RP_WM_S_CA_S_O_GT_SI_1000_6","RP_WM_S_CA_S_O_GT_EI_1000_1","RP_WM_S_CA_S_O_GT_EI_1000_2","RP_WM_S_CA_S_O_GT_EI_1000_3","RP_WM_S_CA_S_O_GT_EI_1000_4","RP_WM_S_CA_S_O_GT_EI_1000_5","RP_WM_S_CA_S_O_GT_EI_1000_6"};

    	  String[] testSIMP= {"RP_FDT_S","BP_FDT_S","RP_WM_S","BP_WM_S","RP_CL_FDT_DS_FDT_S","BP_CL_FDT_DS_FDT_S","RP_CL_FDT_DS_WM_S","BP_CL_FDT_DS_WM_S","RP_CL_WM_DS_FDT_S","BP_CL_WM_DS_FDT_S","RP_CL_WM_DS_WM_S","BP_CL_WM_DS_WM_S"};
    	  String[] testSimpSC= {"RP_CL_FDT_DS_FDT_S_SC","BP_CL_FDT_DS_FDT_S_SC","RP_CL_FDT_DS_WM_S_SC","BP_CL_FDT_DS_WM_S_SC","RP_CL_WM_DS_FDT_S_SC","BP_CL_WM_DS_FDT_S_SC","RP_CL_WM_DS_WM_S_SC","BP_CL_WM_DS_WM_S_SC"};
    	  String[] testSimpScSIMP= {"RP_CL_FDT_DS_FDT_S_SC_S","BP_CL_FDT_DS_FDT_S_SC_S","RP_CL_FDT_DS_WM_S_SC_S","BP_CL_FDT_DS_WM_S_SC_S","RP_CL_WM_DS_FDT_S_SC_S","BP_CL_WM_DS_FDT_S_SC_S","RP_CL_WM_DS_WM_S_SC_S","BP_CL_WM_DS_WM_S_SC_S"};
    	  String[] testOSWsi= {"RP_FDT_S_O_SW_SI_L_10","BP_FDT_S_O_SW_SI_L_10","RP_WM_S_O_SW_SI_L_10","BP_WM_S_O_SW_SI_L_10","RP_CL_FDT_DS_FDT_S_SC_S_O_SW_SI_L_10","BP_CL_FDT_DS_FDT_S_SC_S_O_SW_SI_L_10","RP_CL_FDT_DS_WM_S_SC_S_O_SW_SI_L_10","BP_CL_FDT_DS_WM_S_SC_S_O_SW_SI_L_10","RP_CL_WM_DS_FDT_S_SC_S_O_SW_SI_L_10","BP_CL_WM_DS_FDT_S_SC_S_O_SW_SI_L_10","RP_CL_WM_DS_WM_S_SC_S_O_SW_SI_L_10","BP_CL_WM_DS_WM_S_SC_S_O_SW_SI_L_10"};
    	  String[] testOSWei= {"RP_FDT_S_O_SW_EI_L_10","BP_FDT_S_O_SW_EI_L_10","RP_WM_S_O_SW_EI_L_10","BP_WM_S_O_SW_EI_L_10","RP_CL_FDT_DS_FDT_S_SC_S_O_SW_EI_L_10","BP_CL_FDT_DS_FDT_S_SC_S_O_SW_EI_L_10","RP_CL_FDT_DS_WM_S_SC_S_O_SW_EI_L_10","BP_CL_FDT_DS_WM_S_SC_S_O_SW_EI_L_10","RP_CL_WM_DS_FDT_S_SC_S_O_SW_EI_L_10","BP_CL_WM_DS_FDT_S_SC_S_O_SW_EI_L_10","RP_CL_WM_DS_WM_S_SC_S_O_SW_EI_L_10","BP_CL_WM_DS_WM_S_SC_S_O_SW_EI_L_10"};
    	  String[] testOGTsi100= {"RP_FDT_S_O_GT_SI_100_1","RP_FDT_S_O_GT_SI_100_2","RP_FDT_S_O_GT_SI_100_3","RP_FDT_S_O_GT_SI_100_4","RP_FDT_S_O_GT_SI_100_5","RP_FDT_S_O_GT_SI_100_6","BP_FDT_S_O_GT_SI_100_1","BP_FDT_S_O_GT_SI_100_2","BP_FDT_S_O_GT_SI_100_3","BP_FDT_S_O_GT_SI_100_4","BP_FDT_S_O_GT_SI_100_5","BP_FDT_S_O_GT_SI_100_6","RP_WM_S_O_GT_SI_100_1","RP_WM_S_O_GT_SI_100_2","RP_WM_S_O_GT_SI_100_3","RP_WM_S_O_GT_SI_100_4","RP_WM_S_O_GT_SI_100_5","RP_WM_S_O_GT_SI_100_6","BP_WM_S_O_GT_SI_100_1","BP_WM_S_O_GT_SI_100_2","BP_WM_S_O_GT_SI_100_3","BP_WM_S_O_GT_SI_100_4","BP_WM_S_O_GT_SI_100_5","BP_WM_S_O_GT_SI_100_6","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_1","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_2","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_3","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_4","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_5","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_6","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_1","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_2","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_3","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_4","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_5","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_100_6","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_1","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_2","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_3","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_4","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_5","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_6","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_1","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_2","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_3","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_4","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_5","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_100_6","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_1","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_2","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_3","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_4","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_5","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_6","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_1","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_2","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_3","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_4","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_5","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_100_6","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_1","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_2","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_3","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_4","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_5","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_6","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_1","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_2","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_3","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_4","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_5","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_100_6"};
    	  String[] testOGTsi1000= {"RP_FDT_S_O_GT_SI_1000_1","RP_FDT_S_O_GT_SI_1000_2","RP_FDT_S_O_GT_SI_1000_3","RP_FDT_S_O_GT_SI_1000_4","RP_FDT_S_O_GT_SI_1000_5","RP_FDT_S_O_GT_SI_1000_6","BP_FDT_S_O_GT_SI_1000_1","BP_FDT_S_O_GT_SI_1000_2","BP_FDT_S_O_GT_SI_1000_3","BP_FDT_S_O_GT_SI_1000_4","BP_FDT_S_O_GT_SI_1000_5","BP_FDT_S_O_GT_SI_1000_6","RP_WM_S_O_GT_SI_1000_1","RP_WM_S_O_GT_SI_1000_2","RP_WM_S_O_GT_SI_1000_3","RP_WM_S_O_GT_SI_1000_4","RP_WM_S_O_GT_SI_1000_5","RP_WM_S_O_GT_SI_1000_6","BP_WM_S_O_GT_SI_1000_1","BP_WM_S_O_GT_SI_1000_2","BP_WM_S_O_GT_SI_1000_3","BP_WM_S_O_GT_SI_1000_4","BP_WM_S_O_GT_SI_1000_5","BP_WM_S_O_GT_SI_1000_6","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_1","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_2","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_3","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_4","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_5","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_6","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_1","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_2","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_3","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_4","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_5","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_SI_1000_6","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_1","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_2","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_3","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_4","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_5","RP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_6","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_1","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_2","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_3","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_4","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_5","BP_CL_FDT_DS_WM_S_SC_S_O_GT_SI_1000_6","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_1","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_2","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_3","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_4","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_5","RP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_6","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_1","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_2","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_3","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_4","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_5","BP_CL_WM_DS_FDT_S_SC_S_O_GT_SI_1000_6","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_1","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_2","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_3","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_4","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_5","RP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_6","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_1","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_2","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_3","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_4","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_5","BP_CL_WM_DS_WM_S_SC_S_O_GT_SI_1000_6"};
    	  String[] testOGTei100= {"RP_FDT_S_O_GT_EI_100_1","RP_FDT_S_O_GT_EI_100_2","RP_FDT_S_O_GT_EI_100_3","RP_FDT_S_O_GT_EI_100_4","RP_FDT_S_O_GT_EI_100_5","RP_FDT_S_O_GT_EI_100_6","BP_FDT_S_O_GT_EI_100_1","BP_FDT_S_O_GT_EI_100_2","BP_FDT_S_O_GT_EI_100_3","BP_FDT_S_O_GT_EI_100_4","BP_FDT_S_O_GT_EI_100_5","BP_FDT_S_O_GT_EI_100_6","RP_WM_S_O_GT_EI_100_1","RP_WM_S_O_GT_EI_100_2","RP_WM_S_O_GT_EI_100_3","RP_WM_S_O_GT_EI_100_4","RP_WM_S_O_GT_EI_100_5","RP_WM_S_O_GT_EI_100_6","BP_WM_S_O_GT_EI_100_1","BP_WM_S_O_GT_EI_100_2","BP_WM_S_O_GT_EI_100_3","BP_WM_S_O_GT_EI_100_4","BP_WM_S_O_GT_EI_100_5","BP_WM_S_O_GT_EI_100_6","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_1","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_2","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_3","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_4","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_5","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_6","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_1","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_2","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_3","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_4","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_5","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_100_6","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_1","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_2","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_3","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_4","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_5","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_6","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_1","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_2","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_3","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_4","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_5","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_100_6","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_1","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_2","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_3","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_4","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_5","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_6","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_1","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_2","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_3","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_4","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_5","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_100_6","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_1","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_2","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_3","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_4","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_5","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_6","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_1","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_2","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_3","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_4","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_5","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_100_6"};
    	  String[] testOGTei1000= {"RP_FDT_S_O_GT_EI_1000_1","RP_FDT_S_O_GT_EI_1000_2","RP_FDT_S_O_GT_EI_1000_3","RP_FDT_S_O_GT_EI_1000_4","RP_FDT_S_O_GT_EI_1000_5","RP_FDT_S_O_GT_EI_1000_6","BP_FDT_S_O_GT_EI_1000_1","BP_FDT_S_O_GT_EI_1000_2","BP_FDT_S_O_GT_EI_1000_3","BP_FDT_S_O_GT_EI_1000_4","BP_FDT_S_O_GT_EI_1000_5","BP_FDT_S_O_GT_EI_1000_6","RP_WM_S_O_GT_EI_1000_1","RP_WM_S_O_GT_EI_1000_2","RP_WM_S_O_GT_EI_1000_3","RP_WM_S_O_GT_EI_1000_4","RP_WM_S_O_GT_EI_1000_5","RP_WM_S_O_GT_EI_1000_6","BP_WM_S_O_GT_EI_1000_1","BP_WM_S_O_GT_EI_1000_2","BP_WM_S_O_GT_EI_1000_3","BP_WM_S_O_GT_EI_1000_4","BP_WM_S_O_GT_EI_1000_5","BP_WM_S_O_GT_EI_1000_6","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_1","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_2","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_3","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_4","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_5","RP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_6","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_1","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_2","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_3","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_4","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_5","BP_CL_FDT_DS_FDT_S_SC_S_O_GT_EI_1000_6","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_1","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_2","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_3","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_4","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_5","RP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_6","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_1","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_2","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_3","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_4","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_5","BP_CL_FDT_DS_WM_S_SC_S_O_GT_EI_1000_6","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_1","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_2","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_3","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_4","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_5","RP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_6","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_1","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_2","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_3","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_4","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_5","BP_CL_WM_DS_FDT_S_SC_S_O_GT_EI_1000_6","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_1","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_2","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_3","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_4","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_5","RP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_6","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_1","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_2","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_3","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_4","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_5","BP_CL_WM_DS_WM_S_SC_S_O_GT_EI_1000_6"};
    	  String[] testCA= {"RP_FDT_S_CA","BP_FDT_S_CA","RP_WM_S_CA","BP_WM_S_CA","RP_CL_FDT_DS_FDT_S_CA","BP_CL_FDT_DS_FDT_S_CA","RP_CL_FDT_DS_WM_S_CA","BP_CL_FDT_DS_WM_S_CA","RP_CL_WM_DS_FDT_S_CA","BP_CL_WM_DS_FDT_S_CA","RP_CL_WM_DS_WM_S_CA","BP_CL_WM_DS_WM_S_CA"};
    	  String[] testCaSIMP= {"RP_FDT_S_CA_S","BP_FDT_S_CA_S","RP_WM_S_CA_S","BP_WM_S_CA_S","RP_CL_FDT_DS_FDT_S_CA_S","BP_CL_FDT_DS_FDT_S_CA_S","RP_CL_FDT_DS_WM_S_CA_S","BP_CL_FDT_DS_WM_S_CA_S","RP_CL_WM_DS_FDT_S_CA_S","BP_CL_WM_DS_FDT_S_CA_S","RP_CL_WM_DS_WM_S_CA_S","BP_CL_WM_DS_WM_S_CA_S"};
    	  String[] testCASOSWsi= {"RP_FDT_S_CA_S_O_SW_SI_L_10","BP_FDT_S_CA_S_O_SW_SI_L_10","RP_WM_S_CA_S_O_SW_SI_L_10","BP_WM_S_CA_S_O_SW_SI_L_10","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_SI_L_10","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_SI_L_10"};
    	  String[] testCASOSWei= {"RP_FDT_S_CA_S_O_SW_EI_L_10","BP_FDT_S_CA_S_O_SW_EI_L_10","RP_WM_S_CA_S_O_SW_EI_L_10","BP_WM_S_CA_S_O_SW_EI_L_10","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_SW_EI_L_10","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_SW_EI_L_10"};
    	  String[] testCASOGTsi100= {"RP_FDT_S_CA_S_O_GT_SI_100_1","RP_FDT_S_CA_S_O_GT_SI_100_2","RP_FDT_S_CA_S_O_GT_SI_100_3","RP_FDT_S_CA_S_O_GT_SI_100_4","RP_FDT_S_CA_S_O_GT_SI_100_5","RP_FDT_S_CA_S_O_GT_SI_100_6","BP_FDT_S_CA_S_O_GT_SI_100_1","BP_FDT_S_CA_S_O_GT_SI_100_2","BP_FDT_S_CA_S_O_GT_SI_100_3","BP_FDT_S_CA_S_O_GT_SI_100_4","BP_FDT_S_CA_S_O_GT_SI_100_5","BP_FDT_S_CA_S_O_GT_SI_100_6","RP_WM_S_CA_S_O_GT_SI_100_1","RP_WM_S_CA_S_O_GT_SI_100_2","RP_WM_S_CA_S_O_GT_SI_100_3","RP_WM_S_CA_S_O_GT_SI_100_4","RP_WM_S_CA_S_O_GT_SI_100_5","RP_WM_S_CA_S_O_GT_SI_100_6","BP_WM_S_CA_S_O_GT_SI_100_1","BP_WM_S_CA_S_O_GT_SI_100_2","BP_WM_S_CA_S_O_GT_SI_100_3","BP_WM_S_CA_S_O_GT_SI_100_4","BP_WM_S_CA_S_O_GT_SI_100_5","BP_WM_S_CA_S_O_GT_SI_100_6","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_1","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_2","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_3","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_4","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_5","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_6","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_1","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_2","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_3","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_4","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_5","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_6","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_1","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_2","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_3","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_4","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_5","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_6","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_1","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_2","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_3","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_4","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_5","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_100_6","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_1","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_2","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_3","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_4","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_5","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_6","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_1","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_2","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_3","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_4","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_5","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_100_6","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_1","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_2","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_3","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_4","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_5","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_6","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_1","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_2","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_3","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_4","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_5","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_100_6"};
    	  String[] testCASOGTsi1000= {"RP_FDT_S_CA_S_O_GT_SI_1000_1","RP_FDT_S_CA_S_O_GT_SI_1000_2","RP_FDT_S_CA_S_O_GT_SI_1000_3","RP_FDT_S_CA_S_O_GT_SI_1000_4","RP_FDT_S_CA_S_O_GT_SI_1000_5","RP_FDT_S_CA_S_O_GT_SI_1000_6","BP_FDT_S_CA_S_O_GT_SI_1000_1","BP_FDT_S_CA_S_O_GT_SI_1000_2","BP_FDT_S_CA_S_O_GT_SI_1000_3","BP_FDT_S_CA_S_O_GT_SI_1000_4","BP_FDT_S_CA_S_O_GT_SI_1000_5","BP_FDT_S_CA_S_O_GT_SI_1000_6","RP_WM_S_CA_S_O_GT_SI_1000_1","RP_WM_S_CA_S_O_GT_SI_1000_2","RP_WM_S_CA_S_O_GT_SI_1000_3","RP_WM_S_CA_S_O_GT_SI_1000_4","RP_WM_S_CA_S_O_GT_SI_1000_5","RP_WM_S_CA_S_O_GT_SI_1000_6","BP_WM_S_CA_S_O_GT_SI_1000_1","BP_WM_S_CA_S_O_GT_SI_1000_2","BP_WM_S_CA_S_O_GT_SI_1000_3","BP_WM_S_CA_S_O_GT_SI_1000_4","BP_WM_S_CA_S_O_GT_SI_1000_5","BP_WM_S_CA_S_O_GT_SI_1000_6","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_1","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_2","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_3","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_4","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_5","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_6","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_1","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_2","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_3","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_4","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_5","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_6","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_1","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_2","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_3","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_4","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_5","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_6","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_1","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_2","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_3","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_4","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_5","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_6","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_1","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_2","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_3","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_4","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_5","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_6","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_1","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_2","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_3","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_4","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_5","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_SI_1000_6","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_1","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_2","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_3","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_4","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_5","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_6","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_1","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_2","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_3","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_4","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_5","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_SI_1000_6"};
    	  String[] testCASOGTei100= {"RP_FDT_S_CA_S_O_GT_EI_100_1","RP_FDT_S_CA_S_O_GT_EI_100_2","RP_FDT_S_CA_S_O_GT_EI_100_3","RP_FDT_S_CA_S_O_GT_EI_100_4","RP_FDT_S_CA_S_O_GT_EI_100_5","RP_FDT_S_CA_S_O_GT_EI_100_6","BP_FDT_S_CA_S_O_GT_EI_100_1","BP_FDT_S_CA_S_O_GT_EI_100_2","BP_FDT_S_CA_S_O_GT_EI_100_3","BP_FDT_S_CA_S_O_GT_EI_100_4","BP_FDT_S_CA_S_O_GT_EI_100_5","BP_FDT_S_CA_S_O_GT_EI_100_6","RP_WM_S_CA_S_O_GT_EI_100_1","RP_WM_S_CA_S_O_GT_EI_100_2","RP_WM_S_CA_S_O_GT_EI_100_3","RP_WM_S_CA_S_O_GT_EI_100_4","RP_WM_S_CA_S_O_GT_EI_100_5","RP_WM_S_CA_S_O_GT_EI_100_6","BP_WM_S_CA_S_O_GT_EI_100_1","BP_WM_S_CA_S_O_GT_EI_100_2","BP_WM_S_CA_S_O_GT_EI_100_3","BP_WM_S_CA_S_O_GT_EI_100_4","BP_WM_S_CA_S_O_GT_EI_100_5","BP_WM_S_CA_S_O_GT_EI_100_6","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_1","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_2","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_3","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_4","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_5","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_6","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_1","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_2","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_3","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_4","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_5","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_6","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_1","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_2","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_3","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_4","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_5","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_6","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_1","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_2","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_3","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_4","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_5","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_100_6","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_1","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_2","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_3","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_4","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_5","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_6","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_1","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_2","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_3","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_4","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_5","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_100_6","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_1","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_2","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_3","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_4","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_5","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_6","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_1","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_2","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_3","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_4","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_5","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_100_6"};
    	  String[] testCASOGTei1000= {"RP_FDT_S_CA_S_O_GT_EI_1000_1","RP_FDT_S_CA_S_O_GT_EI_1000_2","RP_FDT_S_CA_S_O_GT_EI_1000_3","RP_FDT_S_CA_S_O_GT_EI_1000_4","RP_FDT_S_CA_S_O_GT_EI_1000_5","RP_FDT_S_CA_S_O_GT_EI_1000_6","BP_FDT_S_CA_S_O_GT_EI_1000_1","BP_FDT_S_CA_S_O_GT_EI_1000_2","BP_FDT_S_CA_S_O_GT_EI_1000_3","BP_FDT_S_CA_S_O_GT_EI_1000_4","BP_FDT_S_CA_S_O_GT_EI_1000_5","BP_FDT_S_CA_S_O_GT_EI_1000_6","RP_WM_S_CA_S_O_GT_EI_1000_1","RP_WM_S_CA_S_O_GT_EI_1000_2","RP_WM_S_CA_S_O_GT_EI_1000_3","RP_WM_S_CA_S_O_GT_EI_1000_4","RP_WM_S_CA_S_O_GT_EI_1000_5","RP_WM_S_CA_S_O_GT_EI_1000_6","BP_WM_S_CA_S_O_GT_EI_1000_1","BP_WM_S_CA_S_O_GT_EI_1000_2","BP_WM_S_CA_S_O_GT_EI_1000_3","BP_WM_S_CA_S_O_GT_EI_1000_4","BP_WM_S_CA_S_O_GT_EI_1000_5","BP_WM_S_CA_S_O_GT_EI_1000_6","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_1","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_2","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_3","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_4","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_5","RP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_6","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_1","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_2","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_3","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_4","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_5","BP_CL_FDT_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_6","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_1","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_2","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_3","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_4","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_5","RP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_6","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_1","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_2","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_3","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_4","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_5","BP_CL_FDT_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_6","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_1","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_2","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_3","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_4","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_5","RP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_6","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_1","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_2","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_3","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_4","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_5","BP_CL_WM_DS_FDT_S_SC_S_CA_S_O_GT_EI_1000_6","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_1","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_2","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_3","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_4","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_5","RP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_6","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_1","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_2","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_3","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_4","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_5","BP_CL_WM_DS_WM_S_SC_S_CA_S_O_GT_EI_1000_6"};

    	  File f= new File(path+System.getProperty("file.separator")+"bat"+System.getProperty("file.separator")+problem.toUpperCase());
          f.mkdirs();
    	  PrintStream ps= new PrintStream(new FileOutputStream(f.getAbsolutePath()+System.getProperty("file.separator")+batFileName, false));
    	  if (type.equals("QUALITY")) {
        	    for (int n=0; n<options.length; n++) {
              	  ps.println("call QUALITYcv_"+options[n]);
         	    }
          } else if (type.equals("TESTSIMP")) {
      	      for (int n=0; n<testSIMP.length; n++) {
   	            ps.println("call TESTcv30_"+testSIMP[n]);
      	      }
          } else if (type.equals("TESTSIMPSC")) {
      	      for (int n=0; n<testSimpSC.length; n++) {
     	        ps.println("call TESTcv30_"+testSimpSC[n]);
        	  }
          } else if (type.equals("TESTSIMPSCSIMP")) {
      	      for (int n=0; n<testSimpScSIMP.length; n++) {
       	        ps.println("call TESTcv30_"+testSimpScSIMP[n]);
          	  }
          } else if (type.equals("TESTOSWsi")) {
      	      for (int n=0; n<testOSWsi.length; n++) {
       	        ps.println("call TESTcv30_"+testOSWsi[n]);
          	  }
          } else if (type.equals("TESTOSWei")) {
      	      for (int n=0; n<testOSWei.length; n++) {
       	        ps.println("call TESTcv30_"+testOSWei[n]);
          	  }
          } else if (type.equals("TESTOGTsi100")) {
      	      for (int n=0; n<testOGTsi100.length; n++) {
       	        ps.println("call TESTcv30_"+testOGTsi100[n]);
          	  }
          } else if (type.equals("TESTOGTsi1000")) {
      	      for (int n=0; n<testOGTsi1000.length; n++) {
       	        ps.println("call TESTcv30_"+testOGTsi1000[n]);
          	  }
          } else if (type.equals("TESTOGTei100")) {
      	      for (int n=0; n<testOGTei100.length; n++) {
       	        ps.println("call TESTcv30_"+testOGTei100[n]);
          	  }
          } else if (type.equals("TESTOGTei1000")) {
      	      for (int n=0; n<testOGTei1000.length; n++) {
       	        ps.println("call TESTcv30_"+testOGTei1000[n]);
          	  }
          } else if (type.equals("TESTCA")) {
      	      for (int n=0; n<testCA.length; n++) {
     	        ps.println("call TESTcv30_"+testCA[n]);
        	  }
          } else if (type.equals("TESTCASIMP")) {
      	      for (int n=0; n<testCaSIMP.length; n++) {
       	        ps.println("call TESTcv30_"+testCaSIMP[n]);
          	  }
          } else if (type.equals("TESTCASOSWsi")) {
      	      for (int n=0; n<testCASOSWsi.length; n++) {
       	        ps.println("call TESTcv30_"+testCASOSWsi[n]);
          	  }
          } else if (type.equals("TESTCASOSWei")) {
      	      for (int n=0; n<testCASOSWei.length; n++) {
       	        ps.println("call TESTcv30_"+testCASOSWei[n]);
          	  }
          } else if (type.equals("TESTCASOGTsi100")) {
      	      for (int n=0; n<testCASOGTsi100.length; n++) {
       	        ps.println("call TESTcv30_"+testCASOGTsi100[n]);
          	  }
          } else if (type.equals("TESTCASOGTsi1000")) {
      	      for (int n=0; n<testCASOGTsi1000.length; n++) {
       	        ps.println("call TESTcv30_"+testCASOGTsi1000[n]);
          	  }
          } else if (type.equals("TESTCASOGTei100")) {
      	      for (int n=0; n<testCASOGTei100.length; n++) {
       	        ps.println("call TESTcv30_"+testCASOGTei100[n]);
          	  }
          } else if (type.equals("TESTCASOGTei1000")) {
      	      for (int n=0; n<testCASOGTei1000.length; n++) {
       	        ps.println("call TESTcv30_"+testCASOGTei1000[n]);
          	  }
          } else {
    	      ps.println("cd .."+System.getProperty("file.separator")+"..");
      	      ps.println("call cleanTMP");
              if (type.equals("EVAL")) {
            	  for (int k=3; k<8; k=k+2) {
        	        for (int n=0; n<opts.length; n++) {
                	    ps.println("call kbctEVAL "+path+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+problem.toUpperCase()+System.getProperty("file.separator")+"eval"+System.getProperty("file.separator")+"LogQuality_CV_"+problem+k+n+" "+opts[n]);
                	    ps.println("call cleanTMP");
           	        }
            	  }
              } else if (type.equals("EXCEL5")) {
              	    ps.println("call kbctEXCEL "+path+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+problem.toUpperCase()+System.getProperty("file.separator")+"5CV"+System.getProperty("file.separator")+"eval"+System.getProperty("file.separator")+"LogQuality 5");
              	    ps.println("call cleanTMP");
              } else if (type.equals("EXCEL10")) {
            	    ps.println("call kbctEXCEL "+path+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+problem.toUpperCase()+System.getProperty("file.separator")+"10CV"+System.getProperty("file.separator")+"eval"+System.getProperty("file.separator")+"LogQuality 10");
              	    ps.println("call cleanTMP");
              } else if (type.equals("EXCEL30")) {
          	        ps.println("call kbctEXCEL "+path+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+problem.toUpperCase()+System.getProperty("file.separator")+"eval"+System.getProperty("file.separator")+"LogQuality 30");
            	    ps.println("call cleanTMP");
              } else if (type.equals("QUALITY5")) {
        	      for (int n=0; n<5; n++) {
              	    ps.println("call kbctQUALITY "+path+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+problem.toUpperCase()+System.getProperty("file.separator")+"5CV"+System.getProperty("file.separator")+"CV"+n+System.getProperty("file.separator")+problem+"5"+"_CV"+n+".ikb %1");
              	    ps.println("call cleanTMP");
         	      }
              } else if (type.equals("QUALITY10")) {
        	      for (int n=0; n<10; n++) {
              	    ps.println("call kbctQUALITY "+path+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+problem.toUpperCase()+System.getProperty("file.separator")+"10CV"+System.getProperty("file.separator")+"CV"+n+System.getProperty("file.separator")+problem+"10"+"_CV"+n+".ikb %1");
              	    ps.println("call cleanTMP");
         	      }
              } else if (type.equals("QUALITY30")) {
            	  for (int k=3; k<8; k=k+2) {
        	        for (int n=0; n<30; n++) {
              	      ps.println("call kbctQUALITY "+path+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+problem.toUpperCase()+System.getProperty("file.separator")+problem.toUpperCase()+n+System.getProperty("file.separator")+problem+k+n+".ikb %1");
              	      ps.println("call cleanTMP");
         	        }
            	  }
              } else if (type.equals("TEST5")) {
        	      for (int n=0; n<5; n++) {
              	    ps.println("call kbctTEST "+path+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+problem.toUpperCase()+System.getProperty("file.separator")+"5CV"+System.getProperty("file.separator")+"CV"+n+System.getProperty("file.separator")+problem+"5"+"_CV"+n+option+".ikb %1");
              	    ps.println("call cleanTMP");
         	      }
              } else if (type.equals("TEST10")) {
        	      for (int n=0; n<10; n++) {
                      if (option!=null)
              	          ps.println("call kbctTEST "+path+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+problem.toUpperCase()+System.getProperty("file.separator")+"10CV"+System.getProperty("file.separator")+"CV"+n+System.getProperty("file.separator")+problem+"10"+"_CV"+n+option+".ikb %1");
                      else
              	          ps.println("call kbctTEST "+path+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+problem.toUpperCase()+System.getProperty("file.separator")+"10CV"+System.getProperty("file.separator")+"CV"+n+System.getProperty("file.separator")+problem+"10"+"_CV"+n+".ikb %1");
                    	  
              	      ps.println("call cleanTMP");
         	      }
              } else if (type.equals("TEST30")) {
            	  for (int k=3; k<8; k=k+2) {
        	        for (int n=0; n<30; n++) {
                      if (option!=null)
              	          ps.println("call kbctTEST "+path+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+problem.toUpperCase()+System.getProperty("file.separator")+problem.toUpperCase()+n+System.getProperty("file.separator")+problem+k+n+option+".ikb %1");
                      else
              	          ps.println("call kbctTEST "+path+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+problem.toUpperCase()+System.getProperty("file.separator")+problem.toUpperCase()+n+System.getProperty("file.separator")+problem+k+n+".ikb %1");
                    	  
              	      ps.println("call cleanTMP");
         	        }
            	  }
              } else if (type.equals("SELECT")) {
           	        ps.println("call kbctSELECT "+path+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+problem.toUpperCase()+System.getProperty("file.separator")+"eval"+System.getProperty("file.separator")+"LogEval_CV "+problem+"30");
              	    ps.println("call cleanTMP");
              } else if (type.equals("BOOTSTRAP")) {
       	            ps.println("call kbctBOOTSTRAP InputBOOTSTRAP.txt");
          	        ps.println("call cleanTMP");
              } else if (type.equals("CHECK")) {
         	        ps.println("call kbctCHECK LogSimplify");
            	    ps.println("call cleanTMP");
              }
      	      ps.println("cd ."+System.getProperty("file.separator")+"bat"+System.getProperty("file.separator")+problem.toUpperCase());
          }
      	  ps.flush();
          ps.close();
      } catch (Throwable t) {
     	  //t.printStackTrace();
    	  MessageKBCT.Error(null, t);
      }
  }
//------------------------------------------------------------------------------
  /**
   * Write a bat file
   */
  public static void buildBATfile(String path, String batFileName, String problem, String message) {
      try {
          File f= new File(path+System.getProperty("file.separator")+"bat"+System.getProperty("file.separator")+problem.toUpperCase());
          f.mkdirs();
    	  PrintStream ps= new PrintStream(new FileOutputStream(f.getAbsolutePath()+System.getProperty("file.separator")+batFileName, false));
          if (message.equals("interpCONF")) {
        	  PrintStream psconf= new PrintStream(new FileOutputStream(f.getAbsolutePath()+System.getProperty("file.separator")+"conf.bat", false));
   	          ps.println("cd .."+System.getProperty("file.separator")+".."+System.getProperty("file.separator")+"..");
 	          ps.println("call cleanTMP");
              int cont211=0;
              String[] KBint211={"KBint211A.kb","KBint211B.kb","KBint211C.kb","KBint211D.kb"};
              int cont2=0;
              String[] KBint2={"KBint2A.kb","KBint2B.kb","KBint2C.kb"};
              int cont213=0;
              String[] KBint213={"KBint213A.kb","KBint213B.kb","KBint213C.kb"};
              int cont223=0;
              String[] KBint223={"KBint223A.kb","KBint223B.kb","KBint223C.kb"};
 	          for (int n=1; n<=216; n++) {
       	           ps.println("call kbctINTERPRETABILITY C:"+System.getProperty("file.separator")+"Development"+System.getProperty("file.separator")+"GUAJE1"+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+"Interpretability"+System.getProperty("file.separator")+"exp5"+System.getProperty("file.separator")+"conf"+n+""+System.getProperty("file.separator")+"wine.ikb CLFDTDSFDT");
        	       ps.println("call cleanTMP");
        	       ps.println("call kbctINTERPRETABILITY C:"+System.getProperty("file.separator")+"Development"+System.getProperty("file.separator")+"GUAJE1"+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+"Interpretability"+System.getProperty("file.separator")+"exp5"+System.getProperty("file.separator")+"conf"+n+""+System.getProperty("file.separator")+"wine.ikb CLFDTDSFDTsimp");
        	       ps.println("call cleanTMP");
        	       ps.println("call kbctINTERPRETABILITY C:"+System.getProperty("file.separator")+"Development"+System.getProperty("file.separator")+"GUAJE1"+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+"Interpretability"+System.getProperty("file.separator")+"exp5"+System.getProperty("file.separator")+"conf"+n+""+System.getProperty("file.separator")+"wine.ikb CLFDTDSWM");
        	       ps.println("call cleanTMP");
        	       ps.println("call kbctINTERPRETABILITY C:"+System.getProperty("file.separator")+"Development"+System.getProperty("file.separator")+"GUAJE1"+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+"Interpretability"+System.getProperty("file.separator")+"exp5"+System.getProperty("file.separator")+"conf"+n+""+System.getProperty("file.separator")+"wine.ikb CLFDTDSWMsimp");
        	       ps.println("call cleanTMP");
        	       ps.println("call kbctINTERPRETABILITY C:"+System.getProperty("file.separator")+"Development"+System.getProperty("file.separator")+"GUAJE1"+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+"Interpretability"+System.getProperty("file.separator")+"exp5"+System.getProperty("file.separator")+"conf"+n+""+System.getProperty("file.separator")+"wine.ikb CLWMDSFDT");
        	       ps.println("call cleanTMP");
        	       ps.println("call kbctINTERPRETABILITY C:"+System.getProperty("file.separator")+"Development"+System.getProperty("file.separator")+"GUAJE1"+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+"Interpretability"+System.getProperty("file.separator")+"exp5"+System.getProperty("file.separator")+"conf"+n+""+System.getProperty("file.separator")+"wine.ikb CLWMDSFDTsimp");
        	       ps.println("call cleanTMP");
        	       ps.println("call kbctINTERPRETABILITY C:"+System.getProperty("file.separator")+"Development"+System.getProperty("file.separator")+"GUAJE1"+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+"Interpretability"+System.getProperty("file.separator")+"exp5"+System.getProperty("file.separator")+"conf"+n+""+System.getProperty("file.separator")+"wine.ikb CLWMDSWM");
        	       ps.println("call cleanTMP");
        	       ps.println("call kbctINTERPRETABILITY C:"+System.getProperty("file.separator")+"Development"+System.getProperty("file.separator")+"GUAJE1"+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+"Interpretability"+System.getProperty("file.separator")+"exp5"+System.getProperty("file.separator")+"conf"+n+""+System.getProperty("file.separator")+"wine.ikb CLWMDSWMsimp");
        	       ps.println("call cleanTMP");
        	       ps.println("call kbctINTERPRETABILITY C:"+System.getProperty("file.separator")+"Development"+System.getProperty("file.separator")+"GUAJE1"+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+"Interpretability"+System.getProperty("file.separator")+"exp5"+System.getProperty("file.separator")+"conf"+n+""+System.getProperty("file.separator")+"wine.ikb FDTsimp");
        	       ps.println("call cleanTMP");
        	       ps.println("call kbctINTERPRETABILITY C:"+System.getProperty("file.separator")+"Development"+System.getProperty("file.separator")+"GUAJE1"+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+"Interpretability"+System.getProperty("file.separator")+"exp5"+System.getProperty("file.separator")+"conf"+n+""+System.getProperty("file.separator")+"wine.ikb WMsimp");
        	       ps.println("call cleanTMP");
        	       ps.println("call kbctINTERPRETABILITY C:"+System.getProperty("file.separator")+"Development"+System.getProperty("file.separator")+"GUAJE1"+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+"Interpretability"+System.getProperty("file.separator")+"exp5"+System.getProperty("file.separator")+"conf"+n+""+System.getProperty("file.separator")+"wine.ikb FDTP");
        	       ps.println("call cleanTMP");
        	       ps.println("call kbctINTERPRETABILITY C:"+System.getProperty("file.separator")+"Development"+System.getProperty("file.separator")+"GUAJE1"+System.getProperty("file.separator")+"KB"+System.getProperty("file.separator")+"Interpretability"+System.getProperty("file.separator")+"exp5"+System.getProperty("file.separator")+"conf"+n+""+System.getProperty("file.separator")+"wine.ikb FDTPsimp");
        	       ps.println("call cleanTMP");
                   ///////////////////////////////////////////////////////
        	       psconf.println("mkdir conf"+n);
        	       psconf.println("copy KBint1.kb conf"+n);
        	       //psconf.println("copy KBint2.kb conf"+n);
        	       psconf.println("copy "+KBint2[cont2]+" conf"+n);
        	       psconf.println("cd conf"+n);
        	       psconf.println("rename "+KBint2[cont2]+" KBint2.kb");
        	       psconf.println("cd ..");
        	       psconf.println("copy KBint3.kb conf"+n);
        	       psconf.println("copy KBint4.kb conf"+n);
        	       //psconf.println("copy KBint211.kb conf"+n);
        	       psconf.println("copy "+KBint211[cont211]+" conf"+n);
        	       psconf.println("cd conf"+n);
        	       psconf.println("rename "+KBint211[cont211]+" KBint211.kb");
        	       psconf.println("cd ..");
        	       psconf.println("copy KBint212.kb conf"+n);
        	       //psconf.println("copy KBint213.kb conf"+n);
        	       psconf.println("copy "+KBint213[cont213]+" conf"+n);
        	       psconf.println("cd conf"+n);
        	       psconf.println("rename "+KBint213[cont213]+" KBint213.kb");
        	       psconf.println("cd ..");
        	       psconf.println("copy KBint221.kb conf"+n);
        	       psconf.println("copy KBint222.kb conf"+n);
        	       //psconf.println("copy KBint223.kb conf"+n);
        	       psconf.println("copy "+KBint223[cont223]+" conf"+n);
        	       psconf.println("cd conf"+n);
        	       psconf.println("rename "+KBint223[cont223]+" KBint223.kb");
        	       psconf.println("cd ..");
        	       psconf.println("copy wine-5x2-1tra.txt conf"+n);
        	       psconf.println("copy wine-5x2-1tra-CLFDTDSFDTsimp.txt conf"+n);
        	       psconf.println("copy wine-5x2-1tra-CLFDTDSWMsimp.txt conf"+n);
        	       psconf.println("copy wine-5x2-1tra-CLWMDSFDTsimp.txt conf"+n);
        	       psconf.println("copy wine-5x2-1tra-CLWMDSWMsimp.txt conf"+n);
        	       psconf.println("copy wine-5x2-1tra-FDTPsimp.txt conf"+n);
        	       psconf.println("copy wine-5x2-1tra-FDTsimp.txt conf"+n);
        	       psconf.println("copy wine-5x2-1tra-WMsimp.txt conf"+n);
        	       psconf.println("copy wine-5x2-1tst.txt conf"+n);
        	       psconf.println("copy wine.Partitions.kb conf"+n);
        	       psconf.println("copy wineCLFDTDSFDT.kb conf"+n);
        	       psconf.println("copy wineCLFDTDSFDTsimp.kb conf"+n);
        	       psconf.println("copy wineCLFDTDSWM.kb conf"+n);
        	       psconf.println("copy wineCLFDTDSWMsimp.kb conf"+n);
        	       psconf.println("copy wineCLWMDSFDT.kb conf"+n);
        	       psconf.println("copy wineCLWMDSFDTsimp.kb conf"+n);
        	       psconf.println("copy wineCLWMDSWM.kb conf"+n);
        	       psconf.println("copy wineCLWMDSWMsimp.kb conf"+n);
        	       psconf.println("copy wineFDTP.kb conf"+n);
        	       psconf.println("copy wineFDTPsimp.kb conf"+n);
        	       psconf.println("copy wineFDTsimp.kb conf"+n);
        	       psconf.println("copy wineWMsimp.kb conf"+n);
                   cont223++;
                   if (cont223==3) {
                	   cont223=0;
                	   cont213++;
                	   if (cont213==3) {
                		   cont213=0;
                		   cont2++;
                		   if (cont2==3) {
                			   cont2=0;
                			   cont211++;
                			   if (cont211==4)
                				   cont211=0;
                		   }
                	   }
                   }
 	          }
   	          ps.println("cd ."+System.getProperty("file.separator")+"bat"+System.getProperty("file.separator")+"INTERP"+System.getProperty("file.separator")+"EXP5");
   	      	  psconf.flush();
   	          psconf.close();
          } else {
    	      ps.println(message);
          }
      	  ps.flush();
          ps.close();
      } catch (Throwable t) {
     	  //t.printStackTrace();
    	  MessageKBCT.Error(null, t);
      }
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with two options to make induction:<br>
   * <ul>
   *    <li> Partitions: launch a window with the list of variables. </li>
   *    <li> Rules: launch a window with the options of the algorithm. </li>
   * </ul>
   */
  public static int ChooseOptimization( Component parent ) {
    Object[] options = { LocaleKBCT.GetString("PartitionOptimization"), LocaleKBCT.GetString("RuleSelection"), LocaleKBCT.GetString("Cancel") };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, LocaleKBCT.GetString("Choose_one_option"),
                               LocaleKBCT.GetString("Optimization"),
                               JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                               null, options, options[0]);
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with two options to make induction:<br>
   * <ul>
   *    <li> Partitions: launch a window with the list of variables. </li>
   *    <li> Rules: launch a window with the options of the algorithm. </li>
   * </ul>
   */
  public static int PartitionOptimizationAlgorithm( Component parent ) {
    Object[] options = { LocaleKBCT.GetString("GeneticTuning"), LocaleKBCT.GetString("SolisWetts"), LocaleKBCT.GetString("Cancel") };
    JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
    return JOptionPane.showOptionDialog(parent, LocaleKBCT.GetString("Choose_one_option"),
                               LocaleKBCT.GetString("Optimization"),
                               JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                               null, options, options[0]);
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of ranking options:
   * <ul>
   *    <li> Output class (Default)</li>
   *    <li> Rule weight (accuracy) </li>
   *    <li> Rule weight (local interpretability) </li>
   *    <li> Rule weight (global interpretability) </li>
   *    <li> Rule weight (local + global interpretability) </li>
   *    <li> Number of premises </li>
   *    <li> Reverse order </li>
   * </ul>
   */
  public static Object RankingOption(Component parentComponent) {
	  Object[] possibleValues= new Object[9];
      possibleValues[0]=LocaleKBCT.GetString("OrderByOutput");
      possibleValues[1]=LocaleKBCT.GetString("OrderByLocalWeight");
      possibleValues[2]=LocaleKBCT.GetString("OrderByGlobalWeight");
      possibleValues[3]=LocaleKBCT.GetString("OrderByWeight");
      possibleValues[4]=LocaleKBCT.GetString("OrderByLocalInterpretability");
      possibleValues[5]=LocaleKBCT.GetString("OrderByGlobalInterpretability");
      possibleValues[6]=LocaleKBCT.GetString("OrderByInterpretability");
      possibleValues[7]=LocaleKBCT.GetString("OrderByNbPremises");
      possibleValues[8]=LocaleKBCT.GetString("ReverseOrder");
      
      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
        LocaleKBCT.GetString("Choose_one_option_for_rule_ranking"),
        LocaleKBCT.GetString("RuleRanking"),
        JOptionPane.QUESTION_MESSAGE,
        null,
        possibleValues,
        possibleValues[0]);
      return selectedValue;
  }
//------------------------------------------------------------------------------
  /**
   * Display a window with the list of output classes. 
   */
  public static Object SelectOutputClass( Component parentComponent, JKBCT kbct) {
      int NbOutputClasses= 1;
	  if (kbct!=null) {
	    	NbOutputClasses= kbct.GetOutput(1).GetLabelsNumber();
      }
      Object[] possibleValues= new Object[NbOutputClasses+1];
      for (int n=0; n<possibleValues.length-1; n++) {
           possibleValues[n]= n+1;
      }
      possibleValues[possibleValues.length-1]= LocaleKBCT.GetString("all");
      JOptionPane.setDefaultLocale(LocaleKBCT.Locale());
      Object selectedValue= JOptionPane.showInputDialog(parentComponent,
                   LocaleKBCT.GetString("Choose_one_variable"), LocaleKBCT.GetString("Induce_Partitions"),
                   JOptionPane.QUESTION_MESSAGE, null,
                   possibleValues, possibleValues[0]);
      return selectedValue;
 }
}