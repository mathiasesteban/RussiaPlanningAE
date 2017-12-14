##!/bin/bash

gnuplot -e "
set terminal png size 800,600;
set output 'plot.png';
FILES = system(\"ls -1 *.FUN\");
LABEL = system(\"ls -1 *.FUN | sed -e 's/asd//' -e 's/.FUN//'\");
plot for [i=1:words(FILES)] word(FILES,i) title word(LABEL,i) noenhanced;"
