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
 * Glass box feature 4, feature 10 in count
 * Sum  of  the  length  of  the  overlapping  sub-segments  of  the  patching  operators
 * used to build t' divided by the length of t'. The overlapping sub-segments of a
 * patching operator are those containing words common to t and t'
 * @author John Evan Ortega
 * @version 0.1
 */
public class OutputFeature10 extends OutputFeature{
	public OutputFeature10(){}
	@Override
	public void setFeature(IntermediateFeature iFeature){
		if(iFeature!=null)
			super.iFeature = iFeature;
		else
			System.err.println("Intermediate Feature is null");
	}
	@Override
	public String process(){

		int tWiggleLen = super.iFeature.hypothesis.size();
		//int tWiggleLen = super.iFeature.t.size();

		// word positions in t,t~=
		int totalMatches 	= 0;
		double ret		= 0;
		//String patchOps 	= "";
		// now we need to find the patching ops that exist in the match
		for(PatchOperator patchOp : super.iFeature.operators){
    			int patchBegin		= patchOp.getOrigPos().getFirst();
    			int patchEnd		= patchOp.getOrigPos().getSecond();
			List<Word> tWords	= new ArrayList<Word>();
			// get the list of words from t
			for(int i=patchBegin;i<=patchEnd;i++){
				tWords.add(super.iFeature.t.getWord(i));
			}
			Segment tSubSegment = new Segment(tWords);
			Segment tauPrimeSubSegment = new Segment(patchOp.getWords());
			if(patchOp.isYesNode()){
				//patchOps+="t sub: " + tSubSegment.toString() + "\n";
				//patchOps+="t' sub: " + tauPrimeSubSegment.toString() + "\n";
				List<Pair<Integer,Integer>> talignment = new ArrayList<Pair<Integer,Integer>>();
				double tscore=TranslationMemory.GetScoreNew(tauPrimeSubSegment, tSubSegment, talignment);
				totalMatches+=(talignment.size());
			}
		}
		if(totalMatches>0 && tWiggleLen>0){
			ret = totalMatches/(double)tWiggleLen;	
		}
		/*
		if(ret==0){
			System.out.println("###" + ret);
			System.out.println("t:" + super.iFeature.t.toString());
			System.out.println("t~:" + super.iFeature.hypothesis.toString());
			System.out.println("s:" + super.iFeature.s.toString());
			System.out.println("s':" + super.iFeature.sPrime.toString());
			System.out.println("patch ops:\n" + patchOps);
			System.out.println("totalMatches (numerator):" + totalMatches);
			System.out.println("tWiggleLen:" + tWiggleLen);
		}
		*/
		return String.valueOf(ret);
	}

}
