package view.panels;

import controller.DBconfig;
import model.Notificacao;
import controller.DBController;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class TechPanel extends JPanel implements ActionListener {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    Connection conexao = DBconfig.getConnection();
    DBController DB = new DBController(conexao);

    public TechPanel(String order, CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        switch (order) {
            case "notificacoes":
                Notificacao[] notificacoes = DB.listarNotificacoes("Técnicos");
                String[] columns = {"ID", "Tipo", "Mensagem", "Data", "Lida"};
                Object[][] data = new Object[notificacoes.length][columns.length];
                for (int i = 0; i < notificacoes.length; i++) {
                    data[i][0] = notificacoes[i].getIdUtilizador();
                    data[i][1] = notificacoes[i].getTipo();
                    data[i][2] = notificacoes[i].getDescricao();
                    data[i][3] = notificacoes[i].getDataHora();
                    data[i][4] = notificacoes[i].isLida() ? "Sim" : "Não";
                }

                JTable table = new JTable(data, columns);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(500, 300));
                add(scrollPane);

                JButton voltarButton = new JButton("Voltar");
                voltarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                voltarButton.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
                add(Box.createVerticalStrut(20));
                add(voltarButton);
                break;
            case "rem_conta":
                JLabel label_remover = new JLabel("Remover Conta");
                label_remover.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(label_remover);
                Utilizador[] utilizadores = DB.listarUtilizadoresInterface();
                String[] columnNames = {"ID", "Nome", "Username", "Email", "Tipo", "Estado", "Remover"};
                Object[][] dataRemover = new Object[utilizadores.length][columnNames.length];
                for (int i = 0; i < utilizadores.length; i++) {
                    dataRemover[i][0] = utilizadores[i].getId();
                    dataRemover[i][1] = utilizadores[i].getName();
                    dataRemover[i][2] = utilizadores[i].getUsername();
                    dataRemover[i][3] = utilizadores[i].getEmail();
                    dataRemover[i][4] = utilizadores[i].getType();
                    dataRemover[i][5] = utilizadores[i].getEstado();
                    dataRemover[i][6] = "Remover";
                };

                JButton botao_remover = new JButton("Remover Conta");
                break;
            case "ver_not":
                break;
            case "insp_equi":
                break;
            case "a_n_cert":
                break;
            case "alterar_info":
                break;
            case "cancel_cert":
                break;
            case "logout":
                break;
        }
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // Implementar a lógica de ação para os botões, se necessário
    }
}
