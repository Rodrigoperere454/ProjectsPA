package view.frames;

import controller.DBController;
import controller.DBconfig;
import view.panels.GestorPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AdminMenuFrame extends JFrame implements ActionListener {
    private JButton[] botoes = new JButton[16];
    private String[] labels = {
            "1 - Registar Gestor",
            "2 - Listar Utilizadores",
            "3 - Pesquisar Utilizadores",
            "4 - Ver Notificações",
            "5 - Ver Certificações",
            "6 - Pesquisar Pedidos",
            "7 - Ver Estado de uma Certificação",
            "8 - Aceitar/Recusar Utilizador",
            "9 - Remover Conta",
            "10 - Aceitar Pedido de Certificação",
            "11 - Adicionar Licença",
            "12 - Atribuir Licença a Certificação",
            "13 - Alterar Informações de Utilizadores",
            "14 - Ver Logs da Aplicação",
            "0 - Sair"
    };

    Connection conexao = DBconfig.getConnection();
    DBController DB = new DBController(conexao);

    public AdminMenuFrame() {
        setTitle("Menu Administrador");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        int notificao_porLer = DB.NotificacoesPorler("Gestores");
        if (notificao_porLer > 0) {
            JOptionPane.showMessageDialog(this, "Os gestores tem  " + notificao_porLer + " notificações por ler!");
        }

        JLabel titulo = new JLabel("Menu Administrador");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(titulo);
        add(Box.createVerticalStrut(20));

        for (int i = 0; i < labels.length; i++) {
            botoes[i] = new JButton(labels[i]);
            botoes[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            botoes[i].setMaximumSize(new Dimension(300, 40));
            botoes[i].addActionListener(this);
            add(botoes[i]);
            add(Box.createVerticalStrut(10));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        for (int i = 0; i < botoes.length; i++) {
            if (source == botoes[i]) {
                switch (i) {
                    case 0:
                        GestorPanel registarPanel= new GestorPanel("registar");
                        this.setContentPane(registarPanel);
                        this.revalidate();
                        this.repaint();
                        break;
                    case 1:  break;
                    case 2:  break;
                    case 3:
                        GestorPanel notificationsPanel= new GestorPanel("notificacao");
                        this.setContentPane(notificationsPanel);
                        this.revalidate();
                        this.repaint();
                        break;
                    case 4:

                        break;
                    case 5:  break;
                    case 6:  break;
                    case 7:  break;
                    case 8:  break;
                    case 9:  break;
                    case 10:  break;
                    case 11: break;
                    case 12:  break;
                    case 13:  break;
                    case 14: dispose(); break;
                }
            }
        }
    }

}
