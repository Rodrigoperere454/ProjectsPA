package view.frames;

import view.dialogs.TypeRegistar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FabMenuFrame extends JFrame implements ActionListener {
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

        JLabel titulo = new JLabel("Menu Fabricante");
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
            add(botoes[i]);
            add(Box.createVerticalStrut(10));
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

                        break;
                    case 2: pedirCertificacao(); break;
                    case 3: listarEquipamentos(); break;
                    case 4: listarPedidosFeitos(); break;
                    case 5: pesquisarEquipamentos(); break;
                    case 6: pesquisarPedidosCertificacao(); break;
                    case 7: verEstadoCertificacao(); break;
                    case 8: removerConta(); break;
                    case 9: alterarMinhasInfos(); break;
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

    private void registarFabricante() {
        // Implementar lógica para registar fabricante
        System.out.println("Registar Fabricante");
    }
    private void adicionarEquipamento() {
        // Implementar lógica para adicionar equipamento
        System.out.println("Adicionar Equipamento");
    }
    private void pedirCertificacao() {
        // Implementar lógica para pedir certificação
        System.out.println("Pedir Certificação");
    }
    private void listarEquipamentos() {
        // Implementar lógica para listar equipamentos
        System.out.println("Listar Equipamentos");
    }
    private void listarPedidosFeitos() {
        // Implementar lógica para listar pedidos feitos
        System.out.println("Listar Pedidos Feitos");
    }
    private void pesquisarEquipamentos() {
        // Implementar lógica para pesquisar equipamentos
        System.out.println("Pesquisar Equipamentos");
    }
    private void pesquisarPedidosCertificacao() {
        // Implementar lógica para pesquisar pedidos de certificação
        System.out.println("Pesquisar Pedidos Certificação");
    }
    private void verEstadoCertificacao() {
        // Implementar lógica para ver estado de uma certificação
        System.out.println("Ver Estado de uma Certificação");
    }
    private void removerConta() {
        // Implementar lógica para remover conta
        System.out.println("Remover Conta");
    }
    private void alterarMinhasInfos() {
        // Implementar lógica para alterar informações
        System.out.println("Alterar Minhas Infos");
    }
    private void sair() {
        dispose(); // Fecha o frame
    }
}
