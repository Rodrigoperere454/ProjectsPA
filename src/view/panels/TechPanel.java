package view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TechPanel extends JPanel implements ActionListener {
    public TechPanel(String order) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        switch (order) {
            case "registar":
                JPanel registarPanel = new JPanel();
                registarPanel.setLayout(null); // desativa o layout automático
                registarPanel.setPreferredSize(new Dimension(400, 500));

                JLabel label_registar = new JLabel("Registar Técnico");
                label_registar.setBounds(120, 10, 200, 30);
                label_registar.setFont(label_registar.getFont().deriveFont(18f));
                registarPanel.add(label_registar);

                JLabel label_nome = new JLabel("Nome:");
                label_nome.setBounds(20, 60, 80, 25);
                registarPanel.add(label_nome);

                JTextField field_nome = new JTextField();
                field_nome.setBounds(120, 60, 200, 25);
                registarPanel.add(field_nome);

                JLabel label_username = new JLabel("Username:");
                label_username.setBounds(20, 100, 80, 25);
                registarPanel.add(label_username);

                JTextField field_username = new JTextField();
                field_username.setBounds(120, 100, 200, 25);
                registarPanel.add(field_username);

                JLabel label_password = new JLabel("Password:");
                label_password.setBounds(20, 140, 80, 25);
                registarPanel.add(label_password);

                JTextField field_password = new JTextField();
                field_password.setBounds(120, 140, 200, 25);
                registarPanel.add(field_password);

                JLabel label_email = new JLabel("Email:");
                label_email.setBounds(20, 180, 80, 25);
                registarPanel.add(label_email);

                JTextField field_email = new JTextField();
                field_email.setBounds(120, 180, 200, 25);
                registarPanel.add(field_email);
                break;
            case "notificacoes":
                break;
            case "rem_conta":
                break;
            case "ver_not":
                break;
            case "insp_equi":
                break;
            case "a_n_cert":
                break;
            case "alterar_info":
                break;
            case "cancel_cert":
                break;
            case "":
                break;
        }
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // Implementar a lógica de ação para os botões, se necessário
    }
}
