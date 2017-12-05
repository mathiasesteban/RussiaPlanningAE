/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningrusia;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
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
    private Calendar[] fechas;
    
    // Ciudades donde se realizan los eventos
    private int[] ciudades;
            
    // Costo viaje entre ciudades
    private int[][] costo_viaje;
    
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
        
        fechas = new Calendar[numberOfVariables];
        
        scanner.nextLine();
        scanner.nextLine();
        
        for (int i =0; i<numberOfVariables; i++){
            fechas[i] = Calendar.getInstance();
            fechas[i].set(Calendar.DAY_OF_MONTH,scanner.nextInt()-1);
            fechas[i].set(Calendar.MONTH,scanner.nextInt()-1);
            fechas[i].set(Calendar.YEAR,scanner.nextInt());
            fechas[i].set(Calendar.HOUR_OF_DAY,scanner.nextInt());
            fechas[i].set(Calendar.MINUTE,scanner.nextInt());
        }
        
        for (int i =0; i<numberOfVariables; i++){
            System.out.println(fechas[i].getTime());
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
    
    
    public double eval_costo_viaje()
    {
        double costo = 0;
        
        // Falta
        
        return costo;
    } // Fin eval_costo_viaje
    
    public double eval_costo_estadia()
    {
        double costo = 0;
        
        // Falta
        
        return costo;
    } // Fin eval_costo_estadia
    
    
    
    public double evaluacion_puntaje(Solution solution) throws JMException
    {
        double puntaje = 0;
        /* el puntaje es una combinacion de 3 variables que involucra la relevancia de los partidos a
        los que se asiste, la cantidad de partidos y la categoria de entrada de cada paritido al 
        que se asiste*/
        double pesoRelevancia = 0.4;
        double pesoCantPartidos = 0.4;
        double [] cantCats = new double[3];
        double [] pesoCantCats = {0.2,0.3,0.5};
        double cantPartidos = 0;
        //double relevancia [] = new double [6];
        //ver el tema de las ponderaciones 
        double relevancia = 0;
        int cantVar = this.numberOfVariables_;
        Variable [] variables = solution.getDecisionVariables();
        for (int i = 0; i< cantVar; i++)
        {
            if (variables[i].getValue() != 0){
                cantPartidos ++;
                if (variables[i].getValue() == 1){
                    cantCats[0]++;
                    relevancia += relevancia(i);
                    
                }
                else if (variables[i].getValue() == 2){
                    cantCats[1]++;
                    relevancia += relevancia(i);
                }
                else {
                    cantCats[2]++;
                    relevancia += relevancia(i);
                }
            }
            else{
                continue;
            }
        }
        puntaje = cantPartidos*pesoCantPartidos + relevancia*pesoRelevancia + cantCats[0]*pesoCantCats[0] + cantCats[1]*pesoCantCats[1] +cantCats[2]*pesoCantCats[2];
        return puntaje;
    } // Fin evaluacion_puntaje
    
    public double relevancia (double posicion)
    {
        double relevancia = 0;
        if(posicion >=0 && posicion < 48){
            relevancia = 1; 
        }
        else if (posicion >=48 && posicion < 55){
            relevancia = 2;
        }
        else if (posicion >= 55 && posicion < 59){
            relevancia = 3;
        }
        else if (posicion >= 59 && posicion < 61){
            relevancia = 4;
        }
        else if (posicion >= 61 && posicion < 62){
            //partido por el 3er 
            relevancia = 3;
        }
        else{
            //es la final papa!! el suenio de todo el mundo
            relevancia = 20;
        }
        return relevancia;
    }
}
