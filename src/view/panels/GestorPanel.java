package view.panels;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GestorPanel extends JPanel implements ActionListener {

    private JTextField field_nome;
    private JTextField field_username;
    private JTextField field_password;
    private JTextField field_email;


    public GestorPanel(String order) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        switch (order) {
            case "registar":
                JLabel label_registar = new JLabel("Registar Gestor");
                label_registar.setAlignmentX(CENTER_ALIGNMENT);
                label_registar.setFont(label_registar.getFont().deriveFont(18f));
                add(label_registar);

                JLabel label_nome = new JLabel("Nome:");
                label_nome.setBounds(20, 20, 80, 25);
                add(label_nome);

                field_nome = new JTextField();
                field_nome.setBounds(120, 20, 200, 25);
                add(field_nome);

                JLabel label_username = new JLabel("Username:");
                label_username.setBounds(20, 60, 80, 25);
                add(label_username);

                field_username = new JTextField();
                field_username.setBounds(120, 60, 200, 25);
                add(field_username);

                JLabel label_password = new JLabel("Password:");
                label_password.setBounds(20, 100, 80, 25);
                add(label_password);

                field_password = new JTextField();
                field_password.setBounds(120, 100, 200, 25);
                add(field_password);

                JLabel label_email = new JLabel("Email:");
                label_email.setBounds(20, 140, 80, 25);
                add(label_email);

                field_email = new JTextField();
                field_email.setBounds(120, 140, 200, 25);
                add(field_email);

                break;

        }
    }


    public void actionPerformed(java.awt.event.ActionEvent e) {
        // Implementar ações para os botões do painel
        // Exemplo: JButton source = (JButton) e.getSource();
        // switch (source.getText()) { ... }
    }
}
