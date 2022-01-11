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
public class RunGetSigmaAndSigmaPrimes{
	
    public static void main(String[] args) {
	String langCmd 				= "en-es";// default at en-es
        boolean SIGMA_MAX_MIN 			= false; // feature 1 part of the Sigma size feature 1
        int SIGMA_MIN 				= 2; // feature 1 these are for sigma size features
        int SIGMA_MAX 				= 4; // feature 1
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
        TranslationMemory trans_memory=new TranslationMemory();
        if(tmpath!=null)
            trans_memory.LoadTMFromObject(tmpath);
        else
            trans_memory.LoadTM(tmsource, tmtarget);
        
        List<Segment> stestsegs=new LinkedList<Segment>();
        try {
            stestsegs=TranslationMemory.ReadSegmentsFile(testsource);
        } catch (FileNotFoundException ex) {
            System.err.print("Error: Test segments file '");
            System.err.print(testsource);
            System.err.println("' could not be found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.print("Error while reading source language test segments from file '");
            System.err.print(testsource);
            System.err.println("'");
            System.exit(-1);
        }
	Segment theSPrimeSegment = null;
	double totalError = 0.0;
        double totalWords = 0.0;
        for(int i=0; i<stestsegs.size(); i++){
	    theSPrimeSegment = stestsegs.get(i); 
	    Segment bestTUSourceSegment = null;
	    PatchManager patchManager = new PatchManager();
	    for(TranslationUnit tu: trans_memory.GetTUs()){
		if(fms){

		    List<Pair<Integer,Integer>> talignment = new ArrayList<Pair<Integer,Integer>>();
		    double sscore=TranslationMemory.GetScoreNew(tu.getSource(), stestsegs.get(i), talignment);
		    Collections.sort(talignment, new Comparator<Pair<Integer,Integer>>() {
		       @Override public int compare(Pair<Integer,Integer> x, Pair<Integer,Integer> y) {
		       return x.getFirst().compareTo(y.getFirst());
		       }
		    });
		    // we want the best scored one
		    if(sscore>=threshold && sscore<1){
			bestTUSourceSegment = new Segment(tu.getSource());
			List<PatchPhrasePair> phrasePairs = patchManager.getAllPhrasePairs(bestTUSourceSegment, theSPrimeSegment, 
							talignment,SIGMA_MAX_MIN, SIGMA_MAX, SIGMA_MIN);
			for(PatchPhrasePair phrase : phrasePairs){
				int fstart  = phrase.foreignPair.getFirst();
				int fend    = phrase.foreignPair.getSecond();
				int estart  = phrase.englishPair.getFirst();
				int eend    = phrase.englishPair.getSecond();
				StringBuilder sPrime = new StringBuilder();
				for(int fctr = fstart; fctr<=fend;fctr++){
					sPrime.append(theSPrimeSegment.getWord(fctr).getValue().toString()+" ");
				}
				StringBuilder sX = new StringBuilder();
				for(int ectr = estart; ectr<=eend;ectr++){
					sX.append(bestTUSourceSegment.getWord(ectr).getValue().toString()+" ");
				}
				System.err.println(sX.toString().toLowerCase()); 
				System.err.println(sPrime.toString().toLowerCase()); 
			} // end for loop phrase pair
		    }
		}//end fms
	    }// end tu for
	}// for test segs
	System.err.println("Ending app");
    }// end main
}//end class

