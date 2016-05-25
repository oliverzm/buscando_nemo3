package Model;

import java.util.LinkedList;

/**
 * Created by jeffer on 18/05/16.
 */
public class AlgoritmoBusqueda {
    protected byte matriz[][];//tablero del juego
    protected byte n;//tamano de la matriz

    protected long nodoCreados = 0;//los nodos que son creados (size = 2^64)
    protected long nodoExpandidos = 0;//los nodos que son expandidos (size = 2^64)
    protected double costoTotal = 0;//el costo total de la solución más optima
    protected byte factorRaminicacion = 0;

    protected byte numeroMetas = 0;//contador que permite conocer cuando se llega a la última meta

    //historial de padres
    protected LinkedList<Nodo> historialPadres;//guarda el historial de todos los padres
    protected int idsHistorialPadres = 0;
    
    public Nodo nodoFinal;

    public AlgoritmoBusqueda(byte[][] matriz, byte n) {
        this.matriz = matriz;
        this.n = n;
        this.historialPadres = new LinkedList<Nodo>();//se inicializa la cola
        this.idsHistorialPadres = 0;

    }

    @Override
    public String toString() {
        return
                "\nPasos Solución: "+this.mostrarRuta() +
                "\nNodos Expandidos: " + this.nodoExpandidos +
                "\nNodos Creados: " + this.nodoCreados +
                "\nCosto total Solución: " + this.costoTotal +
                "\nFactor de Ramificación: " + this.factorRaminicacion;
    }

    /**
     * Metodo que permite a partir de la posición de la solucion obtener
     * todos sus ancestros
     */
    public String mostrarRuta() {
        //Nodo nodo = this.historialPadres.getLast();
    	Nodo nodo = this.nodoFinal;
        int index = nodo.getPadre();
        String ruta = nodo.toString();
        
        System.out.println(nodo+" - "+nodo.getCosto()+" - "+nodo.getCostoAcumulado()+ " - "+nodo.getValorHeuristica()+" - "+nodo.getfN()+" - "+nodo.getMetasCumplidas());
        System.out.println("----------");
        
        while (true) {       	
        	
            index = nodo.getPadre();
            nodo = this.historialPadres.get(index);
            //nodo.mostrarMatriz();
            
            ruta = nodo + "-->"+ruta;
            
            System.out.println(nodo+" - "+nodo.getCosto()+" - "+nodo.getCostoAcumulado()+ " - "+nodo.getValorHeuristica()+" - "+nodo.getfN()+" - "+nodo.getMetasCumplidas());
            System.out.println("----------");
            
            if(index == 0){
                break;
            }
        }
        return ruta;
    }

}
