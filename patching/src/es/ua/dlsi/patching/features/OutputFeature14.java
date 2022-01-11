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
 * Glass box feature 8, feature 14 in count
 * Sum of the length of the overlapping sub-segments of the (σ,σ′) used to build the
 * patching operators used to get t' divided by the length of s′.  The overlapping
 * sub-segments here are those containing words common to
 * s and s′:
 * @author John Evan Ortega
 * @version 0.1
 */
public class OutputFeature14 extends OutputFeature{
	public OutputFeature14(){}
	@Override
	public void setFeature(IntermediateFeature iFeature){
		if(iFeature!=null)
			super.iFeature = iFeature;
		else
			System.err.println("Intermediate Feature is null");
	}
	@Override
	public String process(){

		int sPrimeLen = super.iFeature.sPrime.size();

		int totalMatches 	= 0;
		double ret		= 0;
		String patchOps 	= "";
		// now we need to find the patching ops that exist in the match
		for(PatchOperator patchOp : super.iFeature.operators){
			String patchOpStr 	= patchOp.getWords().toString();
			int patchOpSize		= patchOp.getWords().size();
			int begin		= patchOp.getOrigPos().getFirst();
			int end			= patchOp.getOrigPos().getSecond();
			if(patchOp.isYesNode()){
				// word positions in t,t~=
				List<Pair<Integer,Integer>> salignment = new ArrayList<Pair<Integer,Integer>>();
				double sscore=TranslationMemory.GetScoreNew(super.iFeature.s, super.iFeature.sPrime, salignment);
				totalMatches+=(salignment.size());
			}
		}
		if(totalMatches>0 && sPrimeLen>0){
			ret = totalMatches/(double)sPrimeLen;	
		}
		/*
		if(ret==0){
			System.out.println("###" + ret);
			System.out.println("t:" + super.iFeature.t.toString());
			System.out.println("t~:" + super.iFeature.hypothesis.toString());
			System.out.println("s:" + super.iFeature.s.toString());
			System.out.println("s':" + super.iFeature.sPrime.toString());
		}
		*/
		return String.valueOf(ret);
	}

}
