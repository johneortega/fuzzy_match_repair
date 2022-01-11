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
import es.ua.dlsi.utils.Pair;
import es.ua.dlsi.segmentation.*;

/**
 * This class is a class that will hold operators
 * for repairing sentences via translations.
 * @author John Evan Ortega
 * @version 0.1
 */
public class PatchOperator{

    /** The original position in t that this node was created with */
    protected Pair<Integer,Integer> origPos;
    /** The original position in s that this node was created with */
    protected Pair<Integer,Integer> origSPos;
    /** The original position in s prime that this node was created with */
    protected Pair<Integer,Integer> origSPrimePos;

    /** The new positions that this node has be converted too, should equal the new offset change*/
    //protected Pair<Integer,Integer> newPos;
    protected int offset = 0;// default is 0
    protected Segment words;
    protected Segment tuSourceSegment;
    protected Segment sprimeSegment;
    protected boolean yesNode = false;
    protected boolean noNode = false;
    protected boolean searched = false;
    protected boolean useTrans = false;
    protected String name;

    //public PatchOperator(Pair<Integer,Integer> origPos, Pair<Integer,Integer> newPos, Segment words){
    public PatchOperator(Pair<Integer,Integer> origPos, Segment words){
        this.origPos = origPos;// this is normally for a simple first node
        //this.newPos = newPos;// this is normally for a simple first node
        this.words  = words;// this is normally for a simple first node
    }
    // allow the ability to create a new operator from another
//    public PatchOperator getNewPatchOperator(PatchOperator newOp, boolean yesNode, boolean noNode){
////	Pair<Integer,Integer> origPos =
////	   new Pair(newOp.getOrigPos().getFirst(), newOp.getOrigPos().getSecond());
////	Pair<Integer,Integer> origSPos =
////	   new Pair(newOp.getOrigSPos().getFirst(), newOp.getOrigSPos().getSecond());
////	//Pair<Integer,Integer> newPos =
////	   //new Pair(newOp.getNewPos().getFirst(), newOp.getNewPos().getSecond());
////	StringBuilder stringWords = new StringBuilder();
////	for(Word word : newOp.getWords().getSentence()){
////	   stringWords.append(word.getValue().toString()+" ");
////	}
////	Segment words = new Segment(stringWords.toString());
////	int offset = newOp.getOffset();
////	//PatchOperator op = new PatchOperator(origPos,newPos,words);
////	PatchOperator op = new PatchOperator(origPos,words);
////	op.setOrigSPos(origSPos);
////	if(yesNode)
////	   op.setYesNode(true);
////	if(noNode)
////	   op.setNoNode(true);
////        if(tuSourceSegment!=null)
////	   op.setTuSourceSegment(tuSourceSegment);
////        if(sprimeSegment!=null)
////	   op.setSprimeSegment(sprimeSegment);
////        if(name!=null)
////	   op.setName(name);
//	return op;
//    }
    public void printWords(){
	if(words!=null){
	   for(Word word : words.getSentence()){
		System.out.print(word.getValue().toString() + " ");
	   }
 	   System.out.println();
	}
    }
    public Segment getWords(){
	return words;
    }
    public Segment getTuSourceSegment(){
	return tuSourceSegment;
    }
    public void setTuSourceSegment(Segment tuSourceSegment){
	this.tuSourceSegment=tuSourceSegment;
    }
    public Segment getSprimeSegment(){
	return sprimeSegment;
    }
    public void setSprimeSegment(Segment sprimeSegment){
	this.sprimeSegment=sprimeSegment;
    }
    //public void setNewPos(Pair<Integer,Integer> newPos){
//	this.newPos = newPos;
 //   }
//    public Pair<Integer,Integer> getNewPos(){
//	return newPos;
 //   }
    public void setOrigPos(Pair<Integer,Integer> origPos){
	this.origPos = origPos;
    }
    public Pair<Integer,Integer> getOrigPos(){
	return origPos;
    }
    public void setOrigSPos(Pair<Integer,Integer> origSPos){
	this.origSPos = origSPos;
    }
    public void setOrigSPrimePos(Pair<Integer,Integer> origSPrimePos){
	this.origSPrimePos = origSPrimePos;
    }
    public Pair<Integer,Integer> getOrigSPos(){
	return origSPos;
    }
    public Pair<Integer,Integer> getOrigSPrimePos(){
	return origSPrimePos;
    }
    public void setSearched(boolean searched){
	this.searched = searched;
    }
    public void useTrans(boolean useTrans){
	this.useTrans = useTrans;
    }
    public void setOffset(int offset){
	this.offset= offset;
    }
    public void setYesNode(boolean yesNode){
	this.yesNode = yesNode;
    }
    public void setNoNode(boolean noNode){
	this.noNode = noNode;
    }
    public boolean isYesNode(){
	return yesNode;
    }
    public boolean isNoNode(){
	return noNode;
    }
    public int getOffset(){
	return offset;
    }
    public String getName(){
	return name;
    }
    public void setName(String name){
	this.name=name;
    }
    public boolean equals(Object o){
        if(o == null)        
           return false;
	if(!(o instanceof PatchOperator)) 
           return false;
	PatchOperator other = (PatchOperator) o;
	
//        if(!other.origPos.equals(origPos))
//           return false;
/*
        if(!other.newPos.equals(newPos))
           return false;
        if(other.yesNode!=yesNode)
           return false;
        if(other.noNode!=noNode)
           return false;
        if(other.offset != offset)
           return false;
*/
        //System.out.println("To here is equal");
//        if(!other.words.equals(words))
//           return false;
	//return true;
	return (other.offset==this.offset);
    }
    public static boolean position_equals(PatchOperator po1, PatchOperator po2){
        if(po1 == null)        
           return false;
        if(po2 == null)        
	   return false;
	//System.err.println("This is original po1 begin position" + po1.getOrigPos().getFirst());
	//System.err.println("This is original po1 end position" + po1.getOrigPos().getSecond());
	//System.err.println("This is original po2 begin position" + po2.getOrigPos().getFirst());
	//System.err.println("This is original po2 end position" + po2.getOrigPos().getSecond());
	//System.err.println("po1 words:"+po1.words.toString());
	//System.err.println("po2 words:"+po2.words.toString());
        if(po2.getOrigPos().getFirst()!=po1.getOrigPos().getFirst())
           return false;
        if(po2.getOrigPos().getSecond()!=po1.getOrigPos().getSecond())
           return false;
        if(!po2.words.toString().equals(po1.words.toString()))
           return false;
	return true;
    }
    public static boolean list_contains(List<PatchOperator> ops, PatchOperator po){
	if(ops==null)
	   return false;
	for(PatchOperator op : ops){
	   if(position_equals(op,po))
		return true;
	}
	return false; 
    } 
    @Override public String toString() {
    	StringBuilder result = new StringBuilder();
    	String newLine = System.getProperty("line.separator");
	result.append("Patch Operator with ID: " + this.offset);
	result.append(newLine);
	result.append("original position in T: " + origPos.getFirst() + ", " + origPos.getSecond());
	result.append(newLine);
	result.append("original position in S: " + origSPos.getFirst() + ", " + origSPos.getSecond());
	result.append(newLine);
	result.append("original position in S': " + origSPrimePos.getFirst() + ", " + origSPrimePos.getSecond());
	result.append(newLine);
	String theWords = new String("");	
	if(words!=null){
	   for(Word word : words.getSentence()){
		theWords+=word.getValue().toString() + " ";
	   }
	}
	result.append("tau: " + theWords);
	result.append(newLine);
	if(this.yesNode)
		result.append("apply? YES");
	else
		result.append("apply? NO");
	result.append(newLine);
    	return result.toString();
   }
}
