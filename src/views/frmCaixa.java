/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import modelo.entities.SomatorioVenda;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.CaixaDao;
import modelo.dao.DaoFactory;
import modelo.dao.EmpresaDao;
import modelo.dao.itemsVendasDao;
import modelo.entities.Caixa;
import modelo.entities.Empresa;
import modelo.entities.ItemsVenda;
import modelo.entities.Produto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Tomas
 */
public class frmCaixa extends javax.swing.JFrame {

    EmpresaDao empresaDao = DaoFactory.createEmpresa();
    CaixaDao caixaDao = DaoFactory.createCaixa();
    itemsVendasDao itemsVendaDao = DaoFactory.createItemsVendaDao();
    SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");

    public frmCaixa() {
        initComponents();
        findAll();
    }

    private void findAll() {
        DefaultTableModel modelo = (DefaultTableModel) tblCaixa.getModel();
        modelo.setNumRows(0);
        List<Caixa> list = caixaDao.findAll();
        for (Caixa caixa : list) {
            modelo.addRow(new Object[]{
                caixa.getCaixaId(),
                caixa.getDataAbertura(),
                caixa.getDataFechamento(),
                caixa.getSaldoInicial(),
                caixa.getSaldoAtual(),
                caixa.getUsuario().getNome()
            });
        }
    }

    private void filterByDate() {
        Caixa caixa = new Caixa();
        DefaultTableModel modelo = (DefaultTableModel) tblCaixa.getModel();
        modelo.setNumRows(0);
        caixa.setDataAbertura(dataInicio.getDate());
        if (dataFim.getDate() != null) {
            caixa.setDataFechamento(dataFim.getDate());
            List<Caixa> list = caixaDao.findByDate(caixa);
            for (Caixa caixas : list) {
                modelo.addRow(new Object[]{
                    caixas.getCaixaId(),
                    caixas.getDataAbertura(),
                    caixas.getDataFechamento(),
                    caixas.getSaldoInicial(),
                    caixas.getSaldoAtual(),
                    caixas.getUsuario().getNome()

                });
            }
        } else {
            caixa.setDataFechamento(dataInicio.getDate());
            List<Caixa> list = caixaDao.findByDate(caixa);
            for (Caixa caixas : list) {
                modelo.addRow(new Object[]{
                    caixas.getCaixaId(),
                    caixas.getDataAbertura(),
                    caixas.getDataFechamento(),
                    caixas.getSaldoInicial(),
                    caixas.getSaldoAtual(),
                    caixas.getUsuario().getNome()

                });
            }
        }

    }

    private void caixaVendas() {
        DefaultTableModel modelo = (DefaultTableModel) tblPagamento.getModel();
        modelo.setNumRows(0);
        int linha = tblCaixa.getSelectedRow();
        int id = (int) tblCaixa.getValueAt(linha, 0);

        List<SomatorioVenda> list = caixaDao.findAllByMethod(id);
        for (SomatorioVenda soma : list) {
            modelo.addRow(new Object[]{
                soma.getDescricao(),
                soma.getTotal()
            });
        }
        //listaVenda.setVisible(true);

    }

    //////////////////////total//////////////////////////////////////////////////
    private void totalucros() {
        int linha = tblCaixa.getSelectedRow();
        int id = Integer.parseInt(tblCaixa.getModel().getValueAt(linha, 0).toString());
        ItemsVenda vendas = caixaDao.findTotal(id);
        txtLucros.setText(String.valueOf(vendas.getTotalLucros()));
    }

    private void totalVendas() {
        DefaultTableModel modelo = (DefaultTableModel) tblCaixa.getModel();
        int linha = tblCaixa.getSelectedRow();
        Double a = (Double) modelo.getValueAt(linha, 3);
        Double b = (Double) modelo.getValueAt(linha, 4);
        Double c = b - a;
        txtTotalVendas.setText(String.valueOf(c));
    }

    private void imprimir() throws JRException {

        Empresa empresa;
        empresa = empresaDao.findAll();

        Caixa caixa = new Caixa();
        caixa.setDataAbertura(dataInicio.getDate());
        caixa.setDataFechamento(dataFim.getDate());
        caixa = caixaDao.findByDate2(caixa);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nomeEmpresa", empresa.getE_nome());
        parametros.put("nuit", empresa.getE_nuit());
        parametros.put("telefone", empresa.getE_telefone());
        
        ItemsVenda vendas = caixaDao.findTotal(caixa.getCaixaId());
       

        parametros.put("abertura", caixa.getDataAbertura());
        parametros.put("fechamento", caixa.getDataFechamento());
        parametros.put("valorInicial", caixa.getSaldoInicial());
        parametros.put("valorFinal", caixa.getSaldoAtual());
        parametros.put("usuario", caixa.getUsuario().getNome());
        parametros.put("id", caixa.getCaixaId());
        parametros.put("lucros", vendas.getTotalLucros());
        List<SomatorioVenda> list = caixaDao.findAllByMethod(caixa.getCaixaId());

        JasperDesign path = JRXmlLoader.load("src/relatorios/relatorio_do_caixa.jrxml");
        JasperReport report = JasperCompileManager.compileReport(path);
        JasperPrint print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(list));
        JasperViewer.viewReport(print, false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dataInicio = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        dataFim = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCaixa = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPagamento = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtLucros = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTotalVendas = new javax.swing.JTextField();
        btnImprimir = new javax.swing.JButton();

        jButton4.setText("jButton4");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Acerto do caixa");

        jLabel1.setText("Selecione os periodos");

        jLabel2.setText("De");

        jLabel4.setText("Ate");

        jButton1.setText("Pesquisar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Abertura e fecho do caixa"));

        tblCaixa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Abertura", "Fechamento", "Valor inicial", "Valor final", "Usuario"
            }
        ));
        tblCaixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCaixaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCaixa);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Formas de pagamento"));

        tblPagamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Formas de Pagamento", "Total de vendas"
            }
        ));
        jScrollPane2.setViewportView(tblPagamento);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24x24/icons8-detail-24.png"))); // NOI18N
        jButton3.setText("Ver detalhes de Venda");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel5.setText("Total de Lucros");

        jLabel6.setText("Total de vendas");

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24x24/icons8-printer-24.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(dataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(dataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtLucros, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(btnImprimir))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(dataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(dataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtLucros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtTotalVendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(357, 357, 357)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        filterByDate();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblCaixaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCaixaMouseClicked
        caixaVendas();
        totalucros();
        totalVendas();
    }//GEN-LAST:event_tblCaixaMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        frmListaVendas lista = new frmListaVendas();
        lista.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        try {
            imprimir();
        } catch (JRException ex) {
            Logger.getLogger(frmCaixa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCaixa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;
    private com.toedter.calendar.JDateChooser dataFim;
    private com.toedter.calendar.JDateChooser dataInicio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblCaixa;
    private javax.swing.JTable tblPagamento;
    private javax.swing.JTextField txtLucros;
    private javax.swing.JTextField txtTotalVendas;
    // End of variables declaration//GEN-END:variables
}
