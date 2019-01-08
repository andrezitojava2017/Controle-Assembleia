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
public class Dizimos_model extends Lancamentos_model{
    
    private pessoas_model pessoa;
    private int id_dizimo;

    public int getId_dizimo() {
        return id_dizimo;
    }

    public void setId_dizimo(int id_dizimo) {
        this.id_dizimo = id_dizimo;
    }

    public pessoas_model getPessoa() {
        return pessoa;
    }

    public void setPessoa(pessoas_model pessoa) {
        this.pessoa = pessoa;
    }
}
