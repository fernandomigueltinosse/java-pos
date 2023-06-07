/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import db.DB;
import modelo.dao.impl.CaixaDaoJDBC;
import modelo.dao.impl.CategoriaDaoJBDC;
import modelo.dao.impl.ClienteDaoJDBC;
import modelo.dao.impl.EmpresaDaoJDBC;
import modelo.dao.impl.ItemsVendaDaoJDBC;
import modelo.dao.impl.MetodoPagamentoDaoJDBC;
import modelo.dao.impl.ProdutoDaoJDBC;
import modelo.dao.impl.SangriaDaoJDBC;
import modelo.dao.impl.UsuarioDaoJDBC;
import modelo.dao.impl.VendasDaoJBDC;


public class DaoFactory {
    public static CategoriaDao createCategoriaDao(){
        return new CategoriaDaoJBDC(DB.getConnection());
    }
    
    public static UsuarioDao createUsuarioDao(){
        return new UsuarioDaoJDBC(DB.getConnection());
    }
    
    public static ClienteDao createClienteDao(){
        return new ClienteDaoJDBC(DB.getConnection());
    }
    
    public static ProdutoDao createProdutoDao(){
        return new ProdutoDaoJDBC(DB.getConnection());
    }
    
    public static VendaDao createVendaDao(){
        return new VendasDaoJBDC(DB.getConnection());
    }
     
    public static itemsVendasDao createItemsVendaDao(){
        return new ItemsVendaDaoJDBC(DB.getConnection());
    }
      
    public static MetodoPagamentoDao createMetodoPagamento(){
        return new MetodoPagamentoDaoJDBC(DB.getConnection());
    }  
      
    public static CaixaDao createCaixa(){
        return new CaixaDaoJDBC(DB.getConnection());
    }
      
    public static SangriaDao createSangria(){
        return new SangriaDaoJDBC(DB.getConnection());
    }   
    
    public static EmpresaDao createEmpresa(){
        return new EmpresaDaoJDBC(DB.getConnection());
    } 
}
