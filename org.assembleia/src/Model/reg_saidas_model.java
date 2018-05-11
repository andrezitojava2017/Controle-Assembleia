/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Andre
 */
public class reg_saidas_model {
    
    private String data;
    private double valor;
    private String numero_recibo;
    private String competencia;
    private String descricao_saida;
    private int id;
    private tipo_saida_model id_tipo_saida;
    private pessoas_model id_pessoa;

    public pessoas_model getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(pessoas_model id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public tipo_saida_model getId_tipo_saida() {
        return id_tipo_saida;
    }

    public void setId_tipo_saida(tipo_saida_model id_tipo_saida) {
        this.id_tipo_saida = id_tipo_saida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getNumero_recibo() {
        return numero_recibo;
    }

    public void setNumero_recibo(String numero_recibo) {
        this.numero_recibo = numero_recibo;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getDescricao_saida() {
        return descricao_saida;
    }

    public void setDescricao_saida(String descricao_saida) {
        this.descricao_saida = descricao_saida;
    }
    
}
