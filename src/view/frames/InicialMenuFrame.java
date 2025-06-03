package view.frames;

import view.dialogs.LoginDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicialMenuFrame extends JFrame implements ActionListener {
    private Container cont;
    private JButton btn_login, btn_registar, btn_db;
    private int width = getWidth();
    private int height = getHeight();

    public InicialMenuFrame(){
        setTitle("Menu Inicial");
        cont = getContentPane();
        cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));
        Dimension buttonSize = new Dimension(200, 40);

        JLabel titulo = new JLabel("Menu Principal");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        cont.add(titulo);
        cont.add(Box.createRigidArea(new Dimension(0, 30)));


        btn_login = new JButton("Login");
        btn_login.setPreferredSize(buttonSize);
        btn_login.setMaximumSize(buttonSize);
        btn_login.setMinimumSize(buttonSize);
        btn_login.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_login.addActionListener(this);
        cont.add(btn_login);
        cont.add(Box.createRigidArea(new Dimension(0, 20)));
        btn_registar = new JButton("Registar Utilizador");
        btn_registar.setPreferredSize(buttonSize);
        btn_registar.setMaximumSize(buttonSize);
        btn_registar.setMinimumSize(buttonSize);
        btn_registar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_registar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cont.add(btn_registar);
        cont.add(Box.createRigidArea(new Dimension(0, 20)));
        btn_db = new JButton("Alterar Dados da Base de Dados");
        btn_db.setPreferredSize(buttonSize);
        btn_db.setMaximumSize(buttonSize);
        btn_db.setMinimumSize(buttonSize);
        btn_db.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_db.setAlignmentX(Component.CENTER_ALIGNMENT);
        cont.add(btn_db);

    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == btn_login) {
            LoginDialog loginDialog = new LoginDialog(this);
            loginDialog.setVisible(true);
        } else if (e.getSource() == btn_registar) {
            System.out.println("Registar Utilizador clicked");
        } else if (e.getSource() == btn_db) {
            System.out.println("Alterar Dados da Base de Dados clicked");
        }

    }
}
