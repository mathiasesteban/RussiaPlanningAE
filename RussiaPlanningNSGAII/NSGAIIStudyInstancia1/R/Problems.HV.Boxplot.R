postscript("Problems.HV.Boxplot.eps", horizontal=FALSE, onefile=FALSE, height=8, width=12, pointsize=10)
resultDirectory<-"../data/"
qIndicator <- function(indicator, problem)
{
fileConfiguracion1<-paste(resultDirectory, "Configuracion1", sep="/")
fileConfiguracion1<-paste(fileConfiguracion1, problem, sep="/")
fileConfiguracion1<-paste(fileConfiguracion1, indicator, sep="/")
Configuracion1<-scan(fileConfiguracion1)

fileConfiguracion2<-paste(resultDirectory, "Configuracion2", sep="/")
fileConfiguracion2<-paste(fileConfiguracion2, problem, sep="/")
fileConfiguracion2<-paste(fileConfiguracion2, indicator, sep="/")
Configuracion2<-scan(fileConfiguracion2)

fileConfiguracion3<-paste(resultDirectory, "Configuracion3", sep="/")
fileConfiguracion3<-paste(fileConfiguracion3, problem, sep="/")
fileConfiguracion3<-paste(fileConfiguracion3, indicator, sep="/")
Configuracion3<-scan(fileConfiguracion3)

fileConfiguracion4<-paste(resultDirectory, "Configuracion4", sep="/")
fileConfiguracion4<-paste(fileConfiguracion4, problem, sep="/")
fileConfiguracion4<-paste(fileConfiguracion4, indicator, sep="/")
Configuracion4<-scan(fileConfiguracion4)

fileConfiguracion5<-paste(resultDirectory, "Configuracion5", sep="/")
fileConfiguracion5<-paste(fileConfiguracion5, problem, sep="/")
fileConfiguracion5<-paste(fileConfiguracion5, indicator, sep="/")
Configuracion5<-scan(fileConfiguracion5)

fileConfiguracion6<-paste(resultDirectory, "Configuracion6", sep="/")
fileConfiguracion6<-paste(fileConfiguracion6, problem, sep="/")
fileConfiguracion6<-paste(fileConfiguracion6, indicator, sep="/")
Configuracion6<-scan(fileConfiguracion6)

fileConfiguracion7<-paste(resultDirectory, "Configuracion7", sep="/")
fileConfiguracion7<-paste(fileConfiguracion7, problem, sep="/")
fileConfiguracion7<-paste(fileConfiguracion7, indicator, sep="/")
Configuracion7<-scan(fileConfiguracion7)

fileConfiguracion8<-paste(resultDirectory, "Configuracion8", sep="/")
fileConfiguracion8<-paste(fileConfiguracion8, problem, sep="/")
fileConfiguracion8<-paste(fileConfiguracion8, indicator, sep="/")
Configuracion8<-scan(fileConfiguracion8)

algs<-c("Configuracion1","Configuracion2","Configuracion3","Configuracion4","Configuracion5","Configuracion6","Configuracion7","Configuracion8")
boxplot(Configuracion1,Configuracion2,Configuracion3,Configuracion4,Configuracion5,Configuracion6,Configuracion7,Configuracion8,names=algs, notch = TRUE)
titulo <-paste(indicator, problem, sep=":")
title(main=titulo)
}
par(mfrow=c(2,3))
indicator<-"HV"
qIndicator(indicator, "PlanningProblem")
