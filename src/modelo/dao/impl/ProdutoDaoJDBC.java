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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.dao.ProdutoDao;
import modelo.entities.Categoria;
import modelo.entities.Cliente;
import modelo.entities.Produto;

/**
 *
 * @author pc2
 */
public class ProdutoDaoJDBC implements ProdutoDao {
    
    private final Connection conn;
    
    public ProdutoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Produto obj) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("INSERT INTO produtos(p_nome,p_compra, p_venda,quantidade,barcode,categoriaId) "
                    + "VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, obj.getP_nome());
            pst.setDouble(2, obj.getP_compra());
            pst.setDouble(3, obj.getP_venda());
            pst.setInt(4, obj.getQuantidade());
            pst.setInt(5, obj.getBarcode());
            pst.setInt(6, obj.getCategoria().getId());
            int rowsSffected = pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");
            if (rowsSffected > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                    DB.closeResultSet(rs);
                }
            } else {
                throw new DbException("erro inesperado! nenhuma linha foi afectada");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }
    }
///////////////////////////////////////////////////////////////////////////////////

    @Override
    public void update(Produto obj) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("UPDATE produtos set p_nome=?, p_compra=?, p_venda=?,quantidade=?,barcode=?,categoriaId=? where id=?");
            pst.setString(1, obj.getP_nome());
            pst.setDouble(2, obj.getP_compra());
            pst.setDouble(3, obj.getP_venda());
            pst.setInt(4, obj.getQuantidade());
            pst.setInt(5, obj.getBarcode());
            pst.setInt(6, obj.getCategoria().getId());
            pst.setInt(7, obj.getId());
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Actualizado com sucesso");
            
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }
    }
////////////////////////////////////////////////////////////////////////////////

    @Override
    public void deleteById(Integer id) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("DELETE FROM produtos where id=?");
            pst.setInt(1, id);
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Apagado com sucesso");
            
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }
    }
/////////////////////////////////////////////////////////////////////////////////

    @Override
    public Produto findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM produtos join categoria on produtos.categoriaId=categoria.id where produtos.id = ?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                Categoria categoria = instatiateCategory(rs);
                
                Produto produto = instatienteProduto(rs, categoria);
                return produto;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }
//////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<Produto> findAll() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM produtos  JOIN categoria on produtos.categoriaId=categoria.id");
            rs = pst.executeQuery();
            List<Produto> list = new ArrayList<>();
            Map<Integer, Categoria> map = new HashMap<>();
            while (rs.next()) {
                Categoria cat = map.get(rs.getInt("categoriaId"));                
                if (cat == null) {
                    cat = instatiateCategory(rs);
                    map.put(rs.getInt("categoriaId"), cat);
                }
                
                Produto produto = instatienteProduto(rs, cat);
                list.add(produto);
                
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }
    
    @Override
    public List<Produto> filtrar(String nome) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM produtos  JOIN categoria on produtos.categoriaId=categoria.id WHERE p_nome like ? OR barcode LIKE ? ");
            pst.setString(1, nome + "%");
            pst.setString(2, nome + "%");
            rs = pst.executeQuery();
            List<Produto> list = new ArrayList<>();
            Map<Integer, Categoria> map = new HashMap<>();
            while (rs.next()) {
                Categoria cat = map.get(rs.getInt("categoriaId"));                
                if (cat == null) {
                    cat = instatiateCategory(rs);
                    map.put(rs.getInt("categoriaId"), cat);
                }
                
                Produto produto = instatienteProduto(rs, cat);
                list.add(produto);
                
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }
//////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<Produto> findByCategory(Categoria categoria) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM produtos  JOIN categoria on produtos.categoriaId=categoria.id WHERE categoria.id=?");
            pst.setInt(1, categoria.getId());
            rs = pst.executeQuery();
            List<Produto> list = new ArrayList<>();
            Map<Integer, Categoria> map = new HashMap<>();
            while (rs.next()) {
                Categoria cat = map.get(rs.getInt("categoriaId"));                
                if (cat == null) {
                    cat = instatiateCategory(rs);
                    map.put(rs.getInt("categoriaId"), cat);
                }
                
                Produto produto = instatienteProduto(rs, cat);
                list.add(produto);
                
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }
    
    @Override
    public void updateStock(ArrayList<Produto> listProdutos) {
        PreparedStatement pst = null;
        try {
            for (int i = 0; i < listProdutos.size(); i++) {
                pst = conn.prepareStatement("UPDATE produtos set quantidade=? where id=?");
                pst.setInt(1, listProdutos.get(i).getQuantidade());
                pst.setInt(2, listProdutos.get(i).getId());
                
                pst.executeUpdate();
                //JOptionPane.showMessageDialog(null, "items Cadastrado com sucesso");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }        
        
    }
    
    @Override
    public Produto findBbarCode(Integer barcode) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM produtos join categoria on produtos.categoriaId=categoria.id where produtos.barcode = ?");
            pst.setInt(1, barcode);
            rs = pst.executeQuery();
            if (rs.next()) {
                Categoria categoria = instatiateCategory(rs);
                
                Produto produto = instatienteProduto(rs, categoria);
                return produto;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
        
    }
    
    @Override
    public Produto findByName(String name) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM produtos join categoria on produtos.categoriaId=categoria.id where produtos.p_nome = ?");
            pst.setString(1, name);
            rs = pst.executeQuery();
            if (rs.next()) {
                Categoria categoria = instatiateCategory(rs);
                
                Produto produto = instatienteProduto(rs, categoria);
                return produto;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }
    
    @Override
    public void updateStock(Produto obj) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("UPDATE produtos set quantidade=? where id=?");
            pst.setInt(1, obj.getQuantidade());
            pst.setInt(2, obj.getId());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Actualizado com sucesso");
            
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }
    }
    
    @Override
    public List<Produto> findByDate(String data1, String data2) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM produtos  JOIN categoria on produtos.categoriaId=categoria.id WHERE DATE(sk_data_atualizacao) BETWEEN ? AND ?");
            pst.setString(1, data1);
            pst.setString(2, data2);
            rs = pst.executeQuery();
            List<Produto> list = new ArrayList<>();
            Map<Integer, Categoria> map = new HashMap<>();
            while (rs.next()) {
                Categoria cat = map.get(rs.getInt("categoriaId"));                
                if (cat == null) {
                    cat = instatiateCategory(rs);
                    map.put(rs.getInt("categoriaId"), cat);
                }
                
                Produto produto = instatienteProduto(rs, cat);
                list.add(produto);
                
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
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
        produto.setP_nome(rs.getString("p_nome"));
        produto.setBarcode(rs.getInt("barcode"));
        produto.setP_compra(Double.valueOf(rs.getString("p_compra")));
        produto.setP_venda(Double.valueOf(rs.getString("p_venda")));
        produto.setSk_data_atualizacao(rs.getDate("sk_data_atualizacao"));
        produto.setSk_data_registro(rs.getDate("sk_data_registro"));
        produto.AddQuantity(rs.getInt("quantidade"));
        
        produto.setCategoria(categoria);
        return produto;
    }
    
}
