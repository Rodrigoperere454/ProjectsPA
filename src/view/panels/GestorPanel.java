package view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GestorPanel extends JPanel implements ActionListener {

    private JTextField field_nome;
    private JTextField field_username;
    private JTextField field_password;
    private JTextField field_email;


    public GestorPanel(String order) {
        setLayout(null);

        if (order.equals("registar")) {
            JLabel label_registar = new JLabel("Registar Gestor");
            label_registar.setAlignmentX(CENTER_ALIGNMENT);
            add(label_registar);

            JLabel label_nome = new JLabel("Nome:");
            add(label_nome);

            field_nome = new JTextField();
            field_nome.setBounds(20, 20, 80, 25);
            field_nome.setMaximumSize(field_nome.getPreferredSize());
            add(field_nome);

            JLabel label_username = new JLabel("Username:");
            add(label_username);
            field_username = new JTextField();
            field_username.setBounds(20, 60, 80, 25);
            field_username.setMaximumSize(field_username.getPreferredSize());
            add(field_username);
            JLabel label_password = new JLabel("Password:");
            add(label_password);
            field_password = new JTextField();
            field_password.setBounds(20, 100, 80, 25);
            field_password.setMaximumSize(field_password.getPreferredSize());
            add(field_password);
            JLabel label_email = new JLabel("Email:");
            add(label_email);
            field_email = new JTextField();
            field_email.setBounds(20, 140, 80, 25);
            field_email.setMaximumSize(field_email.getPreferredSize());
            add(field_email);
            JButton botao_registar = new JButton("Registar");
            botao_registar.setBounds(20, 180, 80, 25);
            botao_registar.addActionListener(this);
            add(botao_registar);

        }
    }



    public void actionPerformed(java.awt.event.ActionEvent e) {
    }
}
