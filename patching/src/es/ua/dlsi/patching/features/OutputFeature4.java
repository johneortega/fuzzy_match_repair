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
 * Feature 4 Source sub-segment-level fuzzy-match score:
 * FMS
 * seg
 * (
 * s,s
 * ′
 * )
 * .
 * @author John Evan Ortega
 * @version 0.1
 */
public class OutputFeature4 extends OutputFeature{
	public OutputFeature4(){}
	@Override
	public void setFeature(IntermediateFeature iFeature){
		if(iFeature!=null)
			super.iFeature = iFeature;
		else
			System.err.println("Intermediate Feature is null");
	}
	@Override
	public String process(){
		List<Pair<Integer,Integer>> salignment = new ArrayList<Pair<Integer,Integer>>();
		double sscore=TranslationMemory.GetScoreNew(super.iFeature.s, super.iFeature.sPrime, salignment);
		//System.err.println("this is s:" + super.iFeature.s.toString());
		//System.err.println("this is s':" + super.iFeature.sPrime.toString());

		// the alignment is s to s', the first values will be s
		// let's go through each word and get count of mismatches
		// and matches
		int wordCount = 0;
		List<Integer> sWords = new ArrayList<Integer>();
		for(Pair<Integer,Integer> pair : salignment){
			//System.err.println("This is the alignment:(" + pair.getFirst() + ", " + pair.getSecond());
			sWords.add(pair.getFirst());
		}
		List<Integer> sPrimeWords = new ArrayList<Integer>();
		for(Pair<Integer,Integer> pair : salignment){
			sPrimeWords.add(pair.getSecond());
		}
		boolean prev = false;
		int sMatch = 0;
		int sMismatch = 0;
		for(int i=0;i<super.iFeature.s.getSentence().size();i++){
			if(sWords.contains(i) && (!prev||i==0)){
			 	prev=true;	
				sMatch++;
			}
			else if(!sWords.contains(i)&& (prev||i==0)){
			 	prev=false;	
				sMismatch++;
			}
		}
		prev = false;
		int sPrimeMatch = 0;
		int sPrimeMismatch = 0;
		for(int i=0;i<super.iFeature.sPrime.getSentence().size();i++){
			if(sPrimeWords.contains(i) && (!prev||i==0)){
			 	prev=true;	
				sPrimeMatch++;
			}
			else if(!sPrimeWords.contains(i)&& (prev||i==0)){
			 	prev=false;	
				sPrimeMismatch++;
			}
		}
		int sPrimeTotal = sPrimeMatch + sPrimeMismatch;
		int sTotal = sMatch + sMismatch;
		// the total number of sub-segments is the max
		int numSubSeg = java.lang.Math.max(sPrimeTotal,sTotal);
		int numMisMatch = java.lang.Math.max(sPrimeMismatch,sMismatch);
		//System.err.println("total mismatch:" + numMisMatch);
		//System.err.println("total subsegmens:" + numSubSeg);
		double finalScr  = numMisMatch/(double)numSubSeg;
		return String.valueOf(finalScr);

	}

}
