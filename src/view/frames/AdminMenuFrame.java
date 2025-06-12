package view.frames;

import controller.DBController;
import controller.DBconfig;
import model.Utilizador;
import utils.Session;
import view.dialogs.TypeRegistar;
import view.panels.GestorPanel;
import view.panels.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.sql.Connection;

public class AdminMenuFrame extends JFrame implements ActionListener {
    private JButton[] botoes = new JButton[16];
    private String[] labels = {
            "Registar Gestor",
            "Listar Utilizadores",
            "Pesquisar Utilizadores",
            "Ver Notificações",
            "Ver Certificações",
            "Pesquisar Pedidos",
            "Ver Estado de uma Certificação",
            "Aceitar/Recusar Utilizador",
            "Remover Conta",
            "Aceitar Pedido de Certificação",
            "Adicionar Licença",
            "Atribuir Licença a Certificação",
            "Alterar Informações de Utilizadores",
            "Ver Logs da Aplicação",
            "Logout",
    };

    Connection conexao = DBconfig.getConnection();
    DBController DB = new DBController(conexao);
    Utilizador loggedUser = Session.getUtilizador();

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public AdminMenuFrame() {
        setTitle("Menu Administrador");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criar CardLayout e painel principal
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Painel de conteúdo vertical com BoxLayout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel de imagem
        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setPreferredSize(new Dimension(80, 80));
        imagePanel.setMaximumSize(new Dimension(80, 80));
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.RED)); // debug visual
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(imagePanel);

        // Mensagem de boas-vindas e título
        JLabel welcomeMsg = new JLabel("Bem-vindo, " + loggedUser.getName() + "!");
        welcomeMsg.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(welcomeMsg);

        JLabel titulo = new JLabel("Menu Administrador");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titulo);
        contentPanel.add(Box.createVerticalStrut(20));

        // Notificações
        int notificao_porLer = DB.NotificacoesPorler("Gestores");
        if (notificao_porLer > 0) {
            JOptionPane.showMessageDialog(this, "Os gestores têm " + notificao_porLer + " notificações por ler!");
        }

        // Botões
        for (int i = 0; i < labels.length; i++) {
            botoes[i] = new JButton(labels[i]);
            botoes[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            botoes[i].setMaximumSize(new Dimension(300, 40));
            botoes[i].addActionListener(this);
            contentPanel.add(botoes[i]);
            contentPanel.add(Box.createVerticalStrut(10));
        }

        // Adiciona o painel de conteúdo ao mainPanel com CardLayout
        mainPanel.add(contentPanel, "menu");

        // JScrollPane que envolve o mainPanel
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Define o scrollPane como contentPane do frame
        setContentPane(scrollPane);

        // Mostrar painel inicial
        cardLayout.show(mainPanel, "menu");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(mainPanel, "Tem a certeza que deseja sair?", "Sair", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else if (response == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        for (int i = 0; i < botoes.length; i++) {
            if (source == botoes[i]) {
                switch (i) {
                    case 0:
                        TypeRegistar registarGestor = new TypeRegistar("gestor");
                        registarGestor.setVisible(true);
                        break;
                    case 1:
                        GestorPanel listarUtilizadoresPanel = new GestorPanel("listar_utilizadores", cardLayout, mainPanel);
                        mainPanel.add(listarUtilizadoresPanel, "listar_utilizadores");
                        cardLayout.show(mainPanel, "listar_utilizadores");
                        break;
                    case 2:  break;
                    case 3:
                        GestorPanel notificationsPanel= new GestorPanel("notificacao", cardLayout, mainPanel);
                        mainPanel.add(notificationsPanel, "notificacao");
                        cardLayout.show(mainPanel, "notificacao");
                        break;
                    case 4:

                        break;
                    case 5:  break;
                    case 6:  break;
                    case 7:
                        GestorPanel aceitarPanel = new GestorPanel("aceitar", cardLayout, mainPanel);
                        mainPanel.add(aceitarPanel, "aceitar");
                        cardLayout.show(mainPanel, "aceitar");
                        break;
                    case 8:

                    case 9:
                        GestorPanel aceitar_certe_panel = new GestorPanel("aceitar_certe", cardLayout, mainPanel);
                        mainPanel.add(aceitar_certe_panel, "aceitar_certe");
                        cardLayout.show(mainPanel, "aceitar_certe");
                        break;
                    case 10:  break;
                    case 11: break;
                    case 12:  break;
                    case 13:  break;
                    case 14:
                        int response = JOptionPane.showConfirmDialog(this, "Tem a certeza que deseja fazer logout?", "Logout", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            dispose();
                            JFrame newInitialMenu = new InicialMenuFrame();
                            newInitialMenu.setSize(300, 400);
                            newInitialMenu.setVisible(true);
                        }
                        break;
                }
            }
        }
    }

}
