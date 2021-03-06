/**
 *
 */
package shef.mt.features.impl.gb;

import shef.mt.features.util.Sentence;
import shef.mt.features.impl.Feature;
import java.util.*;

/**
 * using n-best for training LM: sentence 2-gram log-probability
 *
 * @author cat
 *
 */
public class Feature2007 extends Feature {

    public Feature2007() {
        setIndex("2007");
        setDescription("using n-best for training LM: sentence 2-gram log-probability");
        this.addResource("moses.xml");
    }

    /* (non-Javadoc)
     * @see wlv.mt.features.impl.Feature#run(wlv.mt.features.util.Sentence, wlv.mt.features.util.Sentence)
     */
    @Override
    public void run(Sentence source, Sentence target) {
        // TODO Auto-generated method stub
        float value = (Float) source.getValue("2_nb_logprob");
        setValue(value);
    }
}
