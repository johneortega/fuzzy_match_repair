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
import java.util.Map;
import java.util.HashMap;
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
 * Glass box feature 5, feature 11 in count
 * Ratio of words in s′ covered by at least one σ′ used to build a patching operator
 * to the number of words in
 * s'
 * @author John Evan Ortega
 * @version 0.1
 */
public class OutputFeature11 extends OutputFeature{
	public OutputFeature11(){}
	@Override
	public void setFeature(IntermediateFeature iFeature){
		if(iFeature!=null)
			super.iFeature = iFeature;
		else
			System.err.println("Intermediate Feature is null");
	}
	@Override
	public String process(){
		// we need to see how many words were covered
		// to do this we will make a dict the size of the sentence
		// then mark in the dict a 1 when covered
		// lastly we add up all the ones
		int sPrimeLen				= super.iFeature.sPrime.size();
		Map<Integer, Boolean> sPrimeDict	= new HashMap<Integer,Boolean>();
		//String patchOps = "";
		for(int i=0;i<sPrimeLen;i++){
			sPrimeDict.put(i,false);
		}

		for(PatchOperator patchOp : super.iFeature.operators){
			String patchOpStr 	= patchOp.getWords().toString();
			int patchOpSize		= patchOp.getWords().size();
			if(patchOp.isYesNode()){
				//patchOps+="Patch Op: " + patchOpStr + "\n";
				int sPrimeBegin = patchOp.getOrigSPrimePos().getFirst();
				int sPrimeEnd = patchOp.getOrigSPrimePos().getSecond();
				//System.out.println("sprime begin:" + sPrimeBegin);
				//System.out.println("sprime end:" + sPrimeEnd);
				// mark words in sigma prime true
				for(int j=sPrimeBegin;j<=sPrimeEnd;j++){
					sPrimeDict.put(j,true);
				}
			}
		}
		int sigmaPrimeLen = 0;
		for(boolean value :sPrimeDict.values()){
			if(value)
				sigmaPrimeLen++;
		}
		//System.out.println("Sprimelen is: " + sPrimeLen);
		//System.out.println("Sigmaprimelen is: " + sigmaPrimeLen);
		double retValue = 0; 
		if(sPrimeLen>0&&sigmaPrimeLen>0)
			retValue = (sigmaPrimeLen/(double)sPrimeLen);	
		/*
		if(retValue==1){
			System.out.println("###" + retValue);
			System.out.println("t:" + super.iFeature.t.toString());
			System.out.println("t~:" + super.iFeature.hypothesis.toString());
			System.out.println("s:" + super.iFeature.s.toString());
			System.out.println("s':" + super.iFeature.sPrime.toString());
			System.out.println("patch ops:\n" + patchOps);
		}
		*/
		return String.valueOf(retValue);
	}

}
