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
if [ -z "$3" ]
  then
    echo "Please provide number of trees desired"
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
train_file=${train_file%$gz_end}
dev_file=${dev_file%$gz_end}
rm ${train_file}.successrates
rm ${train_file}.without.successrates
rm ${dev_file}.successrates
rm ${dev_file}.without.successrates
rm ${dev_file}.sentences
rm ${dev_file}.sentences.count
rm ${dev_file}.sentences.wer
rm ${train_file}.predicted.csv
rm parse.out
rm parse.out.final.oracle
rm parse.out.final.predicted

echo "Unzipping training file: ${train_file}"
gunzip -f ${train_file}
### this was missing feature 21, put back to hold numbers
#awk -F'\t' -v OFS='\t' {'print $1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14,$15,$16,$17,$18,$19,$20,$22,$23,$24,$25,$26,$27,$28,$29,$30,$31,$32,$33'} ${train_file} > ${train_file}.without.successrates
### this has all 33 features
#awk -F'\t' -v OFS='\t' {'print $1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14,$15,$16,$17,$18,$19,$20,$21,$22,$23,$24,$25,$26,$27,$28,$29,$30,$31,$32,$33'} ${train_file} > ${train_file}.without.successrates
### this has all 33 features except for size of t
awk -F'\t' -v OFS='\t' {'print $1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14,$15,$16,$17,$18,$20,$21,$22,$23,$24,$25,$26,$27,$28,$29,$30,$31,$32,$33'} ${train_file} > ${train_file}.without.successrates
### this has all 33 features except for size of t and no bb10 also
#awk -F'\t' -v OFS='\t' {'print $2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14,$15,$16,$17,$18,$20,$21,$22,$23,$24,$25,$26,$27,$28,$29,$30,$31,$32,$33'} ${train_file} > ${train_file}.without.successrates
awk -F'\t' {'print $34'} ${train_file} > ${train_file}.successrates


### now dev 
echo "Unzipping dev file: ${dev_file}"
gunzip -f ${dev_file}
### this was missing feature 21, put back to hold numbers
#awk -F'\t' -v OFS='\t' {'print $1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14,$15,$16,$17,$18,$19,$20,$22,$23,$24,$25,$26,$27,$28,$29,$30,$31,$32,$33'} ${dev_file} > ${dev_file}.without.successrates
### this has all 33 features
#awk -F'\t' -v OFS='\t' {'print $1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14,$15,$16,$17,$18,$19,$20,$21,$22,$23,$24,$25,$26,$27,$28,$29,$30,$31,$32,$33'} ${dev_file} > ${dev_file}.without.successrates
### this has all 33 features except for size of t
awk -F'\t' -v OFS='\t' {'print $1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14,$15,$16,$17,$18,$20,$21,$22,$23,$24,$25,$26,$27,$28,$29,$30,$31,$32,$33'} ${dev_file} > ${dev_file}.without.successrates
### this has all 33 features except for size of t and no bb10 also
#awk -F'\t' -v OFS='\t' {'print $2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14,$15,$16,$17,$18,$20,$21,$22,$23,$24,$25,$26,$27,$28,$29,$30,$31,$32,$33'} ${dev_file} > ${dev_file}.without.successrates
awk -F'\t' {'print $34'} ${dev_file} > ${dev_file}.successrates


cp ${train_file}.without.successrates features.train.tz
cp ${dev_file}.without.successrates features.dev.tz
cp ${train_file}.successrates success_rates.train.tz
cp ${dev_file}.successrates success_rates.dev.tz

perl -p -i -e 's/ /\t/g' features.train.tz
perl -p -i -e 's/ /\t/g' features.dev.tz

## run the predictions
## uncomment below to use tree idea
## remember to update the file 100t etc with new changes
#python src/learn_model_${3}t.py config/john.cfg
python src/learn_model.py config/john.cfg


wait

## this assume the 24th column is the sentece in order to get the numbers
## this assume the 25th column is the wer
dev_sentences=${dev_file}.sentences
dev_sentences_count=${dev_file}.sentences.count
dev_sentences_wer=${dev_file}.sentences.wer
#awk -F'\t' '{print $30}' ${dev_file} > ${dev_sentences}
#awk -F'\t' '{print $35}' ${dev_file} > ${dev_sentences}
awk -F'\t' '{print $36}' ${dev_file} > ${dev_sentences}
#awk -F'\t' '{print $33}' ${dev_file} > ${dev_sentences}
#awk -F'\t' '{print $34}' ${dev_file} > ${dev_sentences}
#awk -F'\t' '{print $8}' ${dev_file} > ${dev_sentences}
python process.py $dev_sentences > ${dev_sentences_count}
#awk -F'\t' '{print $36}' ${dev_file} > ${dev_sentences_wer}
#awk -F'\t' '{print $31}' ${dev_file} > ${dev_sentences_wer}
awk -F'\t' '{print $37}' ${dev_file} > ${dev_sentences_wer}
#awk -F'\t' '{print $34}' ${dev_file} > ${dev_sentences_wer}
#awk -F'\t' '{print $35}' ${dev_file} > ${dev_sentences_wer}
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

# we are going to create random predictions
# and, we are not going to stop the model running for now
# so, just need to pick a random file and make it $2, to do this
# we are going to read the file and replace using rand
python run_randomizer.py parse.out.final.predicted > parse.out.final.predicted.random
mv parse.out.final.predicted.random parse.out.final.predicted

# default file for parse_final.py is parse.out.final
python parse_final.py parse.out.final.oracle
python parse_final.py parse.out.final.predicted

