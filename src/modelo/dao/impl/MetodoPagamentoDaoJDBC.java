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
import java.util.List;
import javax.swing.JOptionPane;
import modelo.dao.MetodoPagamentoDao;

import modelo.entities.MetodoPagamento;

/**
 *
 * @author pc2
 */
public class MetodoPagamentoDaoJDBC implements MetodoPagamentoDao{
    
     private final Connection conn;
     
    public MetodoPagamentoDaoJDBC(Connection conn){
        this.conn= conn;
    }


    @Override
    public void insert(MetodoPagamento obj) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("INSERT INTO metodopagamento (nome) VALUES (?)");
            pst.setString(1, obj.getMetodo());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"cadastrado com sucesso");
        } catch (SQLException e) {
             throw new DbException(e.getMessage());
        }
          finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public void update(MetodoPagamento obj) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("UPDATE metodoPagamento SET nome=? WHERE id=?");
            pst.setString(1, obj.getMetodo());
            pst.setInt(2, obj.getId());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Actualizado com sucesso");
        } catch (SQLException e) {
             throw new DbException(e.getMessage());
        }
          finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("delete from metodoPagamento where id= ?");
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Apagado com sucesso");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public MetodoPagamento findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM metodoPagamento WHERE id=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                MetodoPagamento metodo = new MetodoPagamento();
                metodo.setId(rs.getInt("id"));
                metodo.setMetodo(rs.getString("nome"));
                return metodo;
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

    @Override
    public List<MetodoPagamento> findAll() {
         PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM metodoPagamento");
            rs = pst.executeQuery();
            List <MetodoPagamento> list = new  ArrayList<>();
            while(rs.next()){
                MetodoPagamento metodo = new MetodoPagamento();
                metodo.setId(rs.getInt("id"));
                metodo.setMetodo(rs.getString("nome"));
                list.add(metodo);
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
    public List<MetodoPagamento> filtrar(String nome) {
     PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM metodoPagamento where nome like ?");
            pst.setString(1, nome + "%");
            rs = pst.executeQuery();
            List <MetodoPagamento> list = new  ArrayList<>();
            while(rs.next()){
                MetodoPagamento metodo = new MetodoPagamento();
                metodo.setId(rs.getInt("id"));
                metodo.setMetodo(rs.getString("nome"));
               
                list.add(metodo);
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
    }
    

