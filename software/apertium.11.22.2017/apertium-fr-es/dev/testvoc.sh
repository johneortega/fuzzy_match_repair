echo "==French->Spanish===========================";
sh inconsistency.sh fr-es > /tmp/fr-es.testvoc; sh inconsistency-summary.sh /tmp/fr-es.testvoc fr-es
echo ""
echo "==Spanish->French===========================";
sh inconsistency.sh es-fr > /tmp/es-fr.testvoc; sh inconsistency-summary.sh /tmp/es-fr.testvoc es-fr
