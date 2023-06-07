/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.dao;

import java.util.List;
import modelo.entities.Usuario;

/**
 *
 * @author pc2
 */
public interface UsuarioDao {
   public void insert(Usuario obj);
   public void update (Usuario obj);
   public void deleteById(Integer id);
    Usuario login(Usuario modelUsuario);
    Usuario findById(Integer id);
    List<Usuario> findAll();
    List<Usuario> filtrar(String nome);
}
