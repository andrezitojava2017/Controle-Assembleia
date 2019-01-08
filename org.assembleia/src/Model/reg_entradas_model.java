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
public class reg_entradas_model {
    
    private String data;
    private String competencia;
    private Double valor_entrada;
    private String descricao;
    private String tipo_entrada;
    private int id;
    private int id_tipo_entrada;

    public int getId_tipo_entrada() {
        return id_tipo_entrada;
    }

    public void setId_tipo_entrada(int id_tipo_entrada) {
        this.id_tipo_entrada = id_tipo_entrada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_entrada() {
        return tipo_entrada;
    }

    public void setTipo_entrada(String tipo_entrada) {
        this.tipo_entrada = tipo_entrada;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public Double getValor_entrada() {
        return valor_entrada;
    }

    public void setValor_entrada(Double valor_entrada) {
        this.valor_entrada = valor_entrada;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
   
    
}
