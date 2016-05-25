package Model.Informada;

import java.awt.Point;
import java.util.Comparator;
import java.util.PriorityQueue;

import Model.AlgoritmoBusqueda;
import Model.ComparadorCostos;
import Model.ComparadorCostosA_asterisk;
import Model.Nodo;
import Model.Personaje;

public class A_asterisk extends AlgoritmoBusqueda{

	private Comparator<Nodo> comparador;
    private PriorityQueue<Nodo> cola;
    private Point[] listadoMetas;
	
	public A_asterisk(byte[][] matriz, byte n, int tipoHeuristica) 
	{
		super(matriz, n);
		comparador = new ComparadorCostosA_asterisk();
		cola = new PriorityQueue<Nodo>(10, comparador);	
		listadoMetas = new Point[3];
	}
	
	public void run()
	{
		Nodo nodoActual = null;
		buscarNodoInicialyMetas();

		while (this.cola.size() > 0)
        {
			nodoActual = this.cola.remove();
						
			if (nodoActual.isMeta()) 
			{
				if (nodoActual.isMetaGlobal()) {//verifica si ya se alcanzo la meta global y actualiza las variables de estado
	                break;
	            }				
				//System.out.println(" -- ir por "+nodoActual.getMetaActual()+ " "+nodoActual);				
				//nodoActual.mostrarMatriz();
			}
			
			this.expandirNodo(nodoActual);//expandir el nodo
        }	
				
		this.nodoFinal = nodoActual;
		this.costoTotal = nodoActual.getCostoAcumulado();
		System.out.println(this.toString());
	}
	
	public void expandirNodo(Nodo nodoActual)
	{
		this.nodoExpandidos++;//se ha expandido un nuevo nodo
        this.historialPadres.add(nodoActual);//se agrega nodo al historial de padres
        byte i = nodoActual.getFila();//obtiene la fila del nodo actual
        byte j = nodoActual.getColumna();//obtiene la columna del noto actual
        
        if(nodoActual.isAquaman())
        {
        	return;
        }
        
        if (i - 1 >= 0) 
        {
            this.crearNodo((byte) (i - 1), j, this.idsHistorialPadres, nodoActual);
        }
        
        if (i + 1 < this.n) 
        {
            this.crearNodo((byte) (i + 1), j, this.idsHistorialPadres, nodoActual);
        }
        
        if (j - 1 >= 0) 
        {
            this.crearNodo(i, (byte) (j - 1), this.idsHistorialPadres, nodoActual);
        }
        
        if (j + 1 < this.n) 
        {
            this.crearNodo(i, (byte) (j + 1), this.idsHistorialPadres, nodoActual);
        }
        
        this.idsHistorialPadres++;//aumenta el identificador de indexamiento
	}
	
	/**
     * Metodo que crea un nuevo nodo, también se comprueba
     * que el nodo no se este devolviendo
     *
     * @param i     fila de la nueva posicion
     * @param j     columna de la nueva posicion
     * @param padreId identificador del padre
     * @param nodoPadre nodo padre del nodo que será creado
     * @return
     */
    private void crearNodo(byte i, byte j, int padreId, Nodo nodoActual) 
    {    	
    	Nodo nodoPadre = historialPadres.get(nodoActual.getPadre());
    	    	
        if(!(i == nodoPadre.getFila() && j == nodoPadre.getColumna()) && nodoActual.getMatriz()[i][j] != 1) 
        {
            Nodo nodo = new Nodo(padreId, i, j, nodoActual.getMatriz(), nodoActual.getMetasCumplidas(), nodoActual.getMetaActual());
            nodo.setFactorReduccion(nodoActual.getFactorReduccion());
            nodo.setCostoAcumulado(nodoActual.getCostoAcumulado());
            nodo.setValorHeuristica(this.primeraHeuristica(i, j, listadoMetas[nodoActual.getMetasCumplidas()]));
            nodo.setfN();
            this.cola.add(nodo);
            this.nodoCreados++;
        }
    }
    
    /**
     * Metodo que retorna el valor de aplicar la heuristica del valor manhattan
     * 
     * @param x
     * @param y
     * @param coordenada
     * @return
     */
    public double primeraHeuristica(int x, int y, Point coordenada)
    {
        double heuristica = Math.abs(x-coordenada.getX())+Math.abs(y-coordenada.getY());
        return heuristica;
    }
	
	public void buscarNodoInicialyMetas()
	{
		byte posX = 0;
		byte posY = 0;
		for (byte i = 0; i < matriz.length; i++) {
			for (byte j = 0; j < matriz.length; j++) {
								
				switch (matriz[i][j]) 
				{
					case 0:
						posX = i;
						posY = j;
						break;
					case 7:
						this.listadoMetas[0] = new Point(i, j);
						break;
					case 6:
						this.listadoMetas[1] = new Point(i, j);
						break;
					case 5:
						this.listadoMetas[2] = new Point(i, j);
						break;
					default:
						break;
				}
			}
		}
		
		Nodo primerNodo = new Nodo(0, posX, posY, this.matriz.clone(), (byte) 0, Personaje.NEMO);
		primerNodo.setCostoAcumulado(0);
		primerNodo.setfN();
		
		cola.add(primerNodo);
	}
}
