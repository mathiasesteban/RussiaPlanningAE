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
        double eval_punt = evaluacion_puntaje(solution);
        
        solution.setObjective(0,eval_costo);
        solution.setObjective(1,-eval_punt);
        
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
        double pesoRelevancia = 0.7;
        double pesoCantPartidos = 0.3;
        double cantPartidos = 0;
        double relevancia = 0;
        int cantVar = this.numberOfVariables_;
        double [][] relevanciaPartidos = new double[cantVar][3];
        cargarRelevancia(relevanciaPartidos,cantVar,3);
        // double [] cantCats = new double[3];
        // double [] pesoCantCats = {0.5,0.3,0.2};
        //double relevancia [] = new double [6];
        //ver el tema de las ponderaciones     
        Variable [] variables = solution.getDecisionVariables();
        for (int i = 0; i< cantVar; i++)
        {
            if (variables[i].getValue() != 0){
                cantPartidos ++;
                if (variables[i].getValue() == 1){
                    int aux = (int) variables[i].getValue();
                    relevancia += relevanciaPartidos[i][aux];
                }               
            }
            else{
                continue;
            }
        }
        puntaje = cantPartidos*pesoCantPartidos + relevancia*pesoRelevancia;
        return puntaje;
    } // Fin evaluacion_puntaje
    
    public void cargarRelevancia (double[][] matrizRelevancia,int largo,int ancho)
    {
        for (int i = 0; i< largo; i++){
            for (int j = 0; j< ancho; j++){
                if(i == 0){
                   if (j == 0){
                       matrizRelevancia[i][j]= 100;
                   }
                   else if (j == 1){
                       matrizRelevancia[i][j]= 125;
                   }
                   else{
                       matrizRelevancia[i][j]= 150;
                   }
                }
                else if(i >=1 && i < 48){
                   if (j == 0){
                       matrizRelevancia[i][j]= 80;
                   }
                   else if (j == 1){
                       matrizRelevancia[i][j]= 100;
                   }
                   else{
                       matrizRelevancia[i][j]= 120;
                   }
                }
                else if (i >=48 && i < 56){
                    if (j == 0){
                       matrizRelevancia[i][j]= 300;
                   }
                   else if (j == 1){
                       matrizRelevancia[i][j]= 370;
                   }
                   else{
                       matrizRelevancia[i][j]= 450;
                   }
                }
                else if (i >= 56 && i < 60){
                    if (j == 0){
                       matrizRelevancia[i][j]= 500;
                   }
                   else if (j == 1){
                       matrizRelevancia[i][j]= 625;
                   }
                   else{
                       matrizRelevancia[i][j]= 750;
                   }
                }
                else if (i >= 60 && i < 62){
                    if (j == 0){
                       matrizRelevancia[i][j]= 750;
                   }
                   else if (j == 1){
                       matrizRelevancia[i][j]= 900;
                   }
                   else{
                       matrizRelevancia[i][j]= 1025;
                   }
                }
                else if (i >= 62 && i < 63){
                    //partido por el 3er 
                    if (j == 0){
                       matrizRelevancia[i][j]= 500;
                   }
                   else if (j == 1){
                       matrizRelevancia[i][j]= 625;
                   }
                   else{
                       matrizRelevancia[i][j]= 750;
                   }
                }
                else{
                    //es la final papa!! el suenio de todo el mundo
                    if (j == 0){
                       matrizRelevancia[i][j]= 1500;
                   }
                   else if (j == 1){
                       matrizRelevancia[i][j]= 1800;
                   }
                   else{
                       matrizRelevancia[i][j]= 2100;
                   }
                }
            }
        }
    }
}

