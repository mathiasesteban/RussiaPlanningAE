/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningrusia;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.util.JMException;

/**
 *
 * @author mathias
 */
public class Checker {
    
    
    // Constructor
    
    public Checker(){}
    
    
    
    public void fix_solution(Solution sol) throws JMException{
        
        Problem prob = sol.getProblem();
        
        Variable[] variables = sol.getDecisionVariables();
        
        int last_index = -1;
        
        for (int i = 0; i< sol.numberOfVariables(); i++){
            
            if(variables[i].getValue() != 0){
                
                if (last_index == -1){
                    last_index = i;
                }
                else{
                    //si se cumple restriccion de tiempo
//                    if () {
//                        last_index = i;
//                        prob.
//                    }
//                    else {
//                        variables[i].setValue(0);
//                    }
                }
                
                
            }
            
        }

    } // Fin fix
    
    
//    public void fix_solution(Solution sol){
//        System.out.println("Fixing");
//        // Nada por ahora
//        
//    }

    
}
