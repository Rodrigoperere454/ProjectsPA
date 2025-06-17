package view.dialogs;

import controller.DBController;
import controller.DBconfig;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class TestDialog extends JDialog implements ActionListener {

    Connection conexao = DBconfig.getConnection();
    DBController DB = new DBController(conexao);

    private JButton botao_teste;
    private JButton botao_cancelar;
    private JTextField field_designacao;
    private JTextField field_descricao;
    private JTextField field_valor;
    private int id_equip;

    public TestDialog(int id_equip) {
        this.id_equip = id_equip;
        setTitle("Registar Teste");
        setLayout(null);
        setSize(400, 500);
        setLocationRelativeTo(null);

        JLabel label_nome = new JLabel("Nome do Teste:");
        label_nome.setBounds(20, 20, 80, 25);
        add(label_nome);

        field_designacao = new JTextField();
        field_designacao.setBounds(120, 20, 200, 25);
        field_designacao.setToolTipText("Insira o nome do teste");
        add(field_designacao);

        JLabel label_descricao = new JLabel("Descricao do Teste:");
        label_descricao.setBounds(20, 60, 80, 25);
        add(label_descricao);

        field_descricao = new JTextField();
        field_descricao.setBounds(120, 60, 200, 25);
        field_descricao.setToolTipText("Insira a descrição do teste");
        add(field_descricao);

        JLabel label_valor = new JLabel("Valor:");
        label_valor.setBounds(20, 100, 80, 25);
        add(label_valor);

        field_valor = new JTextField();
        field_valor.setBounds(120, 100, 200, 25);
        field_valor.setToolTipText("Insira o valor do teste (número inteiro)");
        add(field_valor);


        botao_teste = new JButton("Realizar Teste");
        botao_teste.setBounds(50, 380, 100, 30);
        botao_teste.addActionListener(this);
        botao_teste.setToolTipText("Registar o teste realizado");
        add(botao_teste);

        botao_cancelar = new JButton("Cancelar");
        botao_cancelar.setBounds(200, 380, 100, 30);
        botao_cancelar.setToolTipText("Cancelar e fechar o diálogo");
        botao_cancelar.addActionListener(e -> dispose());
        add(botao_cancelar);

    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == botao_teste){
            int valor = 0;
            String designacao = field_designacao.getText();
            String descricao = field_descricao.getText();
            String valor_str = field_valor.getText();

            if (designacao.isEmpty() || descricao.isEmpty() || valor_str.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                valor = Integer.parseInt(valor_str);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Teste teste = new Teste(id_equip, designacao, descricao, valor);
            try {
                DB.adicionarTeste(teste);
                JOptionPane.showMessageDialog(this, "Teste registado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao registar o teste: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        }

    }
}
