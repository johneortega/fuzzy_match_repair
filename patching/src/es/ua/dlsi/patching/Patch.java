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
 * This class is a class that will hold operators for repairing sentences via
 * translations.
 * 
 * @author John Evan Ortega
 * @version 0.1
 */
public class Patch {

	/** The original sentence **/
	protected Segment origSent;

	/** an internal word list pair created from segment **/
	protected List<Pair<Integer,Word>> outWordList;
	
	/** The patch won't make it **/
	public boolean aborted = false;

	/** A list of patch op ids for this Patch, second integer is 1 for is yesnode **/
	public List<Pair<Integer,Integer>> opIds = new ArrayList<Pair<Integer,Integer>>();

	// this constructor would normally be called from the outside world
	// for the first recursion once we are inside our code
	public Patch(Segment origSent) {
		this.origSent = origSent;
		this.outWordList = getWordListFromSegment(origSent);// this contains
															// original position
															// list
															// when a new word
															// is added it will
															// be of type -1
	}

	// this is for consecutive recursion calls that have data in
	// them for the ops, we do need new objects for ops and outword list though
	public Patch(List<Pair<Integer,Word>> outWordList,
			Segment origSent) {
		this.origSent = origSent;
		this.outWordList = outWordList;
	}
	public List<Pair<Integer,Word>> getOutWordList() {
		return outWordList;
	}

	public void setOutWordList(List<Pair<Integer,Word>> outWordList) {
		this.outWordList = outWordList;
	}
	// this will print out the ops for this patch
	// in a clean way for the feature file
	// it takes the original patch locations and gets the information using
	// the patch operator offset
	public String getFeatureOps(List<PatchOperator> origOps){
		StringBuilder ret = new StringBuilder();
		for(Pair<Integer,Integer> opIdPair : opIds){
			Integer opId 		= opIdPair.getFirst();
			Integer isYesNode 	= opIdPair.getSecond();
			for(PatchOperator currOp : origOps){
				if(currOp.offset == opId){
					StringBuilder patchPrint = new StringBuilder();
					patchPrint.append(currOp.offset);	
					patchPrint.append("_");	
					patchPrint.append(isYesNode);	
					patchPrint.append("_");	
					patchPrint.append(currOp.getOrigPos().getFirst());	
					patchPrint.append("_");	
					patchPrint.append(currOp.getOrigPos().getSecond());	
					patchPrint.append("_");	
					patchPrint.append(currOp.getOrigSPos().getFirst());	
					patchPrint.append("_");	
					patchPrint.append(currOp.getOrigSPos().getSecond());	
					patchPrint.append("_");	
					patchPrint.append(currOp.getOrigSPrimePos().getFirst());	
					patchPrint.append("_");	
					patchPrint.append(currOp.getOrigSPrimePos().getSecond());	
					patchPrint.append(">>>>>");	
					patchPrint.append(currOp.getWords().toString());
					patchPrint.append("===");
					ret.append(patchPrint.toString());
				}
			}

		}
		return ret.toString();
	}
	public static void printOps(List<PatchOperator> patches) {
		System.err
				.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.err.println("PRINTING OPERATOR PATCH LIST");
		System.err
				.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		int i = 0;
		for (PatchOperator patchop : patches) {
			if (patchop.isYesNode())
				System.err.println("^^^ yes op ^^^");
			if (patchop.isNoNode())
				System.err.println("^^^ no op ^^^");
			System.err.println("Patch Operator Number: == " + i
					+ " == in the patches list" + "with id: " + patchop.offset);
			System.err.println("Original Patch Start Position: "
					+ patchop.getOrigPos().getFirst());
			System.err.println("Original Patch End Position: "
					+ patchop.getOrigPos().getSecond());
			System.err.println("Words: "
					+ patchop.getWords().toString());
			// System.err.println("New Patch Start Position: "+
			// patch.getNewPos().getFirst());
			// System.err.println("New Patch End Position: "+
			// patch.getNewPos().getSecond());
			i++;
		}

	}
	public static void printOp(PatchOperator patchop) {
		System.err
				.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.err.println("PRINTING SINGLE PATCH DETAILS");
		System.err
				.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		if(patchop!=null){
			if (patchop.isYesNode())
				System.err.println("^^^ yes op ^^^");
			if (patchop.isNoNode())
				System.err.println("^^^ no op ^^^");
			System.err.println("Patch Operator with id: " + patchop.offset);
			System.err.println("Original Patch Start Position: "
					+ patchop.getOrigPos().getFirst());
			System.err.println("Original Patch End Position: "
					+ patchop.getOrigPos().getSecond());
			// System.err.println("New Patch Start Position: "+
			// patch.getNewPos().getFirst());
			// System.err.println("New Patch End Position: "+
			// patch.getNewPos().getSecond());
		
		}

	}

