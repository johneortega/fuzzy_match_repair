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
awk -F'\t' {'print $31'} ${train_file} > ${train_file}.successrates
awk -F'\t' {'$31=$32=$33=$34=$35=$36=$37=$39=$40=$41=$42""; print $0'} ${train_file} > ${train_file}.without.successrates
#awk -F'\t' {'print $6'} ${train_file} > ${train_file}.successrates
#awk -F'\t' {'$6=$7=$8=$9=$10=$11=$12=$13=$14""; print $0'} ${train_file} > ${train_file}.without.successrates


### now dev 
gunzip -f ${dev_file}
dev_file=${dev_file%$gz_end}
awk -F'\t' {'print $31'} ${dev_file} > ${dev_file}.successrates
awk -F'\t' {'$31=$32=$33=$34=$35=$36=$37=$39=$40=$41=$42""; print $0'} ${dev_file} > ${dev_file}.without.successrates
#awk -F'\t' {'print $6'} ${dev_file} > ${dev_file}.successrates
#awk -F'\t' {'$6=$7=$8=$9=$10=$11=$12=$13=$14""; print $0'} ${dev_file} > ${dev_file}.without.successrates


cp ${train_file}.without.successrates features.train.tz
cp ${dev_file}.without.successrates features.dev.tz
cp ${train_file}.successrates success_rates.train.tz
cp ${dev_file}.successrates success_rates.dev.tz

perl -p -i -e 's/ /\t/g' features.train.tz
perl -p -i -e 's/ /\t/g' features.dev.tz

## run the predictions
python src/learn_model.py config/john.cfg


wait

## this assume the 24th column is the sentece in order to get the numbers
## this assume the 25th column is the wer
dev_sentences=${dev_file}.sentences
dev_sentences_count=${dev_file}.sentences.count
dev_sentences_wer=${dev_file}.sentences.wer
awk -F'\t' '{print $33}' ${dev_file} > ${dev_sentences}
#awk -F'\t' '{print $8}' ${dev_file} > ${dev_sentences}
python process.py $dev_sentences > ${dev_sentences_count}
awk -F'\t' '{print $34}' ${dev_file} > ${dev_sentences_wer}
#awk -F'\t' '{print $9}' ${dev_file} > ${dev_sentences_wer}

## quest gives a predicted.csv file, copy it so that we have it 
predicted_file=${train_file}.predicted.csv

cp predicted.csv $predicted_file

## combine final scores into one file
python parse.py ${dev_sentences_count} ${dev_sentences_wer} ${predicted_file} > parse.out

## prepare the final file
awk -v OFS='\t' -F'\t' {'print $1,$3,$4,$5'} parse.out > parse.out.final.oracle
awk -v OFS='\t' -F'\t' {'print $2,$3,$4,$5'} parse.out > parse.out.final.predicted
perl -p -i -e 's/ /\t/g' parse.out.final.oracle
perl -p -i -e 's/ /\t/g' parse.out.final.predicted

# default file for parse_final.py is parse.out.final
python parse_final.py parse.out.final.oracle
python parse_final.py parse.out.final.predicted

