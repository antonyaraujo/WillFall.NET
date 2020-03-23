/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 * Interface para objetos que devem ser observados por outros objetos.
 * @author ander
 */
public interface Observable {
    
    /**
     * Registra um novo observer.
     * @param observer - Observer a ser registrado.
     */
    public void registerObserver(Observer observer);

    /**
     * Remove um observer.
     * @param observer - Observer que deve ser removido.
     */
    public void removeObserver(Observer observer);

    /**
     * Notifica os Observers desse Observable sobre possiveis alteracoes.
     */
    public void notifyObservers();
    
}