	// sorts by end then beginning using the latest position
	public static void order(List<BooleanPatchOperator> patches, boolean yes) {
		Collections.sort(patches, new Comparator() {
			public int compare(Object o1, Object o2) {
				int x1 = ((BooleanPatchOperator) o1).patchOperator.getOrigPos()
						.getSecond();
				int x2 = ((BooleanPatchOperator) o2).patchOperator.getOrigPos()
						.getSecond();
				if (x1 != x2) {
					return x1 - x2;
				} else {
					int y1 = ((BooleanPatchOperator) o1).patchOperator
							.getOrigPos().getFirst();
					int y2 = ((BooleanPatchOperator) o2).patchOperator
							.getOrigPos().getFirst();
					return y2 - y1;
				}
			}
		});
	}

	// sorts by end then beginning using the latest position
	public static void order(List<PatchOperator> patches) {
		Collections.sort(patches, new Comparator() {
			public int compare(Object o1, Object o2) {
				int x1 = ((PatchOperator) o1).getOrigPos().getSecond();
				int x2 = ((PatchOperator) o2).getOrigPos().getSecond();
				if (x1 != x2) {
					return x1 - x2;
				} else {
					int y1 = ((PatchOperator) o1).getOrigPos().getFirst();
					int y2 = ((PatchOperator) o2).getOrigPos().getFirst();
					return y2 - y1;
				}
			}
		});
	}

