/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.ua.dlsi.tests.statystics;

import es.ua.dlsi.segmentation.Segment;
import es.ua.dlsi.translationmemory.TranslationMemory;
import es.ua.dlsi.translationmemory.TranslationUnit;
import es.ua.dlsi.utils.CmdLineParser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.io.StringWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;

import es.ua.dlsi.utils.Pair;
import es.ua.dlsi.segmentation.Word;
import es.ua.dlsi.patching.BooleanPatchOperator;
import es.ua.dlsi.patching.PatchManager;
import es.ua.dlsi.patching.PatchOperator;
import es.ua.dlsi.patching.Patch;
import es.ua.dlsi.patching.PatchPhraseExtraction;
import es.ua.dlsi.patching.PatchPhrasePair;

import java.util.Locale;
import java.text.Collator;


/**
 *
 * @author john evan ortega 
 * 03/30/2017 this takes two files and creates a readable hashmap object for 
 * the translation map
 */
public class RunCreateTransMapFromFiles{ 
	

    public static void main(String[] args) {
        CmdLineParser parser 			= new CmdLineParser();
        CmdLineParser.Option omtsource 		= parser.addStringOption("mt-source");
        CmdLineParser.Option omttarget 		= parser.addStringOption("mt-target");
        CmdLineParser.Option omtcache           = parser.addStringOption("mt-cache");
	Map<String,String> translations = new HashMap<String,String>();

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

        String mtsource		= (String)parser.getOptionValue(omtsource,null);
        String mttarget		= (String)parser.getOptionValue(omttarget,null);
        String mtcache          = (String)parser.getOptionValue(omtcache,null);
        if(mtsource==null || mttarget==null || mtcache==null){
            System.err.println("Error: please define mtsource and mttarget files.");
            System.exit(-1);
        }
        try {
		BufferedReader brs = new BufferedReader(new FileReader(mtsource));
		BufferedReader brt = new BufferedReader(new FileReader(mttarget));
		while (true){
    			String src = brs.readLine();
			String trg = brt.readLine();
    			if (src == null || trg == null){
                    break;
                }
			translations.put(src,trg);
		}
		brs.close();
		brt.close();
		try{
			// write the translation map
			FileOutputStream fos = new FileOutputStream(mtcache);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(translations);
			oos.close();
			fos.close();
		}
		catch(Exception e){
		    System.err.println("Error while writing in serialized" +  mtcache);
		    System.exit(-1);
		}
		
        } catch (Exception e) {
            	e.printStackTrace();

	}
   } 
}

