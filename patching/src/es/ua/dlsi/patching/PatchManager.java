/**
 * 
 */
package es.ua.dlsi.patching;
import java.util.List;
import java.util.ArrayList;
import es.ua.dlsi.segmentation.*;
import es.ua.dlsi.utils.Pair;
import java.lang.StringBuilder;
/**
 * @author johneortega This is a class that was created to reduce memory
 *         consumption most importantly, it holds the original sentence and out
 *         word list as a global variable. Also, it will handle patches
 *
 */
public class PatchManager {
	public List<PatchOperator> origOps;
	public int counter = 0;
	public Segment origSent;
	public Segment theGoldSegment;
	public int bestPatchSize;
	public int bestEditDistance;
        public int bestOpSize;
        public int bestReplSize;// the size of segment to be replaces
	public String toPrint;
	public boolean DO_INTERMEDIATE = false;

	public PatchManager(){
		try{
			DO_INTERMEDIATE = (boolean)Class.forName("es.ua.dlsi.tests.statystics.RunGlobalScenarioOne").getDeclaredField("DO_INTERMEDIATE").get(null); 
		}
		catch(Exception e){
			System.err.println("could not retrieve boolean from RunGlobalScenarioOne" + e.getMessage());
		}
	}
	public void addPatch(List<PatchOperator> origOps, Segment origSent) {
		this.origOps = origOps;
		this.origSent = origSent;
		// Patch.printOps(origOps);
		PatchOperator currOp = origOps.get(0);
		Patch tempOpsYes = new Patch(origSent); // a base holder where each
												// branch will branch to its own
												// list
		Patch tempOpsNo = new Patch(origSent); // a base holder where each
		
												// branch will branch to its own
		//long startTime1 = System.currentTimeMillis();
		// create a Patch to handle the search
		addOps(currOp, 0, tempOpsYes, true, false);// isyes=true, isno=false
		//long endTime1 = System.currentTimeMillis();
		//long startTime2 = System.currentTimeMillis();
		addOps(currOp, 0, tempOpsNo, false, true);
		//long endTime2 = System.currentTimeMillis();
		// System.err.println("Total execution time: " + (endTime1-startTime1) +
		// "ms for addop 1 " + "and " + (endTime2-startTime2) +
		// "ms for addop 2 " + origOps.size() + " patchOps entering addOps " +
		// counter+ " times");
	}

