package view.frames;

import view.dialogs.TypeRegistar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FabMenuFrame extends JFrame implements ActionListener {
    private JButton[] botoes = new JButton[11];
    private String[] labels = {
            "1 - Registar Fabricante",
            "2 - Adicionar Equipamento",
            "3 - Pedir Certificação",
            "4 - Listar Equipamentos",
            "5 - Listar Pedidos Feitos",
            "6 - Pesquisar Equipamentos",
            "7 - Pesquisar Pedidos Certificação",
            "8 - Ver Estado de uma Certificação",
            "9 - Remover Conta",
            "10 - Alterar Minhas Infos",
            "0 - Sair"
    };

    public FabMenuFrame() {
        setTitle("Menu Fabricante");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Menu Fabricante");
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
                        TypeRegistar registarDialog = new TypeRegistar("fabricante");
                        registarDialog.setVisible(true);
                    break;
                    case 1: adicionarEquipamento(); break;
                    case 2: pedirCertificacao(); break;
                    case 3: listarEquipamentos(); break;
                    case 4: listarPedidosFeitos(); break;
                    case 5: pesquisarEquipamentos(); break;
                    case 6: pesquisarPedidosCertificacao(); break;
                    case 7: verEstadoCertificacao(); break;
                    case 8: removerConta(); break;
                    case 9: alterarMinhasInfos(); break;
                    case 10: sair(); break;
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
