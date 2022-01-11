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
 * return left grounded patch
 * 
 * @author John Evan Ortega
 * @version 0.1
 */
public class OutputFeature41 extends OutputFeature{
	public OutputFeature41(){}
	@Override
	public void setFeature(IntermediateFeature iFeature){
		if(iFeature!=null)
			super.iFeature = iFeature;
		else
			System.err.println("Intermediate Feature is null");
	}
	@Override
	public String process(){
		String patchOps = "";
		int numYesPatchOps = 0;
		int numNoPatchOps = 0;
		int numGroundedPatchOps = 0;
		List<Pair<Integer,Integer>> talignment = new ArrayList<Pair<Integer,Integer>>();
		double tscore=TranslationMemory.GetScoreNew(super.iFeature.t, super.iFeature.hypothesis, talignment);
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
			if(patchOp.isYesNode() && tSubSegment.size()>0 && tauPrimeSubSegment.size()>0){
				String tWord=tSubSegment.getWord(0).getValue().toString();
				String tWiggleWord=tauPrimeSubSegment.getWord(0).getValue().toString();
				String tLastWord=tSubSegment.getWord(tSubSegment.size()-1).getValue().toString();
				String tLastWiggleWord=tauPrimeSubSegment.getWord(tauPrimeSubSegment.size()-1).getValue().toString();
				if(tWord.equals(tWiggleWord) && tLastWord.equals(tLastWiggleWord) && tSubSegment.size()>1 && tauPrimeSubSegment.size()>1)
					++numGroundedPatchOps;
				++numYesPatchOps;
				patchOps+="Patch Op " +  numYesPatchOps + " :\n";
				patchOps+="sigma’ sub-segment:\n";
				patchOps+=sigmaPrimeSubSegment.toString() + "\n";
				patchOps+="sigma sub-segment:\n";
				patchOps+=sSubSegment.toString() + "\n";
				patchOps+="tau’ sub-segment:\n";
				patchOps+=tauPrimeSubSegment.toString() + "\n";
				patchOps+="tau sub-segment ( from position " +  patchBegin + " to position " +  patchEnd + " in t ):\n";
				patchOps+=tSubSegment.toString() + "\n";
			}
			else
				numNoPatchOps++;
		}

		// t* is the gold
		double ret	= 0.0;
		if(numGroundedPatchOps>0 && numYesPatchOps > 0)
			ret = numGroundedPatchOps/(double)numYesPatchOps;
			//System.out.println("numGroundedPatchOps is: " + numGroundedPatchOps);
			//System.out.println("numYesPatchOps" + numYesPatchOps);
			//System.out.println("return value is" +ret);
		return String.valueOf(ret);
	}

}
