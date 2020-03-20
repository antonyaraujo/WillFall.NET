/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import Modelo.Grafo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author antony
 */
public class GrafoTest {
    
    Grafo grafo;
    
    public GrafoTest() {
        grafo = new Grafo();
    }       

    /**
     * 
     */
    @Test
    public void testarPosicao(){
        grafo.adicionarVertice("Julinho", true, 0, 0);
        assertEquals("Verifica se o vértice foi adicionado na posição zero", "Julinho", grafo.getVertice(0).getNome());
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
