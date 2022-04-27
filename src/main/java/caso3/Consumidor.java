package caso3;

public class Consumidor implements Runnable{
    Cola colaCompartida;
    public Consumidor(Cola cola){
        this.colaCompartida=cola;
    }
    @Override
    public void run() {
        int num;
        while (true){
            num=colaCompartida.desencolar();
            if (num!=-1){
                System.out.println("Consumidor recuperó el numero:"+num);
            } /* Fin del if*/
        } /*Fin del bucle infinito*/
    } /* Fin del run()*/
} /*Fin de la clase Consumidor*/