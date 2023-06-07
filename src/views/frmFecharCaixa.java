/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import gnu.io.CommPortIdentifier;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;
import modelo.dao.CaixaDao;
import modelo.dao.DaoFactory;
import modelo.dao.EmpresaDao;
import modelo.entities.Caixa;
import modelo.entities.Empresa;
import modelo.entities.ItemsVenda;
import modelo.entities.SomatorioVenda;
import modelo.entities.Vendas;

/**
 *
 * @author Tomas
 */
public class frmFecharCaixa extends javax.swing.JFrame {
    EmpresaDao empresaDao = DaoFactory.createEmpresa();
    CaixaDao caixaDao = DaoFactory.createCaixa();
    Empresa empresa = new Empresa();
   
    public frmFecharCaixa() {
        initComponents();
        listAvailablePorts();
    }

   private void fecharCaixa(){
       Caixa caixa;
       List<SomatorioVenda> list = new ArrayList<>();
       caixa = caixaDao.findId();
       
       caixa.setSaldoAtual(Double.parseDouble(txtValorActual.getText()));
       caixa.setDataAbertura(new Date());
       caixaDao.fecharCaixa(caixa);
      caixa = caixaDao.findById(caixa.getCaixaId());
       JOptionPane.showMessageDialog(null,caixa.getCaixaId());
       imprimirRelatorio(caixa);
   }
   ////////////////////////////////////////////////////////////////////////////////
   private void imprimirRelatorio(Caixa caixa){
    Date data = new  Date();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    empresa = empresaDao.findAll();
    String dataActual = format.format(data);
    
    List<SomatorioVenda> list = caixaDao.findAllByMethod(caixa.getCaixaId());
    String conteudo = "";
    for (int i = 0; i < list.size(); i++) {
        conteudo += 
                list.get(i).getDescricao()+"           "+
                list.get(i).getTotal()+"\n\r";
    }
    this.imprimir(""
            + "Nome     :"+empresa.getE_nome()+"\n\r"
            + "NUIT     :"+empresa.getE_nuit()+"\n\r"
            + "Telefone :"+empresa.getE_telefone()+"\n\r"
            + "Endereco :"+empresa.getE_bairro()+"\n\r"
            + "Cidade   :"+empresa.getE_cidade()+"\n\r"
            + "-----------------------------\n\r"
            + "      relatorio de vendas         \n\r"
            + "-----------------------------\n\r"
            + "Metodo de pagamento   Total     \n\r"
            + conteudo + ""
            + "-----------------------------\n\r"
            + "Data abertura  : " + format.format(caixa.getDataAbertura()) +"\n\r"
            + "Data fechamento: " + format.format(caixa.getDataFechamento()) +"\n\r"
            + "Saldo inicial  : " + caixa.getSaldoInicial()+"\n\r"
            + "Saldo final    : " + caixa.getSaldoAtual()+"\n\r"
            + "Operador       : " + caixa.getUsuario().getNome()+"\n\r"
            + "--------------------------------\n\r"
            + "Data: "+dataActual +"\n\r"
            +"\f"
            );
 
}
   
///////////////////////////////////////funcao para imprimir/////////////////////
public void imprimir(String text){
    try {
        InputStream prin = new ByteArrayInputStream(text.getBytes());
        DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        SimpleDoc documentoTexto = new SimpleDoc(prin, docFlavor, null);
        PrintService impressora = PrintServiceLookup.lookupDefaultPrintService();
        //pegar a impressora padrao
        PrintRequestAttributeSet printerAttributes = new HashPrintRequestAttributeSet();
        printerAttributes.add(new JobName("impressao",null));
        printerAttributes.add(OrientationRequested.PORTRAIT);
        printerAttributes.add(MediaSizeName.ISO_A4);
        DocPrintJob printJob = impressora.createPrintJob();
        printJob.print(documentoTexto, (PrintRequestAttributeSet) printerAttributes);
    } catch (PrintException e) {
        JOptionPane.showMessageDialog(null, e);
    }
}

////////////////////////////////////////////////////////////////////////////////////////
private void escpos(){
     
     
}
//////////////////////////////////////////////////////////////////////////////////////////

public static void listAvailablePorts() {
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();

        System.out.println("Portas disponíveis:");
        while (portList.hasMoreElements()) {
            CommPortIdentifier portId = portList.nextElement();
            System.out.println(" - " + portId.getName());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtValorActual = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Fechamento do caixa");

        jLabel1.setText("Valor Actual");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24x24/icons8-close-24.png"))); // NOI18N
        jButton1.setText("Fechar Caixa");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtValorActual, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(44, 44, 44))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtValorActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        fecharCaixa();
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(frmFecharCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmFecharCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmFecharCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmFecharCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmFecharCaixa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtValorActual;
    // End of variables declaration//GEN-END:variables
}
