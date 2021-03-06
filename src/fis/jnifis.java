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
//                              jnifis.java
//
//
// Author(s) : Jean-Luc LABLEE
// FISPRO Version : 2.1
// Contact : fispro@ensam.inra.fr
// Last modification date:  September 15, 2004
// File :

//**********************************************************************
package fis;

import java.util.*;

/**
 * fis.jnifis.
 *
 *@author     Jose Maria Alonso Moral
 *@version    3.0 , 04/06/13
 */
//------------------------------------------------------------------------------
public class jnifis {
  // nom de la librairie
  private static String library_name = "jnifis";
  static { System.loadLibrary( library_name ); }  // ouverture de la librairie native
 //static { System.loadLibrary( "C:/Program Files/FisPro/FISPRO-3.5/bin/jnifis.dll "); }  // ouverture de la librairie native

//------------------------------------------------------------------------------
  public jnifis() throws Throwable {}
//------------------------------------------------------------------------------
// Throwable permet d'intercepter les exceptions:
// java.lang.UnsatisfiedLinkError si la fonction native n'est pas trouv�e dans la librairie
// java.lang.NoClassDefFoundError si la classe exception n'est pas trouv� dans la m�thode native
// et toutes les exceptions g�n�r�es par la m�thode native

  public static native void setLocale() throws Throwable;
  public static native void setUserHomeFisproPath( String path ) throws Throwable;  /**< Renseigne la variable UserHomeFisproPath de la librairie. @param chemin home user. */
  // fonctions sif
  public static native long NewFIS() throws Throwable;
  public static native long NewFIS( String file_name, boolean cover ) throws Throwable;
  public static native long NewFIS( long fis_ptr ) throws Throwable;
  public static native long NewFISFPA( long fis_ptr, String data, int strategy, int min_card, double min_deg ) throws Throwable;
  public static native long NewGENFIS( long fis_ptr, String data, boolean sort, double thres ) throws Throwable;
  //public static native long NewFISSIMPLE( long fis_ptr, String data, String validation_data, double homogeneity_threshold, double loss_performance, int output_number, int min_cardinality, double min_matching_degree, double loss_coverage, boolean keep_last, double max_err) throws Throwable;
  public static native long NewFISSIMPLE( long fis_ptr, String data, String resultFile, double homogeneity_threshold, double loss_performance, int output_number, double min_matching_degree, double loss_coverage, double coverage_min, boolean keep_last, double max_err, boolean ruleRemoval, boolean variableRemoval, String validationFile) throws Throwable;
  // Funci�n modificada en cpp/fisopt
  //public static native long NewFISOPT( long fis_ptr, String data, String key, int iterations, double std_gauss, int constraints, int fails, double center_thres, int output_number, boolean without_redondancy, int seed_value, double sw1, double sw2, double sw3, int cardinal_vocabulary, double mu_thres ) throws Throwable;
  // new
  public static native long NewFISOLS( long fis_ptr, String data, int outputNumber, boolean generateRules, double standardDeviation, String conjunction, double totalUnexplainedVariance, double maximumNumberOfRules, boolean reduceVocabulary, double degradationAllowed, double numberOfConc, boolean useRuleConc, boolean outputFuzzification, boolean removeTemporaryFiles, String testDataFile) throws Throwable;
  public static native void outputVocabularyReduction( long fis_ptr, String data, int outputNumber, double blankThreshold, double degradationAllowed, double numberOfConc, boolean useRuleConc) throws Throwable;
  public static native long NewCustomFISOPT( long fis_ptr, String data, String key, int iterations, double std_gauss, int constraints, int fails, double center_thres, int output_number, boolean without_redondancy, int seed_value, double sw1, double sw2, double sw3, int cardinal_vocabulary, double mu_thres, double loss_coverage ) throws Throwable;
  //public static native long NewFISOPT( long fis_ptr, String data, String optimization_order, boolean output, boolean rules, boolean cardinal_vocabulary, int number_of_loops, double loss_coverage, int iterations, double std_gauss, int constraints, int fails, double center_thres, int output_number, boolean without_redondancy, int seed_value, double sw1, double sw2, double sw3, double mu_thres ) throws Throwable;
  public static native long NewFISOPT( long fis_ptr, String fisPath, String data, String optimization_order, boolean output, boolean rules, int number_of_loops, double loss_coverage, int iterations, double std_gauss, int constraints, int fails, int output_number, int seed_value, double sw1, double sw2, double sw3, double mu_thres, boolean crossValidation, double learningDataRatio, boolean classif, double precision, int columnNumber, int sampleNumber, double minBetweenMF) throws Throwable;

