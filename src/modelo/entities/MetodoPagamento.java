/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entities;

/**
 *
 * @author pc2
 */
public class MetodoPagamento {
    private Integer id;
    private String metodo;

    public MetodoPagamento(){
    }
    
    public MetodoPagamento(Integer id, String metodo) {
        this.id = id;
        this.metodo = metodo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    @Override
    public String toString() {
        return  metodo;
    }

   
    
    
    
}
