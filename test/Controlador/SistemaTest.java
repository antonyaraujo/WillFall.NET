/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Grafo;
import Modelo.Vertice;
import java.io.File;
import java.util.List;
import javax.swing.JScrollPane;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ander
 */
public class SistemaTest {
    
    public SistemaTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        Sistema.resetarRede();
    }
    
    @AfterEach
    public void tearDown() {
    }


    /**
     * Test of adicionarAresta method, of class Sistema.
     */
    @Test
    public void testAdicionarAresta() {
        Vertice inicio = null;
        Vertice destino = null;
        double peso = 0.0;
        boolean expResult = false;
        boolean result = Sistema.adicionarAresta(inicio, destino, peso);
        assertEquals(expResult, result,"Adicao de uma aresta entre vertices"
                + "inexistentes");

  
    }

    /**
     * Test of melhorCaminho method, of class Sistema.
     */
    @Test
    public void testMelhorCaminho() {
        String origem = "";
        String destino = "";
        List<Vertice> expResult = null;
        List<Vertice> result = Sistema.melhorCaminho(origem, destino);
        assertEquals(expResult, result,"Melhor caminho entre vertices inexistentes");
    }

    /**
     * Test of identificarMelhorCaminho method, of class Sistema.
     */
    @Test
    public void testIdentificarMelhorCaminho() {
        String nome = "";
        JScrollPane expResult = null;
        JScrollPane result = Sistema.identificarMelhorCaminho(nome);
        assertEquals(expResult, result);

    }

    /**
     * Test of removerVertice method, of class Sistema.
     */
    @Test
    public void testRemoverVertice() {
        String nome = "";
        boolean expResult = false;
        boolean result = Sistema.removerVertice(nome);
        assertEquals(expResult, result, "Condicao de uma remocao bem sucedida");
    }

    /**
     * Test of resetarRede method, of class Sistema.
     */
    @Test
    public void testResetarRede() {
        Sistema.resetarRede();
        int expResult = 0;
        int result = Sistema.getGrafo().getNumVertices();
        assertEquals(expResult, result, "Numero de equipamentos apos"
                + " resetar a rede.");

    }

    /**
     * Test of menorRotaEntre method, of class Sistema.
     */
    @Test
    public void testMenorRotaEntre() {
        String origem = "";
        String destino = "";
        List<Vertice> expResult = null;
        List<Vertice> result = Sistema.menorRotaEntre(origem, destino);
        assertEquals(expResult, result, "Menor rota entre equipamentos "
                + " inexistentes");
    }

    /**
     * Test of calcularCoordenadasEuclidianas method, of class Sistema.
     */
    @Test
    public void testCalcularCoordenadasEuclidianas() {
        String equipamento1 = "";
        String equipamento2 = "";
        double expResult = -1;
        double result = Sistema.calcularCoordenadasEuclidianas(equipamento1, equipamento2);
        assertEquals(expResult, result, "Distancia entre equipamentos inexistentes");
    }
    
}
