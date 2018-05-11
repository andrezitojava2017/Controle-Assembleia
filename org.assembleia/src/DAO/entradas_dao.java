/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexao.ConexaoDB;
import Model.reg_entradas_model;
import Model.tipo_entrada_model;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Andre
 */
public class entradas_dao {

    Connection con;
    PreparedStatement stm;
    ResultSet rs;

    /**
     * captura dados da tabela de tipos de entradas tipos: Dizimo, Ofertas,
     * coleta CIBE, etc...
     *
     * @return List<String>()
     */
    public List<String> carregaTpEntrada() {

        List<String> lista = new ArrayList<>();

        try {

            String sql = "select descricao_tipo_entrada from assembleia.tbl_tipo_entradas";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                String descricao = rs.getString(1);
                lista.add(descricao);
            }
        } catch (SQLException ex) {
            Logger.getLogger(saidas_dao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    /**
     * Inseri novos valores a tabela de entrada
     *
     * @param entrada
     * @param tipo_entrada
     */
    public void inserirRegEntrada(reg_entradas_model entrada, tipo_entrada_model tipo_entrada) {

        try {

            LocalDate data;
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String sql = "insert into assembleia.tbl_entrada("
                    + "data_entrada, competencia_entrada, descricao_entrada, valor_entrada, id_tipo_entrada)"
                    + " values(?,?,?,?,?)";

            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            data = LocalDate.parse(entrada.getData(), formatador);
            stm.setString(1, String.valueOf(data));

            stm.setString(2, entrada.getCompetencia());
            stm.setString(3, entrada.getDescricao());
            stm.setDouble(4, entrada.getValor_entrada());
            stm.setInt(5, tipo_entrada.getId());

            int result = stm.executeUpdate();

            if (result != 0) {

                JOptionPane.showMessageDialog(null, "Lançamento inserido com sucesso!!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Ops...erro na tentativa de inserir novo lançamento de entrada\n" + ex, "Mensagem", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

    }

    /**
     * Faz a inserção na tabela de entradas, valores referente ao DIZIMOS
     * retorna o id da entrada, que sera gravado na tabela dizimos
     *
     * @param entrada
     * @param tipo_entrada
     * @return id_entrada
     */
    public int inserirRegEntradaDizimo(reg_entradas_model entrada, tipo_entrada_model tipo_entrada) {

        int id_entrada = 0;

        try {

            LocalDate data;
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String sql = "insert into assembleia.tbl_entrada("
                    + "data_entrada, competencia_entrada, descricao_entrada, valor_entrada, id_tipo_entrada)"
                    + " values(?,?,?,?,?)";

            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            data = LocalDate.parse(entrada.getData(), formatador);
            stm.setString(1, String.valueOf(data));

            stm.setString(2, entrada.getCompetencia());
            stm.setString(3, entrada.getDescricao());
            stm.setDouble(4, entrada.getValor_entrada());
            stm.setInt(5, tipo_entrada.getId());

            int result = stm.executeUpdate();

            if (result != 0) {

                JOptionPane.showMessageDialog(null, "Lançamento da tabela de entrada realizado com sucesso", "Mensagem", JOptionPane.INFORMATION_MESSAGE);

            }

        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Ops...erro na tentativa de inserir novo lançamento de entrada\n" + ex, "Mensagem", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

        return id_entrada;
    }

    /**
     * recupera lancamentos da tabela entrada, para preencher formulario de
     * alteração
     *
     * @param competencia
     * @return List<reg_entradas_model>
     */
    public List<reg_entradas_model> capturarRegistrosEntradas(String competencia) {

        List<reg_entradas_model> registros = new ArrayList<>();

        try {

            String sql = "select * from assembleia.tbl_entrada where competencia_entrada ='" + competencia + "'";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                reg_entradas_model entrada = new reg_entradas_model();

                entrada.setId(rs.getInt("id"));
                entrada.setData(rs.getString("data_entrada"));
                entrada.setCompetencia(rs.getString("competencia_entrada"));
                entrada.setDescricao(rs.getString("descricao_entrada"));
                entrada.setValor_entrada(rs.getDouble("valor_entrada"));
//                System.out.println(entrada.getId());

                registros.add(entrada);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error na tentativa de recuperar informações da tabela Entrada\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return registros;
    }

    /**
     * recupera lista com competencias lancadas na tabela entrada
     *
     * @return List<String>
     */
    public List<String> recuperaCompetenciasTabelaEntrada() {

        List<String> competencias = new ArrayList<>();

        try {
            String sql = "select competencia_entrada from assembleia.tbl_entrada group by competencia_entrada";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                String registro = rs.getString(1);
                competencias.add(registro);

            }

        } catch (SQLException ex) {
            Logger.getLogger(entradas_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return competencias;
    }

    /**
     * Metodo que recupera as informações de um determinado registro de Entrada
     * Utilizado para preencher o formulario quando solicitado para alteração
     *
     * @param id_selecionado
     * @return Object reg_entrada_model
     */
    public reg_entradas_model capturarRegParaAlteracao(int id_selecionado) {

        reg_entradas_model registro = new reg_entradas_model();

        try {

            String sql = "select * from assembleia.tbl_entrada where id ='" + id_selecionado + "'";
            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {

                // captuando o tipo de entrada
                tipo_entrada_dao tp_entrada = new tipo_entrada_dao();

                registro.setData(rs.getString("data_entrada"));
                registro.setCompetencia(rs.getString("competencia_entrada"));
                registro.setDescricao(rs.getString("descricao_entrada"));
                registro.setValor_entrada(rs.getDouble("valor_entrada"));
                int id_tipo_entrada = rs.getInt("id_tipo_entrada");
                registro.setTipo_entrada(tp_entrada.capturarDescricaoTipoEntrada(id_tipo_entrada));

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error na tentativa de recuperar informações da tabela Entrada\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm, rs);
        }

        return registro;
    }

    /**
     * metodo de atualização de um determinado registro da tabela de entrada
     *
     * @param entrada
     * @param tipo_entrada
     */
    public void atualizarRegEntrada(reg_entradas_model entrada, tipo_entrada_model tipo_entrada) {

        try {

            LocalDate data;
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String sql = "update assembleia.tbl_entrada "
                    + "set data_entrada=?, "
                    + "competencia_entrada=?, "
                    + "descricao_entrada=?, "
                    + "valor_entrada=?, "
                    + "id_tipo_entrada=? "
                    + "where id=" + entrada.getId();

            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            data = LocalDate.parse(entrada.getData(), formatador);
            stm.setString(1, String.valueOf(data));

            stm.setString(2, entrada.getCompetencia());
            stm.setString(3, entrada.getDescricao());
            stm.setDouble(4, entrada.getValor_entrada());
            stm.setInt(5, tipo_entrada.getId());

            int result = stm.executeUpdate();

            if (result != 0) {

                JOptionPane.showMessageDialog(null, "Parabéns... registro atualizado com sucesso!!  :)", "Mensagem", JOptionPane.INFORMATION_MESSAGE);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ops...erro na tentativa de atualização de registro\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

    }

    /**
     * metodo para deletar registros da tabela de entrada
     *
     * @param id_registro
     */
    public void deletarRegEntrada(int id_registro) {

        try {

            String sql = "delete from assembleia.tbl_entrada where id=" + id_registro;

            con = Conexao.ConexaoDB.getconection();
            stm = con.prepareStatement(sql);

            int result = stm.executeUpdate();

            if (result != 0) {

                JOptionPane.showMessageDialog(null, "Parabéns... registro foi deletado com sucesso!!  :)", "Mensagem", JOptionPane.INFORMATION_MESSAGE);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ops...erro na tentativa de deletar registro\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            Conexao.ConexaoDB.fecharConexao(con, stm);
        }

    }

    
    
    
    /**
     * Imprimi relatorio de entradas mensal
     * @param compet
     * @param caminho 
     */
    public void relatorioMensalEntradas(String compet) {

        try {
            con = ConexaoDB.getconection();
//            String caminhoRel = String.valueOf(caminho);
            InputStream caminho = getClass().getResourceAsStream("/Relatorios/Rel_Entradas_Mensais.jasper");
            
            Map parametro = new HashMap();
            parametro.put("competencia", compet);
            
            JasperPrint print = JasperFillManager.fillReport(caminho, parametro, con);
            JasperViewer tela = new JasperViewer(print, false);
            tela.setVisible(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(entradas_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(entradas_dao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
