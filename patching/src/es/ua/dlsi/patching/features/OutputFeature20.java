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
import java.util.Collections;
import java.util.Comparator;
import es.ua.dlsi.utils.Pair;
import es.ua.dlsi.segmentation.*;
import es.ua.dlsi.patching.*;
import es.ua.dlsi.translationmemory.TranslationMemory;
import es.ua.dlsi.translationmemory.TranslationUnit;

/**
 * Series of Glassbox features from QE with Felipe and Mikel
 * https://www.sharelatex.com/project/54182d9e6a2be06c2ee1386c
 * this is the feature class that will write out to the file
 * it will take an intermediate class as the input
 * further classes will have the intermediate feature class available to work on
 * Glass box feature 14, feature 20 in count
 * Number of patching operators used to get t' divided by thw number of mis-matched words in
 * s 
 * @author John Evan Ortega
 * @version 0.1
 */
public class OutputFeature20 extends OutputFeature{
	public OutputFeature20(){}
	@Override
	public void setFeature(IntermediateFeature iFeature){
		if(iFeature!=null)
			super.iFeature = iFeature;
		else
			System.err.println("Intermediate Feature is null");
	}
	@Override
	public String process(){

		// number of patching operators with YES
		int numPatchOps = 0;
		double ret	= 0;
		int sSize	= super.iFeature.s.size();
		int sPrimeSize	= super.iFeature.sPrime.size();
		int maxSSize	= Math.max(sSize,sPrimeSize);
		List<Pair<Integer,Integer>> salignment = new ArrayList<Pair<Integer,Integer>>();
		double sscore=TranslationMemory.GetScoreNew(super.iFeature.s, super.iFeature.sPrime, salignment);
		int sMatched	= salignment.size();
		int misMatched	= maxSSize - sMatched;
		String patchOps = "";
		for(PatchOperator patchOp : super.iFeature.operators){
    			int patchBegin		= patchOp.getOrigPos().getFirst();
    			int patchEnd		= patchOp.getOrigPos().getSecond();
    			int sBegin		= patchOp.getOrigSPos().getFirst();
    			int sEnd		= patchOp.getOrigSPos().getSecond();
    			int sPrimeBegin		= patchOp.getOrigSPrimePos().getFirst();
    			int sPrimeEnd		= patchOp.getOrigSPrimePos().getSecond();
			List<Word> tWords	= new ArrayList<Word>();
			List<Word> sWords	= new ArrayList<Word>();
			List<Word> sPrimeWords 	= new ArrayList<Word>();
			for(int i=sBegin;i<=sEnd;i++){
				sWords.add(super.iFeature.s.getWord(i));
			}
			for(int i=sPrimeBegin;i<=sPrimeEnd;i++){
				sPrimeWords.add(super.iFeature.sPrime.getWord(i));
			}
			Segment sSubSegment 			= new Segment(sWords);
			Segment sigmaPrimeSubSegment 		= new Segment(sPrimeWords);
			// get the list of words from t
			for(int i=patchBegin;i<=patchEnd;i++){
				tWords.add(super.iFeature.t.getWord(i));
			}
			Segment tSubSegment = new Segment(tWords);
			Segment tauPrimeSubSegment = new Segment(patchOp.getWords());
			if(patchOp.isYesNode()){
				patchOps+="t sub: " + tSubSegment.toString() + "\n";
				patchOps+="t' sub: " + tauPrimeSubSegment.toString() + "\n";
				patchOps+="s sub: " + sSubSegment.toString() + "\n";
				patchOps+="s' sub: " + sigmaPrimeSubSegment.toString() + "\n";
				patchOps+="patch begin: " + patchBegin + "\n";
				patchOps+="patch end: " + patchEnd + "\n";
				numPatchOps++;
			}
		}
		if(numPatchOps > 0 && misMatched >0 ){
			ret=numPatchOps/(double)misMatched;
		}
		/*
		if(ret>5){
			System.out.println("###" + ret);
			System.out.println("t:" + super.iFeature.t.toString());
			System.out.println("t*:" + super.iFeature.tPrime.toString());
			System.out.println("t~:" + super.iFeature.hypothesis.toString());
			System.out.println("s:" + super.iFeature.s.toString());
			System.out.println("s':" + super.iFeature.sPrime.toString());
			System.out.println("num patch ops:" + numPatchOps);
			System.out.println("mismatched:" + misMatched);
			System.out.println("patch ops:\n" + patchOps);
		}
		*/
		return String.valueOf(ret);
	}

}
