#!/bin/bash
# now we assume the tm is there (en_es.tmx)
python extract_files_es_pt.py es > es.omega.tm 2>&1
python extract_files_es_pt.py pt > pt.omega.tm 2>&1
/home/johneortega/software/scripts/tokenizer.perl -l es < es.omega.tm > es.omega.tm.tok
/home/johneortega/software/scripts/tokenizer.perl -l pt < pt.omega.tm > pt.omega.tm.tok
/home/johneortega/software/scripts/lowercase.perl < es.omega.tm.tok > es.omega.tm
/home/johneortega/software/scripts/lowercase.perl < pt.omega.tm.tok > pt.omega.tm
echo "files es.omega.tm and pt.omega.tm created"
