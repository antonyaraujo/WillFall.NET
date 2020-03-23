package Controlador;

import Modelo.Aresta;
import Modelo.Grafo;
import Modelo.Vertice;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Classe controladora do sistema.
 * @author antony
 */
public class Sistema {
    
    static Grafo rede = new Grafo();
    
    /**
     *
     * @throws IOException
     */
    public Sistema() throws IOException{        
    }
    
    /**
     * Adiciona vertices no grafo.
     * @param nome - Nome do vertice.
     * @param terminal - Determina se o vertice é terminal ou nao.
     * @param x - Localizacao X do vertice na tela.
     * @param y - Localizacao Y do vertice na tela.
     */
    public static void adicionarVertice(String nome, boolean terminal, int x, int y)
    {
        rede.adicionarVertice(nome, terminal, x, y);
    }
    
    /**
     * Adiciona uma aresta no grafo.
     * @param inicio -  Vertice que inicia a conexao.
     * @param destino - Vertice que termina a conexao.
     * @param peso - O custo da ligacao entre os vertices.
     * @return boolean - Indica uma conexao bem sucedida.
     */
    public static boolean adicionarAresta(Vertice inicio, Vertice destino, double peso){                        
        return rede.adicionarAresta(inicio, destino, peso);
    }
    
    /**
     *
     * @param origem - Vertice de inicio.
     * @param destino -  Vertice de destino
     * @return List<Vertice> -  Uma lista de vertices que indicam o melhor
     * caminho entre os vertices de inicio e destino.
     */
    public static List<Vertice> melhorCaminho(String origem, String destino){   
        return rede.getCaminhoMaisCurtoEntreVertices(origem, destino);
    }
    
