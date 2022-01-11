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
public class RunCreateMT{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	String langCmd 				= "en-es";// default at en-es
        CmdLineParser parser 			= new CmdLineParser();
        CmdLineParser.Option otestsource	= parser.addStringOption('s',"source");
        CmdLineParser.Option olang              = parser.addStringOption("lang");// using this as the comparer
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
        langCmd			= (String)parser.getOptionValue(olang,null);

        if(testsource==null){
            System.err.println("Error: It is needed to define the path to the source language segments of the test set (use parameter --test-source).");
            System.exit(-1);
        }
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
        for(int i=0; i<stestsegs.size(); i++){
	    theSPrimeSegment = stestsegs.get(i); 
	    System.out.println(getTranslationNoMap(theSPrimeSegment.toString(), langCmd));
	}
	System.err.println("Ending app");
    }
    private final static String getTranslationNoMap(final String input, final String langCmd){
	String finalline=new String("");
        try {
		//String[] command = {"apertium", "-u", langCmd};
		String[] command = {"/home/johneortega/software/apertium.r48232/apertium/apertium/apertium", "-u", langCmd};
		ProcessBuilder probuilder = new ProcessBuilder( command );

		Process process = probuilder.start();
		OutputStream stdin = process.getOutputStream ();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
		writer.write(input);
		writer.newLine();
		writer.flush();
		writer.close();

		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
 		String line;
		while ((line = br.readLine()) != null) {
		    finalline+=line;
		}
		br.close();
        } catch (Exception e) {
            	e.printStackTrace();

	}
	return finalline.toLowerCase();
   } 
}

