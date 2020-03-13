package Modelo;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author antony
 */
public class Grafo {
    private ArrayList<Vertice> vertices;
    private ArrayList<Aresta> arestas;
    
    public Grafo(){
        vertices = new ArrayList<Vertice>();
        arestas = new ArrayList<Aresta>();
    }        
    
    public int getNumVertices(){
        return vertices.size();
    }
            
    public Vertice getVertice(int i){
        return vertices.get(i);
    }
    
    public ArrayList<Aresta> getArestas(){
        return arestas;
    }
    
    public ArrayList<Vertice> getVertices(){
        return vertices;
    }
    
    public Vertice buscarVertice(String nome){
        System.out.println(nome);
        for (Vertice v: vertices)
            if (v.getNome().equals(nome))
                return v;
        return null;
    }
    
    public void adicionarVertice(String nome){
        if(buscarVertice(nome) == null){
            vertices.add(new Vertice(nome));        
        }
    }
    
    public void adicionarVertice(String nome, boolean terminal, int x, int y){
        if(buscarVertice(nome) == null){
            vertices.add(new Vertice(nome, terminal, x, y));        
        }
    }
    
    public void adicionarAresta(String origem, String destino,  double peso){
        adicionarVertice(origem);
        adicionarVertice(destino);
        Vertice Origem = buscarVertice(origem);
       
        Vertice Destino = buscarVertice(destino);        
                
        Aresta aresta = new Aresta(Origem, Destino, peso);
        Origem.addVizinho(aresta);
        Destino.addVizinho(aresta);
        arestas.add(aresta);
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
    }

    public List<Vertice> getCaminhoMaisCurtoPara(Vertice targetVerte) {
        List<Vertice> path = new ArrayList<>();

        for (Vertice vertex = targetVerte; vertex != null; vertex = vertex.getAnterior()) {
            path.add(vertex);
        }

        Collections.reverse(path);
        return path;
    }
    
    public List<Vertice> getCaminhoMaisCurtoEntreVertices(String origem, String destino)
    {
        calcularMenoresDistancias(buscarVertice(origem));
        return  getCaminhoMaisCurtoPara(buscarVertice(destino));
    }
    
    public ArrayList<List<Vertice>> matrizMelhorCaminho(Vertice vertice){
        calcularMenoresDistancias(vertice);
        ArrayList<List<Vertice>> caminhos = new ArrayList<List<Vertice>>();
        for(Vertice v : vertices){
            if(!v.getNome().equals(vertice.getNome())){
                caminhos.add(getCaminhoMaisCurtoPara(v));
            }
        }
        return caminhos;
    }
    
    private ArrayList<Integer> buscarArestas(String nome)
    {
        int index = 0;
        ArrayList<Integer> indices = new ArrayList<>();
        for (Aresta a: arestas)
        {
            if (a.getOrigem().getNome().equals(nome))
                indices.add(index);
            index++;
        }
        return indices;
   
    }
    
    
    public boolean removerVertice(String nome)
    {
        Vertice v = buscarVertice(nome);
        if (v==null)
            return false;
        
        ArrayList<Integer> indices = buscarArestas(nome);
        for (int i = 0; i < indices.size(); i++)
        {
           arestas.remove(i);            
        }
        vertices.remove(v);
        return true;
    }
    
    public void removerTodosVertices()
    {
        vertices.clear();
        arestas.clear();
    }

}
