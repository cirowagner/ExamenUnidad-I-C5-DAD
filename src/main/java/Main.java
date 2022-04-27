import caso1.*;
import caso2.*;
import caso3.*;

import utils.BufferedZ;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void main(String []args){
        try {
            BufferedZ key = new BufferedZ();

            char confirm;
            int opt;

            do {
                System.out.println();
                opt = key.read(0,"|>------- Ejercicios ------<|\n"+
                        "1 > Grandes Almacenes \n"+
                        "2 > Cuentas Bancarias \n"+
                        "3 > Factoriales \n");

                switch(opt){
                    case 1:
                        System.out.println(ANSI_GREEN+"+---------------------------------------------+"+ANSI_RESET);
                        final int NUM_CLIENTES  = 300;
                        final int NUM_PRODUCTOS = 100;

                        Cliente[] cliente = new Cliente[NUM_CLIENTES];
                        Almacen almacen = new Almacen(NUM_PRODUCTOS);
                        Puerta puerta = new Puerta();

                        Thread[] hilosAsociados = new Thread[NUM_CLIENTES];

                        for (int i=0; i<NUM_CLIENTES; i++){
                            String nombreHilo   = "Cliente "+i;
                            cliente[i]          = new Cliente(puerta, almacen, nombreHilo);
                            hilosAsociados[i]   = new Thread(cliente[i]);
                            //Intentamos arrancar el hilo
                            hilosAsociados[i].start();
                        } //Fin del for

                        //Una vez arrancados esperamos a que todos terminen
                        for (int i=0; i<NUM_CLIENTES; i++){
                            hilosAsociados[i].join();
                        } //Fin del for
                        System.out.println(ANSI_GREEN+"+---------------------------------------------+"+ANSI_RESET);
                        break;
                    case 2:
                        System.out.println(ANSI_GREEN+"+---------------------------------------------+"+ANSI_RESET);
                        Cuenta cuenta = new Cuenta (100);

                        final int NUM_OPS_CON_100 = 40;
                        final int NUM_OPS_CON_50  = 20;
                        final int NUM_OPS_CON_20  = 60;

                        Thread[] hilosIngresan100 = new Thread[NUM_OPS_CON_100];
                        Thread[] hilosRetiran100  = new Thread[NUM_OPS_CON_100];
                        Thread[] hilosIngresan50  = new Thread[NUM_OPS_CON_50];
                        Thread[] hilosRetiran50   = new Thread[NUM_OPS_CON_50];
                        Thread[] hilosIngresan20  = new Thread[NUM_OPS_CON_20];
                        Thread[] hilosRetiran20   = new Thread[NUM_OPS_CON_20];

                        /* Arrancamos todos los hilos*/
                        for (int i=0; i<NUM_OPS_CON_100;i++){
                            HiloCliente ingresa = new HiloCliente(cuenta, 100);
                            HiloCliente retira  = new HiloCliente(cuenta, -100);

                            hilosIngresan100[i]= new Thread(ingresa);
                            hilosRetiran100[i] = new Thread(retira);

                            hilosIngresan100[i].start();
                            hilosRetiran100[i].start();
                        }

                        for (int i=0; i<NUM_OPS_CON_50;i++){
                            HiloCliente ingresa = new HiloCliente(cuenta, 50);
                            HiloCliente retira  = new HiloCliente(cuenta, -50);

                            hilosIngresan50[i]= new Thread(ingresa);
                            hilosRetiran50[i] = new Thread(retira);

                            hilosIngresan50[i].start();
                            hilosRetiran50[i].start();
                        }

                        for (int i=0; i<NUM_OPS_CON_20;i++){
                            HiloCliente ingresa = new HiloCliente(cuenta, 20);
                            HiloCliente retira  = new HiloCliente(cuenta, -20);

                            hilosIngresan20[i]= new Thread(ingresa);
                            hilosRetiran20[i] = new Thread(retira);

                            hilosIngresan20[i].start();
                            hilosRetiran20[i].start();
                        }
                        /* En este punto todos los hilos están arrancados, ahora toca esperarlos */
                        for (int i=0; i<NUM_OPS_CON_100;i++){
                            hilosIngresan100[i].join();
                            hilosRetiran100[i].join();
                        }

                        for (int i=0; i<NUM_OPS_CON_50;i++){
                            hilosIngresan50[i].join();
                            hilosRetiran50[i].join();
                        }

                        for (int i=0; i<NUM_OPS_CON_20;i++){
                            hilosIngresan20[i].join();
                            hilosRetiran20[i].join();
                        }
                        if (cuenta.esSimulacionCorrecta()){
                            System.out.println(ANSI_GREEN+"> La simulación fue correcta\n" +
                                                          "  La cuenta tiene el monto de los 100 EU"+ANSI_RESET);
                        } else {
                            System.out.println(ANSI_RED+"La simulación falló "+ANSI_RESET);
                            System.out.println("La cuenta tiene: "+cuenta.getSaldo());
                            System.out.println("Revise sus synchronized");
                        }
                        System.out.println(ANSI_GREEN+"+---------------------------------------------+"+ANSI_RESET);
                        break;
                    case 3:
                        System.out.println(ANSI_GREEN+"+---------------------------------------------+"+ANSI_RESET);
                        int MAX_PRODUCTORES     = 5;
                        int MAX_CONSUMIDORES    = 7;
                        int MAX_ELEMENTOS       = 10;

                        Thread[] hilosProductor;
                        Thread[] hilosConsumidor;

                        hilosProductor   = new Thread[MAX_PRODUCTORES];
                        hilosConsumidor  = new Thread[MAX_CONSUMIDORES];

                        Cola colaCompartida=new Cola(MAX_ELEMENTOS);

                        /*Construimos los productores*/
                        for (int i=0; i<MAX_PRODUCTORES; i++){
                            Productor productor=new Productor(colaCompartida);
                            hilosProductor[i]=new Thread(productor);
                            hilosProductor[i].start();
                        }
                        /*Construimos los consumidores*/
                        for (int i=0; i<MAX_CONSUMIDORES; i++){
                            Consumidor consumidor=new Consumidor(colaCompartida);
                            hilosConsumidor[i]=new Thread(consumidor);
                            hilosConsumidor[i].start();
                        }
                        /* Esperamos a que acaben todos los hilos, primero productores y luego consumidores*/
                        for (int i=0; i<MAX_PRODUCTORES; i++){
                            hilosProductor[i].join();
                        }
                        for (int i=0; i<MAX_CONSUMIDORES; i++){
                            hilosConsumidor[i].join();
                        }
                        System.out.println(ANSI_GREEN+"+---------------------------------------------+"+ANSI_RESET);
                        break;
                    default:
                        System.out.println("Opción Incorrecta :c");
                        break;
                }
                System.out.println();
                confirm = key.read(' ', "|=>¿Desea Probar mas Ejercicios? [S/N]: ");
            }while(confirm == 'S' || confirm == 's');
        }catch(Exception er){
            System.out.println(er.getMessage());
        }

        System.out.println();
        System.out.println(ANSI_PURPLE+"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+ANSI_RESET);
        System.out.println(ANSI_PURPLE+"zzz    <---       FIN          --->       zzz"+ANSI_RESET);
        System.out.println(ANSI_PURPLE+"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+ANSI_RESET);
    }
}
