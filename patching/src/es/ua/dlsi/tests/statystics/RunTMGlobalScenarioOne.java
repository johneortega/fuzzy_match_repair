/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.ua.dlsi.tests.statystics;

import es.ua.dlsi.segmentation.Segment;
import es.ua.dlsi.translationmemory.TranslationMemory;
import es.ua.dlsi.translationmemory.TranslationUnit;
import es.ua.dlsi.utils.CmdLineParser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.io.StringWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;

import es.ua.dlsi.utils.Pair;
import es.ua.dlsi.segmentation.Word;
import es.ua.dlsi.patching.BooleanPatchOperator;
import es.ua.dlsi.patching.PatchManager;
import es.ua.dlsi.patching.PatchOperator;
import es.ua.dlsi.patching.Patch;
import es.ua.dlsi.patching.PatchPhraseExtraction;
import es.ua.dlsi.patching.PatchPhrasePair;

import java.util.Locale;
import java.text.Collator;


/**
 *
 * @author john evan ortega 
 */
public class RunTMGlobalScenarioOne{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	// by default this is en-es, but should be passed by cmd line
	String trans_map			= "translation_map_en-es.ser";
	String langCmd 				= "en-es";// default at en-es
	boolean DO_TRANS_MAP			= false; // is this run to create a translation map?
	boolean DO_BASELINE			= false; // this is the baseline - no patching
	boolean IGNORE_FMS			= false; // this is the baseline - no patching
        boolean DEBUG           		= false; // show debug output
        boolean AVERAGE_DOC_WER        		= true; // show the WER of the entire doc averaging each sentence
        boolean SENTENCE_OUTPUT 		= true; // try to keep this one because others depend on it print out the sentence level information
        boolean SOURCE_OUTPUT   		= false; // show patch information 
        boolean SHOW_VALID_PATCHED_SENT 	= true; // show the sentences that have been patched
        final int SX_MAX_SENT_SIZE              = 100; // max size of s(i) sentence, set to 20 because of memory
        //final int MAX_PATCHOPS_SIZE             = 120; // if we have more than 20 patchops, exit
        final int MAX_PATCHOPS_SIZE             = 15; // if we have more than 20 patchops, exit
        boolean SIGMA_MAX_MIN 			= false; // feature 1 part of the Sigma size feature 1
        //int SIGMA_MIN 				= 2; // feature 1 these are for sigma size features
        //int SIGMA_MAX 				= 5; // feature 1
        int SIGMA_MIN 				= 2; // feature 1 these are for sigma size features
        int SIGMA_MAX 				= 4; // feature 1
	// this is for measuring success rates
	boolean MEASURE_TAUS			= false; // measure the taus
	Map<Integer, int[]> TAU_COUNT 		= new TreeMap<Integer,int[]>();//holds taus by length as key and count with totals as value
        boolean SHOW_BEST_T                     = true; // show the best T(i) for T(N) patches 
        boolean SHOW_PATCH_FMS                  = true; // by default we show WER, FMS is 1 - WER
        boolean USE_ANCHOR		        = false; // feature 2, anchor patch on left/right or both
        boolean USE_ANCHOR_LEFT		        = false; // feature 2, anchor patch on left/right or both
        boolean USE_ANCHOR_RIGHT	        = false; // feature 2, anchor patch on left/right or both
        boolean USE_PATCH_RATIO  	        = false; // feature 3, ex: Patch :  [15, 15] "( " --> "( ql ) "
        int USE_PATCH_RATIO_TAU_PRIME           = 3; // feature 3, This patch patches a positions of size 1 with a patch of size 3.
        int USE_PATCH_RATIO_TAU                 = 3; // feature 3, This ratio could be a parameter (|tau'|/|tau| ratio). -- feature 3
        boolean USE_PATCH_MISMATCH              = true; // feature 4, ex: Patch :  [15, 15] "( " --> "( ql ) "
						         // If you have a patch (tau1,tau1') and another patch for the 
							 // same mismatch (tau2,tau2') such that tau1 is a substring of tau2 and tau1' is a substring of 
							 // tau2', then the result will be the same and you can remove one or the other ** this is safe 
        boolean USE_PATCH_MISMATCH_TAU1         = true; // feature 5, ex: Patch :  [15, 15] "( " --> "( ql ) "
                                                         // If you have a patch (tau1,tau1') and another patch for the same mismatch (tau2,tau2') such 
                                                         // that tau1 is a substring of tau2, and tau1' is *not* a substring 
                                                         // of tau2', maybe it would be better to go for the longer patch, as it seems to have longer context.
	boolean USE_PATCH_MISMATCH_RESTRICTIVE  = false; // this should be used for "very" restrictive cases where s and s' patch positios
							// can't match, note, the USE_PATCH_* aove whould be set to false if this is set
        CmdLineParser parser 			= new CmdLineParser();
        CmdLineParser.Option otestsource	= parser.addStringOption('s',"source");
        CmdLineParser.Option otmsource 		= parser.addStringOption("tm-source");
        CmdLineParser.Option otmtarget 		= parser.addStringOption("tm-target");
        CmdLineParser.Option ogold   		= parser.addStringOption("gold");// using this as the comparer
        CmdLineParser.Option olang              = parser.addStringOption("lang");// using this as the comparer
        CmdLineParser.Option ofeatures          = parser.addStringOption("features");// using this as the comparer
        CmdLineParser.Option odebug 		= parser.addStringOption('d',"debug");
        CmdLineParser.Option otmpath 		= parser.addStringOption("tm-path");
        CmdLineParser.Option othreshold 	= parser.addDoubleOption("threshold");
        CmdLineParser.Option ofuzzymatchingscore= parser.addBooleanOption('f', "fms");
        CmdLineParser.Option omt		= parser.addStringOption("mt");