  public static native long [] NewTreeFIS( long fis_ptr, String data, String tree_file, String valid_file, String resultFistreeFile, String perfFistreeFile, int maxtreedepth, double min_ed_gain, double min_significant, double ambiguity_thres, int outputNumber,boolean prune, boolean split,double relperfloss, boolean relgain, boolean display, double covThresh, int min_leaf_card ) throws Throwable;

  public static native void DeleteFIS( long fis_ptr ) throws Throwable;
  public static native boolean EqualFIS( long fis1_ptr, long fis2_ptr ) throws Throwable;
  public static native void SaveFIS( long fis_ptr, String file_name ) throws Throwable;
  public static native void SetFISName( long fis_ptr, String name ) throws Throwable;
  public static native String GetFISName( long fis_ptr ) throws Throwable;
  public static native void SetConjunction( long fis_ptr, String conjunction ) throws Throwable;
  public static native String GetConjunction( long fis_ptr ) throws Throwable;
  public static native int NbInputs( long fis_ptr ) throws Throwable;
  public static native int NbOutputs( long fis_ptr ) throws Throwable;
  public static native long GetInput( long fis_ptr, int input_number ) throws Throwable;
  public static native long GetOutput( long fis_ptr, int output_number ) throws Throwable;
  public static native void AddInput( long fis_ptr, long input_ptr ) throws Throwable;
  public static native void RemoveInput( long fis_ptr, int input_number ) throws Throwable;
  public static native void ReplaceInput( long fis_ptr, int input_number, long input_ptr ) throws Throwable;
  public static native void AddOutput( long fis_ptr, long output_ptr ) throws Throwable;
  public static native void RemoveOutput( long fis_ptr, int output_number ) throws Throwable;
  public static native void ReplaceOutput( long fis_ptr, int output_number, long output_ptr ) throws Throwable;
  public static native String [] TypeConjunction() throws Throwable;
  public static native int NbRules( long fis_ptr ) throws Throwable;
  public static native long GetRule( long fis_ptr, int rule_number ) throws Throwable;
  public static native void AddRule( long fis_ptr, long rule_ptr ) throws Throwable;
  public static native void RemoveRule( long fis_ptr, int rule_number ) throws Throwable;
  public static native long NewRule( long fis_ptr, boolean active, int [] facteurs, double [] actions ) throws Throwable;
  public static native void Infer( long fis_ptr, double [] inputs_value ) throws Throwable;
  public static native void InferFuzzyInput( long fis_ptr, double [] kw, double [] sw , int nbAlphaCut, double [] inputs_values ) throws Throwable;
  public static native int GetBuildFuzIn( long input_ptr, int input_number, double [] kw, double [] sw, double [] inputs_values, Vector<Double> fuzval ) throws Throwable;
  public static native Object[] Infer3DSurface (long ptr, SurfacePlotParameters Parameters) throws Throwable; //MG
  public static native double[][] Infer2DSurface (long ptr, SurfacePlotParameters Parameters) throws Throwable; //MG
  public static native double[] GetRulesWeight (long ptr, double [] input_values) throws Throwable; //MG
  public static native double [] DataFileInfer( long fis_ptr, int output_number, String data_file, String result_file, double blank_thres, boolean display ) throws Throwable;
  public static native double [] SortiesObtenues( long fis_ptr ) throws Throwable;
  public static native void RemoveMFInInput( long fis_ptr, int input_number, int mf_number ) throws Throwable;
  public static native void RemoveMFInOutput( long fis_ptr, int output_number, int mf_number ) throws Throwable;
  public static native void SetOutputDefuz( long fis_ptr, int output_number, String defuz ) throws Throwable;
  public static native void SetOutputImplicatifFuzzy( long fis_ptr, int output_number, boolean impli ) throws Throwable;
  public static native void SetOutputDisjunction( long fis_ptr, int output_number, String disjunction ) throws Throwable;
  public static native void SetOutputClassif( long fis_ptr, int output_number, boolean classif ) throws Throwable;
  public static native double [] GetOutputClassesLabels( long fis_ptr, int output_number ) throws Throwable;
  public static native void Links( long fis_ptr, String data_file, double threshold, boolean sort, String file_prefix ) throws Throwable;
  public static native void Fuz2Crisp( long fis_ptr, int output_number ) throws Throwable;
  public static native void Crisp2Fuz( long fis_ptr, String defuz, int output_number ) throws Throwable;
  public static native void Imp2Conj( long fis_ptr, int output_number, boolean transfPart ) throws Throwable;
  public static native int Conj2Imp( long fis_ptr, int output_number, boolean transfPart ) throws Throwable;
  public static native void InitClasses( long fis_ptr, int output_number, String data_file ) throws Throwable;
  public static native void InitPossibles( long fis_ptr ) throws Throwable;
  public static native double [][] AgregationResult( long fis_ptr, int output_number ) throws Throwable;
  public static native int AgregationImpliResult( long fis_ptr, int output_number, Vector<Double> result) throws Throwable;
  public static native int GetImpliConc( long fis_ptr, int output_number, int rule_number, Vector<Double> result) throws Throwable;
  public static native double [] InferErrorRegression( long fis_ptr ) throws Throwable; 
  // fonctions entr�e
  public static native long NewInput() throws Throwable;
  public static native long NewInput( long input_ptr ) throws Throwable;
  public static native long NewInput( double [] t, double min, double max, boolean sort ) throws Throwable;
  public static native long NewRegularInput( int nb_sef, double min, double max) throws Throwable;
  public static native long NewIrregularInput( double []sommets, int nb_sef, double min, double max) throws Throwable;
  public static native void DeleteInput( long input_ptr ) throws Throwable;
  public static native String GetInputName( long input_ptr ) throws Throwable;
  public static native double[] GetInputRange( long input_ptr ) throws Throwable;
  public static native boolean GetInputActive( long input_ptr ) throws Throwable;
  public static native void SetInputName( long input_ptr, String name ) throws Throwable;
  public static native void SetInputRange( long input_ptr, double domain_inf, double domain_sup ) throws Throwable;
  public static native void SetInputActive( long input_ptr, boolean active ) throws Throwable;
  public static native int GetNbMF( long input_ptr ) throws Throwable;
  public static native long GetMF( long input_ptr, int mf_number ) throws Throwable;
  public static native long GetMFGlob( long input_ptr ) throws Throwable;
  public static native long GetMFConc( long input_ptr, int rule_number ) throws Throwable;
  public static native void AddMF( long input_ptr, long mf_ptr ) throws Throwable;
  public static native void ReplaceMF( long input_ptr, int mf_number, long mf_ptr ) throws Throwable;
  public static native void ReplaceMFOutput( long input_ptr, int mf_number, long mf_ptr ) throws Throwable;
  public static native double [] InputAppartenance( long input_ptr ) throws Throwable;
  public static native double[] GetBreakPoints (long input_ptr) throws Throwable; //MG
  public static native boolean IsSfp( long input_ptr ) throws Throwable;
  public static native void SetTemplate( long input_ptr, double kw, double sw );
  public static native double GetKernelWeightTemplate( long input_ptr);
  public static native double GetSupportWeightTemplate( long input_ptr);
  public static native double[] PcPe(long input_ptr, double[] datas, int dataSize);
  public static native void CheckFuzDist(long input_ptr);
  public static native double InputDistance(long input_ptr, double x, double y, boolean normalize);
  // fonctions sortie
  public static native long NewOutput( long output_ptr ) throws Throwable;
  public static native long NewRegularOutput( int nb_sef, double min, double max, String defuz, boolean classif, double default_value, String disj) throws Throwable;
  public static native long NewIrregularOutput( double []sommets, int nb_sef, double min, double max, String defuz, boolean classif, double default_value, String disj) throws Throwable;
  public static native long NewOutputFloue() throws Throwable;
  public static native long NewOutputFloue( long output_ptr ) throws Throwable;
  public static native long NewOutputNette() throws Throwable;
  public static native long NewOutputNette( long output_ptr ) throws Throwable;
  public static native String GetOutputNature( long output_ptr ) throws Throwable;
  public static native String GetOutputDefuz( long output_ptr ) throws Throwable;
  public static native String GetOutputDisjunction( long output_ptr ) throws Throwable;
  public static native void SetOutputDefaultValue( long output_ptr, double default_value ) throws Throwable;
  public static native double GetOutputDefaultValue( long output_ptr ) throws Throwable;
  public static native boolean GetOutputClassif( long output_ptr ) throws Throwable;
  public static native String GetTypeImplicatif( long output_ptr ) throws Throwable;  
  public static native String [] TypeDefuzzificationFloue() throws Throwable;
  public static native String [] TypeDefuzzificationNette() throws Throwable;
  public static native String [] TypeDefuzzificationImplicatif() throws Throwable;
  public static native String [] TypeDisjunctionImplicatif() throws Throwable;
  public static native String [] TypeDisjunctionFloue() throws Throwable;
  public static native String [] TypeDisjunctionNette() throws Throwable;
  public static native String [] TypeNature() throws Throwable;
  public static native int GetAlarm( long output_ptr ) throws Throwable;
  public static native void SetDefuzThres( long output_ptr, double thres ) throws Throwable;
  public static native double GetDefuzThres( long output_ptr ) throws Throwable;
  public static native double[] GetClasses( long output_ptr ) throws Throwable;  /**< Renvoie les labels des classes pour une sortie classif @param output_ptr pointeur de la sortie @return tableau des labels des classes ou null si la sortie n'est pas classif */
  public static native boolean IsQsp( long output_ptr ) throws Throwable;
  // fonctions SEFS
  public static native String [] MFType() throws Throwable;
  public static native String GetMFName( long ptr ) throws Throwable;
  public static native void SetMFName( long ptr, String name ) throws Throwable;
  public static native String GetMFType( long ptr ) throws Throwable;
  public static native double[] GetMFParam( long ptr ) throws Throwable;
  public static native void DeleteMF( long ptr ) throws Throwable;
  public static native long NewMFTriangular( String name, double a, double b, double c ) throws Throwable;
  public static native long NewMFTrapezoidal( String name, double a, double b, double c, double d ) throws Throwable;
  public static native long NewMFSemiTrapezoidalInf( String name, double a, double b, double c ) throws Throwable;
  public static native long NewMFSemiTrapezoidalSup( String name, double a, double b, double c ) throws Throwable;
  public static native long NewMFDiscrete( String name, double [] values ) throws Throwable;
  public static native long NewMFUniversal( String name, double a, double b ) throws Throwable;
  public static native long NewMFGaussian( String name, double std, double mean ) throws Throwable;
  public static native long NewMFDoor( String name, double a, double b ) throws Throwable;
  public static native long NewMFSinus( String name, double a, double b ) throws Throwable;
  public static native long NewMFSinusInf( String name, double a, double b ) throws Throwable;
  public static native long NewMFSinusSup( String name, double a, double b ) throws Throwable;
  public static native double [] MFSupport( long ptr ) throws Throwable;
  public static native double [] MFAlphaKernel( long ptr, double alpha ) throws Throwable;
  // fonctions regle
  public static native int [] GetRuleProps( long ptr ) throws Throwable;
  public static native double [] GetRuleConcs( long ptr ) throws Throwable;
  public static native void SetRuleProp( long ptr, int input_number, int prop ) throws Throwable;
  public static native void SetRuleConc( long ptr, int output_number, double conc ) throws Throwable;
  public static native boolean GetRuleActive( long ptr ) throws Throwable;
  public static native void SetRuleActive( long ptr, boolean active ) throws Throwable;
  public static native double RulePoids( long ptr ) throws Throwable;
  public static native void DeleteRule( long ptr ) throws Throwable;
  public static native boolean RulePremisseEqual( long rule1_ptr, long rule2_ptr ) throws Throwable;
  public static native void SetExpertWeight( long ptr, double weight ) throws Throwable;
  public static native double GetExpertWeight( long ptr ) throws Throwable;
  // fichier example
  public static native double [][] DataFile( String file_name ) throws Throwable;
  public static native String [] TypeMissingValues() throws Throwable;
  public static native void SetMissingValues( long ptr_sif, String missing_values ) throws Throwable;
  public static native String GetMissingValues( long ptr_sif ) throws Throwable;
  public static native void SetDataFilePath( String new_path ) throws Throwable;
  public static native String[] DataName() throws Throwable;
  // HFP
  public static native long HFPOpen(String data_file, String hfp_file) throws Throwable;
  public static native String [] HFPHierarchyType() throws Throwable;
  public static native long HFPConfig(String data_file, int column_not_process, String hfp_file) throws Throwable;
  public static native String GetHFPHierarchy(long hfp_ptr) throws Throwable;
  public static native void SetHFPHierarchy(long hfp_ptr, String hierarchy) throws Throwable;
  public static native String GetHFPConjunction(long hfp_ptr) throws Throwable;
  public static native void SetHFPConjunction(long hfp_ptr, String hierarchy) throws Throwable;
  public static native double GetHFPToleranceThreshold(long hfp_ptr) throws Throwable;
  public static native void SetHFPToleranceThreshold(long hfp_ptr, double tolerance) throws Throwable;
  public static native String [] HFPDistanceType() throws Throwable;
  //public static native String [] HFPMergingType() throws Throwable;
  public static native Object [] GetHFPParameters(long hfp_ptr) throws Throwable;
  //public static native void SetHFPParameters(long hfp_ptr, String distance, boolean simplified, int dist_mf_nb, int var_mf_nb, String merging, double hetero_penalty, double hetero_prop) throws Throwable;
  public static native void SetHFPParameters(long hfp_ptr, String distance) throws Throwable;
  public static native int GetHFPNbInput(long hfp_ptr) throws Throwable;
  public static native int GetHFPNbOutput(long hfp_ptr) throws Throwable;
  public static native long GetHFPInput(long hfp_ptr, int input_number) throws Throwable;
  public static native long GetHFPOutput(long hfp_ptr, int output_number) throws Throwable;
  public static native void HFPDelete(long hfp_ptr) throws Throwable;
  public static native void HFPSave(long hfp_ptr, String data_file, String hfp_file) throws Throwable;
  public static native int GetHFPInputNumberOfMF(long hfp_input_ptr) throws Throwable;
  public static native void SetHFPInputNumberOfMF(long hfp_input_ptr, int number) throws Throwable;
  public static native void HFPVertex(String data_file, String hfp_file, String vertex_file) throws Throwable;
  //public static native void HFPSelect(String data_file, String hfp_file, double min_cumul_weight, int cardinality_strategy, double min_matching_degree, int min_cardinality, String result_file_name, double loss_coverage, int initial_number_of_mf, int max_number_of_iter, String vertex_file, int output_number, String validation_file) throws Throwable;
  public static native void HFPSelect(String data_file, String hfp_file, boolean ruleInductionMethodFPA, double min_cumul_weight, int cardinality_strategy, double min_matching_degree, int min_cardinality, String result_file_name, double loss_coverage, int initial_number_of_mf, int max_number_of_iter, String vertex_file, int output_number, String validation_file) throws Throwable;
  //public static native long HFPFIS(String data_file, String hfp_file, double min_cumul_weight, int cardinality_strategy, double min_matching_degree, int min_cardinality, int output_number, String vertex_file) throws Throwable;
  public static native long HFPFIS(String data_file, String hfp_file, boolean ruleInductionMethodFPA, double min_cumul_weight, int cardinality_strategy, double min_matching_degree, int min_cardinality, int output_number, String vertex_file) throws Throwable;
  public static native void ReplaceHFPOutput(long hfp_ptr, int output_number, long output_ptr) throws Throwable;
  public static native void SetHFPFIS(String hfp_in, String cfg, String hfp_out) throws Throwable;
  public static native long WangMendel(String fis, String data_file, String fis_out) throws Throwable;
  //public static native long HFPSR(String data_file, String inputs_mfs, int input_hierarchy, double input_tol, int output_mfs, int output_hierarchy, String output_defuz, String output_disj, double output_tol, String conjunction ) throws Throwable;
  public static native long HFPSR(String data_file, String inputs_mfs, int input_hierarchy, double input_tol, int output_mfs, int output_hierarchy, String output_defuz, String output_disj, double output_tol, String conjunction, boolean classif ) throws Throwable;
  // ent�tes du fichier performance
  public static native String PerfHeaderObserved() throws Throwable; /**< Ent�te de la colonne observ� du fichier performance @return String ent�te observ� */
  public static native String PerfHeaderInferred() throws Throwable; /**< Ent�te de la colonne inf�r� du fichier performance @return String ent�te inf�r� */
  public static native String PerfHeaderError() throws Throwable; /**< Ent�te de la colonne erreur du fichier performance @return String ent�te erreur */
  public static native String PerfHeaderClassInferred() throws Throwable; /**< Ent�te de la colonne classe inf�r� du fichier performance @return String ent�te classe inf�r� */
  public static native String PerfHeaderBlank() throws Throwable; /**< Ent�te de la colonne blanc du fichier performance @return String ent�te blanc */
  public static native String PerfHeaderMaxKernel() throws Throwable;
  public static native String PerfHeaderMinKernel() throws Throwable;
  public static native String PerfHeaderMinSupport() throws Throwable;  
  public static native String PerfHeaderMaxSupport() throws Throwable;
  public static native String PerfHeaderInferredSymbMatch() throws Throwable;
  // extensions des fichiers liens
  public static native String LinksItemsRulesExtension() throws Throwable; /**< Extension du fichier items rules */
  public static native String LinksRulesItemsExtension() throws Throwable; /**< Extension du fichier rules items */
  public static native String LinksRulesLinksExtension() throws Throwable; /**< Extension du fichier rules links */
  public static native String LinksRulesSortedExtension() throws Throwable; /**< Extension du fichier rules sorted */
  // sample
  public static native void GenerateSample(String dataFile, int sampleNumber, double validationDataRatio, int seed, boolean classif, int columnNumber, double precision, boolean replace, boolean truncate ) throws Throwable;
  // fonctions gabarit
  public static native void WriteTemplate(String file, double kw, double sw) throws Throwable; 
  public static native double [] ReadTemplate(String file) throws Throwable; 

  public static native void DataDistance(long fis, String dataFile, boolean normalize, double[] distances, double power, String outputFile);
}