/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.dao;

import java.util.List;
import modelo.entities.Empresa;

/**
 *
 * @author pc2
 */
public interface EmpresaDao {
    void insert(Empresa obj);
    void update(Empresa obj);
    Empresa findAll();
    
    
}