        try{
            parser.parse(args);
        }
        catch(CmdLineParser.IllegalOptionValueException e){
            System.err.println(e);
            System.exit(-1);
        }
        catch(CmdLineParser.UnknownOptionException e){
            System.err.println(e);
            System.exit(-1);
        }

        String testsource	= (String)parser.getOptionValue(otestsource,null);
        String tmsource		= (String)parser.getOptionValue(otmsource,null);
        String tmtarget		= (String)parser.getOptionValue(otmtarget,null);
        String gold		= (String)parser.getOptionValue(ogold,null);
        String mt		= (String)parser.getOptionValue(omt,null);
        String langpair 	= (String)parser.getOptionValue(olang,null);
        String tmpath		= (String)parser.getOptionValue(otmpath,null);
        String debug		= (String)parser.getOptionValue(odebug,null);
        String features         = (String)parser.getOptionValue(ofeatures,null);
        Double threshold	= (Double)parser.getOptionValue(othreshold,null);
        boolean fms		= (Boolean)parser.getOptionValue(ofuzzymatchingscore,false);
	

        if(threshold==null){
            System.err.println("Error: It is necessary to set a maximum lenght higher than 0 (use parameter --max-segment-len).");
            System.exit(-1);
        }
        if(gold==null){
            System.err.println("Error: It is necessary to define the path of the file where the features will be writen (use parameter --gold).");
            System.exit(-1);
        }
        if(langpair==null){
            System.err.println("Error: You must define the language pair like en-es, es-pt, or es-fr.");
            System.exit(-1);
        }
	else{
	    trans_map = "translation_map_"+langpair+".ser";
	    langCmd   = langpair;// default at en-es
	}
        if(testsource==null){
            System.err.println("Error: It is needed to define the path to the source language segments of the test set (use parameter --test-source).");
            System.exit(-1);
        }
        if(tmpath==null){
            if(tmsource==null || tmsource==null){
                System.err.println("Error: It is necessary to define the path of the file containing the translation memory java object(use parameter --tm-path).");
                System.exit(-1);
            }
        }
	// set up a list of current features with ints
        List<Integer> feats = null;
        if(features!=null){
	    try{
	       List<String> featsstring = Arrays.asList(features.split(","));
	       if(featsstring!=null)
	          feats = new ArrayList<Integer>();
	          for(String s : featsstring){
		     feats.add(Integer.parseInt(s));
	          }
	    }
	    catch(Exception e){
                System.err.println("Error: Features options should be like --features 1,2,3");
                System.exit(-1);
	    }
	}
        /* 
	    Features Are Set Here
        */
	if(feats!=null){
            // min/max sigma lengths
            // feature 1 - sigma min/max
	    SIGMA_MAX_MIN           = feats.contains(1);
	    USE_ANCHOR              = feats.contains(2);
	    USE_PATCH_RATIO         = feats.contains(3);
	    USE_PATCH_MISMATCH      = feats.contains(4);
	    USE_PATCH_MISMATCH_TAU1 = feats.contains(5);
	}

        TranslationMemory trans_memory=new TranslationMemory();
        if(tmpath!=null)
            trans_memory.LoadTMFromObject(tmpath);
        else
            trans_memory.LoadTM(tmsource, tmtarget);
        
