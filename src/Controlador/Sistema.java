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

/**
 *
 * @author antony
 */
public class Sistema {
    Grafo rede = new Grafo();
    
    public Sistema() throws IOException{        
    }
    
    public boolean adicionarAresta(String inicio,String destino, double peso){                        
        rede.adicionarAresta(inicio, destino, peso);
        return true;
    }
    
    public List<Vertice> melhorCaminho(String origem, String destino){   
        return rede.getCaminhoMaisCurtoEntreVertices(origem, destino);
    }
    
    public void carregarArquivo(File arquivo) throws FileNotFoundException, IOException{        
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
    
     public Grafo getGrafo(){
        return rede;
    }

    public void salvarArquivo(String path) throws IOException {
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
    
    public boolean removerVertice(String nome){
        return rede.removerVertice(nome);
        
    }
    
    public void resetarRede()
    {
        rede.removerTodosVertices();
        
    }
    

}


    

    

