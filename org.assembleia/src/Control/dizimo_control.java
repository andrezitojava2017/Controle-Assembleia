/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import DAO.dizimos_dao;
import Model.Dizimos_model;
import java.util.List;

/**
 *
 * @author Andre
 */
public class dizimo_control {

    /**
     * Inserção de lancamento de dizimos
     *
     * @param dizimos
     */
    public void inserirLancDizimos(Dizimos_model dizimos) {

        dizimos_dao dao = new dizimos_dao();
        dao.novoLancamentoDizimo(dizimos);
    }

    /**
     * Recupera uma lista de competencias ja lançadas na base de dados
     * @return List<> dizimos_model
     */
    public List<Dizimos_model> recuperarCompetenciasDizimos() {

        dizimos_dao dao = new dizimos_dao();
        List<Dizimos_model> dizimos = dao.carregarCompetenciasDizimos();
        return dizimos;
    }

    /**
     * Recupera uma lista de todos os lançamentos registrados na tabela de
     * dizimos
     *
     * @return dizimos List<Dizimos_model>
     */
    public List<Dizimos_model> recuperarLancamentosDizimos() {

        dizimos_dao dao = new dizimos_dao();
        List<Dizimos_model> dizimos = dao.carregarRegistrosEntradaDizimo();
        return dizimos;
    }

    /**
     * Recupera um registro da tabela de dizimos para alteração
     *
     * @param id_registro
     * @return dizimo
     */
    public Dizimos_model recuperarRegistroParaAlterar(int id_registro) {
        dizimos_dao dao = new dizimos_dao();
        Dizimos_model dizimos = dao.carregarRegistrosEntradaDizimo(id_registro);
        return dizimos;
    }

    /**
     * Faz a atualização de um registro da tabela de dizimos
     *
     * @param registro Dizimos_model
     */
    public void atualizarRegistroDizimo(Dizimos_model registro) {
        dizimos_dao dao = new dizimos_dao();
        dao.atualizarDadosRegistroDizimo(registro);
    }
}
