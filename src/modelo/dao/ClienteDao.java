/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.dao;

import java.util.List;
import modelo.entities.Cliente;

/**
 *
 * @author Tomas
 */
public interface ClienteDao {
    void insert(Cliente obj);
    void update (Cliente obj);
    void deleteById(Integer id);
    Cliente findById(Integer id);
    List<Cliente> findAll();
    List<Cliente> filtrar(String nome);
}
