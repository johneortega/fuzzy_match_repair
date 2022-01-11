#!/bin/bash
## used as a quick way for evaluation
#source prepare_for_quest.parseonly.sh run_global_scenario_en_es_60.train.bb.wer.sentences.count
#run_global_scenario_en_es_60.train.bb.wer.sentences.wer run_global_scenario_en_es_60.train.bb.wer.predicted.csv


if [ -z "$1" ]
  then
    echo "Please provide files: dev_setnences_count dev_sentences_wer predicted_file"
    return 1
fi
if [ -z "$2" ]
  then
    echo "Please provide files: dev_setnences_count dev_sentences_wer predicted_file"
    return 1 
fi
if [ -z "$3" ]
  then
    echo "Please provide files: dev_setnences_count dev_sentences_wer predicted_file"
    return 1
fi


## combine final scores into one file
python parse.py ${1} ${2} ${3} > parse.out

## prepare the final file
awk -v OFS='\t' -F'\t' {'print $1,$3,$4,$5'} parse.out > parse.out.final.oracle
awk -v OFS='\t' -F'\t' {'print $2,$3,$4,$5'} parse.out > parse.out.final.predicted
perl -p -i -e 's/ /\t/g' parse.out.final.oracle
perl -p -i -e 's/ /\t/g' parse.out.final.predicted

# default file for parse_final.py is parse.out.final
python parse_final.py parse.out.final.oracle
python parse_final.py parse.out.final.predicted




     
