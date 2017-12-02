/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningrusia;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.encodings.solutionType.ArrayIntSolutionType;
import jmetal.encodings.solutionType.IntSolutionType;
import jmetal.encodings.variable.Int;
import jmetal.util.JMException;

/**
 *
 * @author mathias
 */
public class PlanningProblem extends Problem {
    
    // CONSTRUCTOR
    public PlanningProblem(Integer numberOfVariables){
        
        numberOfVariables_  = numberOfVariables;
        numberOfObjectives_ =  2;
        numberOfConstraints_=  0;
        problemName_        = "PlanningProblem";
        
        upperLimit_ = new double[numberOfVariables_];
        lowerLimit_ = new double[numberOfVariables_];
        
        for (int var = 0; var < numberOfVariables_; var++){
          lowerLimit_[var] = 0;
          upperLimit_[var] = 3;
        }
        
        solutionType_ = new IntSolutionType(this) ;
        
    }
    
    
    @Override
    public void evaluate(Solution solution) throws JMException {
        
        double eval_costo = 1;
        double eval_punt = 1;
        
        solution.setObjective(0,eval_costo);
        solution.setObjective(1,eval_punt);
        
    }
    
    
    public double evaluacion_costo(Solution solution){
        double res = 1;
        
        return res;
        
    }
    
    public double evaluacion_puntaje(Solution solution){
        double res = 1;
        
        return res;
    }
}
