/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.pessoas_model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Andre
 */
public class pessoas_dao {

    Connection con;
    PreparedStatement stm;
    ResultSet rs;

    /**
     * recupera dados de uma pessoa ou empresa cadastrada na tabela pessoas
     *
     * @param idPessoa
     * @return pessoas_model
     */
    public pessoas_model recuperarInfoPessoaEmpresa(int idPessoa) {

        pessoas_model dadosPessoa = new pessoas_model();

        try {

            String sql = "select * from assembleia.tbl_pessoas where id='" + idPessoa + "'";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                dadosPessoa.setNome(rs.getString("nome"));
                dadosPessoa.setCpf(rs.getString("cpf"));
                dadosPessoa.setCnpj(rs.getString("cnpj"));

            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro na tentativa de capturar dados da tabela pessoas\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return dadosPessoa;
    }

    /**
     * inserção de PESSOA TIPO CNPJ
     *
     * @param pessoaEmpresa
     */
    public void inserirEmpresa(pessoas_model pessoaEmpresa) {

        try {
            String sql = "insert into assembleia.tbl_pessoas(nome, cnpj, data_cadastro)"
                    + " values(?,?,?)";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            LocalDate data;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            stm.setString(1, pessoaEmpresa.getNome());
            stm.setString(2, pessoaEmpresa.getCnpj());
            data = LocalDate.parse(pessoaEmpresa.getData_cadastro(), format);
            stm.setString(3, String.valueOf(data));

            int opt = stm.executeUpdate();

            if (opt != 0) {
                JOptionPane.showMessageDialog(null, "Parabens...Cadastro salvo com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de inserir novo cadastro de Pessoas/Empresa\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

    }

    
    /**
     * inserção de PESSOA TIPO CPF
     *
     * @param pessoaEmpresa
     */
    public void inserirPessoa(pessoas_model pessoaEmpresa) {

        try {
            String sql = "insert into assembleia.tbl_pessoas(nome, cpf, data_cadastro)"
                    + " values(?,?,?)";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            LocalDate data;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            stm.setString(1, pessoaEmpresa.getNome());
            stm.setString(2, pessoaEmpresa.getCpf());
            data = LocalDate.parse(pessoaEmpresa.getData_cadastro(), format);
            stm.setString(3, String.valueOf(data));

            int opt = stm.executeUpdate();

            if (opt != 0) {
                JOptionPane.showMessageDialog(null, "Parabens...Cadastro salvo com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de inserir novo cadastro de Pessoas/Empresa\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

    }

    
    /**
     * Recupera informações dos cadastros de pessoas FISICAS CPF
     *
     * @return List<>
     */
    public List<pessoas_model> carregarInfoPessoaFisica() {

        List<pessoas_model> pessoaFisica = new ArrayList<>();

        try {
            String sql = "select * from assembleia.tbl_pessoas where cpf is not null";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                pessoas_model info = new pessoas_model();
                info.setId_pessoa(rs.getInt(1));
                info.setNome(rs.getString(2));
                info.setCpf(rs.getString(4));

                pessoaFisica.add(info);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error na tentativa de ler dados da tabela pessoas\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return pessoaFisica;
    }

    /**
     * recupera informações de cadastros de pessoas JURIDICAS CNPJ
     *
     * @return
     */
    public List<pessoas_model> carregarInfoPessoaJuridica() {

        List<pessoas_model> pessoaJuridica = new ArrayList<>();

        try {
            String sql = "select * from assembleia.tbl_pessoas where cnpj is not null";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                pessoas_model info = new pessoas_model();
                info.setId_pessoa(rs.getInt(1));
                info.setNome(rs.getString(2));
                info.setCnpj(rs.getString(5));

                pessoaJuridica.add(info);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error na tentativa de ler dados da tabela pessoas\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return pessoaJuridica;
    }

    
    /**
     * atualiza informação de uma determinada empresa
     * @param pessoaEmpresa
     * @param id 
     */
    public void atualizarPessoaJuridica(pessoas_model pessoaEmpresa, int id) {

        try {
            String sql = "update assembleia.tbl_pessoas set nome=?, cnpj=? where id=" + id;
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            stm.setString(1, pessoaEmpresa.getNome());
            stm.setString(2, pessoaEmpresa.getCnpj());

            int opt = stm.executeUpdate();

            if (opt != 0) {
                JOptionPane.showMessageDialog(null, "Parabens...Cadastro atualizado com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de atualizar cadastro de Empresa\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

    }
    
    
    /**
     * atualiza informação de uma determinada empresa
     * @param pessoaFisica
     * @param id 
     */
    public void atualizarPessoaFisica(pessoas_model pessoaFisica, int id) {

        try {
            String sql = "update assembleia.tbl_pessoas set nome=?, cpf=? where id=" + id;
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            stm.setString(1, pessoaFisica.getNome());
            stm.setString(2, pessoaFisica.getCpf());

            int opt = stm.executeUpdate();

            if (opt != 0) {
                JOptionPane.showMessageDialog(null, "Parabens...Cadastro atualizado com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de atualizar cadastro de Pessoas\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

    }
    
}
