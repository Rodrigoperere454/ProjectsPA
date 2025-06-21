package view.dialogs;

import controller.DBconfig;
import view.frames.InicialMenuFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DBDataDialog extends JDialog implements ActionListener {

    private JButton botao_conectar;
    private JButton botao_cancelar;
    private JTextField dbname;
    private JTextField host;
    private JTextField port;
    private JTextField user;
    private JTextField pass;


    public DBDataDialog() {
        setLayout(null);
        setTitle("Conectar á base de dados");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JLabel label_dbname = new JLabel("Nome da Base de Dados:");
        label_dbname.setBounds(20, 20, 150, 25);
        add(label_dbname);

        dbname = new JTextField();
        dbname.setBounds(180, 20, 200, 25);
        dbname.setToolTipText("Insira o nome da base de dados");
        add(dbname);

        JLabel label_host = new JLabel("Host:");
        label_host.setBounds(20, 60, 150, 25);
        add(label_host);

        host = new JTextField();
        host.setBounds(180, 60, 200, 25);
        host.setToolTipText("Insira o endereço do host da base de dados");
        add(host);

        JLabel label_port = new JLabel("Porta:");
        label_port.setBounds(20, 100, 150, 25);
        add(label_port);

        port = new JTextField();
        port.setBounds(180, 100, 200, 25);
        port.setToolTipText("Insira a porta de conexão da base de dados");
        add(port);

        JLabel label_user = new JLabel("Utilizador:");
        label_user.setBounds(20, 140, 150, 25);
        add(label_user);

        user = new JTextField();
        user.setBounds(180, 140, 200, 25);
        user.setToolTipText("Insira o nome de utilizador para a base de dados");
        add(user);

        JLabel label_pass = new JLabel("Senha:");
        label_pass.setBounds(20, 180, 150, 25);
        add(label_pass);

        pass = new JTextField();
        pass.setBounds(180, 180, 200, 25);
        pass.setToolTipText("Insira a senha para a base de dados");
        add(pass);

        botao_conectar = new JButton("Conectar");
        botao_conectar.setBounds(50, 220, 100, 30);
        botao_conectar.addActionListener(this);
        botao_conectar.setToolTipText("Conectar à base de dados com os dados fornecidos");
        add(botao_conectar);

        botao_cancelar = new JButton("Cancelar");
        botao_cancelar.setBounds(200, 220, 100, 30);
        botao_cancelar.addActionListener(this);
        botao_cancelar.setToolTipText("Cancelar e fechar o diálogo");
        add(botao_cancelar);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botao_conectar) {
            String dbName = dbname.getText();
            String username = user.getText();
            String hostName = host.getText();
            String password = pass.getText();
            String portNumber = port.getText();

            if (dbname.getText().isEmpty() || host.getText().isEmpty() || port.getText().isEmpty() || user.getText().isEmpty() || pass.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                DBconfig.alterarDadosBDini(dbName,username, password, hostName, portNumber);
            }

            if (DBconfig.getConnection() == null) {
                JOptionPane.showMessageDialog(this, "Falha ao conectar à base de dados. Verifique os dados fornecidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Conexão estabelecida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }

        }else if (e.getSource() == botao_cancelar) {
            dispose();
        }

    }
}
