package view.frames;

import controller.DBconfig;
import view.dialogs.LoginDialog;
import view.dialogs.RegistarDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.net.URISyntaxException;

/**
 * Classe que representa o menu inicial da aplicação.
 * Permite ao utilizador escolher entre fazer login, registar um novo utilizador,
 * alterar os dados da base de dados ou sair da aplicação.
 */
public class InicialMenuFrame extends JFrame implements ActionListener {
    private Container cont;
    private JButton btn_login, btn_registar, btn_db, btn_sair;
    private int width = getWidth();
    private int height = getHeight();

    public InicialMenuFrame(){
        setTitle("Menu Inicial");
        cont = getContentPane();
        cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));
        Dimension buttonSize = new Dimension(200, 40);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        btn_login.setToolTipText("Clique neste botão para iniciar sessão");
        cont.add(btn_login);
        cont.add(Box.createRigidArea(new Dimension(0, 20)));
        btn_registar = new JButton("Registar Utilizador");
        btn_registar.setPreferredSize(buttonSize);
        btn_registar.setMaximumSize(buttonSize);
        btn_registar.setMinimumSize(buttonSize);
        btn_registar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_registar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_registar.addActionListener(this);
        btn_registar.setToolTipText("Clique neste botão para registar um novo utilizador");
        cont.add(btn_registar);
        cont.add(Box.createRigidArea(new Dimension(0, 20)));
        btn_db = new JButton("Alterar Dados da Base de Dados");
        btn_db.setPreferredSize(buttonSize);
        btn_db.setMaximumSize(buttonSize);
        btn_db.setMinimumSize(buttonSize);
        btn_db.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_db.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_db.addActionListener(this);
        btn_db.setToolTipText("Clique neste botão para alterar os dados da base de dados");
        cont.add(btn_db);
        cont.add(Box.createRigidArea(new Dimension(0, 20)));
        btn_sair = new JButton("Sair");
        btn_sair.setToolTipText("Clique neste botão para sair da aplicação");
        btn_sair.setPreferredSize(buttonSize);
        btn_sair.setMaximumSize(buttonSize);
        btn_sair.setMinimumSize(buttonSize);
        btn_sair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_sair.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_sair.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Tem a certeza que deseja sair?", "Sair", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else if (response == JOptionPane.NO_OPTION) {
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
        cont.add(btn_sair);

        try{
            String configDir = new File(DBconfig.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI()).getParent();

            String configPath = configDir + File.separator + "config_database.ini";

            JLabel label = new JLabel("Caminho do ficheiro de propriedades: " + configPath);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            cont.add(label);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(InicialMenuFrame.this, "Tem a certeza que deseja sair?", "Sair", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else if (response == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == btn_login) {
            LoginDialog loginDialog = new LoginDialog(this);
            loginDialog.setVisible(true);
        } else if (e.getSource() == btn_registar) {
            RegistarDialog registarDialog = new RegistarDialog(this);
            registarDialog.setVisible(true);
        } else if (e.getSource() == btn_db) {
            System.out.println("Alterar Dados da Base de Dados clicked");
        }
    }
}
