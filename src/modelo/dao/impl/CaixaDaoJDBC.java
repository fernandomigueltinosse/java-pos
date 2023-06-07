/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao.impl;

import db.DB;
import db.DbException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.dao.CaixaDao;
import modelo.entities.Caixa;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import modelo.entities.ItemsVenda;
import modelo.entities.UserSession;
import modelo.entities.Usuario;
import modelo.entities.SomatorioVenda;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Tomas
 */
public class CaixaDaoJDBC implements CaixaDao {

    private final Connection conn;

    public CaixaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void abrirCaixa(Caixa obj) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("INSERT INTO caixa (saldoInicial,dataAbertura,fk_usuario) VALUES (?,?,?)");
            pst.setDouble(1, obj.getSaldoInicial());
            pst.setDate(2, new java.sql.Date(obj.getDataAbertura().getTime()));
            pst.setString(3, UserSession.nome);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "caixa Aberto com sucesso");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public void fecharCaixa(Caixa obj) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("UPDATE caixa SET datafecho=?,saldoFinal=? where caixaId=?");
            pst.setDate(1, new java.sql.Date(obj.getDataAbertura().getTime()));
            pst.setDouble(2, obj.getSaldoAtual());
            pst.setInt(3, obj.getCaixaId());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "caixa fechado com sucesso");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public Caixa findId() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT caixaId from caixa WHERE dataFecho IS null ORDER BY dataAbertura DESC LIMIT 1");
            rs = pst.executeQuery();
            if (rs.next()) {
                Caixa caixa = new Caixa();
                caixa.setCaixaId(rs.getInt("caixaId"));
   
                return caixa;
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
    public List<Caixa> findAll() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM caixa");
            rs = pst.executeQuery();
            List<Caixa> list = new ArrayList<>();

            while (rs.next()) {
                Caixa caixa = new Caixa();
                caixa.setCaixaId(rs.getInt("caixaId"));
                caixa.setDataAbertura(rs.getDate("dataAbertura"));
                caixa.setDataFechamento(rs.getDate("dataFecho"));
                caixa.setSaldoInicial(rs.getDouble("saldoInicial"));
                caixa.setSaldoAtual(rs.getDouble("saldoFinal"));
                Usuario usuario = new Usuario();
                usuario.setNome(rs.getString("fk_usuario"));
                caixa.setUsuario(usuario);
                list.add(caixa);
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
    public List<Caixa> findByDate(Caixa obj) {
        SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM caixa WHERE DATE(dataAbertura) BETWEEN ? and ? ");
            pst.setString(1, data.format(obj.getDataAbertura()));
            pst.setString(2, data.format(obj.getDataFechamento()));
            rs = pst.executeQuery();
            List<Caixa> list = new ArrayList<>();

            while (rs.next()) {
                Caixa caixa = new Caixa();
                caixa.setCaixaId(rs.getInt("caixaId"));
                caixa.setDataAbertura(rs.getDate("dataAbertura"));
                caixa.setDataFechamento(rs.getDate("dataFecho"));
                caixa.setSaldoInicial(rs.getDouble("saldoInicial"));
                caixa.setSaldoAtual(rs.getDouble("saldoFinal"));
                
                Usuario usuario = new Usuario();
                usuario.setNome(rs.getString("fk_usuario"));
                caixa.setUsuario(usuario);
                list.add(caixa);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }

    }
    
    //////////////////////////////////////////////////////////////////////////////
       @Override
    public Caixa findByDate2(Caixa obj) {
        SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM caixa WHERE DATE(dataAbertura) BETWEEN ? and ? ");
            pst.setString(1, data.format(obj.getDataAbertura()));
            pst.setString(2, data.format(obj.getDataFechamento()));
            rs = pst.executeQuery();
            

            if (rs.next()) {
                Caixa caixa = new Caixa();
                caixa.setCaixaId(rs.getInt("caixaId"));
                caixa.setDataAbertura(rs.getDate("dataAbertura"));
                caixa.setDataFechamento(rs.getDate("dataFecho"));
                caixa.setSaldoInicial(rs.getDouble("saldoInicial"));
                caixa.setSaldoAtual(rs.getDouble("saldoFinal"));
                
                Usuario usuario = new Usuario();
                usuario.setNome(rs.getString("fk_usuario"));
                caixa.setUsuario(usuario);
                return caixa;
            }
            
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
        return null;

    }
    //////////////////////////////////////////////////////////////////////////////

    @Override
    public List<SomatorioVenda> findAllByMethod(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT metodopagamento.nome, \n"
                    + "       CASE \n"
                    + "           WHEN metodopagamento.nome = 'Dinheiro' THEN SUM(vendas.total) + saldoInicial\n"
                    + "           ELSE SUM(vendas.total)\n"
                    + "       END AS total\n"
                    + "FROM caixa JOIN vendas ON vendas.fk_caixa=caixa.caixaId JOIN metodopagamento on vendas.metodoPagamentoId=metodopagamento.id WHERE caixa.caixaId=? GROUP BY metodopagamento.nome;");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            List<SomatorioVenda> list = new ArrayList<>();

            while (rs.next()) {
                SomatorioVenda somatorio = new SomatorioVenda();
                somatorio.setDescricao(rs.getString("nome"));
                somatorio.setTotal(rs.getDouble("total"));

                list.add(somatorio);
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
    public ItemsVenda findTotal(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT  SUM((venda_produto.preco_venda - produtos.p_compra) * venda_produto.quantidade) AS lucro_total FROM venda_produto JOIN produtos ON venda_produto.produtos_id=produtos.id JOIN vendas on venda_produto.vendas_id=vendas.id JOIN caixa on vendas.fk_caixa =caixa.caixaId WHERE caixa.caixaId=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                ItemsVenda vendas = new ItemsVenda();
                vendas.setTotalLucros(rs.getDouble("lucro_total"));
                return vendas;
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
    public Caixa findtest(Caixa obj) {
        SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM caixa WHERE DATE(dataAbertura) BETWEEN ? and ? ");
            pst.setString(1, data.format(obj.getDataAbertura()));
            pst.setString(2, data.format(obj.getDataFechamento()));
            rs = pst.executeQuery();
            if (rs.next()) {
                Caixa caixa = new Caixa();
                caixa.setCaixaId(rs.getInt("caixaId"));
                return caixa;
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
    public Caixa findById(Integer id) {
         PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM caixa where caixaId=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
  
            if (rs.next()) {
                Caixa caixa = new Caixa();
                caixa.setCaixaId(rs.getInt("caixaId"));
                caixa.setDataAbertura(rs.getDate("dataAbertura"));
                caixa.setDataFechamento(rs.getDate("dataFecho"));
                caixa.setSaldoInicial(rs.getDouble("saldoInicial"));
                caixa.setSaldoAtual(rs.getDouble("saldoFinal"));
                Usuario usuario = new Usuario();
                usuario.setNome(rs.getString("fk_usuario"));
                caixa.setUsuario(usuario);
                JOptionPane.showMessageDialog(null, caixa);
                return caixa;
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
    public void CaixaRelatorio(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   

}
