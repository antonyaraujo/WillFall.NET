/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 * Interface para objetos que devem observar outros objetos.
 * @author ander
 */
public interface Observer {
    /**
     * Atualiza esse Observer com determinado dado.
     * @param obj - Dado para atualizacao.
     */
    public void update(Object obj);
}