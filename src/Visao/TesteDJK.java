/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao;

import Modelo.Grafo;

/**
 *
 * @author ander
 */
public class TesteDJK {

    public static Grafo g = new Grafo();
    public static void main(String[] args) {
        
        g.adicionarVertice("A");
        g.adicionarVertice("B");
        g.adicionarVertice("C");
        g.adicionarVertice("D");
        g.adicionarVertice("E");
        g.adicionarVertice("F");
        g.adicionarVertice("G");
       
        g.adicionarAresta("A","B", 2);
        g.adicionarAresta("B","C", 3);
        g.adicionarAresta("C","F", 2);
        g.adicionarAresta("A","D", 10);
        g.adicionarAresta("D","E", 10);
        g.adicionarAresta("E","F", 10);
        
        
        //System.out.println(g.getCaminhoMaisCurtoEntreVertices("A", "F"));
        g.calcularMenoresDistancias(g.buscarVertice("B"));
        System.out.println(g.getCaminhoMaisCurtoPara(g.buscarVertice("F")));
        System.out.println(g.getCaminhoMaisCurtoEntreVertices("B", "F"));
        System.out.println(g.getCaminhoMaisCurtoEntreVertices("D", "F"));
        
        // TODO code application logic here
    }
    
}
