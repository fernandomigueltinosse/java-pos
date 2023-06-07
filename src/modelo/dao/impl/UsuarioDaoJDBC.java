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
import modelo.dao.UsuarioDao;


import modelo.entities.Usuario;



/**
 *
 * @author pc2
 */
public class UsuarioDaoJDBC implements UsuarioDao{
    
    private final Connection conn;
    public UsuarioDaoJDBC(Connection conn){
        this.conn= conn;
    }

    @Override
    public void insert(Usuario obj) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("INSERT INTO usuarios(nome,cargo,senha) "
                    + "VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, obj.getNome());
            pst.setString(2, obj.getCargo());
            pst.setString(3, obj.getSenha());
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
    public void update(Usuario obj) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("UPDATE usuarios SET nome=?,cargo=?,senha=? WHERE id=?");
            pst.setString(1, obj.getNome());
            pst.setString(2, obj.getCargo());
            pst.setString(3, obj.getSenha());
            pst.setInt(4, obj.getId());
            pst.executeUpdate();

        } catch (SQLException e) {
             throw new DbException(e.getMessage());
        }
          finally {
            DB.closeStatement(pst);
        }
    }
/////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void deleteById(Integer id) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("delete from usuarios where id= ?");
            pst.setInt(1, id);
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
    public Usuario findById(Integer id) {
       PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM usuarios WHERE id=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCargo(rs.getString("cargo"));
                usuario.setSenha(rs.getString("senha"));
                return usuario;
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
////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<Usuario> findAll() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM usuarios");
            rs = pst.executeQuery();
            List <Usuario> list = new  ArrayList<>();
            //Map<Integer, Categoria> map = new HashMap<>();
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCargo(rs.getString("cargo"));
                usuario.setSenha(rs.getString("senha"));
                list.add(usuario);
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
    public List<Usuario> filtrar(String nome) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM usuarios where nome like ?");
            pst.setString(1, nome + "%");
            rs = pst.executeQuery();
            List <Usuario> list = new  ArrayList<>();
            //Map<Integer, Categoria> map = new HashMap<>();
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCargo(rs.getString("cargo"));
                usuario.setSenha(rs.getString("senha"));
                list.add(usuario);
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
    public Usuario login(Usuario modelUsuario) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("select * from usuarios where nome=? and senha=?");
            pst.setString(1, modelUsuario.getNome());
            pst.setString(2, modelUsuario.getSenha());
            rs = pst.executeQuery();
            if(rs.next()){
                Usuario usuarios = new Usuario();
                usuarios.setId(rs.getInt("id"));
                usuarios.setNome(rs.getString("nome"));
                usuarios.setCargo(rs.getString("cargo"));
                usuarios.setSenha(rs.getString("senha"));
                return usuarios;
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

    
    
    

