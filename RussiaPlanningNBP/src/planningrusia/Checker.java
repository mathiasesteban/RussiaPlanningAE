/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningrusia;

import java.util.Calendar;
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
                    float [] [] tiempos_viajes;
                    tiempos_viajes = ((PlanningProblem)prob).getTiemposViaje();
                    Calendar [] fechas_partidos;
                    fechas_partidos = ((PlanningProblem) prob).getFechasPartidos();
                    Calendar fecha_primer_partido = fechas_partidos[last_index];
                    Calendar fecha_segundo_partido = fechas_partidos[i];
                    fecha_primer_partido.add(Calendar.HOUR, 3);
                    fecha_primer_partido.add(Calendar.MINUTE,(int)(tiempos_viajes[last_index][i])*60);
                   
                    //si se cumple restriccion de tiempo
                    if (fecha_primer_partido.compareTo(fecha_segundo_partido) < 0) {
                        last_index = i;
                    }
                    //no cumple restriccion
                    else {
                        variables[i].setValue(0);
                    }
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
