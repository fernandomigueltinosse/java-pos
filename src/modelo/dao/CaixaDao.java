/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.dao;

import java.util.List;
import modelo.entities.Caixa;
import modelo.entities.ItemsVenda;
import modelo.entities.SomatorioVenda;


/**
 *
 * @author Tomas
 */
public interface CaixaDao {
    void abrirCaixa(Caixa obj);
    void fecharCaixa(Caixa obj);
    Caixa findId();
    Caixa findById(Integer id);
    Caixa findtest(Caixa obj);
    List<Caixa> findAll();
    List<Caixa> findByDate(Caixa obj);
    Caixa findByDate2(Caixa obj);
 List<SomatorioVenda> findAllByMethod(Integer id);
 ItemsVenda findTotal(Integer id);
 void CaixaRelatorio(Integer id);
}
