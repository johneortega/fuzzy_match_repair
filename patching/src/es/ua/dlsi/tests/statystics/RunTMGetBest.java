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
public class RunTMGetBest{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	// by default this is en-es, but should be passed by cmd line
        CmdLineParser parser 			= new CmdLineParser();
        CmdLineParser.Option otestsource	= parser.addStringOption('s',"source");
        CmdLineParser.Option otmsource 		= parser.addStringOption("tm-source");
        CmdLineParser.Option otmtarget 		= parser.addStringOption("tm-target");
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
        if(testsource==null){
            System.err.println("Error: It is needed to define the path to the source language segments of the test set (use parameter --test-source).");
            System.exit(-1);
        }
        TranslationMemory trans_memory=new TranslationMemory();
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
	Segment theGoldSegment   = null;
        for(int i=0; i<stestsegs.size(); i++){
	    Segment bestTUSourceSegment = null;
	    Segment bestTUTargetSegment = null;
	    theSPrimeSegment = stestsegs.get(i); 
	    List<Pair<Integer,Integer>> bestTalignment = null;
	    boolean bestScoreGotten = false;
	    double bestSScore = 0.0;
	    for(TranslationUnit tu: trans_memory.GetTUs()){

		    List<Pair<Integer,Integer>> talignment = new ArrayList<Pair<Integer,Integer>>();
		    double sscore=TranslationMemory.GetScoreNew(tu.getSource(), stestsegs.get(i), talignment);
		    Collections.sort(talignment, new Comparator<Pair<Integer,Integer>>() {
		       @Override public int compare(Pair<Integer,Integer> x, Pair<Integer,Integer> y) {
		       return x.getFirst().compareTo(y.getFirst());
		       }
		    });
		    // we want the best scored one
		    //if( sscore > bestSScore && sscore<1 && sscore>0.60){
		    if( sscore > bestSScore && sscore<1 && sscore>0.60){
			bestTalignment = talignment;
			bestTUSourceSegment = new Segment(tu.getSource());
			bestTUTargetSegment = new Segment(tu.getTarget());
			bestScoreGotten = true;
			bestSScore = sscore;
		    }//fms threshold
	    }//get TUS for loop
	    //System.err.println("Example #"+i);
	    //System.err.println(theSPrimeSegment.toString()+" (S')");
	    if(bestScoreGotten){
		    //System.err.println(theSPrimeSegment.toString());
		    System.err.println(bestTUSourceSegment.toString());
		    //System.err.println(bestTUSourceSegment.toString()+" (S)");
		    //System.err.println(bestTUTargetSegment.toString()+" (T)");
	    }
	    else{
		    //System.err.println("(S)-- did not meet threshold, but should be counted in overall error");
		    //System.err.println("(T)-- did not meet threshold, but should be counted in overall error");
	    }
								
	}// for test segs
    }
}

