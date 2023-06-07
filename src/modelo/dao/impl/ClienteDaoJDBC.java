/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao.impl;

import db.DB;
import db.DbException;
import java.util.List;
import modelo.dao.ClienteDao;
import modelo.entities.Cliente;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Tomas
 */
public class ClienteDaoJDBC implements ClienteDao{
    
    private final Connection conn;
    public ClienteDaoJDBC(Connection conn){
        this.conn = conn;
    }
    @Override
    public void insert(Cliente obj) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("INSERT INTO clientes(nome,endereco,telefone) "
                    + "VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, obj.getNome());
            pst.setString(2, obj.getEndereco());
             pst.setInt(3, obj.getTelefone());
            int rowsSffected = pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");
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

    @Override
    public void update(Cliente obj) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("UPDATE clientes SET nome=?,endereco=?,telefone=? WHERE id=?");
            pst.setString(1, obj.getNome());
            pst.setString(2, obj.getEndereco());
            pst.setInt(3, obj.getTelefone());
            pst.setInt(4, obj.getId());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Actualizado com sucesso");
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
            pst = conn.prepareStatement("delete from clientes where id= ?");
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deletado com sucesso");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(pst);
        }
    }
/////////////////////////////////////////////////////////////////////////////////
    @Override
    public Cliente findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM clientes WHERE id=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getInt("telefone"));
                return cliente;
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
/////////////////////////////////////////////////////////////+//////////////////
    @Override
    public List<Cliente> findAll() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM clientes");
            rs = pst.executeQuery();
            List <Cliente> list = new  ArrayList<>();
            //Map<Integer, Categoria> map = new HashMap<>();
            while(rs.next()){
               Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getInt("telefone"));
                list.add(cliente);
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
    public List<Cliente> filtrar(String nome) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM clientes where nome like ?");
            pst.setString(1, nome + "%");
            rs = pst.executeQuery();
            List <Cliente> list = new  ArrayList<>();
            while(rs.next()){
               Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getInt("telefone"));
                list.add(cliente);
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
  
