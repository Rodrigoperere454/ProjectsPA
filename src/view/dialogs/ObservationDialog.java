package view.dialogs;

import controller.DBController;
import controller.DBconfig;
import model.Observacao;
import model.Utilizador;
import utils.Session;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class ObservationDialog extends JDialog implements ActionListener {

    Connection conexao = DBconfig.getConnection();
    DBController DB = new DBController(conexao);

    private JButton adicionar_observacao_botao;
    private JButton cancelar_butao;
    private JTextField field_observacao;
    private JTextField field_assunto;
    private JFrame parentFrame;
    Utilizador user = Session.getUtilizador();
    private String obs_info;
    public ObservationDialog(JFrame parent, String obs_info) {
        super(parent, "Adicionar Observação", true);
        setLayout(null);
        this.obs_info = obs_info;
        this.parentFrame = parent;
        setSize(400, 200);
        setLocationRelativeTo(parent);

        JLabel label_assunto = new JLabel("Assunto:");
        label_assunto.setBounds(20, 60, 80, 25);
        add(label_assunto);

        field_assunto = new JTextField();
        field_assunto.setBounds(120, 60, 200, 25);
        field_assunto.setToolTipText("Insira o assunto da observação");
        add(field_assunto);

        JLabel label_observacao = new JLabel("Observação:");
        label_observacao.setBounds(20, 20, 80, 25);
        add(label_observacao);

        field_observacao = new JTextField();
        field_observacao.setBounds(120, 20, 200, 25);
        field_observacao.setToolTipText("Insira a observação");
        add(field_observacao);

        adicionar_observacao_botao = new JButton("Adicionar Observação");
        adicionar_observacao_botao.setBounds(50, 100, 150, 30);
        adicionar_observacao_botao.addActionListener(this);
        adicionar_observacao_botao.setToolTipText("Adicionar a observação");
        add(adicionar_observacao_botao);

        cancelar_butao = new JButton("Cancelar");
        cancelar_butao.setBounds(220, 100, 100, 30);
        cancelar_butao.addActionListener(this);
        cancelar_butao.setToolTipText("Cancelar a adição da observação");
        add(cancelar_butao);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(adicionar_observacao_botao)) {
            String observacao = field_observacao.getText();
            String assunto = field_assunto.getText();
            if (observacao.isEmpty() || assunto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Observacao obs = new Observacao(observacao, user.getId(), assunto, obs_info);
            boolean success = DB.inserirObservacao(obs);
            if (success) {
                JOptionPane.showMessageDialog(this, "Observação adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar observação.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        } else if (e.getSource().equals(cancelar_butao)) {
            dispose();
        }
    }
}
