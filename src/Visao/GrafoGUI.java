package Visao;

import Controlador.Sistema;
import Modelo.Aresta;
import Modelo.Grafo;
import Modelo.Observer;
import Modelo.Vertice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
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
public class GrafoGUI extends javax.swing.JPanel implements Observer {

    private int x_novoVertice;
    private int y_novoVertice;
    private boolean clickNoGrafo;
    private Grafo grafo;
    JPopupMenu popMenuTerminal = new JPopupMenu();
    JScrollPane scrollPane;
    JButton adicionarVertice;
    JLabel grafoLabel;
    boolean hasclicked1 = false;
    JLabel click1label = null;
    Object resposta;
    boolean click;
    JMenuItem caminhos;   
    boolean primeiroMenorRota = false, segundoMenorRota = false;
    String primeiroString, segundoString;

    /**
     * Creates new form desenhoGrafo
     */
    public GrafoGUI() {
        Sistema.getGrafo().registerObserver(this);
        this.setBackground(Color.WHITE);
        this.grafo = Sistema.getGrafo();
        caminhos = new JMenuItem("Ver melhores caminhos");       
        //popMenuTerminal.add(caminhos);           
        clickNoGrafo = false;
        initComponents();
        setVisible(true);
    }

    public void adicionarEquipamentoMouse(Object tipoEquipamento) {
        resposta = tipoEquipamento;
        if (resposta == null) {
            return;
        }
        clickNoGrafo = true;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (clickNoGrafo) {
                    x_novoVertice = e.getX();
                    y_novoVertice = e.getY();
                    String nomeEquipamento;
                    nomeEquipamento = JOptionPane.showInputDialog(null, "Digite o rótulo do equipamento", "Rótulo", 1);
                    if (nomeEquipamento == null) {
                        clickNoGrafo = false;
                        return;
                    } else {
                        nomeEquipamento = nomeEquipamento.toUpperCase();
                    }
                    if (resposta.equals("Roteador")) {
                        Sistema.adicionarVertice(nomeEquipamento, false, x_novoVertice, y_novoVertice);
                    } else if (resposta.equals("Computador")) {
                        Sistema.adicionarVertice(nomeEquipamento, true, x_novoVertice, y_novoVertice);
                    }
                    adicionarEquipamento(grafo.buscarVertice(nomeEquipamento));
                    clickNoGrafo = false;
                }
            }
        });
    }

    private void adicionarEquipamento(Vertice equipamento) {
        JButton bt_equipamento = new JButton();
        bt_equipamento.setName(equipamento.getNome().toUpperCase());
        this.add(equipamento.getNome(), bt_equipamento);
        bt_equipamento.setVisible(true);
        bt_equipamento.setOpaque(false);
        bt_equipamento.setContentAreaFilled(false);
        bt_equipamento.setBorder(null);
        bt_equipamento.setLocation(equipamento.getX(), equipamento.getY());
        bt_equipamento.setSize(35, 35);
        ImageIcon img, selecionado;
        if (!equipamento.isTerminal()) {
            img = new ImageIcon("imagens/roteador.png");
            selecionado = new ImageIcon("imagens/roteador2.png");
            bt_equipamento.setIcon(new ImageIcon(img.getImage().getScaledInstance(bt_equipamento.getWidth(), bt_equipamento.getHeight(), Image.SCALE_SMOOTH)));
            bt_equipamento.setSelectedIcon(new ImageIcon(selecionado.getImage().getScaledInstance(bt_equipamento.getWidth(), bt_equipamento.getHeight(), Image.SCALE_SMOOTH)));
        } else {
            img = new ImageIcon("imagens/terminal.png");
            bt_equipamento.setIcon(new ImageIcon(img.getImage().getScaledInstance(bt_equipamento.getWidth(), bt_equipamento.getHeight(), Image.SCALE_SMOOTH)));
        }
        JMenuItem menorRota  = new JMenuItem("Visualizar menor rota para outro terminal");;
        JPopupMenu menuMenorRota = new JPopupMenu();        
        menuMenorRota.add(menorRota);
        bt_equipamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(equipamento.isTerminal()){
                    segundoString = equipamento.getNome();
                    segundoMenorRota = true;
                    calculadorMenorRota();
                }
                if (!equipamento.isTerminal()) {
                    bt_equipamento.setSelected(!bt_equipamento.isSelected());
                }
            }
        });
        JLabel rotulo = new JLabel(equipamento.getNome());
        rotulo.setLocation(bt_equipamento.getX() + 15, bt_equipamento.getY() + 50);
        rotulo.setSize(100, 10);
        rotulo.setVisible(true);
        this.add(rotulo);
        this.repaint();
        //popMenuEquipamento(equipamento, bt_equipamento); 
        if (equipamento.isTerminal()) {            
            bt_equipamento.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    menuMenorRota.show(e.getComponent(), e.getX(), e.getY());
                }
            }

        });
        menorRota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!primeiroMenorRota) {
                    primeiroString = bt_equipamento.getName();
                    JOptionPane.showMessageDialog(null, "VERTICE A: " + primeiroString);
                    
                    primeiroMenorRota = true;
                    JOptionPane.showMessageDialog(null, "Selecione outro terminal!");                    
                }                
            }
        });
        
        this.initComponents();
    }
        System.gc();
    }

    private void popMenuEquipamento(Vertice equipamento, JButton bt) {
        click = true;
        bt.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && click) {
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
            }

        });

    }

    public void calculadorMenorRota() {
        if(primeiroMenorRota && segundoMenorRota){
            grafo.calcularMenoresDistancias(grafo.buscarVertice(primeiroString));
            String s = Sistema.menorRotaEntre(primeiroString, segundoString);            
            JOptionPane.showMessageDialog(null, "A melhor rota entre " + primeiroString + " e " + segundoString + " é: \n" + s);
            primeiroMenorRota = false;
            segundoMenorRota = false;
        }
        System.gc();        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGraph(g);
    }

    private void paintGraph(Graphics g) {
        for (int i = 0; i < grafo.getNumVertices(); i++) {
            Vertice v = grafo.getVertices().get(i);
            adicionarEquipamento(v);
        }

        ArrayList<Aresta> arestas = grafo.getArestas();
        for (int i = 0; i < arestas.size(); i++) {
            g.drawLine(arestas.get(i).getOrigem().getX() + 15, arestas.get(i).getOrigem().getY() + 20,
                    arestas.get(i).getDestino().getX() + 15,
                    arestas.get(i).getDestino().getY() + 20);
            exibirPesos(g);
        }
    }

    public void exibirPesos(Graphics g) {
        ArrayList<Aresta> arestas = grafo.getArestas();
        for (int i = 0; i < arestas.size(); i++) {
            g.drawString(String.valueOf(arestas.get(i).getPeso()), (arestas.get(i).getDestino().getX() + arestas.get(i).getOrigem().getX()) / 2, (arestas.get(i).getOrigem().getY() + arestas.get(i).getDestino().getY()) / 2);
        }
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

        @Override
        public void update(Object grafoAtualizado) {
            grafo = (Grafo) grafoAtualizado;
        }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
