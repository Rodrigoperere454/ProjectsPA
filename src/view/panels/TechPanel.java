package view.panels;

import controller.DBconfig;
import model.Certificacao;
import model.Equipamento;
import model.Notificacao;
import controller.DBController;
import model.Utilizador;
import utils.Session;
import view.dialogs.TestDialog;
import view.frames.FabMenuFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class TechPanel extends JPanel implements ActionListener {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    Connection conexao = DBconfig.getConnection();
    DBController DB = new DBController(conexao);
    Utilizador user = Session.getUtilizador();

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
            case "insp_equi":
                JLabel label_titulo_equip = new JLabel("Certificar Equipamentos");
                label_titulo_equip.setBounds(20, 0, 200, 25);
                label_titulo_equip.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(label_titulo_equip);

                Equipamento[] equipamentos = DB.listarEquipamentosInterfaceTech(user.getId());
                String[] columnNames_equipamentos = {"ID Equipamento","ID Fabricante", "Marca", "Modelo", "Setor", "Potencia", "Amperagem", "Codigo Sku", "Numero Modelo", "Data Submissão", "Data Certificação"};
                Object[][] data_equipamentos = new Object[equipamentos.length][columnNames_equipamentos.length];
                for(int i = 0; i < equipamentos.length; i++){
                    data_equipamentos[i][0] = equipamentos[i].getId();
                    data_equipamentos[i][1] = equipamentos[i].getId_user();
                    data_equipamentos[i][2] = equipamentos[i].getMarca();
                    data_equipamentos[i][3] = equipamentos[i].getModelo();
                    data_equipamentos[i][4] = equipamentos[i].getSetor_comercial();
                    data_equipamentos[i][5] = equipamentos[i].getPotencia();
                    data_equipamentos[i][6] = equipamentos[i].getAmperagem();
                    data_equipamentos[i][7] = equipamentos[i].getCodigo_sku();
                    data_equipamentos[i][8] = equipamentos[i].getNumero_modelo();
                    data_equipamentos[i][9] = equipamentos[i].getData_submissao();
                    data_equipamentos[i][10] = equipamentos[i].getData_certificacao();
                }

                JTable table_equipamentos = new JTable(data_equipamentos, columnNames_equipamentos);
                table_equipamentos.setPreferredScrollableViewportSize(new Dimension(500, 200));
                table_equipamentos.setFillsViewportHeight(true);
                JScrollPane scrollPaneEquipamentos = new JScrollPane(table_equipamentos);
                add(scrollPaneEquipamentos);

                JButton voltarButton_inspec = new JButton("Voltar");
                voltarButton_inspec.setAlignmentX(Component.CENTER_ALIGNMENT);
                voltarButton_inspec.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
                add(Box.createVerticalStrut(20));
                add(voltarButton_inspec);

                JButton botao_inspecionar = new JButton("Inspecionar Equipamento");
                botao_inspecionar.setAlignmentX(Component.CENTER_ALIGNMENT);
                botao_inspecionar.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                if(table_equipamentos.getSelectedRow() == -1){
                                    JOptionPane.showMessageDialog(mainPanel, "Nenhum Equipamento selecionado, selecione um equipamento para certificar.");
                                } else {
                                    int id_equipamento = (int) table_equipamentos.getValueAt(table_equipamentos.getSelectedRow(), 0);
                                    String marca = (String) table_equipamentos.getValueAt(table_equipamentos.getSelectedRow(), 2);
                                    String modelo = (String) table_equipamentos.getValueAt(table_equipamentos.getSelectedRow(), 3);
                                    String setor = (String) table_equipamentos.getValueAt(table_equipamentos.getSelectedRow(), 4);
                                    int potencia = (int) table_equipamentos.getValueAt(table_equipamentos.getSelectedRow(), 5);
                                    int amperagem = (int) table_equipamentos.getValueAt(table_equipamentos.getSelectedRow(), 6);
                                    int codigo_sku = (int) table_equipamentos.getValueAt(table_equipamentos.getSelectedRow(), 7);
                                    int numero_modelo = (int) table_equipamentos.getValueAt(table_equipamentos.getSelectedRow(), 8);

                                    String message = String.format(
                                            "ID Equipamento: %d\nMarca: %s\nModelo: %s\nSetor: %s\nPotencia: %s\nAmperagem: %s\nCodigo Sku: %s\nNumero Modelo: %s",
                                            id_equipamento, marca, modelo, setor, potencia, amperagem, codigo_sku, numero_modelo
                                    );

                                    JOptionPane.showMessageDialog(mainPanel, message);
                                }
                            }
                        }
                );
                add(botao_inspecionar);
                break;
            case "aceitar_cert":
                JLabel label_titulo_aceitar = new JLabel("Aceitar Certificações");
                label_titulo_aceitar.setBounds(20, 0, 200, 25);
                label_titulo_aceitar.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(label_titulo_aceitar);

                Equipamento[] equipamentos_to_cert = DB.listarEquipamentosInterfaceTechAccept(user.getId());
                String[] columnNames_equipamentos_to_cert = {"ID Equipamento","ID Fabricante", "Marca", "Modelo", "Setor", "Potencia", "Amperagem", "Codigo Sku", "Numero Modelo", "Data Submissão", "Data Certificação"};
                Object[][] data_equipamentos_to_cert = new Object[equipamentos_to_cert.length][columnNames_equipamentos_to_cert.length];
                for(int i = 0; i < equipamentos_to_cert.length; i++){
                    data_equipamentos_to_cert[i][0] = equipamentos_to_cert[i].getId();
                    data_equipamentos_to_cert[i][1] = equipamentos_to_cert[i].getId_user();
                    data_equipamentos_to_cert[i][2] = equipamentos_to_cert[i].getMarca();
                    data_equipamentos_to_cert[i][3] = equipamentos_to_cert[i].getModelo();
                    data_equipamentos_to_cert[i][4] = equipamentos_to_cert[i].getSetor_comercial();
                    data_equipamentos_to_cert[i][5] = equipamentos_to_cert[i].getPotencia();
                    data_equipamentos_to_cert[i][6] = equipamentos_to_cert[i].getAmperagem();
                    data_equipamentos_to_cert[i][7] = equipamentos_to_cert[i].getCodigo_sku();
                    data_equipamentos_to_cert[i][8] = equipamentos_to_cert[i].getNumero_modelo();
                    data_equipamentos_to_cert[i][9] = equipamentos_to_cert[i].getData_submissao();
                    data_equipamentos_to_cert[i][10] = equipamentos_to_cert[i].getData_certificacao();
                }

                JTable table_equipamentos_to_cert = new JTable(data_equipamentos_to_cert, columnNames_equipamentos_to_cert);
                table_equipamentos_to_cert.setPreferredScrollableViewportSize(new Dimension(500, 200));
                table_equipamentos_to_cert.setFillsViewportHeight(true);
                JScrollPane scrollPaneEquipamentos_to_cert = new JScrollPane(table_equipamentos_to_cert);
                add(scrollPaneEquipamentos_to_cert);

                JButton voltarButton_inspec_to_cert = new JButton("Voltar");
                voltarButton_inspec_to_cert.setAlignmentX(Component.CENTER_ALIGNMENT);
                voltarButton_inspec_to_cert.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
                add(Box.createVerticalStrut(20));
                add(voltarButton_inspec_to_cert);

                JButton botao_aceitar_cert = new JButton("Aceitar Certificação");
                botao_aceitar_cert.setAlignmentX(Component.CENTER_ALIGNMENT);
                botao_aceitar_cert.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                double custoDouble = 0;
                                if(table_equipamentos_to_cert.getSelectedRow() == -1){
                                    JOptionPane.showMessageDialog(mainPanel, "Nenhum Equipamento selecionado, selecione um equipamento para aceitar certificação.");
                                } else {
                                    int id_equipamento = (int) table_equipamentos_to_cert.getValueAt(table_equipamentos_to_cert.getSelectedRow(), 0);
                                    String custo =JOptionPane.showInputDialog("Selecione o custo da certificação:");
                                    if (custo == null || custo.isEmpty()) {
                                        JOptionPane.showMessageDialog(mainPanel, "Custo não pode ser vazio.");
                                        return;
                                    }
                                    try {
                                        custoDouble = Double.parseDouble(custo);
                                        if (custoDouble < 0) {
                                            JOptionPane.showMessageDialog(mainPanel, "Custo não pode ser negativo.");
                                            return;
                                        }
                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(mainPanel, "Custo deve ser um número válido.");
                                        return;
                                    }


                                    DB.aceitarCerteficacaoTecnico(id_equipamento, "Finalizado", custoDouble);

                                    JOptionPane.showMessageDialog(mainPanel, "Certificação aceite com sucesso!");
                                }
                            }
                        }
                );
                add(botao_aceitar_cert);

                JButton botao_testes = new JButton("Realizar Testes");
                botao_testes.setAlignmentX(Component.CENTER_ALIGNMENT);
                botao_testes.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                if(table_equipamentos_to_cert.getSelectedRow() == -1){
                                    JOptionPane.showMessageDialog(mainPanel, "Nenhum Equipamento selecionado, selecione um equipamento para realizar testes.");
                                } else {
                                    int id_equipamento = (int) table_equipamentos_to_cert.getValueAt(table_equipamentos_to_cert.getSelectedRow(), 0);
                                    TestDialog testDialog = new TestDialog(id_equipamento);
                                    testDialog.setVisible(true);

                                }
                            }
                        }
                );
                add(botao_testes);
                break;
            case "ver_certificacoes":
                JLabel label_titulo_certificacoes = new JLabel("Certificações Realizadas");
                label_titulo_certificacoes.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(label_titulo_certificacoes);

                Certificacao[] certificacoes = DB.listarCerteficacaoTecnicoInterface(user.getId());
                String[] columnNames_certificacoes = {"ID", "ID Equipamento", "Estado", "Data Realização", "Número Certificação", "Custo"};
                Object[][] data_certificacoes = new Object[certificacoes.length][columnNames_certificacoes.length];
                for(int i = 0; i < certificacoes.length; i++){
                    data_certificacoes[i][0] = certificacoes[i].getId();
                    data_certificacoes[i][1] = certificacoes[i].getId_equipamento();
                    data_certificacoes[i][2] = certificacoes[i].getEstado();
                    data_certificacoes[i][3] = certificacoes[i].getData_realizacao();
                    data_certificacoes[i][4] = certificacoes[i].getNumero_certificacao();
                    data_certificacoes[i][5] = certificacoes[i].getCusto();
                }

                JTable table_certificacoes = new JTable(data_certificacoes, columnNames_certificacoes);
                JScrollPane scrollPaneCertificacoes = new JScrollPane(table_certificacoes);
                scrollPaneCertificacoes.setPreferredSize(new Dimension(500, 300));
                add(scrollPaneCertificacoes);

                JButton voltarButton_cert = new JButton("Voltar");
                voltarButton_cert.setAlignmentX(Component.CENTER_ALIGNMENT);
                voltarButton_cert.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
                voltarButton_cert.setToolTipText("Voltar ao menu principal");
                add(Box.createVerticalStrut(20));
                add(voltarButton_cert);
                break;

            case "cancel_cert":
                JLabel label_titulo_cancelar = new JLabel("Arquivar Certificações");
                label_titulo_cancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(label_titulo_cancelar);
                Certificacao[] certificacoes_cancelar = DB.listarCerteficacaoTecnicoInterface(user.getId());
                String[] columnNames_certificacoes_cancelar = {"ID", "ID Equipamento", "Estado", "Data Realização", "Número Certificação", "Custo"};
                Object[][] data_certificacoes_cancelar = new Object[certificacoes_cancelar.length][columnNames_certificacoes_cancelar.length];
                for(int i = 0; i < certificacoes_cancelar.length; i++){
                    data_certificacoes_cancelar[i][0] = certificacoes_cancelar[i].getId();
                    data_certificacoes_cancelar[i][1] = certificacoes_cancelar[i].getId_equipamento();
                    data_certificacoes_cancelar[i][2] = certificacoes_cancelar[i].getEstado();
                    data_certificacoes_cancelar[i][3] = certificacoes_cancelar[i].getData_realizacao();
                    data_certificacoes_cancelar[i][4] = certificacoes_cancelar[i].getNumero_certificacao();
                    data_certificacoes_cancelar[i][5] = certificacoes_cancelar[i].getCusto();
                }

                JTable table_certificacoes_cancelar = new JTable(data_certificacoes_cancelar, columnNames_certificacoes_cancelar);
                JScrollPane scrollPaneCertificacoes_cancelar = new JScrollPane(table_certificacoes_cancelar);
                scrollPaneCertificacoes_cancelar.setPreferredSize(new Dimension(500, 300));
                add(scrollPaneCertificacoes_cancelar);

                JButton voltarButton_cancelar = new JButton("Voltar");
                voltarButton_cancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
                voltarButton_cancelar.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
                voltarButton_cancelar.setToolTipText("Voltar ao menu principal");
                add(voltarButton_cancelar);

                JButton botao_cancelar_cert = new JButton("Arquivar Certificação");
                botao_cancelar_cert.setAlignmentX(Component.CENTER_ALIGNMENT);
                botao_cancelar_cert.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                if(table_certificacoes_cancelar.getSelectedRow() == -1){
                                    JOptionPane.showMessageDialog(mainPanel, "Nenhuma Certificação selecionada, selecione uma certificação para arquivar.");
                                } else {
                                    int id_certificacao = (int) table_certificacoes_cancelar.getValueAt(table_certificacoes_cancelar.getSelectedRow(), 0);
                                    int certeza = JOptionPane.showConfirmDialog(mainPanel, "Tem a certeza que deseja arquivar a certificação " + id_certificacao + "?", "Arquivar Certificação", JOptionPane.YES_NO_OPTION);
                                    if (certeza == JOptionPane.YES_OPTION) {
                                        DB.enviarNotificacao(user.getId(), "Arquivar Certificação - ID: " + id_certificacao, "tecnico", "Gestores");
                                        JOptionPane.showMessageDialog(mainPanel, "Uma notificação foi enviada para um gestor para arquivar a certificação.");
                                    } else {
                                        JOptionPane.showMessageDialog(mainPanel, "Operação cancelada!");
                                    }
                                }
                            }
                        }
                );
                add(botao_cancelar_cert);

                break;
        }
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // Implementar a lógica de ação para os botões, se necessário
    }
}
