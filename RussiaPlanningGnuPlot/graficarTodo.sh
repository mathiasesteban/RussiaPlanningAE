##!/bin/bash

gnuplot -e "
set terminal png size 800,600;
set output 'plot.png';
FILES = system(\"ls -1 *.result\");
LABEL = system(\"ls -1 *.result | sed -e 's/asd//' -e 's/.result//'\");
plot for [i=1:words(FILES)] word(FILES,i) title word(LABEL,i) noenhanced;"
