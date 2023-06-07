/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.dao.DaoFactory;
import modelo.dao.VendaDao;
import modelo.dao.itemsVendasDao;
import modelo.entities.ItemsVenda;
import modelo.entities.Vendas;

/**
 *
 * @author pc2
 */
public class frmListaVendas extends javax.swing.JFrame {

    VendaDao vendaDao = DaoFactory.createVendaDao();
    itemsVendasDao itemsVendaDao = DaoFactory.createItemsVendaDao();
    //ItemsVenda itemVenda= new ItemsVenda();
    
    public frmListaVendas() {
        initComponents();
        findallVendas();
    }
    SimpleDateFormat data =new SimpleDateFormat("yyyy-MM-dd");
    
    
    private void findaByDate(){
        DefaultTableModel modelo = (DefaultTableModel) tblVendas.getModel();
          modelo.setNumRows(0);
          tblVendas.getColumnModel().getColumn(0);
         String data1 = data.format(jData1.getDate());
         if(jData2.getDate()!=null){
              String data2 =  data.format(jData2.getDate());
        List<Vendas> list = vendaDao.filtrarByDate(data1,data2);
        for (Vendas venda : list){
             modelo.addRow(new Object[]{
               venda.getId(),
               venda.getSubtotal(),
               venda.getValorRecebido(),
               venda.getTrocos(),
               venda.getData(),
               venda.getMetodo().getMetodo(),
            });
        }
         }else{
             String data2 =  data.format(jData1.getDate());
              List<Vendas> list = vendaDao.filtrarByDate(data1,data2);
        for (Vendas venda : list){
             modelo.addRow(new Object[]{
               venda.getId(),
               venda.getSubtotal(),
               venda.getValorRecebido(),
               venda.getTrocos(),
               venda.getData(),
               venda.getMetodo().getMetodo(),
            });
        }
         }
        
    }
    //////////////////////////////////////////////////////////////////////////////
    private void findallVendas(){
        DefaultTableModel modelo = (DefaultTableModel) tblVendas.getModel();
          modelo.setNumRows(0);
          tblVendas.getColumnModel().getColumn(0);
          
        List<Vendas> list = vendaDao.findAll();
        for (Vendas venda : list){
             modelo.addRow(new Object[]{
               venda.getId(),
               venda.getSubtotal(),
               venda.getValorRecebido(),
               venda.getTrocos(),
               venda.getData(),
               venda.getMetodo().getMetodo(),
            });
        }
    }
    
////////////////////////////////////////////////////////////////////////////////
    private void detalhesVenhas(){
        DefaultTableModel  modelo = (DefaultTableModel) tblDetalhes.getModel();
        modelo.setNumRows(0);
      int linha = tblVendas.getSelectedRow();
      int id = Integer.parseInt(tblVendas.getModel().getValueAt(linha, 0).toString());
        List<ItemsVenda> list = itemsVendaDao.findById(id);
        for(ItemsVenda itVenda : list){
            
             modelo.addRow(new Object[]{
             itVenda.getProduto().getP_nome(),
             itVenda.getPreco(),
             itVenda.getQuantidade(),
             itVenda.getLucros()
        });
        }
       totalVendas(); 
         
    }
    
    private void totalVendas(){
        int linha = tblVendas.getSelectedRow();
      int id = Integer.parseInt(tblVendas.getModel().getValueAt(linha, 0).toString());
      ItemsVenda vendas = itemsVendaDao.findTotal(id);
      txtTotalLucro.setText(String.valueOf(vendas.getTotalLucros()));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVendas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetalhes = new javax.swing.JTable();
        jData1 = new com.toedter.calendar.JDateChooser();
        jData2 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtTotalLucro = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lista de vendas");

        tblVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Valor total", "Valor recebido", "Trocos", "Data", "Metodo de pagamento"
            }
        ));
        tblVendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVendasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVendas);

        jLabel1.setText("Selecione a data");

        jLabel2.setText("Data início");

        jLabel3.setText("Data fim");

        jButton1.setText("Filtrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Detalhes da venda");

        tblDetalhes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Descrição do produto", "Preço unitário", "Quantidade", "Lucros"
            }
        ));
        jScrollPane2.setViewportView(tblDetalhes);
        if (tblDetalhes.getColumnModel().getColumnCount() > 0) {
            tblDetalhes.getColumnModel().getColumn(0).setMinWidth(500);
            tblDetalhes.getColumnModel().getColumn(0).setMaxWidth(500);
        }

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("TOTAL DE LUCRO");

        jLabel6.setText("Vendas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotalLucro, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel1)
                        .addGap(73, 73, 73)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jData1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jData2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 962, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jButton1)
                    .addComponent(jLabel1)
                    .addComponent(jData1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jData2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTotalLucro, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        findaByDate();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblVendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVendasMouseClicked
        detalhesVenhas();
    }//GEN-LAST:event_tblVendasMouseClicked

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
            java.util.logging.Logger.getLogger(frmListaVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmListaVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmListaVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmListaVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmListaVendas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jData1;
    private com.toedter.calendar.JDateChooser jData2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDetalhes;
    private javax.swing.JTable tblVendas;
    private javax.swing.JTextField txtTotalLucro;
    // End of variables declaration//GEN-END:variables
}
