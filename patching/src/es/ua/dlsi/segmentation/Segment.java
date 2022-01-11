/*
 * Copyright (C) 2011 Universitat d'Alacant
 *
 * author: Miquel Esplà Gomis
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

package es.ua.dlsi.segmentation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import es.ua.dlsi.utils.Pair;

/**
 * This class represents a sentence (or segment) as a list of words. It implements
 * a set of methods to handle the comparison between segments and sub-segments.
 * @author Miquel Esplà Gomis
 * @version 0.9
 */
public class Segment implements Serializable{

    /** List of words which conform a sentence */
    protected List<Word> sentence;

    /**
     * Length of the sentence in words
     * @return Returns the length of the sentence
     */
    public int size(){
        return sentence.size();
    }

    /**
     * Overloaded constructor of the Segment object
     * @param sentence String wich contains the sentence from which the object
     * will be created
     */
    public Segment(String sentence){
        this.sentence=new LinkedList<Word>();
        String[] words=sentence.toLowerCase().split("[ \\t]");
        for(int i=0;i<words.length;i++){
            if(!words[i].trim().equals("")){
                this.sentence.add(new Word(words[i].trim()));
            }
        }
    }

    /**
     * Overloaded constructor of the Segment object
     * @param sentence String wich contains the sentence from which the object
     * will be created
     */
    public Segment(String sentence, boolean keep_capitals){
        this.sentence=new LinkedList<Word>();
        String[] words;
        if(keep_capitals)
            words=sentence.split("[ \\t]");
        else
            words=sentence.toLowerCase().split("[ \\t]");
        for(int i=0;i<words.length;i++){
            if(!words[i].trim().equals("")){
                this.sentence.add(new Word(words[i].trim()));
            }
        }
    }

    /**
     * Overloaded constructor of the Segment object
     * @param sentence List of words from which the object will be created
     */
    public Segment(List<Word> sentence){
        this.sentence=new LinkedList<Word>();
        for(Word s: sentence){
            this.sentence.add(s);
        }
    }
    /**
     * Overloaded constructor of the Segment object
     * @param sentence List of words from which the object will be created
     */
    public Segment(List<Pair<Integer,Word>> sentence, boolean fakevalue){
        this.sentence=new LinkedList<Word>();
        for(Pair<Integer,Word> s: sentence){
            this.sentence.add(s.getSecond());
        }
    }
    /**
     * Overloaded constructor of the Segment object
     * @param s Segment from which the new one is copied
     */
    public Segment(Segment s){
        this.sentence=s.sentence;
    }

    /**
     * Method which returns a word in the sentence which is placed in a given position
     * @param index Position of the word which will be returned in the sentence
     * @return Returns a word in the sentence which is placed in a given position
     */
    public Word getWord(int index){
        if(index<0 || index>=sentence.size())
            return null;
        else
            return sentence.get(index);
    }
    public void setWord(int index, Word newWord){
        if(index > 0 && index<=sentence.size())
            this.sentence.set(index, newWord);
    }

    /**
     * Method which returns the sentence as a list of words
     * @return Returns the sentence as a list of words
     */
    public List<Word> getSentence() {
        return sentence;
    }


    /**
     * Method which returns the sentence as a list of words
     * @return Returns the sentence as a list of words
     */
    public List<Integer> getSentenceCodes() {
        List<Integer> exit=new LinkedList<Integer>();
        for(Word w: sentence)
            exit.add(w.getCode());
        return exit;
    }


    /**
     * Method wich compares a given segment with the sentence. If the segment is
     * contained by the sentence, it returns the index of the coincidence (the
     * first word wich is coincident between the segment and the sentence)
     * @param seg Segment which will be compared with the sentence
     * @return Returns the index of the coincidence (the
     * first word wich is coincident between the segment and the sentence)
     */
    public Set<Integer> Appears(Segment seg){
        Set<Integer> exit=new LinkedHashSet<Integer>();
        int i;

        if(this.sentence.size()>0 && seg.size()>0){
            for(i=0;i<=seg.size()-sentence.size();i++){
                if(sentence.equals(seg.sentence.subList(i, i+sentence.size()))){
                    exit.add(i);
                }
            }
            if(exit.isEmpty())
                return null;
            else
                return exit;
        }
        else
            return null;
    }

