/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao.impl;

import db.DB;
import db.DbException;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import modelo.dao.CategoriaDao;
import modelo.entities.Categoria;
import modelo.entities.Usuario;


public class CategoriaDaoJBDC implements CategoriaDao{
    
    private final Connection conn;
    public CategoriaDaoJBDC(Connection conn){
        this.conn = conn;
    }
////////////////////////////////////////////////////////////////////////////////
    @Override
    public void insert(Categoria obj) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("INSERT INTO categoria(nome) "
                    + "VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, obj.getNome());
            int rowsSffected = pst.executeUpdate();
            
            if(rowsSffected >0){
                ResultSet rs = pst.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                     DB.closeResultSet(rs);
                }
            }else{
                throw  new DbException("erro inesperado! nenhuma linha foi afectada");
            }
        } catch (SQLException e) {
             throw new DbException(e.getMessage());
        }
          finally {
            DB.closeStatement(pst);
        }
    }

////////////////////////////////////////////////////////////////////////////////    
    @Override
    public void update(Categoria obj) {
       PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("UPDATE categoria SET nome=? WHERE id=?");
            pst.setString(1, obj.getNome());
            pst.setInt(2, obj.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
             throw new DbException(e.getMessage());
        }
          finally {
            DB.closeStatement(pst);
        }
    }
////////////////////////////////////////////////////////////////////////////////
    @Override
    public void deleteById(Integer id) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("delete from categoria where id= ?");
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(pst);
        }
        
    }
///////////////////////////////////////////////////////////////////////////////
    @Override
    public Categoria findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM categoria WHERE id=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
                return cat;
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
    
    ///////////////////////////////////////////////////////////////////////////////

    @Override
    public List<Categoria> findAll() {
         PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM categoria where id=2");
            rs = pst.executeQuery();
            List <Categoria> list = new  ArrayList<>();
            while(rs.next()){
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
                list.add(cat);
            }
          
            return  list;
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
@Override
    public List<Categoria> filtrar(String nome) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM categoria where nome like ?");
            pst.setString(1, nome + "%");
            rs = pst.executeQuery();
            List <Categoria> list = new  ArrayList<>();
            //Map<Integer, Categoria> map = new HashMap<>();
            while(rs.next()){
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNome(rs.getString("nome"));
               
                list.add(categoria);
            }
            return  list;
        } 
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }    

    @Override
    public Categoria findAll2() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM categoria");
            rs = pst.executeQuery();
           
            if(rs.next()){
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
                return  cat;
            }
          
            
        } 
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
        return null;
        
    }

    @Override
    public Categoria findByName(String name) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM categoria WHERE nome=? LIMIT 1 ");
            pst.setString(1, name);
            rs = pst.executeQuery();
            if(rs.next()){
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
                return cat;
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
