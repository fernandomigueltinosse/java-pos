/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.escpos.image.Bitonal;
import com.github.anastaciocintra.escpos.image.BitonalThreshold;
import com.github.anastaciocintra.output.PrinterOutputStream;
import java.awt.print.PrinterException;

import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.CaixaDao;
import modelo.dao.CategoriaDao;
import modelo.dao.DaoFactory;
import modelo.dao.EmpresaDao;
import modelo.dao.MetodoPagamentoDao;
import modelo.dao.ProdutoDao;
import modelo.dao.VendaDao;
import modelo.dao.itemsVendasDao;
import modelo.entities.Caixa;
import modelo.entities.Categoria;
import modelo.entities.Empresa;
import modelo.entities.ItemsVenda;
import modelo.entities.MetodoPagamento;

import modelo.entities.Produto;
import modelo.entities.UserSession;
import modelo.entities.Vendas;

/**
 *
 * @author pc2
 */
public class frmVendas extends javax.swing.JFrame {

    //////////////////Intancias///////////////////////////////////////////////
    itemsVendasDao itemsVendaDao = DaoFactory.createItemsVendaDao();
    MetodoPagamentoDao metodoDao = DaoFactory.createMetodoPagamento();
    EmpresaDao empresaDao = DaoFactory.createEmpresa();
    Empresa empresa = new Empresa();
    ProdutoDao produtoDao = DaoFactory.createProdutoDao();
    VendaDao vendaDao = DaoFactory.createVendaDao();
    CaixaDao caixaDao = DaoFactory.createCaixa();
    Vendas vendas = new Vendas();
    ItemsVenda items = new ItemsVenda();
    ArrayList<ItemsVenda> ListItemsVendas = new ArrayList<>();
    ArrayList<Produto> listProdutos = new ArrayList<>();
    double total, subtotal, valorRecebido, trocos;
    int quantidade;

    public frmVendas() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        mostrarMetdodoPagamento();
        quantidade = 1;
        txtQuantidade.setText(String.valueOf(quantidade));
        ListaProduto();
        // findAll();

    }

    ///////////////////////setar dados cliecando enter//////////////////////////
    private void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER) {
            JOptionPane.showMessageDialog(null, "keypressed");
        } else {
            JOptionPane.showMessageDialog(null, "key not pressed");
        }
    }

    private void desconto() {

        if (!txtDescontos.getText().isEmpty()) {
            total = Double.parseDouble(txtTotal.getText());
            Double desconto = Double.valueOf(txtDescontos.getText());
            txtTotal.setText(String.valueOf(total - desconto));
        }
    }
///////////////////////////////////////////////////////////

    private void somarValorTotal() {
        double soma = 0, valor;
        int count = tblItems.getRowCount();
        for (int i = 0; i < count; i++) {
            valor = (double) tblItems.getValueAt(i, 5);
            soma = soma + valor;
        }
        txtSubtotal.setText(String.valueOf(soma));
        txtTotal.setText(String.valueOf(soma));
        desconto();
    }

////////////////////////////////////////////////////////////////////////////////
    public void adicionarProdutos() {
        Produto produto;
        if (!txtId.getText().isEmpty()) {
            produto = produtoDao.findById(Integer.valueOf(txtId.getText()));
            if (produto != null) {
                add(produto);
            } else {
                JOptionPane.showMessageDialog(null, "O Produto com esse código não existe");
            }
        } else if (!txtBarcode.getText().isEmpty()) {
            produto = produtoDao.findBbarCode(Integer.valueOf(txtBarcode.getText()));
            if (produto != null) {
                add(produto);
            } else {
                JOptionPane.showMessageDialog(null, "O Produto com esse código não existe");
            }
        } else {
            produto = produtoDao.findByName(txtDescricao.getText());
            if (produto != null) {
                add(produto);
            } else {
                JOptionPane.showMessageDialog(null, "O Produto com esse código não existe");
            }
        }

    }
////////////////////////////////////////////////////////////////////////////////

    private void add(Produto produto) {
        quantidade = Integer.parseInt(txtQuantidade.getText());
        total = quantidade * produto.getP_venda();
        if (produto.getQuantidade() < quantidade) {
            JOptionPane.showMessageDialog(null, "A quantidade nao é suficiente\n\r a quantidade disponivel é de " + produto.getQuantidade() + " unidades");
        } else {
            DefaultTableModel modelo = (DefaultTableModel) tblItems.getModel();
            modelo.addRow(new Object[]{modelo.getRowCount() + 1, produto.getId(), produto.getP_nome(),
                produto.getP_venda(), quantidade, total});

            limpar();
            somarValorTotal();
        }
        quantidade = 1;
        txtQuantidade.setText(String.valueOf(quantidade));
    }

    ////////////////////////////////////////////////////////////////////////
    public void removerProdutos() {
        DefaultTableModel modelo = (DefaultTableModel) tblItems.getModel();
        int linha = Integer.parseInt(JOptionPane.showInputDialog("Digite o numero do item"));
        modelo.removeRow(linha - 1);
        int qtyLinha = modelo.getRowCount();
        for (int i = 0; i < qtyLinha; i++) {
            modelo.setValueAt(i + 1, i, 0);
        }
        somarValorTotal();
        limpar();
    }

