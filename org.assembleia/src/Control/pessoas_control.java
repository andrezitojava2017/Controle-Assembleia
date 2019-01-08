/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import DAO.pessoas_dao;
import Model.pessoas_model;
import Model.tipo_pessoas;
import java.util.List;

/**
 *
 * @author Andre
 */
public class pessoas_control {
 
    /**
     * recupera informaçoes de uma determinad pessoa ou empresa cadastrada na
     * tabela de pessoas
     * @param idPessoa
     * @return pessoas_model
     */
    public pessoas_model capturarInfoPessoasEmpresa(int idPessoa){
        
        pessoas_dao dao = new pessoas_dao();
        pessoas_model pessoaEmpresa = dao.recuperarInfoPessoaEmpresa(idPessoa);
        
        return pessoaEmpresa;
    }
    
    /**
     * Faz a inserção na base de dados, informações sobre nova pessoa ou empresa a ser
     * cadastrada na tabela de pessoas
     * @param pessoaEmpresa
     * @param tipoPessoa constante TIPO_PESSOA indicando se é CPNJ ou CPF a ser cadastrado
     */
    public void inserirPessoasEmpresas(pessoas_model pessoaEmpresa, int tipoPessoa){
        
        if(tipoPessoa == 0){
            
            pessoas_dao cnpj = new pessoas_dao();
            cnpj.inserirEmpresa(pessoaEmpresa);
            
        } else {
            
            pessoas_dao cpf = new pessoas_dao();
            cpf.inserirPessoa(pessoaEmpresa);
            
        }
        
    }
    
    
    /**
     * recupera informações de cadastros de pessoas FISICAS -CPF
     * @return List<>
     */
    public List<pessoas_model> recuperarInfoPessoaFisica(){
        
        List<pessoas_model> pessoaFisica;
        
        pessoas_dao dao = new pessoas_dao();
        pessoaFisica = dao.carregarInfoPessoaFisica();
        
        return pessoaFisica;
    }
    
    
        
    /**
     * recupera informações de cadastros de pessoas JURIDICA -CNPJ
     * @return List<>
     */
    public List<pessoas_model> recuperarInfoPessoaJuridica(){
        
        List<pessoas_model> pessoaJuridica;
        
        pessoas_dao dao = new pessoas_dao();
        pessoaJuridica = dao.carregarInfoPessoaJuridica();
        
        return pessoaJuridica;
    }
    
    
    /**
     * atualiza informações de um cadastro, conforme tipo selecionado
     * @param pessoaEmpresa
     * @param tipoPessoa
     * @param id 
     */
    public void atualizarCadastrPessoa(pessoas_model pessoaEmpresa, int tipoPessoa, int id){
        
        if(tipoPessoa == tipo_pessoas.CNPJ){
            
            pessoas_dao juridica = new pessoas_dao();
            juridica.atualizarPessoaJuridica(pessoaEmpresa, id);
            
        } else if(tipoPessoa == tipo_pessoas.CPF) {
            
            pessoas_dao fisica = new pessoas_dao();
            fisica.atualizarPessoaFisica(pessoaEmpresa, id);
            
        }
        
    }
}
