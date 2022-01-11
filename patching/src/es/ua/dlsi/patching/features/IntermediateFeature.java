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

/**
 * Series of Glassbox features from QE with Felipe and Mikel
 * https://www.sharelatex.com/project/54182d9e6a2be06c2ee1386c
 * this is the main feature class that initialize the class with the various 
 * operators
 * this is a pojo class to hold the necessaries
 * which has all of the code and is called from a Test
 * @author John Evan Ortega
 * @version 0.1
 */
public class IntermediateFeature{
	private int id				= 0;
	public int sentid                       = 0;
	public Segment s 			= null;
	public Segment sPrime			= null;
	public Segment t 			= null;
	public Segment tPrime			= null;
	public Segment hypothesis      		= null;
	public List<PatchOperator> operators	= new ArrayList<PatchOperator>(); 
	
	public IntermediateFeature(String mainString, int id){
		this.id = id;

        //System.out.println(mainString);
		// first create the patching operators	
		String[] splitString = mainString.split("\\t");
		String allOps = splitString[0];
        // this is a hack but saves time
        allOps = allOps.replaceAll("====","===");
		String stringOps[] = allOps.split("===");
		for(String op : stringOps){
			if(op.length()>0){
				String patchSplit[] = op.split(">>>>>");
				String patchInfo  = patchSplit[0];
				String patchWords = "";
				if(patchSplit.length>1){
					patchWords = patchSplit[1];
				}
				String patchTell[]  = patchInfo.split("_");
				if(patchTell.length>5){
					String patchOffset= patchTell[0];
					String patchIsYes = patchTell[1];
					String patchStart = patchTell[2];
					String patchEnd   = patchTell[3];
					String sPatchStart = patchTell[4];
					String sPatchEnd   = patchTell[5];
					String sPrimePatchStart = patchTell[6];
					String sPrimePatchEnd   = patchTell[7];
					Integer begin	  = Integer.valueOf(patchStart);	
					Integer end	  = Integer.valueOf(patchEnd);	
					Integer sBegin	  = Integer.valueOf(sPatchStart);	
					Integer sEnd	  = Integer.valueOf(sPatchEnd);	
					Integer sPrimeBegin	  = Integer.valueOf(sPrimePatchStart);	
					Integer sPrimeEnd	  = Integer.valueOf(sPrimePatchEnd);	
					Pair<Integer,Integer> indexes = new Pair<Integer,Integer>(begin,end);
					Pair<Integer,Integer> sIndexes = new Pair<Integer,Integer>(sBegin,sEnd);
					Pair<Integer,Integer> sPrimeIndexes = new Pair<Integer,Integer>(sPrimeBegin,sPrimeEnd);
					Integer isYesNode = Integer.valueOf(patchIsYes);
					
                    /*
					System.err.println(patchWords);
					System.err.println(patchOffset);
					System.err.println(patchIsYes);
					System.err.println(patchStart);
					System.err.println(patchEnd);
					System.err.println(indexes);
                    */
					PatchOperator newOp = new PatchOperator(indexes, new Segment (patchWords));
					if(isYesNode>0)
						newOp.setYesNode(true);
					else
						newOp.setNoNode(true);
					newOp.setOffset(Integer.valueOf(patchOffset));
					newOp.setOrigSPos(sIndexes);
					newOp.setOrigSPrimePos(sPrimeIndexes);
					//System.err.println(newOp);
					operators.add(newOp);
				}
				
			}
			
			
		}
	
		if(splitString.length>5){
			sentid     = Integer.parseInt(splitString[1]);
			sPrime     = new Segment(splitString[2]);
			tPrime     = new Segment(splitString[3]);
			s	    = new Segment(splitString[4]);
			t 	    = new Segment(splitString[5]);
			hypothesis  = new Segment(splitString[6]);
		}
	}
	public int hashCode()
	{
		return id;
	}
	public boolean equals(Object o){
		if(o == null)        
		   return false;
		if(!(o instanceof IntermediateFeature)) 
		   return false;
		IntermediateFeature other = (IntermediateFeature) o;
		return (other.id==this.id);
	}
	public static void main(String[] args){
		String test ="0_1_0_0_4_4>>>>>[ 1===7_0_3_3_6_6>>>>>1 ]===4_1_4_6_4_6>>>>>[ 1 ]===\tfsaf";
		IntermediateFeature ifeat = new IntermediateFeature(test,1);

	}

}