        List<Segment> stestsegs=new LinkedList<Segment>();
        List<Segment> goldSegs=new LinkedList<Segment>();
        //List<Segment> mtSegs=new LinkedList<Segment>();
        try {
            stestsegs=TranslationMemory.ReadSegmentsFile(testsource);
            goldSegs = TranslationMemory.ReadSegmentsFile(gold);
            //mtSegs = TranslationMemory.ReadSegmentsFile(mt);
        } catch (FileNotFoundException ex) {
            System.err.print("Error: Test segments file '");
            System.err.print(testsource);
            System.err.print(ogold);
            System.err.println("' could not be found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.print("Error while reading source language test segments from file '");
            System.err.print(testsource);
            System.err.println("'");
            System.exit(-1);
        }
/*
"Example#" number
New source sentence S' in quotes, followed by "(S')"
Target reference sentence T' in quotes, followed by "(T')"
Best fuzzy match source S in quotes, followed by "(S)"
Best fuzzy match target T in quotes, followed by "(T)
The word "Repairs:"
Each patched sentence T~ in quotes, preceded by number of words and edit
distance to target reference (nothing else)
Best edit distance
Max(length(T~),length(T')) for best example

Such as

Repairs:
"Connecteu l' impressora a l' ordinador" 6 1
"Connecteu la impressora a l' ordinador" 6 0
"Connecteu l' impressora a l' ordinadpr" 6 1
"Connecteu la impressora a l' ordinador" 6 0
"Connecteu l' impressora a l' ordinador" 6 1
0 6
*/
	Segment theSPrimeSegment = null;
	Segment theGoldSegment   = null;
	Segment theMTSegment   = null;
	double totalError = 0.0;
    double totalWords = 0.0;
    for(int i=0; i<stestsegs.size(); i++){
		//theMTSegment     = mtSegs.get(i);
        //System.err.println("Example #"+i);
        //System.err.println(theMTSegment.toString());
	    // first make sure test size is less than 50
	    if(stestsegs.get(i).size()<=SX_MAX_SENT_SIZE){
		    
		    double bestFMSScoreForSourceSegment = 0.0;
		    Segment bestTUSourceSegment = null;
		    Segment bestTUTargetSegment = null;
		    theSPrimeSegment = stestsegs.get(i); 
		    theGoldSegment   = goldSegs.get(i);
		    List<Pair<Integer,Integer>> bestTalignment = null;
		    boolean bestScoreGotten = false;
		    for(TranslationUnit tu: trans_memory.GetTUs()){
                if(fms){

                    List<Pair<Integer,Integer>> talignment = new ArrayList<Pair<Integer,Integer>>();
                    double sscore=TranslationMemory.GetScoreNew(tu.getSource(), stestsegs.get(i), talignment);
                    Collections.sort(talignment, new Comparator<Pair<Integer,Integer>>() {
                       @Override public int compare(Pair<Integer,Integer> x, Pair<Integer,Integer> y) {
                       return x.getFirst().compareTo(y.getFirst());
                       }
                    });
                    //System.out.println("this is the threshold: " + threshold + " and this is the sscore: " + sscore);
                    // we want the best scored one
                    if(sscore>=threshold && sscore<1 && sscore > bestFMSScoreForSourceSegment){
                        bestTalignment = talignment;
                        bestFMSScoreForSourceSegment=sscore;
                        bestTUSourceSegment = new Segment(tu.getSource());
                        bestTUTargetSegment = new Segment(tu.getTarget());
                        bestScoreGotten = true;
                    }//fms threshold
                }// if fms
		    }//get TUS for loop
		    System.err.println("Example #"+i);
		    System.err.println(theSPrimeSegment.toString()+" (S')");
		    System.err.println(theGoldSegment.toString()+" (T')"); 
		    if(bestScoreGotten){
			    System.err.println(bestTUSourceSegment.toString()+" (S)");
			    System.err.println(bestTUTargetSegment.toString()+" (T)");
		    }
		    else{
			    System.err.println("(S)-- did not meet threshold, but should be counted in overall error");
			    System.err.println("(T)-- did not meet threshold, but should be counted in overall error");
		    }
			// these two vars are for if/else in patch from scenario 1 mikel
			int bestEd = 0;
			int bestMaxWords=0;
		    if(bestScoreGotten){
				bestEd = Segment.EditDistance(bestTUTargetSegment, theGoldSegment, null);
				bestMaxWords=java.lang.Math.max(bestTUTargetSegment.size(),theGoldSegment.size());
                System.err.println(bestTUTargetSegment.toString());
			}
			else{
				// here we run MT on the entire source sentences
				// and then get the edit distance between the gold segment
				// and the translated segment
				bestEd = theGoldSegment.size();
				bestMaxWords=theGoldSegment.size();
			}
			totalError+=bestEd;
			totalWords+=bestMaxWords;
			System.err.println(bestEd + " " + bestMaxWords);
	    }// if testseg <=50
	}// for test segs
	System.err.println("Relative Error is" + (totalError/totalWords));
	System.err.println("Ending app");
    }
}

