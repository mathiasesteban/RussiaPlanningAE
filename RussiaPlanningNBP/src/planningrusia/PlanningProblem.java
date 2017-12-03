/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningrusia;

import java.util.Vector;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.encodings.solutionType.ArrayIntSolutionType;
import jmetal.encodings.solutionType.IntSolutionType;
import jmetal.encodings.variable.Int;
import jmetal.util.JMException;

/**
 *
 * @author mathias
 */
public class PlanningProblem extends Problem {
    
    // Costo de las entradas por categorias
    private int[][] costo_entrada;
    
    // Costo de la estadia en la ciudad donde se realiza el evento
    private int[] costo_estadia;
    
    // Fecha de realizacion del evento
    private int[] fecha;
    
    // Costo viaje entre ciudades
    private int[][] costo_viaje;
    
    // Ciudades
    private int[] ciudades;
    
    // Constructor
    public PlanningProblem(Integer numberOfVariables){
        
        // ---------------------- DEFINICION DEL PROBLEMA ---------------------
        
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
        
        // --------------------------------------------------------------------
        
        
        // --------------------------- DATOS ----------------------------------
        
        // Estas variables se leeran desde un archivo de configuracion
        costo_entrada = new int[64][3];
        
        for (int i =0; i<64; i++){
            
            if ( i == 0){
                costo_entrada[i][0] = 550;
                costo_entrada[i][1] = 390;
                costo_entrada[i][2] = 220;
            }
            else if ( i>0 && i<48){
                costo_entrada[i][0] = 210;
                costo_entrada[i][1] = 165;
                costo_entrada[i][2] = 105;
            } 
            else if (i>47 && i <56){
                costo_entrada[i][0] = 245;
                costo_entrada[i][1] = 185;
                costo_entrada[i][2] = 115;
            }
            else if (i>55 && i <60){
                costo_entrada[i][0] = 365;
                costo_entrada[i][1] = 255;
                costo_entrada[i][2] = 175;
            }
            else if (i>59 && i < 62){
                costo_entrada[i][0] = 750;
                costo_entrada[i][1] = 480;
                costo_entrada[i][2] = 285;
            }
            else if ( i == 62){
                costo_entrada[i][0] = 365;
                costo_entrada[i][1] = 255;
                costo_entrada[i][2] = 175;
            }
            else{
                costo_entrada[i][0] = 1100;
                costo_entrada[i][1] = 710;
                costo_entrada[i][2] = 455;
            }
        }
        
        costo_estadia = new int[64];
        
        for (int i =0; i<64; i++){
            costo_estadia[i] = 100;
            
        }
        
        // --------------------------------------------------------------------
   
    } // Fin constructor
    
    
    @Override
    public void evaluate(Solution solution) throws JMException {
        
        double eval_costo = evaluacion_costo(solution);
        double eval_punt = 1;
        
        solution.setObjective(0,eval_costo);
        solution.setObjective(1,eval_punt);
        
    } // Fin evaluate
    
    
    public double evaluacion_costo(Solution solution){
        double costo = 0;
        
        int last_city = -1;
        
        Variable[] partidos = solution.getDecisionVariables();
        
        for (int i = 0; i< numberOfVariables_;i++){
            
            int cat_entrada = (int)((Int)partidos[i]).getValue(); 
            
            if(cat_entrada != 0){
                costo += costo_entrada[i][cat_entrada-1];
                
                if(last_city != -1){
                    costo += eval_costo_estadia();
                    costo += eval_costo_viaje();
                }
                
                last_city = i;
            }
        }
        
        return costo;
        
    } // Fin evaluacion_costo
    
    
    public double eval_costo_viaje(){
        double costo = 0;
        
        // Falta
        
        return costo;
    } // Fin eval_costo_viaje
    
    public double eval_costo_estadia(){
        double costo = 0;
        
        // Falta
        
        return costo;
    } // Fin eval_costo_estadia
    
    
    
    public double evaluacion_puntaje(Solution solution){
        double res = 1;
        
        // Falta
        
        return res;
    } // Fin evaluacion_puntaje
}
