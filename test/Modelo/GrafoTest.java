/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ander
 */
public class GrafoTest {
    
    public GrafoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getNumVertices method, of class Grafo.
     */
    @Test
    public void testGetNumVertices() {
        Grafo instance = new Grafo();
        int expResult = 0;
        int result = instance.getNumVertices();
        assertEquals(expResult, result, "Numero de vertices do grafo"
                + "apos nao adicionar nenhum vertice.");
    }

    /**
     * Test of getVertice method, of class Grafo.
     */
    @Test
    public void testGetVertice() {
        int i = 0;
        Grafo instance = new Grafo();
        Vertice expResult = null;
        Vertice result = instance.getVertice(i);
        assertEquals(expResult, result, "Vertice do indice 0 da lista de vertices"
                + "do grafo.");
    }

    /**
     * Test of getVertices method, of class Grafo.
     */
    @Test
    public void testGetVertices() {
        Grafo instance = new Grafo();
        int expResult = 0;
        int result = instance.getVertices().size();
        assertEquals(expResult, result, "Lista de vertices do grafo apos nao ser"
                + "adicionado nenhum vertice");
    }

    /**
     * Test of buscarVertice method, of class Grafo.
     */
    @Test
    public void testBuscarVertice() {
        String nome = "";
        Grafo instance = new Grafo();
        Vertice expResult = null;
        Vertice result = instance.buscarVertice(nome);
        assertEquals(expResult, result,"Valor retornado ao buscar um vertice"
                + " no grafo e o mesmo nao for achado");

    }

    /**
     * Test of adicionarVertice method, of class Grafo.
     */
    @Test
    public void testAdicionarVertice() {
        String nome = "";
        boolean terminal = false;
        int x = 0;
        int y = 0;
        Grafo instance = new Grafo();
        instance.adicionarVertice(nome, terminal, x, y);
        int expResult = 1;
        int result = instance.getNumVertices();
        assertEquals(expResult,result, "Numero de vertices do grafo apos "
                + "ser adicionado um vertice." );
    }

    /**
     * Test of removerVertice method, of class Grafo.
     */
    @Test
    public void testRemoverVertice() {
        String nome = "";
        Grafo instance = new Grafo();
        boolean expResult = false;
        boolean result = instance.removerVertice(nome);
        assertEquals(expResult, result, "Boolean indicativo para uma remocao "
                + "foi mal sucedida.");
    }

    /**
     * Test of adicionarAresta method, of class Grafo.
     */
    @Test
    public void testAdicionarAresta() {
        Vertice origem = null;
        Vertice destino = null;
        double peso = 0.0;
        Grafo instance = new Grafo();
        instance.adicionarAresta(origem, destino, peso);
        int expResult = 0;
        int result = instance.getArestas().size();
        assertEquals(expResult, result, "Numero de arestas do grafo apos"
                + "adicionar uma aresta com vertices inexistentes.");
    }



    /**
     * Test of getCaminhoMaisCurtoEntreVertices method, of class Grafo.
     */
    @Test
    public void testGetCaminhoMaisCurtoEntreVertices() {
        String Origem = "";
        String Destino = "";
        Grafo instance = new Grafo();
        List<Vertice> expResult = null;
        List<Vertice> result = instance.getCaminhoMaisCurtoEntreVertices(Origem, Destino);
        assertEquals(expResult, result, "Caminho mais curto entre verties inexistentes.");
    }


    /**
     * Test of resetar method, of class Grafo.
     */
    @Test
    public void testResetar() {
        Grafo instance = new Grafo();
        instance.resetar();
        int expResult = 0;
        int result = instance.getNumVertices();
        assertEquals(expResult, result, "Numero de vertices apos resetar "
                + " o grafo.");
    }


    
}
