/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entities;

import java.io.Serializable;

/**
 *
 * @author pc2
 */
public class Categoria implements Serializable{
    private Integer id;
    private String nome;
    
    public Categoria(){
    }
    
    public Categoria(Integer id, String nome){
        this.id = id;
        this.nome = nome;
    }

   
    
 /* @Override
    public String toString(){
        return "Categoria [id=" +getId() +",nome=" + getNome() + "]";
    }*/
    
   @Override
    public String toString(){
        return  getNome();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
