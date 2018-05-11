/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.pessoas_model;
import Model.reg_saidas_model;
import Model.tipo_saida_model;
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
public class saidas_dao {

    Connection con;
    PreparedStatement stm;
    ResultSet rs;

    /**
     * faz a gravação de um novo lançamento de saidas tabela saidas
     *
     * @param saidas
     * @param id_tipo_saida
     * @param id_pessoa_empresa
     */
    public void gravar_lancamento_saidas(reg_saidas_model saidas, int id_tipo_saida, int id_pessoa_empresa) {

        try {
            String sql = "insert into assembleia.tbl_saidas("
                    + "data_saida, "
                    + "competencia_saida, "
                    + "descricao_saida,"
                    + "valor_saida,"
                    + "numero_recibo,"
                    + "id_credor,"
                    + "id_tipo_saida) values(?,?,?,?,?,?,?)";

            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            LocalDate data;
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            data = LocalDate.parse(saidas.getData(), formatador);

            stm.setString(1, String.valueOf(data));
            stm.setString(2, saidas.getCompetencia());
            stm.setString(3, saidas.getDescricao_saida());
            stm.setDouble(4, saidas.getValor());
            stm.setString(5, saidas.getNumero_recibo());
            stm.setInt(6, id_pessoa_empresa);
            stm.setInt(7, id_tipo_saida);

            int opt = stm.executeUpdate();

            if (opt != 0) {
                JOptionPane.showMessageDialog(null, "Lancamento de saida gravado com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro na tentativa de salvar lancamento de saida\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

    }

    /**
     * recupera lista com competencias lancadas na tabela Saida
     *
     * @return List<String>
     */
    public List<reg_saidas_model> recuperaCompetenciasTabelaSaida() {

        List<reg_saidas_model> competencias = new ArrayList<>();

        try {
            String sql = "select competencia_saida from assembleia.tbl_saidas group by competencia_saida";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                reg_saidas_model registro = new reg_saidas_model();

                registro.setCompetencia(rs.getString(1));
                competencias.add(registro);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:\n" + ex);
        }
        return competencias;
    }

    /**
     * recupera lancamentos da tabela Saida POR COMPETENCIA, para preencher
     * formulario de alteração
     *
     * @param competencia
     * @return List<reg_saidas_model>
     */
    public List<reg_saidas_model> capturarRegistrosSaidas(String competencia) {

        List<reg_saidas_model> registros = new ArrayList<>();

        try {

            String sql = "select * from assembleia.tbl_saidas where competencia_saida ='" + competencia + "'";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                reg_saidas_model saida = new reg_saidas_model();
                tipo_saida_model tipoSaida = new tipo_saida_model();
                pessoas_model idPessoa = new pessoas_model();

                saida.setId(rs.getInt("id"));
                saida.setData(rs.getString("data_saida"));
                saida.setCompetencia(rs.getString("competencia_saida"));
                saida.setDescricao_saida(rs.getString("descricao_saida"));
                saida.setValor(rs.getDouble("valor_saida"));
                saida.setNumero_recibo(rs.getString("numero_recibo"));

                tipoSaida.setId_saida(rs.getInt("id_tipo_saida"));
                saida.setId_tipo_saida(tipoSaida);
                idPessoa.setId_pessoa(rs.getInt("id_credor"));
                saida.setId_pessoa(idPessoa);

                registros.add(saida);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error na tentativa de recuperar informações da tabela Saidas\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return registros;
    }

    /**
     * recupera informações de um determinado registro da tabela de saidas
     * utilizado para preencher formulario de saida para alteração
     *
     * @param id_registro
     * @return REG_SAIDAS_MODEL
     */
    public reg_saidas_model recuperarRegParaAlteracao(int id_registro) {

        reg_saidas_model saida = new reg_saidas_model();

        try {

            String sql = "select * from assembleia.tbl_saidas where id =" + id_registro;
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                tipo_saida_model idTipoSaida = new tipo_saida_model();
                pessoas_model idPessoa = new pessoas_model();

                saida.setId(rs.getInt("id"));
                saida.setData(rs.getString("data_saida"));
                saida.setCompetencia(rs.getString("competencia_saida"));
                saida.setDescricao_saida(rs.getString("descricao_saida"));
                saida.setValor(rs.getDouble("valor_saida"));
                saida.setNumero_recibo(rs.getString("numero_recibo"));
                idTipoSaida.setId_saida(rs.getInt("id_tipo_saida"));
                saida.setId_tipo_saida(idTipoSaida);
                idPessoa.setId_pessoa(rs.getInt("id_credor"));
                saida.setId_pessoa(idPessoa);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error na tentativa de recuperar informações da tabela Saidas\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return saida;
    }

    /**
     * Atualizacao registro de saida do banco de dados
     *
     * @param regSaida
     */
    public void atualizarRegistroSaida(reg_saidas_model regSaida) {

        String sql = "update assembleia.tbl_saidas set"
                + " data_saida=?, "
                + "competencia_saida=?, "
                + "descricao_saida=?, "
                + "valor_saida=?, "
                + "numero_recibo=?, "
                + "id_credor=?, "
                + "id_tipo_saida=? "
                + "where id=" + regSaida.getId();

        try {
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(regSaida.getData(), format);

            stm.setString(1, String.valueOf(data));
            stm.setString(2, regSaida.getCompetencia());
            stm.setString(3, regSaida.getDescricao_saida());
            stm.setDouble(4, regSaida.getValor());
            stm.setString(5, regSaida.getNumero_recibo());
            pessoas_model idPessoa = regSaida.getId_pessoa();
            stm.setInt(6, idPessoa.getId_pessoa());
            tipo_saida_model tpSaida = regSaida.getId_tipo_saida();
            stm.setInt(7, tpSaida.getId_saida()); 
            
            int ret = stm.executeUpdate();
            
            if(ret != 0){
                JOptionPane.showMessageDialog(null, "Registro atualizado com sucesso! :)", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro na tentativa de atualizar informações\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally{
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }
    }
    
    /**
     * Metodo que faz a exclusão de um determinado registro selecionado pelo usuario
     * @param id_reg 
     */
    public void deletarRegistroSaida(int id_reg){
        
        try {
            String sql = "delete from assembleia.tbl_saidas where id=" + id_reg;
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            int rest = stm.executeUpdate();
            
            if(rest != 0){
                JOptionPane.showMessageDialog(null, "Registro foi excluido com sucesso! :)", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro na tentativa de exclusão de registron\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }
        
    }
}
