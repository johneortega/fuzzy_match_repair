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
public class RunMachineTranslation{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean AVERAGE_DOC_WER        		= true; // show the WER of the entire doc averaging each sentence
        boolean SENTENCE_OUTPUT 		= true; // try to keep this one because others depend on it print out the sentence level information
        boolean SHOW_VALID_PATCHED_SENT 	= true; // show the sentences that have been patched
        final int SX_MAX_SENT_SIZE              = 100; // max size of s(i) sentence, set to 20 because of memory
        boolean SHOW_BEST_T                     = true; // show the best T(i) for T(N) patches 
							 // 12/18/2014, tau/tau' thing by mikel, abc abcd goode, then abcde not good
						 	 // here we can use feature 3 to limit

        CmdLineParser parser 			= new CmdLineParser();
        CmdLineParser.Option otestsource	= parser.addStringOption('s',"source");
        CmdLineParser.Option omt		= parser.addStringOption("mt");
        CmdLineParser.Option ogold   		= parser.addStringOption("gold");// using this as the comparer
        CmdLineParser.Option otmpath 		= parser.addStringOption("tm-path");

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
        String gold		= (String)parser.getOptionValue(ogold,null);
        String mt		= (String)parser.getOptionValue(omt,null);
        String tmpath		= (String)parser.getOptionValue(otmpath,null);

        if(gold==null){
            System.err.println("Error: It is necessary to define the path of the file where the features will be writen (use parameter --gold).");
            System.exit(-1);
        }
        if(mt==null){
            System.err.println("Error: It is necessary to define the path of the file where the machine translation will be writen (use parameter --mt).");
            System.exit(-1);
        }
        if(testsource==null){
            System.err.println("Error: It is needed to define the path to the source language segments of the test set (use parameter --test-source).");
            System.exit(-1);
        }
        List<Segment> stestsegs=new LinkedList<Segment>();
        List<Segment> goldSegs=new LinkedList<Segment>();
        List<Segment> mtSegs=new LinkedList<Segment>();
        try {
            stestsegs=TranslationMemory.ReadSegmentsFile(testsource);
            goldSegs = TranslationMemory.ReadSegmentsFile(gold);
            mtSegs = TranslationMemory.ReadSegmentsFile(mt);
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
	    // first make sure test size is less than 50
	    if(stestsegs.get(i).size()<=SX_MAX_SENT_SIZE){
		    theSPrimeSegment = stestsegs.get(i); 
		    theGoldSegment   = goldSegs.get(i);
		    theMTSegment     = mtSegs.get(i);
/*
We took this out to measure entire sentences, no FMS needed
		    
		    double bestFMSScoreForSourceSegment = 0.0;
		    Segment bestTUSourceSegment = null;
		    Segment bestTUTargetSegment = null;
		    theSPrimeSegment = stestsegs.get(i); 
		    theGoldSegment   = goldSegs.get(i);
		    theMTSegment   = mtSegs.get(i);
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
		    System.err.println(theMTSegment.toString()+" (MT)"); 
		    if(bestScoreGotten){
			    System.err.println(bestTUSourceSegment.toString()+" (S)");
			    System.err.println(bestTUTargetSegment.toString()+" (T)");
		    }
		    else{
			    // done for comparisons without fms match
			    System.err.println("(S)-- did not meet threshold, but should be counted in overall error");
			    System.err.println("(T)-- did not meet threshold, but should be counted in overall error");
		    }
*/
		    // theSPrimeSegment
		    // theGoldSegment
		    // bestFMSScoreForSourceSegment
		    // bestTUSourceSegment
		    // bestTUTargetSegment
		    // patch with these
		// these two vars are for if/else in patch from scenario 1 mikel
/*
We took this out to measure entire sentences, no FMS needed
		int bestEd = 0;
		int bestMaxWords=0;
		if(bestScoreGotten){
			bestEd = Segment.EditDistance(bestTUTargetSegment, theGoldSegment, null);
			bestMaxWords=java.lang.Math.max(bestTUTargetSegment.size(),theGoldSegment.size());
			// if the MT is better use it instead
			int bestMTEd = Segment.EditDistance(theMTSegment, theGoldSegment, null);
			int bestMTMaxWords=java.lang.Math.max(theMTSegment.size(),theGoldSegment.size());
			if(bestMTEd<bestEd)
				bestEd=bestMTEd;
			if(bestMTMaxWords<bestMaxWords)
				bestMaxWords=bestMTMaxWords;
		}
		else{
			bestEd = theGoldSegment.size();
			bestMaxWords=theGoldSegment.size();
		}
*/
		System.err.println("Example #"+i);
		System.err.println(theSPrimeSegment.toString()+" (S')");
		System.err.println(theGoldSegment.toString()+" (T')"); 
		System.err.println(theMTSegment.toString()+" (MT)"); 
		int bestEd = theGoldSegment.size();
		int bestMaxWords=theGoldSegment.size();
		// if the MT is better use it instead
		int bestMTEd = Segment.EditDistance(theMTSegment, theGoldSegment, null);
		int bestMTMaxWords=java.lang.Math.max(theMTSegment.size(),theGoldSegment.size());
		if(bestMTEd<bestEd)
			bestEd=bestMTEd;
		if(bestMTMaxWords<bestMaxWords)
			bestMaxWords=bestMTMaxWords;

		totalError+=bestEd;
		totalWords+=bestMaxWords;
		System.err.println(bestEd + " " + bestMaxWords);
	    }// if testseg <=50
	}// for test segs
	System.err.println("Relative Error is" + (totalError/totalWords));
	System.err.println("Ending app");
    }
}

