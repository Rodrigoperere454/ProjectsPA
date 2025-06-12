package view.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.sql.Connection;

import controller.DBController;
import controller.DBconfig;
import model.Utilizador;
import utils.Session;
import view.dialogs.TypeRegistar;
import view.panels.*;

public class TechMenuFrame extends JFrame implements ActionListener {
    private final Connection connection = DBconfig.getConnection();
    private final DBController DB = new DBController(connection);

    Utilizador loggedUser = Session.getUtilizador();

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JButton[] botoes = new JButton[9];
    private String[] labels = {
            "Registar Técnico",
            "Ver Notificações",
            "Remover Conta",
            "Ver Certificações",
            "Inspecionar Equipamento",
            "Aceitar/Negar Certificação",
            "Alterar Minhas Infos",
            "Cancelar Certificação",
            "Logout"
    };

    public TechMenuFrame() {
        setTitle("Menu Técnico");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Painel de menu principal
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        // Painel da imagem
        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setPreferredSize(new Dimension(80, 80));
        imagePanel.setMaximumSize(new Dimension(80, 80));
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adicionar imagem ao menuPanel
        menuPanel.add(imagePanel);

        JLabel titulo = new JLabel("Menu Técnico");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeMsg = new JLabel("Bem-vindo, " + loggedUser.getName() + "!");
        welcomeMsg.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeMsg.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(welcomeMsg);
        menuPanel.add(titulo);
        menuPanel.add(Box.createVerticalStrut(20));

        for (int i = 0; i < labels.length; i++) {
            botoes[i] = new JButton(labels[i]);
            botoes[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            botoes[i].setMaximumSize(new Dimension(300, 40));
            botoes[i].addActionListener(this);
            menuPanel.add(botoes[i]);
            menuPanel.add(Box.createVerticalStrut(10));
        }

        // Adicionar o menuPanel ao mainPanel (CardLayout)
        mainPanel.add(menuPanel, "menu");

        // Criar JScrollPane com o mainPanel
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Definir o JScrollPane como content pane do JFrame
        setContentPane(scrollPane);

        // Mostrar o card "menu"
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
                        TypeRegistar registarDialog = new TypeRegistar("tecnico");
                        registarDialog.setVisible(true);
                        break;
                    case 1:
                        TechPanel notificacoesPanel = new TechPanel("notificacoes", cardLayout, mainPanel);
                        mainPanel.add(notificacoesPanel, "notificacoes");
                        cardLayout.show(mainPanel, "notificacoes");
                        break;
                    case 2:
                        int rem_conta = JOptionPane.showConfirmDialog(this, "Vai realizar um pedido de remoção de conta. Tem a certeza?", "Remover Conta", JOptionPane.YES_NO_OPTION);
                        if (rem_conta == JOptionPane.YES_OPTION) {
                            int id_user = loggedUser.getId();
                            DB.enviarNotificacao(id_user, "remover conta", "tecnico", "Gestores");
                            JOptionPane.showMessageDialog(this, "Pedido de remoção de conta enviado com sucesso!");
                        }
                        break;

                    case 4:
                        TechPanel insp_equiPanel = new TechPanel("insp_equi", cardLayout, mainPanel);
                        mainPanel.add(insp_equiPanel, "insp_equi");
                        cardLayout.show(mainPanel, "insp_equi");
                        break;
                    case 5:
                        TechPanel aceitarPanel = new TechPanel("aceitar_cert", cardLayout, mainPanel);
                        mainPanel.add(aceitarPanel, "aceitar_cert");
                        cardLayout.show(mainPanel, "aceitar_cert");
                        break;
                    case 8:
                        int response = JOptionPane.showConfirmDialog(this, "Tem a certeza que deseja fazer logout?", "Logout", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            Session.limparSessao();
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


