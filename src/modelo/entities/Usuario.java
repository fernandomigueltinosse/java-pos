/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entities;

/**
 *
 * @author pc2
 */
public class Usuario {
    private Integer id;
    private String nome;
    private String cargo;
    private String senha;
    
    public Usuario(){
    }
    
    public Usuario(Integer id, String nome,String cargo,String senha){
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.senha = senha;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    @Override
    public String toString(){
        return  "usuario [id="+id +",nome="+nome + ",cargo="+ cargo + ",senha=" + senha + "]";
    }
    
}
