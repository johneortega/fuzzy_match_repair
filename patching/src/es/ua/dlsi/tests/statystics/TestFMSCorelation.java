/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.ua.dlsi.tests.statystics;

import es.ua.dlsi.utils.CmdLineParser;
import es.ua.dlsi.utils.Pair;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author miquel
 */
public class TestFMSCorelation {

    static public String StringArray2String(String[] s){
        StringBuilder sb=new StringBuilder();
        for(String seg: s){
            sb.append(seg);
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    private static short minimum(int a, int b, int c) {
        return (short) Math.min(a, Math.min(b, c));
    }

    static public int EditDistance(String[] s, String[] t) {
        int n = s.length; // length of s
        int m = t.length; // length of t
        short[][] table=new short[m+1][n+1];
        short[] d = null;
        short[] p = null;

        if (n == 0)
            return m;
        else if (m == 0)
            return n;

        short[] swap; // placeholder to assist in swapping p and d

        // indexes into strings s and t
        short i; // iterates through s
        short j; // iterates through t

        String t_j = null; // jth object of t

        short cost; // cost

        p=table[0];
        for (i = 0; i <= n; i++)
            p[i] = i;

        for (j = 1; j <= m; j++) {
            d=table[j];
            t_j = t[j - 1];
            d[0] = j;

            String s_i = null; // ith object of s
            for (i = 1; i <= n; i++) {
                s_i = s[i - 1];
                cost = s_i.equals(t_j) ? (short) 0 : (short) 1;
                // minimum of cell to the left+1, to the top+1, diagonally left
                // and up +cost
                d[i] = minimum(d[i - 1] + 1, p[i] + 1, p[i - 1] + cost);
            }
            // copy current distance counts to 'previous row' distance counts
            p = d;
        }
        return p[n];
    }

    public static double ComputeCorrelAllTM(int i, List<String[]> slist,
            List<String[]> tlist, String[] s, String[] t){
        List<Pair<Double,Double>> scoreslist=new LinkedList<Pair<Double,Double>>();
        double ssum=0.0, tsum=0.0;
        for(int j=0; j<i; j++){
            if(j!=i){
                String[] ssample=slist.get(j);
                String[] tsample=tlist.get(j);
                double sscore=EditDistance(s, ssample);
                double tscore=EditDistance(t, tsample);
                ssum+=sscore;
                tsum+=tscore;
                Pair p=new Pair(sscore, tscore);
                scoreslist.add(p);
            }
        }
        for(int j=i; j<slist.size(); j++){
            if(j!=i){
                String[] ssample=slist.get(j);
                String[] tsample=tlist.get(j);
                double sscore=EditDistance(s, ssample);
                double tscore=EditDistance(t, tsample);
                ssum+=sscore;
                tsum+=tscore;
                Pair p=new Pair(sscore, tscore);
                scoreslist.add(p);
            }
        }
        double smean=ssum/(double)(slist.size()-1);
        double tmean=tsum/(double)(tlist.size()-1);
        double devs_by_devt=0.0;
        double sq_devs=0.0;
        double sq_devt=0.0;
        for(Pair<Double,Double> p: scoreslist){
            devs_by_devt+=(p.getFirst()-smean)*(p.getSecond()-tmean);
            sq_devs+=Math.pow(p.getFirst()-smean,2);
            sq_devt+=Math.pow(p.getSecond()-smean,2);
        }
        double correl=devs_by_devt/Math.sqrt(sq_devs*sq_devt);
        return correl;
    }

    public static double ComputeCorrelSampling(List<String[]> slist, List<String[]> tlist,
            String[] s, String[] t, PrintWriter pwdeb){
        List<Pair<Double,Double>> scoreslist=new LinkedList<Pair<Double,Double>>();
        double ssum=0.0, tsum=0.0;
        for(int j=0; j<slist.size(); j++){
            String[] ssample=slist.get(j);
            String[] tsample=tlist.get(j);
            double sscore=EditDistance(s, ssample);
            double tscore=EditDistance(t, tsample);
            ssum+=sscore;
            tsum+=tscore;
            if(pwdeb!=null){
                pwdeb.print(sscore+";"+tscore+" ");
            }
            Pair p=new Pair(sscore, tscore);
            scoreslist.add(p);
        }
        double smean=ssum/(double)(slist.size());
        double tmean=tsum/(double)(tlist.size());
        if(pwdeb!=null){
            pwdeb.println(smean+";"+tmean);
        }
        double devs_by_devt=0.0;
        double sq_devs=0.0;
        double sq_devt=0.0;
        for(Pair<Double,Double> p: scoreslist){
            devs_by_devt+=(p.getFirst()-smean)*(p.getSecond()-tmean);
            sq_devs+=Math.pow(p.getFirst()-smean,2);
            sq_devt+=Math.pow(p.getSecond()-tmean,2);
        }
        double correl=devs_by_devt/Math.sqrt(sq_devs*sq_devt);
        return correl;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option otmsource = parser.addStringOption('s',"tm-source");
        CmdLineParser.Option otmtarget = parser.addStringOption('t',"tm-target");
        CmdLineParser.Option ooutput = parser.addStringOption('o',"output");
        CmdLineParser.Option odebug = parser.addStringOption('d',"debug");
        CmdLineParser.Option osampling = parser.addIntegerOption("sampling");

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
        String output=(String)parser.getOptionValue(ooutput,null);
        String debug=(String)parser.getOptionValue(odebug,null);
        int sampling=(Integer)parser.getOptionValue(osampling,-1);

        PrintWriter pw;

        if(output==null){
            System.err.println("Warning: Undefined output file: the results will be printed in the default output.");
            pw=new PrintWriter(System.out);
        }
        else{
            try{
                pw=new PrintWriter(output);
            } catch(FileNotFoundException ex){
                System.err.println("Warning: Output file "+output+" could not be found: the results will be printed in the default output.");
                pw=new PrintWriter(System.out);
            }
        }

        PrintWriter pwdeb=null;
        if(debug!=null){
            try{
                pwdeb=new PrintWriter(debug);
            } catch(FileNotFoundException ex){
                System.err.println("Warning: Output file "+debug+" could not be found: no debug information will be shown.");
            }
        }

        List<String[]> slines=new LinkedList<String[]>();
        List<String[]> tlines=new LinkedList<String[]>();
        BufferedReader sourcereader = null;
        BufferedReader targetreader = null;
        String sourceline=null;
        String targetline=null;
        try{
            //sourcereader = new BufferedReader(new FileReader(tmsource));
            
            sourcereader = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream(tmsource)), "UTF-8"));
            targetreader = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream(tmtarget)), "UTF-8"));
        }catch(FileNotFoundException ex){
            ex.printStackTrace(System.err);
            System.exit(-1);
        }catch(IOException ex){
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
        if(sampling==-1){
            try{
                while((sourceline=sourcereader.readLine())!=null)
                    slines.add(sourceline.split(" "));
            }catch(IOException e){
                e.printStackTrace();
                System.exit(-1);
            }
            try{
                while((targetline=targetreader.readLine())!=null)
                    tlines.add(targetline.split(" "));
            }catch(IOException e){
                e.printStackTrace();
                System.exit(-1);
            }
        }
        else{
            int ntus=Integer.MAX_VALUE;

            try {
                //Loading a firts TM of sampling+1 TU
                int nlines=0;
                while((sourceline=sourcereader.readLine())!=null
                        && (targetline=targetreader.readLine())!=null
                        && nlines<sampling){
                    slines.add(sourceline.split(" "));
                    tlines.add(targetline.split(" "));
                    nlines++;
                }
                if(sourceline==null ||targetline==null){
                    //If the TM is smaller than the sample+1: error
                    System.err.print("The TM is smaller than the sample proposed");
                    System.exit(-1);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("Error while trying to read one of the translation memory sentences files.");
            }

            int ntustmp=sampling+1;
            for(int currtu=0;currtu<ntus;currtu++){
                String[] s=slines.remove(0);
                String[] t=tlines.remove(0);
                /*if(pwdeb!=null){
                    pwdeb.println("Computin correlation for segments '"+s.toString()+"' and '"+t.toString()+"'.");
                    pwdeb.println("\tComparing with segments between '"+
                            trans_memory.get(0).getSource().toString()+"' and '"+
                            trans_memory.get(trans_memory.size()-1).getSource().toString()
                            +"'");
                }*/
                System.err.print("Checking TU ");
                System.err.print(currtu);
                System.err.print(" with ");
                System.err.print(slines.size());
                System.err.print("elements in the sample");
                System.err.print("\r");
                double correl=ComputeCorrelSampling(slines, tlines, s, t, pwdeb);
                pw.print(correl);
                pw.print("\t");
                pw.print(StringArray2String(s));
                pw.print("\t");
                pw.println(StringArray2String(s));
                try{
                    if((sourceline=sourcereader.readLine())!=null
                            && (targetline=targetreader.readLine())!=null){
                        slines.add(sourceline.split(" "));
                        tlines.add(targetline.split(" "));
                        ntustmp++;
                    }
                    else{
                        ntus=ntustmp;
                        sourcereader.close();
                        targetreader.close();
                        try{
                            sourcereader = new BufferedReader(new FileReader(tmsource));
                            targetreader = new BufferedReader(new FileReader(tmtarget));
                            sourceline=sourcereader.readLine();
                            targetline=targetreader.readLine();
                            slines.add(sourceline.split(" "));
                            tlines.add(targetline.split(" "));
                        }catch(FileNotFoundException ex){
                            ex.printStackTrace(System.err);
                            System.exit(-1);
                        }
                    }
                } catch (IOException ex) {
                    System.err.println("Error while trying to read one of the translation memory sentences files.");
                    System.exit(-1);
                }
            }
        }

        
        System.err.println();

        pw.close();
    }
}