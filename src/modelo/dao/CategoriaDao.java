/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.dao;

import java.util.List;
import modelo.entities.Categoria;



public interface CategoriaDao {
    void insert(Categoria obj);
    void update (Categoria obj);
    void deleteById(Integer id);
    Categoria findById(Integer id);
    Categoria findByName(String name);
    Categoria findAll2();
    List<Categoria> findAll();
    List<Categoria> filtrar(String nome);
}
