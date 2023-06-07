/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entities;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author pc2
 */
public class Produto {
    SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
   private Integer id;
   private Integer barcode;
   private String p_nome;
   private Double p_compra;
   private Double p_venda;
   private Integer quantidade = 0;
   private Categoria categoria;
   private Date sk_data_registro;
   private Date sk_data_atualizacao;
  
   
   public Produto(){
   }
   
   public Produto(Integer id,Integer barcode,String nome,Double preco_compra,Double preco_venda, Integer quantidade, Categoria categoria){
       this.id = id;
       this.p_nome = nome;
       this.p_compra = preco_compra;
       this.p_venda = preco_venda;
       this.quantidade = quantidade;
       this.categoria = categoria;
       this.barcode = barcode;
   }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
     public Integer getBarcode() {
        return barcode;
    }

    public void setBarcode(Integer barcode) {
        this.barcode = barcode;
    }
    
   
    
    public Categoria getCategoria() {
        return categoria;
    }

    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
 
         
   public void AddQuantity(Integer quantidade){
       this.setQuantidade((Integer) (this.getQuantidade() + quantidade));          
   }
   
    public void RemoverQuantity(Integer quantidade){
        this.setQuantidade((Integer) (this.getQuantidade() - quantidade));          
   }
   
   public double totalValueInStock(){
       return getP_venda() * getQuantidade();
   }
  /* @Override
    public String toString(){
        return "produto[id="+id+",nome="+nome+",preco_compra="+preco_compra+",preco_venda="+preco_venda+",stock="+getQuantidade()+",categoria="+categoria+"]";
    }
    */
     @Override
    public String toString(){
        return "produto[nome="+getP_nome()+"]";
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getP_nome() {
        return p_nome;
    }

    public void setP_nome(String p_nome) {
        this.p_nome = p_nome;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getP_compra() {
        return p_compra;
    }

    public void setP_compra(Double p_compra) {
        this.p_compra = p_compra;
    }

    public Double getP_venda() {
        return p_venda;
    }

    public void setP_venda(Double p_venda) {
        this.p_venda = p_venda;
    }

    public Date getSk_data_registro() {
        return sk_data_registro;
    }

    public void setSk_data_registro(Date sk_data_registro) {
        this.sk_data_registro = sk_data_registro;
    }

    public Date getSk_data_atualizacao() {
        return sk_data_atualizacao;
    }

    public void setSk_data_atualizacao(Date sk_data_atualizacao) {
        this.sk_data_atualizacao = sk_data_atualizacao;
    }

   
}
