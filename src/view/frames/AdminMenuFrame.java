package view.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public AdminMenuFrame() {
        setTitle("Menu Administrador");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

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
                    case 0: registarGestor(); break;
                    case 1: listarUtilizadores(); break;
                    case 2: pesquisarUtilizadores(); break;
                    case 3: verNotificacoes(); break;
                    case 4: verCertificacoes(); break;
                    case 5: pesquisarPedidos(); break;
                    case 6: verEstadoCertificacao(); break;
                    case 7: aceitarRecusarUtilizador(); break;
                    case 8: removerConta(); break;
                    case 9: aceitarPedidoCertificacao(); break;
                    case 10: adicionarLicenca(); break;
                    case 11: atribuirLicencaCertificacao(); break;
                    case 12: alterarInformacoesUtilizadores(); break;
                    case 13: verLogsAplicacao(); break;
                    case 14: sair(); break;
                }
            }
        }
    }
    private void registarGestor() {
        // Implementar lógica para registar gestor
        System.out.println("Registar Gestor");
    }
    private void listarUtilizadores() {
        // Implementar lógica para listar utilizadores
        System.out.println("Listar Utilizadores");
    }
    private void pesquisarUtilizadores() {
        // Implementar lógica para pesquisar utilizadores
        System.out.println("Pesquisar Utilizadores");
    }
    private void verNotificacoes() {
        // Implementar lógica para ver notificações
        System.out.println("Ver Notificações");
    }
    private void verCertificacoes() {
        // Implementar lógica para ver certificações
        System.out.println("Ver Certificações");
    }
    private void pesquisarPedidos() {
        // Implementar lógica para pesquisar pedidos
        System.out.println("Pesquisar Pedidos");
    }
    private void verEstadoCertificacao() {
        // Implementar lógica para ver estado de certificação
        System.out.println("Ver Estado de Certificação");
    }
    private void aceitarRecusarUtilizador() {
        // Implementar lógica para aceitar/recusar utilizador
        System.out.println("Aceitar/Recusar Utilizador");
    }
    private void removerConta() {
        // Implementar lógica para remover conta
        System.out.println("Remover Conta");
    }
    private void aceitarPedidoCertificacao() {
        // Implementar lógica para aceitar pedido de certificação
        System.out.println("Aceitar Pedido de Certificação");
    }
    private void adicionarLicenca() {
        // Implementar lógica para adicionar licença
        System.out.println("Adicionar Licença");
    }
    private void atribuirLicencaCertificacao() {
        // Implementar lógica para atribuir licença a certificação
        System.out.println("Atribuir Licença a Certificação");
    }
    private void alterarInformacoesUtilizadores() {
        // Implementar lógica para alterar informações de utilizadores
        System.out.println("Alterar Informações de Utilizadores");
    }
    private void verLogsAplicacao() {
        // Implementar lógica para ver logs da aplicação
        System.out.println("Ver Logs da Aplicação");
    }
    private void sair() {
        dispose(); // Fecha o frame
    }
}
