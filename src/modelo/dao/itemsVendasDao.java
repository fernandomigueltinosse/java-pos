/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.dao;
import java.util.ArrayList;
import java.util.List;
    import modelo.entities.ItemsVenda;
import modelo.entities.Produto;

public interface itemsVendasDao {
    
   ArrayList<ItemsVenda> ListItemsVendas = new ArrayList<>();
     void insert(ArrayList<ItemsVenda> ListItemsVendas);
     
     
     List<ItemsVenda> findById(Integer id);
     ItemsVenda findTotal(Integer id);
    
}

