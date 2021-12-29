#!/bin/bash
# now we assume the tm is there (en_es.tmx)
python extract_files.py en > en_de.omega.en.tm 2>&1
python extract_files.py de > en_de.omega.de.tm 2>&1
/home/johneortega/software/scripts/tokenizer.perl -l en < en_de.omega.en.tm > en_de.omega.en.tm.tok
/home/johneortega/software/scripts/tokenizer.perl -l de < en_de.omega.de.tm > en_de.omega.de.tm.tok
/home/johneortega/software/scripts/lowercase.perl < en_de.omega.en.tm.tok > en_de.omega.en.tm
/home/johneortega/software/scripts/lowercase.perl < en_de.omega.de.tm.tok > en_de.omega.de.tm
echo "files en.omega.tm and es.omega.tm created"
