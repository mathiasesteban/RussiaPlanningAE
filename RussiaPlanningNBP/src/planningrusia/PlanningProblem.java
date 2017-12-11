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
    private String[] ciudades;
    
    private int[] ciudad_partido;
    
    // Relevancias por tipo de entrada para cada partido
    private int[][] relevanciaPartidos;
    
    // Costo viaje entre ciudades
    private int[][] costos_viaje;
    
    // Tiempo vuelo entre ciudades (en horas)
    private float[][] tiempos_viaje;
    
    private float pesoRelevancia;
    private float pesoCantPartidos;
    
    // Constructor
    public PlanningProblem(String pathToFile) throws FileNotFoundException{
        
        // -------------------------- CARGA DATOS -----------------------------
        
        Scanner scanner = new Scanner(new File(pathToFile));
        
        // Esta instruccion es utilizada para avanzar el cursor a la siguiente
        // linea
        scanner.nextLine();
        
        // Leo el numero de variables
        int numberOfVariables = scanner.nextInt();    
     
        scanner.nextLine();
        scanner.nextLine();
        
        // Leo los tipos de entradas que existen
        int tipos_entrada = scanner.nextInt();
        
        scanner.nextLine();
        scanner.nextLine();
        
        // Leo la cantidad de objetivos
        int numberOfObjetives = scanner.nextInt();
        
        scanner.nextLine();
        scanner.nextLine();
        
        // Leo la cantidad de restricciones
        int numberOfConstraints = scanner.nextInt();
        
        costo_entrada = new int[numberOfVariables][tipos_entrada];
        
        scanner.nextLine();
        scanner.nextLine();
        
        // Leo los costos de las entradas para cada evento y para cada tipo 
        for (int j = 0; j < numberOfVariables; j++ ){
            for(int k = 0; k < tipos_entrada ; k++){
                costo_entrada[j][k] = scanner.nextInt();
            }
        }
        
        scanner.nextLine();
        scanner.nextLine();
        
        // Leo la cantidad de ciudades
        int numberOfCities = scanner.nextInt();
        
        scanner.nextLine();
        scanner.nextLine();
        
        ciudades = new String[numberOfCities];
        
        // Leo las ciudades donde ocurren los eventos
        for (int i =0; i<numberOfCities; i++){
            ciudades[i] = scanner.nextLine();
        }
        
        scanner.nextLine();
        
        costo_estadia = new int[numberOfCities];
        
        // Leo los costos de alojamiento en cada ciudad
        for (int i =0; i<numberOfCities; i++){
            costo_estadia[i] = scanner.nextInt();
        }
         
        fechas = new Calendar[numberOfVariables];
        
        scanner.nextLine();
        scanner.nextLine();
        
        // Leo las fechas en las que se realizan los eventos
        for (int i =0; i<numberOfVariables; i++){
            fechas[i] = Calendar.getInstance();
            fechas[i].set(Calendar.DAY_OF_MONTH,scanner.nextInt());
            fechas[i].set(Calendar.MONTH,scanner.nextInt()-1);
            fechas[i].set(Calendar.YEAR,scanner.nextInt());
            fechas[i].set(Calendar.HOUR_OF_DAY,scanner.nextInt());
            fechas[i].set(Calendar.MINUTE,scanner.nextInt());
        }

        scanner.nextLine();
        scanner.nextLine();
        
        ciudad_partido = new int[numberOfVariables];
        
        // Leo las ciudades en las que se realizan los eventos
        for (int i =0; i<numberOfVariables; i++){
            ciudad_partido[i] = scanner.nextInt();
        }
        
        scanner.nextLine();
        scanner.nextLine();
        
        relevanciaPartidos = new int[numberOfVariables][tipos_entrada];
        
        for (int j = 0; j < numberOfVariables; j++ ){
            for(int k = 0; k < tipos_entrada ; k++){
                relevanciaPartidos[j][k] = scanner.nextInt();
            }
        }
        
        scanner.nextLine();
        scanner.nextLine();
        
        pesoRelevancia = (float) scanner.nextInt() / 10;
        
        scanner.nextLine();
        scanner.nextLine();
        
        pesoCantPartidos = (float) scanner.nextInt() / 10;
        
        scanner.nextLine();
        scanner.nextLine();
        
        costos_viaje = new int[numberOfCities][numberOfCities];
        
        for (int i =0; i<numberOfCities; i++){
            for (int j =0; j<numberOfCities; j++){
                costos_viaje[i][j] = scanner.nextInt();
            }
        }
        
        scanner.nextLine();
        scanner.nextLine();
        
        tiempos_viaje = new float[numberOfCities][numberOfCities];
        
        
        for (int i =0; i<numberOfCities; i++){
            for (int j =0; j<numberOfCities; j++){
                tiempos_viaje[i][j] = scanner.nextFloat();
            }
        }
        
             
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
        
        
        // --------------------------- PRINTS ---------------------------------
        
        System.out.println();
        System.out.println(" ----------- DATOS DEL PROBLEMA ------------ \n");
        
        System.out.println("Numero de eventos: " + numberOfVariables);
        System.out.println("Tipos de entrada disponibles: " + tipos_entrada);
        System.out.println("Numero de objetivos del problema: " + numberOfObjetives);
        System.out.println("Numero de restricciones: " + numberOfConstraints);
        
        System.out.println();
        
        System.out.println("Partido - Costo Entradas - Fecha - Ciudad - Relevancia entradas\n" );
        
        for (int j = 0; j < numberOfVariables; j++ ){
            
            System.out.print(j + " - ");
            
            for(int k = 0; k < tipos_entrada ; k++){
                System.out.print(costo_entrada[j][k]);
                System.out.print(" ");
            }
            
            System.out.print(" - ");
            
            System.out.print(fechas[j].getTime());
            
            System.out.print(" - ");
            
            System.out.print(ciudad_partido[j]);
            
            System.out.print(" - ");
            
            for(int k = 0; k < tipos_entrada ; k++){
                System.out.print(relevanciaPartidos[j][k]);
                System.out.print(" ");
            }
            
            System.out.println();
        }
        
        System.out.println();
        System.out.println("Indice ciudad - Nombre Ciudad - Costo estadia\n");
        
        
        for (int j = 0; j < numberOfCities; j++ ){
            System.out.print(j + " - ");
            System.out.print(ciudades[j] + " - ");
            System.out.println( "USS " + costo_estadia[j]);
        }
        
        System.out.println();
        System.out.println("Peso de la relevancia del partido sobre el puntaje de la solucion: " + pesoRelevancia);
        System.out.println();
        System.out.println("Peso de la cantidad de partidos sobre el puntaje de la solucion: " + pesoCantPartidos);
        System.out.println();
        
        
        System.out.println("Matriz de costos de viaje: \n");
        
        for (int j = 0; j < numberOfCities; j++ ){
            for (int i = 0; i < numberOfCities; i++ ){
                System.out.print( costos_viaje[j][i] + " ");
            }
            
            System.out.println();
        }
        
        
        System.out.println("\nMatriz de tiempos de viaje: \n");
        
        for (int j = 0; j < numberOfCities; j++ ){
            for (int i = 0; i < numberOfCities; i++ ){
                System.out.print( tiempos_viaje[j][i] + " ");
            }
            
            System.out.println();
        }
        System.out.println("\n------------------------------------------\n");
        
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
        
        int last_index = -1;
        
        Variable[] partidos = solution.getDecisionVariables();
        
        for (int i = 0; i< numberOfVariables_;i++){
            
            int cat_entrada = (int)((Int)partidos[i]).getValue(); 
            
            if(cat_entrada != 0){
                costo += costo_entrada[i][cat_entrada-1];
                
                if(last_index != -1){
                    costo += eval_costo_estadia(i,last_index);
                    costo += eval_costo_viaje(i,last_index);
                }
                
                last_index = i;
            }
        }
        
        return costo;
        
    } // Fin evaluacion_costo
    
    
    public double eval_costo_viaje(int partido, int partido_anterior)
    {
        double costo = costos_viaje[ciudad_partido[partido_anterior]][ciudad_partido[partido]];
        
        return costo;
    } // Fin eval_costo_viaje
    
    public double eval_costo_estadia(int partido, int partido_anterior)
    {
        double costo = 0;
        
        long seconds = (fechas[partido].getTimeInMillis() - fechas[partido_anterior].getTimeInMillis()) / 1000;
        int days = (int) ((seconds / 3600)/24);
        
        costo = days*costo_estadia[ciudad_partido[partido_anterior]];
        return costo;
        
    } // Fin eval_costo_estadia
    
    
    
    public double evaluacion_puntaje(Solution solution) throws JMException
    {
        double puntaje = 0;
        
         
        // El puntaje es una combinacion de 2 variables que involucra la 
        // relevancia y la cantidad de partidos a los que asiste
        
        
        double cantPartidos = 0;
        double relevancia = 0;
        int cantVar = this.numberOfVariables_;
    
        Variable [] variables = solution.getDecisionVariables();
        
        // Recorro la solucion
        for (int i = 0; i< cantVar; i++)
        {
            // Si asisto al evento influye en el puntaje
            if (variables[i].getValue() != 0){
                
                cantPartidos ++;
                
                if (variables[i].getValue() == 1){
                    int aux = (int) variables[i].getValue();
                    relevancia += relevanciaPartidos[i][aux];
                }            
                
            }
            
        }
        
        puntaje = cantPartidos * pesoCantPartidos + relevancia * pesoRelevancia;
        return puntaje;
        
    } // Fin evaluacion_puntaje
    
    public float[][] getTiemposViaje(){ 
        return this.tiempos_viaje;
    }
    
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

