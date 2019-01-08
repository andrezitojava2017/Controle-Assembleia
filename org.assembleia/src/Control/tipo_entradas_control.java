/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import DAO.tipo_entrada_dao;
import Model.tipo_entrada_model;
import java.util.List;

/**
 *
 * @author Andre
 */
public class tipo_entradas_control {

    /**
     * Recupera o id de um determinado tipo de entrada tabela tipo_entradas
     *
     * @param tipo_entrada
     * @return int id
     */
    public int capturarIdTipoEntrada(String tipo_entrada) {

        tipo_entrada_dao dao = new tipo_entrada_dao();
        int id = dao.capturarIdTipoEntrada(tipo_entrada);

        return id;
    }

    
    /**
     * Recupera descricao do tipo de entrada vinculada a um id
     * @param id_tipo_entrada
     * @return 
     */
    public String capturarTipoEntrada(int id_tipo_entrada) {

        tipo_entrada_dao dao = new tipo_entrada_dao();
        String descricao = dao.capturarDescricaoTipoEntrada(id_tipo_entrada);

        return descricao;
    }

    /**
     * faz inserção de novo tipo de entrada ou saida conforme seleção do usuario
     * na tela de cadastro de novo tipo
     *
     * @param descricaoNovoTipo
     */
    public void inserirNovoTipoEntrada(String descricaoNovoTipo) {

        tipo_entrada_dao dao = new tipo_entrada_dao();
        dao.inserirNovoTipoEntrada(descricaoNovoTipo);

    }

    /**
     * Recupera os tipos de entradas cadastrados na tabela
     * @return List<tipo_entrada_model>
     */
    public List<tipo_entrada_model> recuperarTiposEntradas(){
        
        tipo_entrada_dao dao = new tipo_entrada_dao();
        List<tipo_entrada_model> tpEntradas = dao.recuperarListaTiposEntradas();
        
        return tpEntradas;
    }
    
    /**
     * Atualiza determinado registro de tpo de entrada
     * @param tipoEntrada tipo_entrada_model
     */
    public void atualizarTipoEntrada(tipo_entrada_model tipoEntrada){
        tipo_entrada_dao dao = new tipo_entrada_dao();
        dao.atualizarTipoEntrdada(tipoEntrada);
    }
}
