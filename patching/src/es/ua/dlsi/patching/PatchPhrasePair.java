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

package es.ua.dlsi.patching;
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

/**
 * This class is a class that will hold double alignment pairs
 * @author John Evan Ortega
 * @version 0.1
 */
public class PatchPhrasePair{
   public Pair<Integer,Integer> foreignPair;
   public Pair<Integer,Integer> englishPair;
   public PatchPhrasePair(int eBegin,int eEnd,int fBegin,int fEnd){
      foreignPair = new Pair<Integer,Integer>(fBegin,fEnd);
      englishPair = new Pair<Integer,Integer>(eBegin,eEnd);
   }
   int[] getFEPairsAsSet(){
	return new int[]{foreignPair.getFirst(),foreignPair.getSecond(),englishPair.getFirst(),englishPair.getSecond()};
   }
   int[] getEFPairsAsSet(){
	return new int[]{englishPair.getFirst(),englishPair.getSecond(), foreignPair.getFirst() ,foreignPair.getSecond()};
   }
   @Override
   public boolean equals(Object object)
   {
       boolean sameSame = false;

       if (object != null && object instanceof PatchPhrasePair)
       {
           sameSame = 
        		   
        		   (
        				   ((PatchPhrasePair) object).englishPair.getFirst() == this.englishPair.getFirst() &&
        				   ((PatchPhrasePair) object).englishPair.getSecond()== this.englishPair.getSecond() &&
        				   ((PatchPhrasePair) object).foreignPair.getFirst() == this.foreignPair.getFirst() &&
        				   ((PatchPhrasePair) object).foreignPair.getSecond() == this.foreignPair.getSecond()
        		   )
        		   ;
       }

       return sameSame;
   }
}
