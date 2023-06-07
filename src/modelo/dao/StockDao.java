/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.dao;

import java.util.List;
import modelo.entities.Produto;

/**
 *
 * @author pc2
 */
public interface StockDao {
    List<Produto> findByDate(Produto obj);
    
}
