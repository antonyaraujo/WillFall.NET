/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao;

import Modelo.Aresta;
import Modelo.Grafo;
import Modelo.Vertice;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author antony
 */
    public class GrafoGUI extends javax.swing.JPanel{

    private int x_novoVertice;
    private int y_novoVertice;
    private boolean clickNoGrafo;
    private Grafo grafo;
    JPopupMenu popMenu = new JPopupMenu();
    JPopupMenu popMenuTerminal = new JPopupMenu();
    JMenuItem addVertexMenuItem = new JMenuItem("Adicionar vértice");
    JMenuItem addEdgeMenuItem = new JMenuItem("Adicionar aresta");
    JTable tableAdjMatrix;
    JScrollPane scrollPane;
    JButton adjMatrixButton;
    JButton adicionarVertice;
    JLabel grafoLabel;
    boolean hasclicked1=false;
    JLabel click1label=null;
    Object resposta;
    boolean click;
    JMenuItem caminhos;    
    /**
     * Creates new form desenhoGrafo
     */
        
    public GrafoGUI(Grafo grafo) {  
        this.grafo = grafo;
        grafoLabel = new JLabel("GRAFO COM " + " VÉRTICES");
        add(grafoLabel);
        adjMatrixButton = new JButton("Visualizar Matrix de Adjacência");
        add(adjMatrixButton);
        adjMatrixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adjMatrix();
            }
        });
        popMenu.add(addVertexMenuItem);
        popMenu.add(addEdgeMenuItem);
        add(popMenu);
        popUpMenu();
        caminhos = new JMenuItem("Ver melhores caminhos");
        popMenuTerminal.add(caminhos);        
        clickNoGrafo = false;
        initComponents();
        setVisible(true);   
    }
    
    public Grafo getGrafo(){
        return grafo;
    }       
        
    public void adicionarEquipamentoMouse(Object tipoEquipamento){ 
        resposta = tipoEquipamento;
        if (resposta == null)
            return;
        clickNoGrafo = true;                                
        addMouseListener(new MouseAdapter() 
        {            
            public void mouseClicked(MouseEvent e)
            {
                if(clickNoGrafo){                    
                    x_novoVertice = e.getX();
                    y_novoVertice = e.getY();   
                    String nomeEquipamento;
                    nomeEquipamento = JOptionPane.showInputDialog(null, "Digite o rótulo do equipamento", "Rótulo", 1);
                    if (nomeEquipamento==null)
                    {
                        clickNoGrafo = false;
                        return;
                    }
                    else nomeEquipamento = nomeEquipamento.toUpperCase();
                    if (resposta.equals("Roteador")){                        
                        grafo.adicionarVertice(nomeEquipamento, false, x_novoVertice, y_novoVertice);                                      
                    }
                    else if (resposta.equals("Computador")){
                        grafo.adicionarVertice(nomeEquipamento, true, x_novoVertice, y_novoVertice);                                                             
                    }
                    adicionarEquipamento(grafo.buscarVertice(nomeEquipamento));                                        
                    clickNoGrafo = false;                    
                }                
            }                        
        });  

    } 
    
    private void adicionarEquipamento(Vertice equipamento){
        JButton bt_equipamento = new JButton(); 
        bt_equipamento.setName(equipamento.getNome().toUpperCase());        
        this.add(equipamento.getNome(), bt_equipamento);        
        bt_equipamento.setLocation(equipamento.getX(), equipamento.getY());
        bt_equipamento.setSize(50, 50);        
        ImageIcon img, selecionado;        
        if(!equipamento.isTerminal()){
            img = new ImageIcon("imagens/roteador.png");
            selecionado = new ImageIcon("imagens/roteador2.png");
            bt_equipamento.setIcon(new ImageIcon(img.getImage().getScaledInstance(bt_equipamento.getWidth(), bt_equipamento.getHeight(), Image.SCALE_SMOOTH)));
            bt_equipamento.setSelectedIcon(new ImageIcon(selecionado.getImage().getScaledInstance(bt_equipamento.getWidth(), bt_equipamento.getHeight(), Image.SCALE_SMOOTH)));            
        }
        else{
            img = new ImageIcon("imagens/terminal.png");
            bt_equipamento.setIcon(new ImageIcon(img.getImage().getScaledInstance(bt_equipamento.getWidth(), bt_equipamento.getHeight(), Image.SCALE_SMOOTH)));            
        }   
        bt_equipamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!equipamento.isTerminal())
                    bt_equipamento.setSelected(!bt_equipamento.isSelected());
               
                    
                
            }
        });
        bt_equipamento.setVisible(true);
        bt_equipamento.setOpaque(false);
        bt_equipamento.setContentAreaFilled(false);
        bt_equipamento.setBorder(null);               
        JLabel rotulo = new JLabel(equipamento.getNome());
        rotulo.setLocation(bt_equipamento.getX()+15, bt_equipamento.getY()+50);
        rotulo.setSize(100, 10);        
        rotulo.setVisible(true);            
        this.add(rotulo);                   
        this.repaint();        
        popMenuEquipamento(equipamento, bt_equipamento);        
        this.initComponents();        
    }
    
    private void popMenuEquipamento(Vertice equipamento, JButton bt)
    {
        click = true;
        bt.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON3 && click){                                
                    popMenuTerminal.show(e.getComponent(), e.getX(), e.getY());  
                    bt.setSelected(true);
                }
                click = false;
                bt.setSelected(false);
            }
            
        });        
                        
       caminhos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {               
                String[] colunas = new String[grafo.getNumVertices()];
                for(int i = 0; i < grafo.getNumVertices(); i++){
                    colunas[i] = grafo.getVertices().get(i).getNome();
                }
                Object[][] dados = new Object[grafo.getNumVertices()][grafo.getNumVertices()];
                ArrayList<List<Vertice>> lista = grafo.matrizMelhorCaminho(equipamento);
                for(int j = 0; j < lista.size(); j++){
                    for(int i = 0; i < lista.get(j).size(); i++){
                        dados[i][j] = lista.get(j).get(i).getNome();
                    }
                        
                }
                JTable tabela = new JTable(dados, colunas);                
                JScrollPane barraRolagem = new JScrollPane(tabela);                
                tabela.setVisible(true);
                barraRolagem.setVisible(true);                
                
            }
               
        });               
        
        
    }
    
        
        
    private void popUpMenu()
    {        
        addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON3)
                {
                    popMenu.show(e.getComponent(), e.getX(), e.getY());
                    x_novoVertice = e.getX();
                    y_novoVertice = e.getY();
                }                                
            }
                        
        });
                
        
        addVertexMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String label = JOptionPane.showInputDialog(popMenu,"Digite o rótulo do equipamento", "Rótulo", 1).toUpperCase();
                if (label==null)
                    return;
                grafo.adicionarVertice(label, false, x_novoVertice, y_novoVertice);
                grafoLabel.setText("GRAFO COM " + grafo.getNumVertices() + " VÉRTICES");
                repaint();
            }
               
        });
        
        addEdgeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome1 = JOptionPane.showInputDialog(popMenu,"Digite o rótulo do primeiro vértice", "Rótulo", 1).toUpperCase();
                String nome2 = JOptionPane.showInputDialog(popMenu,"Digite o rótulo do segundo vértice", "Rótulo", 1).toUpperCase();
                int peso = Integer.parseInt(JOptionPane.showInputDialog(popMenu,"Digite o peso","Peso", 1).toUpperCase());                
                grafo.adicionarAresta(nome1, nome2, peso);
                repaint();
            }
        });
        
        
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        paintGraph(g);            
    }
    
    private void paintGraph(Graphics g)
    {
        for (int i = 0; i < grafo.getNumVertices(); i++)
        {
            Vertice v = grafo.getVertices().get(i);            
            adicionarEquipamento(v);                            
        }
        
        ArrayList<Aresta> arestas = grafo.getArestas();
        for (int i =0; i <  arestas.size(); i++){                                        
                    g.drawLine(arestas.get(i).getOrigem().getX()+15, arestas.get(i).getOrigem().getY()+20, 
                            arestas.get(i).getDestino().getX()+15,
                            arestas.get(i).getDestino().getY()+20);           
                    exibirPesos(g);
        }
    }      
    
    public void exibirPesos(Graphics g){
        ArrayList<Aresta> arestas = grafo.getArestas();        
            for (int i =0; i <  arestas.size(); i++){                                                
                g.drawString(String.valueOf(arestas.get(i).getPeso()), (arestas.get(i).getDestino().getX()+arestas.get(i).getOrigem().getX())/2, (arestas.get(i).getOrigem().getY()+arestas.get(i).getDestino().getY())/2);
            }       
    }
    
    public void adjMatrix()
    {        
      /*  int [][] matrix = new int [grafo.getNumVertices()][grafo.getNumVertices()];
        String [][] dados = new String[grafo.getNumVertices()][grafo.getNumVertices()+1];
        
        for (int i=0; i< grafo.getNumVertices(); i++)
            for (int j=0; j<grafo.getNumVertices() +1; j++)
                if (j==0)
                 dados[i][j] = grafo.getVertice(i).getNome();
                    
        
        for (int i = 0; i < grafo.getNumVertices(); i++)
            for (int j =0; j< grafo.getNumVertices(); j++)
            {
                int valor = matrix[i][j];
                dados[i][j+1] = String.valueOf(valor);
            }
        
        ArrayList<Vertice> v = grafo.getVertices();
        String[] colunas = new String[grafo.getNumVertices()+1];
        colunas[0] = "Vértices";
        for (int i = 0; i < grafo.getNumVertices(); i++)
            colunas[i+1] = v.get(i).getNome();
       
        tableAdjMatrix = new JTable(dados, colunas);
        scrollPane = new JScrollPane(tableAdjMatrix);   
        JFrame mAdjFrame = new JFrame();
        mAdjFrame.setTitle("Matriz de Adjacência");
        mAdjFrame.add(scrollPane);
        mAdjFrame.setSize(360,360);
        mAdjFrame.setVisible(true);         */
    }       

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createTitledBorder("Visualização da Rede - WillFall.NET"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
