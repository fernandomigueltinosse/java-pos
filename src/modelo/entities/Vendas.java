/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entities;

import java.util.Date;

/**
 *
 * @author pc2
 */
public class Vendas {
    private Integer id;
    private Double subtotal;
    private Double total;
    private Double desconto;
    private Double valorRecebido;
    private Double trocos;
    private Date data;
    private MetodoPagamento metodo;
    private Caixa caixa;
    
        
    public Vendas(){
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

    /**
     * @return the subtotal
     */
    public Double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the valorRecebido
     */
    public Double getValorRecebido() {
        return valorRecebido;
    }

    /**
     * @param valorRecebido the valorRecebido to set
     */
    public void setValorRecebido(Double valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    /**
     * @return the trocos
     */
    public Double getTrocos() {
        return trocos;
    }

    /**
     * @param trocos the trocos to set
     */
    public void setTrocos(Double trocos) {
        this.trocos = trocos;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    public MetodoPagamento getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoPagamento metodo) {
        this.metodo = metodo;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

  

    

    
   

   
    
    
        
        
}
