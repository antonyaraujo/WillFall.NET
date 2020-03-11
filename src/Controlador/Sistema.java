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
    
    public boolean adicionarAresta(String inicio, Boolean terminal, String destino, double peso){                        
        rede.adicionarAresta(inicio, destino, terminal, peso);
        return true;
    }
    
    public List<Vertice> melhorCaminho(String origem, String destino){        
        Vertice Origem = rede.buscarVertice(origem);
        Vertice Destino = rede.buscarVertice(destino);
        rede.computePath(Origem);
        return rede.getShortestPathTo(Destino);
    }
    
    public void carregarArquivo(File arquivo) throws FileNotFoundException, IOException{        
        if(arquivo.exists()){
        FileReader leitor = new FileReader(arquivo);
        BufferedReader buffer = new BufferedReader(leitor);
        while(buffer.ready()){
            String dados[] = buffer.readLine().split(";");
            if(dados[1].toLowerCase().equals("sim"))            
                adicionarAresta(dados[0], true, dados[2], Double.parseDouble(dados[3]));  
            else
                adicionarAresta(dados[0], false, dados[2], Double.parseDouble(dados[3]));  
            rede.buscarVertice(dados[0]).setX(Integer.parseInt(dados[4]));
            rede.buscarVertice(dados[0]).setY(Integer.parseInt(dados[5]));
            rede.buscarVertice(dados[2]).setX(Integer.parseInt(dados[6]));
            rede.buscarVertice(dados[2]).setY(Integer.parseInt(dados[7]));
        }
        } else{
            JOptionPane.showMessageDialog(null, "Arquivo inexistente");
        }
    }

     public Grafo getGrafo(){
        return rede;
    }

    public void salvarArquivo(String path) throws IOException {
       File arquivo = new File(path);       
       String arquivoFinal = "";
       for(int i = 0; i < rede.getArestas().size(); i++){
           String origem = rede.getArestas().get(i).getOrigem().getNome();
           String destino = rede.getArestas().get(i).getDestino().getNome();
           String terminal;
           if(rede.getArestas().get(i).isTerminal())
               terminal = "Sim";
           else 
               terminal = "Nao";
        String peso = String.valueOf(rede.getArestas().get(i).getPeso());
        String x_origem = String.valueOf(rede.getArestas().get(i).getOrigem().getX());
        String y_origem = String.valueOf(rede.getArestas().get(i).getOrigem().getY());
        String x_destino = String.valueOf(rede.getArestas().get(i).getDestino().getX());
        String y_destino = String.valueOf(rede.getArestas().get(i).getDestino().getY());
       arquivoFinal += origem + ";" + terminal + ";" + destino + ";" + peso + ";" + x_origem + ";" +y_origem + ";" + x_destino + ";" +y_destino + "\n";
       System.out.println(arquivoFinal);
      
    }
    FileWriter escritor = new FileWriter(arquivo+".txt");
    BufferedWriter buffer = new BufferedWriter(escritor);
    buffer.write(arquivoFinal);
    buffer.close();       
    }
    
    public boolean removerVertice(String nome){
        return rede.removerVertice(nome);
        
    }
    

}


    

    

