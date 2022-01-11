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
 *  
 * feature 24 success rate without max (0, below
 * a value of -1111111 here means that the denominator was less than 0
 * 
 * max(0, ED(t,t*)-ED(t~,t*))
 * --------------------------
 *       ED(t,t*)
 * @author John Evan Ortega
 * @version 0.1
 */
public class OutputFeature24 extends OutputFeature{
	public OutputFeature24(){}
	@Override
	public void setFeature(IntermediateFeature iFeature){
		if(iFeature!=null)
			super.iFeature = iFeature;
		else
			System.err.println("Intermediate Feature is null");
	}
	@Override
	public String process(){
		// t* is the gold
		Segment tPrime	= super.iFeature.tPrime;
		Segment t	= super.iFeature.t;
		Segment tWiggle	= super.iFeature.hypothesis;
		double ret	= 0.0;
		int edTPrime	= Segment.EditDistance(t,tPrime,null);
		int edTWiggle	= Segment.EditDistance(tWiggle,tPrime,null);
		int top		= ((edTPrime-edTWiggle)>0)?(edTPrime-edTWiggle):0;
		//System.out.println("edTWiggle: " + String.valueOf(edTWiggle));
		//System.out.println("edTPrime: " +String.valueOf(edTPrime));
		//System.out.println("top: " + String.valueOf(top));
		if(top!=0 || edTPrime != 0)
			ret = top/(double)edTPrime;
		/*
		if(ret	== -111111111){
			//System.out.println(String.valueOf(ret)+"--" +String.valueOf(edTPrime));
			return String.valueOf(ret)+String.valueOf(edTPrime);
		}
		*/
		//else	
		String patchOps = "";
		// System.err.println("t:" + super.iFeature.t);
		// now found out the ratio of words covered
		for(PatchOperator patchOp : super.iFeature.operators){
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
			Segment sSubSegment 			= new Segment(sWords);
			Segment sigmaPrimeSubSegment 		= new Segment(sPrimeWords);
			List<Pair<Integer,Integer>> salignment = new ArrayList<Pair<Integer,Integer>>();
			double sscore=TranslationMemory.GetScoreNew(sigmaPrimeSubSegment, sSubSegment, salignment);
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
				patchOps+="t sub: " + tSubSegment.toString() + "\n";
				patchOps+="t' sub: " + tauPrimeSubSegment.toString() + "\n";
				patchOps+="s sub: " + sSubSegment.toString() + "\n";
				patchOps+="s' sub: " + sigmaPrimeSubSegment.toString() + "\n";
				patchOps+="s sub begin: " + sBegin + "\n";
				patchOps+="s sub end: " + sEnd + "\n";
				patchOps+="s' sub begin: " +sPrimeBegin + "\n";
				patchOps+="s' sub end: " +sPrimeEnd + "\n";
			}
                }
		if (Double.isNaN(ret) || Double.isInfinite(ret)){
			System.out.println("###" + ret);
			System.out.println("t:" + super.iFeature.t.toString());
			System.out.println("t~:" + super.iFeature.hypothesis.toString());
			System.out.println("tprime:" + super.iFeature.tPrime.toString());
			System.out.println("s:" + super.iFeature.s.toString());
			System.out.println("s':" + super.iFeature.sPrime.toString());
			System.out.println("patch ops:\n" + patchOps);
		}
		return String.valueOf(ret);
	}

}