    public List<Pair<Integer,Integer>> AppearsNew(Segment seg){
        List<Pair<Integer,Integer>> exit=new ArrayList<Pair<Integer,Integer>>();
        if(this.sentence.size()>0 && seg.size()>0){
            for(int i=0;i<sentence.size();i++){
		// break if passed the end
		if((i+seg.sentence.size())>this.sentence.size()){
		    break;
		}
		/*
		System.out.println("Segment Sentence Size" + seg.sentence.size());
		for(int j=0; j<seg.size();j++){
		    System.out.println("Seg word: " + seg.getWord(j).getValue().toString()+" ");
		}
		System.out.println("====>>>>> ");
		List<Word> subSegs = sentence.subList(i, i+seg.sentence.size());
		for(Word word : subSegs){
		    System.out.println("This Sentences SubSeg word: " + word.getValue().toString()+" ");
		}
		for(Word word : seg.sentence){
		    System.out.println("This Segment passed in word is: " + word.getValue().toString()+" ");
		}
		System.out.println("<<<<<====>>>>> ");
		*/
                if(seg.sentence.equals(sentence.subList(i, i+seg.sentence.size()))){
		    //System.out.println("Segment match with: " + i + " and " + (i+seg.sentence.size()));
		    // something weird here gave us one more than needed, must subtract one
   		    // from the end
		    Pair<Integer,Integer> match = new Pair<Integer,Integer>(i, i+seg.sentence.size()-1);
                    exit.add(match);
                }
            }
        }
        return exit;
    }
    public void replaceSubSegment(Segment replace, int startPosition){
	// replace words in current segment
	// starting at current position
	for(Word replWord : replace.sentence){
	   setWord(startPosition, replWord);
	   startPosition++;
	}
    }


    /**
     * Checks if the sentence contains a given subsentence. An empty Segment will not be considered a subsequence of any sentence
     * @param sentence Subsentence candidate to be contained in the sentence
     * @return Returns <code>true</code> if the sentence contains the subsentencen and <code>false</code> in other case
     */
    /*public boolean isSubSentence(Segment sentence){
        if(MatchesSegment(sentence)>-1)
            return true;
        else
            return false;
    }*/
    /**
     * Method which segments a sentence in all the possible segments of a given length
     * @param len_segs Length of the segments which must be generated
     * @return Returns a HashMap containing all the generated segments and the corresponding SubSegment
     */
    public List<SubSegment> SubsegmentSentence(int len_segs){
        int i;
        List<SubSegment> exit = new LinkedList<SubSegment>();

        for(i=0;i<sentence.size()-len_segs+1;i++){
            exit.add(new SubSegment(sentence.subList(i, i+len_segs),i, len_segs));
        }
        return exit;
    }

    /**
     * Method which segments a sentence in all the possible segments of a given length
     * @param len_segs Length of the segments which must be generated
     * @return Returns a HashMap containing all the generated segments and the corresponding SubSegment
     */
    public Set<Segment> SegmentSentence(int len_segs){
        int i;
        Set<Segment> exit = new LinkedHashSet<Segment>();
        for(i=0;i<sentence.size()-len_segs+1;i++){
            Segment ss=new Segment(sentence.subList(i, i+len_segs));
            exit.add(ss);
        }
        return exit;
    }

    /**
     * Method which segments a sentence in all the possible segments with a given maximum length
     * @param max_size The maximum length wich can take the generated segments
     * @return Returns a HashMap containing all the generated segments and the corresponding SubSegment
     */
    public List<SubSegment> AllSubSegmentsInSentence(int max_size){
        int i,size;
        List<SubSegment> exit = new LinkedList<SubSegment>();

        for(size=1;size<=sentence.size() && size<=max_size;size++){
            for(i=0;i<=sentence.size()-size;i++){
                exit.add(new SubSegment(sentence.subList(i, i+size),i, size));
            }
        }
        return exit;
    }

