/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningrusia;

import java.io.FileNotFoundException;
import static java.lang.Math.abs;
import java.util.Arrays;
import java.util.Calendar;


/**
 *
 * @author mateo
 */
public class RussiaPlanningGreedy_main {
    
    private int [] solucion;
    private float puntajeFinal;
    private float costoFinal;
    private PlanningProblem prob;
    
    
    public RussiaPlanningGreedy_main() throws FileNotFoundException
    {
       this.prob = new PlanningProblem("datos.config");
       this.solucion = null;
       this.costoFinal = 0;
       this.puntajeFinal = 0;   
    }
    
    public void main(float criterio) throws FileNotFoundException 
    {
        int tamSolucion = prob.getTamanioSolucion();
        int [] solucion = new int[tamSolucion];
        for (int i=0; i < tamSolucion ; i++){
            solucion[i]=0;
        }
        float AcPuntaje = 0;
        float AcCosto = 0;
        int indiceAnt = -1;
        int [] aux;
        double costo = 0;
        double puntaje = 0;
        for (int i=0; i < tamSolucion; i++){
            if (indiceAnt == -1){
                indiceAnt = i;
                //de momento siempre se esta yendo al primer partido eso habria que solucionarlo
                AcCosto+= prob.getCostosEntradas()[i][0];
                AcPuntaje+= prob.getRelevanciaPartidos()[i][0];
                solucion[i]= 1;
            }
            else{
                if (checkFecha(prob,indiceAnt,i)){
                    for (int j=1; j <= 3; j++){
                       aux = Arrays.copyOf(solucion,tamSolucion);
                       aux[i]=j;
                       costo = evaluacion_costo(prob,aux);
                       puntaje = evaluacion_puntaje(prob,aux);
                       if (AcCosto > 0){
                           float relacionCosto = (float)(AcCosto/costo)*100;
                           float relacionPuntaje = (float)(AcPuntaje/puntaje)*100;
//                           System.out.println("arranca criterio" + j);
//                           System.out.println(criterio);
//                           System.out.println(costo);
//                           System.out.println(puntaje);
//                           System.out.println(relacionCosto);
//                           System.out.println(relacionPuntaje);
                           if( abs(relacionPuntaje - relacionCosto) >= criterio){
                               indiceAnt = i;
                               solucion[i]=j;
                               AcCosto = (int)costo;
                               AcPuntaje = (int)puntaje;
                            }
                        }
//                       
////                       else{
////                           AcCosto+= costo;
////                           AcPuntaje+= puntaje;
////                           
////                       }
                    }
                    
                } 
            }
        }
        // for hasta numero de variables
        // acumulador costo y acumulador puntaje
        // para cada iteracion si puedo ir pruebo con todos los tipos de entrada y si cumplo el criterio actulizo
        // y voy acutlizando el arreglo pero tambien llevando un arreglo auxiliar para ir probando
        //todas las evaluaciones que ya tengo
//        System.out.println("solucion");
//        
//        System.out.println("");
        this.solucion = solucion;
        this.costoFinal = (float) costo;
        this.puntajeFinal = (float) puntaje;

    }

    
    public static boolean checkFecha(PlanningProblem prob, int last_index, int current_index)
    {   
        boolean result = false;
        float [][] tiempos_viajes;
        tiempos_viajes = Arrays.copyOf(((PlanningProblem)prob).getTiemposViaje(),((PlanningProblem)prob).getTiemposViaje().length);
       
        Calendar [] fechas_partidos;
        fechas_partidos = Arrays.copyOf(((PlanningProblem)prob).getFechasPartidos(),((PlanningProblem)prob).getFechasPartidos().length);
        
        int [] ciudades_partidos;
        ciudades_partidos = Arrays.copyOf(((PlanningProblem)prob).getCiudadesPartidos(),((PlanningProblem)prob).getCiudadesPartidos().length);
        
        Calendar fecha_primer_partido = (Calendar) fechas_partidos[last_index].clone();
        Calendar fecha_segundo_partido = (Calendar) fechas_partidos[current_index].clone();

        fecha_primer_partido.add(Calendar.HOUR, 3);

        int ciudad_lastindex = ciudades_partidos[last_index] ;
        int ciudad_current_index = ciudades_partidos[current_index];

        fecha_primer_partido.add(Calendar.MINUTE,(int)(tiempos_viajes[ciudad_lastindex][ciudad_current_index])*60);

        //si se cumple restriccion de tiempo
        if (fecha_primer_partido.compareTo(fecha_segundo_partido) < 0) {
            result = true;
        }
    
        return result;
    }        
    
    
    public static double  evaluacion_costo(PlanningProblem prob, int [] solucion)
    {
        double costo = 0;
        int [] [] costo_entrada = prob.getCostosEntradas();
        
        int last_index = -1;
        
        for (int i = 0; i < prob.getTamanioSolucion();i++){ 
            
            if(solucion[i] != 0){
                costo += costo_entrada[i][3-solucion[i]];
                
                if(last_index != -1){
                    costo += eval_costo_estadia(prob,i,last_index);
                    costo += eval_costo_viaje(prob,i,last_index);
                }
                
                last_index = i;
            }
        }
        
        return costo;
        
    } // Fin evaluacion_costo
    
    
    public static double eval_costo_viaje(PlanningProblem prob ,int partido, int partido_anterior)
    {
        double costo = (prob.getCostosViajes())[prob.getCiudadesPartidos()[partido_anterior]][prob.getCiudadesPartidos()[partido]];
        
        return costo;
    } // Fin eval_costo_viaje
    
    public static double eval_costo_estadia(PlanningProblem prob, int partido, int partido_anterior)
    {
        double costo = 0;
        
        long seconds = (prob.getFechasPartidos()[partido].getTimeInMillis() - prob.getFechasPartidos()[partido_anterior].getTimeInMillis()) / 1000;
        int days = (int) ((seconds / 3600)/24);
        
        costo = days*prob.getCostosEstadia()[prob.getCiudadesPartidos()[partido_anterior]];
        return costo;
        
    } // Fin eval_costo_estadia
    
    
    
    public static double evaluacion_puntaje(PlanningProblem prob, int [] solution)
    {
        double puntaje = 0;
        
        // El puntaje es una combinacion de 2 variables que involucra la 
        // relevancia y la cantidad de partidos a los que asiste
        
        double cantPartidos = 0;
        double relevancia = 0;
        int cantVar = prob.getTamanioSolucion();
        
        // Recorro la solucion
        for (int i = 0; i< cantVar; i++)
        {
            // Si asisto al evento influye en el puntaje
            if (solution[i] != 0){
                
                cantPartidos ++;
                int aux = solution[i];
                relevancia += prob.getRelevanciaPartidos()[i][aux-1];
            }    
        }
       
      puntaje = cantPartidos * prob.getPesoCantPartidos() + relevancia * prob.getPesoRelevancia();
      return puntaje;        
    } // Fin evaluacion_puntaje
    
    public float getCostoFinal()
    {
        return this.costoFinal;
    }
    
    public float getPuntajeFinal()
    {
        return this.puntajeFinal;
    }
    
    public int [] getSolucion(){
        return this.solucion;
    }
}   //fin falso greedy