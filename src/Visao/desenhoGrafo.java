/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao;

import Modelo.Aresta;
import Modelo.Grafo;
import Modelo.Vertice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author antony
 */
public class desenhoGrafo extends javax.swing.JPanel {

    private int x_novoVertice;
    private int y_novoVertice;
    private Grafo grafo;
    JPopupMenu popMenu = new JPopupMenu();
    JMenuItem addVertexMenuItem = new JMenuItem("Adicionar vértice");
    JMenuItem addEdgeMenuItem = new JMenuItem("Adicionar aresta");
    JTable tableAdjMatrix;
    JScrollPane scrollPane;
    JButton adjMatrixButton;
    JButton eulerianidade;
    JLabel grafoLabel;
    /**
     * Creates new form desenhoGrafo
     */
        
    public desenhoGrafo(Grafo grafo) {
        
        
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
        
        eulerianidade = new JButton("Conferir Eulerianidade do Grafo");
        add(eulerianidade);
        eulerianidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //euleriano();
            }
        });  
        initComponents();
        setVisible(true);
    }
    
    public Grafo getGrafo(){
        return grafo;
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
                String label = JOptionPane.showInputDialog(popMenu,"Digite o rótulo do vértice", "Rótulo", 1).toUpperCase();
                if (label==null)
                    return;
                grafo.adicionarVertice(label, x_novoVertice, y_novoVertice);
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
            g.setColor(Color.BLACK);
            g.fillOval(grafo.getVertice(i).getX(), grafo.getVertice(i).getY(), 40, 40);
            g.setColor(Color.WHITE);
            g.drawString(grafo.getVertice(i).getNome(), grafo.getVertice(i).getX()+17, grafo.getVertice(i).getY()+23);
            g.setColor(Color.BLACK);
        }
        
        ArrayList<Aresta> arestas = grafo.getArestas();
        for (int i =0; i <  arestas.size(); i++){                                        
                    g.drawLine(arestas.get(i).getOrigem().getX() +10, arestas.get(i).getOrigem().getY() +10, 
                            arestas.get(i).getDestino().getX() +10,
                            arestas.get(i).getDestino().getY() + 10);           
                     g.drawString(String.valueOf(arestas.get(i).getPeso()), (arestas.get(i).getDestino().getX()+arestas.get(i).getOrigem().getX())/2, (arestas.get(i).getOrigem().getY()+arestas.get(i).getDestino().getY())/2);
        }
    }
        
    /*@Override
    public Dimension getPreferredSize()
    {
     return new Dimension(1200, 680);
    }*/
    
    public void adjMatrix()
    {        
        int [][] matrix = new int [grafo.getNumVertices()][grafo.getNumVertices()];
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
        mAdjFrame.setVisible(true);
        
    }
    
        private void buildGraph7()
    {
  
        grafo.adicionarVertice("A", 200, 350);
        grafo.adicionarVertice("B", 480, 224);
        grafo.adicionarVertice("C", 870, 224);
        grafo.adicionarVertice("D", 1120, 350);
        grafo.adicionarVertice("E", 870, 530);
        grafo.adicionarVertice("F", 480, 530);
        grafo.adicionarVertice("G", 675, 350);
        
        grafo.adicionarAresta("A", "B", 5);
        grafo.adicionarAresta("A", "F", 5);
        grafo.adicionarAresta("B", "C", 5);
        grafo.adicionarAresta("B", "F", 5);
        grafo.adicionarAresta("B", "G", 5);
        grafo.adicionarAresta("C", "D", 5);
        grafo.adicionarAresta("C", "E", 5);
        grafo.adicionarAresta("C", "G", 5);
        grafo.adicionarAresta("D", "E", 5);
        grafo.adicionarAresta("E", "F", 5);
        grafo.adicionarAresta("E", "G", 5);
        grafo.adicionarAresta("F", "G", 5);
    }
    
    
    private void buildGraph4()
    {
        grafo.adicionarVertice("A", 500, 180);
        grafo.adicionarVertice("B", 800, 180);
        grafo.adicionarVertice("C", 800, 480);
        grafo.adicionarVertice("D", 500, 480);
        
        grafo.adicionarAresta("A", "B", 5);
        grafo.adicionarAresta("A", "D", 5);
        grafo.adicionarAresta("B", "C", 5);
        grafo.adicionarAresta("C", "D", 5);
 
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

        getAccessibleContext().setAccessibleName("Visualização da Rede - WillFall.NET");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
