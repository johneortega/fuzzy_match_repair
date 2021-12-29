#!/bin/bash
# now we assume the tm is there (en_es.tmx)
python extract_files_es_fr.py es > es.omega.tm 2>&1
python extract_files_es_fr.py fr > fr.omega.tm 2>&1
/home/johneortega/software/scripts/tokenizer.perl -l es < es.omega.tm > es.omega.tm.tok
/home/johneortega/software/scripts/tokenizer.perl -l fr < fr.omega.tm > fr.omega.tm.tok
/home/johneortega/software/scripts/lowercase.perl < es.omega.tm.tok > es.omega.tm
/home/johneortega/software/scripts/lowercase.perl < fr.omega.tm.tok > fr.omega.tm
echo "files es.omega.tm and fr.omega.tm created"
