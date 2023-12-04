package org.teste.tecnico.utils.interfaces;

/**
 * Contrato de Crud com parametro generico
 * @author iago.holek
 * @param <T>
 */
public interface Crudable<T> {

   void create(T obj);

}
