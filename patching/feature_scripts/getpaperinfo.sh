### creates an output file paperinfo_datetime.out
### this gives you all the info you need like words, etc..

#filename='run_global_scenario_en_es_60.train.bbandbothgrounding.wer'
DATE_WITH_TIME=`date "+%Y%m%d-%H%M%S"`
outdir=paper_info_${DATE_WITH_TIME}
outfile=$outdir/paper_info.out
mkdir -p $outdir
echo "Writing paper info to: ${outfile}" > ${outfile}
#for lang in en_es es_fr;
#for lang in es_pt;
for lang in en_es;
do
    for fms in 60 70 80 90; 
        do
        for tset in 'train' 'dev' 'test';
        do
            gunzip run_global_scenario_${lang}_${fms}.${tset}.bbandbothgrounding.wer.new.gz
            filename="run_global_scenario_${lang}_${fms}.${tset}.bbandbothgrounding.wer.new"
            echo "----- ${fms} ---- ${tset}---" >> ${outfile}
            filebase=${outfile}.${lang}.${fms}.${tset}
            awk -F '\t' '{print $22}' $filename >> ${filebase}.poc
            python getpaperinfo.1.py ${filebase}.poc
            poctotal=$(cat ${filebase}.poc.total)
            hyptotal=$(wc -l < $filename | bc)
            wc -l < $filename > ${filebase}.hc
            awk -F '\t' '{print $24}' $filename >> ${filebase}.wc
            python getpaperinfo.1.py ${filebase}.wc
            wctotal=$(cat ${filebase}.wc.total)
            avgwcphtot=$(echo ${wctotal}/${hyptotal} | bc -l)
            echo $avgwcphtot >> ${filebase}.avgwcph
        done;
    done;
done;
