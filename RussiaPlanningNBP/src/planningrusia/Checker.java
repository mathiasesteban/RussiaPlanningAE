/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningrusia;

import java.util.Arrays;
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
    
    // Fix Solution
    public void fix_solution(Solution sol) throws JMException{
        
        Problem prob = sol.getProblem();
        
        Variable[] variables = sol.getDecisionVariables();
        
        int last_index = -1;
        
        // Necesario hacer copia de los array, si se trabaja con referencias
        // queda inconsistente el problema
        
        float [][] tiempos_viajes;
        tiempos_viajes = Arrays.copyOf(((PlanningProblem)prob).getTiemposViaje(),((PlanningProblem)prob).getTiemposViaje().length);
       
        Calendar [] fechas_partidos;
        fechas_partidos = Arrays.copyOf(((PlanningProblem)prob).getFechasPartidos(),((PlanningProblem)prob).getFechasPartidos().length);
        
        int [] ciudades_partidos;
        ciudades_partidos = Arrays.copyOf(((PlanningProblem)prob).getCiudadesPartidos(),((PlanningProblem)prob).getCiudadesPartidos().length);
        
        for (int i = 0; i< sol.numberOfVariables(); i++){
            
            if(variables[i].getValue() != 0){
                
                if (last_index == -1){
                    last_index = i;
                }
                else{
                    Calendar fecha_primer_partido = (Calendar) fechas_partidos[last_index].clone();
                    Calendar fecha_segundo_partido = (Calendar) fechas_partidos[i].clone();
                    
                    fecha_primer_partido.add(Calendar.HOUR, 3);
                    
                    int ciudad_lastindex = ciudades_partidos[last_index] ;
                    int ciudad_i = ciudades_partidos[i];
                    
                    fecha_primer_partido.add(Calendar.MINUTE,(int)(tiempos_viajes[ciudad_lastindex][ciudad_i])*60);
                   
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
      
}
