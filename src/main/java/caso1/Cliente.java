package caso1;

import java.util.Random;

public class Cliente implements Runnable{
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";

    Puerta      puerta;
    Almacen     almacen;
    String      nombre;
    Random      generador;
    final int MAX_INTENTOS  =   10;
    public Cliente(Puerta p, Almacen a, String nombre){
        this.puerta     =   p;
        this.almacen    =   a;
        this.nombre     =   nombre;
        generador       =   new Random();
    }

    public void esperar(){
        try {
            int ms_azar = generador.nextInt(100);
            Thread.sleep(ms_azar);
        } catch (InterruptedException ex) {
            System.out.println("Falló la espera");
        }
    }
    @Override
    public void run() {
        for (int i=0; i<MAX_INTENTOS; i++){
            if (!puerta.estaOcupada()){
                if (puerta.intentarEntrar()){
                    esperar();
                    puerta.liberarPuerta();
                    if (almacen.cogerProducto()){
                        System.out.println(ANSI_GREEN+this.nombre+": cogí un producto!"+ANSI_RESET);
                        return ;
                    } else{
                        System.out.println(ANSI_PURPLE+this.nombre+": ops, crucé pero no cogí nada"+ANSI_RESET);
                        return ;
                    } //Fin del else
                } //Fin del if
            } else{
                esperar();
            }

        } //Fin del for
        //Se superó el máximo de reintentos y abandonamos
        System.out.println(ANSI_RED+this.nombre+": lo intenté muchas veces y no pude"+ANSI_RESET);
    }
}