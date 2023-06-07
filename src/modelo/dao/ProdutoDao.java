/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.entities.Categoria;
import modelo.entities.Produto;


/**
 *
 * @author pc2
 */
public interface ProdutoDao {
    void insert (Produto obj);
    void update (Produto obj);
    void updateStock (Produto obj);
    void deleteById(Integer id);
    Produto findById(Integer id);
    Produto findBbarCode(Integer barcode);
    Produto findByName(String name);
    List<Produto> findAll();
    List<Produto> filtrar(String nome);
    List<Produto> findByCategory(Categoria categoria);
     List<Produto> findByDate(String data1,String data2);
    public void updateStock(ArrayList<Produto> listProdutos);
}
