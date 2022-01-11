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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author miquel
 */
public class RunTMGetNumMatchesByFMS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option otestsource = parser.addStringOption('s',"source");
        CmdLineParser.Option otmsource  = parser.addStringOption("tm-source");
        CmdLineParser.Option otmtarget = parser.addStringOption("tm-target");
        CmdLineParser.Option othreshold = parser.addDoubleOption("threshold");
        CmdLineParser.Option ogold   		= parser.addStringOption("gold");// using this as the comparer
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

        String testsource=(String)parser.getOptionValue(otestsource,null);
        String tmsource=(String)parser.getOptionValue(otmsource,null);
        String tmtarget=(String)parser.getOptionValue(otmtarget,null);
        Double threshold=(Double)parser.getOptionValue(othreshold,null);
        String gold		= (String)parser.getOptionValue(ogold,null);

        if(threshold==null){
            System.err.println("Error: It is necessary to set the fuzzy-matching score threshold"
                    + " (use parameter --threshold).");
            System.exit(-1);
        }
        if(testsource==null){
            System.err.println("Error: It is needed to define the path to the source language segments of the test set (use parameter --test-source).");
            System.exit(-1);
        }
        if(tmsource==null){
            System.err.println("Error: It is needed to define the path to the tm source language segments of the test set (use parameter --test-source).");
            System.exit(-1);
        }

	    // of the wall way of doing this test, but it works
	    System.err.println("begin loading tm");
        TranslationMemory trans_memory=new TranslationMemory();
        trans_memory.LoadTM(tmsource, tmtarget);
	    System.err.println("done loading tm");
        List<Segment> stestsegs=new LinkedList<Segment>();
        List<Segment> goldSegs=new LinkedList<Segment>();
        try {
            stestsegs=TranslationMemory.ReadSegmentsFile(testsource);
            goldSegs = TranslationMemory.ReadSegmentsFile(gold);
        } catch (FileNotFoundException ex) {
            System.err.print("Error: Source language test segments file '");
            System.err.print(testsource);
            System.err.println("' could not be found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.print("Error while reading source language test segments from file '");
            System.err.print(testsource);
            System.err.println("'");
            System.exit(-1);
        }
	    int totalSixtySegsMatched = 0;
	    int totalSeventySegsMatched = 0;
	    int totalEightySegsMatched = 0;
	    int totalNinetySegsMatched = 0;
        for(int i=0; i<stestsegs.size(); i++){
            System.err.println("trying source segment number: " + String.valueOf(i));
            boolean sixtyHit = false;
            boolean seventyHit = false;
            boolean eightyHit = false;
            boolean ninetyHit = false;
	        for(TranslationUnit tu: trans_memory.GetTUs()){
                boolean esplaGomis = false;
		        double sscore=TranslationMemory.GetScoreNew(tu.getSource(), stestsegs.get(i), null);
		        double tscore=TranslationMemory.GetScoreNew(tu.getTarget(), goldSegs.get(i), null)*100;
                // esplagomis comment later
		        if(tscore >= (sscore*100) - 5)
                    esplaGomis = true;
                // esplagomis comment later
                //if(esplaGomis){
                    if(sscore>=0.60 && sscore<1 && !sixtyHit){
                        System.err.println("hit sixty for source segment number: " + String.valueOf(i));
                        sixtyHit=true;
                        totalSixtySegsMatched++;
                    }
                    if(sscore>=0.70 && sscore<1 && !seventyHit){
                        System.err.println("hit seventy for source segment number: " + String.valueOf(i));
                        seventyHit=true;
                        totalSeventySegsMatched++;
                    }
                    if(sscore>=0.80 && sscore<1 && !eightyHit){
                        System.err.println("hit eighty for source segment number: " + String.valueOf(i));
                        eightyHit=true;
                        totalEightySegsMatched++;
                    }
                    if(sscore>=0.90 && sscore<1 && !ninetyHit){
                        System.err.println("hit ninety for source segment number: " + String.valueOf(i));
                        ninetyHit=true;
                        totalNinetySegsMatched++;
                    }
                //}
            }
        }
        System.err.println("total 60 segs matched: "+totalSixtySegsMatched);
        System.err.println("total 70 segs matched: "+totalSeventySegsMatched);
        System.err.println("total 80 segs matched: "+totalEightySegsMatched);
        System.err.println("total 90 segs matched: "+totalNinetySegsMatched);
    }
}
