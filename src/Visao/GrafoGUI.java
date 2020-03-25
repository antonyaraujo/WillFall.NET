package Visao;

import Controlador.Sistema;
import Modelo.Aresta;
import Modelo.Vertice;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Painel onde o grafo pode ser desenhado.
 * @author antony
 */
public class GrafoGUI extends javax.swing.JPanel{

    private int x_novoVertice;
    private int y_novoVertice;
    private boolean clickNoGrafo;
    private JScrollPane scrollPane;
    private JButton adicionarVertice;
    private JLabel grafoLabel;
    private boolean hasclicked1 = false;
    private JLabel click1label = null;
    private Object resposta;
    private boolean click;
    private boolean primeiroMenorRota = false, segundoMenorRota = false;
    private String primeiroString, segundoString;
    private boolean destacarRota = false;
    private List<Vertice> menorRotaVertices;
    private boolean exibirPesos = true;

    /**
     * Creates new form desenhoGrafo
     */
    public GrafoGUI() {
        this.setBackground(Color.WHITE);
        clickNoGrafo = false;
        initComponents();
        setVisible(true);
    }

    /**
     * Adiciona um equipamento no painel.
     * @param tipoEquipamento - Tipo do equipamento a ser adicionado.
     */
    public void adicionarEquipamentoMouse(Object tipoEquipamento) {
        resposta = tipoEquipamento;
        if (resposta == null) {
            return;
        }
        JOptionPane.showMessageDialog(null, "Selecione no painel a localização do novo " + resposta, "Localização", JOptionPane.INFORMATION_MESSAGE);
        clickNoGrafo = true;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (clickNoGrafo) {
                    x_novoVertice = e.getX();
                    y_novoVertice = e.getY();
                    String nomeEquipamento;
                    nomeEquipamento = JOptionPane.showInputDialog(null, "Digite o rótulo do equipamento", "Rótulo", 1).toUpperCase();
                    if (nomeEquipamento == null) {
                        clickNoGrafo = false;
                        return;
                    } 
                    else {
                        if (Sistema.getGrafo().buscarVertice(nomeEquipamento) == null) 
                        {
                            if (resposta.equals("Computador")) 
                            {
                                Sistema.adicionarVertice(nomeEquipamento, true, x_novoVertice, y_novoVertice);
                            } 
                            else 
                            {
                                Sistema.adicionarVertice(nomeEquipamento, false, x_novoVertice, y_novoVertice);
                            }
                            adicionarEquipamento(Sistema.getGrafo().buscarVertice(nomeEquipamento));
                        } 
                        else 
                        {
                            JOptionPane.showMessageDialog(null, "Este nome já está sendo utilizado por outro equipamento");
                        }
                    }
                    repaint();
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
        bt_equipamento.setBackground(Color.WHITE);
        //bt_equipamento.setOpaque(false);
        //.setContentAreaFilled(false);
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
        JMenuItem menorRota = new JMenuItem("Caminho menos custoso (Terminal)");
        JMenuItem menorRotaTodos = new JMenuItem("Caminho menos custoso (Todos)");
        JMenuItem menorRotaTodosNaoTerminal = new JMenuItem("Caminho menos custoso (Todos)");
        JPopupMenu terminalMenu = new JPopupMenu();
        JPopupMenu naoTerminalMenu = new JPopupMenu();
        terminalMenu.add(menorRota);
        terminalMenu.add(menorRotaTodos);
        naoTerminalMenu.add(menorRotaTodosNaoTerminal);
        bt_equipamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (equipamento.isTerminal() && primeiroMenorRota) {
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
        rotulo.setLocation(bt_equipamento.getX() + 5, bt_equipamento.getY() + 40);
        rotulo.setFont(new Font("default", Font.ROMAN_BASELINE, 11));        
        rotulo.setSize(100, 10);
        rotulo.setVisible(true);
        this.add(rotulo);
        //this.repaint();

        if (equipamento.isTerminal()) {
            bt_equipamento.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        terminalMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }

            });
            menorRota.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!primeiroMenorRota) {
                        primeiroString = bt_equipamento.getName();
                        JOptionPane.showMessageDialog(null, "TERMINAL SELECIONADO: " + primeiroString);

                        primeiroMenorRota = true;
                        JOptionPane.showMessageDialog(null, "Selecione outro terminal!");
                    }
                }
            });

            menorRotaTodos.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JScrollPane barraRolagem = Sistema.identificarMelhorCaminho(equipamento.getNome());
                    JFrame exibir = new JFrame("Caminhos menos custosos do " + equipamento.getNome());
                    exibir.add(barraRolagem);
                    exibir.setSize(600, 200);
                    exibir.setVisible(true);
                    exibir.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    exibir.setLocationRelativeTo(null);
                }
            });
        } else {
            bt_equipamento.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        naoTerminalMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }

            });

            menorRotaTodosNaoTerminal.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JScrollPane barraRolagem = Sistema.identificarMelhorCaminho(equipamento.getNome());
                    JFrame exibir = new JFrame("Caminhos menos custosos do " + equipamento.getNome());
                    exibir.add(barraRolagem);
                    exibir.setSize(600, 200);
                    exibir.setVisible(true);
                    exibir.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    exibir.setLocationRelativeTo(null);
                }
            });
        }
    }

    /**
     *
     */
    public void calculadorMenorRota() {
        if (primeiroMenorRota && segundoMenorRota) {
            menorRotaVertices = Sistema.menorRotaEntre(primeiroString, segundoString);
            destacarRota = true;
            repaint();
            JOptionPane.showMessageDialog(null, "A menor rota entre " + primeiroString + "e " + segundoString + " está destacada no grafo"
                    + "\nRota: " + menorRotaVertices + "\nPressione OK para não destacar mais a rota");
            repaint();
        }
        menorRotaVertices.clear();
        primeiroMenorRota = false;
        segundoMenorRota = false;
    }

    /**
     * Desenha o grafo no painel.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGraph(g);
        if (destacarRota) {
            paintMenorRota(g);
        }
        if (exibirPesos) {
            exibirPesos(g);
        }
    }

    private void paintGraph(Graphics g) {
        for (int i = 0; i < Sistema.getGrafo().getNumVertices(); i++) {
            Vertice v = Sistema.getGrafo().getVertices().get(i);
            adicionarEquipamento(v);
        }

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        ArrayList<Aresta> arestas = Sistema.getGrafo().getArestas();
        for (int i = 0; i < arestas.size(); i++) {
            g2d.drawLine(arestas.get(i).getOrigem().getX() + 15, arestas.get(i).getOrigem().getY() + 20,
                    arestas.get(i).getDestino().getX() + 15,
                    arestas.get(i).getDestino().getY() + 20);
        }
    }

    private void paintMenorRota(Graphics g) {        
        g.setColor(Color.red);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = 0; i < menorRotaVertices.size() - 1; i++) {
            Vertice v1 = menorRotaVertices.get(i);
            Vertice v2 = menorRotaVertices.get(i + 1);            
            g2d.drawLine(v1.getX() + 15, v1.getY() + 20, v2.getX() + 15, v2.getY() + 20);            
        }
        g.setColor(Color.black);
        destacarRota = false;
    }

    /**
     *
     * @param e
     */
    public void setExibirPeso(boolean e) {
        exibirPesos = e;
    }

    /**
     * Desenha os pesos das arestas do grafo.
     * @param g
     */
    public void exibirPesos(Graphics g) {
        ArrayList<Aresta> arestas = Sistema.getGrafo().getArestas();
        g.setColor(Color.getColor("39 64 139"));        
        for (int i = 0; i < arestas.size(); i++) {            
            g.setFont(new Font("default", Font.BOLD, 10));
            g.drawString(String.valueOf(arestas.get(i).getPeso()), ((arestas.get(i).getDestino().getX() + arestas.get(i).getOrigem().getX()) / 2)+10, ((arestas.get(i).getOrigem().getY() + arestas.get(i).getDestino().getY()) / 2)+10);
        }
        g.setColor(Color.black);
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

    /**
     *
     * @param destacarRota
     */
    public void setDestacarRota(boolean destacarRota) {
        this.destacarRota = destacarRota;
    }

    /**
     *
     * @param menorRotaVertices
     */
    public void setMenorRotaVertices(List<Vertice> menorRotaVertices) {
        this.menorRotaVertices = menorRotaVertices;
    }
    



    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