	private static List<Pair<Integer,Word>> getWordListFromSegment(Segment segment) {
		int posCounter = 0;
		List<Pair<Integer,Word>> retList = new ArrayList<Pair<Integer,Word>>();
		for (Word word : segment.getSentence()) {
			retList.add(new Pair<Integer,Word>(posCounter,word));
			posCounter++;
		}
		return retList;
	}
	// from the restriction added in the paper
	// this needs to fail if already covered
	// checks positions in S
	private boolean isSourceCovered(PatchOperator newOp, List<PatchOperator> origOps){
		int newSFirst  = newOp.getOrigSPos().getFirst();
		int newSSec    = newOp.getOrigSPos().getSecond();
		// check current opIds for sPost
		for(Pair<Integer,Integer> opIdPair : opIds){
			Integer opId 		= opIdPair.getFirst();
			Integer isYesNode 	= opIdPair.getSecond();
			for(PatchOperator currOp : origOps){
				if(currOp.offset == opId && isYesNode == 1){
					int currSFirst = currOp.getOrigSPos().getFirst();	
					int currSSec   = currOp.getOrigSPos().getSecond();	
					/*
					System.err.println("This is the curr.offset:" + currOp.offset);
					System.err.println("This is the new S First:" + newSFirst);
					System.err.println("This is the current S First:" + currSFirst);
					System.err.println("This is the new S Sec:" + newSSec);
					System.err.println("This is the current S Sec:" + currSSec);
					*/
					if(overlap(currSFirst,currSSec,newSFirst,newSSec)){
						//System.err.println("<<< true >>>");
						return true;
					}
				}
			}

		}
		int newSPrimeFirst  = newOp.getOrigSPrimePos().getFirst();
		int newSPrimeSec    = newOp.getOrigSPrimePos().getSecond();
		// check current opIds for sPost
		for(Pair<Integer,Integer> opIdPair : opIds){
			Integer opId 		= opIdPair.getFirst();
			Integer isYesNode 	= opIdPair.getSecond();
			for(PatchOperator currOp : origOps){
				if(currOp.offset == opId && isYesNode == 1){
					int currSPrimeFirst = currOp.getOrigSPrimePos().getFirst();	
					int currSPrimeSec   = currOp.getOrigSPrimePos().getSecond();	
					/*
					System.err.println("This is the curr.offset:" + currOp.offset);
					System.err.println("This is the new First:" + newSPrimeFirst);
					System.err.println("This is the current First:" + currSPrimeFirst);
					System.err.println("This is the new Sec:" + newSPrimeSec);
					System.err.println("This is the current Sec:" + currSPrimeSec);
					*/
					if(overlap(currSPrimeFirst,currSPrimeSec,newSPrimeFirst,newSPrimeSec)){
						//System.err.println("<<< true >>>");
						return true;
					}
				}
			}

		}
		return false;
	}
	// tempOpsParam is the branch as it grows
	// currOp is the current patch operator to possibly be added to to the
	// branch
	public void analyze(PatchOperator currOp, boolean isYesNode, List<PatchOperator> origOps) {
		Segment tempOrigSent = this.origSent;
		Segment currPatchSegment = currOp.getWords();
		boolean tempAborted = false;
		if (isYesNode) {
			/*
			System.err.println("Printint out Major word list");
			for(Pair<Integer,Word> word : outWordList){
				System.err.print(word.getSecond().getValue()+" ");
			}
			*/
			//System.err.println("is yes node");
			// returns true if s is already covered by another op
			tempAborted=isSourceCovered(currOp, origOps);

			//System.err.println("source covered?: " + String.valueOf(tempAborted));
			
			
                        // find the position that it belongs to
			int currFirstPosOrig = currOp.getOrigPos().getFirst();
			int currSecondPosOrig = currOp.getOrigPos()
					.getSecond();
			
			//System.err.println("currFirstPosOrig: " + String.valueOf(currFirstPosOrig));
			//System.err.println("currSecondPosOrig: " + String.valueOf(currSecondPosOrig));

			// int currFirstPosNew = currOp.getNewPos().getFirst();
			// int currSecondPosNew = currOp.getNewPos().getSecond();
			// we need to check if the original position range
			// has an X (-1 type) in it and can't be overwritten
			boolean beginHit = false;
			int hitCtr = 0;
			for (Pair<Integer,Word> pair : outWordList) {
				int pairPos = pair.getFirst();
				if (beginHit) {
					++hitCtr;
					// this is to check for cases that have a patch removed
					// and can't be patched 5 6 7 8 is now 5 6 8, 7 can't be
					// patched
					if ((hitCtr + currFirstPosOrig) != pairPos) {
						tempAborted = true;
						break;
					}
				}
				if (pairPos == currFirstPosOrig) {
					beginHit = true;
				}// we need to make sure that all positions for the new patch
					// exist
					// to be replaced, do that by starting a counter here
				if (beginHit && pairPos == -1) {// make sure contigous no -1
					tempAborted = true;
					break;
				}
				if (pairPos == currSecondPosOrig)
					break;// we have hit the contigous end
			}
			// if beginHit false, we never saw the contigous patch
			if (!beginHit)
				tempAborted = true;
			// System.err.println("tempAborted is:" + tempAborted);
			if (!tempAborted) {
			        //System.err.println("temp aborted");
				// System.err.println("This is the original sententce");
				// get the alignment to see which words will be inserted
				// this words will be marked -1 in the list
				// if you've mad it here it's because you have a contigous place
				// to patch
				// without a x(-1) marked
				// the +1 here is because of sublist politics
				List<Word> subListWords = tempOrigSent.getSentence().subList(
						currFirstPosOrig, currSecondPosOrig + 1);
				int size = currPatchSegment.size();
				boolean[] talignment = new boolean[size];
				Arrays.fill(talignment, false);
				Segment.EditDistance(
						new Segment(subListWords).getSentenceCodes(),
						currPatchSegment.getSentenceCodes(), talignment, false);// set
																				// to
																				// true
																				// for
																				// debug
				// remove from the modif list the original words
				// in order to replace with the new ones
				// remember, here you should have a contigous patch
				boolean afterWordBegin = false;
				List<Pair<Integer,Word>>tempOutWordList = new ArrayList<Pair<Integer,Word>>();
				for (Pair<Integer,Word> outWord : outWordList) {
					int outWordPos = outWord.getFirst();
					if (outWordPos > currSecondPosOrig) {
						afterWordBegin = true;
					}
					// / remember -1 is less then
					if (outWordPos < currFirstPosOrig && !afterWordBegin)
						tempOutWordList.add(outWord);
				}
				 //System.err.println("This is the replacement patch");
				 //for(Word word : currPatchSegment.getSentence()){
				 //System.err.print(word.getValue() + " ");
				 //}
				 //System.err.println("");
				// clear out outWordList put the beggining
				//outWordList = new ArrayList<Integer>(beforeWords);
				// System.err.println(":------------- ALIGNMENT FOR PATCH --------------:");
				// we will start from the original start position
				// / using the i below as the word in the patch
				int newPatchCtr = currFirstPosOrig;	
				for (int i = 0; i < size; i++) {
					 //System.err.print(currPatchSegment.getWord(i).getValue());
					 //System.err.print(":");
					 //System.err.println(talignment[i]);
					 //System.err.println("newPatchCtr: " + newPatchCtr);
					boolean isAligned = talignment[i];
					Word newWord = new Word(currPatchSegment.getWord(i).getValue());
					if (!isAligned) {
						// this is the word that is not aligned
						// this word(s) have to go back into the list correctly
						// with a -1
						tempOutWordList.add(new Pair<Integer,Word>(-1,newWord));
					} else {				

						tempOutWordList.add(new Pair<Integer,Word>(newPatchCtr,newWord));
						newPatchCtr++;// this is it's own counter
					}
				}
				afterWordBegin=false;
				for (Pair<Integer,Word> outWord : outWordList) {
					int outWordPos = outWord.getFirst();
					if (outWordPos > currSecondPosOrig) {
						afterWordBegin = true;
					}
					if (afterWordBegin)
						tempOutWordList.add(outWord);
				}
				/*
				System.err.println("Printint out word list");
				for(Pair<Integer,Word> word : tempOutWordList){
					System.err.print(word.getSecond().getValue()+" ");
				}
				*/

				setOutWordList(tempOutWordList);
			}// end tempAborted
		}// end if yes node
		aborted = tempAborted;
		if(!aborted){
			int isYesInt = (isYesNode) ? 1 : 0;
			Pair<Integer,Integer> currOpObj = new Pair(currOp.offset,isYesInt); 
			opIds.add(currOpObj);
		}
		
	}
	private static boolean overlap(int x1, int x2, int y1, int y2){
		int lower = Math.max(x1, y1);
		int upper = Math.min(x2, y2);
		if(lower <= upper)
  			return true;
		return false;
	}

}