    /**
     * Method which segments a sentence in all the possible segments with a given maximum length
     * @param max_size The maximum length wich can take the generated segments
     * @return Returns a HashMap containing all the generated segments and the corresponding SubSegment
     */
    public Set<Segment> AllSegmentsInSentence(int max_size){
        int i,size;
        Set<Segment> exit = new LinkedHashSet<Segment>();

        for(size=1;size<=sentence.size() && size<=max_size;size++){
            for(i=0;i<=sentence.size()-size;i++){
                Segment ss=new Segment(sentence.subList(i, i+size));
                exit.add(new Segment(ss.getSentence()));
            }
        }
        return exit;
    }

    /**
     * Method which checks if a sentence contains a given word
     * @param word Word which will be searched in the sentence
     * @return Returns <code>true</code> if the sentence contains the word and <code>false</code> in other case
     */
    /*public boolean contains(String word){
        return this.sentence.contains(word.toLowerCase());
    }*/

    /**
     * Method that compares the segment with another object.
     * @param o The object with which the segment will be compared
     * @return Returns <code>true</code> if the objects are equal and <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object o){
        boolean exit=true;

        if(o.getClass()!=Segment.class)
            exit=false;
        else{
            Segment s=(Segment)o;
            if(sentence.size()!=s.sentence.size())
                exit=false;
            else{
                for(int i=0;i<sentence.size() && exit;i++){
                    if(sentence.get(i).getCode()!=s.getWord(i).getCode()){
                        exit=false;
                        break;
                    }
                }
            }
        }
        return exit;
    }
    /**
     * Method that compares words from this segment to words from another segment.
     * @param thisStart The object with which the segment will be compared
     * @param thisEnd The object with which the segment will be compared
     * @param thatSegment The object with which the segment will be compared
     * @param thatStart The object with which the segment will be compared
     * @param thatEndo The object with which the segment will be compared
     * @return Returns <code>true</code> if the words in the Segment are equal and <code>false</code> otherwise.
     */
    public boolean wordsEqual(int thisStart, int thisEnd, Segment thatSegment,int thatStart, int thatEnd){
        boolean exit=true;
	try{
		if(this==null || thatSegment==null){
			exit=false;
		}
		else if((thisEnd-thisStart)!=(thatEnd-thatStart)){
			// not same size
			exit=false;

		}
		else{
			// if here, they are the same size
			int j = thatStart;
			for(int i=thisStart;i<=thisEnd;i++){
			    if(this.sentence.get(i).getCode()!=thatSegment.getWord(j).getCode()){
				exit=false;
				break;
			    }
			    j++;
			}
		}
	}
	catch(Exception e){
		exit=false;
	}
        return exit;
    }

