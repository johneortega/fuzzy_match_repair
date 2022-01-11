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
 * Glass box feature 1, feature 7 in count
 * Ratio of word positions in
 * t
 * ' 
 * covered by at least one patching operator to the
 * number of words in
 * t
 * '
 * @author John Evan Ortega
 * @version 0.1
 */
public class OutputFeature7 extends OutputFeature{
	public OutputFeature7(){}
	@Override
	public void setFeature(IntermediateFeature iFeature){
		if(iFeature!=null)
			super.iFeature = iFeature;
		else
			System.err.println("Intermediate Feature is null");
	}
	@Override
	public String process(){
		// first figure out how many words exist in the t~- hypothesis
		int hypSize 	= super.iFeature.hypothesis.size();
		String hypStr  	= super.iFeature.hypothesis.toString();
		int finSize 	= 0;
		double retSize 	= 0;
		//String patchOps = "";
		// now found out the ratio of words covered
		for(PatchOperator patchOp : super.iFeature.operators){
			String patchOpStr 	= patchOp.getWords().toString();
			int patchOpSize		= patchOp.getWords().size();
			if(patchOp.isYesNode()){
				 //System.err.println("This is the hypStr: "+hypStr);
				 //System.err.println("This is the patchOpStr: "+patchOpStr);
				
				 //patchOps+="This is the hypStr: "+hypStr+"\n";
				 //patchOps+="This is the patchOpStr: "+patchOpStr+"\n";
		
				// we assume that patches don't overlap
				if(hypStr.toLowerCase().contains(patchOpStr.toLowerCase())){
					finSize+=patchOpSize;
				}
			}
			//else{
				 //patchOps+="NO NODE: \n";

			//}
		}
		if(finSize>0){
			//System.err.println("size: " + finSize);
			//System.err.println("hypsize: " + hypSize);
			retSize = finSize/(double)hypSize;	
			//System.err.println("retSize: " + retSize);
		}
		/*
		if(retSize==0 || retSize==1){
			System.out.println("###" + retSize);
			System.out.println("t:" + super.iFeature.t.toString());
			System.out.println("t~:" + super.iFeature.hypothesis.toString());
			System.out.println("s:" + super.iFeature.s.toString());
			System.out.println("s':" + super.iFeature.sPrime.toString());
			//System.out.println("patchops:" + patchOps);
		}
		*/
		return String.valueOf(retSize);
	}

}