    /**
     *
     * @param arquivo - Arquivo que sera salvo os dados do sistema.
     * @throws FileNotFoundException - Arquivo nao encontrado.
     * @throws IOException
     */
    public static void carregarArquivo(File arquivo) throws FileNotFoundException, IOException{        
        try 
        {     
            int i = 0;
            String linha;
            FileReader leitor = new FileReader(arquivo);
            BufferedReader buffer = new BufferedReader(leitor);
            while((linha = buffer.readLine())!=null && !linha.replaceAll("\n", "").equals("CONEXOES"))
            {
                try 
                {
                    String dados[] = linha.split(";");
                    boolean terminal = (dados[1].equals("SIM"));
                    int x = Integer.valueOf(dados[2]);
                    int y = Integer.valueOf(dados[3]);
                    rede.adicionarVertice(dados[0], terminal, x, y);
                }
                catch (ArrayIndexOutOfBoundsException e) 
                {
                    System.out.println("Formatação inválida na linha " + i + " ao adicionar um equipamento!\n"
                            + "Causa: "+ e.getMessage());
                }
                catch(NumberFormatException e)
                {
                    System.out.println("Formatação inválida na linha " + i + " ao adicionar um equipamento!\n"
                            + "Causa: "+ e.getMessage() + " não pode ser convertido para o tipo inteiro");
                }
                catch (IllegalArgumentException e)
                {
                    System.out.println("Formatação inválida na linha " + i + " ao adicionar um equipamento!\n"
                            + "Causa: " + e.getMessage());
                }
                catch (Exception e)
                {
                    System.out.println("Formatação inválida na linha " + i + " ao adicionar um equipamento!");
                }
                i++;
            }

            while((linha = buffer.readLine())!=null)
            {
                try 
                {
                    String dados[] = linha.split(";");
                    Vertice origem = rede.buscarVertice(dados[0]);
                    Vertice destino = rede.buscarVertice(dados[1]);
                    double peso = Double.valueOf(dados[2]);         
                    if(origem != null && destino != null)
                        rede.adicionarAresta(origem, destino, peso);
                }
                catch (ArrayIndexOutOfBoundsException e) 
                {
                    System.out.println("Formatação inválida na linha " + i + " ao adicionar um equipamento!\n"
                            + "Causa: "+ e.getMessage());
                }
                catch(NumberFormatException e)
                {
                    System.out.println("Formatação inválida na linha " + i + " ao adicionar um equipamento!\n"
                            + "Causa: "+ e.getMessage() + " não pode ser convertido para o tipo double");
                }
                catch (IllegalArgumentException e)
                {
                    System.out.println("Formatação inválida na linha " + i + " ao adicionar um equipamento!\n"
                            + "Causa: " + e.getMessage());
                }
                catch (Exception e)
                {
                    System.out.println("Formatação inválida na linha " + i + " ao adicionar um equipamento!");
                }
                i++;
            }

        } 
        catch (FileNotFoundException e) 
        {
          JOptionPane.showMessageDialog(null,"Arquivo não encontrado!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
     public static Grafo getGrafo(){
         return rede;
    }

    /**
     * Indentifica os melhores caminhos entre um vertice e todos os demais.
     * @param nome - Nome do vertice a se indentificar os melhores caminhos.
     * @return JScrollPane - Uma tabela com os melhores caminhos para o vertice
     * pretendido.
     */
    public static JScrollPane identificarMelhorCaminho(String nome){ 
        Vertice equipamento = rede.buscarVertice(nome);
        if (equipamento==null)
            return null;
        ArrayList<String> colunas = new ArrayList<String>();
        for(Vertice v: rede.getVertices()){
           if(v != equipamento)
               colunas.add(v.getNome());
         }
            JTable tabela = new JTable(rede.matrizMelhorCaminho(equipamento), colunas.toArray());                                    
            tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tabela.setAutoResizeMode(0);
            JScrollPane barraRolagem = new JScrollPane(tabela);
            return barraRolagem;
     }
     
    /**
     * Armazena em um arquivo txt os dados da rede.
     * @param path - Diretório de onde será salvo o arquivo.
     * @throws IOException
     */
    public static void salvarArquivo(String path) throws IOException {
       File arquivo = new File(path);
       String vertices="ROTULO;TERMINAL;COORDENADA_X;COORDENADA_Y\n";
       String arestas = "ORIGEM;DESTINO;PESO\n";
       StringBuilder arquivoFinal = new StringBuilder();
       
       
       for(int i = 0; i < rede.getVertices().size(); i++)
       {
           Vertice v = rede.getVertice(i);
           String rotulo = v.getNome();
           String terminal;
           terminal = (v.isTerminal())? "SIM":"NAO";
           String x = String.valueOf(v.getX());
           String y = String.valueOf(v.getY());
           vertices += rotulo + ";" + terminal + ";" + x + ";" +y + "\n";
        }
       
       for (int i =0; i < rede.getArestas().size(); i++)
       {
           Aresta a = rede.getArestas().get(i);
           String origem = a.getOrigem().getNome();
           String destino = a.getDestino().getNome();
           String peso = String.valueOf(a.getPeso());
           arestas+= origem + ";" + destino + ";" + peso + "\n"; 
       }
       arquivoFinal.append("EQUIPAMENTOS\n");
       arquivoFinal.append(vertices);
       arquivoFinal.append("CONEXOES\n");
       arquivoFinal.append(arestas);
       
        FileWriter escritor = new FileWriter(arquivo+".txt");
        BufferedWriter buffer = new BufferedWriter(escritor);
        buffer.write(arquivoFinal.toString());
        buffer.close();       
    }
    
    /**
     * Remove um vertice do grafo. 
     * @param nome - Nome do vertice que deve se removido.
     * @return boolean - True se a remocao foi bem sucedida;False caso não.
     */
    public static boolean removerVertice(String nome){
        return rede.removerVertice(nome);
        
    }
    
    /**
     * Remove todos os vertices do grafo.
     */
    public static void resetarRede()
    {
        rede.resetar();
    }
    
    /**
     * Metodo que retorna a menor rota entre dois vertices, a partir do metodo do grafo de mesma funcao
     * @param origem - nome do equipamento de origem
     * @param destino - nome do equipamento de destino
     * @return  lista de vertices que compoem o menor caminho entre a origem e o destino
     */
    public static List<Vertice> menorRotaEntre(String origem, String destino)
    {         
        return rede.getCaminhoMaisCurtoEntreVertices(origem, destino);
    }
    
    /**
     * Método que calcula o valor da distância euclidiana entre 2 vértices
     * @param equipamento1 - nome do primeiro equipamento
     * @param equipamento2 - nome do segundo equipamento
     * @return Retorna o resultado da fórmula √((v1x - v2x)² + (v1y-v2y)²) em double
     */
    public static double calcularCoordenadasEuclidianas(String equipamento1, String equipamento2){
        Vertice v1 = rede.buscarVertice(equipamento1);
       
               
        Vertice v2 = rede.buscarVertice(equipamento2);
        // Verifica se o equipamento 2 existe
        if(v1 != null && v2 != null)            
            return Math.sqrt(Math.pow((v1.getX()-v2.getX()), 2.0) + Math.pow((v1.getY()-v2.getY()), 2.0));
        return -1;
    }
    

}


    

    

