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
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of adicionarVertice method, of class Sistema.
     */
    @Test
    public void testAdicionarVertice() {
        System.out.println("adicionarVertice");
        String nome = "";
        boolean terminal = false;
        int x = 0;
        int y = 0;
        Sistema.adicionarVertice(nome, terminal, x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adicionarAresta method, of class Sistema.
     */
    @Test
    public void testAdicionarAresta() {
        System.out.println("adicionarAresta");
        Vertice inicio = null;
        Vertice destino = null;
        double peso = 0.0;
        boolean expResult = false;
        boolean result = Sistema.adicionarAresta(inicio, destino, peso);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of melhorCaminho method, of class Sistema.
     */
    @Test
    public void testMelhorCaminho() {
        System.out.println("melhorCaminho");
        String origem = "";
        String destino = "";
        List<Vertice> expResult = null;
        List<Vertice> result = Sistema.melhorCaminho(origem, destino);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of carregarArquivo method, of class Sistema.
     */
    @Test
    public void testCarregarArquivo() throws Exception {
        System.out.println("carregarArquivo");
        File arquivo = null;
        Sistema.carregarArquivo(arquivo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGrafo method, of class Sistema.
     */
    @Test
    public void testGetGrafo() {
        System.out.println("getGrafo");
        Grafo expResult = null;
        Grafo result = Sistema.getGrafo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of identificarMelhorCaminho method, of class Sistema.
     */
    @Test
    public void testIdentificarMelhorCaminho() {
        System.out.println("identificarMelhorCaminho");
        String nome = "";
        JScrollPane expResult = null;
        JScrollPane result = Sistema.identificarMelhorCaminho(nome);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvarArquivo method, of class Sistema.
     */
    @Test
    public void testSalvarArquivo() throws Exception {
        System.out.println("salvarArquivo");
        String path = "";
        Sistema.salvarArquivo(path);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removerVertice method, of class Sistema.
     */
    @Test
    public void testRemoverVertice() {
        System.out.println("removerVertice");
        String nome = "";
        boolean expResult = false;
        boolean result = Sistema.removerVertice(nome);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetarRede method, of class Sistema.
     */
    @Test
    public void testResetarRede() {
        System.out.println("resetarRede");
        Sistema.resetarRede();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of menorRotaEntre method, of class Sistema.
     */
    @Test
    public void testMenorRotaEntre() {
        System.out.println("menorRotaEntre");
        String origem = "";
        String destino = "";
        List<Vertice> expResult = null;
        List<Vertice> result = Sistema.menorRotaEntre(origem, destino);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcularCoordenadasEuclidianas method, of class Sistema.
     */
    @Test
    public void testCalcularCoordenadasEuclidianas() {
        System.out.println("calcularCoordenadasEuclidianas");
        String equipamento1 = "";
        String equipamento2 = "";
        double expResult = 0.0;
        double result = Sistema.calcularCoordenadasEuclidianas(equipamento1, equipamento2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
