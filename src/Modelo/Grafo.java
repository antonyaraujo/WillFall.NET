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

    private ArrayList<Vertice> vertices; // Armazena os vertices existentes no Grafo
    private ArrayList<Aresta> arestas; // Armazena as arestas existentes no Grafo
    private List<Observer> observers;

    public Grafo() {
        vertices = new ArrayList<>(); // Inicializa a ArrayList vertices
        arestas = new ArrayList<>(); // Inicializa a ArrayList arestas
        observers = new ArrayList<>();
    }

    /**
     * Metodo que retorna o valor referente ao tamanho da ArrayList vertices
     *
     * @return inteiro que representa a quantidade de vertices que o grafo
     * possui
     */
    public int getNumVertices() {
        return vertices.size();
    }

    /**
     * Metodo que retorna um vertice do ArrayList vertices
     *
     * @param i - recebe um inteiro que identifica a posicao do vertice desejado
     * @return vertice - retorna o vertice que ocupa a posicao i na ArrayList
     * vertice
     * @return null - nao ha retorno se a posicao informada for invalida
     */
    public Vertice getVertice(int i) {
        if (i < getNumVertices() && i > -1) {
            return vertices.get(i);
        } else {
            return null;
        }
    }

    /**
     * Metodo que retorna todos os vertices do grafo
     *
     * @return ArrayList de objetos do tipo Vertice
     */
    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    /**
     * Metodo que retorna um vertice a partir do nome     
     * @param nome - nome dado ao objeto do tipo Vertice que deseja ser
     * encontrado
     * @return v - vertice cujo nome e igual ao nome passado como parametro
     * @return null - o retorno e null se nao existe vertice com nome
     * equivalente
     */
    public Vertice buscarVertice(String nome) {
        for (Vertice v : vertices) { // For each que percorre todos os vertices da ArrayList Vertices
            if (v.getNome().equals(nome)) { // Verifica se o nome passado como parametro e igual lexicograficamente ao nome do vertice sendo percorrido
                return v;
            }
        }
        return null;
    }

    /**
     * Metodo que adiciona um Objeto do tipo Vertice a ArrayList de Vertices
     * @param nome - nome ou rotulo dado ao Vertice
     * @param terminal - identifica se e um computador (true) ou nao (false)
     * @param x - posicao x do vertice na tela
     * @param y - posicao y do vertice na tela
     * @return nao ha retorno
     */
    public void adicionarVertice(String nome, boolean terminal, int x, int y) {
        if (buscarVertice(nome) == null) // Se nao existir vertice com o nome passado, o vertice pode ser criado
        {
            vertices.add(new Vertice(nome,
                    terminal, x, y));
        }
        notifyObservers();
    }

    /**
     * Metodo que remove um vertice da ArrayList de vertices do grafo a partir do seu nome
     * @param nome - nome a partir do qual sera identificado o vertice a ser removido
     * @return true - se o vertice tiver sido encontrado e removido
     * @return false - se o vertice não tiver sido encontrado e removido
     */
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
    
    /**
     * Metodo que adiciona um Objeto do tipo Aresta a ArrayList de arestas
     * @param origem - nome do vertice de origem
     * @param destino - nome do vertice de destino
     * @param peso - valor da conexao entre origem e destino     
     * @return nao ha retorno
     */
    public boolean adicionarAresta(Vertice origem, Vertice destino, double peso) {        
        if (origem == null || destino == null) {
            return false;
        }
        Aresta aresta = new Aresta(origem, destino, peso);
        origem.addVizinho(aresta);
        destino.addVizinho(aresta);
        arestas.add(aresta);
        notifyObservers();
        return true;
       
    }

    /**
     * Metodo que retorna todas as arestas do grafo
     *
     * @return ArrayList de objetos do tipo Aresta
     */
    public ArrayList<Aresta> getArestas() {
        return arestas;
    }
    
    /**
     * Metodo que remove um objeto Aresta da ArrayListde arestas do grafo
     * @param a - Objeto aresta a ser removido da lista
     */
    private void removerAresta(Aresta a) {
        arestas.remove(a);
        for (Vertice v : vertices) {
            v.getArestas().remove(a);
        }
    }
    
    /**
     * Metodo que retorna todas as arestas que contem um determinado vertice
     * @param nome - String que possui o nome do vertice a ser buscado
     * @return ArrayList contendo a posicao na lista de aresta de todas as 
     * arestas que contem, na origem ou destino, o vertice com o dado nome
     */
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

    /**
     * Metodo que calcula as melhores distancias de um vertice para todos os outros
     * @param origem - vertice a partir do qual que-se partir para outros
     */
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

    /**
     * Metodo que retorna uma lista de Vertices para o menor caminho ate o vertice de destino
     * @param destino - vertice ate o qual o caminho sera percorrido
     * @return lista contendo os vertices do menor caminho ate o destino
     */
    public List<Vertice> getCaminhoMaisCurtoPara(Vertice destino) {
        List<Vertice> path = new ArrayList<>();
        for (Vertice vertex = destino; vertex != null; vertex = vertex.getAnterior()) {
            path.add(vertex);
        }
        for (Vertice v : vertices) {
            v.reset();
        }
        Collections.reverse(path);
        return path;
    }

    
    /**
     * Metodo que retorna uma lista de vertices do menor caminho entre um vertice de origem e outro de destino
     * @param Origem - nome do vertice a partir do qual o caminho inicia
     * @param Destino - nome do vertice no qual o caminho se finda
     * @return lista de vertices com o menor caminho entre origem e destino
     */
    public List<Vertice> getCaminhoMaisCurtoEntreVertices(String Origem, String Destino) {
        if (buscarVertice(Origem)==null)
            return null;
        if (buscarVertice(Destino)==null)
            return null;
        calcularMenoresDistancias(buscarVertice(Origem));
        return getCaminhoMaisCurtoPara(buscarVertice(Destino));
    }

    /**
     * Metodo que calcula uma matriz contendo o melhor caminho de um vertice para todos os outros do grafo
     * @param vertice - vertice a partir do qual os melhores caminhos serao calculados
     * @return matriz contendo todos os melhores caminhos entre um vertice e os outros do grafo
     */
    public Object[][] matrizMelhorCaminho(Vertice vertice) {
        ArrayList<Vertice> aux = new ArrayList<>(vertices);
        aux.remove(vertice);
        Object[][] matriz = new Object[aux.size()][aux.size()];
        for (int linha = 0; linha < aux.size(); linha++) {
            for (int coluna = 0; coluna < aux.size(); coluna++) {
                this.calcularMenoresDistancias(vertice);
                List<Vertice> caminho = getCaminhoMaisCurtoPara(aux.get(coluna));
                // O tamanho da linha não pode ser maior que o tamanho do caminho pois essa posição não existe no vetor caminho
                if (caminho.size() > linha) {
                    if(caminho.size() == 1 && caminho.get(0) == aux.get(coluna))
                        matriz[linha][coluna] = "!!!!! Sem conexão";
                    else
                        matriz[linha][coluna] = caminho.get(linha).getNome(); // Adiciona na linha da coluna o vertice a ser percorrido no dado momento do vertice da coluna
                }
            }
        }

        return matriz;
   }     
   

    /**
     * Metodo que remove todos os vertices e arestas do grafo
     */
    public void resetar() {
        vertices.clear();
        arestas.clear();
        notifyObservers();
    }

    /**
     * Metodo que adiciona um observador ao JFrame para ser notificado de possíveis mudancas
     * @param observer - o elemento observador a ser adicionado
     */
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Metodo que remove um observador ao JFrame para ser notificado de possiveis mudancas
     * @param observer - o elemento observador a ser removido
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    
    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(getNumVertices());
        }
    }

}
