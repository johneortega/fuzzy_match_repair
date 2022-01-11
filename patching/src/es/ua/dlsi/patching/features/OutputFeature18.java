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
 * Glass box feature 12, feature 18 in count
 * Measure  of  how  evenly  distributed  are  the  mismatched  source-language  sub-
 * segments in the patching operators used to build the patched translation
 * @author John Evan Ortega
 * @version 0.1
 */
public class OutputFeature18 extends OutputFeature{
	public OutputFeature18(){}
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
		double topret		= 0;
		double botret		= 0;
		int totalYesPatchOps	= 0;
		int totalNoPatchOps	= 0;
		String patchOps = "";
		patchOps+="-----------Begin------------\n";
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
			if(patchOp.isYesNode()){
				totalYesPatchOps++;	
				patchOps+="s' sub: " + sigmaPrimeSubSegment.toString() + "\n";
				patchOps+="s' sub begin: " +sPrimeBegin + "\n";
				patchOps+="s' sub end: " +sPrimeEnd + "\n";
				patchOps+="s sub: " + sSubSegment.toString() + "\n";
				patchOps+="s sub begin: " + sBegin + "\n";
				patchOps+="s sub end: " + sEnd + "\n";
				// now go find all the contiguos sub-segments
				// and use that count as nk
				int matchSize=0;// total number of matches
				boolean wasMatched = false;
				int prevTP = 0;
				int prevT  = 0;
				int prevMatched = 0;
				double finalNumerator = 0;
				double topRightExpr = 1;
				double finalExpression = 0;
				// match size is the number of matching sub-segments
				// now we need to make the multiplication
				int alignCounter = 0;
				for(Pair<Integer,Integer> pair : salignment){
					int tp = pair.getFirst();
					int t  = pair.getSecond();
					boolean runNum = false;
					++alignCounter;
					patchOps+="this is tp: " + tp + "\n";
					patchOps+="this is t: " + t + "\n";
					patchOps+="this is prevtp: " + prevTP+"\n";
					patchOps+="this is prevt: " + prevT+"\n";
					if((tp==prevTP-1 && t==prevT-1)||alignCounter==1){
						++matchSize;
					}
					// not matched before, now it is
					if(!wasMatched && ((tp==prevTP-1 && t==prevT-1)||(alignCounter==1))){
						++prevMatched;
						wasMatched=true;
					        patchOps+="prevmatch++\n";
					}
					// matched before, now it's not
					if(wasMatched && ( tp != prevTP-1 || t != prevT-1 ) && alignCounter>1){
						wasMatched=false;
						runNum = true;
					}
					// or at the end with a match
					if(wasMatched && alignCounter == salignment.size()){
						runNum = true;
					}
					if(runNum){
						// ends matched let's do calcs
						// prevMatched is the mi
						// matchSize is nk
						double nominator = 0;
						patchOps+="ms:" + matchSize+ "pm:" + prevMatched+"\n";
						// 04/03/2017 - new from mikel
						nominator = ((double)matchSize - 1); 
						double denominator=0;
						// 04/03/2017 - new from mikel
						denominator = sSubSegment.size() - (2 * prevMatched) + 1; 
						patchOps+="nom:" + nominator + "den:" + denominator+"\n";
						if(nominator>0 && denominator>0){
							topRightExpr = topRightExpr * (nominator/(double)denominator);
						}
						else{
							topRightExpr = 0;
						}
						patchOps+="topRightExpr immed:" + topRightExpr+"\n";
						// set back to get size again
						matchSize=0;
					}
					prevTP = tp;
					prevT  = t;
					patchOps+="TR:" + topRightExpr+"\n";	
					//patchOps+="this is tp: " + tp + "\n";
				}// end for loop
				// now we have all topright expressions
				// multiply * tau size
				if(topRightExpr>0){
					finalNumerator = sSubSegment.size() * (double)topRightExpr;
				}
				patchOps+="Final Numerator:" + finalNumerator+"\n";	
				patchOps+="Final Expression:" + finalExpression+"\n";	
				// now we add on for patching operator sum
				topret+=finalNumerator;
				botret+=sSubSegment.size();
				patchOps+="Final Expression:" + finalExpression+"\n";	
			}// end if yes node
			else{
				totalNoPatchOps++;	
			}
		}
		patchOps+="-----------Finished------------\n";
		patchOps+="topret:" + topret +"\n";	
		patchOps+="botret:" + botret +"\n";	
		if(topret>0 && botret>0)
			ret = topret/botret;
		/*
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
}
