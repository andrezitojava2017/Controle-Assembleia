/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import DAO.entradas_dao;
import Model.reg_entradas_model;
import Model.tipo_entrada_model;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author Andre
 */
public class registro_entrada_control {

    /**
     * captura a lista de tipos de entradas
     *
     * @return List<>
     */
    public List<String> preencheComboTipoEntrada() {
        List<String> lista;

        entradas_dao capt = new entradas_dao();
        lista = capt.carregaTpEntrada();

        return lista;
    }

    /**
     * Inserir novos valores a tabela de entradas
     *
     * @param entrada
     * @param tipo_entrada
     */
    public void inserirNovoRegEntrada(reg_entradas_model entrada, tipo_entrada_model tipo_entrada) {

        entradas_dao dao = new entradas_dao();
        dao.inserirRegEntrada(entrada, tipo_entrada);

    }

    /**
     * Recupera registros da tabela entrada para preenchimento do formulario
     * para alterações
     *
     * @return List<>()
     */
    public List<reg_entradas_model> recuperarRegistrosEntradas(String competencia) {

        entradas_dao dao = new entradas_dao();
        List<reg_entradas_model> registros = dao.capturarRegistrosEntradas(competencia);

        return registros;
    }

    /**
     * captura informações de um determinado registro da tabela entradas
     * metodo solicitao para alteração de informações do registro
     * @param id_selecionado
     * @return 
     */
    public reg_entradas_model recuperarInfoRegistroParaAlterar(int id_selecionado) {

        entradas_dao dao = new entradas_dao();
        reg_entradas_model registro = dao.capturarRegParaAlteracao(id_selecionado);

        return registro;
    }

    
    /**
     * metodo que faz a atualização de determinado registro da tabela entrada
     * @param entrada
     * @param tipo_entrada 
     */
    public void atualizarRegEntrada(reg_entradas_model entrada, tipo_entrada_model tipo_entrada){
        
        entradas_dao dao = new entradas_dao();
        dao.atualizarRegEntrada(entrada, tipo_entrada);
        
    }
    
    /**
     * deleta um determinado registro da tabela de entradas
     * @param id_registro 
     */
    public void deletarRegEntrada(int id_registro){
        
        entradas_dao dao = new entradas_dao();
        dao.deletarRegEntrada(id_registro);
        
    }
    
    
    /**
     * Recupera as competencias lançadas na tabela de entradas
     * @return List<String>
     */
    public List<String>recuperarCompetenciasEntrada(){
        entradas_dao dao = new entradas_dao();
        List<String> competencia = dao.recuperaCompetenciasTabelaEntrada();
        return competencia;
    }
    
    /**
     * Abre o relatorio mensal de entradas
     * @param competencia 
     */
    public void abrirRelatorioEntradasMensais(String competencia){
        entradas_dao dao = new entradas_dao();
        dao.relatorioMensalEntradas(competencia);
    }
}
