/*
 * Copyright (C) 2011 Universitat d'Alacant
 *
 * author: Miquel Esplà Gomis
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
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
//-a f --tm-path /home/miquel/Projects/WordKeepingRecommend/provaEditDistance/TMtest.obj --threshold 0,5 -m 0 -o /home/miquel/Projects/WordKeepingRecommend/provaEditDistance/TMtest.feat
//-a g --tm-path /home/miquel/Projects/WordKeepingRecommend/provaEditDistance/TMtest.obj -m 0 --tm-source /home/miquel/Projects/WordKeepingRecommend/provaEditDistance/TMtest.en --tm-target /home/miquel/Projects/WordKeepingRecommend/provaEditDistance/TMtest.es
//-w /home/miquel/TMtest.bands.weights --test-source /home/miquel/TMtrans.en --test-target /home/miquel/TMtrans.es -t 0,5 --tm-path /home/miquel/TMtest.obj -o /home/miquel/result.test
package es.ua.dlsi.translationmemory;

import es.ua.dlsi.classifiers.ClassifierWeightsSet;
import es.ua.dlsi.features.DumbBaselineFMS;
import es.ua.dlsi.features.FeaturesComputer;
import es.ua.dlsi.features.GeometricStyleMNFeatures;
import es.ua.dlsi.features.GeometricStyleMNFeaturesNotNormalised;
import es.ua.dlsi.features.GeometricStyleMNFeaturesNotNormalisedAndSourceTarget;
import es.ua.dlsi.features.GeometricStyleMNFeaturesOnlyPos;
import es.ua.dlsi.features.GeometricStyleMNFeaturesOnlyPosWithQuantitativeInfo;
import es.ua.dlsi.features.GeometricStyleMNFeaturesOnlyPosWithQuantitativeInfoNormalised;
import es.ua.dlsi.features.MNFeatures;
import es.ua.dlsi.features.MNFeaturesNotNormalised;
import es.ua.dlsi.features.SourceTagetFeatures;
import es.ua.dlsi.segmentation.Evidence;
import es.ua.dlsi.segmentation.Segment;
import es.ua.dlsi.utils.CmdLineParser;
import es.ua.dlsi.utils.MyInteger;
import es.ua.dlsi.utils.Pair;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * This class represents a translation memory which incorporates a special
 * annotation to record information about the parallelism of sub-segments. This
 * TM is used to perform experiments on word keeping recommendation in which a
 * computer aided translation system uses the translation memory to provide
 * translation units to the user as a base for new translation tasks.
 * @author Miquel Esplà Gomis
 */
public class TranslationMemory implements Serializable {

    /**
     * Class created to implement a comparator to order the translation units
     * from shorter to longer.
     */
    class TusComparator implements Comparator{
        /**
         *
         * @param o1 First translation unit to be compared
         * @param o2 Second translation unit to be compared
         * @return Returns 1 if {@link #o1} is longer than {@link #o2}, -1 if {@link #o2} is longer than {@link #o1} and 0 if they are equal
         */
        public int compare(Object o1, Object o2){
            int l1=((TranslationUnit)o1).getSource().size();
            int l2=((TranslationUnit)o2).getSource().size();

            if(l1 > l2) {
                return 1;
            }
            else if(l1 < l2) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }

    /**
     * List of translation units in the translation memory
     */
    List<TranslationUnit> tus;

    /**
     * Default constructor
     */
    public TranslationMemory(){
        tus=new LinkedList<TranslationUnit>();
    }

    /**
     * This method sorts the TUs in the TM by the source-segment length. In this
     * way, if a comparison of fuzzy-matching score is performed on the whole TM,
     * it can be limited only to those TU with the adequate (not too long, not
     * too short) length.
     */
    public void Sort(){
        Collections.sort(tus, new TusComparator());
    }

    /**
     * Method that returns the list of translation units
     * @return Returns {@link #tus}
     */
    public List<TranslationUnit> GetTUs(){
        return tus;
    }


    /**
     * Method that adds a translation unit to the list from two <code>String</code> objects
     * @param source Source segment
     * @param target Target segment
     * @return Returns <code>true</code> if the translation unit has been added to the translation memory and <code>false</code> otherwise (if it already exists in the translation memory).
     */
    public boolean AddTU(String source, String target){
        TranslationUnit tu=new TranslationUnit(source, target);
        if(!tus.contains(tu)){
            tus.add(tu);
            return true;
        }
        else{
            //System.err.println("Duplicated TU: <"+source+"> <"+target+">");
            return false;
        }
    }

