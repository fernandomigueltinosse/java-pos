/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entities;

/**
 *
 * @author pc2
 */
public class ItemsVenda {
    private Integer id;
    private Double preco;
    private Integer quantidade;
    private Vendas venda;
    private Produto produto;
    private Double lucros;
    private Double totalLucros;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * @return the venda
     */
    public Vendas getVenda() {
        return venda;
    }

    /**
     * @param venda the venda to set
     */
    public void setVenda(Vendas venda) {
        this.venda = venda;
    }

    
    
    @Override
    public String toString() {
        return "ItemsVenda{" + "id=" + getId() + ", preco=" + getPreco() + ", quantidade=" + getQuantidade() + ", venda=" + getVenda() + ", produto=" + getProduto() + '}';
    }

    public Double getLucros() {
        return lucros;
    }

    public void setLucros(Double lucros) {
        this.lucros = lucros;
    }

    public Double getTotalLucros() {
        return totalLucros;
    }

    public void setTotalLucros(Double totalLucros) {
        this.totalLucros = totalLucros;
    }
    
    
    
}
