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
public class RunTMCreate{
    final static int SX_MAX_SENT_SIZE              = 100; // max size of s(i) sentence, set to 20 because of memory
    final static int SENT_NEEDED                   = 16000; // number of sentences
    final static String newSource                  = "en_de.new.source"; // number of sentences
    final static String newTarget                  = "en_de.new.target"; // number of sentences
    final static String newTMSource                  = "en_de.new.tm.source"; // number of sentences
    final static String newTMTarget                  = "en_de.new.tm.target"; // number of sentences

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CmdLineParser parser 			= new CmdLineParser();
        CmdLineParser.Option otestsource	= parser.addStringOption('s',"source");
        CmdLineParser.Option otmsource 		= parser.addStringOption("tm-source");
        CmdLineParser.Option otmtarget 		= parser.addStringOption("tm-target");
        CmdLineParser.Option otmpath 		= parser.addStringOption("tm-path");
        CmdLineParser.Option othreshold 	= parser.addDoubleOption("threshold");

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
        String tmpath		= (String)parser.getOptionValue(otmpath,null);
        Double threshold	= (Double)parser.getOptionValue(othreshold,null);
	

        if(threshold==null){
            System.err.println("Error: It is necessary to set a maximum lenght higher than 0 (use parameter --max-segment-len).");
            System.exit(-1);
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
	System.out.println("Loading TM");
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
        TranslationMemory oldTM = new TranslationMemory();
        TranslationMemory newSourceTM = new TranslationMemory();
        // loop through the tu against itself
        // make sure it doesn't exist in the source and matches fms
	System.out.println("Reading through TUs:");
	int tuAdded = 0;
        for(TranslationUnit tu1: trans_memory.GetTUs()){
        	for(TranslationUnit tu2: trans_memory.GetTUs()){
    			if(isGoodSegment(stestsegs, tu1.getSource())){
    				if(passesFMS(tu1.getSource(), tu2.getSource(), threshold)){
    					if(!isInNewTM(tu1.getSource(), newSourceTM)){
						//System.out.println("Adding new Source Sentence"+tu1.getSource().toString());
						newSourceTM.AddTU(tu1.getSource().toString(),tu1.getTarget().toString());
						tuAdded++;
						if(!isInNewTM(tu2.getSource(), oldTM)){
							//System.out.println("Adding new TM Sentence"+tu2.getSource().toString());
							oldTM.AddTU(tu2.getSource().toString(),tu2.getTarget().toString());
							break;
						}
						if(tuAdded >= SENT_NEEDED)
							break;
					}
				}
			}

		}
		if(tuAdded >= SENT_NEEDED)
			break;
        }//get TUS for loop
	System.out.println("Writing TM and Source files for " + tuAdded + " sentences:");
	System.out.println("es_pt.new.source"); // number of sentences
	System.out.println("es_pt.new.target"); // number of sentences
	System.out.println("es_pt.new.tm.source"); // number of sentences
	System.out.println("es_pt.new.tm.target"); // number of sentences
        oldTM.WriteSegmentsFile(newTMSource, newTMTarget);
        newSourceTM.WriteSegmentsFile(newSource, newTarget);
	System.out.println("Ending app");
    }
    public static boolean isGoodSegment(List<Segment> stestsegs, Segment otherSegment){
	if(otherSegment.size()>SX_MAX_SENT_SIZE){
		return false;
	}
	// does it exist in the source?
        for(int i=0; i<stestsegs.size(); i++){
		if(stestsegs.get(i).equals(otherSegment))
			return false;
	
	}
	return true;
    }
    public static boolean passesFMS(Segment mainSegment, Segment otherSegment, double threshold){
        List<Pair<Integer,Integer>> talignment = new ArrayList<Pair<Integer,Integer>>();
        double sscore=TranslationMemory.GetScoreNew(mainSegment, otherSegment, talignment);
        // we want the best scored one
        if(sscore>=threshold && sscore<1){
		return true;
        }
	else{
		return false;
	}
    }
    public static boolean isInNewTM(Segment testSegment, TranslationMemory tm){
	for(TranslationUnit tu2: tm.GetTUs()){
		if(tu2.getSource().equals(testSegment))
			return true;
	}
	return false;
    }
}

