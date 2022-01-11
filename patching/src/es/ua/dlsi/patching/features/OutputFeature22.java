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
 * not really a feature, just a way to get iwhat is the maximum/minimum and 
 * median of the lenght of patching operators we are using.
 * you can put this in excel to get it, also, in FeatureManager just comment all others to get it fast
 * we will just print them out with endlines 
 * @author John Evan Ortega
 * @version 0.1
 */
public class OutputFeature22 extends OutputFeature{
	public OutputFeature22(){}
	@Override
	public void setFeature(IntermediateFeature iFeature){
		if(iFeature!=null)
			super.iFeature = iFeature;
		else
			System.err.println("Intermediate Feature is null");
	}
	@Override
	public String process(){
		for(PatchOperator patchOp : super.iFeature.operators){
			if(patchOp.isYesNode()){
				String patchOpStr 	= patchOp.getWords().toString();
				int patchOpSize		= patchOp.getWords().size();
				int patchBegin		= patchOp.getOrigPos().getFirst();
				int patchEnd		= patchOp.getOrigPos().getSecond();
				int sBegin		= patchOp.getOrigSPos().getFirst();
				int sEnd		= patchOp.getOrigSPos().getSecond();
				int sPrimeBegin		= patchOp.getOrigSPrimePos().getFirst();
				int sPrimeEnd		= patchOp.getOrigSPrimePos().getSecond();
				List<Word> sWords	= new ArrayList<Word>();
				List<Word> sPrimeWords 	= new ArrayList<Word>();
				for(int i=sBegin;i<=sEnd;i++){
					sWords.add(super.iFeature.s.getWord(i));
				}
				for(int i=sPrimeBegin;i<=sPrimeEnd;i++){
					sPrimeWords.add(super.iFeature.sPrime.getWord(i));
				}
				List<Word> tWords	= new ArrayList<Word>();
				// get the list of words from t
				for(int i=patchBegin;i<=patchEnd;i++){
					tWords.add(super.iFeature.t.getWord(i));
				}
					// t* is the gold
					Segment tPrime	= super.iFeature.tPrime;
					Segment t	= super.iFeature.t;
					Segment tWiggle	= super.iFeature.hypothesis;
					double ret	= 0;

					int edTPrime	= Segment.EditDistance(tPrime,t,null);
					int edTWiggle	= Segment.EditDistance(tWiggle,t,null);
					int maxTop	= java.lang.Math.max(0,(edTPrime-edTWiggle));

					if(maxTop > 0 && edTPrime > 0)
						ret = maxTop/(double)edTPrime;
				//if(ret==1){
				//System.err.println(patchOpSize);
				//if(patchOpSize==17){
				/*
					Segment sSubSegment 			= new Segment(sWords);
					Segment sigmaPrimeSubSegment 		= new Segment(sPrimeWords);
					Segment tSubSegment = new Segment(tWords);
					Segment tauPrimeSubSegment = new Segment(patchOp.getWords());
					System.err.println("s prime: " + super.iFeature.sPrime.toString());
					System.err.println("s: " + super.iFeature.s.toString());
					System.err.println("t: " + super.iFeature.t.toString());
					System.err.println("t~ " + super.iFeature.hypothesis.toString());
					System.err.println("t* " + super.iFeature.tPrime.toString());
					System.err.println("s sub: " + sSubSegment.toString());
					System.err.println("sigma prime sub: " + sigmaPrimeSubSegment.toString());
					System.err.println("t sub: " + tSubSegment.toString());
					System.err.println("tau prime sub: " + tauPrimeSubSegment.toString());
				*/
				//}
				//if(patchOpSize==0){
				/*
				if(tWords.size()==0){
					System.err.println("empty");
				}
				else{
					System.err.println("other");
				}
				/*
				*/
			}
		}
		return String.valueOf(0);
	}

}
