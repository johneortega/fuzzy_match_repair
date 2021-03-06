/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shef.mt.features.impl.doclevel;

import java.util.ArrayList;
import shef.mt.features.impl.DocLevelFeature;
import shef.mt.features.util.Doc;
import shef.mt.features.util.Sentence;

/**
 *
 * Source document perplexity without end of sentence markers
 * 
 * @author Carolina Scarton
 */
public class DocLevelFeature1011 extends DocLevelFeature {
    public DocLevelFeature1011() {
        this.setIndex(1011);
        this.setDescription("source documents perplexity without end of sentence markers");
        this.addResource("source.lm");
        
    }

    @Override
    public void run(Doc source, Doc target) {
        ArrayList<Sentence> sentences = source.getSentences();
        double doc_log_prob = 0.0;
        for(int i=0; i<sentences.size();i++){
            
            doc_log_prob+=(float) sentences.get(i).getValue("ppl1");
        }
        setValue((float) doc_log_prob/sentences.size());
    }

    @Override
    public void run(Sentence source, Sentence target) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
