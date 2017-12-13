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


/**
 *
 * @author mathias
 */
public class PlanningProblem {
    
    // Costo de las entradas por categorias
    private final int[][] costo_entrada;
    
    // Costo de la estadia en la ciudad donde se realiza el evento
    private final int[] costo_estadia;
    
    // Fecha de realizacion del evento
    private final Calendar[] fechas;
    
    // Ciudades donde se realizan los eventos
    private final String[] ciudades;
    
    private final int[] ciudad_partido;
    
    // Relevancias por tipo de entrada para cada partido
    private final int[][] relevanciaPartidos;
    
    // Costo viaje entre ciudades
    private final int[][] costos_viaje;
    
    // Tiempo vuelo entre ciudades (en horas)
    private final float[][] tiempos_viaje;
    
    private final float pesoRelevancia;
    private final float pesoCantPartidos;
    private final int criterio;
    private final int tamSol;
    
    // Constructor
    public PlanningProblem(String pathToFile) throws FileNotFoundException{
        
        // -------------------------- CARGA DATOS -----------------------------
        
        Scanner scanner = new Scanner(new File(pathToFile));
        
        // Esta instruccion es utilizada para avanzar el cursor a la siguiente
        // linea
        scanner.nextLine();
        
        // Leo el numero de variables
        int numberOfVariables = scanner.nextInt();
        tamSol = numberOfVariables;
     
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
        
        scanner.nextLine();
        scanner.nextLine();
        
        criterio = scanner.nextInt();
        
        
        
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
        System.out.println(criterio);
        System.out.println("\n------------------------------------------\n");
        
        // --------------------------------------------------------------------
   
    } // Fin constructor
    
    
    public float[][] getTiemposViaje(){ 
        return this.tiempos_viaje;
    }
    
    public Calendar[] getFechasPartidos(){
        return this.fechas;
    }
    
    public int[] getCiudadesPartidos(){
        return this.ciudad_partido;
    }
    
    public int getTamanioSolucion(){
        return this.tamSol;
    }
    
    public int getCriterio(){
        return this.criterio;
    }
    
    public int[][] getCostosEntradas(){
        return this.costo_entrada;
    }
    
    public float getPesoRelevancia(){
        return this.pesoRelevancia;
    }
    
    public float getPesoCantPartidos(){
        return this.pesoCantPartidos;
    }
    
    public int [] getCostosEstadia(){
        return this.costo_estadia;
    }
    
    public int [] [] getCostosViajes(){
        return this.costos_viaje;
    }
    
    public int [] [] getRelevanciaPartidos(){
        return this.relevanciaPartidos;
    }
    
}

