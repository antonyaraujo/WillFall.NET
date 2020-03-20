package Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
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
    
    public Vertice(String name, boolean terminal, int x, int y) {
        this.nome = name;
        this.arestas = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.terminal = terminal;
    }

    public void addVizinho(Aresta aresta) {
        this.arestas.add(aresta);
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

    public boolean isVisited() {
        return visitado;
    }

    public void setVisited(boolean visitado) {
        this.visitado = visitado;
    }

    public Vertice getAnterior() {
        return anterior;
    }

    public void setAnterior(Vertice anterior) {
        this.anterior = anterior;
    }

    public double getDistanciaMinima() {
        return distanciaMinima;
    }

    public void setDistanciaMinima(double distanciaMinima) {
        this.distanciaMinima = distanciaMinima;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }   

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }
    
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

    @Override
    public int compareTo(Vertice otherVertice) {
        return Double.compare(this.distanciaMinima, otherVertice.distanciaMinima);
    }
}    
