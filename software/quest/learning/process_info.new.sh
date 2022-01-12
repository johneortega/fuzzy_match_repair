#!/bin/bash
outdir=${1}
echo "Reading paper info from: ${outdir}"
#for lang in en_es es_fr;
#for lang in es_pt;
#for lang in 'en_es' 'es_pt' 'es_fr';
for fms in 60 70 80 90; 
do
    echo $fms
    for lang in 'en_es' 'es_pt' 'es_fr';
    do
        for tset in 'train' 'dev' 'test';
        do
            gunzip --quiet run_global_scenario_${lang}_${fms}.${tset}.bbandbothgrounding.wer.new.gz >> /dev/null 2>&1
            segfile=run_global_scenario_${lang}_${fms}.${tset}.bbandbothgrounding.wer.new
            filename=${outdir}/paper_info.out.${lang}.${fms}.${tset}.hc
            segfile_sentences=${segfile}.sentences
            segfile_sentences_count=${segfile}.sentences.new.count
            #echo "awking train segfile ${segfile}"
            awk -F '\t' {'print $36'} ${segfile} > ${segfile_sentences}
            #echo "processing ${segfile_sentences}"
            #python process.py $segfile_sentences > ${segfile_sentences_count}
            python process.new.py /home/johneortega/patching/scripts/paperinfo/${lang}/pop.${fms}.po.out.$tset.hc > ${segfile_sentences_count}
            #echo "testing train"
            segcnt=$(tail -n 1 ${segfile_sentences_count})
            if [ ${tset} = 'train' ]; then
                trainhc=$(cat $filename)
                # last line in file is count of segments
                trainseg=$segcnt
                trainavg=$(echo ${trainhc}/${segcnt} | bc -l)
            elif [ ${tset} = 'dev' ]; then
                devhc=$(cat $filename)
                # last line in file is count of segments
                devseg=$segcnt
                devavg=$(echo ${devhc}/${segcnt} | bc -l)
            elif [ ${tset} = 'test' ]; then
                testhc=$(cat $filename)
                # last line in file is count of segments
                testseg=$segcnt
                testavg=$(echo ${testhc}/${segcnt} | bc -l)
            fi
        done;
        echo "& \\texttt{${lang}} & ${trainseg} & ${trainavg} & ${devseg} & ${devavg} & ${testseg} & ${testavg}\\\\"
    done;
done;
