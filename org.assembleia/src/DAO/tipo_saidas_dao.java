/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.tipo_saida_model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Andre
 */
public class tipo_saidas_dao {

    Connection con;
    PreparedStatement stm;
    ResultSet rs;

    /**
     * recupera uma lista com tipos de saidas cadastrados
     *
     * @return
     */
    public List<tipo_saida_model> recuperarListaTiposSaidas() {

        List<tipo_saida_model> lista = new ArrayList<>();

        try {

//            String sql = "select descricao from assembleia.tbl_tipo_saidas";
            String sql = "select id, descricao_tipo_saida from assembleia.tbl_tipo_saidas";

            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                tipo_saida_model saida = new tipo_saida_model();
                saida.setTipo_saida(rs.getString(2));
                saida.setId_saida(rs.getInt(1));

                lista.add(saida);

            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro na tentativa de leitura da tabela tipos saidas\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);

        }

        return lista;
    }

    /**
     * recupera o id de uma determinada tipo de saida informado pelo usuario
     *
     * @param descricao
     * @return id_saida
     */
    public int capturarIdTipoSaida(String descricao) {

        int id_saida = 0;

        try {

            String sql = "select id from assembleia.tbl_tipo_saidas where descricao_tipo_saida='" + descricao + "'";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareCall(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                id_saida = rs.getInt(1);
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro na tentativa de ler dados da tabela tipos saida\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return id_saida;
    }

    /**
     * recupera o id de uma determinada tipo de saida informado pelo usuario
     *
     * @param id_tipo_saida
     * @return tipoCadastrado
     */
    public String capturarTipoSaidaCadastrado(int id_tipo_saida) {

        String tipoCadastrado = null;
        try {

            String sql = "select descricao_tipo_saida from assembleia.tbl_tipo_saidas where id=" + id_tipo_saida;
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareCall(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                tipoCadastrado = rs.getString(1);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de ler dados da tabela tipo de saida\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return tipoCadastrado;
    }

    /**
     * inseri novo tipo de saidas na tabela de saidas
     *
     * @param descricao
     */
    public void inserirNovoTipoSaida(String descricao) {

        try {

            String sql = "insert into assembleia.tbl_tipo_saidas(descricao_tipo_saida) value(?)";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            stm.setString(1, descricao);

            int opt = stm.executeUpdate();

            if (opt != 0) {
                JOptionPane.showMessageDialog(null, "Novo tipo salvo com sucesso", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de inserir novo tipo\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }
    }

    /**
     * Atualiza um determinado tipo de saida
     *
     * @param dados
     */
    public void atualizarTipo(tipo_saida_model dados) {

        try {
            String sql = "update assembleia.tbl_tipo_saidas set descricao_tipo_saida=? where id=" + dados.getId_saida();
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            stm.setString(1, dados.getTipo_saida());
            int ret = stm.executeUpdate();

            if (ret != 0) {
                JOptionPane.showMessageDialog(null, "Atualização realizada com sucesso! :)", "Mensagem", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar registro\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally{
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

    }
}
