/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningrusia;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author mateo
 */
public class main {
    public static void main(String [] args) throws FileNotFoundException, UnsupportedEncodingException {
        float CostoFinal = 0;
        float puntajeFinal = 0;
        int [] solucionFinal = new int[64];
        RussiaPlanningGreedy_main greedy = new RussiaPlanningGreedy_main();
        PrintWriter fun = new PrintWriter("FUNGreedy.txt", "UTF-8");
        PrintWriter var = new PrintWriter("VARGreedy.txt", "UTF-8");
        for(int i =0; i<100; i++){
            float criterioFrac = (float) i;
            System.out.println(criterioFrac);
            greedy.main(criterioFrac);
            fun.print((int)greedy.getCostoFinal());
            fun.print(" ");
            fun.print(-(int)greedy.getPuntajeFinal());
            fun.println("");
            for (int j=0; j<64; j++){
                var.print(greedy.getSolucion()[j]);
                var.print(" ");
            }
            var.println("");
  
        }
        fun.close();
        var.close();
        
    }
}
