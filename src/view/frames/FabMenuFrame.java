package view.frames;

import controller.DBController;
import controller.DBconfig;
import model.Utilizador;
import utils.Session;
import view.dialogs.InsertEquipDialog;
import view.dialogs.ObservationDialog;
import view.dialogs.TypeRegistar;
import view.panels.FabPanel;
import view.panels.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.sql.Connection;

public class FabMenuFrame extends JFrame implements ActionListener {
    private final Connection connection = DBconfig.getConnection();
    private final DBController DB = new DBController(connection);

    Utilizador loggedUser = Session.getUtilizador();

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JButton[] botoes = new JButton[11];
    private String[] labels = {
            "Registar Fabricante",
            "Adicionar Equipamento",
            "Pedir Certificação",
            "Listar Equipamentos",
            "Listar Pedidos Feitos",
            "Ver Estado de uma Certificação",
            "Remover Conta",
            "Observações",
            "Logout"
    };

    public FabMenuFrame() {
        setTitle("Menu Fabricante");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // CardLayout principal
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Painel de menu principal
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel da imagem
        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setPreferredSize(new Dimension(80, 80));
        imagePanel.setMaximumSize(new Dimension(80, 80));
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(imagePanel);

        JLabel titulo = new JLabel("Menu Fabricante");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel welcomeMsg = new JLabel("Bem-vindo, " + loggedUser.getName() + "!");
        welcomeMsg.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeMsg.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(welcomeMsg);
        contentPanel.add(titulo);
        contentPanel.add(Box.createVerticalStrut(20));

        for (int i = 0; i < labels.length; i++) {
            botoes[i] = new JButton(labels[i]);
            botoes[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            botoes[i].setMaximumSize(new Dimension(300, 40));
            botoes[i].addActionListener(this);
            contentPanel.add(botoes[i]);
            contentPanel.add(Box.createVerticalStrut(10));
        }

        // Adicionar o painel de conteúdo ao mainPanel com CardLayout
        mainPanel.add(contentPanel, "menu");

        // JScrollPane que envolve o mainPanel
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        setContentPane(scrollPane);

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
                        TypeRegistar registarDialog = new TypeRegistar("fabricante");
                        registarDialog.setVisible(true);
                        break;
                    case 1:
                        InsertEquipDialog inserir_equip = new InsertEquipDialog(this);
                        inserir_equip.setVisible(true);
                        break;
                    case 2:
                        FabPanel certeficarEquipamentosPanel = new FabPanel("pedir_certi", cardLayout, mainPanel);
                        mainPanel.add(certeficarEquipamentosPanel, "pedir_certi");
                        cardLayout.show(mainPanel, "pedir_certi");
                        break;
                    case 3:
                        FabPanel listarEquipamentosPanel = new FabPanel("listar_equip", cardLayout, mainPanel);
                        mainPanel.add(listarEquipamentosPanel, "listar_equip");
                        cardLayout.show(mainPanel, "listar_equip");
                        break;
                    case 4:
                        FabPanel pedidosFeitosPanel = new FabPanel("pedidos_feitos", cardLayout, mainPanel);
                        mainPanel.add(pedidosFeitosPanel, "pedidos_feitos");
                        cardLayout.show(mainPanel, "pedidos_feitos");
                        break;
                    case 5:
                        FabPanel verEstadoCertificacaoPanel = new FabPanel("ver_estado_certificacao", cardLayout, mainPanel);
                        mainPanel.add(verEstadoCertificacaoPanel, "ver_estado_certificacao");
                        cardLayout.show(mainPanel, "ver_estado_certificacao");
                        break;
                    case 6:
                        int confirm = JOptionPane.showConfirmDialog(this, "Tem a certeza que deseja remover a sua conta?", "Remover Conta", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            try {
                                DB.enviarNotificacao(loggedUser.getId(), "remover conta", "fabricante", "Gestores");
                                JOptionPane.showMessageDialog(this, "Notificação Enviada para um gestor. Brevemente entraremos em contato.");
                                dispose();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Erro ao remover conta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        }else if (confirm == JOptionPane.NO_OPTION) {
                            JOptionPane.showMessageDialog(this, "Operação cancelada.");
                        }
                        break;
                    case 7:
                        ObservationDialog dialog = new ObservationDialog(FabMenuFrame.this, "Menu Fabricante");
                        dialog.setSize(400, 200);
                        dialog.setLocationRelativeTo(FabMenuFrame.this);
                        dialog.setVisible(true);
                        break;
                    case 8:
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