    public void LoadTMFromObject(String path){
        List<TranslationUnit> trans_memory;
        try {
            //Loading the TM
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path)));
            trans_memory = (List<TranslationUnit>) input.readObject();
            input.close();
            this.tus=trans_memory;
            for(TranslationUnit tu: this.tus){
                tu.source.RefreshWordCodes();
                tu.target.RefreshWordCodes();
            }
        } catch (IOException ex) {
            System.err.println("Error while trying to read source TM file. The path may be incorrect or the object could be corrupted.");
        }catch (ClassNotFoundException ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
    }

    /**
     * Method that loads the TM from a pair of aligned files containing the
     * sentences S and the sentences T. The format of the files must correspond
     * to a list of sentences (one per line) which places each parallel sentence
     * in the same position that in the other document.
     * @param sources_file File from which the source sentences will be read.
     * @param targets_file File from which the target sentences will be read.
     * @return Returns <code>true</code> if the load is performed correctly and <code>false</code> otherwise.
     */
    public boolean LoadTM(String sources_file, String targets_file){

        BufferedReader sourcereader;
        BufferedReader targetreader;
        String sourceline;
        String targetline=null;

        try {
            //sourcereader = new BufferedReader(new FileReader(sources_file));
            sourcereader = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream(sources_file)), "UTF-8"));
            //targetreader = new BufferedReader(new FileReader(targets_file));
            targetreader = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream(targets_file)), "UTF-8"));
            while((sourceline=sourcereader.readLine())!=null && (targetline=targetreader.readLine())!=null){
                AddTU(sourceline, targetline);
            }
            //System.out.println("TUs read: "+tus.size());
            sourcereader.close();
            targetreader.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.err.println("Error while trying to read or close source train sentences file.");
            return false;
        }
        return true;
    }
    public boolean LoadTMForFMS(String sources_file, String targets_file, double threshold){

        BufferedReader sourcereader;
        BufferedReader targetreader;
        String sourceline;
        String targetline=null;

        try {
            //sourcereader = new BufferedReader(new FileReader(sources_file));
            sourcereader = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream(sources_file)), "UTF-8"));
            //targetreader = new BufferedReader(new FileReader(targets_file));
	    System.out.println("Reading lines");
	    int totallines = 0;
	    int srcLineCnt = 0;
            while((sourceline=sourcereader.readLine())!=null){
		++srcLineCnt;
            	targetreader = new BufferedReader(new InputStreamReader(new GZIPInputStream(
		new FileInputStream(targets_file)), "UTF-8"));
		int targetlineCnt=0;
		while((targetline=targetreader.readLine())!=null){
			++targetlineCnt;
			//AddTU(sourceline, targetline);
			//System.out.println("Trying: ");
			//System.out.println(sourceline);
			//System.out.println(targetline);
			double sscore=GetScore(new Segment(sourceline), new Segment(targetline), threshold, null);
			//System.out.println("threshold: " +threshold);
			
			if(sscore>=threshold && sscore<1){
				System.out.println("Score: " +sscore);
				System.out.println("Source Line Count: " +srcLineCnt);
				System.out.println("Source Line: " + sourceline);
				System.out.println("Target Line Count: " +targetlineCnt);
				System.out.println("Target Line: " +targetline);
				System.out.println("Target file: " +targets_file);
				totallines++;
			}
		}
		//System.out.println("Done with line" + srcLineCnt);
            	targetreader.close();
		targetreader=null;
            }
            //System.out.println("TUs read: "+tus.size());
            System.out.println("Total lines matched: "+totallines);
            sourcereader.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.err.println("Error while trying to read or close source train sentences file.");
            return false;
        }
        return true;
    }
    // load source language tm and print out matching target language
    // assumes all files are already lc
    public boolean LoadTMForMatching(String source_file, String source_tm_file, String target_tm_file){

        BufferedReader sourceReader;
        BufferedReader sourceTMReader;
        BufferedReader targetTMReader;
        String sourceFileLine=null;
        String sourceTMFileLine=null;
        String targetTMFileLine=null;

        try {
            sourceReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream(source_file)), "UTF-8"));
	    System.out.println("Reading lines");
            while((sourceFileLine=sourceReader.readLine())!=null ){
            	sourceTMReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(
		new FileInputStream(source_tm_file)), "UTF-8"));
            	targetTMReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(
		new FileInputStream(target_tm_file)), "UTF-8"));
		// source tm line should match
            	while((sourceTMFileLine=sourceTMReader.readLine())!=null && (targetTMFileLine=targetTMReader.readLine())!=null ){
			//if(sourceTMFileLine.equals(sourceFileLine)){
			if(!sourceTMFileLine.equals(sourceFileLine)){
				System.out.println(sourceTMFileLine);
				//System.out.println(targetTMFileLine);
				//break;
			}
		}
            	sourceTMReader.close();
            	targetTMReader.close();
		targetTMReader=null;
		sourceTMReader=null;
            }
            sourceReader.close();
	    sourceReader=null;
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.err.println("Error while trying to read or close source train sentences file.");
            return false;
        }
        return true;
    }

    /**
     * Method that loads the TM from a pair of aligned files containing the
     * sentences S and the sentences T. The format of the files must correspond
     * to a list of sentences (one per line) which places each parallel sentence
     * in the same position that in the other document.
     * @param sources_file File from which the source sentences will be read.
     * @param targets_file File from which the target sentences will be read.
     * @return Returns <code>true</code> if the load is performed correctly and <code>false</code> otherwise.
     */
    public boolean LoadTM(String sources_file, String targets_file, String source_segments, String target_segments, boolean debug){

        BufferedReader sourcereader;
        BufferedReader targetreader;
        String sourceline;
        String targetline=null;
        try {
            //sourcereader = new BufferedReader(new FileReader(sources_file));
            sourcereader = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream(sources_file)), "UTF-8"));
            //targetreader = new BufferedReader(new FileReader(targets_file));
            targetreader = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream(targets_file)), "UTF-8"));
            int numTus=0;
            while((sourceline=sourcereader.readLine())!=null && (targetline=targetreader.readLine())!=null){
                ++numTus;
                System.err.println("Adding Tu #" + numTus);
                AddTU(sourceline, targetline);

            }
            //System.out.println("TUs read: "+tus.size());
            sourcereader.close();
            targetreader.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.err.println("Error while trying to read or close source train sentences file.");
            return false;
        }
        return true;
    }

    /**
     * Method that computes the fuzzy-matching score for a pair of sentences. It
     * firstly computes a preliminar score based only in the length of the sentences.
     * If the ration of their length is higher than the set threshold, it is returned
     * and the edit distance is not computed. Otherwise, the method computes the edit
     * distance between the segments on the length of the longest one.
     * @param s1 First segment to compare.
     * @param s2 Second segment to compare.
     * @return Returns the fuzzy-matching score for a pair of segments.
     */
    static public double GetScore(Segment s1, Segment s2, double threshold, boolean[] alignment){
        if(s1.size()==0 || s2.size()==0) {
            return 0;
        }
        else{
            int max_len=Math.max(s1.size(),s2.size());
            //double preliminar_result=((double)1)-(Math.abs(((double)s1.size()-s2.size())/(double)max_len));
            List<Integer> codes1, codes2;
            codes1=s1.getSentenceCodes();
            codes2=s2.getSentenceCodes();
            List<Integer> tmpcodes;
            tmpcodes=new LinkedList<Integer>(codes1);
            tmpcodes.removeAll(codes2);
            double preliminar_result=((double)1)-(Math.abs(((double)tmpcodes.size())/(double)max_len));
            if(preliminar_result<threshold){
                return -1;
            }
            else{
                double editdistance;
                if(alignment==null) {
                    editdistance=((double)1)-((double)Segment.EditDistance(codes1, codes2, null, null, false)/(double)max_len);
                }
                else {
                    editdistance=((double)1)-((double)Segment.EditDistance(codes1, codes2, alignment, null, false)/(double)max_len);
                }
                return editdistance;
            }
        }
    }
    static public double GetScoreNew(Segment s1, Segment s2, List<Pair<Integer,Integer>> alignment){
        if(s1.size()==0 || s2.size()==0) {
            return 0;
        }
        else{
            	int max_len=Math.max(s1.size(),s2.size());
		double editdistance;
		editdistance=((double)1)-((double)Segment.EditDistance(s1, s2, alignment)/(double)max_len);
		return editdistance;
        }
    }





    /**
     * Method that applais a leave-one-out algoritmh on the TM to compare each TU with all the
     * rest. In each iteration, it extracts a TU from the TM and obtains all the matching
     * TUs for it, based on the fuzzy-matching score of their source-segment. Then, it computes
     * the features for each word in the target-segment of those target-segments.
     * @param max_segmentlen Maximum segment length used to obtain the features.
     * @param foutput File where the result will be written.
     * @param minfeatfile Minimum possible feature (if any feature have value = 0, it will be substituted by this minimum value).
     */
    public void GetFeaturesLeaveOneOut(int max_segmentlen, File foutput, File
            minfeatfile, double threshold, boolean debug, boolean provide_fms_editdist,
            boolean discad_words_without_evidence, int features_type){
        double min_feature=Double.MAX_VALUE;
        PrintWriter pw;

        try{
            //pw=new PrintWriter(foutput);
            pw=new PrintWriter(new GZIPOutputStream(new FileOutputStream(foutput)));
            for(int testtuindex=0;testtuindex<this.tus.size();testtuindex++){
                System.out.print(testtuindex);
                TranslationUnit testtu=this.tus.get(testtuindex);
                int startingindex=0;
                while(startingindex<testtuindex && tus.get(startingindex).source.size()<
                        (double)testtu.getSource().size()*threshold) {
                    startingindex++;
                }
                int finalindex=tus.size()-1;
                while(finalindex>=testtuindex && tus.get(finalindex).source.size()>
                        (double)testtu.getSource().size()/threshold) {
                    finalindex--;
                }
                //From the fist TU to the current TU
                for(int currenttuindex=startingindex;currenttuindex<testtuindex;currenttuindex++){
                    TranslationUnit currenttu=this.tus.get(currenttuindex);
                    boolean[] aligned=new boolean[currenttu.getSource().size()];
                    if(currenttu.getTarget().toString().equals("plan de reestructuración") && testtu.getTarget().toString().equals("plan de regionalizacion")) {
                        System.out.println(currenttu.getTarget().toString());
                    }
                    if(testtu.getTarget().toString().equals("plan de reestructuración") && currenttu.getTarget().toString().equals("plan de regionalizacion")) {
                        System.out.println(currenttu.getTarget().toString());
                    }
                    double score=GetScore(testtu.getSource(),currenttu.getSource(),threshold,aligned);
                    if(score>=threshold){
                        if(debug){
                            System.out.println("MATCHING SENTENCES:");
                            System.out.println("\ts_i "+currenttu.getSource());
                            System.out.println("\ts' "+testtu.getSource());
                            System.out.println("\tt_i "+currenttu.getTarget());
                            System.out.println("\tt' "+testtu.getTarget());
                            System.out.print("\tMatching v_ij in s_i: ");
                            for(int i=0;i<currenttu.getSource().size();i++){
                                if(aligned[i]) {
                                    System.out.print("("+i+"-"+currenttu.getSource().getWord(i).getValue()+")");
                                }
                            }
                            System.out.println();
                        }
                        List<Pair<String,double[]>> features=null;
                        FeaturesComputer feat;
                        //We compute the features vector
                        switch(features_type){
                            case 0:
                                feat=new SourceTagetFeatures(testtu.getSource(),
                                        currenttu, max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 1:
                                feat=new MNFeatures(testtu.getSource(), currenttu,
                                        max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 2:
                                feat=new MNFeaturesNotNormalised(testtu.getSource(),
                                        currenttu, max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 3:
                                feat=new GeometricStyleMNFeatures(testtu.getSource(),
                                        currenttu, max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 4:
                                feat=new GeometricStyleMNFeaturesNotNormalised(
                                        testtu.getSource(), currenttu, 
                                        max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 5:
                                feat=new GeometricStyleMNFeaturesOnlyPos(
                                        testtu.getSource(), currenttu, 
                                        max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 6:
                                feat=new GeometricStyleMNFeaturesNotNormalisedAndSourceTarget(
                                        testtu.getSource(), currenttu, 
                                        max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                                
                            break;
                            case 7:
                                feat=new GeometricStyleMNFeaturesOnlyPosWithQuantitativeInfo(
                                        testtu.getSource(), currenttu, 
                                        max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 8:
                                feat=new GeometricStyleMNFeaturesOnlyPosWithQuantitativeInfoNormalised(
                                        testtu.getSource(), currenttu, 
                                        max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 9:
                                feat=new DumbBaselineFMS(testtu.getSource(), currenttu, 
                                        max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                        }
                        //List<Pair<String,double[]>> features=FeaturesComputer.CalculateAllFeatures(
                        boolean[] talignment=new boolean[currenttu.getTarget().size()];
                        double teditdist=Segment.EditDistance(testtu.getTarget().getSentenceCodes(),
                                currenttu.getTarget().getSentenceCodes(),talignment, debug);
                        if(debug){
                            System.out.print("\tMatching w_ij in t_i: ");
                            for(int i=0;i<currenttu.getTarget().size();i++){
                                if(talignment[i]) {
                                    System.out.print("("+i+"-"+currenttu.getTarget().getWord(i).getValue()+")");
                                }
                            }
                            System.out.println();
                        }
                        for(int wpos=0;wpos<features.size();wpos++){
                            Pair<String,double[]> s = features.get(wpos);
                            double[] feature_values=s.getSecond();
                            if(feature_values!=null){
                                if(debug) {
                                    System.out.print("\t\t");
                                }
                                if(provide_fms_editdist){
                                    pw.print((1-score)*Math.max(testtu.getSource().size(),
                                            currenttu.getSource().size()));
                                    pw.print(" ");
                                    pw.print(teditdist);
                                    pw.print(" ");
                                    pw.print(score);
                                    pw.print(" ");
                                    pw.print(1-(teditdist/Math.max(testtu.getTarget().size(),
                                            currenttu.getTarget().size())));
                                    pw.print(" ");
                                }
                                for(int minc=0;minc<feature_values.length;minc++){
                                    if(feature_values[minc]<min_feature && feature_values[minc]>0) {
                                        min_feature=feature_values[minc];
                                    }
                                    pw.print(feature_values[minc]+",");
                                    if(debug){
                                        System.out.print(feature_values[minc]+",");
                                    }
                                }
                                if(talignment[wpos]){
                                    pw.print("0.95");
                                    if(debug){
                                        System.out.print("1");
                                    }
                                }
                                else {
                                    pw.print("0.05");
                                    if(debug) {
                                        System.out.print("0");
                                    }
                                }
                                if(debug) {
                                    System.out.print("        "+s.getFirst());
                                }
                                pw.println();
                                if(debug){
                                    System.out.println();
                                }
                            }
                        }
                        if(debug){
                            for(Evidence e: testtu.evidences){
                                System.out.println(e.toString());
                            }
                        }
                    }
                    else{
                        if(debug){
                            System.out.println("NOT MATCHING SENTENCES: ("+score+")");
                            System.out.println("\ts_i "+currenttu.getSource());
                            System.out.println("\ts' "+testtu.getSource());
                            System.out.print("\tMatching v_ij in s_i: ");
                            for(int i=0;i<currenttu.getSource().size();i++){
                                if(aligned[i]) {
                                    System.out.print("("+currenttu.getSource().getWord(i)+")");
                                }
                            }
                            System.out.println();
                        }
                    }
                }
                //From the current TU to the last TU
                for(int currenttuindex=testtuindex+1;currenttuindex<finalindex;currenttuindex++){
                    TranslationUnit currenttu=this.tus.get(currenttuindex);
                    boolean[] aligned=new boolean[currenttu.getSource().size()];
                    double score=GetScore(testtu.getSource(),currenttu.getSource(),threshold,aligned);
                    if(score>=threshold){
                        if(debug){
                            System.out.println("MATCHING SENTENCES:");
                            System.out.println("\ts_i "+currenttu.getSource());
                            System.out.println("\ts' "+testtu.getSource());
                            System.out.println("\tt_i "+currenttu.getTarget());
                            System.out.println("\tt' "+testtu.getTarget());
                            System.out.print("\tMatching v_ij in s_i: ");
                            for(int i=0;i<currenttu.getSource().size();i++){
                                if(aligned[i]) {
                                    System.out.print("("+i+"-"+currenttu.getSource().getWord(i).getValue()+")");
                                }
                            }
                            System.out.println();
                        }
                        //We compute the features vector
                        //List<Pair<String,double[]>> features=FeaturesComputer.CalculateAllFeatures(
                        List<Pair<String,double[]>> features=null;
                        FeaturesComputer feat;
                        //We compute the features vector
                        switch(features_type){
                            case 0:
                                feat=new SourceTagetFeatures(testtu.getSource(),
                                        currenttu, max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 1:
                                feat=new MNFeatures(testtu.getSource(), currenttu,
                                        max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 2:
                                feat=new MNFeaturesNotNormalised(testtu.getSource(),
                                        currenttu, max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 3:
                                feat=new GeometricStyleMNFeatures(testtu.getSource(),
                                        currenttu, max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 4:
                                feat=new GeometricStyleMNFeaturesNotNormalised(testtu.getSource(),
                                        currenttu, max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 5:
                                feat=new GeometricStyleMNFeaturesOnlyPos(testtu.getSource(),
                                        currenttu, max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 6:
                                feat=new GeometricStyleMNFeaturesNotNormalisedAndSourceTarget(
                                        testtu.getSource(), currenttu, 
                                        max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                                
                            break;
                            case 7:
                                feat=new GeometricStyleMNFeaturesOnlyPosWithQuantitativeInfo(
                                        testtu.getSource(), currenttu, max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 8:
                                feat=new GeometricStyleMNFeaturesOnlyPosWithQuantitativeInfoNormalised(
                                        testtu.getSource(), currenttu, max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                            case 9:
                                feat=new DumbBaselineFMS(testtu.getSource(), currenttu, 
                                        max_segmentlen, score);
                                features=feat.Compute(null, discad_words_without_evidence);
                            break;
                        }
                        boolean[] talignment=new boolean[currenttu.getTarget().size()];
                        double teditdist=Segment.EditDistance(testtu.getTarget().getSentenceCodes(),
                                currenttu.getTarget().getSentenceCodes(),talignment, debug);
                        if(debug){
                            System.out.print("\tMatching w_ij in t_i: ");
                            for(int i=0;i<talignment.length;i++){
                                if(talignment[i]) {
                                    System.out.print("("+i+"-"+currenttu.getTarget().getWord(i).getValue()+")");
                                }
                            }
                            System.out.println();
                        }
                        for(int wpos=0;wpos<features.size();wpos++){
                            Pair<String,double[]> s = features.get(wpos);
                            double[] feature_values=s.getSecond();
                            if(feature_values!=null){
                                if(debug) {
                                    System.out.print("\t\t");
                                }
                                if(provide_fms_editdist){
                                    pw.print((1-score)*Math.max(testtu.getSource().size(),
                                            currenttu.getSource().size()));
                                    pw.print(" ");
                                    pw.print(teditdist);
                                    pw.print(" ");
                                    pw.print(score);
                                    pw.print(" ");
                                    pw.print(1-(teditdist/Math.max(testtu.getTarget().size(),
                                            currenttu.getTarget().size())));
                                    pw.print(" ");
                                }
                                for(int minc=0;minc<feature_values.length;minc++){
                                    if(feature_values[minc]<min_feature && feature_values[minc]>0) {
                                        min_feature=feature_values[minc];
                                    }
                                    pw.print(feature_values[minc]+",");
                                    if(debug) {
                                        System.out.print(feature_values[minc]+",");
                                    }
                                }
                                if(talignment[wpos]){
                                    pw.print("0.95");
                                    if(debug) {
                                        System.out.print("1");
                                    }
                                }
                                else {
                                    pw.print("0.05");
                                    if(debug) {
                                        System.out.print("0");
                                    }
                                }
                                if(debug) {
                                    System.out.print("        "+s.getFirst());
                                }
                                pw.println();
                                if(debug) {
                                    System.out.println();
                                }
                            }
                        }
                    }
                }
                System.out.print("\r");
            }
            System.out.println();
            System.err.println("Min. value: "+min_feature);
            pw.close();
        }
        catch(FileNotFoundException ex){
            System.err.println("Error: Output file "+foutput.getName()+" not found.");
        }
        catch(IOException ex){
            System.err.println("Error while reading file "+foutput.getName()+".");
        }
        if(minfeatfile!=null){
            try{
                pw=new PrintWriter(minfeatfile);
                pw.print(min_feature);
                pw.close();
            }
            catch(FileNotFoundException ex){
                System.err.println("Error: Minimum weight file "+minfeatfile.getName()+" not found.");
            }
        }
    }

    /**
     * Method that generates a list of the evidences found in the TUs of the TM.
     * It first caluclates all the possible segments from the source sentences in
     * the TU and looks for them in the segment dictionary. If the segments appear
     * in the dictionary it looks for the translation segments in the target sentence
     * and, if it found any coincidence, the evidence (the pair of segments) is added
     * to the list of evidences. Then the process is repeated segmenting the target sentence
     * and looking for the segments in de dictionary and repeating the process.
     * @param sdic Segment dictionary.
     * @return Returns the number of evidences added to the list.
     */
    public int GenerateEvidences(SegmentDictionary sdic, int segmaxlen, boolean debug){
        int nevidences=0;
        //We check all the TUs in the TM
        for(TranslationUnit tu : tus){
            if(debug) {
                System.out.println("TU:\n\tSource segment: "+tu.getSource()+"\n\tTarget segment: "+tu.getTarget());
            }
            nevidences+=tu.CollectHTMLEvidences(sdic, segmaxlen, debug);
        }
        return nevidences;
    }

    /**
     * Method that generates all the possible sub-segments between 0 and the value
     * of <code>max_len</code> and prints them into an output file. Each segment
     * will be finished with an empty character and a line-break.
     * @param sfile File from which segments are to be read.
     * @param tfile Output file.
     * @param max_len Maximum length of the sub-segments.
     */
    public static void GenerateSegmentsFile(String sfile, String tfile, int max_len, boolean html){
        Set<Segment> segments;
        //FileReader inFile;
        FileWriter outFile;
        BufferedReader in=null;
        PrintWriter out;
        System.out.println(sfile);
        try {
            //File from which sentences will be read
            //inFile=new FileReader(sfile);
            //in=new BufferedReader(inFile);
            in = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream(sfile)), "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.err.println("Error while trying to read the segments file");
            System.exit(-1);
        }
        try{
            //File in which the generated segments will be written
            File of=new File(tfile);
            if(of.exists()){
                System.err.println("Warning: File ");
                System.err.println(tfile);
                System.err.println(" already exists. New sub-segments will be"
                        + "added at the end of the file.");
            }
            outFile = new FileWriter(tfile);
            out = new PrintWriter(outFile);

            //Generating a line with each segment in a text file
            String sline;
            while((sline = in.readLine())!=null){
                Segment segment=new Segment(sline);
                segments=segment.AllSegmentsInSentence(max_len);
                for(Segment seg: segments){
                    if(html) {
                        out.print("<p>"+seg.toString()+"</p>");
                    }
                    else {
                        out.print(seg.toString()+" .");
                    }
                    out.println();
                    out.println();
                }
            }
            in.close();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.err.println("Error while trying to create sub-segments file");
            System.exit(-1);
        }
    }

    /**
     * Method that generates all the possible sub-segments between 0 and the value
     * of <code>max_len</code> and prints them into an output file. Each segment
     * will be finished with an empty character and a line-break.
     * @param sfile File from which segments are to be read.
     * @param tfile Output file.
     */
    public void WriteSegmentsFile(String sfile, String tfile){
        FileWriter outSFile;
        FileWriter outTFile;
        PrintWriter outS;
        PrintWriter outT;
        try{
            File otf=new File(tfile);
            if(otf.exists()){
                System.err.println("Warning: File ");
                System.err.println(tfile);
                System.err.println(" already exists. New sub-segments will be"
                        + "added at the end of the file.");
            }
            outSFile = new FileWriter(sfile);
            outTFile = new FileWriter(tfile);
            outS = new PrintWriter(outSFile);
            outT = new PrintWriter(outTFile);
            for(TranslationUnit tu: tus){
	    	outS.println(tu.getSource().toString());
	    	outT.println(tu.getTarget().toString());
            }
            outSFile.close();
            outTFile.close();
            outS.close();
            outT.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.err.println("Error while trying to create sub-segments file");
            System.exit(-1);
        }
    }
    static public List<Segment> ReadSegmentsFile(String testsegsfile) throws
            FileNotFoundException,IOException {
        List<Segment> testsegs=new LinkedList<Segment>();
        //FileReader fr = new FileReader(testsegsfile);
        //BufferedReader br=new BufferedReader(fr);
        BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                new FileInputStream(testsegsfile)), "UTF-8"));
        String line;
        while ((line = br.readLine()) != null) {
            Segment s=new Segment(line);
            testsegs.add(s);
        }
        return testsegs;
    }

    static public ClassifierWeightsSet ReadWeightsFile(String weightsfile)
            throws FileNotFoundException,IOException {
        ClassifierWeightsSet classifierweightsset=new ClassifierWeightsSet();
        FileReader fr = new FileReader(weightsfile);
        BufferedReader br=new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineweights=line.split(":");
            String[] charweights=lineweights[1].split(",");
            double[] weights=new double[charweights.length-1];
            double bias=Double.parseDouble(charweights[0]);
            for(int i=1;i<charweights.length;i++){
                weights[i-1]=Double.parseDouble(charweights[i]);
            }
            //ClassifierWeights weightsset=new ClassifierWeights(weights, bias);
            classifierweightsset.AddWeights(
                    Double.parseDouble(lineweights[0]), weights, bias);
        }
        return classifierweightsset;
    }

    /**
     * Method that process a list of features to compute the logarithm of each of
     * them and write them into a file.
     * @param features List of features to be processed
     * @param minval Minimum value (instead of zero)
     * @param foutput Output file
     */
    public static void LogFeatures(List<double[]> features, double minval, File foutput){
        PrintWriter pw;
        double min=Math.log(minval/10);

        try{
            //pw=new PrintWriter(foutput);
            pw=new PrintWriter(new GZIPOutputStream(new FileOutputStream(foutput)));
            for(double[] f: features){
                int i;
                for(i=0;i<f.length-1;i++){
                    double val=f[i];
                    if(val==0) {
                        pw.print(min+",");
                    }
                    else {
                        pw.print(Math.log(val)+",");
                    }
                }
                pw.println((int)f[i]);
            }
            pw.close();
        }
        catch(FileNotFoundException ex){
            System.err.println("Error: Output file "+foutput.getName()+" not found.");
        }
        catch(IOException ex){
            System.err.println("Error while reading file "+foutput.getName()+".");
        }
    }

    public static void main(String[] args) {
        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option otmsource = parser.addStringOption("tm-source");
        CmdLineParser.Option otmtarget = parser.addStringOption("tm-target");
        CmdLineParser.Option otestsource = parser.addStringOption("test-source");
        CmdLineParser.Option otesttarget = parser.addStringOption("test-target");
        CmdLineParser.Option ooutput = parser.addStringOption('o',"output");
        CmdLineParser.Option osegsource = parser.addStringOption("seg-source");
        CmdLineParser.Option osegtarget = parser.addStringOption("seg-target");
        CmdLineParser.Option othreshold = parser.addDoubleOption('t',"threshold");
        CmdLineParser.Option omaxseglen = parser.addIntegerOption('m',"max-segment-len");
        CmdLineParser.Option oaction = parser.addStringOption('a',"action");
        CmdLineParser.Option otmpath = parser.addStringOption("tm-path");
        CmdLineParser.Option odebug = parser.addBooleanOption('d',"debug");
        CmdLineParser.Option ohtmlsubsegs = parser.addBooleanOption("html");
        CmdLineParser.Option odiscardwordsnoevidence = parser.addBooleanOption("discard-words-noevidence");
        CmdLineParser.Option ominfeatpath = parser.addStringOption("min-feature-path");
        CmdLineParser.Option ofeattypestring = parser.addStringOption("feat-type");
        CmdLineParser.Option oprovidefmseditdistinfo = parser.addBooleanOption("provide-fms-editdistance");
        
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

        String tmsource=(String)parser.getOptionValue(otmsource,null);
        String tmtarget=(String)parser.getOptionValue(otmtarget,null);
        String testsource=(String)parser.getOptionValue(otestsource,null);
        String testtarget=(String)parser.getOptionValue(otesttarget,null);
        String segsource=(String)parser.getOptionValue(osegsource,null);
        String segtarget=(String)parser.getOptionValue(osegtarget,null);
        Double threshold=(Double)parser.getOptionValue(othreshold,null);
        int maxseglen=(Integer)parser.getOptionValue(omaxseglen,-1);
        String action=(String)parser.getOptionValue(oaction,null);
        String output=(String)parser.getOptionValue(ooutput,null);
        String tmpath=(String)parser.getOptionValue(otmpath,null);
        String minfeatpath=(String)parser.getOptionValue(ominfeatpath,null);
        String feattypestring=(String)parser.getOptionValue(ofeattypestring,null);
        Boolean debug=(Boolean)parser.getOptionValue(odebug,false);
        Boolean htmlsubsegs=(Boolean)parser.getOptionValue(ohtmlsubsegs,false);
        Boolean discardwordsnoevidence=(Boolean)parser.getOptionValue(odiscardwordsnoevidence,false);
        boolean providefmseditdistinfo=(Boolean)parser.getOptionValue(oprovidefmseditdistinfo,false);
        
        
        if(action==null || action.length()!=1){
            System.err.println("Error: option '-a' must be defined using one of the following options:");
            System.err.println("\ts: generates segment files");
            System.err.println("\tg: generates the translation memory");
            System.err.println("\tf: generates a set of training features");
            System.exit(-1);
        }

        switch(action.charAt(0)){
            //Obtain all sub-segments until a maximum lenght
            case 's':
                if(maxseglen<0){
                    System.err.println("Error: It is necessary to set a maximum lenght higher than 0 (use parameter --max-segment-len).");
                    System.exit(-1);
                }
                /*if(threshold<=0){
                    System.err.println("Error: It is necessary to set a maximum lenght higher than 0 (use parameter --max-segment-len).");
                    System.exit(-1);
                }*/
                if(tmsource!=null){
                    if(segsource==null){
                        System.err.println("Error: It is necessary to define a the file where the source language sub-segments will be saved (use parameter --seg-source).");
                        System.exit(-1);
                    }
                    else{
                        GenerateSegmentsFile(tmsource, segsource, maxseglen, htmlsubsegs);
                    }
                }
                if(tmtarget!=null){
                    if(segtarget==null){
                        System.err.println("Error: It is necessary to define a the file where the target language sub-segments will be saved (use parameter --seg-target).");
                        System.exit(-1);
                    }
                    else{
                        GenerateSegmentsFile(tmtarget, segtarget, maxseglen, htmlsubsegs);
                    }
                }
            break;
            //Generate the translation memory and write it into a file as a java object
            case 'g':
                if(tmsource==null){
                    System.err.println("Error: It is necessary to define the file containing the source language segments of the translation memory (use parameter --tm-source).");
                    System.exit(-1);
                }
                if(tmtarget==null){
                    System.err.println("Error: It is necessary to define the file containing the target language segments of the translation memory (use parameter --tm-target).");
                    System.exit(-1);
                }
                if(segsource==null || segtarget==null){
                    System.err.println("Warning: Undefined path to access the file containing the source"
                            + "or the target language sub-segments of the sub-segment dictionary"
                            + "(use parameter --seg-source or --seg-target). The translation memory"
                            + "will be generated without obtaining the translation evidence.");
                }
                else{
                    if(maxseglen<0){
                        System.err.println("Warning: Undefined maximum lenght higher than 0 (use parameter --max-segment-len)."
                                + "The translation memory will be generated without obtaining the translation evidence.");
                    }
                }
                if(tmpath==null){
                    System.err.println("Error: It is necessary to define the path of the output file containing the translation memory java object(use parameter --tm-path).");
                    System.exit(-1);
                }
                //Creating and loading TM
                TranslationMemory tm=new TranslationMemory();
                tm.LoadTM(tmsource, tmtarget);
                tm.Sort();
                if(segsource!=null && segtarget!=null){
                    //Loading sub-segment dictionary
                    SegmentDictionary sdic = new SegmentDictionary();
                    if(htmlsubsegs) {
                        sdic.LoadHTMLSegments(segsource, segtarget, debug);
                    }
                    else {
                        sdic.LoadSegments(segsource, segtarget, debug);
                    }
                    tm.GenerateEvidences(sdic, maxseglen, debug);
                }
                try {
                    ObjectOutputStream outputtm = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(tmpath)));
                    outputtm.writeObject(tm.tus);
                    outputtm.close();
                    //System.out.println(tm.evidences.size());
                } catch (IOException ex) {
                    System.err.println("Error while trying to write source TM file at "+tmpath+".");
                    ex.printStackTrace(System.err);
                }
            break;
            /*case 'b':
                TranslationMemory tm_test=new TranslationMemory(), tm_trans=new TranslationMemory();
                tm_test.LoadTM(tmsource, tmtarget);
                tm_trans.LoadTM(segsource, segtarget);
                for(TranslationUnit tutest: tm_test.tus){
                    for(TranslationUnit tutrans: tm_trans.tus){
                        double score1=GetScore(tutest.source, tutrans.source, threshold,null);
                        double score2=GetScore(tutest.target, tutrans.target, 0,null);
                        if(score1>=threshold)
                            System.out.println(score1+"\t"+score2);
                    }
                }
            break;*/
            //Obtain features
            case 'f':
                if(feattypestring==null){
                    System.err.println("You must define the type of features to"
                            + "be obtained by using the option --feat-type. One"
                            + "of the following options must be chosen: source-target,"
                            + "mn, mn-not-norm, geometric-style, geometric-style-onlypos,"
                            + "or geometric-style-not-norm");
                    System.exit(-1);
                }

                int featuretype=0;
                if(feattypestring.equals("source-target")){
                    featuretype=0;
                }
                else if(feattypestring.equals("mn")){
                    featuretype=1;
                }
                else if(feattypestring.equals("mn-not-norm")){
                    featuretype=2;
                }
                else if(feattypestring.equals("geometric-style")){
                    featuretype=3;
                }
                else if(feattypestring.equals("geometric-style-not-norm")){
                    featuretype=4;
                }
                else if(feattypestring.equals("geometric-style-onlypos")){
                    featuretype=5;
                }
                else if(feattypestring.equals("source-target-geometric-style-not-norm")){
                    featuretype=6;
                }
                else if(feattypestring.equals("geometric-style-onlypos-quantitative")){
                    featuretype=7;
                }
                else if(feattypestring.equals("geometric-style-onlypos-quantitative-norm")){
                    featuretype=8;
                }
                else if(feattypestring.equals("dumb-baseline")){
                    featuretype=9;
                }
                else{
                    System.err.println("Option chosen for --feat-type option is unknown");
                    System.exit(-1);
                }
            
                if(maxseglen<0){
                    System.err.println("Error: It is necessary to set a maximum lenght equal or higher than 0 (use parameter --max-segment-len).");
                    System.exit(-1);
                }
                if(threshold==null){
                    System.err.println("Error: It is necessary to set a maximum lenght higher than 0 (use parameter --max-segment-len).");
                    System.exit(-1);
                }
                if(tmpath==null){
                    System.err.println("Error: It is necessary to define the path of the file containing the translation memory java object(use parameter --tm-path).");
                    System.exit(-1);
                }
                if(output==null){
                    System.err.println("Error: It is necessary to define the path of the file where the features will be writen (use parameter --feature-output).");
                    System.exit(-1);
                }
                if(debug) {
                    System.out.println(threshold);
                }
                //Loading the TM

                TranslationMemory trans_memory = new TranslationMemory();
                try{
                    ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(tmpath)));
                    trans_memory.tus=(List<TranslationUnit>) input.readObject();
                    input.close();
                } catch (IOException ex) {
                    System.err.println("Error while trying to read source TM file. The path may be incorrect or the object could be corrupted.");
                }catch (ClassNotFoundException ex) {
                    ex.printStackTrace(System.err);
                    System.exit(-1);
                }
                //trans_memory.LoadTMFromObject(tmpath);
                if(minfeatpath==null) {
                    trans_memory.GetFeaturesLeaveOneOut(maxseglen, new File(output),
                            null,threshold,debug,providefmseditdistinfo,
                            discardwordsnoevidence, featuretype);
                }
                else {
                    trans_memory.GetFeaturesLeaveOneOut(maxseglen, new File(output),
                            new File(minfeatpath),threshold,debug, providefmseditdistinfo,
                            discardwordsnoevidence, featuretype);
                }

            break;
            //Counting matches
            case 'd':
                if(tmsource==null || tmtarget==null){
                    System.err.println("Error: It is needed to define the path to the translatio memory java object (use parameter --tm-path) or the paths of the files containing the source and the target segments (use parameter --tm-source and --tm-target).");
                    System.exit(-1);
                }
                if(threshold==null){
                    System.err.println("Error: It is necessary to set a maximum lenght higher than 0 (use parameter --max-segment-len).");
                    System.exit(-1);
                }
                else{
                    tm=new TranslationMemory();
                    tm.LoadTM(tmsource, tmtarget);
                    for(int i=0;i<tm.tus.size();i++){
                        for(int j=i+1;j<tm.tus.size();j++){
                            double score=GetScore(tm.tus.get(i).source,
                                    tm.tus.get(j).source, threshold, null);
                            if(score>threshold){
                                System.out.print("\rFuzzy match score between TUs ");
                                System.out.print(i);
                                System.out.print(" and ");
                                System.out.print(j);
                                System.out.print(": ");
                                System.out.println(score);
                            }
                        }
                    }
                }
            break;
            /*case 'r':
                if(foutput!=null && tmpath!=null && maxseglen>=0){
                    try {
                        //Loading the TM
                        ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(tmpath)));
                        TranslationMemory tm = (TranslationMemory) input.readObject();
                        input.close();
                        if(threshold>0.0)
                            tm.SetThreshold(threshold);
                        if(minfeatpath==null)
                            tm.GetFeaturesLeaveOneOut(maxseglen, new File(foutput),null,debug);
                        else
                            tm.GetFeaturesLeaveOneOut(maxseglen, new File(foutput),new File(minfeatpath),debug);

                    } catch (IOException ex) {
                        System.err.println("Error while trying to read source TM file.");
                    }catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            break;*/
            //Information about matching
            case 'e':
                if(tmsource==null){
                    System.err.println("Error: It is needed to define the path to the translatio memory source language segments (use parameter --tm-source).");
                    System.exit(-1);
                }
                if(tmtarget==null){
                    System.err.println("Error: It is needed to define the path to the translatio memory target language segments (use parameter --tm-target).");
                    System.exit(-1);
                }
                if(testsource==null){
                    System.err.println("Error: It is needed to define the path to the source language segments of the test set (use parameter --test-source).");
                    System.exit(-1);
                }
                if(testtarget==null){
                    System.err.println("Error: It is needed to define the path to the target language segments of the test set (use parameter --test-source).");
                    System.exit(-1);
                }
                trans_memory=new TranslationMemory();
                trans_memory.LoadTM(tmsource, tmtarget);
                trans_memory.Sort();

                List<Segment> testsegs=new LinkedList<Segment>();
                try {
                    testsegs=ReadSegmentsFile(testsource);
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

                //Loading the TM
                SortedMap<Integer, MyInteger> slen=new TreeMap<Integer, MyInteger>();
                SortedMap<Integer, MyInteger> tlen=new TreeMap<Integer, MyInteger>();
                int ssegmentslen=0;
                int tsegmentslen=0;
                for(TranslationUnit tu: trans_memory.tus){
                    ssegmentslen+=tu.source.size();
                    tsegmentslen+=tu.target.size();
                    for(Evidence e: tu.getEvidences()){
                        int len=e.getSegment().getLength();
                        if(slen.containsKey(len)) {
                            slen.get(len).increment();
                        }
                        else {
                            slen.put(len, new MyInteger(1));
                        }
                        len=e.getTranslation().getLength();
                        if(tlen.containsKey(len)) {
                            tlen.get(len).increment();
                        }
                        else {
                            tlen.put(len, new MyInteger(1));
                        }
                    }
                }
                double[] saverage=new double[5];
                double[] taverage=new double[5];
                double[] rangessaverage=new double[5];
                double[] rangestaverage=new double[5];
                saverage[0]=saverage[1]=saverage[2]=saverage[3]=saverage[4]=0.0;
                taverage[0]=taverage[1]=taverage[2]=taverage[3]=taverage[4]=0.0;
                rangessaverage[0]=rangessaverage[1]=rangessaverage[2]=rangessaverage[3]=rangessaverage[4]=0.0;
                rangestaverage[0]=rangestaverage[1]=rangestaverage[2]=rangestaverage[3]=rangestaverage[4]=0.0;

                for(int i=0;i<testsegs.size();i++){
                    System.err.print("\rChecking matches for source language test segment ");
                    System.err.print(i);
                    System.err.print("         ");
                    Segment curseg=testsegs.get(i);
                    double[] sum=new double[5];
                    sum[0]=sum[1]=sum[2]=sum[3]=sum[4]=0.0;
                    int initindex=0;
                    while(initindex<trans_memory.tus.size() &&
                            trans_memory.tus.get(initindex).source.size()>=
                            (double)curseg.size()*0.5) {
                        initindex++;
                    }
                    int finalindex=trans_memory.tus.size()-1;
                    while(finalindex>=initindex && trans_memory.tus.get(finalindex).source.size()>=
                            (double)curseg.size()/0.5) {
                        finalindex--;
                    }
                    while(initindex<finalindex){
                        double score=GetScore(trans_memory.tus.get(finalindex).source,
                                curseg, 0.5, null);
                        if(debug && score>=0.5) {
                            System.out.println(trans_memory.tus.get(finalindex).source+"\t"+curseg+"\t"+score);
                        }
                        if(score>=0.9){
                            sum[0]++;
                        }
                        else if(score>=0.8){
                            sum[1]++;
                        }
                        else if(score>=0.7){
                            sum[2]++;
                        }
                        else if(score>=0.6){
                            sum[3]++;
                        }
                        else if(score>=0.5){
                            sum[4]++;
                        }
                        finalindex--;
                    }
                    saverage[0]+=sum[0];
                    saverage[1]+=sum[0]+sum[1];
                    saverage[2]+=sum[0]+sum[1]+sum[2];
                    saverage[3]+=sum[0]+sum[1]+sum[2]+sum[3];
                    saverage[4]+=sum[0]+sum[1]+sum[2]+sum[3]+sum[4];

                    rangessaverage[0]+=sum[0];
                    rangessaverage[1]+=sum[1];
                    rangessaverage[2]+=sum[2];
                    rangessaverage[3]+=sum[3];
                    rangessaverage[4]+=sum[4];
                }

                testsegs=new LinkedList<Segment>();
                try {
                    testsegs=ReadSegmentsFile(testtarget);
                } catch (FileNotFoundException ex) {
                    System.err.print("Error: Target language test segments file '");
                    System.err.print(testtarget);
                    System.err.println("' could not be found.");
                    System.exit(-1);
                } catch (IOException ex) {
                    System.err.print("Error while reading target language test segments from file '");
                    System.err.print(testtarget);
                    System.err.println("'");
                    System.exit(-1);
                }

                trans_memory=new TranslationMemory();
                trans_memory.LoadTM(tmtarget,tmsource);
                trans_memory.Sort();


                for(int i=0;i<testsegs.size();i++){
                    System.err.print("\rChecking matches for target language test segment ");
                    System.err.print(i);
                    System.err.print("         ");
                    Segment curseg=testsegs.get(i);
                    double[] sum=new double[5];
                    sum[0]=sum[1]=sum[2]=sum[3]=sum[4]=0.0;
                    int initindex=0;
                    while(initindex<trans_memory.tus.size() &&
                            trans_memory.tus.get(initindex).source.size()>=
                            (double)curseg.size()*0.5){
                        initindex++;
                    }
                    int finalindex=trans_memory.tus.size()-1;
                    while(finalindex>=initindex && trans_memory.tus.get(finalindex).source.size()>=
                            (double)curseg.size()/0.5) {
                        finalindex--;
                    }
                    while(initindex<finalindex){
                        double score=GetScore(trans_memory.tus.get(finalindex).source,
                                curseg, 0.5, null);
                        if(debug && score>=0.5) {
                            System.out.println(trans_memory.tus.get(finalindex).source+"\t"+curseg+"\t"+score);
                        }
                        if(score>=0.9){
                            sum[0]++;
                        }
                        else if(score>=0.8){
                            sum[1]++;
                        }
                        else if(score>=0.7){
                            sum[2]++;
                        }
                        else if(score>=0.6){
                            sum[3]++;
                        }
                        else if(score>=0.5){
                            sum[4]++;
                        }

                        finalindex--;
                    }
                    taverage[0]+=sum[0];
                    taverage[1]+=sum[0]+sum[1];
                    taverage[2]+=sum[0]+sum[1]+sum[2];
                    taverage[3]+=sum[0]+sum[1]+sum[2]+sum[3];
                    taverage[4]+=sum[0]+sum[1]+sum[2]+sum[3]+sum[4];

                    rangestaverage[0]+=sum[0];
                    rangestaverage[1]+=sum[1];
                    rangestaverage[2]+=sum[2];
                    rangestaverage[3]+=sum[3];
                    rangestaverage[4]+=sum[4];
                }
                
                System.err.println("\rFinished                                 ");
                System.out.println("SOURCE");
                System.out.print("\tAverage segment length: ");
                System.out.println((double)ssegmentslen/trans_memory.tus.size());
                for(Entry<Integer,MyInteger> e: slen.entrySet()){
                    System.out.print("\tEvidences of length L=");
                    System.out.print(e.getKey());
                    System.out.print(": ");
                    System.out.println(e.getValue().getValue());
                }
                System.out.println("\tMATCHING INFO");
                    System.out.print("\t\tAverage matches per TU with fuzzy matching score threshold at 0.5: ");
                    System.out.print(saverage[4]/testsegs.size());
                    System.out.print("/");
                    System.out.println(rangessaverage[4]/testsegs.size());
                    System.out.print("\t\tAverage matches per TU with fuzzy matching score threshold at 0.6: ");
                    System.out.print(saverage[3]/testsegs.size());
                    System.out.print("/");
                    System.out.println(rangessaverage[3]/testsegs.size());
                    System.out.print("\t\tAverage matches per TU with fuzzy matching score threshold at 0.7: ");
                    System.out.print(saverage[2]/testsegs.size());
                    System.out.print("/");
                    System.out.println(rangessaverage[2]/testsegs.size());
                    System.out.print("\t\tAverage matches per TU with fuzzy matching score threshold at 0.8: ");
                    System.out.print(saverage[1]/testsegs.size());
                    System.out.print("/");
                    System.out.println(rangessaverage[1]/testsegs.size());
                    System.out.print("\t\tAverage matches per TU with fuzzy matching score threshold at 0.9: ");
                    System.out.print(saverage[0]/testsegs.size());
                    System.out.print("/");
                    System.out.println(rangessaverage[0]/testsegs.size());
                System.out.println("TARGET");
                System.out.print("\tAverage segment length: ");
                System.out.println((double)tsegmentslen/trans_memory.tus.size());
                for(Entry<Integer,MyInteger> e: tlen.entrySet()){
                    System.out.print("\tEvidences of length L=");
                    System.out.print(e.getKey());
                    System.out.print(": ");
                    System.out.println(e.getValue().getValue());
                }
                System.out.println("\tMATCHING INFO");
                System.out.print("\t\tAverage matches per TU with fuzzy matching score threshold at 0.5: ");
                System.out.print(taverage[4]/testsegs.size());
                System.out.print("/");
                System.out.println(rangestaverage[4]/testsegs.size());
                System.out.print("\t\tAverage matches per TU with fuzzy matching score threshold at 0.6: ");
                System.out.print(taverage[3]/testsegs.size());
                System.out.print("/");
                System.out.println(rangestaverage[3]/testsegs.size());
                System.out.print("\t\tAverage matches per TU with fuzzy matching score threshold at 0.7: ");
                System.out.print(taverage[2]/testsegs.size());
                System.out.print("/");
                System.out.println(rangestaverage[2]/testsegs.size());
                System.out.print("\t\tAverage matches per TU with fuzzy matching score threshold at 0.8: ");
                System.out.print(taverage[1]/testsegs.size());
                System.out.print("/");
                System.out.println(rangestaverage[1]/testsegs.size());
                System.out.print("\t\tAverage matches per TU with fuzzy matching score threshold at 0.9: ");
                System.out.print(taverage[0]/testsegs.size());
                System.out.print("/");
                System.out.println(rangestaverage[0]/testsegs.size());
            break;
            //Obtins the logarithm for each features in a file of features separated by a coma
            /*case 'l':
                if(finput!=null && foutput!=null && minval>0.0){
                    BufferedReader reader = null;
                    String line=null;
                    List<double[]> features=new LinkedList<double[]>();

                    try {
                        reader = new BufferedReader(new FileReader(finput));
                        while((line=reader.readLine())!=null){
                            String[] stmp=line.split(",");
                            double[] ftmp=new double[stmp.length];
                            for(int i=0;i<stmp.length;i++){
                                ftmp[i]=Double.parseDouble(stmp[i]);
                            }
                            features.add(ftmp);
                        }
                    } catch (FileNotFoundException ex) {
                        System.err.println("Error while trying to read source features file: file not found.");
                        ex.printStackTrace();
                        System.exit(-1);
                    } catch (IOException ex) {
                        System.err.println("Error while trying to read or close source features file.");
                        ex.printStackTrace();
                        System.exit(-1);
                    }

                    LogFeatures(features, minval, new File(foutput));
                }
            break;*/
            default:
                System.err.println("Unknown action "+action);
                System.exit(-1);
        }
    }
}
