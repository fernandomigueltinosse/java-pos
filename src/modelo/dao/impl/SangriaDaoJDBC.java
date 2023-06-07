/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.dao.SangriaDao;
import modelo.entities.Sangria;

/**
 *
 * @author pc2
 */
public class SangriaDaoJDBC  implements SangriaDao{
     private final Connection conn;
    public SangriaDaoJDBC(Connection conn){
        this.conn= conn;
    }

    @Override
    public void insert(Sangria obj) {
       PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("INSERT INTO fuxo_caixa (sangria, observacao, f_data, fk_id_caixa) VALUES (?,?,?,?)");
            pst.setDouble(1, obj.getValor());
            pst.setString(2, obj.getObservacao());
            pst.setDate(3, new java.sql.Date(obj.getData().getTime()));
            pst.setInt(4, obj.getCaixa().getCaixaId());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Operacao realizada com sucesso");
        } catch (SQLException e) {
             throw new DbException(e.getMessage());
        }
          finally {
            DB.closeStatement(pst);
        }
    }
    
    
    
}
