/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.dizimo_control;
import Control.pessoas_control;
import Model.Dizimos_model;
import Model.pessoas_model;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andre
 */
public class form_localizar_dizimos extends javax.swing.JDialog {

    /**
     * atributo para manter dados capturas da tabela entrada, para preenchimento
     * de campos do formulario assim evitamos uma nova leitura de informaçoes ao
     * banco
     */
    private List<Dizimos_model> dizimos;

    // atributo que ira armazenar o id selecionado na tabela, indicndo qual registro sera alterado
    int cod_reg = 0;

    /**
     * Creates new form form_alterar_reg_entrada
     */
    public form_localizar_dizimos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        preencherComboBoxCompetencias();
        recuperarDadosLancDizimos();
    }

    /**
     * Simplesmente faz a captura de todas as informações da tabela dizimos
     * armazenando em uma list<dizimos_model>
     */
    private void recuperarDadosLancDizimos(){
        
        dizimo_control control = new dizimo_control();
        this.dizimos = control.recuperarLancamentosDizimos();
        
    }
    
    /**
     * Preenche o combobox com as competencias registradas na tabela de dizimos
     */
    private void preencherComboBoxCompetencias() {

        dizimo_control control = new dizimo_control();
        this.dizimos = control.recuperarCompetenciasDizimos();

        for (Dizimos_model dizimo : dizimos) {
            combo_competencias.addItem(dizimo.getCompetencia());
        }
    }

    /**
     * preenche a area de descrição com as informaçoes da pessoa vinculada ao
     * registro de dizimo
     */
    private void preencherDadosReferentePessoa(int idPessoa) {
        pessoas_control pessoa = new pessoas_control();
        pessoas_model lista = pessoa.capturarInfoPessoasEmpresa(idPessoa);
        textArea_descricao.setText(lista.getNome());
    }

    /**
     * Metodo que faz o preenchimento da tabela com as informações recuperadas
     * da tabela ENTRADAS
     *
     */
    private void preencherTabelaRegEntradas(String competencia) {

        DefaultTableModel tabela = (DefaultTableModel) tbl_registros.getModel();
        tabela.setNumRows(0);
        
        // recupera uma lista com os lançamento de uma dterminado competencia
        dizimo_control control = new dizimo_control();
        List<Dizimos_model> listaDizimos = control.recuperarLancamentosDizimos();

        // preenchendo a tabela
        for (Dizimos_model dizimo : listaDizimos) {
            if (dizimo.getCompetencia().equalsIgnoreCase(competencia)) {

                tabela.addRow(new Object[]{
                    dizimo.getId_dizimo(),
                    formatarData(dizimo.getData()),
                    dizimo.getCompetencia(),
                    formatarValor(dizimo.getValor_lancamento())

                });
            }
        }
    }

    /**
     * Metodo para formatar valores doubles no formato BR
     *
     * @param valor
     * @return String valorFormatado
     */
    private String formatarValor(Double valor) {

        String valorFormatado;

        DecimalFormat format = new DecimalFormat("R$ ###,##0.00");
        valorFormatado = format.format(valor);
//        System.out.println(valorFormatado);

        return valorFormatado;
    }

    /**
     * Metodo que formata a data no padrão dd/MM/yyyy
     *
     * @param data
     * @return String dat
     */
    private String formatarData(String data) {

        String dataRegistro = null;

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dat = LocalDate.parse(data);

        dataRegistro = format.format(dat);
//        System.out.println(dataRegistro);

        return dataRegistro;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        group = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        combo_competencias = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_registros = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textArea_descricao = new javax.swing.JTextArea();
        btn_alterar_reg = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Localizar entradas de dizimos");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel1.setText("Selecionar competencia:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(combo_competencias, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(combo_competencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jButton1.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        jButton1.setText("Carregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        tbl_registros.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        tbl_registros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Data lancameto", "Competencia", "Valor R$"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_registros.setOpaque(false);
        tbl_registros.setShowVerticalLines(false);
        tbl_registros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_registrosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_registros);
        if (tbl_registros.getColumnModel().getColumnCount() > 0) {
            tbl_registros.getColumnModel().getColumn(0).setMinWidth(30);
            tbl_registros.getColumnModel().getColumn(0).setMaxWidth(60);
            tbl_registros.getColumnModel().getColumn(1).setMinWidth(80);
            tbl_registros.getColumnModel().getColumn(1).setMaxWidth(120);
            tbl_registros.getColumnModel().getColumn(2).setMinWidth(80);
            tbl_registros.getColumnModel().getColumn(2).setMaxWidth(120);
            tbl_registros.getColumnModel().getColumn(3).setMinWidth(80);
            tbl_registros.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Descrição:"));

        textArea_descricao.setColumns(20);
        textArea_descricao.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        textArea_descricao.setRows(5);
        jScrollPane2.setViewportView(textArea_descricao);

        btn_alterar_reg.setFont(new java.awt.Font("DejaVu Sans", 0, 12)); // NOI18N
        btn_alterar_reg.setText("Alterar este registro");
        btn_alterar_reg.setEnabled(false);
        btn_alterar_reg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_alterar_regActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_alterar_reg)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_alterar_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        preencherTabelaRegEntradas(combo_competencias.getSelectedItem().toString());

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbl_registrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_registrosMouseClicked

        // capturar o id referente ao lançamento na tabela selecionada
        int linha = tbl_registros.getSelectedRow();
        int codSelec = Integer.parseInt(tbl_registros.getValueAt(linha, 0).toString());

        for (Dizimos_model dizimo : dizimos) {
            if(dizimo.getId_dizimo() == codSelec){ // captura o id selecionado na tabela
                preencherDadosReferentePessoa(dizimo.getPessoa().getId_pessoa());
                btn_alterar_reg.setEnabled(true);
            }
        }
        
    }//GEN-LAST:event_tbl_registrosMouseClicked

    private void btn_alterar_regActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_alterar_regActionPerformed

        int row = tbl_registros.getSelectedRow();
        int valor = (Integer) tbl_registros.getValueAt(row, 0);

        //atribuindo valor, esse é o id do registro selecionado para possivel alteração
        this.cod_reg = valor;
        dispose();
    }//GEN-LAST:event_btn_alterar_regActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(form_localizar_dizimos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_localizar_dizimos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_localizar_dizimos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_localizar_dizimos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                form_localizar_dizimos dialog = new form_localizar_dizimos(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_alterar_reg;
    private javax.swing.JComboBox<String> combo_competencias;
    private javax.swing.ButtonGroup group;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_registros;
    private javax.swing.JTextArea textArea_descricao;
    // End of variables declaration//GEN-END:variables
}