////////////////////////////////////////////////////////////////////////////////
    public void novo() {
        DefaultTableModel modelo = (DefaultTableModel) tblItems.getModel();
        modelo.setNumRows(0);
        txtTotal.setText("0.0");
        txtDescricao.setText("");
        txtTrocos.setText("0.0");
        txtValorRecebido.setText("0.0");
        txtQuantidade.setText("1");
    }

////////////////////////////////////////////////////////////////////////////////
    private void trocos() {

        total = Double.parseDouble(txtTotal.getText());
        subtotal = Double.parseDouble(txtTotal.getText());
        valorRecebido = Double.parseDouble(txtValorRecebido.getText());
        trocos = valorRecebido - subtotal;
        txtTrocos.setText(String.valueOf(trocos));
    }
////////////////////////////////////////////////////////////////////////////////

    private void vendas() {
        Produto produto;
        ListItemsVendas = new ArrayList<>();
        Caixa caixa = caixaDao.findId();
        vendas.setCaixa(caixa);
        vendas.setTotal(Double.valueOf(txtTotal.getText()));
        vendas.setSubtotal(Double.valueOf(txtSubtotal.getText()));
        vendas.setDesconto(Double.valueOf(txtDescontos.getText()));
        vendas.setValorRecebido(Double.valueOf(txtValorRecebido.getText()));
        vendas.setTrocos(vendas.getValorRecebido() - vendas.getSubtotal());
        vendas.setData(new Date());
        MetodoPagamento metodo = (MetodoPagamento) comboMetodoPagamento.getSelectedItem();
        vendas.setMetodo(metodo);

        vendaDao.insert(vendas);

        for (int i = 0; i < tblItems.getRowCount(); i++) {
            items = new ItemsVenda();
            produto = new Produto();
            produto.setId((Integer) tblItems.getValueAt(i, 1));
            produto = produtoDao.findById(produto.getId());
            items.setProduto(produto);
            items.setPreco((Double) tblItems.getValueAt(i, 3));
            items.setQuantidade((Integer) tblItems.getValueAt(i, 4));
            items.setVenda(vendas);
            produto.RemoverQuantity(items.getQuantidade());
            ////////////////produto////////////////////////////////////
            ListItemsVendas.add(items);
            listProdutos.add(produto);
        }

        itemsVendaDao.insert(ListItemsVendas);
        produtoDao.updateStock(listProdutos);
        JOptionPane.showMessageDialog(null, vendas);
        StringBuilder(ListItemsVendas, vendas);
        txtSubtotal.setText("0.0");
        txtTotal.setText("0.0");
        txtDescricao.setText("");
        txtTrocos.setText("0.0");
        txtValorRecebido.setText("0.0");
        novo();
        ListaProduto();
    }
////////////////////////////////////////////////////////////////////////////////

    // String conteudoRecibo = StringBuilder(ListItemsVendas, vendas);
    public void StringBuilder(ArrayList<ItemsVenda> listItemsVenda, Vendas vendas) {
        empresa = empresaDao.findAll();

        Date data = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dataActual = format.format(data);
        StringBuilder builder = new StringBuilder();
        builder.append("Nome:    \t").append(empresa.getE_nome()).append("\n\r");
        builder.append("NUIT:    \t").append(empresa.getE_nuit()).append("\n\r");
        builder.append("Telefone:\t").append(empresa.getE_telefone()).append("\n\r");
        builder.append("Endereco:\t").append(empresa.getE_bairro()).append("\n\r");
        builder.append("Cidade:  \t").append(empresa.getE_cidade()).append("\n\r");
        builder.append("--------------------------------\n\r");
        builder.append("Desccricao\t").append("qtd\t").append("preco").append("\n\r");
        builder.append("--------------------------------\n\r");
        for (ItemsVenda list : listItemsVenda) {
            String descricao = list.getProduto().getP_nome();
            String qty = list.getQuantidade().toString();
            String preco = list.getPreco().toString();
            builder.append(descricao).append("\t").append(qty).append("\t").append(preco).append("\n\r");
        }
        builder.append("--------------------------------\n\r");
        builder.append("Sub total:     \t").append(vendas.getSubtotal()).append("\n\r");
        builder.append("Valor recebido:\t").append(vendas.getValorRecebido()).append("\n\r");
        builder.append("Trocos:        \t").append(vendas.getTrocos()).append("\n\r");
        builder.append("Descontos:     \t").append(vendas.getDesconto()).append("\n\r");
        builder.append("Total:         \t").append(vendas.getTotal()).append("\n\r");
        builder.append("--------------------------------\n\r");
        builder.append("Data:          \t").append(dataActual).append("\n\r");
        builder.toString();
        System.out.println(builder.toString());
        //imprimirEscPos(builder.toString());
        //imprimir(builder.toString()); 
    }
