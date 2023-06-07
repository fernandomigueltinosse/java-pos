/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.dao;

import java.util.List;
import modelo.entities.Vendas;

/**
 *
 * @author Tomas
 */
public interface VendaDao {
    void insert(Vendas vendas);
    List<Vendas> filtrarByDate(String date1, String data2);
    List<Vendas> findAll();

    
}
