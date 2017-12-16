write("", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex",append=FALSE)
resultDirectory<-"/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/data"
latexHeader <- function() {
  write("\\documentclass{article}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\title{StandardStudy}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\usepackage{amssymb}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\author{A.J.Nebro}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\begin{document}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\maketitle", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\section{Tables}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
}

latexTableHeader <- function(problem, tabularString, latexTableFirstLine) {
  write("\\begin{table}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\caption{", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write(problem, "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write(".HV.}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)

  write("\\label{Table:", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write(problem, "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write(".HV.}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)

  write("\\centering", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\begin{scriptsize}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\begin{tabular}{", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write(tabularString, "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write(latexTableFirstLine, "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\hline ", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
}

latexTableTail <- function() { 
  write("\\hline", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\end{tabular}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\end{scriptsize}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  write("\\end{table}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
}

latexTail <- function() { 
  write("\\end{document}", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
}

printTableLine <- function(indicator, algorithm1, algorithm2, i, j, problem) { 
  file1<-paste(resultDirectory, algorithm1, sep="/")
  file1<-paste(file1, problem, sep="/")
  file1<-paste(file1, indicator, sep="/")
  data1<-scan(file1)
  file2<-paste(resultDirectory, algorithm2, sep="/")
  file2<-paste(file2, problem, sep="/")
  file2<-paste(file2, indicator, sep="/")
  data2<-scan(file2)
  if (i == j) {
    write("--", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  }
  else if (i < j) {
    if (wilcox.test(data1, data2)$p.value <= 0.05) {
      if (median(data1) >= median(data2)) {
        write("$\\blacktriangle$", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
      }
      else {
        write("$\\triangledown$", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE) 
      }
    }
    else {
      write("--", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE) 
    }
  }
  else {
    write(" ", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
  }
}

### START OF SCRIPT 
# Constants
problemList <-c("PlanningProblem") 
algorithmList <-c("Configuracion1", "Configuracion2", "Configuracion3", "Configuracion4", "Configuracion5", "Configuracion6", "Configuracion7", "Configuracion8") 
tabularString <-c("lccccccc") 
latexTableFirstLine <-c("\\hline  & Configuracion2 & Configuracion3 & Configuracion4 & Configuracion5 & Configuracion6 & Configuracion7 & Configuracion8\\\\ ") 
indicator<-"HV"

 # Step 1.  Writes the latex header
latexHeader()
# Step 2. Problem loop 
for (problem in problemList) {
  latexTableHeader(problem,  tabularString, latexTableFirstLine)

  indx = 0
  for (i in algorithmList) {
    if (i != "Configuracion8") {
      write(i , "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
      write(" & ", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
      jndx = 0 
      for (j in algorithmList) {
        if (jndx != 0) {
          if (indx != jndx) {
            printTableLine(indicator, i, j, indx, jndx, problem)
          }
          else {
            write("  ", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
          }
          if (j != "Configuracion8") {
            write(" & ", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
          }
          else {
            write(" \\\\ ", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
          }
        }
        jndx = jndx + 1
      }
      indx = indx + 1
    }
  }

  latexTableTail()
} # for problem

tabularString <-c("| l | p{0.15cm}   | p{0.15cm}   | p{0.15cm}   | p{0.15cm}   | p{0.15cm}   | p{0.15cm}   | p{0.15cm}   | ") 

latexTableFirstLine <-c("\\hline \\multicolumn{1}{|c|}{} & \\multicolumn{1}{c|}{Configuracion2} & \\multicolumn{1}{c|}{Configuracion3} & \\multicolumn{1}{c|}{Configuracion4} & \\multicolumn{1}{c|}{Configuracion5} & \\multicolumn{1}{c|}{Configuracion6} & \\multicolumn{1}{c|}{Configuracion7} & \\multicolumn{1}{c|}{Configuracion8} \\\\") 

# Step 3. Problem loop 
latexTableHeader("PlanningProblem ", tabularString, latexTableFirstLine)

indx = 0
for (i in algorithmList) {
  if (i != "Configuracion8") {
    write(i , "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
    write(" & ", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)

    jndx = 0
    for (j in algorithmList) {
      for (problem in problemList) {
        if (jndx != 0) {
          if (i != j) {
            printTableLine(indicator, i, j, indx, jndx, problem)
          }
          else {
            write("  ", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
          } 
          if (problem == "PlanningProblem") {
            if (j == "Configuracion8") {
              write(" \\\\ ", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
            } 
            else {
              write(" & ", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
            }
          }
     else {
    write("&", "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/NSGAIIStudy/R/Problems.HV.Wilcox.tex", append=TRUE)
     }
        }
      }
      jndx = jndx + 1
    }
    indx = indx + 1
  }
} # for algorithm

  latexTableTail()

#Step 3. Writes the end of latex file 
latexTail()

