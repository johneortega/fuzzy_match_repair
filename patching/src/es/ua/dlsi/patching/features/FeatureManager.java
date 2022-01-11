/*
 * Copyright (C) 2011 Universitat d'Alacant
 *
 * author: John Evan Ortega
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package es.ua.dlsi.patching.features;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;
import es.ua.dlsi.utils.Pair;
import es.ua.dlsi.segmentation.*;
import es.ua.dlsi.patching.*;
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
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import es.ua.dlsi.segmentation.Segment;
import es.ua.dlsi.translationmemory.TranslationMemory;
import es.ua.dlsi.translationmemory.TranslationUnit;
import java.math.*;
import java.text.DecimalFormat;

/**
 * Series of Glassbox features from QE with Felipe and Mikel
 * https://www.sharelatex.com/project/54182d9e6a2be06c2ee1386c
 * This is a manager, it will be able to read a feature file in
 * and populate an object that has all of the information like operators
 * S,S', etc. to be worked on
 * separately, an output will be produced with Features
 * NOTE: there will be two files produced here
 * 1) an intermediate file with the initial patch operators, s', etc..
 * 2) a feature file that has the values needed
 * @author John Evan Ortega
 * @version 0.1
 */
public class FeatureManager{
	// should be able to load all features
	// using ids
	// also should be able to read in a feature file
	// the basic operations will be gotten from another class
	// that c
	String featureFile = null;
	Set<IntermediateFeature> ifFeatures = new HashSet<IntermediateFeature>();
	final static String TMSOURCE = "input/en_es.omega.tm.gz";
	final static String TMTARGET = "input/es_en.omega.tm.gz";
	//final static String sentWER  = "run_global_scenario_en_es_60.intermediate.dev.wer";
	final static String sentWER  = "run_global_scenario_en_es_60.intermediate.test.wer";
	public FeatureManager(String intermediateFile, String featureFile){
		// load all of the ifFeatures and sort by value
		if(loadIFile(intermediateFile)){
			System.err.println("Intermediate Data Loaded");
		}
		this.featureFile = featureFile;

	}
	public boolean loadIFile(String imFile){
		BufferedReader reader;
		String line;
		try {
		    reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(
			    new FileInputStream(imFile)), "UTF-8"));
		    int linecount = 1;
		    while((line=reader.readLine())!=null){
			IntermediateFeature ifeature = new IntermediateFeature(line,linecount);
			ifFeatures.add(ifeature);
			linecount++;
		    }
		    reader.close();
		} catch (IOException ex) {
		    ex.printStackTrace(System.err);
		    System.err.println("Error while trying to read intermediate file.");
		    return false;
		}
		return true;
	}
	/**
	 * takes all the features and writes them out
	 * @param foutput Output file
	 */
	public void process(){
        	TranslationMemory trans_memory=new TranslationMemory();
            	//trans_memory.LoadTM(TMSOURCE, TMTARGET);
                // create an array of the features you want to add
                List<OutputFeature> oFeatures = new ArrayList<OutputFeature>();
	        long startTime = System.currentTimeMillis();
       		long endTime = System.currentTimeMillis();

/// DELETE BELOW LATER
                //oFeatures.add(new OutputFeature27());// s'
                //oFeatures.add(new OutputFeature47());// s
                //oFeatures.add(new OutputFeature29());// t
                //oFeatures.add(new OutputFeature30());// t~
                //oFeatures.add(new OutputFeature51());// t'

/// DELETE ABOVE LATER
                oFeatures.add(new OutputFeature1()); /// removed for experiment
                oFeatures.add(new OutputFeature2());
                oFeatures.add(new OutputFeature3());
                oFeatures.add(new OutputFeature4()); // removed for experiment
                oFeatures.add(new OutputFeature5());
                oFeatures.add(new OutputFeature6());
                oFeatures.add(new OutputFeature7());
                oFeatures.add(new OutputFeature8());
                oFeatures.add(new OutputFeature9());
                oFeatures.add(new OutputFeature10());
                oFeatures.add(new OutputFeature11());
                oFeatures.add(new OutputFeature12());
                oFeatures.add(new OutputFeature13());
                oFeatures.add(new OutputFeature14());
                oFeatures.add(new OutputFeature15());
                oFeatures.add(new OutputFeature16());
                oFeatures.add(new OutputFeature17());
                oFeatures.add(new OutputFeature18());
                //oFeatures.add(new OutputFeature19(trans_memory));//keep this one commented
                oFeatures.add(new OutputFeature34());// the size of t 
                oFeatures.add(new OutputFeature20());
                oFeatures.add(new OutputFeature21());
                oFeatures.add(new OutputFeature35());// the number of patchops
                oFeatures.add(new OutputFeature36());// the number of tokens in s'
                oFeatures.add(new OutputFeature37());// the number of tokens in t-~
                oFeatures.add(new OutputFeature38());// the number of punc marks in s'
                oFeatures.add(new OutputFeature39());// the number of punc marks in t-~
                oFeatures.add(new OutputFeature40());// is it grounded or no
                oFeatures.add(new OutputFeature41());// num patches grounded / total patches 
                oFeatures.add(new OutputFeature42());// num punc marks in t~ / num punc in s' 
                oFeatures.add(new OutputFeature43());// num digits in t~ / num digits in s'
                oFeatures.add(new OutputFeature48());// num digits in s'
                oFeatures.add(new OutputFeature49());// num digits in t~
                oFeatures.add(new OutputFeature50());// num tokens in t-~ / num of tok in s'
                oFeatures.add(new OutputFeature0());// success rate or wer
///**** PUT BACK ***/
                oFeatures.add(new OutputFeature24());// the ed distance between t and twiggle (no max, 0) 
                oFeatures.add(new OutputFeature27());// printed out sprime
                oFeatures.add(new OutputFeature28());// printed out edit distance number like 18 55
                oFeatures.add(new OutputFeature45());// printed out number of edits for twiggle
                oFeatures.add(new OutputFeature46());// printed out number of edits for t
/*// PUT BACK ABOVE***********/
                //oFeatures.add(new OutputFeature33(sentWER));// the best wer for this sentence 
		/*
                oFeatures.add(new OutputFeature25());// the denominator of the max success equation
                oFeatures.add(new OutputFeature26());// the numberator of the success equation
                //oFeatures.add(new OutputFeature32()); // this print out all info
		// 22 and 23 are for other things related to general questions from Felipe
                //oFeatures.add(new OutputFeature22());
                //oFeatures.add(new OutputFeature23());
		*/
		PrintWriter pw;
		try{
			pw=new PrintWriter(new GZIPOutputStream(new FileOutputStream(featureFile)));
			// this goes through all the lines from the intermediate file
			// we need to go through all the output features
			// and print them out
		    	for(IntermediateFeature ifeature : ifFeatures){
				// temp, skip if success rate <= 0
				//OutputFeature sr = new OutputFeature0();
 				// add only if miquelespla gomis says so
				OutputFeature sr = new OutputFeature44();
				sr.setFeature(ifeature);
				String srOut = sr.process();
				Double srInt = Double.valueOf(srOut);
				// uncomment for espla-gomis
				//if(srInt==1.0){
					StringBuilder oString = new StringBuilder();
					int i = 0;
					for(OutputFeature ofeature : oFeatures){
						ofeature.setFeature(ifeature);
                    /* remove code later
                        String procOFeat = ofeature.process();
                            try
                            {
                              DecimalFormat df = new DecimalFormat("#.####");
                              df.setRoundingMode(RoundingMode.CEILING);
                              Double theDub = Double.parseDouble(procOFeat);
                              procOFeat = String.valueOf(df.format(theDub));
                              
                            }
                            catch(NumberFormatException e)
                            {
                              //not a double
                            }
						oString.append(procOFeat);
                    //end code later */
                    
						oString.append(ofeature.process());
						oString.append("\t");
						i++;
					}
					pw.println(oString.toString());
				//}//espla-gomis if
			}
		    	pw.close();
		}
		catch(FileNotFoundException ex){
		    	System.err.println("Error: Output file "+featureFile +" not found.");
		}
		catch(IOException ex){
		    	System.err.println("Error while reading file "+ featureFile +".");
		}
	}
	public static void main(String[] args){
        	CmdLineParser parser 			= new CmdLineParser();
        	CmdLineParser.Option oimfile		= parser.addStringOption('i',"intermediate");
        	CmdLineParser.Option offile		= parser.addStringOption('f',"feature");
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
        	String imfile		= (String)parser.getOptionValue(oimfile,null);
        	String ffile		= (String)parser.getOptionValue(offile,null);
		if(imfile==null){
		    System.err.println("Error: You must enter an intermediate file path (use parameter -i).");
		    System.exit(-1);
		}
		if(ffile==null){
		    System.err.println("Error: You must enter an feature out file path (use parameter -f).");
		    System.exit(-1);
		}
		FeatureManager fm 	= new FeatureManager(imfile,ffile);
		fm.process();
		System.err.println("Process Done.");
	}

}
