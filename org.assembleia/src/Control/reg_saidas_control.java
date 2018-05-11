/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import DAO.saidas_dao;
import Model.reg_saidas_model;
import java.util.List;

/**
 *
 * @author Andre
 */
public class reg_saidas_control {

    /**
     * inseri novo lancamento de saida na tabela saidas
     *
     * @param saida
     * @param id_tipo_saida
     * @param id_pessoa_empresa
     */
    public void salvarRegistroSaida(reg_saidas_model saida, int id_tipo_saida, int id_pessoa_empresa) {

        saidas_dao dao = new saidas_dao();
        dao.gravar_lancamento_saidas(saida, id_tipo_saida, id_pessoa_empresa);

    }

    /**
     * recupera as competencias da tabela saida
     *
     * @param competencia
     * @return List<reg_saidas_model>
     */
    public List<reg_saidas_model> capturarCompetenciasTabSaida() {

        saidas_dao dao = new saidas_dao();
        List<reg_saidas_model> comptSaida = dao.recuperaCompetenciasTabelaSaida();

        return comptSaida;
    }

    /**
     * recupera informações de todos os registros lançados na competencia 
     * informada no parametro
     * @param competencia
     * @return List<reg_saidas_model>
     */
    public List<reg_saidas_model> recuperarRegistroSaida(String competencia) {
        saidas_dao dao = new saidas_dao();
        List<reg_saidas_model> regSaidas = dao.capturarRegistrosSaidas(competencia);
        
        return regSaidas;
    }

    /**
     * recupera as informações de um determinado registro da tabela de SAIDAS
     * utilizado para preencher o formulario saida, para possivel alterações
     *
     * @param id_reg_saida
     * @return
     */
    public reg_saidas_model capturarRegistroSaida(int id_reg_saida) {

        saidas_dao dao = new saidas_dao();
        reg_saidas_model regAlterar = dao.recuperarRegParaAlteracao(id_reg_saida);
        
        return regAlterar;
    }
    
    /**
     * Faz a atualização de dados da tabela de Registros de saidas
     * @param informacao 
     */
    public void atualizarRegistroSaida(reg_saidas_model informacao){
        
        saidas_dao dao = new saidas_dao();
        dao.atualizarRegistroSaida(informacao);
        
    }
    
    /**
     * Deleta um determinado registro de saida selecionado pelo usuario
     * @param id_registro 
     */
    public void deletarRegistroSaida(int id_registro){
        
        saidas_dao dao = new saidas_dao();
        dao.deletarRegistroSaida(id_registro);
    }

}
