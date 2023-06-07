/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.dao.StockDao;
import modelo.entities.Categoria;
import modelo.entities.Produto;

/**
 *
 * @author pc2
 */
public class StockDaoJDBC implements StockDao{
    
     private final Connection conn;

    public StockDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public List<Produto> findByDate(Produto obj) {
       PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM produtos  JOIN categoria on produtos.categoriaId=categoria.id WHERE DATE(sk_data_atualizacao) BETWEEN ? AND ?");
            rs = pst.executeQuery();
            List<Produto> list = new ArrayList<>();
            Map<Integer, Categoria> map = new HashMap<>();
            while(rs.next()){
               Categoria cat = map.get(rs.getInt("categoriaId")); 
                if(cat == null){
                   cat = instatiateCategory(rs);
                   map.put(rs.getInt("categoriaId"), cat);
                }
             
                Produto produto = instatienteProduto(rs, cat);
                list.add(produto);
                
            }
            return list;
        } 
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////
    private Categoria instatiateCategory(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getInt("categoriaId"));
        categoria.setNome(rs.getString("nome"));
        return categoria;
    }
////////////////////////////////////////////////////////////////////////////////
    private Produto instatienteProduto(ResultSet rs, Categoria categoria) throws SQLException {
       Produto produto = new Produto();
        produto.setId(rs.getInt("id"));
        produto.setP_nome(rs.getString("descricao"));
        produto.setBarcode(rs.getInt("barcode"));
        produto.setP_compra(Double.valueOf(rs.getString("p_compra")));
        produto.setP_venda(Double.valueOf(rs.getString("p_venda")));
        produto.AddQuantity(rs.getInt("quantidade"));
        produto.setCategoria(categoria);
        return produto;
    }
    
}