   /**
    * Method that computes a hash code for an instance of the class.
    * @return Returns the computed hash code.
    */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.sentence != null ? this.sentence.hashCode() : 0);
        return hash;
    }

    /**
     * Method which converts the list of words into a String
     * @return Returns a String containing the sentence with the words separated by a space
     */
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        if(!this.sentence.isEmpty()){
            for(Word s: this.sentence){
                sb.append(s.getValue());
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    /**
     * Method wich returns the longest common subsequence of words contained by two sentences
     * @param sentence Segment with which the comparison will be performed
     * @return Returns the length of the longest comon subsequence of words
     */
    public int LongestCommonSubsequence(Segment sentence){
        int exit=0;

        //Set<Segment> sub_sentences=sentence.AllSegmentsInSentence(sentence.size()).keySet();
        Set<Segment> sub_sentences=sentence.AllSegmentsInSentence(sentence.size());
        for(Segment s : sub_sentences){
            //if(this.MatchesSegment(s)>=0 && s.sentence.size()>exit)
            if(this.AppearsNew(s).size()>0 && s.sentence.size()>exit)
                exit=s.sentence.size();
        }
        return exit;
    }

    private static int minimumInt(int a, int b, int c) {
      return Math.min(Math.min(a, b), c);
    }
/*.. this is the original Miquel ED just using alignment pairs
    static public int EditDistance(Segment seg1, Segment seg2,  List<Pair<Integer,Integer>> alignment){
	List<Integer> s1=seg1.getSentenceCodes();	
	List<Integer> s2=seg2.getSentenceCodes();	
	Integer maxval =null;

        int[][] d=new int[s1.size()+1][s2.size()+1];
        int i, j;
        
        //int maxi, maxj;
        if(maxval==null)
            maxval=s1.size()+s2.size();

        for(i=0; i<s1.size()+1 && i<=maxval;i++){
            d[i][0]=i;
        }

        for(i=0; i<s2.size()+1 && i<=maxval;i++){
            d[0][i]=i;
        }

        for(j=1;j<s2.size()+1;j++){
            int initi;
            int minval;
            if(j>maxval){
                initi=(j-maxval);
                if(s1.get(initi-1).equals(s2.get(j-1)))
                    d[initi][j]=d[initi-1][j-1];
                else{
                    if(d[initi-1][j-1]<d[initi][j-1])
                        d[initi][j]=d[initi-1][j-1]+1;
                    else
                        d[initi][j]=d[initi][j-1]+1;
                }
                minval=d[initi][j];
                initi++;
            }else{
                minval=Integer.MAX_VALUE;
                initi=1;
            }
            for(i=initi;i<s1.size()+1 && i<j+maxval;i++){
                if(s1.get(i-1).equals(s2.get(j-1)))
                    d[i][j]=d[i-1][j-1];
                else{
                    if(d[i-1][j]<d[i][j-1]){
                        if(d[i-1][j]<d[i-1][j-1])
                            d[i][j]=d[i-1][j]+1;
                        else
                            d[i][j]=d[i-1][j-1]+1;
                    }
                    else{
                        if(d[i][j-1]<d[i-1][j-1])
                            d[i][j]=d[i][j-1]+1;
                        else
                            d[i][j]=d[i-1][j-1]+1;
                    }
                }
                if(d[i][j]<minval)
                    minval=d[i][j];
            }
            if(i<s1.size()+1){
                if(s1.get(i-1).equals(s2.get(j-1)))
                    d[i][j]=d[i-1][j-1];
                else{
                    if(d[i-1][j-1]<d[i-1][j])
                        d[i][j]=d[i-1][j-1]+1;
                    else
                        d[i][j]=d[i-1][j]+1;
                }
                if(d[i][j]<minval)
                    minval=d[i][j];
            }
            if(minval>=maxval+1){
                return maxval+1;
            }
        }
		if(alignment!=null){
		    i=s1.size();
		    j=s2.size(); 
		    while(j>0){
			if(i==0){
			    j--;
			}
			else{
			    if(d[i-1][j-1]<=d[i-1][j]){
				if(d[i-1][j-1]<=d[i][j-1]){
				    if(s1.get(i-1).equals(s2.get(j-1)))
					alignment.add(new Pair<Integer,Integer>(i-1,j-1));
				    i--;
				    j--;
				}
				else{
				    /*if(s1.get(i-1).equals(s2.get(j-1)))
					alignment[j-1]=true;*/
/*
				    j--;
				}
			    }
			    else{
				if(d[i-1][j]<=d[i][j-1]){
				    //if(s1.get(i-1).equals(s2.get(j-1)))
					//alignment.add(new Pair<Integer,Integer>(i-1,j-1));
				    i--;
				}
				else{
				    /*if(s1.get(i-1).equals(s2.get(j-1)))
					alignment[j-1]=true;*/
/*
				    j--;
				}
			    }
			}
		    }
		}
        return d[s1.size()][s2.size()];
    }
*/
    /*
        new method by john to compute edit distance and fill 
        allignment arrays for two segments 11/20/2013
	on 02/19/2014 we decided that we needed non source aligned ones also
*/
    static public int EditDistance(Segment s1, Segment s2,  List<Pair<Integer,Integer>> alignment){
                int[][] distance = new int[s1.size() + 1][s2.size() + 1];
                for (int i = 0; i <= s1.size(); i++)
                        distance[i][0] = i;
                for (int j = 1; j <= s2.size(); j++)
                        distance[0][j] = j;
		// we have to use this because the equals may return true if the same word is side-by-side
		Set<Integer> initialJSet = new HashSet<Integer>();
                for (int i = 1; i <= s1.size(); i++)
                        for (int j = 1; j <= s2.size(); j++){
                                int areAligned = 0;
                                // System.out.println("This is I word:" + s1.getSentence().get(i - 1));
                                // System.out.println("This is J word:" + s2.getSentence().get(j - 1));
                                if(s1.getSentence().get(i - 1).equals(s2.getSentence().get(j - 1))){
                                //if(s1.getSentence().get(i - 1).equals(s2.getSentence().get(j - 1)) && !initialJSet.contains(j-1)){
                                        //alignment.add(new Pair<Integer,Integer>(i-1,j-1));// add the matched pair to the alignment
					areAligned=0;//hack for now
					//initialJSet.add(j-1);//add this to avoid alignment colistions
				}
                                else{
                                	areAligned=1; // add one to distance
				}
                                distance[i][j] = minimumInt(
                                                distance[i - 1][j] + 1,
                                                distance[i][j - 1] + 1,
                                                distance[i - 1][j - 1]+ (areAligned));
                        }
		       /// new code
/*
		       if(alignment!=null){
			    int i=s1.size();
			    int j=s2.size();
			    // create a set of positions for j that have already been used
		            Set<Integer> secondJSet = new HashSet<Integer>();
			    while(j>0 && i>0){
				//if(s1.getSentence().get(i-1).equals(s2.getSentence().get(j-1)) && !secondJSet.contains(j-1)){
				if(s1.getSentence().get(i-1).equals(s2.getSentence().get(j-1))){
				    alignment.add(new Pair<Integer,Integer>(i-1,j-1));
				    //secondJSet.add(j-1);
				}
				if(distance[i-1][j-1] <= distance[i-1][j]){
				    if(distance[i-1][j-1] <= distance[i][j-1]){
					i--;
				    }
			            j--;
                                }
				else{
				    if(distance[i-1][j] <= distance[i][j-1]){
					i--;
				    }
				    else{
					j--;
				    }
				}
			    }
		       }
*/
		if(alignment!=null){
		    int i=s1.size();
		    int j=s2.size(); 
		    while(j>0){
			if(i==0){
			    j--;
			}
			else{
			    if(distance[i-1][j-1]<=distance[i-1][j]){
				if(distance[i-1][j-1]<=distance[i][j-1]){
				    if(s1.getSentence().get(i-1).equals(s2.getSentence().get(j-1)))
					alignment.add(new Pair<Integer,Integer>(i-1,j-1));
				    i--;
				    j--;
				}
				else{
				    j--;
				}
			    }
			    else{
				if(distance[i-1][j]<=distance[i][j-1]){
				    //if(s1.getSentence().get(i-1).equals(s2.getSentence().get(j-1)))
				//	alignment.add(new Pair<Integer,Integer>(i-1,j-1));
				    i--;
				}
				else{
				    j--;
				}
			    }
			}
		    }
		}
                return distance[s1.size()][s2.size()];    
    } 
    /*
        new method by john to compute edit distance and fill 
        allignment arrays for two segments 11/20/2013
	on 02/19/2014 we decided that we needed non source aligned ones also
    static public int EditDistance(Segment s1, Segment s2,  List<Pair<Integer,Integer>> alignment){
                int[][] distance = new int[s1.size() + 1][s2.size() + 1];
		Map<Pair<Integer,Integer>,Pair<Integer,Integer>> distancePairs = new HashMap<Pair<Integer,Integer>,Pair<Integer,Integer>>();
                for (int i = 0; i <= s1.size(); i++)
                        distance[i][0] = i;
                for (int j = 1; j <= s2.size(); j++)
                        distance[0][j] = j;
                for (int i = 1; i <= s1.size(); i++)
                        for (int j = 1; j <= s2.size(); j++){
                                int areAligned = 0;
				int imin = 0;
				int jmin = 0;
                                //System.out.println("This is I word:" + s1.getSentence().get(i - 1));
                                //System.out.println("This is J word:" + s2.getSentence().get(j - 1));
                                if(s1.getSentence().get(i - 1).equals(s2.getSentence().get(j - 1)))
                                        //alignment.add(new Pair<Integer,Integer>(i-1,j-1));// add the matched pair to the alignment
					areAligned=0;//hack for now
                                else
                                	areAligned=1; // add one to distance
				int mindist=minimumInt(distance[i - 1][j] + 1, distance[i][j - 1] + 1,distance[i - 1][j - 1]+ (areAligned));
                                if (distance[i-1][j] + 1 < mindist) {
					imin 	= i-1; 
					jmin 	= j;
				}
                                else if(distance[i][j-1] + 1 < mindist) {
					imin 	= i; 
					jmin 	= j-1;
				}
                                else{
					imin 	= i-1; 
					jmin 	= j-1;
				}
				Pair<Integer,Integer> newPairSet=new Pair<Integer,Integer>(i,j);
				Pair<Integer,Integer> newMinPair=new Pair<Integer,Integer>(imin,jmin);
                                distancePairs.put(newPairSet,newMinPair); 
				distance[i][j]=mindist;
			}
		       if(alignment!=null){
				/*
				int itrack=s1.size();
				int jtrack=s2.size();
				while(itrack!=0 && jtrack!=0){
					Pair<Integer,Integer> iPair = distancePairs.get(new Pair(itrack,jtrack));
					System.out.println("We've entered the hmap line =================>>>" );
					
					System.out.println("We've entered the hmap line with Segment 1" + s1.toString() );
					System.out.println("We've entered the hmap line with Segment 2" + s2.toString() );
					System.out.println("We've entered the hmap line with Alignment(" + iPair.getFirst() + ", " + iPair.getSecond());
					alignment.add(iPair);
					System.out.println("We've entered the hmap line =================<<<");
					itrack--;
					jtrack--;
					
				}
			    int i=s1.size();
			    int j=s2.size();
			    while(j>0 && i>0){
				//Pair<Integer,Integer> iPair = distancePairs.get(new Pair(i,j));
				//alignment.add(iPair);
				if(distance[i-1][j-1] < distance[i-1][j]){
				    if(distance[i-1][j-1] < distance[i][j-1]){
					i--;
				    }
			            j--;
                                }
				else{
				    if(distance[i-1][j] < distance[i][j-1]){
					i--;
				    }
				    else{
					j--;
				    }
				}
			    }
		       }

                return distance[s1.size()][s2.size()];    
    } 
    */
    /**
     * Method that computes the Levenstein's edit distance for a pair of sentences.
     * @param s1 First sentence to compare.
     * @param s2 Second sentence to compare.
     * @return Returns the value of the edit distance computed.
     */
    static public int EditDistance(List<Integer> s1, List<Integer> s2, boolean[] alignment, Integer maxval, boolean debug){
        int[][] d=new int[s1.size()+1][s2.size()+1];
        int i, j;
        
        //int maxi, maxj;
        if(maxval==null)
            maxval=s1.size()+s2.size();

        for(i=0; i<s1.size()+1 && i<=maxval;i++){
            d[i][0]=i;
        }

        for(i=0; i<s2.size()+1 && i<=maxval;i++){
            d[0][i]=i;
        }

        for(j=1;j<s2.size()+1;j++){
            int initi;
            int minval;
            if(j>maxval){
                initi=(j-maxval);
                if(s1.get(initi-1).equals(s2.get(j-1)))
                    d[initi][j]=d[initi-1][j-1];
                else{
                    if(d[initi-1][j-1]<d[initi][j-1])
                        d[initi][j]=d[initi-1][j-1]+1;
                    else
                        d[initi][j]=d[initi][j-1]+1;
                }
                minval=d[initi][j];
                initi++;
            }else{
                minval=Integer.MAX_VALUE;
                initi=1;
            }
            for(i=initi;i<s1.size()+1 && i<j+maxval;i++){
                if(s1.get(i-1).equals(s2.get(j-1)))
                    d[i][j]=d[i-1][j-1];
                else{
                    if(d[i-1][j]<d[i][j-1]){
                        if(d[i-1][j]<d[i-1][j-1])
                            d[i][j]=d[i-1][j]+1;
                        else
                            d[i][j]=d[i-1][j-1]+1;
                    }
                    else{
                        if(d[i][j-1]<d[i-1][j-1])
                            d[i][j]=d[i][j-1]+1;
                        else
                            d[i][j]=d[i-1][j-1]+1;
                    }
                }
                if(d[i][j]<minval)
                    minval=d[i][j];
            }
            if(i<s1.size()+1){
                if(s1.get(i-1).equals(s2.get(j-1)))
                    d[i][j]=d[i-1][j-1];
                else{
                    if(d[i-1][j-1]<d[i-1][j])
                        d[i][j]=d[i-1][j-1]+1;
                    else
                        d[i][j]=d[i-1][j]+1;
                }
                if(d[i][j]<minval)
                    minval=d[i][j];
            }
            if(minval>=maxval+1){
                if(debug){
                    for(j=0;j<s2.size()+1;j++){
                        for(i=0;i<s1.size();i++){
                            System.out.print(d[i][j]);
                            System.out.print("\t");
                        }
                        System.out.println(d[i][j]);

                    }
                }
                return maxval+1;
            }
        }
        if(alignment!=null){
            Arrays.fill(alignment, false);
            i=s1.size();
            j=s2.size(); 
            while(j>0){
                if(i==0){
                    alignment[j-1]=false;
                    j--;
                }
                else{
                    if(d[i-1][j-1]<=d[i-1][j]){
                        if(d[i-1][j-1]<=d[i][j-1]){
                            if(s1.get(i-1).equals(s2.get(j-1)))
                                alignment[j-1]=true;
                            i--;
                            j--;
                        }
                        else{
                            /*if(s1.get(i-1).equals(s2.get(j-1)))
                                alignment[j-1]=true;*/
                            j--;
                        }
                    }
                    else{
                        if(d[i-1][j]<=d[i][j-1]){
                            if(s1.get(i-1).equals(s2.get(j-1)))
                                alignment[j-1]=true;
                            i--;
                        }
                        else{
                            /*if(s1.get(i-1).equals(s2.get(j-1)))
                                alignment[j-1]=true;*/
                            j--;
                        }
                    }
                }
            }
        }
        if(debug){
            for(j=0;j<s2.size()+1;j++){
                for(i=0;i<s1.size();i++){
                    System.out.print(d[i][j]);
                    System.out.print("\t");
                }
                System.out.println(d[i][j]);
            }
            System.out.println("THE EDIT DIS SCORE IS:"+ d[s1.size()][s2.size()]);    
        }
        return d[s1.size()][s2.size()];
    }

    /**
     * Method that computes the Levenstein's edit distance for a pair of sentences.
     * @param s1 First sentence to compare.
     * @param s2 Second sentence to compare.
     * @return Returns the value of the edit distance computed.
     */
    static public int EditDistance(List<Integer> s1, List<Integer> s2, boolean[] alignment, boolean debug){
        return EditDistance(s1, s2, alignment, null, debug);
    }

    private static short minimum(int a, int b, int c) {
        return (short) Math.min(a, Math.min(b, c));
    }

    static public int OmegaTEditDistance(List<Integer> s, List<Integer> t) {
        int n = s.size(); // length of s
        int m = t.size(); // length of t
        final int MAX_N = 1000;
        short[] d = new short[MAX_N + 1];
        short[] p = new short[MAX_N + 1];

        if (n == 0)
            return m;
        else if (m == 0)
            return n;

        if (n > MAX_N)
            n = MAX_N;
        if (m > MAX_N)
            m = MAX_N;

        short[] swap; // placeholder to assist in swapping p and d

        // indexes into strings s and t
        short i; // iterates through s
        short j; // iterates through t

        Integer t_j = null; // jth object of t

        short cost; // cost

        for (i = 0; i <= n; i++)
            p[i] = i;

        for (j = 1; j <= m; j++) {
            t_j = t.get(j - 1);
            d[0] = j;

            Integer s_i = null; // ith object of s
            for (i = 1; i <= n; i++) {
                s_i = s.get(i - 1);
                cost = s_i.equals(t_j) ? (short) 0 : (short) 1;
                // minimum of cell to the left+1, to the top+1, diagonally left
                // and up +cost
                d[i] = minimum(d[i - 1] + 1, p[i] + 1, p[i - 1] + cost);
            }

            // copy current distance counts to 'previous row' distance counts
            swap = p;
            p = d;
            d = swap;
        }

        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return p[n];
    }

    /*static public int OmegaTEditDistance(List<Integer> s, List<Integer> t, boolean[] alignment) {
        int n = s.size(); // length of s
        int m = t.size(); // length of t
        short[][] table=new short[m+1][n+1];
        short[] d = null;
        short[] p = null;

        if (n == 0)
            return m;
        else if (m == 0)
            return n;

        short[] swap; // placeholder to assist in swapping p and d

        // indexes into strings s and t
        short i; // iterates through s
        short j; // iterates through t

        Integer t_j = null; // jth object of t

        short cost; // cost

        p=table[0];
        for (i = 0; i <= n; i++)
            p[i] = i;

        for (j = 1; j <= m; j++) {
            d=table[j];
            t_j = t.get(j - 1);
            d[0] = j;

            Integer s_i = null; // ith object of s
            for (i = 1; i <= n; i++) {
                s_i = s.get(i - 1);
                cost = s_i.equals(t_j) ? (short) 0 : (short) 1;
                // minimum of cell to the left+1, to the top+1, diagonally left
                // and up +cost
                d[i] = minimum(d[i - 1] + 1, p[i] + 1, p[i - 1] + cost);
            }

            // copy current distance counts to 'previous row' distance counts
            p = d;
        }

        if(alignment!=null){
            i=(short)s.size();
            j=(short)t.size();
            if(s.get(i-1).equals(t.get(j-1)))
                alignment[j-1]=true;
            else
                alignment[j-1]=false;
            while(j>1){
                if(table[j-1][i-1]<table[j-1][i]){
                    if(table[j-1][i-1]<table[j][i-1]){
                        if(s.get(i-1).equals(t.get(j-1)))
                            alignment[j-1]=true;
                        else
                            alignment[j-1]=false;
                        i--;
                        j--;
                    }
                    else{
                        i--;
                    }
                }
                else{
                    if(table[j-1][i]<table[j][i-1]){
                        if(s.get(i-1).equals(t.get(j-1))){
                            alignment[j-1]=true;
                        }
                        else
                            alignment[j-1]=false;
                        j--;
                    }
                    else{
                        i--;
                    }
                }
            }
        }

        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return p[n];
    }*/

    public void RefreshWordCodes(){
        for(Word w: this.sentence)
            w.RefreshCode();
    }
    
    public void AddPOSInfo(List<String> poscathegories){
        
    }
}
