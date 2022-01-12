package shef.mt.tools;

import shef.mt.features.util.Sentence;
import shef.mt.features.util.Doc;
import java.io.*;


/**
 * The FMRProcessor class analyses a file produced by a FMR run and sets
 * the best FMR Sentence object These values are, currently, the best FMR from various patched setnences 
 *
 */
public class FMRProcessor extends ResourceProcessor{

    BufferedReader br;
    int sentCount;
    String line = null;
    String error = "0";
    String words = "0";
    String fmr = "0";
    /**
     * initialise a POSProcessor from an input file The POSProcessor expect an
     * input file in a fixed format, where each line is of the type:<br> <i>word
     * DT	word</i> (tokens separated by tab) <br>
     *
     *
     * @param input the input file
     *
     */
    public FMRProcessor(String input) {
        try {
            System.out.println("INPUT TO FMRPROCESSOR:" + input);
            br = new BufferedReader(new FileReader(input));
//			bwXPos = new BufferedWriter(new FileWriter(input+getXPOS()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        sentCount = 0;
    }

    @Override
    public void processNextSentence(Sentence sent) {
	try{
		error = "0";
		words = "0";
		fmr = "0";
		line = br.readLine();
		if(line!=null && !line.trim().isEmpty()){
			String[] split = line.split(" ");
			error = split[0];
			words = split[1];
			fmr   = split[2];
			sent.setValue("error", error);
			sent.setValue("words", words);
			sent.setValue("fmr", fmr);
		}
	}
	catch(Exception e){
		System.out.println("Could not process sentence" + sent.getIndex());
	}
	sentCount++;
    }
    @Override
    public void processNextDocument(Doc source) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
