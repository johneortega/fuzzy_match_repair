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
 * Test Feature
 * Number of digits in t~/ num of puncmarks in s'
 * @author John Evan Ortega
 * @version 0.1
 */
public class OutputFeature43 extends OutputFeature{
	public OutputFeature43(){}
	@Override
	public void setFeature(IntermediateFeature iFeature){
		if(iFeature!=null)
			super.iFeature = iFeature;
		else
			System.err.println("Intermediate Feature is null");
	}
	@Override
	public String process(){
		double ret		= 0;
		//int totalYesPatchOps	= 0;
		//int totalNoPatchOps	= 0;
		String patchOps = "";
		int topret=nummarks(super.iFeature.hypothesis.toString());
		double botret=nummarks(super.iFeature.sPrime.toString());
		// System.err.println("t:" + super.iFeature.t);
		// now found out the ratio of words covered
		/*
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
			List<Pair<Integer,Integer>> talignment = new ArrayList<Pair<Integer,Integer>>();
			double tscore=TranslationMemory.GetScoreNew(tauPrimeSubSegment, tSubSegment, talignment);
			if(patchOp.isYesNode()){
				totalYesPatchOps++;	
				patchOps+="t sub: " + tSubSegment.toString() + "\n";
				patchOps+="t begin: " + patchBegin + "\n";
				patchOps+="t end: " + patchEnd + "\n";
				patchOps+="t' sub: " + tauPrimeSubSegment.toString() + "\n";
			}// if yes node end
			else{
				totalNoPatchOps++;	
			}
		}
		*/
		if(topret>0 && botret>0)
			ret = topret/botret;
		/*
		System.err.println("ret:" + ret);
		System.err.println("topret:" + topret);
		System.err.println("botret:" + botret);
		System.err.println("s'" + super.iFeature.sPrime.toString());
		System.err.println("t~:" + super.iFeature.hypothesis.toString());
		if(ret>1||ret<0){
		        System.err.println(totalYesPatchOps + "\tYES patch ops. ");
		        System.err.println(totalNoPatchOps + "\tNO patch ops. ");
			System.err.println("###" + ret);
			System.err.println("t:" + super.iFeature.t.toString());
			System.err.println("t~:" + super.iFeature.hypothesis.toString());
			System.err.println("s:" + super.iFeature.s.toString());
			System.err.println("s':" + super.iFeature.sPrime.toString());
			System.err.println("patch ops:\n" + patchOps);
		}
		*/
		return String.valueOf(ret);
	}
	private int nummarks(String s){
		int numpunct = 0;
		String puncts ="123456789";
		char[] chars = puncts.toCharArray();
		for(int i=0;i<s.length();i++){
			for(int j=0;j<chars.length;j++) {
			    if(chars[j]==s.charAt(i))
				numpunct += 1;
			}
		}
		return numpunct;
	}
}
