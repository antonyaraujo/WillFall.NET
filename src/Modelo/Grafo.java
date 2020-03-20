package Modelo;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author antony
 */
public class Grafo implements Observable {

    private ArrayList<Vertice> vertices;
    private ArrayList<Aresta> arestas;
    private List<Observer> observers;

    public Grafo() {
        vertices = new ArrayList<>();
        arestas = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public int getNumVertices() {
        return vertices.size();
    }

    public Vertice getVertice(int i) {
        return vertices.get(i);
    }

    public ArrayList<Aresta> getArestas() {
        return arestas;
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public Vertice buscarVertice(String nome) {
        for (Vertice v : vertices) {
            if (v.getNome().equals(nome)) {
                return v;
            }
        }
        return null;
    }

    public void adicionarVertice(String nome) {
        if (buscarVertice(nome) == null) {
            vertices.add(new Vertice(nome));
        }
        notifyObservers();
    }

    public void adicionarVertice(String nome, boolean terminal, int x, int y) {
        if (buscarVertice(nome) == null) {
            vertices.add(new Vertice(nome, terminal, x, y));
        }
        notifyObservers();
    }

    public void adicionarAresta(String origem, String destino, double peso) {
        Vertice Origem = buscarVertice(origem);
        Vertice Destino = buscarVertice(destino);
        if (Origem == null || Destino == null) {
            return;
        }
        Aresta aresta = new Aresta(Origem, Destino, peso);
        Origem.addVizinho(aresta);
        Destino.addVizinho(aresta);
        arestas.add(aresta);
        notifyObservers();
    }

    public void calcularMenoresDistancias(Vertice origem) {
        origem.setDistanciaMinima(0);
        PriorityQueue<Vertice> filaPrioridade = new PriorityQueue<>();
        filaPrioridade.add(origem);

        while (!filaPrioridade.isEmpty()) {
            Vertice vertex = filaPrioridade.poll();

            for (Aresta aresta : vertex.getArestas()) {
                Vertice v = aresta.getDestino();
                //Vertice u = edge.getStartVertice();
                double weight = aresta.getPeso();
                double minDistance = vertex.getDistanciaMinima() + weight;

                if (minDistance < v.getDistanciaMinima()) {
                    filaPrioridade.remove(vertex);
                    v.setAnterior(vertex);
                    v.setDistanciaMinima(minDistance);
                    filaPrioridade.add(v);
                }
            }
        }        
        filaPrioridade.clear();     
    }

    public List<Vertice> getCaminhoMaisCurtoPara(Vertice destino) {
        List<Vertice> path = new ArrayList<>();        
        for (Vertice vertex = destino; vertex != null; vertex = vertex.getAnterior()) {
            path.add(vertex);
        }
        for(Vertice v : vertices){
            v.reset();
        }
        Collections.reverse(path); 
        return path;
    }

    public List<Vertice> getCaminhoMaisCurtoEntreVertices(String Origem, String Destino) {        
        calcularMenoresDistancias(buscarVertice(Origem));
        return  getCaminhoMaisCurtoPara(buscarVertice(Destino));
    }

    public Object[][] matrizMelhorCaminho(Vertice vertice) {
        calcularMenoresDistancias(vertice);        
        ArrayList<Vertice> listaV = vertices;
        listaV.remove(vertice);        
        Object[][] matriz = new Object[getNumVertices() / 2][getNumVertices()];
        for (int linha = 0; linha < getNumVertices() / 2; linha++) {
            for (int coluna = 0; coluna < getNumVertices(); coluna++) {                
                    if (getCaminhoMaisCurtoPara(listaV.get(coluna)).size() > linha) {
                        matriz[linha][coluna] = getCaminhoMaisCurtoPara(listaV.get(coluna)).get(linha);
                   }                
            }
        }                
        notifyObservers();
        return matriz;        
    }

    private ArrayList<Integer> buscarArestas(String nome) {
        int index = 0;
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (Aresta a : arestas) {
            if (a.getOrigem().getNome().equals(nome)) {
                indices.add(index);
            }
            if (a.getDestino().getNome().equals(nome)) {
                indices.add(index);
            }
            index++;
        }
        return indices;
    }

    public boolean removerVertice(String nome) {
        Vertice v = buscarVertice(nome);
        if (v == null) {
            return false;
        }

        vertices.remove(v);
        for (Aresta a : v.getArestas()) {
            removerAresta(a);
        }
        notifyObservers();
        return true;
    }

    private void removerAresta(Aresta a) {
        arestas.remove(a);
        for (Vertice v : vertices) {
            v.getArestas().remove(a);
        }
    }

    public void removerTodosVertices() {
        vertices.clear();
        arestas.clear();
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

}
