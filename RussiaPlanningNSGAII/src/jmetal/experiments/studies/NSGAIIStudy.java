//  NSGAIIStudy.java
//
//  Authors:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package jmetal.experiments.studies;

import java.io.FileNotFoundException;
import jmetal.core.Algorithm;
import jmetal.experiments.Experiment;
import jmetal.experiments.Settings;
import jmetal.experiments.settings.NSGAII_Settings;
import jmetal.experiments.util.Friedman;
import jmetal.util.JMException;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.metaheuristics.nsgaII.NSGAII;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.selection.SelectionFactory;
import planningrusia.PlanningProblem;

/**
 * Class implementing an example of experiment using NSGA-II as base algorithm.
 * The experiment consisting in studying the effect of the crossover probability
 * in NSGA-II.
 */
public class NSGAIIStudy extends Experiment {
  /**
   * Configures the algorithms in each independent run
   * @param problemName The problem to solve
   * @param problemIndex
   * @param algorithm Array containing the algorithms to run
   * @throws ClassNotFoundException 
   */
  public synchronized void algorithmSettings(String problemName, 
  		                                       int problemIndex, 
  		                                       Algorithm[] algorithm) 
    throws ClassNotFoundException {  
    
  try 
    {
        int numberOfAlgorithms = algorithmNameList_.length;

        HashMap[] parameters = new HashMap[numberOfAlgorithms];

        for (int i = 0; i < numberOfAlgorithms; i++) {
          parameters[i] = new HashMap();
        } // for

        if (!paretoFrontFile_[problemIndex].equals("")) {
          for (int i = 0; i < numberOfAlgorithms; i++)
            parameters[i].put("paretoFrontFile_", paretoFrontFile_[problemIndex]);
        } // if

        
        if ((!paretoFrontFile_[problemIndex].equals("")) || 
                  (paretoFrontFile_[problemIndex] == null)) {
          for (int i = 0; i < numberOfAlgorithms; i++)
            parameters[i].put("paretoFrontFile_",  paretoFrontFile_[problemIndex]);
        } // if
        
        
        // Defino el problema, que sera el mismo para todas las ejecuciones de 
        // todos los algoritmos
        Problem problem = null;
        try {
            problem = new PlanningProblem("datos.config");
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
        }

          Operator  crossover ; // Crossover operator
          Operator  mutation  ; // Mutation operator
          Operator  selection ; // Selection operator

        for (int i = 0; i < numberOfAlgorithms; i++){
            //algorithm[i] = new NSGAII_Settings(problemName).configure(parameters[i]);
          
            algorithm[i] = new NSGAII(problem);
          
          
            double pc = 0;
            double pm = 0;
            int tp = 0;

            if ( i== 0){
               pc = 0.6;
               pm = 0.03;
               tp = 100;
            }
            else if(i== 1){
                pc = 0.6;
                pm = 0.03;
                tp = 150;
            }
            else if(i== 2){
                pc = 0.9;
                pm = 0.03;
                tp = 100;
            }
            else if(i== 3){
                pc = 0.9;
                pm = 0.03;
                tp = 150;
            }
            else if(i== 4){
                pc = 0.6;
                pm = 0.07;
                tp = 100;
            }
            else if(i== 5){
                pc = 0.6;
                pm = 0.07;
                tp = 150;
            }
            else if(i== 6){
                pc = 0.9;
                pm = 0.07;
                tp = 100;
            }
            else if(i== 7){
                pc = 0.9;
                pm = 0.07;
                tp = 100;
            } 
                 
            HashMap  parameters2 ; 
            parameters2 = new HashMap() ;
            
            algorithm[i].setInputParameter("populationSize",tp);
            algorithm[i].setInputParameter("maxEvaluations",tp*10000);

            // Mutation and Crossover for Real codification 
            
            parameters2.put("probability", pc) ;
            parameters2.put("distributionIndex", 20.0) ;
            crossover = CrossoverFactory.getCrossoverOperator("SinglePointCrossover", parameters2);                   

            parameters2 = new HashMap() ;
            parameters2.put("probability", pm) ;
            parameters2.put("distributionIndex", 20.0) ;
            mutation = MutationFactory.getMutationOperator("BitFlipMutation", parameters2);                    

            // Selection Operator 
            parameters = null ;
            selection = SelectionFactory.getSelectionOperator("BinaryTournament2", parameters2) ;                           

            // Add the operators to the algorithm
            algorithm[i].addOperator("crossover",crossover);
            algorithm[i].addOperator("mutation",mutation);
            algorithm[i].addOperator("selection",selection);
        }    
        
    } catch (IllegalArgumentException ex) {
      Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
    } catch (JMException ex) {
      Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
    }
  } // algorithmSettings
  
  
  
  
  // #####################  MAIN ############################################# 
  
