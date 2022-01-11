/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ua.dlsi.translationmemory;

import es.ua.dlsi.segmentation.Segment;
import es.ua.dlsi.utils.CmdLineParser;
import java.util.Arrays;

/**
 *
 * @author miquel
 */
public class EditDistanceAlignment {

    public static void main(String[] args) {
        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option oseg1 = parser.addStringOption('1',"segment1");
        CmdLineParser.Option oseg2 = parser.addStringOption('2',"segment2");

        try{
            parser.parse(args);
        }
        catch(CmdLineParser.IllegalOptionValueException e){
            System.err.println(e);
            System.exit(-1);
        }
        catch(CmdLineParser.UnknownOptionException e){
            System.err.println(e);
            System.exit(-1);
        }

        String seg1=(String)parser.getOptionValue(oseg1,null);
        String seg2=(String)parser.getOptionValue(oseg2,null);
        
        if(seg1==null || seg2==null){
            System.err.println("Error: It is necessary to provide two text segments by means of the options -1 and -2.");
            System.exit(-1);
        }
        
        Segment s1=new Segment(seg1);
        Segment s2=new Segment(seg2);
        
        boolean[] talignment=new boolean[s2.size()];
        Arrays.fill(talignment, false);
        Segment.EditDistance(s1.getSentenceCodes(), s2.getSentenceCodes(), talignment, true);
        
        for(int i=0;i<s2.size(); i++){
            System.out.print(s2.getWord(i).getValue());
            System.out.print(":");
            System.out.println(talignment[i]);
        }
    }
}
