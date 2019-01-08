/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Dizimos_model;
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
public class dizimos_dao {

    Connection con;
    PreparedStatement stm;
    ResultSet rs;

    /**
     * faz lançamento de dizimo na tabela DIZIMOS
     *
     * @param dizimos
     */
    public void novoLancamentoDizimo(Dizimos_model dizimos) {

        try {

            String sql = "insert into assembleia.tbl_dizimos("
                    + "data_dizimo, "
                    + "descricao_dizimo, "
                    + "id_pessoas, "
                    + "valor_dizimo, "
                    + "competencia_dizimo,"
                    + "recibo_dizimo) "
                    + "value(?,?,?,?,?,?)";

            LocalDate data;
            DateTimeFormatter formatar = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            data = LocalDate.parse(dizimos.getData(), formatar);// posicao 0 indica DATA lançamento

            stm.setString(1, String.valueOf(data));
            stm.setString(2, dizimos.getDescricao()); // posicao 1 indica DESCRIÇÃO do conteudo
            stm.setInt(3, dizimos.getPessoa().getId_pessoa());
            stm.setDouble(4, dizimos.getValor_lancamento());
            stm.setString(5, dizimos.getCompetencia());
            stm.setString(6, dizimos.getRecibo());

            int opt = stm.executeUpdate();

            if (opt != 0) {
                JOptionPane.showMessageDialog(null, "Lançamento de dizimo salvo com sucesso", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de lançamento de dizimo\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

    }

    /**
     * Recupera lançamentos da tabela de dizimos
     *
     * @return dizimos List<dizimos_model>
     */
    public List<Dizimos_model> carregarRegistrosEntradaDizimo() {

        List<Dizimos_model> dizimos = new ArrayList<>();

        try {
            String sql = "select * from assembleia.tbl_dizimos";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {

                Dizimos_model dizimo = new Dizimos_model();
                dizimo.setId_dizimo(rs.getInt("id"));
                dizimo.setData(rs.getString("data_dizimo"));
                dizimo.setDescricao(rs.getString("descricao_dizimo"));
                dizimo.setValor_lancamento(rs.getDouble("valor_dizimo"));
                dizimo.setCompetencia(rs.getString("competencia_dizimo"));
                pessoas_model pessoa = new pessoas_model();
                pessoa.setId_pessoa(rs.getInt("id_pessoas"));
                dizimo.setPessoa(pessoa);

                dizimos.add(dizimo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de capturar dados da tabela de dizimos\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return dizimos;
    }

    /**
     * Recupera uma lista de competencias lançadas na tabela de dizimos
     *
     * @return
     */
    public List<Dizimos_model> carregarCompetenciasDizimos() {

        List<Dizimos_model> dizimos = new ArrayList<>();

        try {
            String sql = "select competencia_dizimo from assembleia.tbl_dizimos group by competencia_dizimo";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {

                Dizimos_model dizimo = new Dizimos_model();
                dizimo.setCompetencia(rs.getString("competencia_dizimo"));
                dizimos.add(dizimo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de capturar competencias da tabela de dizimos\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return dizimos;
    }

    /**
     * Recupera um registro da tabela dizimos, para alteração
     *
     * @param id_registro
     * @return dizimo
     */
    public Dizimos_model carregarRegistrosEntradaDizimo(int id_registro) {

        Dizimos_model dizimo = new Dizimos_model();

        try {
            String sql = "select * from assembleia.tbl_dizimos where id=" + id_registro;
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {

                dizimo.setId_dizimo(rs.getInt("id"));
                dizimo.setData(rs.getString("data_dizimo"));
                dizimo.setDescricao(rs.getString("descricao_dizimo"));
                dizimo.setValor_lancamento(rs.getDouble("valor_dizimo"));
                dizimo.setCompetencia(rs.getString("competencia_dizimo"));
                dizimo.setRecibo(rs.getString("recibo_dizimo"));
                pessoas_model pessoa = new pessoas_model();
                pessoa.setId_pessoa(rs.getInt("id_pessoas"));
                dizimo.setPessoa(pessoa);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de capturar dados da tabela de dizimos\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return dizimo;
    }

    /**
     * Faz a atualização de um determinado registro da tabela de dizimos
     *
     * @param registro Dizimos_model
     */
    public void atualizarDadosRegistroDizimo(Dizimos_model registro) {
        try {
            String sql = "update assembleia.tbl_dizimos set "
                    + "data_dizimo=?, "
                    + "descricao_dizimo=?, "
                    + "id_pessoas=?, "
                    + "valor_dizimo=?, "
                    + "competencia_dizimo=?,"
                    + "recibo_dizimo=?,"
                    + "descricao_dizimo=? "
                    + "where id=" + registro.getId_dizimo();
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            // formata a data com padrão aceitavel pelo Mysql: yyyy-MM-dd
            LocalDate data = LocalDate.parse(registro.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            stm.setString(1, String.valueOf(data));
            stm.setString(2, registro.getDescricao());
            stm.setInt(3, registro.getPessoa().getId_pessoa());
            stm.setDouble(4, registro.getValor_lancamento());
            stm.setString(5, registro.getCompetencia());
            stm.setString(6, registro.getRecibo());
            stm.setString(7, registro.getDescricao());
            int retorno = stm.executeUpdate();

            if (retorno != 0) {
                JOptionPane.showMessageDialog(null, "Parabens...os dados foram atualizados com sucesso! :)", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de atualizar registro\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

    }

}
