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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import es.ua.dlsi.utils.Pair;
import es.ua.dlsi.segmentation.Word;
import es.ua.dlsi.patching.PatchOperator;
import es.ua.dlsi.patching.Patch;
import es.ua.dlsi.patching.PatchPhraseExtraction;
import es.ua.dlsi.patching.PatchPhrasePair;
import java.util.Locale;
import java.text.Collator;


/**
 *
 * @author john evan ortega jr 
 */
public class TestWERTwoSegments{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	//String testsource = "/home/johneortega/rebecca/wer_test/file1.txt.gz";
	//#String gold = "/home/johneortega/rebecca/wer_test/file2.txt.gz";
	//String testsource = "/home/johneortega/rebecca/wer_test/en_es.mt.2016.apertium.gz";
	//String testsource = "/home/johneortega/patching/input/en_es.mt.r83165.apertium.gz";
	//String testsource = "/home/johneortega/rebecca/wer_test/dgtnmt/mt.txt.gz";
	//String gold = "/home/johneortega/rebecca/wer_test/es.omega.test.gz";
	//String gold = "/home/johneortega/rebecca/wer_test/dgtnmt/gold.txt.gz";
//String testsource = "/home/johneortega/computewer/WER-in-python/Random1_Oracle/test.100.output.postprocessed.gz";
//String testsource = "/home/johneortega/computewer/WER-in-python/Random1_Oracle/test.tok.fmr.gz";
//String testsource = "/home/johneortega/computewer/WER-in-python/Random1_Oracle/test.100.output.postprocessed.gz";
//String testsource = "/home/johneortega/computewer/WER-in-python/Random1/test.tok.fmr.gz";
//String testsource = "/home/johneortega/computewer/WER-in-python/test.tok.mt.gz";
//String testsource = "/home/johneortega/computewer/WER-in-python/Random1_Oracle/test.tok.mt.gz";
//String testsource = "/home/johneortega/computewer/WER-in-python/Random1/test.tok.fmr.gz";
String testsource = "/home/johneortega/for_marco_set2/2018_07_20_WMT2018/1000_test_sentences/nmt.1000.gz";
/*

drwxr-xr-x 2 johneortega johneortega 4.0K Jul 20 20:43 .
drwxr-xr-x 4 johneortega johneortega 4.0K Jul 20 20:21 ..
-rw-r--r-- 1 johneortega johneortega  31K Jul 20 20:20 tm.1000.gz
-rw-r--r-- 1 johneortega johneortega  35K Jul 20 19:44 fmr_ape.rand.1000.gz
-rw-r--r-- 1 johneortega johneortega  34K Jul 20 19:43 fmr_ape.oracle.rand.1000.gz
-rw-r--r-- 1 johneortega johneortega  32K Jul 20 19:31 fmr.oracle.1000.gz
-rw-r--r-- 1 johneortega johneortega  36K Jul 20 19:30 fmr.rand.1000.gz
-rw-r--r-- 1 johneortega johneortega 158K May 19 10:14 ref.1000
-rw-r--r-- 1 johneortega johneortega  34K May 19 10:14 pbmt.1000.gz
-rw-r--r-- 1 johneortega johneortega  32K May 19 10:14 source.1000.gz
-rw-r--r-- 1 johneortega johneortega  33K May 18 10:10 pbmt_ape_only.1000.gz
-rw-r--r-- 1 johneortega johneortega  33K May 18 09:34 fmr_ape.oracle.1000.gz
-rw-r--r-- 1 johneortega johneortega  33K May 18 10:10 pbmt_ape_only.1000.gz
johneortega@aruba:~/for_marco_set2/2018_07_20_WMT2018/1000_test_sentences$ pwd
/home/johneortega/for_marco_set2/2018_07_20_WMT2018/1000_test_sentences
*/


//String gold = "/home/johneortega/computewer/WER-in-python/Random1/test.tok.ref.gz";
String gold = "/home/johneortega/for_marco_set2/2018_07_20_WMT2018/1000_test_sentences/ref.1000.gz";
        List<Segment> stestsegs=new LinkedList<Segment>();
        List<Segment> goldSegs=new LinkedList<Segment>();
        try {
            stestsegs=TranslationMemory.ReadSegmentsFile(testsource);
            goldSegs = TranslationMemory.ReadSegmentsFile(gold);
        } catch (FileNotFoundException ex) {
            System.err.print("Error: Test segments file '");
            System.err.print(testsource);
            System.exit(-1);
        } catch (IOException ex) {
            System.err.print("Error while reading source language test segments from file '");
            System.err.print(testsource);
            System.err.println("'");
            System.exit(-1);
        }
	int ed_total = 0;
        int max_total = 0;
        for(int i=0; i<stestsegs.size(); i++){
		// there's actually various patch situations here
		Segment segment1 = stestsegs.get(i);
		Segment segment2 = goldSegs.get(i);
	        //if(segment1.size()<=100)
			//System.out.println(segment1.toString());
		//	System.out.println(segment2.toString());
	        List<Pair<Integer,Integer>> talignment = new ArrayList<Pair<Integer,Integer>>();
	        double sscore=TranslationMemory.GetScoreNew(segment1, segment2, talignment);
		int ed            = segment2.size();
		int theMax =segment2.size();
	        if(segment1.size()<=100){
			ed            = Segment.EditDistance(segment1, segment2, null);
			System.out.println("the edit distance is:" + ed);
			theMax =java.lang.Math.max(segment1.size(),segment2.size());
			System.out.println("the max is:" + theMax);
		}
		ed_total+=ed;
		max_total+=theMax;
   	 }
	double new_ed_total = (double)ed_total;
	System.out.println("the final score is:" + new_ed_total/max_total);
	
    }
}
