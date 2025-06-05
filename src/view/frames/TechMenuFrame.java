package view.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.dialogs.TypeRegistar;
import view.panels.*;

public class TechMenuFrame extends JFrame implements ActionListener {

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

        JLabel titulo = new JLabel("Menu Técnico");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(Box.createVerticalStrut(20));
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
                        TypeRegistar registarDialog = new TypeRegistar("tecnico");
                        registarDialog.setVisible(true);
                        break;
                    case 1:
                        TechPanel notificacoesPanel = new TechPanel("notificacoes", cardLayout, mainPanel);
                        mainPanel.add(notificacoesPanel, "notificacoes");
                        cardLayout.show(mainPanel, "notificacoes");
                        break;
                    case 2:
                        TechPanel removerContaPanel = new TechPanel("rem_conta", cardLayout, mainPanel);
                        mainPanel.add(removerContaPanel, "rem_conta");
                        cardLayout.show(mainPanel, "rem_conta");
                        break;
                    case 8:
                        int response = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Logout", JOptionPane.YES_NO_OPTION);
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


