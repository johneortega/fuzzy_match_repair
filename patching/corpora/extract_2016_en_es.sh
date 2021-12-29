#!/bin/bash
# download the files if they don't exist
# now we assume the tm is there (en_es.tmx)
#python extract_files.py en > en.2016.omega.tm 2>&1
#python extract_files.py es > es.2016.omega.tm 2>&1
/home/johneortega/software/scripts/tokenizer.perl -l en < en.2016.omega.tm > en.2016.omega.tm.tok
/home/johneortega/software/scripts/tokenizer.perl -l es < es.2016.omega.tm > es.2016.omega.tm.tok
/home/johneortega/software/scripts/lowercase.perl < en.2016.omega.tm.tok > en.2016.omega.tm
/home/johneortega/software/scripts/lowercase.perl < es.2016.omega.tm.tok > es.2016.omega.tm
#echo "files en.omega.tm and es.omega.tm created"
