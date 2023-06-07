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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.dao.itemsVendasDao;
import modelo.entities.ItemsVenda;
import modelo.entities.Produto;

/**
 *
 * @author pc2
 */
public class ItemsVendaDaoJDBC implements itemsVendasDao{

    private final Connection conn;
     
    public ItemsVendaDaoJDBC(Connection conn){
        this.conn= conn;
    }

    @Override
    public void insert(ArrayList<ItemsVenda> ListItemsVendas) {
       int count = ListItemsVendas.size();
        
            PreparedStatement pst = null;
        try {
            for (int i = 0; i < count; i++) {
            pst = conn.prepareStatement("INSERT INTO venda_produto(vendas_id, produtos_id, preco_venda, quantidade) VALUES (?,?,?,?)");
            pst.setInt(1, ListItemsVendas.get(i).getVenda().getId());
            pst.setInt(2, ListItemsVendas.get(i).getProduto().getId());
            pst.setDouble(3, ListItemsVendas.get(i).getPreco());
           pst.setInt(4, ListItemsVendas.get(i).getQuantidade());
          
            pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "items Cadastrado com sucesso");
        }
        } catch (SQLException e) {
             throw new DbException(e.getMessage());
        }
          finally {
            DB.closeStatement(pst);
        } 
        
  
        }

    @Override
    public List<ItemsVenda> findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT p_nome,preco_venda,venda_produto.quantidade as quantidadeVendida, (venda_produto.preco_venda - produtos.p_compra) * venda_produto.quantidade AS lucro FROM venda_produto JOIN produtos ON venda_produto.produtos_id=produtos.id WHERE venda_produto.vendas_id=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            List<ItemsVenda> list = new ArrayList<>();
            //Map<Integer, Categoria> map = new HashMap<>();
            while(rs.next()){
              Produto produto =  new Produto();
              produto.setP_nome(rs.getString("p_nome"));
              
                ItemsVenda items = new ItemsVenda();
                items.setPreco(rs.getDouble("preco_venda"));
                items.setQuantidade(rs.getInt("quantidadeVendida"));
                items.setProduto(produto);
                items.setLucros(rs.getDouble("lucro"));
                list.add(items);
                
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
///////////////////////////calcula o lucro total das vendas--------------------
    @Override
    public ItemsVenda findTotal(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT  SUM((venda_produto.preco_venda - produtos.p_compra) * venda_produto.quantidade) AS lucro_total FROM venda_produto JOIN produtos ON venda_produto.produtos_id=produtos.id WHERE venda_produto.vendas_id=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                ItemsVenda vendas = new ItemsVenda();
                vendas.setTotalLucros(rs.getDouble("lucro_total"));
                return vendas;
            }
            return  null;
        } 
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }
    
    
       
    }

    
    
