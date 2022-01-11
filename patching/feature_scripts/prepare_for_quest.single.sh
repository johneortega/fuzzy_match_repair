#!/bin/bash
## this script takes two files a dev.tar.gz and train.tar.gz file
## the idea is to leave a final fila success_rates.train.tz success_rates.dev.tz and features.train.tz and features.dev.tz
## this assumes certain columns exit, you may have to modify it according to your success rate column


if [ -z "$1" ]
  then
    echo "Please provide files: training.tar.gz then dev.tar.gz"
    exit -1
fi
if [ -z "$2" ]
  then
    echo "Please provide files: training.tar.gz then dev.tar.gz"
    exit -1
fi
if [ ${1: -3} != ".gz" ]
  then
    echo "${1} not gzipped"
fi
if [ ${2: -3} != ".gz" ]
  then
    echo "${2} not gzipped"
fi
train_file=$1
dev_file=$2
gz_end=".gz"

### first do train unzip and run
gunzip -f ${train_file}
train_file=${train_file%$gz_end}
### now dev 
gunzip -f ${dev_file}
dev_file=${dev_file%$gz_end}


cp ${train_file} features.train.tz
cp ${dev_file} features.dev.tz
cp run_global_scenario_en_es_60.train.bb.successrates success_rates.train.tz
cp run_global_scenario_en_es_60.dev.bb.successrates success_rates.dev.tz

perl -p -i -e 's/ /\t/g' features.train.tz
perl -p -i -e 's/ /\t/g' features.dev.tz

## run the predictions
python src/learn_model.py config/john.cfg


wait

## this assume the 24th column is the sentece in order to get the numbers
## this assume the 25th column is the wer
dev_sentences=run_global_scenario_en_es_60.dev.bb.sentences
dev_sentences_count=run_global_scenario_en_es_60.dev.bb.sentences.count
dev_sentences_wer=run_global_scenario_en_es_60.dev.bb.sentences.wer

## combine final scores into one file
python parse.py ${dev_sentences_count} ${dev_sentences_wer} > parse.out

## prepare the final file
awk -v OFS='\t' -F'\t' {'print $2,$3,$4,$5'} parse.out > parse.out.final
perl -p -i -e 's/ /\t/g' parse.out.final

# default file for parse_final.py is parse.out.final
python parse_final.py





