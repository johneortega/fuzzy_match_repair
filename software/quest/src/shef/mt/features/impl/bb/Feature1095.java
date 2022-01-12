/**
 *
 */
package shef.mt.features.impl.bb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import shef.mt.features.impl.Feature;
import shef.mt.features.util.Sentence;
import shef.mt.features.util.StopWord;



/**
 * Number of stopwords between in target sentence
 *
 * @author Luong Ngoc Quang
 *
 *
 */
public class Feature1095 extends Feature {

    //final static Float probThresh = 0.10f;

    public Feature1095() {
        setIndex(1095);
        setDescription("wer of best patched sentence");
        this.addResource("fmr.path");
    }

    /* (non-Javadoc)
     * @see wlv.mt.features.util.Feature#run(wlv.mt.features.util.Sentence, wlv.mt.features.util.Sentence)
     */
    @Override
    public void run(Sentence source, Sentence target) {
        // TODO Auto-generated method stub
	System.out.println(source.getValue("error")+" "+ source.getValue("words")+ " "+source.getValue("fmr"));
        setValue(source.getValue("fmr").toString());
    }
}
