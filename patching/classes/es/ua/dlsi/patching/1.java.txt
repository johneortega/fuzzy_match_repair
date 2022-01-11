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
import java.lang.Math;

/**
 * This class is a class that will hold operators
 * for repairing sentences via translations.
 * 
 * @author John Evan Ortega
 * @version 0.1
 */
public class PatchPhraseExtraction{
   
    /** A list of alignments */
    protected List<Pair<Integer,Integer>> alignments; 
    /** The english sentence normally Sx **/
    protected Segment engSent;
    /** The foreign sentence normally S' **/
    protected Segment forSent;
    public PatchPhraseExtraction(Segment engSent, Segment forSent, List<Pair<Integer,Integer>> alignments){
       this.alignments = alignments;
       this.engSent    = engSent;
       this.forSent    = forSent;
    }
    /** 
         list of extractions, we can do this from beginning to end or just a segment (ngram passed)
	 assumes that the english sentence is the Sx or ngram operated sentence
     **/
    public List<PatchPhrasePair> extractPhrasePairs(int startPos, int endPos){
       List<PatchPhrasePair> phrasePairs = new ArrayList<PatchPhrasePair>();
       
       // we assume that the start and end positions are 
       // ngrampositions
       if( endPos > (engSent.size()-1))
          endPos = engSent.size()-1;
       if(startPos < 0)
	  startPos = 0;
       int prevstart = startPos;
       for(int estart = startPos; estart <= endPos; estart++){
	  for(int eend = estart; eend <= endPos; eend++){
	     int fstart = forSent.size()-1;// the starting position is at end
	     int fend   = -1;
	     for(Pair<Integer,Integer> alignment : alignments){
	        int e = alignment.getFirst();
	        int f = alignment.getSecond();
	        if(e >= estart && e <= eend){
		   fstart = Math.min(f,fstart);
		   fend   = Math.max(f,fend);
		}
	     }
	     //System.out.println("Extracting with:" +  fstart + "," + fend + "," + estart + "," + eend);
	     List<PatchPhrasePair> tempPairs = extract(fstart,fend,estart,eend);
             if(tempPairs.size()>0){
                phrasePairs.addAll(tempPairs);
	     }
	  }
       }
       return phrasePairs;
    }    
    // extracts phrase pairs with max/min for the length of sigma
    public List<PatchPhrasePair> extractPhrasePairsWithMaxMin(int startPos, int endPos, int max, int min){
       List<PatchPhrasePair> phrasePairs = new ArrayList<PatchPhrasePair>();
       
       // we assume that the start and end positions are 
       // ngrampositions
       if( endPos > (engSent.size()-1))
          endPos = engSent.size()-1;
       if(startPos < 0)
	  startPos = 0;
       int prevstart = startPos;
       for(int estart = startPos; estart <= endPos; estart++){
	  for(int eend = estart; eend <= endPos; eend++){
	     int fstart = forSent.size()-1;// the starting position is at end
	     int fend   = -1;
	     for(Pair<Integer,Integer> alignment : alignments){
	        int e = alignment.getFirst();
	        int f = alignment.getSecond();
	        if(e >= estart && e <= eend){
		   fstart = Math.min(f,fstart);
		   fend   = Math.max(f,fend);
		}
	     }
	     //System.err.println("Extracting with:" +  fstart + "," + fend + "," + estart + "," + eend);
             if((fend-fstart>=min && fend-fstart<=max) && (eend-estart>=min && eend-estart<=max)){
	        List<PatchPhrasePair> tempPairs = extract(fstart,fend,estart,eend);
                if(tempPairs.size()>0){
                   phrasePairs.addAll(tempPairs);
	        }
             }
	  }
       }
       return phrasePairs;
    }    
    private List<PatchPhrasePair> extract(int fstart, int fend, int estart, int eend){
       List<PatchPhrasePair> pairs = new ArrayList<PatchPhrasePair>();
       if(fend==-1)
          return pairs;
       for(Pair<Integer,Integer> alignment : alignments){
	  int e = alignment.getFirst();
	  int f = alignment.getSecond();
          if ( (e < estart || e > eend) && (f > fstart && f < fend))  {
	     return pairs;
          }
       }
       int fs = fstart;
       boolean fsAligned = false;
       while(!fsAligned && fs>-1){
          boolean feAligned = false;
          int fe = fend;
          while(!feAligned && fe<forSent.size()){
	     //System.err.println("adding :" +  estart + "," + eend + "," + fs + "," + fe);
	     PatchPhrasePair pair = new PatchPhrasePair(estart,eend,fs,fe);
	     pairs.add(pair); 
             ++fe;
             feAligned = isAligned(alignments,fe);
          }
          --fs;
          fsAligned = isAligned(alignments,fs);
       }
       return pairs;
    } 
    private static boolean isAligned(List<Pair<Integer,Integer>> alignments, int falign){
       for(Pair<Integer,Integer> alignment : alignments){
	  int e = alignment.getFirst();
	  int f = alignment.getSecond();
          if(f==falign)
	    return true;
       }
       return false;
    }
    public static void main(String[] args){
/*
	Segment sPrime = new Segment("Until when the blue green dog has");
	Segment sX     = new Segment("And the red dog has");
*/
/*
	Segment sPrime = new Segment("see military goods controls for o-ethyl-2-diisopropylaminoethyl methyl phosphonite ( ql ) ( 57856-11-8 ) ;");
	Segment sX     = new Segment("see military goods controls for o-ethyl-2-diisopropylaminoethyl methyl phosphonite ( 57856-11-8 ) ;");
*/
	Segment sPrime = new Segment("designed for power inputs of 4 kw or less ;");
	Segment sX     = new Segment("designed for power inputs of 5 kw or more ;");
	List<Pair<Integer,Integer>> alignments = new ArrayList<Pair<Integer,Integer>>();
/*
        Pair<Integer,Integer> pair1   = new Pair<Integer,Integer>(1,2);
        Pair<Integer,Integer> pair2   = new Pair<Integer,Integer>(3,5);
        Pair<Integer,Integer> pair3   = new Pair<Integer,Integer>(4,6);
*/
/*
        Pair<Integer,Integer> pair1 = new Pair<Integer,Integer>(0,0);
        Pair<Integer,Integer> pair2 = new Pair<Integer,Integer>(1,1);
        Pair<Integer,Integer> pair3 = new Pair<Integer,Integer>(2,2);
        Pair<Integer,Integer> pair4 = new Pair<Integer,Integer>(3,3);
        Pair<Integer,Integer> pair5 = new Pair<Integer,Integer>(4,4);
        Pair<Integer,Integer> pair6 = new Pair<Integer,Integer>(5,5);
        Pair<Integer,Integer> pair7 = new Pair<Integer,Integer>(6,6);
        Pair<Integer,Integer> pair8  = new Pair<Integer,Integer>(7,7);
        Pair<Integer,Integer> pair9  = new Pair<Integer,Integer>(8,8);
        Pair<Integer,Integer> pair10  = new Pair<Integer,Integer>(9,12);
        Pair<Integer,Integer> pair11 = new Pair<Integer,Integer>(10,13);
        Pair<Integer,Integer> pair12 = new Pair<Integer,Integer>(11,14);
*/
        Pair<Integer,Integer> pair1 = new Pair<Integer,Integer>(0,0);
        Pair<Integer,Integer> pair2 = new Pair<Integer,Integer>(1,1);
        Pair<Integer,Integer> pair3 = new Pair<Integer,Integer>(2,2);
        Pair<Integer,Integer> pair4 = new Pair<Integer,Integer>(3,3);
        Pair<Integer,Integer> pair5 = new Pair<Integer,Integer>(4,4);
        Pair<Integer,Integer> pair6 = new Pair<Integer,Integer>(6,6);
        Pair<Integer,Integer> pair7 = new Pair<Integer,Integer>(7,7);
        Pair<Integer,Integer> pair8  = new Pair<Integer,Integer>(9,9);

	alignments.add(pair1);
	alignments.add(pair2);
	alignments.add(pair3);
	alignments.add(pair4);
	alignments.add(pair5);
	alignments.add(pair6);
	alignments.add(pair7);
	alignments.add(pair8);
/*
	alignments.add(pair9);
	alignments.add(pair10);
	alignments.add(pair11);
	alignments.add(pair12);
*/

        PatchPhraseExtraction pe =  new PatchPhraseExtraction(sX,sPrime,alignments);
	List<PatchPhrasePair> phrasePairs = pe.extractPhrasePairs(7,9);
	for(PatchPhrasePair phrase : phrasePairs){
	   int fstart  = phrase.foreignPair.getFirst();
	   int fend    = phrase.foreignPair.getSecond();
	   int estart  = phrase.englishPair.getFirst();
	   int eend    = phrase.englishPair.getSecond();
	   System.out.println(estart + "," + eend + "," + fstart + "," + fend);

        }
    }
}
