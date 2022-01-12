### creates an output file paperinfo_datetime.out
### this gives you all the info you need like words, etc..

#filename='run_global_scenario_en_es_60.train.bbandbothgrounding.wer'
DATE_WITH_TIME=`date "+%Y%m%d-%H%M%S"`
outdir=paper_info_${DATE_WITH_TIME}
outfile=$outdir/paper_info.out
# a file with the node visited counts
nodefile=/home/johneortega/patching/scripts/pop.out 
opfile=/home/johneortega/patching/scripts/out 
mkdir -p $outdir
echo "Writing paper info to: ${outfile}" > ${outfile}
#for lang in en_es es_fr;
#for lang in es_pt;
for lang in en_es;
do
    for fms in 60 70 80 90; 
        do
        #for tset in 'train' 'dev' 'test';
        for tset in 'test';
        do
            gunzip run_global_scenario_${lang}_${fms}.${tset}.bbandbothgrounding.wer.new.gz
            filename="run_global_scenario_${lang}_${fms}.${tset}.bbandbothgrounding.wer.new"
            echo "----- ${fms} ---- ${tset}---" >> ${outfile}
            filebase=${outfile}.${lang}.${fms}.${tset}
            awk -F '\t' '{print $36}' $filename >> ${filebase}.sentences
            # 60 will give us the big dictionary
            sfilename="run_global_scenario_${lang}_60.${tset}.bbandbothgrounding.wer.new.sentences"
            scfilename="run_global_scenario_${lang}_${fms}.${tset}.bbandbothgrounding.wer.new.sentences.count"
            python getpaperinfo.1.new.py $scfilename ${filebase}.sentences $nodefile $opfile $sfilename $filebase.poc
        done;
    done;
done;