/////////////////////////////////////////////////////////////////////////////////

    public void imprimirEscPos(String conteudo) {
        try {
            // Estabelece uma conexão com a impressora
            Socket printerSocket = new Socket("IP_DA_IMPRESSORA", 9100);
            PrinterOutputStream printerOutputStream = new PrinterOutputStream((PrintService) printerSocket);

            try ( // Inicializa o objeto EscPos com a saída da impressora
                     EscPos escpos = new EscPos(printerOutputStream)) {
                // Define o estilo do texto
                Style style = new Style()
                        .setFontSize(Style.FontSize._2, Style.FontSize._2)
                        .setJustification(EscPosConst.Justification.Center);
                // Imprime o conteúdo do recibo
                escpos.write(style,conteudo);
                // Finaliza a impressão
                escpos.feed(3);
                escpos.cut(EscPos.CutMode.FULL);
                // Fecha a conexão com a impressora
                printerOutputStream.close();
                printerSocket.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(frmVendas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
///////////////////////////////////////funcao para imprimir/////////////////////

    public void imprimir(String conteudoRecibo) {
        try {
            InputStream prin = new ByteArrayInputStream(conteudoRecibo.getBytes());
            DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            SimpleDoc documentoTexto = new SimpleDoc(prin, docFlavor, null);
            PrintService impressora = PrintServiceLookup.lookupDefaultPrintService();
            //pegar a impressora padrao
            PrintRequestAttributeSet printerAttributes = new HashPrintRequestAttributeSet();
            printerAttributes.add(new JobName("impressao", null));
            printerAttributes.add(OrientationRequested.PORTRAIT);
            printerAttributes.add(MediaSizeName.ISO_A4);
            DocPrintJob printJob = impressora.createPrintJob();
            printJob.print(documentoTexto, (PrintRequestAttributeSet) printerAttributes);
        } catch (PrintException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void limpar() {
        txtBarcode.setText("");
        txtId.setText("");
        txtDescricao.setText("");

    }
////////////////////////////////////////////////////////////////////////////////

    private void mostrarMetdodoPagamento() {
        List<MetodoPagamento> list = metodoDao.findAll();
        comboMetodoPagamento.removeAllItems();

        // Adicionar as categorias ao ComboBox
        for (MetodoPagamento metodo : list) {
            comboMetodoPagamento.addItem(metodo);
        }

    }

////////////////////////////////////////////////////////////////////////////////
    private void ListaProduto() {
        DefaultTableModel modelo = (DefaultTableModel) tblProduto.getModel();
        modelo.setNumRows(0);
        tblProduto.getColumnModel().getColumn(0);

        List<Produto> list = produtoDao.findAll();
        for (Produto produto : list) {
            modelo.addRow(new Object[]{
                produto.getId(),
                produto.getP_nome(),
                produto.getP_venda(),
                produto.getQuantidade(),});
        }
    }
    ///////////////////////////////////////////////////////////////////////////////

    private void filtrarByname() {
        DefaultTableModel modelo = (DefaultTableModel) tblProduto.getModel();
        modelo.setNumRows(0);
        tblProduto.getColumnModel().getColumn(0);

        List<Produto> list = produtoDao.filtrar(txtDescricao.getText());
        for (Produto produto : list) {
            modelo.addRow(new Object[]{
                produto.getId(),
                produto.getP_nome(),
                produto.getP_venda(),
                produto.getQuantidade(),});
        }
    }
//////////////////mouse clicked//////////////////////////////////////////////////

    private void findById() {
        int linha = tblProduto.getSelectedRow();
        int id = Integer.parseInt(tblProduto.getModel().getValueAt(linha, 0).toString());
        Produto produto = produtoDao.findById(id);
        txtDescricao.setText(produto.getP_nome());
    }
///////////////////////////////insert///////////////////////////////////////////  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOptionPane1 = new javax.swing.JOptionPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBarcode = new javax.swing.JTextField();
        txtDescricao = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProduto = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtDescontos = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtValorRecebido = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        FinalizarVenda = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        comboMetodoPagamento = new javax.swing.JComboBox<>();
        txtSubtotal = new javax.swing.JLabel();
        txtTrocos = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        lblOperador = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        ListaDeProdutos = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do produto"));

        jLabel1.setText("Codigo de barra");

        txtBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBarcodeActionPerformed(evt);
            }
        });

        txtDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescricaoActionPerformed(evt);
            }
        });
        txtDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescricaoKeyReleased(evt);
            }
        });

        jLabel2.setText("Descrição");

        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        jLabel11.setText("Cod. produto");

        jLabel3.setText("Quantidade");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtBarcode, txtDescricao, txtId});

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Items"));

        tblItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Id", "Descrição", "Preço", "Qtd", "Total"
            }
        ));
        jScrollPane1.setViewportView(tblItems);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de produtos"));

        tblProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Descrição", "Preço", "Stock"
            }
        ));
        tblProduto.setPreferredSize(new java.awt.Dimension(300, 100));
        tblProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblProduto);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Total      :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Trocos   :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Descontos");

        txtDescontos.setText("0.0");
        txtDescontos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescontosFocusLost(evt);
            }
        });
        txtDescontos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescontosKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Valor recebido");

        txtValorRecebido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorRecebidoKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Metodo de pagamento");

        FinalizarVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24x24/icons8-shopping-cart-24.png"))); // NOI18N
        FinalizarVenda.setText("Finalizar ");
        FinalizarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinalizarVendaActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24x24/icons8-cancel-24.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Subtotal:");

        comboMetodoPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtSubtotal.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSubtotal.setText("0.0");

        txtTrocos.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTrocos.setText("0.0");

        txtTotal.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTotal.setText("0.0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(FinalizarVenda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addGap(17, 17, 17))
                    .addComponent(txtValorRecebido)
                    .addComponent(txtDescontos)
                    .addComponent(comboMetodoPagamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel6))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(txtSubtotal))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txtTrocos))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTotal)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtValorRecebido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addGap(8, 8, 8)
                .addComponent(txtDescontos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(8, 8, 8)
                .addComponent(comboMetodoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtSubtotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTrocos))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTotal))
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FinalizarVenda)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Operador");

        lblOperador.setText("jLabel13");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(lblOperador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblOperador))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "F1         - Apagar item", "ENTER  - Adicionar Produtos" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList1);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Comandos");

        ListaDeProdutos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        ListaDeProdutos.setText("Pesquisar produtos");
        ListaDeProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListaDeProdutosActionPerformed(evt);
            }
        });
        jMenu2.add(ListaDeProdutos);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem2.setText("Apagar Items");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("jMenuItem3");
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDescontosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontosFocusLost
        //desconto();
    }//GEN-LAST:event_txtDescontosFocusLost

    private void txtDescontosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescontosKeyReleased
        somarValorTotal();

    }//GEN-LAST:event_txtDescontosKeyReleased

    private void FinalizarVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinalizarVendaActionPerformed
        vendas();

    }//GEN-LAST:event_FinalizarVendaActionPerformed

    private void txtValorRecebidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorRecebidoKeyReleased
        trocos();
    }//GEN-LAST:event_txtValorRecebidoKeyReleased

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        novo();


    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        adicionarProdutos();

    }//GEN-LAST:event_txtIdActionPerformed

    private void txtBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBarcodeActionPerformed
        adicionarProdutos();
    }//GEN-LAST:event_txtBarcodeActionPerformed

    private void txtDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescricaoActionPerformed
        adicionarProdutos();
    }//GEN-LAST:event_txtDescricaoActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        removerProdutos();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void ListaDeProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListaDeProdutosActionPerformed

    }//GEN-LAST:event_ListaDeProdutosActionPerformed

    private void txtDescricaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescricaoKeyReleased
        filtrarByname();
    }//GEN-LAST:event_txtDescricaoKeyReleased

    private void tblProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutoMouseClicked
        findById();
        txtDescricao.requestFocus();
    }//GEN-LAST:event_tblProdutoMouseClicked

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
            java.util.logging.Logger.getLogger(frmVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmVendas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton FinalizarVenda;
    private javax.swing.JMenuItem ListaDeProdutos;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<Object> comboMetodoPagamento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblOperador;
    private javax.swing.JTable tblItems;
    private javax.swing.JTable tblProduto;
    private javax.swing.JTextField txtBarcode;
    private javax.swing.JTextField txtDescontos;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JLabel txtSubtotal;
    private javax.swing.JLabel txtTotal;
    private javax.swing.JLabel txtTrocos;
    private javax.swing.JTextField txtValorRecebido;
    // End of variables declaration//GEN-END:variables
}
