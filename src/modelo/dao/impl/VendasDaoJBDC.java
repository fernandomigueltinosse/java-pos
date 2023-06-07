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
import modelo.dao.VendaDao;
import modelo.entities.Categoria;
import modelo.entities.MetodoPagamento;

import modelo.entities.Vendas;

/**
 *
 * @author Tomas
 */
public class VendasDaoJBDC implements VendaDao{

     private final Connection conn;
     
    public VendasDaoJBDC(Connection conn){
        this.conn= conn;
    }
    @Override
    public void insert(Vendas vendas) {
       PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("INSERT INTO vendas(subTotal, Valor_recebido, Trocos, desconto, total, data, metodoPagamentoId, fk_caixa) "
                    + "VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, String.valueOf(vendas.getSubtotal()));
            pst.setString(2, String.valueOf(vendas.getValorRecebido()));
            pst.setString(3, String.valueOf(vendas.getTrocos()));
            pst.setString(4, String.valueOf(vendas.getDesconto()));
            pst.setString(5, String.valueOf(vendas.getTotal()));
            pst.setDate(6, new java.sql.Date(vendas.getData().getTime()));
            pst.setInt(7, vendas.getMetodo().getId());
            pst.setInt(8, vendas.getCaixa().getCaixaId());
            int rowsSffected = pst.executeUpdate();
            if(rowsSffected >0){
                ResultSet rs = pst.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1); 
                    vendas.setId(id);
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
    public List<Vendas> filtrarByDate(String date1,String data2) {
         PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM vendas JOIN metodopagamento ON vendas.metodoPagamentoId=metodopagamento.id WHERE DATE(data) BETWEEN ? and ?");
            pst.setString(1, date1);
            pst.setString(2, data2);
            rs = pst.executeQuery();
            List<Vendas> list = new ArrayList<>();
            //Map<Integer, Categoria> map = new HashMap<>();
            while(rs.next()){
              MetodoPagamento metodo = new MetodoPagamento();
              metodo.setId(rs.getInt("metodoPagamentoId"));
              metodo.setMetodo(rs.getString("nome"));
              
              Vendas vendas = new Vendas();
              vendas.setId(rs.getInt("id"));
              vendas.setSubtotal(rs.getDouble("subTotal"));
              vendas.setTotal(rs.getDouble("total"));
              vendas.setDesconto(rs.getDouble("desconto"));
              vendas.setValorRecebido(rs.getDouble("valor_recebido"));
              vendas.setTrocos(rs.getDouble("trocos"));
              vendas.setData(rs.getDate("data"));
              vendas.setMetodo(metodo);
              
                list.add(vendas);
                
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

    @Override
    public List<Vendas> findAll() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT * FROM vendas JOIN metodopagamento ON vendas.metodoPagamentoId=metodopagamento.id");
            rs = pst.executeQuery();
            List<Vendas> list = new ArrayList<>();
            //Map<Integer, Categoria> map = new HashMap<>();
            while(rs.next()){
              MetodoPagamento metodo = new MetodoPagamento();
              metodo.setId(rs.getInt("metodoPagamentoId"));
              metodo.setMetodo(rs.getString("nome"));
              
              Vendas vendas = new Vendas();
              vendas.setId(rs.getInt("id"));
              vendas.setSubtotal(rs.getDouble("subTotal"));
              vendas.setTotal(rs.getDouble("total"));
              vendas.setDesconto(rs.getDouble("desconto"));
              vendas.setValorRecebido(rs.getDouble("valor_recebido"));
              vendas.setTrocos(rs.getDouble("trocos"));
              vendas.setData(rs.getDate("data"));
              vendas.setMetodo(metodo);
              
                list.add(vendas);
                
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
    
}
