/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.pessoas_control;
import Control.reg_saidas_control;
import Control.tipo_saidas_control;
import DAO.saidas_dao;
import Model.TipoRegistro;
import Model.pessoas_model;
import Model.reg_saidas_model;
import Model.tipo_saida_model;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Andre
 */
public class form_cad_saida extends javax.swing.JDialog {

    // armazena o id da pessoa/empresa selecionada
    private int id_pessoa_selecionada = 0;

    // id que referencia o registro da tabela saida, que sera atualizado
    private int id_reg_saida = 0;

    /**
     * Creates new form form_cad_entrada
     */
    public form_cad_saida(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        carregarInfo();
    }

    /**
     * Limpa os campos deste formulario
     */
    private void limparCampos() {
        // limpar campos
        campo_valor_saida.setText("");
        campo_num_recibo.setText("");
        campo_cpf_cnpj.setText("");
        campo_nome_pessoa_empresa.setText("");
        area_descricao.setText("");
        this.id_pessoa_selecionada = 0; // muda o id da pessoa selecionada
    }

    /**
     * Preenche os campos com informações do lançamento de saida recuperado da
     * base
     *
     * @param id_reg_saida
     */
    private void preencherCamposSaidasAlterar(int id_reg_saida) {

        reg_saidas_control recuperarInfoSaida = new reg_saidas_control();
        reg_saidas_model regSaida = recuperarInfoSaida.capturarRegistroSaida(id_reg_saida);

        // preenchendo campos com as informações da base de dados
        campo_competencia.setText(regSaida.getCompetencia().replace("/", ""));
        campo_data_lancamento.setText(formatarData(regSaida.getData()));
        campo_valor_saida.setText(formatarValor(regSaida.getValor()));
        campo_num_recibo.setText(regSaida.getNumero_recibo());
        area_descricao.setText(regSaida.getDescricao_saida());

        preencherComboTipoSaida(regSaida);
        preencherCamposPessoasEmpresa(regSaida);

    }

    /**
     * Seleciona a opção do tipo de pagamento recuperado
     *
     * @param regSaida
     */
    private void preencherComboTipoSaida(reg_saidas_model regSaida) {
        // recupera informações sobre o tipo de saida informada
        tipo_saidas_control recuperarTpSaida = new tipo_saidas_control();
        tipo_saida_model tpSaidaModel = regSaida.getId_tipo_saida();
        combo_tipo_saida.setSelectedItem(recuperarTpSaida.recuperarTipoSaida(tpSaidaModel.getId_saida()));

    }

    /**
     * metodo que faz o preenchimento dos campos especificos de pessoa/empresa
     *
     * @param regSaida
     */
    private void preencherCamposPessoasEmpresa(reg_saidas_model regSaida) {

        // recuperando informações de uma determinada pessoa vinculada ao registro de saida
        pessoas_control recuperarPessoa = new pessoas_control();

        // recuperando o id que da pessoa vinculada ao registro de saida
        pessoas_model id_pessoa = regSaida.getId_pessoa();

        // aqui já recuperamos todas as informações da pessoa
        pessoas_model dadosPessoa = recuperarPessoa.capturarInfoPessoasEmpresa(id_pessoa.getId_pessoa());

        //atributo id_pessoa_selecionada pertence a esta classe
        this.id_pessoa_selecionada = id_pessoa.getId_pessoa();

        // agora iremos preencher os campos
        if (dadosPessoa.getCpf() != null) {
            campo_nome_pessoa_empresa.setText(dadosPessoa.getNome());
            campo_cpf_cnpj.setText(dadosPessoa.getCpf());
        } else {
            campo_nome_pessoa_empresa.setText(dadosPessoa.getNome());
            campo_cpf_cnpj.setText(dadosPessoa.getCnpj());
        }

    }

    /**
     * Formata a data no padrao "dd/MM/yyyy"
     *
     * @param data
     * @return String
     */
    private String formatarData(String data) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dat = LocalDate.parse(data);
        String dataFormatada = format.format(dat);

