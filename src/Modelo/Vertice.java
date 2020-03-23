package Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um Nó de um Grafo.
 * @author antony
 */
public class Vertice implements Comparable<Vertice>{    
    private String nome;
    private List<Aresta> arestas;
    private boolean visitado;
    private Vertice anterior;    
    private double distanciaMinima = Double.MAX_VALUE;
    private int x, y;
    private boolean terminal;
    
    /**
     * Cria um novo Vertice.
     * @param name - Nome do Vertice.
     * @param terminal - Indica se e terminal ou nao.
     * @param x -  Localizacao X na tela.
     * @param y - Localizacao Y na tela.
     */
    public Vertice(String name, boolean terminal, int x, int y) {
        this.nome = name;
        this.arestas = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.terminal = terminal;
    }

    /**
     * Adiciona uma aresta na lista de arestas adjacentes do Vertice.
     * @param aresta
     */
    public void addVizinho(Aresta aresta) {
        arestas.add(aresta);
    }
    

    public String getNome(){
        return nome;
    }

 
    public List<Aresta> getArestas() {
        return arestas;
    }

   
    public void setEdges(List<Aresta> arestas) {
        this.arestas = arestas;
    }

    /**
     * Indica se o Vertice foi visitado ou nao.
     * @return
     */
    public boolean isVisited() {
        return visitado;
    }

    /**
     *
     * @param visitado
     */
    public void setVisited(boolean visitado) {
        this.visitado = visitado;
    }

    /**
     *
     * @return
     */
    public Vertice getAnterior() {
        return anterior;
    }

    /**
     *
     * @param anterior
     */
    public void setAnterior(Vertice anterior) {
        this.anterior = anterior;
    }

    /**
     *
     * @return
     */
    public double getDistanciaMinima() {
        return distanciaMinima;
    }

    /**
     *
     * @param distanciaMinima
     */
    public void setDistanciaMinima(double distanciaMinima) {
        this.distanciaMinima = distanciaMinima;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }   

    /**
     * Indica se o Vertice e terminal ou nao.
     * @return
     */
    public boolean isTerminal() {
        return terminal;
    }

    /**
     *
     * @param terminal
     */
    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }
    
    /**
     * Reseta os atributos variaveis do Vertice para seu estado inicial.
     */
    public void reset()
    {
        visitado = false;
        anterior = null;
        distanciaMinima = Double.MAX_VALUE;
    }
    
    
    @Override
    public String toString() {
        return nome;
    }

    /**
     * Compara esse Vertice com outro por meio de suas distanciasMinima.
     * @param otherVertice
     * @return int
     */
    @Override
    public int compareTo(Vertice otherVertice) {
        return Double.compare(this.distanciaMinima, otherVertice.distanciaMinima);
    }
}    
