package view.dialogs;

import controller.*;
import model.Utilizador;
import utils.Session;
import view.frames.AdminMenuFrame;
import view.frames.FabMenuFrame;
import view.frames.TechMenuFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;


public class LoginDialog extends JDialog implements ActionListener {

    Connection conexao = DBconfig.getConnection();
    DBController DB = new DBController(conexao);

    private JButton botao_login;
    private JButton botao_cancelar;
    private JTextField field_username;
    private JTextField field_password;
    private JFrame parentFrame;


    public LoginDialog(JFrame parent) {
        super(parent, "Login", true);
        setLayout(null);
        this.parentFrame = parent;
        setSize(300, 200);
        setLocationRelativeTo(parent);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 20, 80, 25);
        add(usernameLabel);

        field_username = new JTextField();
        field_username.setBounds(100, 20, 160, 25);
        field_username.setToolTipText("Insira o seu username");
        add(field_username);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 60, 80, 25);
        add(passwordLabel);

        field_password = new JPasswordField();
        field_password.setBounds(100, 60, 160, 25);
        field_password.setToolTipText("Insira a sua password");
        add(field_password);

        botao_login = new JButton("Login");
        botao_login.setBounds(50, 100, 80, 25);
        botao_login.addActionListener(this);
        botao_login.setToolTipText("Entrar na conta");
        add(botao_login);

        botao_cancelar = new JButton("Cancel");
        botao_cancelar.setBounds(150, 100, 80, 25);
        botao_cancelar.addActionListener(this);
        botao_cancelar.setToolTipText("Voltar ao menu principal");
        add(botao_cancelar);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(botao_login)) {
            String username = field_username.getText();
            String password = field_password.getText();
            Utilizador utilizador = DB.loginUtilizador(username, password);
            if (utilizador != null) {
                Session.setUtilizador(utilizador); // para manter e aceder ao utilizador logado em outras partes do programa
                JOptionPane.showMessageDialog(this, "Login successful! Welcome " + utilizador.getUsername() + "!");
                if (utilizador.getType().equalsIgnoreCase("tecnico")) {
                    TechMenuFrame menuTech = new TechMenuFrame();
                    menuTech.setVisible(true);
                } else if (utilizador.getType().equalsIgnoreCase("fabricante")) {
                    FabMenuFrame menuFab = new FabMenuFrame();
                    menuFab.setVisible(true);
                } else {
                    AdminMenuFrame menuAdmin = new AdminMenuFrame();
                    menuAdmin.setVisible(true);
                }
                dispose();
                this.parentFrame.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Login failed! Please check your username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        }else if(e.getSource().equals(botao_cancelar)){
            dispose();
        }
    }
}
