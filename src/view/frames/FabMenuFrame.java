package view.frames;

import controller.DBController;
import controller.DBconfig;
import model.Utilizador;
import utils.Session;
import view.dialogs.InsertEquipDialog;
import view.dialogs.TypeRegistar;
import view.panels.FabPanel;
import view.panels.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            "Pesquisar Equipamentos",
            "Pesquisar Pedidos Certificação",
            "Ver Estado de uma Certificação",
            "Remover Conta",
            "Alterar Minhas Infos",
            "Logout"
    };

    public FabMenuFrame() {
        setTitle("Menu Fabricante");
        setSize(400, 600);
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
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.RED)); // debug visual
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(imagePanel);

        JLabel titulo = new JLabel("Menu Administrador");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel welcomeMsg = new JLabel("Bem-vindo, " + loggedUser.getName() + "!");
        welcomeMsg.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(welcomeMsg);
        add(titulo);
        add(Box.createVerticalStrut(20));

        for (int i = 0; i < labels.length; i++) {
            botoes[i] = new JButton(labels[i]);
            botoes[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            botoes[i].setMaximumSize(new Dimension(300, 40));
            botoes[i].addActionListener(this);
            menuPanel.add(botoes[i]);
            menuPanel.add(Box.createVerticalStrut(10));
        }

        mainPanel.add(menuPanel, "menu");
        setContentPane(mainPanel);
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
                        FabPanel certeficarEquipamentosPanel = new FabPanel(this, "pedir_certi");
                        this.setContentPane(certeficarEquipamentosPanel);
                        this.revalidate();
                        this.repaint();
                        break;
                    case 3:
                        FabPanel listarEquipamentosPanel = new FabPanel(this, "listar_equip");
                        this.setContentPane(listarEquipamentosPanel);
                        this.revalidate();
                        this.repaint();
                        break;
                    case 4:  break;
                    case 5:  break;
                    case 6:  break;
                    case 7:  break;
                    case 8: break;
                    case 9: break;
                    case 10:
                        int response = JOptionPane.showConfirmDialog(this, "Tem a certeza que deseja fazer logout?", "Logout", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            dispose();
                            new InicialMenuFrame().setVisible(true);
                        }
                        break;
                }
            }
        }
    }
}
