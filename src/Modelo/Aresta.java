package Modelo;

/**
 *
 * @author antony
 */

public class Aresta {    
    private double peso;
    private Vertice origem;
    private Vertice destino;
    private boolean terminal;   

    public Aresta(Vertice origem, Vertice destino, double peso) {
        this.peso = peso;
        this.origem = origem;
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public void setOrigem(Vertice origem) {
        this.origem = origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }
    
    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }
    
  
}