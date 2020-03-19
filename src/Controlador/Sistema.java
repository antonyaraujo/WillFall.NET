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
 *
 * @author antony
 */
public class Sistema {
    static Grafo rede = new Grafo();
    
    public Sistema() throws IOException{        
    }
    
    public static void adicionarVertice(String nome, boolean terminal, int x, int y)
    {
        rede.adicionarVertice(nome, terminal, x, y);
    }
    
    public static boolean adicionarAresta(String inicio,String destino, double peso){                        
        rede.adicionarAresta(inicio, destino, peso);
        return true;
    }
    
    public static List<Vertice> melhorCaminho(String origem, String destino){   
        return rede.getCaminhoMaisCurtoEntreVertices(origem, destino);
    }
    
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
                    String origem = dados[0];
                    String destino = dados[1];
                    double peso = Double.valueOf(dados[2]);
                    rede.adicionarAresta(origem, destino, peso);
                }
                catch (Exception e) 
                {
                    System.out.println("Formatação inválida na linha " + i + " ao adicionar uma conexão!");
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

     public static JScrollPane identificarMelhorCaminho(String nome){
         Vertice equipamento = rede.buscarVertice(nome);
         ArrayList<String> colunas = new ArrayList<String>();
            for (int i = 0; i < rede.getNumVertices(); i++) {
                colunas.add(rede.getVertices().get(i).getNome());
            }
            Object[][] dados = new Object[rede.getNumVertices()/2][rede.getNumVertices()];
            ArrayList<List<Vertice>> lista = rede.matrizMelhorCaminho(equipamento);
            for (int j = 0; j < lista.size(); j++) {
                for (int i = 0; i < lista.get(j).size(); i++) {
                    dados[i][j] = lista.get(j).get(i).getNome();
                }
            }            
            colunas.remove(equipamento.getNome());            
            JTable tabela = new JTable(dados, colunas.toArray());                                    
            tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            JScrollPane barraRolagem = new JScrollPane(tabela);
            barraRolagem.notifyAll();
            return barraRolagem;
     }
     
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
    
    public static boolean removerVertice(String nome){
        return rede.removerVertice(nome);
        
    }
    
    public static void resetarRede()
    {
        rede.removerTodosVertices();
    }
    
    public static List<Vertice> menorRotaEntre(String origem, String destino)
    {
        return rede.getCaminhoMaisCurtoEntreVertices(origem, destino);
    }
    

}


    

    

