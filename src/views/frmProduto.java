/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.CategoriaDao;
import modelo.dao.DaoFactory;
import modelo.dao.ProdutoDao;
import modelo.entities.Categoria;
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
 * @author pc2
 */
public class frmProduto extends javax.swing.JFrame {

    
    ProdutoDao produtoDao =  DaoFactory.createProdutoDao();
     CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
     
    public frmProduto() {
        initComponents();
        findAll();
        Activar_botao(false, false, false,false);
        mostrarCategorias();
    }
    
/////////////////////////////////activar botao//////////////////////////////////////
    private void Activar_botao(boolean salvar,boolean actualizar, boolean apagar,boolean quantidade){
        btnCadastrar.setEnabled(salvar);
        btnActualizar.setEnabled(actualizar);
        btnApagar.setEnabled(apagar);
       
    }
////////////////////////////cancelar////////////////////////////////////////////
    private void cancelar(){
        txtBarcode.setText("");
        txtCompra.setText("");
        txtDescricao.setText("");
        txtId.setText("");
        txtQuantidade.setText("");
        txtVenda.setText("");
    }
///////////////////////////findAll//////////////////////////////////////////////
    private void findAll(){
        DefaultTableModel modelo = (DefaultTableModel) tblProduto.getModel();
          modelo.setNumRows(0);
          tblProduto.getColumnModel().getColumn(0);
          
        List<Produto> list = produtoDao.findAll();
        for (Produto produto : list){
             modelo.addRow(new Object[]{
               produto.getId(),
               produto.getP_nome(),
               produto.getP_compra(),
               produto.getP_venda(),
               produto.getQuantidade(),
               produto.getCategoria(),
            });
        }
    }
///////////////////////////findById/////////////////////////////////////////////
private void findById(){
    int linha = tblProduto.getSelectedRow();
    int id = Integer.parseInt(tblProduto.getModel().getValueAt(linha, 0).toString());
   
    Produto produto = produtoDao.findById(id);
    txtId.setText(String.valueOf(produto.getId()));
    txtDescricao.setText(produto.getP_nome());
    txtCompra.setText(String.valueOf(produto.getP_compra()));
    txtVenda.setText(String.valueOf(produto.getP_venda()));
    txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
    txtBarcode.setText(String.valueOf(produto.getBarcode()));
    
    DefaultComboBoxModel Model = new DefaultComboBoxModel();
     List<Categoria> list = categoriaDao.findAll();
         comboBox.removeAllItems();
        
        // Adicionar as categorias ao ComboBox
        for (Categoria categoria : list) {
            comboBox.addItem(categoria);
            Model.addElement(categoria.getNome());
            
        } 
        
       comboBox.setModel(Model); 
       comboBox.setSelectedItem(produto.getCategoria().getNome());
}    
///////////////////////////////insert///////////////////////////////////////////
    private void insert(){
        
        if(comboBox.getSelectedItem().toString()!=null){
            Categoria categoria = categoriaDao.findByName(comboBox.getSelectedItem().toString());
             Produto produto = new Produto(null,Integer.valueOf(txtBarcode.getText()),txtDescricao.getText() , Double.valueOf(txtCompra.getText()), Double.valueOf(txtVenda.getText()),Integer.valueOf(txtQuantidade.getText()), categoria);
        produtoDao.insert(produto);
        findAll();
        Activar_botao(false, false, false,false);
        cancelar();
        }else{
            JOptionPane.showMessageDialog(null, "A sua lisat de categoria esta vazia");
        }
    }
///////////////////////////////////update///////////////////////////////////////
        private void update(){
        if(comboBox.getSelectedItem().toString()!=null){
            Categoria categoria = categoriaDao.findByName(comboBox.getSelectedItem().toString());
        Produto produto = new Produto(Integer.valueOf(txtId.getText()),Integer.valueOf(txtBarcode.getText()),txtDescricao.getText() , Double.valueOf(txtCompra.getText()), Double.valueOf(txtVenda.getText()),Integer.valueOf(txtQuantidade.getText()), categoria);
       produtoDao.update(produto);
        }    
        
    }
//////////////////////////////////Delete////////////////////////////////////////
        private void deletar(){
            produtoDao.deleteById(Integer.valueOf(txtId.getText()));
        }

  
////////////////////////////////////////////////////////////////////////////////
       private void filtrarByname(){
    DefaultTableModel modelo = (DefaultTableModel) tblProduto.getModel();
          modelo.setNumRows(0);
          tblProduto.getColumnModel().getColumn(0);
          
        List<Produto> list = produtoDao.filtrar(txtPesquisa.getText());
        for (Produto produto : list){
             modelo.addRow(new Object[]{
               produto.getId(),
               produto.getP_nome(),
               produto.getP_compra(),
               produto.getP_venda(),
               produto.getQuantidade(),
               produto.getCategoria().getNome()
            });
        }
}
       
////////////////////////////////////////////////////////////////////////////////
       private void mostrarCategorias(){
         List<Categoria> list = categoriaDao.findAll();
         comboBox.removeAllItems();
        
        // Adicionar as categorias ao ComboBox
        for (Categoria categoria : list) {
            comboBox.addItem(categoria);
        }
         
       }
      ///////////////////////imprimir///////////////////////////////////////////
       private void imprimir(List<Produto> list) throws JRException{
            JasperDesign path = JRXmlLoader.load("src/relatorios/teste.jrxml");
            JasperReport report = JasperCompileManager.compileReport(path);
            JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(list));
            JasperViewer.viewReport(print,false);
       }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtBarcode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCompra = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtVenda = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduto = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        comboBox = new javax.swing.JComboBox<>();
        btnNovo = new javax.swing.JButton();
        btnCadastrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnApagar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de produtos");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastrar produtos"));

        jLabel1.setText("Id");

        txtId.setEnabled(false);

        jLabel2.setText("Codigo de barra");

        jLabel3.setText("Descrição");

        jLabel4.setText("Preço de compra");

        jLabel5.setText("Preço de venda");

        jLabel6.setText("Quantidade ");

        jLabel7.setText("Categoria");

        tblProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Descrição", "Preço de compra", "Preço de venda", "Qtd em stock", "Categoria"
            }
        ));
        tblProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProduto);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24x24/icons8-search-24.png"))); // NOI18N
        jLabel8.setText("Pesquisar produto");

        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        comboBox.setModel(comboBox.getModel());
        comboBox.setSelectedIndex(comboBox.getSelectedIndex());
        comboBox.setSelectedItem(comboBox);
        comboBox.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(70, 70, 70)
                                        .addComponent(jLabel7)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 347, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(txtPesquisa))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(37, 37, 37)
                                .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(jLabel5)
                                .addGap(41, 41, 41)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtQuantidade, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                                    .addComponent(txtVenda))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24x24/icons8-new-copy-24.png"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24x24/icons8-add-24.png"))); // NOI18N
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24x24/icons8-pencil-24.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnApagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24x24/icons8-delete-24.png"))); // NOI18N
        btnApagar.setText("Apagar");
        btnApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApagarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24x24/icons8-cancel-24.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(btnApagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(btnNovo)
                        .addGap(36, 36, 36)
                        .addComponent(btnCadastrar)
                        .addGap(31, 31, 31)
                        .addComponent(btnActualizar)
                        .addGap(38, 38, 38)
                        .addComponent(btnApagar)
                        .addGap(45, 45, 45)
                        .addComponent(btnCancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        insert();
       
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void tblProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutoMouseClicked
        findById();
        Activar_botao(false, true, true,true);
    }//GEN-LAST:event_tblProdutoMouseClicked

    private void btnApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagarActionPerformed
        deletar();
        findAll();
        Activar_botao(false, false, false,false);
        cancelar();
    }//GEN-LAST:event_btnApagarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        update();
        findAll();
        Activar_botao(false, false, false,false);
        cancelar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        Activar_botao(true, false, false,false);
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelar();
         Activar_botao(false, false, false,false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
        
    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        filtrarByname();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    
    
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
            java.util.logging.Logger.getLogger(frmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmProduto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnApagar;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnNovo;
    public javax.swing.JComboBox<Object> comboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProduto;
    private javax.swing.JTextField txtBarcode;
    private javax.swing.JTextField txtCompra;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtVenda;
    // End of variables declaration//GEN-END:variables
}
