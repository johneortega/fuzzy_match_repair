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
 * Using Machine Translation to Provide Target-Language
 * Edit Hints in Computer Aided Translation Based on
 * Translation Memories
 * return 1 if good
 * theory is that fms(t,t*) is <= fms(s,s') (+-5)
 * @version 0.1
 */
public class OutputFeature44 extends OutputFeature{
	public OutputFeature44(){}
	@Override
	public void setFeature(IntermediateFeature iFeature){
		if(iFeature!=null)
			super.iFeature = iFeature;
		else
			System.err.println("Intermediate Feature is null");
	}
	@Override
	public String process(){
		String patchOps = "";
		double ret = 0.0;
		int numYesPatchOps = 0;
		int numNoPatchOps = 0;
		// t* is the gold
        //System.err.println("This is the t: " + super.iFeature.t.toString());
        //System.err.println("This is the tPrime: " + super.iFeature.tPrime.toString());
		double tscore=TranslationMemory.GetScoreNew(super.iFeature.t, super.iFeature.tPrime, null)*100;
		double sscore=TranslationMemory.GetScoreNew(super.iFeature.s, super.iFeature.sPrime, null)*100;
		if(tscore >= sscore - 5)
			ret = 1.0;
		//else
	//		System.out.println("no good tscore" + tscore + " sscore" + sscore);
		return String.valueOf(ret);
	}

}