	public void addOps(PatchOperator currOp, int currOpPos, Patch tempOpsNew,
			boolean isYesNode, boolean isNoNode) {
		counter++;
		PatchOperator lastPossOrigOp = origOps.get(origOps.size() - 1);
		int currSecondPos = currOp.getOrigPos().getSecond();
		int origSecondPos = lastPossOrigOp.getOrigPos().getSecond();
		/*
		 * System.err.println(
		 * 
		 * " patch id: "+ tempOpsNew.id + " patchopid: "+ currOp.offset +
		 * " lastPossOrigOp id:" + lastPossOrigOp.offset + " begin: " +
		 * currOp.getOrigPos().getFirst() + " end: " +
		 * currOp.getOrigPos().getSecond() + " currOpPos: " + currOpPos +
		 * " origOps.size()-1: " + (origOps.size()-1) + " currSecondPos: " +
		 * currSecondPos + " origSecondPos: " + origSecondPos + " words: " +
		 * currOp.getWords().toString());
		 */
		tempOpsNew.analyze(currOp, isYesNode, origOps);
		if (tempOpsNew.aborted) {
			tempOpsNew = null;
			return;
		}
		// orig pos
		if (currOp.equals(lastPossOrigOp)) {// note: operator equality only uses
											// orig pos
			int ed = Segment.EditDistance(new Segment(tempOpsNew.outWordList,
					false), theGoldSegment, null);
			// we want the patch with the best ED
			if (ed < bestEditDistance) {
				bestEditDistance = ed;
				bestPatchSize = tempOpsNew.outWordList.size();
        			bestOpSize = currOp.getWords().size();
				// we add one here becasue the offset is 0 -> offset
        			bestReplSize = (currOp.getOrigPos().getSecond() - currOp.getOrigPos().getFirst())+1;
				if(bestReplSize<0)
					bestReplSize=0;
				StringBuilder wordss = new StringBuilder();
				for(Pair<Integer,Word> word : tempOpsNew.outWordList){
					wordss.append(word.getSecond().getValue()+" ");
				}
				// uncomment this for the orig impl
				//if(!DO_INTERMEDIATE)
                    // marco turchi
					//System.err.println("Best Patch: " + wordss.toString());
					//System.err.println(wordss.toString());
			}
			if(DO_INTERMEDIATE){
				/// --- BEGIN USED FOR FEATURES ONLY -- 
				StringBuilder wordss = new StringBuilder();
				for(Pair<Integer,Word> word : tempOpsNew.outWordList){
					wordss.append(word.getSecond().getValue()+" ");
				}
				StringBuilder finalWords =  new StringBuilder();
				finalWords.append(tempOpsNew.getFeatureOps(origOps));
				finalWords.append("\t");
				finalWords.append(toPrint);
				finalWords.append("\t");
				finalWords.append(wordss);
				System.err.println(finalWords.toString());
				/// --- END USED FOR FEATURES ONLY -- 
			}
			tempOpsNew = null;
			return;
		} else if (isYesNode && currOpPos == (origOps.size() - 1)) {
			int ed = Segment.EditDistance(new Segment(tempOpsNew.outWordList,
					false), theGoldSegment, null);
			// we want the patch with the best ED
			if (ed < bestEditDistance) {
				bestEditDistance = ed;
				bestPatchSize = tempOpsNew.outWordList.size();
        			bestOpSize = currOp.getWords().size();
				// we add one here becasue the offset is 0 -> offset
        			bestReplSize = (currOp.getOrigPos().getSecond() - currOp.getOrigPos().getFirst())+1;
				if(bestReplSize<0)
					bestReplSize=0;
				StringBuilder wordss = new StringBuilder();
				for(Pair<Integer,Word> word : tempOpsNew.outWordList){
					wordss.append(word.getSecond().getValue()+" ");
				}
				// uncomment this for the orig impl
				//if(!DO_INTERMEDIATE)
					//System.err.println("Best Patch: " + wordss.toString());
                    // marco turchi
					//System.err.println(wordss.toString());
			}
			if(DO_INTERMEDIATE){
				/// --- BEGIN USED FOR FEATURES ONLY -- 
				StringBuilder wordss = new StringBuilder();
				for(Pair<Integer,Word> word : tempOpsNew.outWordList){
					wordss.append(word.getSecond().getValue()+" ");
				}
				StringBuilder finalWords =  new StringBuilder();
				finalWords.append(tempOpsNew.getFeatureOps(origOps));
				finalWords.append("\t");
				finalWords.append(toPrint);
				finalWords.append("\t");
				finalWords.append(wordss);
				System.err.println(finalWords.toString());
				/// --- END USED FOR FEATURES ONLY -- 
			}
			tempOpsNew = null;
			return;
		} else if (isNoNode && currOpPos == (origOps.size() - 1)) {
			int ed = Segment.EditDistance(new Segment(tempOpsNew.outWordList,
					false), theGoldSegment, null);
			// we want the patch with the best ED
			if (ed < bestEditDistance) {
				bestEditDistance = ed;
				bestPatchSize = tempOpsNew.outWordList.size();
        			bestOpSize = currOp.getWords().size();
				// we add one here becasue the offset is 0 -> offset
        			bestReplSize = (currOp.getOrigPos().getSecond() - currOp.getOrigPos().getFirst())+1;
				if(bestReplSize<0)
					bestReplSize=0;
				StringBuilder wordss = new StringBuilder();
				for(Pair<Integer,Word> word : tempOpsNew.outWordList){
					wordss.append(word.getSecond().getValue()+" ");
				}
				// uncomment this for the orig impl
				//if(!DO_INTERMEDIATE)
					//System.err.println("Best Patch: " + wordss.toString());
                    //remove marco turchi
					//System.err.println(wordss.toString());
			}
			if(DO_INTERMEDIATE){
				/// --- BEGIN USED FOR FEATURES ONLY -- 
				StringBuilder wordss = new StringBuilder();
				for(Pair<Integer,Word> word : tempOpsNew.outWordList){
					wordss.append(word.getSecond().getValue()+" ");
				}
				StringBuilder finalWords =  new StringBuilder();
				finalWords.append(tempOpsNew.getFeatureOps(origOps));
				finalWords.append("\t");
				finalWords.append(toPrint);
				finalWords.append("\t");
				finalWords.append(wordss);
				System.err.println(finalWords.toString());
				/// --- END USED FOR FEATURES ONLY -- 
			}
			tempOpsNew = null;
			return;
		}
		// this is the last node because it's a "Yes and covers end"
		else if (isYesNode && currSecondPos >= origSecondPos) {
			int ed = Segment.EditDistance(new Segment(tempOpsNew.outWordList,
					false), theGoldSegment, null);
			// we want the patch with the best ED
			if (ed < bestEditDistance) {
				bestEditDistance = ed;
				bestPatchSize = tempOpsNew.outWordList.size();
        			bestOpSize = currOp.getWords().size();
				// we add one here becasue the offset is 0 -> offset
        			bestReplSize = (currOp.getOrigPos().getSecond() - currOp.getOrigPos().getFirst())+1;
				if(bestReplSize<0)
					bestReplSize=0;
				StringBuilder wordss = new StringBuilder();
				for(Pair<Integer,Word> word : tempOpsNew.outWordList){
					wordss.append(word.getSecond().getValue()+" ");
				}
				// uncomment this for the orig impl
				//if(!DO_INTERMEDIATE)
					//System.err.println("Best Patch: " + wordss.toString());
                    // remove marco turchi
					//System.err.println(wordss.toString());
			}
			if(DO_INTERMEDIATE){
				/// --- BEGIN USED FOR FEATURES ONLY -- 
				StringBuilder wordss = new StringBuilder();
				for(Pair<Integer,Word> word : tempOpsNew.outWordList){
					wordss.append(word.getSecond().getValue()+" ");
				}
				StringBuilder finalWords =  new StringBuilder();
				finalWords.append(tempOpsNew.getFeatureOps(origOps));
				finalWords.append("\t");
				finalWords.append(toPrint);
				finalWords.append("\t");
				finalWords.append(wordss);
				System.err.println(finalWords.toString());
				/// --- END USED FOR FEATURES ONLY -- 
			}
			tempOpsNew = null;
			return;
		} else {
			// clone the next operator setting it's yes value to true
			Patch tempOpsNewYes = new Patch(tempOpsNew.outWordList, origSent);
			tempOpsNewYes.opIds.addAll(tempOpsNew.opIds);
			Patch tempOpsNewNo = new Patch(tempOpsNew.outWordList, origSent);// create
			tempOpsNewNo.opIds.addAll(tempOpsNew.opIds);
			tempOpsNew = null;
			PatchOperator nextOp = origOps.get(currOpPos + 1);
			// make sure to delete the old patch since you are creating two new
			// ones
			addOps(nextOp, currOpPos + 1, tempOpsNewYes, true, false);
			addOps(nextOp, currOpPos + 1, tempOpsNewNo, false, true);
		}
	}
	// method used often to get phrase pairs
	public List<PatchPhrasePair> getAllPhrasePairs(Segment bestTUSourceSegment, Segment theSPrimeSegment, 
			List<Pair<Integer,Integer>> bestTalignment, boolean SIGMA_MAX_MIN, int SIGMA_MAX, int SIGMA_MIN) {
	
			        //int nGrams = 3;
				List<PatchPhrasePair> phrasePairs = new ArrayList<PatchPhrasePair>();
				// there's actually various patch situations here
				// english sentence is Sx, foreign S'
				PatchPhraseExtraction pe = new PatchPhraseExtraction(bestTUSourceSegment,theSPrimeSegment,bestTalignment);
				//for (int sxBegin=0; sxBegin < bestTUSourceSegment.getSentence().size();sxBegin++) {
					//int sxEnd = sxBegin + (nGrams-1);
					//if( sxEnd >= bestTUSourceSegment.getSentence().size())
				int sxBegin=0;
				int sxEnd = bestTUSourceSegment.getSentence().size()-1;
				// the idea here is to get all the phrase pairs but avoid duplicates
				List<PatchPhrasePair> tmpPhrasePairs = null;
				if(SIGMA_MAX_MIN)
				   tmpPhrasePairs = pe.extractPhrasePairsWithMaxMin(sxBegin,sxEnd, SIGMA_MAX,SIGMA_MIN);
				else
				   tmpPhrasePairs = pe.extractPhrasePairs(sxBegin,sxEnd);
				if(tmpPhrasePairs!=null){
					for(PatchPhrasePair phrasePair : tmpPhrasePairs){
						int engFirst  = phrasePair.englishPair.getFirst();
						int engSecond = phrasePair.englishPair.getSecond();
						int forFirst  = phrasePair.foreignPair.getFirst();
						int forSecond = phrasePair.foreignPair.getSecond();
						// english sentence is Sx, foreign S'-theSprimSegment
    						boolean sameWords = bestTUSourceSegment.wordsEqual(engFirst, engSecond, theSPrimeSegment, forFirst,forSecond);
						if(! phrasePairs.contains(phrasePair) && !sameWords){
							//System.err.println("Adding new phrasepair: " + phrasePair.englishPair.getFirst() + "-" + phrasePair.englishPair.getSecond() + "-" + phrasePair.foreignPair.getFirst()+ "-" +phrasePair.foreignPair.getSecond());
							phrasePairs.add(phrasePair);
						}
					}
					
				}
				return phrasePairs;
	}

}
