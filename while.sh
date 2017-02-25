#!/bin/bash
echo '' > temp

playNum=0
winNum=0
winRate=0

while [ true ];
do
	java -jar BattleshipServer.jar -p $2&
	java -cp .:BattleshipClient.jar client.BattleshipClient -v $1 localhost:$2 >> temp
	
	playNum=`expr $playNum + 1`

	v=$(grep '<<*' temp)
	if [ '<<YOU WIN>>' = "${v}" ]; then
		winNum=`expr $winNum + 1`
	fi
	winRate=`echo "scale=3; $winNum / $playNum * 100" | bc`

	echo "PlayNum:${playNum} WinNum:${winNum} (${winRate}%)"
	echo '' > temp
done