        return dataFormatada;
    }

    /**
     * Metodo para formatar valores doubles no formato BR
     *
     * @param valor
     * @return String valorFormatado
     */
    private String formatarValor(Double valor) {

        String valorFormatado;
        DecimalFormat format = new DecimalFormat("###,##0.00");
        valorFormatado = format.format(valor);

        return valorFormatado;
    }

    /**
     * preenche os campos referente a pessoas ou empresa
     *
     * @param cod_pessoa
     */
    public void preencherCamposPessoasEmpresa(int cod_pessoa) {

        pessoas_control capturar = new pessoas_control();
        pessoas_model pessoaEmpresa = capturar.capturarInfoPessoasEmpresa(cod_pessoa);

        // verificamos se o cadastro é CNPJ ou CPF
        if (pessoaEmpresa.getCnpj() == null) {
            campo_cpf_cnpj.setText(pessoaEmpresa.getCpf());
            campo_nome_pessoa_empresa.setText(pessoaEmpresa.getNome());

        } else {
            campo_cpf_cnpj.setText(pessoaEmpresa.getCnpj());
            campo_nome_pessoa_empresa.setText(pessoaEmpresa.getNome());
        }
    }

    /**
     * metodo criado para tratar os valores corretamente para serem salvos na
     * base de dados
     *
     * @param valor_entrada
     */
    private double convertValorInfo(String valor_entrada) {

        // transfroma em vetor
        IntStream s = valor_entrada.chars();
        double valor = 0;

        // verifica a qtd de posiçoes... indica numeros com casas de milhares
        if (s.count() > 6) {
            String n = valor_entrada.replace(".", "");
            String b = n.replace(",", ".");

            valor = Double.parseDouble(b);
//            System.out.println(valor);

        } else {

            // valores com casas abaixo de milhares
            String n = valor_entrada.replace(",", ".");
            valor = Double.parseDouble(n);
//            System.out.println(n);
        }

        return valor;
    }

    /**
     * carrega informações principais do formulario de entrada
     */
    private void carregarInfo() {
        //carregando a data atual
        LocalDateTime data = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        campo_data_lancamento.setText(formato.format(data));

        //captura o mes e preenche o campo competencia
        LocalDate dat = LocalDate.now();
        String mes = String.valueOf(dat.getMonthValue());
        String ano = String.valueOf(dat.getYear());
        campo_competencia.setText(mes + "/" + ano);

        // carrega os tipos de entradas cadastrados
        tipo_saidas_control tpSaidas = new tipo_saidas_control();
        List<tipo_saida_model> lista = tpSaidas.recuperaListaTipoSaidas();

        // preenche o combo com os dados capturados da tabela tipo de saidas
        for (tipo_saida_model list : lista) {

            combo_tipo_saida.addItem(list.getTipo_saida());

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        area_descricao = new javax.swing.JTextArea();
        btn_salvar_lancamento = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        combo_tipo_saida = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        campo_data_lancamento = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        campo_valor_saida = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        campo_num_recibo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        campo_nome_pessoa_empresa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        campo_cpf_cnpj = new javax.swing.JTextField();
        btn_localizar_pessoa_empresa = new javax.swing.JButton();
        campo_competencia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btn_atualizar = new javax.swing.JButton();
        btn_deletar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de novas entradas");
        setResizable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Breve descrição"));

        jLabel4.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel4.setText("Descrição:");

        area_descricao.setColumns(20);
        area_descricao.setLineWrap(true);
        area_descricao.setRows(10);
        area_descricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                area_descricaoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(area_descricao);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_salvar_lancamento.setFont(new java.awt.Font("Miriam", 1, 14)); // NOI18N
        btn_salvar_lancamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/Salvar_registro.png"))); // NOI18N
        btn_salvar_lancamento.setToolTipText("Salvar lancamento");
        btn_salvar_lancamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvar_lancamentoActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Informações"));

        jLabel1.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel1.setText("Tipo de saida:");

        combo_tipo_saida.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        combo_tipo_saida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                combo_tipo_saidaKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel2.setText("Data lançamento:");

        try {
            campo_data_lancamento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campo_data_lancamento.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        campo_data_lancamento.setOpaque(false);
        campo_data_lancamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campo_data_lancamentoKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel3.setText("Competencia:");

        jLabel5.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel5.setText("Valor de saida R$:");

        campo_valor_saida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        campo_valor_saida.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        campo_valor_saida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campo_valor_saidaKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel7.setText("Numero recibo:");

        campo_num_recibo.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        campo_num_recibo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campo_num_reciboKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel8.setText("Credor:");

        campo_nome_pessoa_empresa.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        campo_nome_pessoa_empresa.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel9.setText("CPF/CNPJ:");

        campo_cpf_cnpj.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        campo_cpf_cnpj.setEnabled(false);

        btn_localizar_pessoa_empresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icons8-pesquisar-16.png"))); // NOI18N
        btn_localizar_pessoa_empresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_localizar_pessoa_empresaActionPerformed(evt);
            }
        });
        btn_localizar_pessoa_empresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_localizar_pessoa_empresaKeyPressed(evt);
            }
        });

        campo_competencia.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(campo_data_lancamento, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(jLabel1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(campo_num_recibo, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(campo_competencia))
                                .addGap(56, 56, 56)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(combo_tipo_saida, 0, 92, Short.MAX_VALUE)
                                    .addComponent(campo_valor_saida)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(156, 156, 156)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campo_cpf_cnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(campo_nome_pessoa_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_localizar_pessoa_empresa)))))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(campo_data_lancamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(campo_competencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(combo_tipo_saida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(campo_valor_saida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(campo_num_recibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(campo_cpf_cnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(campo_nome_pessoa_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_localizar_pessoa_empresa)))
        );

        jLabel6.setFont(new java.awt.Font("Dotum", 1, 24)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/lancamento_saida.png"))); // NOI18N
        jLabel6.setText("Registrar novas saidas");
        jLabel6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icons8-vassoura-68.png"))); // NOI18N
        jButton1.setToolTipText("Limpar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jButton2.setText("Alterar um registro");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btn_atualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/atualizar_registro.png"))); // NOI18N
        btn_atualizar.setToolTipText("Salvar Alterações");
        btn_atualizar.setEnabled(false);
        btn_atualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atualizarActionPerformed(evt);
            }
        });

        btn_deletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/excluir_registro.png"))); // NOI18N
        btn_deletar.setToolTipText("Deletar registro");
        btn_deletar.setEnabled(false);
        btn_deletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deletarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 92, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btn_salvar_lancamento, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(btn_atualizar)
                        .addGap(18, 18, 18)
                        .addComponent(btn_deletar)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel6)
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_atualizar)
                        .addComponent(btn_deletar))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_salvar_lancamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(718, 627));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_salvar_lancamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvar_lancamentoActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "Salvar as informações no banco de dados?");

        if (opt == 0) {

            // verificando se todos os campos estão preenchidos
            if (campo_competencia.getText().isEmpty() || campo_data_lancamento.getText().isEmpty() || area_descricao.getText().isEmpty() || campo_num_recibo.getText().isEmpty() || campo_valor_saida.getText().isEmpty()) {

                // mensagem de erro ao usuario
                JOptionPane.showMessageDialog(null, "Atenção: preencha todos os campos do formulario\nVerifique se foi selecionado algum credor(pessoa)", "Mensagem", JOptionPane.INFORMATION_MESSAGE);

            } else {

                reg_saidas_model saida = new reg_saidas_model();

                // tratando o valor informado pelo usuario, para evitar erros a inserção
                double valorSaida = convertValorInfo(campo_valor_saida.getText());

                // recuperando o id do tipo de saida selecionado no combobox
                tipo_saidas_control recuperarTpSaida = new tipo_saidas_control();
                int id_tipo_saida = recuperarTpSaida.recuperaIdTipoSaida(combo_tipo_saida.getSelectedItem().toString());

                saida.setData(campo_data_lancamento.getText());
                saida.setCompetencia(campo_competencia.getText());
                saida.setDescricao_saida(area_descricao.getText());
                saida.setNumero_recibo(campo_num_recibo.getText());
                saida.setValor(valorSaida);

                reg_saidas_control lancar = new reg_saidas_control();
                lancar.salvarRegistroSaida(saida, id_tipo_saida, id_pessoa_selecionada);

            }
        }

    }//GEN-LAST:event_btn_salvar_lancamentoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // limpar campos
        limparCampos();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void campo_data_lancamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campo_data_lancamentoKeyPressed
        // troca de foco ao pressionar ENTER
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            combo_tipo_saida.requestFocus();
        }
    }//GEN-LAST:event_campo_data_lancamentoKeyPressed

    private void combo_tipo_saidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combo_tipo_saidaKeyPressed
        // troca de foco ao pressionar ENTER
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            campo_competencia.requestFocus();
        }
    }//GEN-LAST:event_combo_tipo_saidaKeyPressed

    private void campo_valor_saidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campo_valor_saidaKeyPressed
        // troca de foco ao pressionar ENTER
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            campo_num_recibo.requestFocus();
        }
    }//GEN-LAST:event_campo_valor_saidaKeyPressed

    private void btn_localizar_pessoa_empresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_localizar_pessoa_empresaActionPerformed
        // formulario buscar pessoas
        form_localizar_pessoas form = new form_localizar_pessoas(null, true);
        form.setVisible(true);

        // passando valor recuperado para o metodo que ira preencher os dados de pessoa ou empresa
        if (form.codigo_pessoa != 0) {

            this.id_pessoa_selecionada = form.codigo_pessoa; // atributo desta classe
            preencherCamposPessoasEmpresa(form.codigo_pessoa);

        } else {
            JOptionPane.showMessageDialog(this, "Atenção: nenhuma pessoa foi selecionada, verifique", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_localizar_pessoa_empresaActionPerformed

    private void campo_num_reciboKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campo_num_reciboKeyPressed
        // troca de foco ao pressionar ENTER
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btn_localizar_pessoa_empresa.requestFocus();
        }
    }//GEN-LAST:event_campo_num_reciboKeyPressed

    private void btn_localizar_pessoa_empresaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_localizar_pessoa_empresaKeyPressed
        // troca de foco ao pressionar ENTER
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            form_localizar_pessoas local = new form_localizar_pessoas(null, true);
            local.setVisible(true);
        }
    }//GEN-LAST:event_btn_localizar_pessoa_empresaKeyPressed

    private void area_descricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_area_descricaoKeyPressed
        // troca de foco ao pressionar ENTER
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btn_salvar_lancamento.requestFocus();
        }
    }//GEN-LAST:event_area_descricaoKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        form_alterar_registros buscarReg = new form_alterar_registros(null, true, TipoRegistro.TIPO_SAIDA);
        buscarReg.setVisible(true);

        if (buscarReg.cod_reg != 0) {
            preencherCamposSaidasAlterar(buscarReg.cod_reg);
            this.id_reg_saida = buscarReg.cod_reg;
            btn_atualizar.setEnabled(true);
            btn_deletar.setEnabled(true);
            btn_salvar_lancamento.setEnabled(false);

        } else {
            btn_atualizar.setEnabled(false);
            btn_deletar.setEnabled(false);
            btn_salvar_lancamento.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Nenhum registro foi selecionado", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizarActionPerformed

        // atualizando registro da tabela saidas
        if (campo_competencia.getText().isEmpty() || campo_data_lancamento.getText().isEmpty() || campo_num_recibo.getText().isEmpty() || campo_valor_saida.getText().isEmpty() || area_descricao.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Atenção: verifique se todos os campos foram corretamente preenchidos", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
        } else {

            reg_saidas_model regSaida = new reg_saidas_model();
            tipo_saida_model tpSaida = new tipo_saida_model();
            pessoas_model infoPessoa = new pessoas_model();

            regSaida.setId(this.id_reg_saida); // esse é o id do registro que sera atualizado
            regSaida.setCompetencia(campo_competencia.getText());
            regSaida.setData(campo_data_lancamento.getText());
            regSaida.setNumero_recibo(campo_num_recibo.getText());
            regSaida.setDescricao_saida(area_descricao.getText());
            double valor = convertValorInfo(campo_valor_saida.getText());// conversão para um padrão aceito pelo mysql
            regSaida.setValor(valor);

            // recuperando o id do tipo de saida selecionado no combobox
            tipo_saidas_control recTpSaida = new tipo_saidas_control();
            tpSaida.setId_saida(recTpSaida.recuperaIdTipoSaida(combo_tipo_saida.getSelectedItem().toString()));
            regSaida.setId_tipo_saida(tpSaida);

            // id da pessoa que esta sendo vinculada ao processo de saida
            infoPessoa.setId_pessoa(this.id_pessoa_selecionada);
            regSaida.setId_pessoa(infoPessoa);

            reg_saidas_control atualizar = new reg_saidas_control();
            atualizar.atualizarRegistroSaida(regSaida);

        }


    }//GEN-LAST:event_btn_atualizarActionPerformed

    private void btn_deletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deletarActionPerformed
        // confirma a exclusao do registro de saida
        int ret = JOptionPane.showConfirmDialog(this, "Confirma a exclusão deste registro?", "Confirmação", JOptionPane.YES_NO_CANCEL_OPTION);

        if (ret == 0) {
            // exclui o registro da base de dados
            reg_saidas_control del = new reg_saidas_control();
            del.deletarRegistroSaida(id_reg_saida);
            limparCampos();
            
            // Apos exclusao desativa o btn_deletar e o btn_atualizar
            btn_deletar.setEnabled(false);
            btn_atualizar.setEnabled(false);

        } else {
            btn_localizar_pessoa_empresa.requestFocus();
        }
    }//GEN-LAST:event_btn_deletarActionPerformed

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
            java.util.logging.Logger.getLogger(form_cad_saida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_cad_saida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_cad_saida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_cad_saida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                form_cad_saida dialog = new form_cad_saida(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextArea area_descricao;
    private javax.swing.JButton btn_atualizar;
    private javax.swing.JButton btn_deletar;
    private javax.swing.JButton btn_localizar_pessoa_empresa;
    private javax.swing.JButton btn_salvar_lancamento;
    private javax.swing.JTextField campo_competencia;
    private javax.swing.JTextField campo_cpf_cnpj;
    private javax.swing.JFormattedTextField campo_data_lancamento;
    private javax.swing.JTextField campo_nome_pessoa_empresa;
    private javax.swing.JTextField campo_num_recibo;
    private javax.swing.JFormattedTextField campo_valor_saida;
    private javax.swing.JComboBox<String> combo_tipo_saida;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
