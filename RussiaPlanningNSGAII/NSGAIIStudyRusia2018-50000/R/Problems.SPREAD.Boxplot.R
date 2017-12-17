postscript("Problems.SPREAD.Boxplot.eps", horizontal=FALSE, onefile=FALSE, height=8, width=12, pointsize=10)
resultDirectory<-"../data/"
qIndicator <- function(indicator, problem)
{
fileRusia2018<-paste(resultDirectory, "Rusia2018", sep="/")
fileRusia2018<-paste(fileRusia2018, problem, sep="/")
fileRusia2018<-paste(fileRusia2018, indicator, sep="/")
Rusia2018<-scan(fileRusia2018)

algs<-c("Rusia2018")
boxplot(Rusia2018,names=algs, notch = TRUE)
titulo <-paste(indicator, problem, sep=":")
title(main=titulo)
}
par(mfrow=c(2,3))
indicator<-"SPREAD"
qIndicator(indicator, "PlanningProblem")
