
##!/bin/bash
# A sample Bash script, by Ryan

echo Graficando los resultados!

gnuplot -e "set terminal png size 800,600; set output 'FUN.png';
			plot \"FUN\""

gnuplot -e "set terminal png size 800,600; set output 'FUN_NSGAII.png';
			plot \"FUN_NSGAII\""