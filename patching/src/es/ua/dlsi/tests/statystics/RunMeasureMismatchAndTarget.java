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
public class RunMeasureMismatchAndTarget{

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
	boolean MEASURE_SIGMAS			= false; // measure the taus
	Map<Integer, int[]> SIG_COUNT 		= new TreeMap<Integer,int[]>();//holds sigmas by length as key and count with totals as value
        boolean SHOW_BEST_T                     = true; // show the best T(i) for T(N) patches 
        boolean SHOW_PATCH_FMS                  = true; // by default we show WER, FMS is 1 - WER
        boolean USE_ANCHOR		        = false; // feature 2, anchor patch on left/right or both
        boolean USE_ANCHOR_LEFT		        = false; // feature 2, anchor patch on left/right or both
        boolean USE_ANCHOR_RIGHT	        = false; // feature 2, anchor patch on left/right or both
        boolean USE_PATCH_RATIO  	        = false; // feature 3, ex: Patch :  [15, 15] "( " --> "( ql ) "
        int USE_PATCH_RATIO_TAU_PRIME           = 3; // feature 3, This patch patches a positions of size 1 with a patch of size 3.
        int USE_PATCH_RATIO_TAU                 = 3; // feature 3, This ratio could be a parameter (|tau'|/|tau| ratio). -- feature 3
        boolean USE_PATCH_MISMATCH              = false; // feature 4, ex: Patch :  [15, 15] "( " --> "( ql ) "
						         // If you have a patch (tau1,tau1') and another patch for the 
							 // same mismatch (tau2,tau2') such that tau1 is a substring of tau2 and tau1' is a substring of 
							 // tau2', then the result will be the same and you can remove one or the other ** this is safe 
        boolean USE_PATCH_MISMATCH_TAU1         = false; // feature 5, ex: Patch :  [15, 15] "( " --> "( ql ) "
                                                         // If you have a patch (tau1,tau1') and another patch for the same mismatch (tau2,tau2') such 
                                                         // that tau1 is a substring of tau2, and tau1' is *not* a substring 
                                                         // of tau2', maybe it would be better to go for the longer patch, as it seems to have longer context.
	boolean USE_PATCH_MISMATCH_RESTRICTIVE  = true; // this should be used for "very" restrictive cases where s and s' patch positios
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
        try {
            stestsegs=TranslationMemory.ReadSegmentsFile(testsource);
            goldSegs = TranslationMemory.ReadSegmentsFile(gold);
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
	double totalError = 0.0;
        double totalWords = 0.0;
	double totalBestOpSize = 0.0;
        double totalBestOpCount = 0.0;
	double totalBestReplSize = 0.0;
        double totalBestReplCount = 0.0;
	Map<String,String> translations = new HashMap<String,String>();
	try{
		// load the translation map
		FileInputStream fis = new FileInputStream(trans_map);
		ObjectInputStream ois = new ObjectInputStream(fis);
		translations = (Map<String,String>) ois.readObject();
		ois.close();
		fis.close();
	}
	catch(Exception e){
	    Writer writer = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(writer);
	    e.printStackTrace(printWriter);
	    String s = writer.toString();
            System.err.println("Error while reading in serialized " + trans_map);
            System.err.println("File probably empty");
            System.err.println(s);
            //System.exit(-1);
	}


        for(int i=0; i<stestsegs.size(); i++){
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
			    // done for comparisons without fms match
			    if(IGNORE_FMS){
				    continue;
			    }
			    else{
				    System.err.println("(S)-- did not meet threshold, but should be counted in overall error");
				    System.err.println("(T)-- did not meet threshold, but should be counted in overall error");
			    }
		    }
		    // theSPrimeSegment
		    // theGoldSegment
		    // bestFMSScoreForSourceSegment
		    // bestTUSourceSegment
		    // bestTUTargetSegment
		    // patch with these
		    PatchManager patchManager = new PatchManager();
		    List<PatchOperator> patchOps = new ArrayList<PatchOperator>();
		    int patchOpsCounter = 0;
		    if(bestScoreGotten && !DO_BASELINE){
			        //int nGrams = 3;
				List<PatchPhrasePair> phrasePairs = new ArrayList<PatchPhrasePair>();
				// there's actually various patch situations here
				// english sentence is Sx, foreign S'
				PatchPhraseExtraction pe = new PatchPhraseExtraction(bestTUSourceSegment,theSPrimeSegment,bestTalignment);
				//for (int sxBegin=0; sxBegin < bestTUSourceSegment.getSentence().size();sxBegin++) {
					//int sxEnd = sxBegin + (nGrams-1);
					//if( sxEnd >= bestTUSourceSegment.getSentence().size())
				int sxBegin=0;
				int sxEnd = bestTUSourceSegment.getSentence().size()-1;
				// the idea here is to get all the phrase pairs but avoid duplicates
				List<PatchPhrasePair> tmpPhrasePairs = null;
				if(SIGMA_MAX_MIN)
				   tmpPhrasePairs = pe.extractPhrasePairsWithMaxMin(sxBegin,sxEnd, SIGMA_MAX,SIGMA_MIN);
				else
				   tmpPhrasePairs = pe.extractPhrasePairs(sxBegin,sxEnd);
				if(tmpPhrasePairs!=null){
					for(PatchPhrasePair phrasePair : tmpPhrasePairs){
						int engFirst  = phrasePair.englishPair.getFirst();
						int engSecond = phrasePair.englishPair.getSecond();
						int forFirst  = phrasePair.foreignPair.getFirst();
						int forSecond = phrasePair.foreignPair.getSecond();
						// english sentence is Sx, foreign S'-theSprimSegment
    						boolean sameWords = bestTUSourceSegment.wordsEqual(engFirst, engSecond, theSPrimeSegment, forFirst,forSecond);
						if(! phrasePairs.contains(phrasePair) && !sameWords){
							//System.err.println("Adding new phrasepair: " + phrasePair.englishPair.getFirst() + "-" + phrasePair.englishPair.getSecond() + "-" + phrasePair.foreignPair.getFirst()+ "-" +phrasePair.foreignPair.getSecond());
							phrasePairs.add(phrasePair);
						}
					}
					
				}
				
			    //}// end loop for phrasePairs should contain only distinct english, foreign begin,end positions

				boolean translationFound 	= false;
				String translation 		= null;
			 	Set<Segment> TAUS_TRIED 	= null;
			 	Set<Segment> SIGS_TRIED 	= null;
				if(MEASURE_TAUS)
					TAUS_TRIED = new HashSet<Segment>();
				if(MEASURE_SIGMAS)
					SIGS_TRIED = new HashSet<Segment>();
				// for every misalignment we should try to get the patch
				for(PatchPhrasePair phrase : phrasePairs){
					int fstart  = phrase.foreignPair.getFirst();
					int fend    = phrase.foreignPair.getSecond();
					int estart  = phrase.englishPair.getFirst();
					int eend    = phrase.englishPair.getSecond();
					if(DEBUG){	
						System.err.println("\n|================ s(X) and SPrime Gram Compare  ====================|\n");
						System.err.println("\n S(X) Current Translation Check is: (" + estart +", " + eend + ")\n");
						System.err.println("\n SPrime Current Translation Check is: (" + fstart +", " + fend + ")\n");
					}
					
					StringBuilder sPrime = new StringBuilder();
					for(int fctr = fstart; fctr<=fend;fctr++){
						sPrime.append(theSPrimeSegment.getWord(fctr).getValue().toString()+" ");
					}
					StringBuilder sX = new StringBuilder();
					for(int ectr = estart; ectr<=eend;ectr++){
						sX.append(bestTUSourceSegment.getWord(ectr).getValue().toString()+" ");
					}
					if(DEBUG){
						System.err.println("\n|================ S Prime TO S X Comparison ====================|\n");
						System.err.println("This is the s X ngram window: " + sX.toString().toLowerCase()); 
						System.err.println("This is the s prime ngram window: " + sPrime.toString().toLowerCase()); 
					}
					// here we need to see when the word is different and translate it		
					if(!sPrime.toString().toLowerCase().equals(sX.toString().toLowerCase()) && sX.toString().length()>0){
						// feature 2, patches with left/right or both
						if(USE_ANCHOR){
							boolean leftAncHit = false;
							boolean rightAncHit = false;
							boolean mismatchHit = false;
							// want to know when left is there or right is there
							int ectr=estart;
							for(int fctr = fstart; fctr<=fend;fctr++){
								if(ectr < bestTUSourceSegment.size()){
									String sPrimeWord=theSPrimeSegment.getWord(fctr).getValue().toString();
									String sXWord=bestTUSourceSegment.getWord(ectr).getValue().toString();
									if(sPrimeWord.equals(sXWord) && mismatchHit){
										rightAncHit=true;
										mismatchHit=false;//we do this for a consec mismatch for right
									}
									else if(sPrimeWord.equals(sXWord)){
										leftAncHit=true;
										mismatchHit=false;//we do this for a consec mismatch for right
									}
									else{
										//mismatch
										mismatchHit=true;

									}
									ectr++;
								}
							}
							if(USE_ANCHOR_LEFT){
								if(!leftAncHit)
									continue;// left anchor not there	
							}
							if(USE_ANCHOR_RIGHT){
								if(!rightAncHit)
									continue;// right anchor not there
							} 
						}
						// ADD CACHE TO MAP
						// sX is sigma and translation is tau
						translation = getTranslation(sX.toString(), langCmd, translations,DO_TRANS_MAP,trans_map);	
						translation = translation.toLowerCase();
						if(DEBUG){
							System.err.println("\n|================ SX TRANSLATION ====================|\n");
							System.err.println("The translation for SX segment: "+ sX + " is: " + translation);
							System.err.println("|=====================================================|\n");
						}
						if(translation!=null&&translation.length()>0){
							// create a segment with the translation
							Segment transSegment = new Segment(translation);
							// create a segment with the translation
							Segment sxSegment = new Segment(sX.toString());
							// check if tu has translation segment, TAU exists in T
							List<Pair<Integer,Integer>> tuContains = bestTUTargetSegment.AppearsNew(transSegment); 
							if(DEBUG){
								System.err.println("\n|================ TU Contains ====================|\n");
								System.err.println("The transSegment is: " + transSegment.toString());
								System.err.println("The List tuContains is of size: " + tuContains.size());
								System.err.println("|=====================================================|\n");
							}
							// here we are going to add to the TAU count or not
							// we are measuring where TAU exists in T
							int[] patchTauCount = {0, 0};
							if(MEASURE_TAUS){
								if(TAU_COUNT.get(transSegment.size())!=null)
									patchTauCount=TAU_COUNT.get(transSegment.size());
			 					if(!TAUS_TRIED.contains(transSegment)){
									// taus of this length have been done before
									// add to total 
									// the two dimensions are
									// total taus, total match
									patchTauCount[0]=patchTauCount[0]+1;
									// successful
									if(tuContains!=null && tuContains.size()>0){
										/// above it would have be initialized for this tau size
										/// here we are addign to the successfule taus
										patchTauCount[1]=patchTauCount[1]+1;
									}
									TAU_COUNT.put(transSegment.size(), patchTauCount);
									// adding one more tried here
									TAUS_TRIED.add(transSegment);
									System.err.println("TAU_RESULT:The tau is: " + transSegment.toString());
									System.err.println("TAU_RESULT:The transSegment is: " + bestTUTargetSegment);
									System.err.println("TAU_RESULT:The transSegment size is: " + transSegment.size());
								}
							}
							// here we are going to add to the TAU count or not
							// we are measuring where TAU exists in T
							int[] patchSigCount = {0, 0};
							if(MEASURE_SIGMAS){
								if(SIG_COUNT.get(sxSegment.size())!=null)
									patchSigCount=SIG_COUNT.get(sxSegment.size());
			 					if(!SIGS_TRIED.contains(sxSegment)){
									// taus of this length have been done before
									// add to total 
									// the two dimensions are
									// total taus, total match
									patchSigCount[0]=patchSigCount[0]+1;
									SIG_COUNT.put(sxSegment.size(), patchSigCount);
									// adding one more tried here
									SIGS_TRIED.add(sxSegment);
									System.err.println("SIG_RESULT:The sigma is: " + sxSegment.toString());
									System.err.println("SIG_RESULT:The sigma size is: " + sxSegment.size());
								}
							}
							if(tuContains!=null && tuContains.size()>0){
								for(Pair<Integer,Integer> matchedTu : tuContains){
									int firstTu = matchedTu.getFirst();
									int secondTu = matchedTu.getSecond();
									Pair<Integer,Integer> origSentPosPatch1 = new Pair(firstTu,secondTu); // "red"  
									Pair<Integer,Integer> origSPositions = new Pair(estart,eend); // s positions 
									String finalSPrimeTranslation = getTranslation(sPrime.toString(), langCmd, translations,DO_TRANS_MAP,trans_map);	
									// only do more if you are not doing map
									if(!DO_TRANS_MAP){
										finalSPrimeTranslation = finalSPrimeTranslation.toLowerCase();
										Segment PatchOne = new Segment(finalSPrimeTranslation.toString());
										if(DEBUG){
											System.err.println("The first Matched TU Position is: " + matchedTu.getFirst());
											System.err.println("The second Matched TU Position is: " + matchedTu.getSecond());
											System.err.println("The patching word to insert is: " + finalSPrimeTranslation.toString());
										}
										PatchOperator patchOpOne   =  new PatchOperator(origSentPosPatch1, PatchOne);
										if(USE_PATCH_RATIO){
											int tauPrimeSize = (secondTu - firstTu)+1;
											int tauSize      = PatchOne.size();
											if((tauPrimeSize<=USE_PATCH_RATIO_TAU_PRIME) && (tauSize <=USE_PATCH_RATIO_TAU)){ 
												// 02/10/2015 add patch tuSourcSegment for print
												patchOpOne.setTuSourceSegment(new Segment(sX.toString()));
												patchOpOne.setSprimeSegment(new Segment(sPrime.toString()));
												patchOpOne.setOrigSPos(origSPositions);
												patchOpOne.setOffset(patchOpsCounter);
												patchOpsCounter++;
												if(!PatchOperator.list_contains(patchOps, patchOpOne)){
												//if(!patchOps.contains(patchOpOne)){
													patchOps.add(patchOpOne);
												}
											}
										}
										else{
											// 02/10/2015 add patch tuSourcSegment for print
											patchOpOne.setTuSourceSegment(new Segment(sX.toString()));
											patchOpOne.setSprimeSegment(new Segment(sPrime.toString()));
											patchOpOne.setOrigSPos(origSPositions);
											patchOpOne.setOffset(patchOpsCounter);
											patchOpsCounter++;
											//if(!patchOps.contains(patchOpOne)){
											if(!PatchOperator.list_contains(patchOps, patchOpOne)){
												patchOps.add(patchOpOne);
												if(DEBUG)
													System.out.println(patchOpOne.toString());
											}
											//}
										}// end else+
										// feature 4 using size here for a speed factor as avoiding this goes faster
										//System.err.println("Before mismatch");
										if(USE_PATCH_MISMATCH && patchOps.size()>0){
											//System.err.println("Before remove");
											 //Patch.printOps(patchOps);
											usePatchMismatch(patchOps, patchOpOne);
											//System.err.println("After remove");
										   //Patch.printOps(patchOps);
										}
										// feature 5 using size here for a speed factor as avoiding this goes faster
										if(USE_PATCH_MISMATCH_TAU1 && patchOps.size()>0){
											usePatchMismatchTau1(patchOps, patchOpOne);
										}
										
										// this is for very restrictive patch ops
										// where one op cannot work on another (see restricive emails)
										if(USE_PATCH_MISMATCH_RESTRICTIVE && patchOps.size()>0){
											useRestrictivePatchMismatch(patchOps, patchOpOne);
										}
										// we do this to save to the maximum amount of patches
										// this way it continues with at leat the maximum
										// if it's equal, then the ops will be processed
										// if not, or greater than, ops will be skipped
										if(patchOps.size()>=MAX_PATCHOPS_SIZE)
											break;
										//System.err.println("After use patch size:" + patchOps.size());
										//Patch.printOps(patchOps);
									}// end DO_TRANS_MAP
									
								}// end for tu containse
							}// end if tuCtonains!=null
						}
					}// end check if string equals
				} // end for loop phrase pair
			   //}// end for loop 
		   }// end bestgotten
		 if(patchOps!=null && DEBUG)
			 for(PatchOperator oper : patchOps)
				System.out.println(oper.toString());
		 //Patch.printOps(patchOps);
		 if(!DO_TRANS_MAP){
									
			// these two vars are for if/else in patch from scenario 1 mikel
			int bestEd = 0;
			int bestMaxWords=0;
                        int bestOpSize=0;
			int bestOpCount=0;
                        int bestReplSize=0;
			int bestReplCount=0;
		    	if(bestScoreGotten){
				// System.err.println("INSIDE BEST SCORE GOTTENT");
				bestEd = Segment.EditDistance(bestTUTargetSegment, theGoldSegment, null);
				bestMaxWords=java.lang.Math.max(bestTUTargetSegment.size(),theGoldSegment.size());
			}
			/* no need to translate here
			else{
				// System.err.println("INSIDE BEST not SCORE GOTTENT");
				// here we run MT on the entire source sentences
				// and then get the edit distance between the gold segment
				// and the translated segment
				bestEd = theGoldSegment.size();
				bestMaxWords=theGoldSegment.size();
				String mtTrans = getTranslationNoMap(theSPrimeSegment.toString(), langCmd);	
				mtTrans        = mtTrans.toLowerCase();
				Segment mtTransSeg = new Segment(mtTrans);	
				int mtBestEd = Segment.EditDistance(mtTransSeg, theGoldSegment, null);
				int mtBestMaxWords=java.lang.Math.max(mtTransSeg.size(),theGoldSegment.size());
				// System.err.println("The Gold segment size is:"+ bestEd);
				// System.err.println("The MT segment size is:"+ mtBestEd);
				if(mtBestEd<bestEd){
					bestEd=mtBestEd;
					bestMaxWords=mtBestMaxWords;
				}
				
				//bestEd = theGoldSegment.size();
				//bestMaxWords=theGoldSegment.size();
			}
			*/

			//System.err.println("Patch Size" + patchOps.size());
			if(patchOps.size()>0){
				if(DEBUG){
						System.err.println("Candidate Possible List Size: " + patchOps.size());
						for(PatchOperator op: patchOps){
							if(op.isYesNode()){
								System.err.println("Patch operator type YES");
							}
							else if(op.isNoNode()){
								System.err.println("Patch operator type NO");
							}
							int firstOpPos = op.getOrigPos().getFirst();
							int secOpPos = op.getOrigPos().getSecond();
							StringBuilder tuWords = new StringBuilder();
							for(int opCtr=firstOpPos; opCtr<=secOpPos;opCtr++){
								tuWords.append(bestTUTargetSegment.getWord(opCtr).getValue() + " ");
							}
							StringBuilder stringWrds = new StringBuilder();
							stringWrds.append("Patch :  [" + firstOpPos + ", " + secOpPos+"] ");
							stringWrds.append("\"");
							stringWrds.append(tuWords.toString());
							stringWrds.append("\"");
							stringWrds.append(" --> ");
							stringWrds.append("\"");
							for(Word word : op.getWords().getSentence()){
								stringWrds.append(word.getValue().toString() + " ");
								//wordCtr++;
							}
							stringWrds.append("\"");
							System.err.println(stringWrds);
							System.err.println(".........");
						}
				}					
				// call recrusion, final List will have all of the patches
				// we begin with the first operator in the list					patchManager.bestEditDistance;
				//System.err.println("Size of patchops: " + patchOps.size());
				if(patchOps.size()<MAX_PATCHOPS_SIZE){
					Patch.order(patchOps);
					patchManager.theGoldSegment   = theGoldSegment;
					patchManager.bestEditDistance = theGoldSegment.size();
					//Patch.printOps(patchOps);
					patchManager.addPatch(patchOps, bestTUTargetSegment);
					bestEd 		  = patchManager.bestEditDistance;
					int bestPatchSize = patchManager.bestPatchSize;
                                        bestOpSize = patchManager.bestOpSize;
					bestOpCount++;
                                        bestReplSize = patchManager.bestReplSize;
					bestReplCount++;
					bestMaxWords=java.lang.Math.max(theGoldSegment.size(),bestPatchSize);
				}
			    //System.err.println("Number of PatchOps"+patchOps.size());
				else{
						//System.err.println("Error: Too Many Patch Operators " + patchOps.size());//adding for space on output
						//Patch.printOps(patchOps);
						
						//bestEd=0;
						//bestMaxWords=0;
				}
			}// if patch ops size
			totalError+=bestEd;
			totalWords+=bestMaxWords;
			totalBestOpSize+=bestOpSize;
			totalBestOpCount+=bestOpCount;
			totalBestReplSize+=bestReplSize;
			totalBestReplCount+=bestReplCount;
			System.err.println(bestEd + " " + bestMaxWords);
		 }//if(!DO_TRANS_MAP){
	    }// if testseg <=50
	}// for test segs
	System.err.println("Relative Error is" + (totalError/totalWords));
	System.err.println("Average Best Target Operator Size:" + (totalBestOpSize/totalBestOpCount));
	System.err.println("Average Best Replacement Operator Size:" + (totalBestReplSize/totalBestReplCount));
	System.err.println("Ending app");
    }
    private static void useRestrictivePatchMismatch(List<PatchOperator> patchOps, PatchOperator op){
    	//System.err.println("Inside of usePatchMismatch patch size" + patchOps.size());
    	//feature 4 to remove redundant patches
		// some logic uses last and first so we should order here
		//System.err.println(">>>>>>>>>>>>>>>. Begin of Tau <<<<<<<<<<<<<<<<<<<<");
		// remove redundant patches
		// TAU_PRIME IS THE SOURCE TU - TAU IS THE PATCH
    	//Patch.order(patchOps);
		List<PatchOperator> removePatchOps = new ArrayList<PatchOperator>();
		// for all previous patches
		// see if they fit, since this patchOps is already sorted
		// shouldn't have to check consecutive patches only previous
		int firstOpPos = op.getOrigPos().getFirst();
		int secOpPos = op.getOrigPos().getSecond();
		//StringBuilder opTau = new StringBuilder(); 
		//for(Word word : op.getWords().getSentence()){
		//	opTau.append(word.getValue().toString() + " ");
		//}
		//System.err.println("Current OpTau: " +op.getWords().toString());
		for(PatchOperator prevOp : patchOps){
			// also check if a previous one can consume the current one
			int prevFirstOpPos = prevOp.getOrigPos().getFirst();
			int prevSecOpPos   = prevOp.getOrigPos().getSecond();
			//System.err.println("Previous OpTau: " +prevOp.getWords().toString());
			//StringBuilder prevOpTau = new StringBuilder(); 
			//for(Word word : prevOp.getWords().getSentence()){
			//	prevOpTau.append(word.getValue().toString() + " ");
			//}

				// do we need to remove this one? 
				// if so, add it to the remove list and break
			if(!prevOp.equals(op)){
				if(prevFirstOpPos<=firstOpPos && prevSecOpPos>=secOpPos){
//						System.err.println("Removing op");
//						System.err.println("prevFirstOpPos:" + prevFirstOpPos);
//						System.err.println("prevSecOpPos:" + prevSecOpPos);
//						System.err.println("firstOpPos:" + firstOpPos);
//						System.err.println("secOpPos:" + secOpPos);
//						System.err.println("Previous op words: " + prevOp.getWords().toString());
//						System.err.println("Op words: "+op.getWords().toString());
						removePatchOps.add(op);
						continue;
				}
			
				// do we need to remove a previous one?
				if(firstOpPos<=prevFirstOpPos && secOpPos>=prevSecOpPos){
//						System.err.println("Removing prevOp");
//						System.err.println("prevFirstOpPos:" + prevFirstOpPos);
//						System.err.println("prevSecOpPos:" + prevSecOpPos);
//						System.err.println("firstOpPos:" + firstOpPos);
//						System.err.println("secOpPos:" + secOpPos);
//						System.err.println("Previous op words: " + prevOp.getWords().toString());
//						System.err.println("Op words: "+op.getWords().toString());
						removePatchOps.add(prevOp);
						continue;
				}
			}

		}
		if(removePatchOps.size()>0){
			//System.err.println("removed patch ops");
			patchOps.removeAll(removePatchOps);						
			//Patch.order(patchOps);// order after deleting
			
		}
		//System.err.println(">>>>>>>>>>>>>>>. End of Tau <<<<<<<<<<<<<<<<<<<<");
	}
    private static void usePatchMismatch(List<PatchOperator> patchOps, PatchOperator op){
    	//System.err.println("Inside of usePatchMismatch patch size" + patchOps.size());
    	//feature 4 to remove redundant patches
		// some logic uses last and first so we should order here
		//System.err.println(">>>>>>>>>>>>>>>. Begin of Tau <<<<<<<<<<<<<<<<<<<<");
		// remove redundant patches
		// TAU_PRIME IS THE SOURCE TU - TAU IS THE PATCH
    	//Patch.order(patchOps);
		List<PatchOperator> removePatchOps = new ArrayList<PatchOperator>();
		// for all previous patches
		// see if they fit, since this patchOps is already sorted
		// shouldn't have to check consecutive patches only previous
		int firstOpPos = op.getOrigPos().getFirst();
		int secOpPos = op.getOrigPos().getSecond();
		//StringBuilder opTau = new StringBuilder(); 
		//for(Word word : op.getWords().getSentence()){
		//	opTau.append(word.getValue().toString() + " ");
		//}
		//System.err.println("Current OpTau: " +op.getWords().toString());
		for(PatchOperator prevOp : patchOps){
			// also check if a previous one can consume the current one
			int prevFirstOpPos = prevOp.getOrigPos().getFirst();
			int prevSecOpPos   = prevOp.getOrigPos().getSecond();
			//System.err.println("Previous OpTau: " +prevOp.getWords().toString());
			//StringBuilder prevOpTau = new StringBuilder(); 
			//for(Word word : prevOp.getWords().getSentence()){
			//	prevOpTau.append(word.getValue().toString() + " ");
			//}

				// do we need to remove this one? 
				// if so, add it to the remove list and break
			if(!prevOp.equals(op)){
				if(prevFirstOpPos<=firstOpPos && prevSecOpPos>=secOpPos){
					if(prevOp.getWords().toString().toLowerCase().contains(op.getWords().toString().toLowerCase())){
//						System.err.println("Removing op");
//						System.err.println("prevFirstOpPos:" + prevFirstOpPos);
//						System.err.println("prevSecOpPos:" + prevSecOpPos);
//						System.err.println("firstOpPos:" + firstOpPos);
//						System.err.println("secOpPos:" + secOpPos);
//						System.err.println("Previous op words: " + prevOp.getWords().toString());
//						System.err.println("Op words: "+op.getWords().toString());
						removePatchOps.add(op);
						continue;
					}
				}
			
				// do we need to remove a previous one?
				if(firstOpPos<=prevFirstOpPos && secOpPos>=prevSecOpPos){
					if(op.getWords().toString().toLowerCase().contains(prevOp.getWords().toString().toLowerCase())){
//						System.err.println("Removing prevOp");
//						System.err.println("prevFirstOpPos:" + prevFirstOpPos);
//						System.err.println("prevSecOpPos:" + prevSecOpPos);
//						System.err.println("firstOpPos:" + firstOpPos);
//						System.err.println("secOpPos:" + secOpPos);
//						System.err.println("Previous op words: " + prevOp.getWords().toString());
//						System.err.println("Op words: "+op.getWords().toString());
						removePatchOps.add(prevOp);
						continue;
					}
				}
			}

		}
		if(removePatchOps.size()>0){
			//System.err.println("removed patch ops");
			patchOps.removeAll(removePatchOps);						
			//Patch.order(patchOps);// order after deleting
			
		}
		//System.err.println(">>>>>>>>>>>>>>>. End of Tau <<<<<<<<<<<<<<<<<<<<");
	}
    private static void usePatchMismatchTau1(List<PatchOperator> patchOps, PatchOperator op){   
    	//System.err.println("Inside of usePatchMismatchTau1 patch size" + patchOps.size());
    	// feature 5
    	// remove redundant patches
		// TAU_PRIME IS THE SOURCE TU - TAU IS THE PATCH
		//System.err.println(">>>>>>>>>>>>>>>Begin of Tau 1 <<<<<<<<<<<<<<<<<<<<");
    	//Patch.order(patchOps);
		List<PatchOperator> removePatchOps = new ArrayList<PatchOperator>();
		// for all previous patches
		// see if they fit, since this patchOps is already sorted
		// shouldn't have to check consecutive patches only previous
		int firstOpPos = op.getOrigPos().getFirst();
		int secOpPos = op.getOrigPos().getSecond();
		//StringBuilder opTau = new StringBuilder(); 
		//for(Word word : op.getWords().getSentence()){
		//	opTau.append(word.getValue().toString() + " ");
		//}
		for(PatchOperator prevOp : patchOps){
			// also check if a previous one can consume the current one
			int prevFirstOpPos = prevOp.getOrigPos().getFirst();
			int prevSecOpPos   = prevOp.getOrigPos().getSecond();
			//StringBuilder prevOpTau = new StringBuilder(); 
			//for(Word word : prevOp.getWords().getSentence()){
			//	prevOpTau.append(word.getValue().toString() + " ");
			//}
			if(!prevOp.equals(op)){
				// do we need to remove this one due to size? 
				// if so, add it to the remove list and break
				if(prevFirstOpPos<=firstOpPos && prevSecOpPos>=secOpPos){// this line gives a substring of source taus
					if(!prevOp.getWords().toString().toLowerCase().contains(op.getWords().toString().toLowerCase())){// this line compares tau'
						if(op.getWords().toString().length()<prevOp.getWords().toString().length())
							removePatchOps.add(op);
						continue;
					}
				}
				// do we need to remove a previous one due to size being longer?
				if(firstOpPos<=prevFirstOpPos && secOpPos>=prevSecOpPos){// this line gives a substring of source taus
					if(!op.getWords().toString().toLowerCase().contains(prevOp.getWords().toString().toLowerCase())){// this line compares tau'
						if(prevOp.getWords().toString().length()<op.getWords().toString().length())
							removePatchOps.add(prevOp);
						continue;
					}
				}
			}

		}
		if(removePatchOps.size()>0){
			//System.err.println("removed patch ops");
			patchOps.removeAll(removePatchOps);						
			//Patch.order(patchOps);// order after deleting
			
		}
		//System.err.println(">>>>>>>>>>>>>>>. End of Tau1 <<<<<<<<<<<<<<<<<<<<");
	}
    
    // should be "en" or "es"
    // this one was made to avoid the translation of entire sentences
    // since we need the mt translation of a stentnce that doesn't mattch the fms
    private final static String getTranslationNoMap(final String input, final String langCmd){
	String finalline=new String("");
        try {
		String[] command = {"apertium", "-u", langCmd};
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
	return finalline;
   } 
    private final static String getTranslation(final String input, final String langCmd, final Map<String,String> translations, final boolean DO_TRANS_MAP, final String trans_map){

	String finalline=new String("");
	// pull from the map if already there
	if(translations!=null && translations.containsKey(input)){
		finalline=translations.get(input);
		return finalline;
	}
        try {
		String[] command = {"apertium", "-u", langCmd};
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
		// add the translation to the map and serialize
		translations.put(input,finalline);
		if(DO_TRANS_MAP){
			//System.err.println("writing "+ trans_map);
			try{
				// write the translation map
				FileOutputStream fos = new FileOutputStream(trans_map);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(translations);
				oos.close();
				fos.close();
			}
			catch(Exception e){
			    Writer swriter = new StringWriter();
			    PrintWriter printWriter = new PrintWriter(writer);
			    e.printStackTrace(printWriter);
			    String s = swriter.toString();
			    System.err.println("Error while writing in serialized" +  trans_map);
			    System.err.println(s);
			    System.exit(-1);
			}
		}
		
        } catch (Exception e) {
            	e.printStackTrace();

	}
	return finalline;
   } 
}

