/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Application;

import java.util.List;
import modelo.dao.CategoriaDao;
import modelo.dao.ClienteDao;
import modelo.dao.DaoFactory;
import modelo.dao.ProdutoDao;
import modelo.dao.UsuarioDao;
import modelo.entities.Categoria;
import modelo.entities.Cliente;
import modelo.entities.Produto;
import modelo.entities.Usuario;

/**
 *
 * @author pc2
 */
public class POS {

    /**
     * @param args the command line arguments
     */
   /* public static void main(String[] args) {
        CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
        System.out.println("text  findById");
        Categoria categoria = categoriaDao.findById(1);
        //System.out.println(categoria);
        ///////////////////////////////////////////////////////////////////////
       /* System.out.println("text findall");
        List<Categoria> list = categoriaDao.findAll();
        for (Categoria cat : list){
            System.out.println(cat);
        }*/
        ///////////////////////////////////////////////////////////////////////
        /*Categoria novaCategoria = new Categoria(null,"leites");
        System.out.println("dados da categoria"+novaCategoria.getId());
        categoriaDao.insert(novaCategoria);
        System.out.println("inserido! novo id=" + novaCategoria.getId());
        ///////////////////////////////////////////////////////////
        //categoria = categoriaDao.findById(1);
        //categoria.setNome_categoia("wisky");
        //categoriaDao.update(categoria);
        //System.out.println("actualizado com sucesso");
        
        ///////////////////////////////////////////////////////
        //categoriaDao.deleteById(7);
        //System.out.println("deletado com sucesso");
        
        ////////////////////////////////////////////////////////////////////////
        //UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
         //System.out.println("  inserir usuario");
         //Usuario novousuario = new Usuario(null, "martinha", "user", "123");
         //usuarioDao.insert(novousuario);
         //System.out.println("inserido! novo id=" + novousuario.getId());
         
         ////////////////////findByid///////////////////////////////////////////
         
         //Usuario usuario = usuarioDao.findById(1);
         //System.out.println(usuario);
         
         ////////////////findAll//////////////////////////////////
         /* System.out.println("text findall");
        List<Usuario> list = usuarioDao.findAll();
        for (Usuario user : list){
            System.out.println(user);
        }*/
        
        ///////////////////delete//////////////////////////////////////////////
        /*usuarioDao.deleteById(3);
        System.out.println("deletado com sucesso");*/
        ////////////////////update user/////////////////////////
      /*usuario = usuarioDao.findById(2);
        usuario.setNome("palmira");
        usuarioDao.update(usuario);
        System.out.println("actualizado com sucesso");*/
      
        /*ClienteDao clienteDao = DaoFactory.createClienteDao();
        Cliente cliente = new Cliente(null, "pedro", "matola", 826055177);*/
        //clienteDao.insert(cliente);
        //System.out.println("inserido! novo id=" + cliente.getId());
        
        //////////////////findById//////////////////////////////////////////////
        //cliente = clienteDao.findById(1);
        //System.out.println(cliente);
        
        ////////////////findAll//////////////////////////////////
         /* System.out.println("text findall");
        List<Cliente> list = clienteDao.findAll();
        for (Cliente cli : list){
            //System.out.println(cli);
        }
        */
        ///////////////////update//////////////////////////////////
        //cliente = clienteDao.findById(1);
        
        //cliente.setNome("beto");
        //clienteDao.update(cliente);
        //System.out.println("actualizado com sucesso");
        //System.out.println("text  findById produto");
        //ProdutoDao produtoDao = DaoFactory.createProdutoDao();
        //Produto produto = produtoDao.findById(1);
       // System.out.println(produto);
       /*
       System.out.println("text  findBy category");
        categoria  = new Categoria(1, null);
       List<Produto> list2 = produtoDao.findByCategory(categoria);
        for (Produto obj : list2){
            System.out.println(obj);
        }
        
        System.out.println("/n==== test : insert =====");
        Produto produto = new Produto(null, 1232, "gold", 10.0, 15.0, 200, categoria);
        produtoDao.insert(produto);
        */
        
        
    }
    
    
    

