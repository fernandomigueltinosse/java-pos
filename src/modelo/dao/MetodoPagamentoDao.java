/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.dao;

import java.util.List;
import modelo.entities.MetodoPagamento;

/**
 *
 * @author pc2
 */
public interface MetodoPagamentoDao {
    void insert(MetodoPagamento obj);
    void update (MetodoPagamento obj);
    void deleteById(Integer id);
    MetodoPagamento findById(Integer id);
    List<MetodoPagamento> findAll();
    List<MetodoPagamento> filtrar(String nome);
}
