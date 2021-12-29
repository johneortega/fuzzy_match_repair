#!/bin/bash
# now we assume the tm is there (en_es.tmx)
python extract_files_en_es.py en > en.omega.tm 2>&1
python extract_files_en_es.py es > es.omega.tm 2>&1
/home/johneortega/software/scripts/tokenizer.perl -l en < en.omega.tm > en.omega.tm.tok
/home/johneortega/software/scripts/tokenizer.perl -l es < es.omega.tm > es.omega.tm.tok
/home/johneortega/software/scripts/lowercase.perl < en.omega.tm.tok > en.omega.tm
/home/johneortega/software/scripts/lowercase.perl < es.omega.tm.tok > es.omega.tm
echo "files en.omega.tm and es.omega.tm created"
