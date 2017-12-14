//  NSGAII_main.java
//
//  Author:
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

package jmetal.metaheuristics.nsgaII;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.selection.SelectionFactory;
import jmetal.problems.ProblemFactory;
import jmetal.problems.ZDT.ZDT3;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.Configuration;
import jmetal.util.JMException;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import planningrusia.PlanningProblem;

/** 
 * Class to configure and execute the NSGA-II algorithm.  
 *     
 * Besides the classic NSGA-II, a steady-state version (ssNSGAII) is also
 * included (See: J.J. Durillo, A.J. Nebro, F. Luna and E. Alba 
 *                  "On the Effect of the Steady-State Selection Scheme in 
 *                  Multi-Objective Genetic Algorithms"
 *                  5th International Conference, EMO 2009, pp: 183-197. 
 *                  April 2009)
 */ 

public class NSGAII_main {
  public static Logger      logger_ ;      // Logger object
  public static FileHandler fileHandler_ ; // FileHandler object

  /**
   * @param args Command line arguments.
   * @throws JMException 
   * @throws IOException 
   * @throws SecurityException 
   * Usage: three options
   *      - jmetal.metaheuristics.nsgaII.NSGAII_main
   *      - jmetal.metaheuristics.nsgaII.NSGAII_main problemName
   *      - jmetal.metaheuristics.nsgaII.NSGAII_main problemName paretoFrontFile
   */
  public static void main(String [] args) throws 
                                  JMException, 
                                  SecurityException, 
                                  IOException, 
                                  ClassNotFoundException {
     
    int j = 0;
    
    double pc = 0;
    double pm = 0;
    int tp = 0;
    
    String instance_name = "instancia1.config";
    String pareto_front = "front1.FUN";
    
    // Configuracion parametricas, cambiar la variable j 
    
    if ( j== 0){
        pc = 0.6;
        pm = 0.03;
        tp = 100;
    }
    else if(j== 1){
        pc = 0.6;
        pm = 0.03;
        tp = 150;
    }
    else if(j== 2){
        pc = 0.9;
        pm = 0.03;
        tp = 100;
    }
    else if(j== 3){
        pc = 0.9;
        pm = 0.03;
        tp = 150;
    }
    else if(j== 4){
        pc = 0.6;
        pm = 0.07;
        tp = 100;
    }
    else if(j== 5){
        pc = 0.6;
        pm = 0.07;
        tp = 150;
    }
    else if(j== 6){
        pc = 0.9;
        pm = 0.07;
        tp = 100;
    }
    else if(j== 7){
        pc = 0.9;
        pm = 0.03;
        tp = 150;
    } 
    
    PrintWriter out = new PrintWriter("Configuracion" + j + "-Indicadores");
    
    
    // Cantidad de ejecuciones
    for (int i = 0; i < 1 ; i++){
    
        Problem   problem   ; // The problem to solve
        Algorithm algorithm ; // The algorithm to use
        Operator  crossover ; // Crossover operator
        Operator  mutation  ; // Mutation operator
        Operator  selection ; // Selection operator

        HashMap  parameters ; // Operator parameters

        QualityIndicator indicators ; // Object to get quality indicators

        // Logger object and file to store log messages
        logger_      = Configuration.logger_ ;
        fileHandler_ = new FileHandler("NSGAII_main.log"); 
        logger_.addHandler(fileHandler_) ;
        
        // Problema con path a la isntancia a evaluar
        problem = new PlanningProblem(instance_name);
        
        // Indicador con path al archivo con frentes de pareto
        indicators = new QualityIndicator(problem,pareto_front) ;

        algorithm = new NSGAII(problem);

        // Algorithm parameters
        algorithm.setInputParameter("populationSize",tp);
        algorithm.setInputParameter("maxEvaluations",tp*10000);

        // Mutation and Crossover for Real codification 
        parameters = new HashMap() ;
        parameters.put("probability", pc) ;
        parameters.put("distributionIndex", 20.0) ;
        crossover = CrossoverFactory.getCrossoverOperator("SinglePointCrossover", parameters);                   

        parameters = new HashMap() ;
        parameters.put("probability", pm) ;
        parameters.put("distributionIndex", 20.0) ;
        mutation = MutationFactory.getMutationOperator("BitFlipMutation", parameters);                    

        // Selection Operator 
        parameters = null ;
        selection = SelectionFactory.getSelectionOperator("BinaryTournament2", parameters) ;                           

        // Add the operators to the algorithm
        algorithm.addOperator("crossover",crossover);
        algorithm.addOperator("mutation",mutation);
        algorithm.addOperator("selection",selection);

        // Add the indicator object to the algorithm
        algorithm.setInputParameter("indicators", indicators) ;

        // Execute the Algorithm
        long initTime = System.currentTimeMillis();
        SolutionSet population = algorithm.execute();
        long estimatedTime = System.currentTimeMillis() - initTime;

        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss.SSS");
        String actualDate = dateFormat.format(date);

        // Result messages 
        logger_.info("Total execution time: "+estimatedTime + "ms");
        logger_.info("Variables values have been writen to file VAR");
        population.printVariablesToFile("Configuracion" + j + "-" + actualDate + ".VAR");    
        logger_.info("Objectives values have been writen to file FUN");
        population.printObjectivesToFile("Configuracion" + j + "-" + actualDate + ".FUN");

        if (indicators != null) {
            
          double hv = indicators.getHypervolume(population);
          double gd = indicators.getGD(population);
          double igd = indicators.getIGD(population);
          double spread = indicators.getSpread(population);
          double eps = indicators.getEpsilon(population);
            
            
          logger_.info("Quality indicators") ;
          logger_.info("Hypervolume: " + hv) ;
          logger_.info("GD         : " + gd) ;
          logger_.info("IGD        : " + igd) ;
          logger_.info("Spread     : " + spread) ;
          logger_.info("Epsilon    : " + eps) ;  

          int evaluations = ((Integer)algorithm.getOutputParameter("evaluations")).intValue();
          logger_.info("Speed      : " + evaluations + " evaluations") ;   
          
          out.println(hv + " " + gd + " " + igd + " " +spread+ " " +eps);
          
        } // if
    
    } // End for ejecuciones
    out.close();
  } //main
} // NSGAII_main
