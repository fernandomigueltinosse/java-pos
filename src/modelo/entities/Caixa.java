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
public class Caixa {
    private Integer caixaId;
    private double saldoInicial;
    private double saldoAtual;
    private Date dataAbertura;
    private Date dataFechamento;
    
    
    private Usuario usuario;

    /**
     * @return the saldoInicial
     */
    public double getSaldoInicial() {
        return saldoInicial;
    }

    /**
     * @param saldoInicial the saldoInicial to set
     */
    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    /**
     * @return the saldoAtual
     */
    public double getSaldoAtual() {
        return saldoAtual;
    }

    /**
     * @param saldoAtual the saldoAtual to set
     */
    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    /**
     * @return the dataAbertura
     */
    public Date getDataAbertura() {
        return dataAbertura;
    }

    /**
     * @param dataAbertura the dataAbertura to set
     */
    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    /**
     * @return the dataFechamento
     */
    public Date getDataFechamento() {
        return dataFechamento;
    }

    /**
     * @param dataFechamento the dataFechamento to set
     */
    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the caixaId
     */
    public Integer getCaixaId() {
        return caixaId;
    }

    /**
     * @param caixaId the caixaId to set
     */
    public void setCaixaId(Integer caixaId) {
        this.caixaId = caixaId;
    }

    @Override
    public String toString() {
        return "Caixa{" + "caixaId=" + caixaId + ", saldoInicial=" + saldoInicial + ", saldoAtual=" + saldoAtual + ", dataAbertura=" + dataAbertura + ", dataFechamento=" + dataFechamento + ", usuario=" + usuario + '}';
    }

    
  
    
    
    
}
