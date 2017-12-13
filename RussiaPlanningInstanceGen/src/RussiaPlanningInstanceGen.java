
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author mathias
 */
public class RussiaPlanningInstanceGen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        Scanner scanner = new Scanner(new File("semilla"));
        
        int numberOfVariables;
        int tiposEntradas;
        int precioEntrada_lowrange;
        int precioEntrada_highrange;
        int cantCiudades;
        int precioEstadia_lowrange;
        int precioEstadia_highrange;
        int relevancia_lowrange;
        int relevancia_highrange;
        int relevancia_pond;
        int cantpartidos_pond;
        int hours_lowrange;
        int hours_highrange;
        int precioViaje_lowrange;
        int precioViaje_highrange;
        int tiempoViaje_lowrange;
        int tiempoViaje_highrange;
        
        // Leer Semilla
        scanner.nextLine();
        numberOfVariables = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        tiposEntradas = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        precioEntrada_lowrange = scanner.nextInt();
        precioEntrada_highrange = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        cantCiudades = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        precioEstadia_lowrange = scanner.nextInt();
        precioEstadia_highrange = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        relevancia_lowrange = scanner.nextInt();
        relevancia_highrange = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        relevancia_pond = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        cantpartidos_pond = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        hours_lowrange = scanner.nextInt();
        hours_highrange = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        precioViaje_lowrange = scanner.nextInt();
        precioViaje_highrange = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        tiempoViaje_lowrange = scanner.nextInt();
        tiempoViaje_highrange = scanner.nextInt();
        
        // Print semilla
        System.out.println(numberOfVariables);
        System.out.println(tiposEntradas);
        System.out.print(precioEntrada_lowrange);
        System.out.println(" " + precioEntrada_highrange);
        System.out.println(cantCiudades);
        System.out.print(precioEstadia_lowrange);
        System.out.println(" " + precioEstadia_highrange);
        System.out.print(relevancia_lowrange);
        System.out.println(" " + relevancia_highrange);
        System.out.println(relevancia_pond);
        System.out.println(cantpartidos_pond);
        System.out.print(hours_lowrange);
        System.out.println(" " + hours_highrange);
        System.out.print(precioViaje_lowrange);
        System.out.println(" " + precioViaje_highrange);
        System.out.print(tiempoViaje_lowrange);
        System.out.println(" " + tiempoViaje_highrange);
        
        // Generar instancia
        
        PrintWriter out = new PrintWriter("instancia");
        
        out.println("#NumeroVariables");
        out.println(numberOfVariables);
        out.println("#TiposEntradas");
        out.println(tiposEntradas);
        out.println("#NumberOfObjectives");
        out.println("2");
        out.println("#NumberOfConstraints");
        out.println("1");
        out.println("#CostoEntradas");
        
        for (int i = 0; i < numberOfVariables; i++){
            for (int j = 0; j < tiposEntradas; j++){
                Random r = new Random();
                int value = r.nextInt(precioEntrada_highrange - precioEntrada_lowrange) + precioEntrada_lowrange;
                out.print(value + " ");
            }
            out.println();
        }
        out.println("#Cantidad de ciudades");
        out.println(cantCiudades);
        out.println("#Nombre de las ciudades");
        
        for (int i = 0; i < cantCiudades ; i++){
            out.print("Ciudad ");
            out.println(i);
        }
        
        out.println("#Costo de la estadia por ciudad");
        
        for (int i = 0; i < cantCiudades ; i++){
            Random r = new Random();
            int value = r.nextInt(precioEstadia_highrange - precioEstadia_lowrange) + precioEstadia_lowrange;
            out.println(value + " ");
        }

        out.println("#Fechas de eventos - DIA MES AÑO HORAS MINUTOS");
        
        Calendar calendar = Calendar.getInstance();
        
        for (int i =0; i < numberOfVariables ; i++){
            Random r = new Random();
            int value = r.nextInt(hours_highrange - hours_lowrange) + hours_lowrange;
            calendar.add(Calendar.HOUR_OF_DAY, value);
            
            out.print(calendar.get(Calendar.DAY_OF_MONTH) + " ");
            out.print(calendar.get(Calendar.MONTH) + " ");
            out.print(calendar.get(Calendar.YEAR) + " ");
            out.print(calendar.get(Calendar.HOUR_OF_DAY) + " ");
            out.print(calendar.get(Calendar.MINUTE) + "\n");
        }
        
        out.println("#Ciudad donde se realiza cada partido");
        
        for (int i = 0; i < numberOfVariables; i++){
            Random r = new Random();
            int value = r.nextInt(cantCiudades);
            
            out.println(value);
            
        }
        
        out.println("#Relevancias por partido y tipo de entrada");
        
        for(int i = 0 ; i < numberOfVariables; i++){
            
            Random r = new Random();
            int value = r.nextInt(relevancia_highrange - relevancia_lowrange) + relevancia_lowrange;
            
            for (int j= 0; j < tiposEntradas ; j++){
                
                value = r.nextInt(relevancia_highrange - value) + value;
                out.print(value + " ");
            }
            
            out.println();
        }
        
        out.println("# Peso de la relevancia del partido sobre el puntaje de la solucion ( valor / 10)");
        out.println(relevancia_pond);
        out.println("# Peso de la cantidad de partidos sobre el puntaje de la solucion (valor / 10)");
        out.println(cantpartidos_pond);
        out.println("#Costo de vuelos entre ciudades");
        
        for (int i = 0; i < cantCiudades ; i ++){
            for (int j = 0; j < cantCiudades; j++){
                
                if (i == j){
                    out.print("0 ");
                }
                else{
                    Random r = new Random();
                    int value = r.nextInt(precioViaje_highrange - precioViaje_lowrange) + precioViaje_lowrange;
                    out.print(value + " ");
                }
            }
            out.println();
        }
        
        out.println("#Tiempo de vuelo entre ciudades");
        
        for (int i = 0; i < cantCiudades ; i ++){
            for (int j = 0; j < cantCiudades; j++){
                
                if (i == j){
                    out.print("0 ");
                }
                else{
                    Random r = new Random();
                    float value = r.nextInt(tiempoViaje_highrange - tiempoViaje_lowrange) + tiempoViaje_lowrange;
                    out.print(value + " ");
                }
            }
            out.println();
        }
        
        out.close();
    }
    
}
