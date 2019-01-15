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
//                              ConfigKBCT.java
//
//
//**********************************************************************

package kbct;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import kbctAux.MessageKBCT;

/**
 * kbct.ConfigKBCT stores user configuration (language, look, user, ...)
 *
 *@author     Jose Maria Alonso Moral
 *@version    3.0 , 27/07/15
 */
//------------------------------------------------------------------------------
public class ConfigKBCT {
  private Properties p;
  private FileInputStream f;
  private String kbctpath;
  private static String config_name= System.getProperty("user.dir") + System.getProperty("file.separator") + "guaje.cnf";
//------------------------------------------------------------------------------
  public ConfigKBCT() {
	this.p= new Properties();
    try {
      // open config file
   	  //System.out.println("kbctpath -> "+kbctpath);
   	  //System.out.println("user.dir -> "+System.getProperty("user.dir"));
      File fc= new File(config_name);
  	  //System.out.println("fc -> "+fc.getAbsolutePath());
      if (!fc.exists())
    	  fc.createNewFile();
    	  
      this.f= new FileInputStream(config_name);
      this.p.load(this.f);
    } catch( Exception e ) {
    	e.printStackTrace();
    }
  }
//------------------------------------------------------------------------------
  /**
   * Store current configuration properties.
   */
  public void Save() {
    try { p.store(new FileOutputStream(config_name),"KBCT Config File"); }
    catch( Exception e ) { MessageKBCT.Error(null, e ); }
  }
//------------------------------------------------------------------------------
  /**
   * Set new decnumber.
   */
  public void SetNumberOfDecimals( int decnumber ) { p.setProperty("Decnumber", ""+decnumber); Save(); }
//------------------------------------------------------------------------------
  /**
   * Return current decnumber.
   */
  public int GetNumberOfDecimals() { return (new Integer(p.getProperty("Decnumber", ""+LocaleKBCT.DefaultNumberOfDecimals()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set new selected language.
   */
  public void SetLanguage( String language ) { p.setProperty("Language", language); Save(); }
//------------------------------------------------------------------------------
  /**
   * Return current language.
   */
  public String GetLanguage() { return p.getProperty("Language", LocaleKBCT.DefaultLanguage()); }
//------------------------------------------------------------------------------
  /**
   * set new selected country.
   */
  public void SetCountry( String country ) { p.setProperty("Country", country); Save(); }
//------------------------------------------------------------------------------
  /**
   * Return current country.
   */
  public String GetCountry() { return p.getProperty("Country", LocaleKBCT.DefaultCountry()); }
//------------------------------------------------------------------------------
  /**
   * Set new selected path for Ontology files.
   */
  public void SetOntologyPath( String Ontology_path_file ) { p.setProperty("OntologyFilePath", Ontology_path_file); Save(); }
//------------------------------------------------------------------------------
  /**
   * Return current path for Ontology files.
   */
  public String GetOntologyPath() { return p.getProperty("OntologyFilePath", System.getProperty("user.dir")); }
//------------------------------------------------------------------------------
  /**
   * Set new selected path for KBCT files.
   */
  public void SetKBCTFilePath( String KBCT_path_file ) { p.setProperty("KBCTFilePath", KBCT_path_file); Save(); }
//------------------------------------------------------------------------------
  /**
   * Return current path for KBCT files.
   */
  public String GetKBCTFilePath() { return p.getProperty("KBCTFilePath", System.getProperty("user.dir")); }
//------------------------------------------------------------------------------
  /**
   * Set new selected paths for KBCT interpretability files.
   */
  public void SetKBCTintFilePath( String[] KBCT_int_path_files ) { 
	     p.setProperty("KBCTintFilePath1", KBCT_int_path_files[0]); Save();
	     p.setProperty("KBCTintFilePath211", KBCT_int_path_files[1]); Save();
	     p.setProperty("KBCTintFilePath212", KBCT_int_path_files[2]); Save();
	     p.setProperty("KBCTintFilePath213", KBCT_int_path_files[3]); Save();
	     p.setProperty("KBCTintFilePath221", KBCT_int_path_files[4]); Save();
	     p.setProperty("KBCTintFilePath222", KBCT_int_path_files[5]); Save();
	     p.setProperty("KBCTintFilePath223", KBCT_int_path_files[6]); Save();
	     p.setProperty("KBCTintFilePath2", KBCT_int_path_files[7]); Save();
	     p.setProperty("KBCTintFilePath3", KBCT_int_path_files[8]); Save();
	     p.setProperty("KBCTintFilePath4", KBCT_int_path_files[9]); Save();
      /*for (int n=0; n<KBCT_int_path_files.length; n++) {
	     p.setProperty("KBCTintFilePath"+String.valueOf(n+1), KBCT_int_path_files[n]); Save();
      }*/
  }
//------------------------------------------------------------------------------
  /**
   * Return current paths for KBCT interpretability files.
   */
  public String[] GetKBCTintFilePath() { 
     String[] paths= new String[10];
     paths[0]= p.getProperty("KBCTintFilePath1", LocaleKBCT.DefaultKBCTintPath()[0]);
     paths[1]= p.getProperty("KBCTintFilePath211", LocaleKBCT.DefaultKBCTintPath()[1]);
     paths[2]= p.getProperty("KBCTintFilePath212", LocaleKBCT.DefaultKBCTintPath()[2]);
     paths[3]= p.getProperty("KBCTintFilePath213", LocaleKBCT.DefaultKBCTintPath()[3]);
     paths[4]= p.getProperty("KBCTintFilePath221", LocaleKBCT.DefaultKBCTintPath()[4]);
     paths[5]= p.getProperty("KBCTintFilePath222", LocaleKBCT.DefaultKBCTintPath()[5]);
     paths[6]= p.getProperty("KBCTintFilePath223", LocaleKBCT.DefaultKBCTintPath()[6]);
     paths[7]= p.getProperty("KBCTintFilePath2", LocaleKBCT.DefaultKBCTintPath()[7]);
     paths[8]= p.getProperty("KBCTintFilePath3", LocaleKBCT.DefaultKBCTintPath()[8]);
     paths[9]= p.getProperty("KBCTintFilePath4", LocaleKBCT.DefaultKBCTintPath()[9]);
     /*for (int n=0; n<paths.length; n++) {
	     paths[n]= p.getProperty("KBCTintFilePath"+String.valueOf(n+1), LocaleKBCT.DefaultKBCTintPath()[n]);
	     //paths[n]= p.getProperty("KBCTintFilePath"+String.valueOf(n+1), System.getProperty("user.dir"));
     }*/
     return paths;
  }
//------------------------------------------------------------------------------
  /**
   * Set new selected path for KBCT interpretability file n.
   */
  public void SetKBCTintFilePath( String KBCT_int_path_file, int number ) { 
	 p.setProperty("KBCTintFilePath"+number, KBCT_int_path_file); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return current path for KBCT interpretability file n.
   */
  public String GetKBCTintFilePath(int number, int index) { 
      //System.out.println("GetKBCTintFilePath: "+number);
	  return p.getProperty("KBCTintFilePath"+number, LocaleKBCT.DefaultKBCTintPath()[index]);
	 //return p.getProperty("KBCTintFilePath"+number, System.getProperty("user.dir"));
  }
//------------------------------------------------------------------------------
  /**
   * Set new selected conjunction operators for KBCT interpretability files.
   */
  public void SetKBCTintConjunction( String[] conj ) { 
      for (int n=0; n<conj.length; n++) {
	     p.setProperty("KBCTintConjunction"+String.valueOf(n+1), conj[n]); Save();
      }
  }
//------------------------------------------------------------------------------
  /**
   * Return current conjunction operators for KBCT interpretability files.
   */
  public String[] GetKBCTintConjunction() { 
     String[] conjs= new String[10];
     for (int n=0; n<conjs.length; n++) {
	     conjs[n]= p.getProperty("KBCTintConjunction"+String.valueOf(n+1), LocaleKBCT.DefaultKBCTintConjunction()[n]);
     }
     return conjs;
  }
//------------------------------------------------------------------------------
  /**
   * Set new selected conjunction operator for KBCT interpretability file n.
   */
  public void SetKBCTintConjunction( String KBCT_int_conj, int number ) { 
	 p.setProperty("KBCTintConjunction"+number, KBCT_int_conj); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return current conjunction operator for KBCT interpretability file n.
   */
  public String GetKBCTintConjunction(int number) { 
	 return p.getProperty("KBCTintConjunction"+number, LocaleKBCT.DefaultKBCTintConjunction()[number-1]);
  }
//------------------------------------------------------------------------------
  /**
   * Set new selected disjunction operators for KBCT interpretability files.
   */
  public void SetKBCTintDisjunction( String[] disj ) { 
      for (int n=0; n<disj.length; n++) {
	     p.setProperty("KBCTintDisjunction"+String.valueOf(n+1), disj[n]); Save();
      }
  }
//------------------------------------------------------------------------------
  /**
   * Return current disjunction operators for KBCT interpretability files.
   */
  public String[] GetKBCTintDisjunction() { 
     String[] disjs= new String[10];
     for (int n=0; n<disjs.length; n++) {
    	 disjs[n]= p.getProperty("KBCTintDisjunction"+String.valueOf(n+1), LocaleKBCT.DefaultKBCTintDisjunction()[n]);
     }
     return disjs;
  }
//------------------------------------------------------------------------------
  /**
   * Set new selected disjunction operator for KBCT interpretability file n.
   */
  public void SetKBCTintDisjunction( String KBCT_int_disj, int number ) { 
	 p.setProperty("KBCTintDisjunction"+number, KBCT_int_disj); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return current disjunction operator for KBCT interpretability file n.
   */
  public String GetKBCTintDisjunction(int number) { 
	 return p.getProperty("KBCTintDisjunction"+number, LocaleKBCT.DefaultKBCTintDisjunction()[number-1]);
  }
//------------------------------------------------------------------------------
  /**
   * Set new selected defuzzification operators for KBCT interpretability files.
   */
  public void SetKBCTintDefuzzification( String[] defuzz ) { 
      for (int n=0; n<defuzz.length; n++) {
	     p.setProperty("KBCTintDefuzzification"+String.valueOf(n+1), defuzz[n]); Save();
      }
  }
//------------------------------------------------------------------------------
  /**
   * Return current defuzzification operators for KBCT interpretability files.
   */
  public String[] GetKBCTintDefuzzification() { 
     String[] defuzz= new String[10];
     for (int n=0; n<defuzz.length; n++) {
    	 defuzz[n]= p.getProperty("KBCTintDefuzzification"+String.valueOf(n+1), LocaleKBCT.DefaultKBCTintDefuzzification()[n]);
     }
     return defuzz;
  }
//------------------------------------------------------------------------------
  /**
   * Set new selected defuzzification operator for KBCT interpretability file n.
   */
  public void SetKBCTintDefuzzification( String KBCT_int_defuzz, int number ) { 
	 p.setProperty("KBCTintDefuzzification"+number, KBCT_int_defuzz); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return current defuzzification operator for KBCT interpretability file n.
   */
  public String GetKBCTintDefuzzification(int number) { 
	 return p.getProperty("KBCTintDefuzzification"+number, LocaleKBCT.DefaultKBCTintDefuzzification()[number-1]);
  }
//------------------------------------------------------------------------------
  /**
   * Set new selected path for Data files.
   */
  public void SetDataFilePath( String data_path_file ) throws Throwable {
    jnikbct.SetDataFilePath(data_path_file + System.getProperty("file.separator"));
    p.setProperty("DataFilePath", data_path_file);
    Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return current path for Data files.
   */
  public String GetDataFilePath() {
    return p.getProperty("DataFilePath", System.getProperty("user.dir"));
  }
//------------------------------------------------------------------------------
  /**
   * Set new selected Look and Feel.
   */
  public void SetLookAndFeel( String laf ) { p.setProperty("LookAndFeel", laf); Save(); }
//------------------------------------------------------------------------------
  /**
   * Return current Look and Feel.
   */
  public String GetLookAndFeel() { return p.getProperty("LookAndFeel", LocaleKBCT.DefaultLookAndFeel()); }
//------------------------------------------------------------------------------
  /**
   * Set new selected user.
   */
  public void SetUser( String user ) {
    p.setProperty("User", user); Save();
    this.SetUserChange("Yes");
  }
//------------------------------------------------------------------------------
  /**
   * Return current user.
   */
  public String GetUser() { return p.getProperty("User", LocaleKBCT.DefaultUser()); }
//------------------------------------------------------------------------------
  /**
   * Save a change in the type of user
   */
  public void SetUserChange( String change ) { p.setProperty("UserChange", change); Save(); }
//------------------------------------------------------------------------------
  /**
   * Auxiliar method used in order to detect the change of user between Beginner and Expert.
   */
  public String GetUserChange() {
    String uc= p.getProperty("UserChange", LocaleKBCT.DefaultUserChange());
    this.SetUserChange("No");
    return uc;
  }
//------------------------------------------------------------------------------
  public String GetKbctPath() {
	//System.out.println("guajepath -> "+System.getProperty("guajepath"));
  	if (System.getProperty("guajepath")==null) { 
	    String GUAJEPATH= System.getProperty("user.dir");
	    if (GUAJEPATH.startsWith("Archivos de programa (x86)",3))
	    	GUAJEPATH= GUAJEPATH.replaceFirst("Archivos de programa (x86)","Archiv~2");
	    else if (GUAJEPATH.startsWith("Archivos de programa",3))
	    	GUAJEPATH= GUAJEPATH.replaceFirst("Archivos de programa","Archiv~1");
	    else if (GUAJEPATH.startsWith("Program Files (x86)",3))
	    	GUAJEPATH= GUAJEPATH.replaceFirst("Program Files (x86)","Progra~2");
	    else if (GUAJEPATH.startsWith("Program Files",3))
	    	GUAJEPATH= GUAJEPATH.replaceFirst("Program Files","Progra~1");
	    else if (GUAJEPATH.startsWith("Documents and Settings",3))
	    	GUAJEPATH= GUAJEPATH.replaceFirst("Documents and Settings","Docume~1");
	  
        return GUAJEPATH;
  	} else {
  		return System.getProperty("guajepath");
  	}
  }
//------------------------------------------------------------------------------
// Parameters
//------------------------------------------------------------------------------
  /**
   * Set SMOTE flag.
   */
  public void SetSMOTE( boolean sm ) {
    p.setProperty("SMOTE", ""+sm); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return SMOTE flag.
   */
  public boolean GetSMOTE() { return (new Boolean(p.getProperty("SMOTE", ""+LocaleKBCT.DefaultSMOTE()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set new Number of Neighbors (k-NN).
   */
  public void SetSMOTEnumberOfNeighbors( int nn ) { p.setProperty("NNeighbors", ""+nn); Save(); }
//------------------------------------------------------------------------------
  /**
   * Return current Number of Neighbors (k-NN).
   */
  public int GetSMOTEnumberOfNeighbors() { return (new Integer(p.getProperty("NNeighbors", ""+LocaleKBCT.DefaultSMOTEnumberOfNeighbors()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set new Type of SMOTE.
   */
  public void SetSMOTEtype( String st ) { p.setProperty("Stype", ""+st); Save(); }
//------------------------------------------------------------------------------
  /**
   * Return current Type of SMOTE.
   */
  public String GetSMOTEtype() { return p.getProperty("Stype", ""+LocaleKBCT.DefaultSMOTEtype()); }
//------------------------------------------------------------------------------
  /**
   * Set new Balancing for SMOTE.
   */
  public void SetSMOTEbalancing( boolean sb ) { p.setProperty("Sbalancing", ""+sb); Save(); }
//------------------------------------------------------------------------------
  /**
   * Return current Balancing for SMOTE.
   */
  public boolean GetSMOTEbalancing() { return (new Boolean(p.getProperty("Sbalancing", ""+LocaleKBCT.DefaultSMOTEbalancing()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set new Balancing ALL for SMOTE.
   */
  public void SetSMOTEbalancingALL( boolean sba ) { p.setProperty("SbalancingALL", ""+sba); Save(); }
//------------------------------------------------------------------------------
  /**
   * Return current Balancing ALL for SMOTE.
   */
  public boolean GetSMOTEbalancingALL() { return (new Boolean(p.getProperty("SbalancingALL", ""+LocaleKBCT.DefaultSMOTEbalancingALL()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Quantity of generated examples for SMOTE.
   */
  public void SetSMOTEquantity( double qge ) {
    p.setProperty("Squantity", ""+qge); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Quantity of generated examples for SMOTE.
   */
  public double GetSMOTEquantity() { return (new Double(p.getProperty("Squantity", ""+LocaleKBCT.DefaultSMOTEquantity()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set new Distance for SMOTE.
   */
  public void SetSMOTEdistance( String sd ) { p.setProperty("Sdistance", ""+sd); Save(); }
//------------------------------------------------------------------------------
  /**
   * Return current Distance for SMOTE.
   */
  public String GetSMOTEdistance() { return p.getProperty("Sdistance", ""+LocaleKBCT.DefaultSMOTEdistance()); }
//------------------------------------------------------------------------------
  /**
   * Set new Interpolation for SMOTE.
   */
  public void SetSMOTEinterpolation( String si ) {
	  //System.out.println("ConfigKBCT: SetSMOTEinterpolation="+si);
	  p.setProperty("Sinterpolation", ""+si); Save(); 
  }
//------------------------------------------------------------------------------
  /**
   * Return current Interpolation for SMOTE.
   */
  public String GetSMOTEinterpolation() { return p.getProperty("Sinterpolation", ""+LocaleKBCT.DefaultSMOTEinterpolation()); }
//------------------------------------------------------------------------------
  /**
   * Set Alpha for SMOTE.
   */
  public void SetSMOTEalpha( double sa ) {
    p.setProperty("Salpha", ""+sa); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Alpha for SMOTE.
   */
  public double GetSMOTEalpha() { return (new Double(p.getProperty("Salpha", ""+LocaleKBCT.DefaultSMOTEalpha()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Mu for SMOTE.
   */
  public void SetSMOTEmu( double sm ) {
    p.setProperty("Smu", ""+sm); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Mu for SMOTE.
   */
  public double GetSMOTEmu() { return (new Double(p.getProperty("Smu", ""+LocaleKBCT.DefaultSMOTEmu()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set InducePartitions flag.
   */
  public void SetInducePartitions( boolean ip ) {
    p.setProperty("InducePartitions", ""+ip); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return InducePartitions flag.
   */
  public boolean GetInducePartitions() { return (new Boolean(p.getProperty("InducePartitions", ""+LocaleKBCT.DefaultInducePartitions()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set InductionType flag.
   */
  public void SetInductionType( String it ) {
    p.setProperty("InductionType", ""+it); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return InductionType flag.
   */
  public String GetInductionType() { return p.getProperty("InductionType", ""+LocaleKBCT.DefaultInductionType()); }
//------------------------------------------------------------------------------
  /**
   * Set InductionNbLabels flag.
   */
  public void SetInductionNbLabels( int it ) {
    p.setProperty("InductionNbLabels", ""+it); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return InductionNbLabels flag.
   */
  public int GetInductionNbLabels() { return (new Integer(p.getProperty("InductionNbLabels", ""+LocaleKBCT.DefaultInductionNbLabels()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set PartitionSelection flag.
   */
  public void SetPartitionSelection( boolean ps ) {
    p.setProperty("PartitionSelection", ""+ps); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return PartitionSelection flag.
   */
  public boolean GetPartitionSelection() { return (new Boolean(p.getProperty("PartitionSelection", ""+LocaleKBCT.DefaultPartitionSelection()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set FeatureSelection flag.
   */
  public void SetFeatureSelection( boolean fs ) {
    p.setProperty("FeatureSelection", ""+fs); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return FeatureSelection flag.
   */
  public boolean GetFeatureSelection() { return (new Boolean(p.getProperty("FeatureSelection", ""+LocaleKBCT.DefaultFeatureSelection()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set default C45 parameters (weka), P1.
   */
  public void SetFeatureSelectionC45P1( String p1 ) {
    p.setProperty("FeatureSelectionC45P1", ""+p1); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return default C45 parameters (weka), P1.
   */
  public String GetFeatureSelectionC45P1() { return p.getProperty("FeatureSelectionC45P1", LocaleKBCT.DefaultFeatureSelectionC45P1()); }
//------------------------------------------------------------------------------
  /**
   * Set default C45 parameters (weka), P2.
   */
  public void SetFeatureSelectionC45P2( String p2 ) {
    p.setProperty("FeatureSelectionC45P2", ""+p2); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return default C45 parameters (weka), P2.
   */
  public String GetFeatureSelectionC45P2() { return p.getProperty("FeatureSelectionC45P2", LocaleKBCT.DefaultFeatureSelectionC45P2()); }
//------------------------------------------------------------------------------
  /**
   * Set default C45 parameters (weka), P3.
   */
  public void SetFeatureSelectionC45P3( String p3 ) {
    p.setProperty("FeatureSelectionC45P3", ""+p3); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return default C45 parameters (weka), P3.
   */
  public String GetFeatureSelectionC45P3() { return p.getProperty("FeatureSelectionC45P3", LocaleKBCT.DefaultFeatureSelectionC45P3()); }
//------------------------------------------------------------------------------
  /**
   * Set default C45 parameters (weka), P4.
   */
  public void SetFeatureSelectionC45P4( String p4 ) {
    p.setProperty("FeatureSelectionC45P4", ""+p4); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return default C45 parameters (weka), P4.
   */
  public String GetFeatureSelectionC45P4() { return p.getProperty("FeatureSelectionC45P4", LocaleKBCT.DefaultFeatureSelectionC45P4()); }
//------------------------------------------------------------------------------
  /**
   * Set Distance.
   */
  public void SetDistance( String d ) {
    p.setProperty("Distance", d); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Distance.
   */
  public String GetDistance() { return p.getProperty("Distance", LocaleKBCT.DefaultDistance()); }
//------------------------------------------------------------------------------
  /**
   * Set DataSelection flag.
   */
  public void SetDataSelection( boolean ds ) {
    p.setProperty("DataSelection", ""+ds); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return DataSelection flag.
   */
  public boolean GetDataSelection() { return (new Boolean(p.getProperty("DataSelection", ""+LocaleKBCT.DefaultDataSelection()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set TolThresDataSelection.
   */
  public void SetTolThresDataSelection( double ttds ) {
    p.setProperty("TolThresDataSelection", ""+ttds); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return TolThresDataSelection.
   */
  public double GetTolThresDataSelection() { return (new Double(p.getProperty("TolThresDataSelection", ""+LocaleKBCT.DefaultTolThresDataSelection()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set ClusteringSelection flag.
   */
  public void SetClusteringSelection( boolean cs ) {
    p.setProperty("ClusteringSelection", ""+cs); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return ClusteringSelection flag.
   */
  public boolean GetClusteringSelection() { return (new Boolean(p.getProperty("ClusteringSelection", ""+LocaleKBCT.DefaultClusteringSelection()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set ClustersNumber.
   */
  public void SetClustersNumber( int cn ) {
    p.setProperty("ClustersNumber", ""+cn); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return ClustersNumber.
   */
  public int GetClustersNumber() { return (new Integer(p.getProperty("ClustersNumber", ""+LocaleKBCT.DefaultClustersNumber()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set RuleInduction flag.
   */
  public void SetRuleInduction( boolean ri ) {
    p.setProperty("RuleInduction", ""+ri); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return RuleInduction flag.
   */
  public boolean GetRuleInduction() { return (new Boolean(p.getProperty("RuleInduction", ""+LocaleKBCT.DefaultRuleInduction()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set InductionRulesAlgorithm.
   */
  public void SetInductionRulesAlgorithm( String ira ) {
    p.setProperty("InductionRulesAlgorithm", ira); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return InductionRulesAlgorithm.
   */
  public String GetInductionRulesAlgorithm() { return p.getProperty("InductionRulesAlgorithm", LocaleKBCT.DefaultInductionRulesAlgorithm()); }
//------------------------------------------------------------------------------
  /**
   * Set Strategy.
   */
  public void SetStrategy( String s ) {
    p.setProperty("Strategy", s); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Strategy.
   */
  public String GetStrategy() { return p.getProperty("Strategy", LocaleKBCT.DefaultStrategy()); }
//------------------------------------------------------------------------------
  /**
   * Set MinCard.
   */
  public void SetMinCard( int mc ) {
    p.setProperty("MinCard", ""+mc); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return MinCard.
   */
  public int GetMinCard() { return (new Integer(p.getProperty("MinCard", ""+LocaleKBCT.DefaultMinCard()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set MinDeg.
   */
  public void SetMinDeg( double md ) {
    p.setProperty("MinDeg", ""+md); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return MinDeg.
   */
  public double GetMinDeg() { return (new Double(p.getProperty("MinDeg", ""+LocaleKBCT.DefaultMinDeg()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set TreeFile.
   */
  public void SetTreeFile( String tf ) {
    p.setProperty("TreeFile", tf); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return TreeFile.
   */
  public String GetTreeFile() { return p.getProperty("TreeFile", LocaleKBCT.DefaultTreeFile()); }
//------------------------------------------------------------------------------
  /**
   * Set MaxTreeDepth.
   */
  public void SetMaxTreeDepth( int mtd ) {
    p.setProperty("MaxTreeDepth", ""+mtd); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return MaxTreeDepth.
   */
  public int GetMaxTreeDepth() { return (new Integer(p.getProperty("MaxTreeDepth", ""+LocaleKBCT.DefaultMaxTreeDepth()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set MinSignificantLevel.
   */
  public void SetMinSignificantLevel( double msl ) {
    p.setProperty("MinSignificantLevel", ""+msl); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return MinSignificantLevel.
   */
  public double GetMinSignificantLevel() { return (new Double(p.getProperty("MinSignificantLevel", ""+LocaleKBCT.DefaultMinSignificantLevel()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set LeafMinCard.
   */
  public void SetLeafMinCard( int lmc ) {
    p.setProperty("LeafMinCard", ""+lmc); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return LeafMinCard.
   */
  public int GetLeafMinCard() { return (new Integer(p.getProperty("LeafMinCard", ""+LocaleKBCT.DefaultLeafMinCard()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set ToleranceThreshold.
   */
  public void SetToleranceThreshold( double tt ) {
    p.setProperty("ToleranceThreshold", ""+tt); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return ToleranceThreshold.
   */
  public double GetToleranceThreshold() { return (new Double(p.getProperty("ToleranceThreshold", ""+LocaleKBCT.DefaultToleranceThreshold()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set MinEDGain.
   */
  public void SetMinEDGain( double meg ) {
    p.setProperty("MinEDGain", ""+meg); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return MinEDGain.
   */
  public double GetMinEDGain() { return (new Double(p.getProperty("MinEDGain", ""+LocaleKBCT.DefaultMinEDGain()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set CovThresh.
   */
  public void SetCovThresh( double msl ) {
    p.setProperty("CovThresh", ""+msl); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return DFCovThresh.
   */
  public double GetCovThresh() { return (new Double(p.getProperty("CovThresh", ""+LocaleKBCT.DefaultCovThresh()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set DFPerfLoss.
   */
  public void SetPerfLoss( double dpl ) {
    p.setProperty("PerfLoss", ""+dpl); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return PerfLoss.
   */
  public double GetPerfLoss() { return (new Double(p.getProperty("PerfLoss", ""+LocaleKBCT.DefaultPerfLoss()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Prune flag.
   */
  public void SetPrune( boolean pp ) {
    p.setProperty("Prune", ""+pp); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Prune flag.
   */
  public boolean GetPrune() { return (new Boolean(p.getProperty("Prune", ""+LocaleKBCT.DefaultPrune()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Split flag.
   */
  public void SetSplit( boolean s ) {
    p.setProperty("Split", ""+s); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Split flag.
   */
  public boolean GetSplit() { return (new Boolean(p.getProperty("Split", ""+LocaleKBCT.DefaultSplit()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Display flag.
   */
  public void SetDisplay( boolean d ) {
    p.setProperty("Display", ""+d); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Display flag.
   */
  public boolean GetDisplay() { return (new Boolean(p.getProperty("Display", ""+LocaleKBCT.DefaultDisplay()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Relgain flag.
   */
  public void SetRelgain( boolean rg ) {
    p.setProperty("Relgain", ""+rg); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Relgain flag.
   */
  public boolean GetRelgain() { return (new Boolean(p.getProperty("Relgain", ""+LocaleKBCT.DefaultRelgain()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Simplify flag.
   */
  public void SetRuleRanking( boolean rank ) {
    p.setProperty("RuleRanking", ""+rank); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return RuleRanking flag.
   */
  public boolean GetRuleRanking() { return (new Boolean(p.getProperty("RuleRanking", ""+LocaleKBCT.DefaultRuleRanking()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set orderRulesByOutputClass flag.
   */
  public void SetOrderRulesByOutputClass( boolean oboc ) {
    p.setProperty("OrderRulesByOutputClass", ""+oboc); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return orderRulesByOutputClass flag.
   */
  public boolean GetOrderRulesByOutputClass() { return (new Boolean(p.getProperty("OrderRulesByOutputClass", ""+LocaleKBCT.DefaultOrderRulesByOutputClass()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set orderRulesByLocalWeight flag.
   */
  public void SetOrderRulesByLocalWeight( boolean oblw ) {
    p.setProperty("OrderRulesByLocalWeight", ""+oblw); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return orderRulesByLocalWeight flag.
   */
  public boolean GetOrderRulesByLocalWeight() { return (new Boolean(p.getProperty("OrderRulesByLocalWeight", ""+LocaleKBCT.DefaultOrderRulesByLocalWeight()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set orderRulesByGlobalWeight flag.
   */
  public void SetOrderRulesByGlobalWeight( boolean obgw ) {
    p.setProperty("OrderRulesByGlobalWeight", ""+obgw); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return orderRulesByGlobalWeight flag.
   */
  public boolean GetOrderRulesByGlobalWeight() { return (new Boolean(p.getProperty("OrderRulesByGlobalWeight", ""+LocaleKBCT.DefaultOrderRulesByGlobalWeight()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set orderRulesByWeight flag.
   */
  public void SetOrderRulesByWeight( boolean obw ) {
    p.setProperty("OrderRulesByWeight", ""+obw); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return orderRulesByWeight flag.
   */
  public boolean GetOrderRulesByWeight() { return (new Boolean(p.getProperty("OrderRulesByWeight", ""+LocaleKBCT.DefaultOrderRulesByWeight()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set orderRulesByLocalIntWeight flag.
   */
  public void SetOrderRulesByLocalIntWeight( boolean obliw ) {
    p.setProperty("OrderRulesByLocalIntWeight", ""+obliw); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return orderRulesByLocalIntWeight flag.
   */
  public boolean GetOrderRulesByLocalIntWeight() { return (new Boolean(p.getProperty("OrderRulesByLocalIntWeight", ""+LocaleKBCT.DefaultOrderRulesByLocalIntWeight()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set orderRulesByGlobalIntWeight flag.
   */
  public void SetOrderRulesByGlobalIntWeight( boolean obgiw ) {
    p.setProperty("OrderRulesByGlobalIntWeight", ""+obgiw); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return orderRulesByGlobalIntWeight flag.
   */
  public boolean GetOrderRulesByGlobalIntWeight() { return (new Boolean(p.getProperty("OrderRulesByGlobalIntWeight", ""+LocaleKBCT.DefaultOrderRulesByGlobalIntWeight()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set orderRulesByIntWeight flag.
   */
  public void SetOrderRulesByIntWeight( boolean obiw ) {
    p.setProperty("OrderRulesByIntWeight", ""+obiw); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return orderRulesByIntWeight flag.
   */
  public boolean GetOrderRulesByIntWeight() { return (new Boolean(p.getProperty("OrderRulesByIntWeight", ""+LocaleKBCT.DefaultOrderRulesByIntWeight()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set orderRulesByNumberPremises flag.
   */
  public void SetOrderRulesByNumberPremises( boolean obnp ) {
    p.setProperty("OrderRulesByNumberPremises", ""+obnp); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return orderRulesByNumberPremises flag.
   */
  public boolean GetOrderRulesByNumberPremises() { return (new Boolean(p.getProperty("OrderRulesByNumberPremises", ""+LocaleKBCT.DefaultOrderRulesByNumberPremises()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set ReverseOrderRules flag.
   */
  public void SetReverseOrderRules( boolean ro ) {
    p.setProperty("ReverseOrderRules", ""+ro); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return ReverseOrderRules flag.
   */
  public boolean GetReverseOrderRules() { return (new Boolean(p.getProperty("ReverseOrderRules", ""+LocaleKBCT.DefaultReverseOrderRules()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set outputClassSelected flag.
   */
  public void SetOutputClassSelected( String soc ) {
    p.setProperty("OutputClassSelected", soc); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return OutputClassSelected flag.
   */
  public String GetOutputClassSelected() { return (new String(p.getProperty("OutputClassSelected", LocaleKBCT.DefaultOutputClassSelected()))); }
//------------------------------------------------------------------------------
  /**
   * Set LVreduction flag.
   */
  public void SetLVreduction( boolean red ) {
    p.setProperty("LVreduction", ""+red); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return LVreduction flag.
   */
  public boolean GetLVreduction() { return (new Boolean(p.getProperty("LVreduction", ""+LocaleKBCT.DefaultLVreduction()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Simplify flag.
   */
  public void SetSimplify( boolean simp ) {
    p.setProperty("Simplify", ""+simp); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Simplify flag.
   */
  public boolean GetSimplify() { return (new Boolean(p.getProperty("Simplify", ""+LocaleKBCT.DefaultSimplify()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set FirstReduceRuleBase flag.
   */
  public void SetFirstReduceRuleBase( boolean frrb ) {
    p.setProperty("FirstReduceRuleBase", ""+frrb); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return FirstReduceRuleBase flag.
   */
  public boolean GetFirstReduceRuleBase() { return (new Boolean(p.getProperty("FirstReduceRuleBase", ""+LocaleKBCT.DefaultFirstReduceRuleBase()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set SelectedOutput.
   */
  public void SetSelectedOutput( int so ) {
    p.setProperty("SelectedOutput", ""+so); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return SelectedOutput.
   */
  public int GetSelectedOutput() { return (new Integer(p.getProperty("SelectedOutput", ""+LocaleKBCT.DefaultSelectedOutput()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set MaximumLossOfCoverage.
   */
  public void SetMaximumLossOfCoverage( double mlc ) {
    p.setProperty("MaximumLossOfCoverage", ""+mlc); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return MaximumLossOfCoverage.
   */
  public double GetMaximumLossOfCoverage() { return (new Double(p.getProperty("MaximumLossOfCoverage", ""+LocaleKBCT.DefaultMaximumLossOfCoverage()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set MaximumLossOfPerformance.
   */
  public void SetMaximumLossOfPerformance( double mlp ) {
    p.setProperty("MaximumLossOfPerformance", ""+mlp); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return MaximumLossOfPerformance.
   */
  public double GetMaximumLossOfPerformance() { return (new Double(p.getProperty("MaximumLossOfPerformance", ""+LocaleKBCT.DefaultMaximumLossOfPerformance()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set MaximumNumberNewErrorCases.
   */
  public void SetMaximumNumberNewErrorCases( int mnec ) {
    p.setProperty("MaximumNumberNewErrorCases", ""+mnec); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return MaximumNumberNewErrorCases.
   */
  public int GetMaximumNumberNewErrorCases() { return (new Integer(p.getProperty("MaximumNumberNewErrorCases", ""+LocaleKBCT.DefaultMaximumNumberNewErrorCases()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set MaximumNumberNewAmbiguityCases.
   */
  public void SetMaximumNumberNewAmbiguityCases( int mnac ) {
    p.setProperty("MaximumNumberNewAmbiguityCases", ""+mnac); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return MaximumNumberNewAmbiguityCases.
   */
  public int GetMaximumNumberNewAmbiguityCases() { return (new Integer(p.getProperty("MaximumNumberNewAmbiguityCases", ""+LocaleKBCT.DefaultMaximumNumberNewAmbiguityCases()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set MaximumNumberNewUnclassifiedCases.
   */
  public void SetMaximumNumberNewUnclassifiedCases( int mnuc ) {
    p.setProperty("MaximumNumberNewUnclassifiedCases", ""+mnuc); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return MaximumNumberNewUnclassifiedCases.
   */
  public int GetMaximumNumberNewUnclassifiedCases() { return (new Integer(p.getProperty("MaximumNumberNewAmbiguityCases", ""+LocaleKBCT.DefaultMaximumNumberNewUnclassifiedCases()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set ruleRemoval flag.
   */
  public void SetRuleRemoval( boolean rr ) {
    p.setProperty("RuleRemoval", ""+rr); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return ruleRemoval flag.
   */
  public boolean GetRuleRemoval() { return (new Boolean(p.getProperty("RuleRemoval", ""+LocaleKBCT.DefaultRuleRemoval()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set variableRemoval flag.
   */
  public void SetVariableRemoval( boolean vr ) {
    p.setProperty("VariableRemoval", ""+vr); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return variableRemoval flag.
   */
  public boolean GetVariableRemoval() { return (new Boolean(p.getProperty("VariableRemoval", ""+LocaleKBCT.DefaultVariableRemoval()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set premiseRemoval flag.
   */
  public void SetPremiseRemoval( boolean pr ) {
    p.setProperty("PremiseRemoval", ""+pr); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return premiseRemoval flag.
   */
  public boolean GetPremiseRemoval() { return (new Boolean(p.getProperty("PremiseRemoval", ""+LocaleKBCT.DefaultPremiseRemoval()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set SimpRuleRanking flag.
   */
  public void SetSimpRuleRanking( String srank ) {
    p.setProperty("SimpRuleRanking", ""+srank); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return SimpRuleRanking flag.
   */
  public String GetSimpRuleRanking() { return p.getProperty("SimpRuleRanking", ""+LocaleKBCT.DefaultSimpRuleRanking()); }
//------------------------------------------------------------------------------
  /**
   * Set selectedPerformance flag.
   */
  public void SetSelectedPerformance( boolean sp ) {
    p.setProperty("SelectedPerformance", ""+sp); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return selectedPerformance flag.
   */
  public boolean GetSelectedPerformance() { return (new Boolean(p.getProperty("SelectedPerformance", ""+LocaleKBCT.DefaultSelectedPerformance()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set OnlyRBsimp flag.
   */
  public void SetOnlyRBsimplification( boolean obw ) {
    p.setProperty("OnlyRBsimplification", ""+obw); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return OnlyRBsimp flag.
   */
  public boolean GetOnlyRBsimplification() { return (new Boolean(p.getProperty("OnlyRBsimplification", ""+LocaleKBCT.DefaultOnlyRBsimplification()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set OnlyDBsimp flag.
   */
  public void SetOnlyDBsimplification( boolean obw ) {
    p.setProperty("OnlyDBsimplification", ""+obw); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return OnlyDBsimp flag.
   */
  public boolean GetOnlyDBsimplification() { return (new Boolean(p.getProperty("OnlyDBsimplification", ""+LocaleKBCT.DefaultOnlyDBsimplification()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set solveLingConflicts flag.
   */
  public void SetSolveLingConflicts( boolean slc ) {
    p.setProperty("SolveLingConflicts", ""+slc); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return solveLingConflicts flag.
   */
  public boolean GetSolveLingConflicts() { return (new Boolean(p.getProperty("SolveLingConflicts", ""+LocaleKBCT.DefaultSolveLingConflicts()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Optimization flag.
   */
  public void SetOptimization( boolean opt ) {
    p.setProperty("Optimization", ""+opt); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Optimization flag.
   */
  public boolean GetOptimization() { return (new Boolean(p.getProperty("Optimization", ""+LocaleKBCT.DefaultOptimization()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Optimization option: Partition Tuning (0) or Rule Selection (1).
   */
  public void SetOptOptimization( int opt ) {
    p.setProperty("OptOptimization", ""+opt); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Optimization option: Partition Tuning (0) or Rule Selection (1).
   */
  public int GetOptOptimization() { return (new Integer(p.getProperty("OptOptimization", ""+LocaleKBCT.DefaultOptOptimization()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Optimization Algorithm: Genetic Tuning (0) or Solis Wetts (1).
   */
  public void SetOptAlgorithm( int algorithm ) {
    p.setProperty("OptAlgorithm", ""+algorithm); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Optimization Algorithm: Genetic Tuning (0) or Solis Wetts (1).
   */
  public int GetOptAlgorithm() { return (new Integer(p.getProperty("OptAlgorithm", ""+LocaleKBCT.DefaultOptAlgorithm()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set BoundedOptimization flag.
   */
  public void SetBoundedOptimization( boolean flag ) {
    p.setProperty("BoundedOptimization", ""+flag); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return BoundedOptimization flag.
   */
  public boolean GetBoundedOptimization() { return (new Boolean(p.getProperty("BoundedOptimization", ""+LocaleKBCT.DefaultBoundedOptimization()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set SWoption (1: variable by variable / 2: label by label) in the optimization process.
   */
  public void SetSWoption( int opt ) {
    p.setProperty("SWoption", ""+opt); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return SWoption (1: variable by variable / 2: label by label) in the optimization process.
   */
  public int GetSWoption() { return (new Integer(p.getProperty("SWoption", ""+LocaleKBCT.DefaultSWoption()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Number of iterations (by label) in the optimization process.
   */
  public void SetNbIterations( int nbIter ) {
    p.setProperty("NbIterations", ""+nbIter); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Number of iterations (by label or by variable to optimize) in the optimization process.
   */
  public int GetNbIterations() { return (new Integer(p.getProperty("NbIterations", ""+LocaleKBCT.DefaultNbIterations()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Number of generations in the genetic tuning.
   */
  public void SetNbGenerations( int nbGener ) {
    p.setProperty("NbGenerations", ""+nbGener); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Number of generations in the genetic tuning.
   */
  public int GetNbGenerations() { return (new Integer(p.getProperty("NbGenerations", ""+LocaleKBCT.DefaultNbGenerations()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set initial generation.
   */
  public void SetInitialGeneration( int iniGen ) {
    p.setProperty("InitialGeneration", ""+iniGen); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return initial generation.
   */
  public int GetInitialGeneration() { return (new Integer(p.getProperty("InitialGeneration", ""+LocaleKBCT.DefaultInitialGeneration()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Milestone generation.
   */
  public void SetMilestoneGeneration( int mGen ) {
    p.setProperty("MilestoneGeneration", ""+mGen); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Milestone generation.
   */
  public int GetMilestoneGeneration() { return (new Integer(p.getProperty("MilestoneGeneration", ""+LocaleKBCT.DefaultMilestoneGeneration()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Population length.
   */
  public void SetPopulationLength( int l ) {
    p.setProperty("PopulationLength", ""+l); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Population length.
   */
  public int GetPopulationLength() { return (new Integer(p.getProperty("PopulationLength", ""+LocaleKBCT.DefaultPopulationLength()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Crossover Operator: Max-Min (0) or Dubois (1).
   */
  public void SetCrossoverOperator( int op ) {
    p.setProperty("CrossoverOperator", ""+op); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Crossover Operator: Max-Min (0) or Dubois (1).
   */
  public int GetCrossoverOperator() { return (new Integer(p.getProperty("CrossoverOperator", ""+LocaleKBCT.DefaultCrossoverOperator()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Crossover Probability.
   */
  public void SetCrossoverProb( double Prob ) {
    p.setProperty("CrossoverProb", ""+Prob); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Crossover Probability.
   */
  public double GetCrossoverProb() { return (new Double(p.getProperty("CrossoverProb", ""+LocaleKBCT.DefaultCrossoverProb()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Mutation Probability.
   */
  public void SetMutationProb( double Prob ) {
    p.setProperty("MutationProb", ""+Prob); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Mutation Probability.
   */
  public double GetMutationProb() { return (new Double(p.getProperty("MutationProb", ""+LocaleKBCT.DefaultMutationProb()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set v.
   */
  public void SetTournamentSize( int ts ) {
    p.setProperty("TournamentSize", ""+ts); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return TournamentSize.
   */
  public int GetTournamentSize() { return (new Integer(p.getProperty("TournamentSize", ""+LocaleKBCT.DefaultTournamentSize()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Seed.
   */
  public void SetSeed( int seed ) {
    p.setProperty("Seed", ""+seed); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Seed.
   */
  public int GetSeed() { return (new Integer(p.getProperty("Seed", ""+LocaleKBCT.DefaultSeed()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Parameter Alfa.
   */
  public void SetParAlfa( double a ) {
    p.setProperty("ParAlfa", ""+a); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Parameter Alfa.
   */
  public double GetParAlfa() { return (new Double(p.getProperty("ParAlfa", ""+LocaleKBCT.DefaultParAlfa()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Number of generations in the genetic tuning.
   */
  public void SetRuleSelectionNbGenerations( int nbGener ) {
    p.setProperty("RuleSelectionNbGenerations", ""+nbGener); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Number of generations in the genetic tuning.
   */
  public int GetRuleSelectionNbGenerations() { return (new Integer(p.getProperty("RuleSelectionNbGenerations", ""+LocaleKBCT.DefaultRuleSelectionNbGenerations()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set initial generation.
   */
  public void SetRuleSelectionInitialGeneration( int iniGen ) {
    p.setProperty("RuleSelectionInitialGeneration", ""+iniGen); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return initial generation.
   */
  public int GetRuleSelectionInitialGeneration() { return (new Integer(p.getProperty("RuleSelectionInitialGeneration", ""+LocaleKBCT.DefaultRuleSelectionInitialGeneration()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Milestone generation.
   */
  public void SetRuleSelectionMilestoneGeneration( int mGen ) {
    p.setProperty("RuleSelectionMilestoneGeneration", ""+mGen); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Milestone generation.
   */
  public int GetRuleSelectionMilestoneGeneration() { return (new Integer(p.getProperty("RuleSelectionMilestoneGeneration", ""+LocaleKBCT.DefaultRuleSelectionMilestoneGeneration()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Population length.
   */
  public void SetRuleSelectionPopulationLength( int l ) {
    p.setProperty("RuleSelectionPopulationLength", ""+l); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Population length.
   */
  public int GetRuleSelectionPopulationLength() { return (new Integer(p.getProperty("RuleSelectionPopulationLength", ""+LocaleKBCT.DefaultRuleSelectionPopulationLength()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Crossover Operator: Max-Min (0) or Dubois (1).
   */
  public void SetRuleSelectionCrossoverOperator( int op ) {
    p.setProperty("RuleSelectionCrossoverOperator", ""+op); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Crossover Operator: Max-Min (0) or Dubois (1).
   */
  public int GetRuleSelectionCrossoverOperator() { return (new Integer(p.getProperty("RuleSelectionCrossoverOperator", ""+LocaleKBCT.DefaultRuleSelectionCrossoverOperator()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Crossover Probability.
   */
  public void SetRuleSelectionCrossoverProb( double Prob ) {
    p.setProperty("RuleSelectionCrossoverProb", ""+Prob); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Crossover Probability.
   */
  public double GetRuleSelectionCrossoverProb() { return (new Double(p.getProperty("RuleSelectionCrossoverProb", ""+LocaleKBCT.DefaultRuleSelectionCrossoverProb()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Mutation Probability.
   */
  public void SetRuleSelectionMutationProb( double Prob ) {
    p.setProperty("RuleSelectionMutationProb", ""+Prob); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Mutation Probability.
   */
  public double GetRuleSelectionMutationProb() { return (new Double(p.getProperty("RuleSelectionMutationProb", ""+LocaleKBCT.DefaultRuleSelectionMutationProb()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set W1 (fitness).
   */
  public void SetRuleSelectionW1( double w1 ) {
    p.setProperty("RuleSelectionW1", ""+w1); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return W1 (fitness).
   */
  public double GetRuleSelectionW1() { return (new Double(p.getProperty("RuleSelectionW1", ""+LocaleKBCT.DefaultRuleSelectionW1()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set W2 (fitness).
   */
  public void SetRuleSelectionW2( double w2 ) {
    p.setProperty("RuleSelectionW2", ""+w2); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return W2 (fitness).
   */
  public double GetRuleSelectionW2() { return (new Double(p.getProperty("RuleSelectionW2", ""+LocaleKBCT.DefaultRuleSelectionW2()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set interpretability index: HILK (0), Nauck (1), Number of rules (2), Number of premises (3), average rule length (4).
   */
  public void SetRuleSelectionInterpretabilityIndex( int op ) {
    p.setProperty("RuleSelectionInterpretabilityIndex", ""+op); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return interpretability index: HILK (0), Nauck (1), Number of rules (2), Number of premises (3), average rule length (4).
   */
  public int GetRuleSelectionInterpretabilityIndex() { return (new Integer(p.getProperty("RuleSelectionInterpretabilityIndex", ""+LocaleKBCT.DefaultRuleSelectionInterpretabilityIndex()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set v.
   */
  public void SetRuleSelectionTournamentSize( int ts ) {
    p.setProperty("RuleSelectionTournamentSize", ""+ts); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return TournamentSize.
   */
  public int GetRuleSelectionTournamentSize() { return (new Integer(p.getProperty("RuleSelectionTournamentSize", ""+LocaleKBCT.DefaultRuleSelectionTournamentSize()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set AmbThres.
   */
  public void SetAmbThres( double bh ) {
    p.setProperty("AmbThres", ""+bh); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return AmbThres.
   */
  public double GetAmbThres() { return (new Double(p.getProperty("AmbThres", ""+LocaleKBCT.DefaultAmbThres()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set BlankThres.
   */
  public void SetBlankThres( double bh ) {
    p.setProperty("BlankThres", ""+bh); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return BlankThres.
   */
  public double GetBlankThres() { return (new Double(p.getProperty("BlankThres", ""+LocaleKBCT.DefaultBlankThres()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Completeness flag.
   */
  public void SetCompleteness( boolean comp ) {
    p.setProperty("Completeness", ""+comp); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Completeness flag.
   */
  public boolean GetCompleteness() { return (new Boolean(p.getProperty("Completeness", ""+LocaleKBCT.DefaultCompleteness()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set CompletenessThres.
   */
  public void SetCompletenessThres( double cth ) {
    p.setProperty("CompletenessThres", ""+cth); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return CompletenessThres.
   */
  public double GetCompletenessThres() { return (new Double(p.getProperty("CompletenessThres", ""+LocaleKBCT.DefaultCompletenessThres()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set TESTautomatic flag.
   */
  public void SetTESTautomatic( boolean ta ) {
    p.setProperty("TESTautomatic", ""+ta); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return TESTautomatic flag.
   */
  public boolean GetTESTautomatic() { return (new Boolean(p.getProperty("TESTautomatic", ""+LocaleKBCT.DefaultTESTautomatic()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set INFERautomatic flag.
   */
  public void SetINFERautomatic( boolean ia ) {
    p.setProperty("INFERautomatic", ""+ia); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return INFERautomatic flag.
   */
  public boolean GetINFERautomatic() { return (new Boolean(p.getProperty("INFERautomatic", ""+LocaleKBCT.DefaultINFERautomatic()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set INFERautomatic flag.
   */
  public void SetFINGRAMSautomatic( boolean ia ) {
    p.setProperty("FINGRAMSautomatic", ""+ia); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return FINGRAMSautomatic flag.
   */
  public boolean GetFINGRAMSautomatic() { return (new Boolean(p.getProperty("FINGRAMSautomatic", ""+LocaleKBCT.DefaultFINGRAMSautomatic()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Problem.
   */
  public void SetProblem( String prob ) {
    p.setProperty("Problem", prob); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Problem.
   */
  public String GetProblem() { return p.getProperty("Problem", LocaleKBCT.DefaultProblem()); }
//------------------------------------------------------------------------------
  /**
   * Set Number of Inputs.
   */
  public void SetNumberOfInputs( int noi ) {
    p.setProperty("NumberOfInputs", ""+noi); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Number of Inputs.
   */
  public int GetNumberOfInputs() { return (new Integer(p.getProperty("NumberOfInputs", ""+LocaleKBCT.DefaultNumberOfInputs()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Classification flag.
   */
  public void SetClassificationFlag( boolean classFlag ) {
    p.setProperty("ClassificationFlag", ""+classFlag); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Classification Flag.
   */
  public boolean GetClassificationFlag() { return (new Boolean(p.getProperty("ClassificationFlag", ""+LocaleKBCT.DefaultClassificationFlag()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Number of output Labels.
   */
  public void SetNumberOfOutputLabels( int nool ) {
    p.setProperty("NumberOfOutputLabels", ""+nool); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Number of output Labels.
   */
  public int GetNumberOfOutputLabels() { return (new Integer(p.getProperty("NumberOfOutputLabels", ""+LocaleKBCT.DefaultNumberOfOutputLabels()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Parameter L.
   */
  public void SetParameterL( double pl ) {
    p.setProperty("ParameterL", ""+pl); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Parameter L.
   */
  public double GetParameterL() { return (new Double(p.getProperty("ParameterL", ""+LocaleKBCT.DefaultParameterL()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Parameter M.
   */
  public void SetParameterM( double pm ) {
    p.setProperty("ParameterM", ""+pm); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Parameter M.
   */
  public double GetParameterM() { return (new Double(p.getProperty("ParameterM", ""+LocaleKBCT.DefaultParameterM()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Max NbRules.
   */
  public void SetMaxNbRules( int mnr ) {
    p.setProperty("MaxNbRules", ""+mnr); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Max NbRules.
   */
  public int GetMaxNbRules() { return (new Integer(p.getProperty("MaxNbRules", ""+LocaleKBCT.DefaultMaxNbRules()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Max NbPremises.
   */
  public void SetMaxNbPremises( int mnp ) {
    p.setProperty("MaxNbPremises", ""+mnp); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Max NbPremises.
   */
  public int GetMaxNbPremises() { return (new Integer(p.getProperty("MaxNbPremises", ""+LocaleKBCT.DefaultMaxNbPremises()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Fingram flag.
   */
  public void SetFingram( boolean fing ) {
    p.setProperty("Fingram", ""+fing); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Fingram flag.
   */
  public boolean GetFingram() { return (new Boolean(p.getProperty("Fingram", ""+LocaleKBCT.DefaultFingram()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set FingramWS flag.
   */
  public void SetFingramWS( boolean fingws ) {
    p.setProperty("FingramWS", ""+fingws); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return FingramWS flag.
   */
  public boolean GetFingramWS() { return (new Boolean(p.getProperty("FingramWS", ""+LocaleKBCT.DefaultFingramWS()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set FingramWOS flag.
   */
  public void SetFingramWOS( boolean fingwos ) {
    p.setProperty("FingramWOS", ""+fingwos); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return FingramWOS flag.
   */
  public boolean GetFingramWOS() { return (new Boolean(p.getProperty("FingramWOS", ""+LocaleKBCT.DefaultFingramWOS()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Set LimMax parameter (interpretability index).
   */
  public void SetLimMaxIntIndex( int limmax ) {
    p.setProperty("LimMaxIntIndex", ""+limmax); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return LimMax parameter (interpretability index).
   */
  public int GetLimMaxIntIndex() { return (new Integer(p.getProperty("LimMaxIntIndex", ""+LocaleKBCT.DefaultLimMaxIntIndex()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set sample for instance-based Fingrams.
   */
  public void SetFingramsSelectedSample( int s ) {
    p.setProperty("FingramsSelectedSample", ""+s); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return sample for instance-based Fingrams.
   */
  public int GetFingramsSelectedSample() { return (new Integer(p.getProperty("FingramsSelectedSample", ""+LocaleKBCT.DefaultFingramsSelectedSample()))).intValue(); }
//------------------------------------------------------------------------------
  /**
   * Set Fingrams metric.
   */
  public void SetFingramsMetric( String fm ) {
    p.setProperty("FingramsMetric", ""+fm); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Fingrams metric.
   */
  public String GetFingramsMetric() { return p.getProperty("FingramsMetric", LocaleKBCT.DefaultFingramsMetric()); }
//------------------------------------------------------------------------------
  /**
   * Set Fingrams layout method.
   */
  public void SetFingramsLayout( String fl ) {
    p.setProperty("FingramsLayout", ""+fl); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Fingrams layout method.
   */
  public String GetFingramsLayout() { return p.getProperty("FingramsLayout", LocaleKBCT.DefaultFingramsLayout()); }
//------------------------------------------------------------------------------
  /**
   * Set GoodnessHigh Threshold.
   */
  public void SetGoodnessHighThreshold( double ghth ) {
    p.setProperty("GoodnessHighThreshold", ""+ghth); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return GoodnessHigh Threshold.
   */
  public double GetGoodnessHighThreshold() { return (new Double(p.getProperty("GoodnessHighThreshold", ""+LocaleKBCT.DefaultGoodnessHighThreshold()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set GoodnessLow Threshold.
   */
  public void SetGoodnessLowThreshold( double glth ) {
    p.setProperty("GoodnessLowThreshold", ""+glth); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return GoodnessLow Threshold.
   */
  public double GetGoodnessLowThreshold() { return (new Double(p.getProperty("GoodnessLowThreshold", ""+LocaleKBCT.DefaultGoodnessLowThreshold()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set PathFinder Threshold.
   */
  public void SetPathFinderThreshold( double pfth ) {
    p.setProperty("PathFinderThreshold", ""+pfth); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return PathFinder Threshold.
   */
  public double GetPathFinderThreshold() { return (new Double(p.getProperty("PathFinderThreshold", ""+LocaleKBCT.DefaultPathFinderThreshold()))).doubleValue(); }
//------------------------------------------------------------------------------
  /**
   * Set PathFinder Q parameter.
   */
  public void SetPathFinderParQ( int q ) {
    p.setProperty("PathFinderParQ", ""+q); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return PathFinder Q parameter.
   */
  public int GetPathFinderParQ() { return (new Integer(p.getProperty("PathFinderParQ", ""+LocaleKBCT.DefaultPathFinderParQ()))).intValue(); }
//-------------------------------------------------------------------------------
  /**
   * Set Tutorial flag.
   */
  public void SetTutorialFlag( boolean tut ) {
    p.setProperty("Tutorial", ""+tut); Save();
  }
//------------------------------------------------------------------------------
  /**
   * Return Tutorial flag.
   */
  public boolean GetTutorialFlag() { return (new Boolean(p.getProperty("Tutorial", ""+LocaleKBCT.DefaultTutorialFlag()))).booleanValue(); }
//------------------------------------------------------------------------------
  /**
   * Close open files to release memory.
   */
  public void closeConfigFile() {
	  try {
	  this.f.close();
	  this.f= null;
	  this.p.clear();
	  this.p= null;
	  this.finalize();
	  } catch (Throwable t) {
		  System.out.println("Error when closing ConfigFile");
		  t.printStackTrace();
	  }
  }
}