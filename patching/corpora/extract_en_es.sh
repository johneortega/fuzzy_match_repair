#!/bin/bash
# download the files if they don't exist
if [ ! -f /home/johneortega/patching/corpora/en_es.tmx ]; then
	wget http://optima.jrc.it/Resources/DGT-TM-2015/Vol_2014_1.zip
	wget http://optima.jrc.it/Resources/DGT-TM-2015/Vol_2014_2.zip
	wget http://optima.jrc.it/Resources/DGT-TM-2015/Vol_2014_3.zip
	wget http://optima.jrc.it/Resources/TMXtract.jar
	java -jar TMXtract.jar EN ES /home/johneortega/patching/corpora/en_es.tmx Vol_*.zip
	touch /home/johneortega/patching/corpora/en_es.tmx
fi
# now we assume the tm is there (en_es.tmx)
python extract_files.py en > en.omega.tm 2>&1
python extract_files.py es > es.omega.tm 2>&1
/home/johneortega/software/scripts/tokenizer.perl -l en < en.omega.tm > en.omega.tm.tok
/home/johneortega/software/scripts/tokenizer.perl -l es < es.omega.tm > es.omega.tm.tok
/home/johneortega/software/scripts/lowercase.perl < en.omega.tm.tok > en.omega.tm
/home/johneortega/software/scripts/lowercase.perl < es.omega.tm.tok > es.omega.tm
echo "files en.omega.tm and es.omega.tm created"
