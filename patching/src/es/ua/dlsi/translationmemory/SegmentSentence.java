/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.ua.dlsi.translationmemory;

import es.ua.dlsi.segmentation.Segment;
import es.ua.dlsi.utils.CmdLineParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author miquel
 */
public class SegmentSentence {

    /**
     * Method that generates all the possible sub-segments between 0 and the value
     * of <code>max_len</code> and prints them into an output file. Each segment
     * will be finished with an empty character and a line-break.
     * @param sfile File from which segments are to be read.
     * @param tfile Output file.
     * @param max_len Maximum length of the sub-segments.
     */
    public static void GenerateAllSegmentsFile(String sfile, String tfile, boolean html){
        Set<Segment> segments;
        //FileReader inFile;
        FileWriter outFile;
        BufferedReader in=null;
        PrintWriter out;
        System.out.println(sfile);
        try {
            //File from which sentences will be read
            //inFile=new FileReader(sfile);
            //in=new BufferedReader(inFile);
            in = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream(sfile)), "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error while trying to read the segments file");
            System.exit(-1);
        }
        try{
            //File in which the generated segments will be written
            File of=new File(tfile);
            if(of.exists()){
                System.err.println("Warning: File ");
                System.err.println(tfile);
                System.err.println(" already exists. New sub-segments will be"
                        + "added at the end of the file.");
            }
            outFile = new FileWriter(tfile);
            out = new PrintWriter(outFile);

            //Generating a line with each segment in a text file
            String sline=null;
            out.println("<html><body>");
            while((sline = in.readLine())!=null){
                Segment segment=new Segment(sline,true);
                segments=segment.AllSegmentsInSentence(segment.size());
                for(Segment seg: segments){
                    if(html)
                        out.println("<p>"+seg.toString()+"</p>");
                    else{
                        out.println(seg.toString()+" .");
                        out.println();
                    }
                }
            }
            out.println("</body></html>");
            in.close();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error while trying to create sub-segments file");
            System.exit(-1);
        }
    }

    /**
     * Method that generates all the possible sub-segments between 0 and the value
     * of <code>max_len</code> and prints them into an output file. Each segment
     * will be finished with an empty character and a line-break.
     * @param sfile File from which segments are to be read.
     * @param tfile Output file.
     * @param max_len Maximum length of the sub-segments.
     */
    public static void GenerateSegmentsFile(String sfile, String tfile, int max_len, boolean html){
        Set<Segment> segments;
        //FileReader inFile;
        FileWriter outFile;
        BufferedReader in=null;
        PrintWriter out;
        System.out.println(sfile);
        try {
            //File from which sentences will be read
            //inFile=new FileReader(sfile);
            //in=new BufferedReader(inFile);
            in = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream(sfile)), "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error while trying to read the segments file");
            System.exit(-1);
        }
        try{
            //File in which the generated segments will be written
            File of=new File(tfile);
            if(of.exists()){
                System.err.println("Warning: File ");
                System.err.println(tfile);
                System.err.println(" already exists. New sub-segments will be"
                        + "added at the end of the file.");
            }
            outFile = new FileWriter(tfile);
            out = new PrintWriter(outFile);

            //Generating a line with each segment in a text file
            String sline=null;
            while((sline = in.readLine())!=null){
                Segment segment=new Segment(sline,true);
                segments=segment.AllSegmentsInSentence(max_len);
                for(Segment seg: segments){
                    if(html)
                        out.print("<p>"+seg.toString()+"</p>");
                    else
                        out.print(seg.toString()+" .");
                    out.println();
                    out.println();
                }
            }
            in.close();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error while trying to create sub-segments file");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option otmsource = parser.addStringOption('s',"tm-source");
        CmdLineParser.Option otmtarget = parser.addStringOption('t',"tm-target");
        CmdLineParser.Option osegsource = parser.addStringOption("seg-source");
        CmdLineParser.Option osegtarget = parser.addStringOption("seg-target");
        CmdLineParser.Option omaxseglen = parser.addIntegerOption('m',"max-segment-len");
        CmdLineParser.Option ohtmlsubsegs = parser.addBooleanOption("html");
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
        String tmsource=(String)parser.getOptionValue(otmsource,null);
        String tmtarget=(String)parser.getOptionValue(otmtarget,null);
        int maxseglen=(Integer)parser.getOptionValue(omaxseglen,-1);
        String segsource=(String)parser.getOptionValue(osegsource,null);
        String segtarget=(String)parser.getOptionValue(osegtarget,null);
        Boolean htmlsubsegs=(Boolean)parser.getOptionValue(ohtmlsubsegs,false);
        if(maxseglen<0){
            System.err.println("Warning: No maximum lenght was set: segments will be segmented in all possible sub-segments.");
            
        }
        /*if(threshold<=0){
            System.err.println("Error: It is necessary to set a maximum lenght higher than 0 (use parameter --max-segment-len).");
            System.exit(-1);
        }*/
        if(tmsource!=null){
            if(segsource==null){
                System.err.println("Error: It is necessary to define a the file where the source language sub-segments will be saved (use parameter --seg-source).");
                System.exit(-1);
            }
            else{
                if(maxseglen>-1)
                    GenerateSegmentsFile(tmsource, segsource, maxseglen, htmlsubsegs);
                else
                    GenerateAllSegmentsFile(tmsource, segsource, htmlsubsegs);
            }
        }
        if(tmtarget!=null){
            if(segtarget==null){
                System.err.println("Error: It is necessary to define a the file where the target language sub-segments will be saved (use parameter --seg-target).");
                System.exit(-1);
            }
            else{
                if(maxseglen>-1)
                    GenerateSegmentsFile(tmtarget, segtarget, maxseglen, htmlsubsegs);
                else
                    GenerateAllSegmentsFile(tmtarget, segtarget, htmlsubsegs);
            }
        }
    }
}
