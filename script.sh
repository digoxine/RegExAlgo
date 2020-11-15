tmp="tmp_file.txt"
res="res_grep.txt"
if [[ ! -e $tmp ]]
then
    touch $tmp
else
    rm $tmp
    touch $tmp
fi
if [[ ! -e $res ]]
then
    touch $res
else
    rm $res
    touch $res
fi
cut -f2 -d ' ' results.txt > $tmp
length_text=10000
while [ $length_text -le 2700000 ]; do
    while read line; do
	file_search="./resources/generated_texts/text_generated_"
	file_search+="$length_text"
	file_search+=".txt"
	start=`date +%s%3N`
	#echo $start
	grep -q -E $line $file_search
	end=`date +%s%3N`
	runtime=$((end-start))
	echo "$length_text $runtime" >> $res
	((length_text=10000+length_text))
    done<$tmp
done
