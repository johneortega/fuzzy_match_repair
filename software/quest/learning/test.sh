maeshow=$(grep 'INFO:root:mae = ' prepare.out)
maeshow=$(echo "${maeshow/INFO:root:mae =/}")
maeshow="$(echo -e "${maeshow}" | tr -d '[:space:]')"
echo $maeshow
