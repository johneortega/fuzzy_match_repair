TMPDIR=/tmp

if [[ $1 = "es-fr" ]]; then

lt-expand ../apertium-fr-es.es.dix | grep -v '<prn><enc>' | grep -e ':>:' -e '\w:\w' | sed 's/:>:/%/g' | sed 's/:/%/g' | cut -f2 -d'%' |  sed 's/^/^/g' | sed 's/$/$ ^.<sent>$/g' | tee $TMPDIR/tmp_testvoc1.txt |
        apertium-pretransfer|
        apertium-transfer ../apertium-fr-es.es-fr.t1x  ../es-fr.t1x.bin  ../es-fr.autobil.bin |
        apertium-interchunk ../apertium-fr-es.es-fr.t2x  ../es-fr.t2x.bin |
        apertium-postchunk ../apertium-fr-es.es-fr.t3x  ../es-fr.t3x.bin  | tee $TMPDIR/tmp_testvoc2.txt |
        lt-proc -d ../es-fr.autogen.bin > $TMPDIR/tmp_testvoc3.txt
paste -d _ $TMPDIR/tmp_testvoc1.txt $TMPDIR/tmp_testvoc2.txt $TMPDIR/tmp_testvoc3.txt | sed 's/\^.<sent>\$//g' | sed 's/_/   --------->  /g'

elif [[ $1 = "fr-es" ]]; then

lt-expand ../fr.dix | grep -v '<prn><enc>' | grep -e ':>:' -e '\w:\w' | sed 's/:>:/%/g' | sed 's/:/%/g' | cut -f2 -d'%' |  sed 's/^/^/g' | sed 's/$/$ ^.<sent>$/g' | tee $TMPDIR/tmp_testvoc1.txt |
        apertium-pretransfer|
        apertium-transfer ../apertium-fr-es.fr-es.t1x  ../fr-es.t1x.bin  ../fr-es.autobil.bin |
        apertium-interchunk ../apertium-fr-es.fr-es.t2x  ../fr-es.t2x.bin |
        apertium-postchunk ../apertium-fr-es.fr-es.t3x  ../fr-es.t3x.bin  | tee $TMPDIR/tmp_testvoc2.txt |
        lt-proc -d ../fr-es.autogen.bin > $TMPDIR/tmp_testvoc3.txt
paste -d _ $TMPDIR/tmp_testvoc1.txt $TMPDIR/tmp_testvoc2.txt $TMPDIR/tmp_testvoc3.txt | sed 's/\^.<sent>\$//g' | sed 's/_/   --------->  /g'


else
	echo "sh inconsistency.sh <direction>";
fi
