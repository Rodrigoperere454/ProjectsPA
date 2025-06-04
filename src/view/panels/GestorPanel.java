package view.panels;

import controller.DBController;
import controller.DBconfig;
import model.Notificacao;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.Connection;
import java.util.Arrays;

public class GestorPanel extends JPanel implements ActionListener {

    private JTextField field_nome;
    private JTextField field_username;
    private JTextField field_password;
    private JTextField field_email;
    private JButton botao_registar;

    Connection conexao = DBconfig.getConnection();
    DBController DB = new DBController(conexao);


    public GestorPanel(String order) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        switch (order) {
            case "registar":
                JLabel label_registar = new JLabel("Registar Gestor");
                label_registar.setBounds(20, 0, 200, 25);
                label_registar.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(label_registar);

                JLabel label_nome = new JLabel("Nome:");
                label_nome.setBounds(20, 30, 80, 25);
                add(label_nome);

                field_nome = new JTextField();
                field_nome.setBounds(120, 30, 200, 25);
                add(field_nome);

                JLabel label_username = new JLabel("Username:");
                label_username.setBounds(20, 70, 80, 25);
                add(label_username);

                field_username = new JTextField();
                field_username.setBounds(120, 70, 200, 25);
                add(field_username);

                JLabel label_password = new JLabel("Password:");
                label_password.setBounds(20, 110, 80, 25);
                add(label_password);

                field_password = new JTextField();
                field_password.setBounds(120, 110, 200, 25);
                add(field_password);

                JLabel label_email = new JLabel("Email:");
                label_email.setBounds(20, 150, 80, 25);
                add(label_email);

                field_email = new JTextField();
                field_email.setBounds(120, 150, 200, 25);
                add(field_email);

                botao_registar = new JButton("Registar");
                botao_registar.setBounds(120, 190, 100, 30);
                botao_registar.addActionListener(this);
                add(botao_registar);

                break;
            case "notificacao":
                JLabel label_notificacao = new JLabel("Notificações");
                label_notificacao.setBounds(20, 0, 200, 25);
                label_notificacao.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(label_notificacao);


                DB.lerNotificacoes("Gestores");
                Notificacao[] notificacoes = DB.listarNotificacoes("Gestores");
                System.out.println(Arrays.toString(notificacoes));
                JList lista_notificacoes = new JList(notificacoes);
                add(lista_notificacoes);

                break;
        }
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource().equals(botao_registar)) {
            String nome = field_nome.getText();
            String username = field_username.getText();
            String password = field_password.getText();
            String email = field_email.getText();

            Utilizador gestor = new Utilizador(nome, username, password, email, "Gestor");
            boolean sucesso = DB.inserirUtilizador(gestor);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Gestor " + nome + " registrado com sucesso!");
                return;
            }
            JOptionPane.showMessageDialog(this, "Erro ao registar Gestor. Verifique os dados inseridos.");

        }
    }
}
