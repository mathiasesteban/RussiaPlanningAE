/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningrusia;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
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
    public PlanningProblem(String pathToFile) throws FileNotFoundException{
        
        Scanner scanner = new Scanner(new File(pathToFile));
        
        scanner.nextLine();
        
        // Leo el numero de variables
        int numberOfVariables = scanner.nextInt();    
        System.out.println(numberOfVariables);
        
        scanner.nextLine();
        scanner.nextLine();
        
        // Leo los tipos de entradas que existen
        int tipos_entrada = scanner.nextInt();
        System.out.println(tipos_entrada);
        
        scanner.nextLine();
        scanner.nextLine();
        
        int numberOfObjetives = scanner.nextInt();
        System.out.println(numberOfObjetives);
        
        scanner.nextLine();
        scanner.nextLine();
        
        int numberOfConstraints = scanner.nextInt();
        System.out.println(numberOfConstraints);
        
        // ---------------------- DEFINICION DEL PROBLEMA ---------------------
        
        numberOfVariables_  = numberOfVariables;
        numberOfObjectives_ =  numberOfObjetives;
        numberOfConstraints_=  numberOfConstraints;
        problemName_        = "PlanningProblem";
        
        upperLimit_ = new double[numberOfVariables_];
        lowerLimit_ = new double[numberOfVariables_];
        
        for (int var = 0; var < numberOfVariables_; var++){
          lowerLimit_[var] = 0;
          upperLimit_[var] = tipos_entrada;
        }
        
        solutionType_ = new IntSolutionType(this) ;
        
        // --------------------------------------------------------------------
        
        
        // --------------------------- DATOS ----------------------------------
        
        
       
        costo_entrada = new int[numberOfVariables][tipos_entrada];
        
        scanner.nextLine();
        scanner.nextLine();
        
        for (int j = 0; j < numberOfVariables; j++ ){
            for(int k = 0; k < tipos_entrada ; k++){
                costo_entrada[j][k] = scanner.nextInt();
            }
        }
        
        scanner.nextLine();
        scanner.nextLine();
        
        costo_estadia = new int[numberOfVariables];
        
        for (int i =0; i<numberOfVariables; i++){
            costo_estadia[i] = scanner.nextInt();
            
        }
        
        for (int j = 0; j < numberOfVariables; j++ ){
            for(int k = 0; k < tipos_entrada ; k++){
                System.out.print(costo_entrada[j][k]);
                System.out.print(" ");
            }
            System.out.println("");
        }
        
        for (int j = 0; j < numberOfVariables; j++ ){
            System.out.println(costo_estadia[j]);
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