  public static void main(String[] args) throws JMException, IOException {
    NSGAIIStudy exp = new NSGAIIStudy() ; // exp = experiment
    
    exp.experimentName_  = "NSGAIIStudy" ;
    
    // Se debe agregar un nombre por cada configuracion/algoritmo que se desea
    // correr, los resultados se guardaran en carpetas con los nombres aqui 
    // especificados
    exp.algorithmNameList_   = new String[] {
      "Configuracion1", "Configuracion2", "Configuracion3", "Configuracion4",
      "Configuracion5","Configuracion6","Configuracion7","Configuracion8"} ;
    
    // Lista de problemas a ejecutar, es MUY IMPORTANTE setear correctamente los
    // nombres porque Factory los tiene parseados, en caso de ser un problema
    // custom, debe agregarse en el archivo 
    exp.problemList_     = new String[] { "PlanningProblem" };
    
    // Nombre con el cual se guardara el frente de pareto
    exp.paretoFrontFile_ = new String[] { "PlanningProblem.pf" };
    
    // Indicadores a utilizar, MUY IMPORTANTE RESPETAR NOMBRES
    exp.indicatorList_   = new String[] {"HV", "SPREAD", "IGD", "EPSILON"};
    
    
    // EL numero de algoritmos corresponde a la lista dada, se inicializa el
    // arreglo de algoritmo en funcion de cuantos nombres se hayan especificado
    int numberOfAlgorithms = exp.algorithmNameList_.length ;

    
    // Path al directorio donde se desea tener los resultados del experimento
    exp.experimentBaseDirectory_ = 
        "/home/mathias/Documentos/RussiaPlanningAE/RussiaPlanningNSGAII/" +
        exp.experimentName_;
    

    // Path vacio significa que genere automaticamente el frente de pareto
    exp.paretoFrontDirectory_ = "";
    
    exp.algorithmSettings_ = new Settings[numberOfAlgorithms] ;
    
    // Numero de ejecuciones independientes que se desean realizar
    exp.independentRuns_ = 20 ;

    
    // Inicializar el experimento
    exp.initExperiment();

    // Run the experiments, con la cantidad de hilos especificados por la 
    // siguiente variable
    int numberOfThreads = 1;
    exp.runExperiment(numberOfThreads) ;
    
    // Generar indicadores, HV, SPREAD, etc ..
    exp.generateQualityIndicators() ;
    
    // Generate latex tables (comment this sentence is not desired)
    exp.generateLatexTables() ;
    
    // Configure the R scripts to be generated
    int rows  ;
    int columns  ;
    String prefix ;
    String [] problems ;

    rows = 2 ;
    columns = 3 ;
    prefix = new String("Problems");
    problems = new String[]{"PlanningProblem"} ;

    boolean notch ;
    exp.generateRBoxplotScripts(rows, columns, problems, prefix, notch = true, exp) ;
    exp.generateRWilcoxonScripts(problems, prefix, exp) ;

    // Applying Friedman test
    Friedman test = new Friedman(exp);
    test.executeTest("EPSILON");
    test.executeTest("HV");
    test.executeTest("SPREAD");
    
    
  } // main
} // NSGAIIStudy


