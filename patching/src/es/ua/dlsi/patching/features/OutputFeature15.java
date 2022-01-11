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
 * Glass box feature 9, feature 15 in count
 * Mean target context per patching operator
 * @author John Evan Ortega
 * @version 0.1
 */
public class OutputFeature15 extends OutputFeature{
	public OutputFeature15(){}
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
		int totalLCS		= 0;
		int totalMin		= 0;
		long startTime = System.currentTimeMillis();
		long endTime   = System.currentTimeMillis();
		//String patchOps = "";
		for(PatchOperator patchOp : super.iFeature.operators){
			String patchOpStr 	= patchOp.getWords().toString();
			int patchOpSize		= patchOp.getWords().size();
    			int patchBegin		= patchOp.getOrigPos().getFirst();
    			int patchEnd		= patchOp.getOrigPos().getSecond();
			List<Word> tWords	= new ArrayList<Word>();
			// get the list of words from t
			for(int i=patchBegin;i<=patchEnd;i++){
				tWords.add(super.iFeature.t.getWord(i));
			}
			Segment tSubSegment = new Segment(tWords);
			Segment tauPrimeSubSegment = new Segment(patchOp.getWords());
			if(patchOp.isYesNode()){
				//startTime = System.currentTimeMillis();
				if(tSubSegment!=null && tauPrimeSubSegment!=null){
					//patchOps+="t sub: " + tSubSegment.toString() + "\n";
					//patchOps+="t' sub: " + tauPrimeSubSegment.toString() + "\n";
					//lcsLen = getLCS(tSubSegment.toString(),tauPrimeSubSegment.toString());
					int lcsLen1 = tSubSegment.LongestCommonSubsequence(tauPrimeSubSegment);
					int lcsLen2 = tauPrimeSubSegment.LongestCommonSubsequence(tSubSegment);
					int lcsLen = Math.max(lcsLen1,lcsLen2);
					totalLCS+=lcsLen;
					int minLen = Math.min(tSubSegment.size(),tauPrimeSubSegment.size());
					totalMin+=minLen;
					//patchOps+="lcLen: " + lcsLen + "\n";
					//patchOps+="minLen: " + minLen + "\n";
				}
			}
		}
		if(totalLCS > 0 && totalMin > 0){
			ret = totalLCS/(double)totalMin;
		}
		/*
		if(ret==1||ret==0){
			System.out.println("###" + ret);
			System.out.println("t:" + super.iFeature.t.toString());
			System.out.println("t~:" + super.iFeature.hypothesis.toString());
			System.out.println("s:" + super.iFeature.s.toString());
			System.out.println("s':" + super.iFeature.sPrime.toString());
			System.out.println("patch ops:\n" + patchOps);
		}
		*/
		return String.valueOf(ret);
	}
	public static int getLCS(String a, String b){
		int m = a.length();
		int n = b.length();
    		if (m == 0 || n == 0) return 0; 
		int[][] dp = new int[m+1][n+1];
		for(int i=0; i<=m; i++){
			for(int j=0; j<=n; j++){
				if(i==0 || j==0){
					dp[i][j]=0;
				}else if(a.charAt(i-1)==b.charAt(j-1)){
					dp[i][j] = 1 + dp[i-1][j-1];
				}else{
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
				}
			}
		}
	 
		return dp[m][n];
	}

}
