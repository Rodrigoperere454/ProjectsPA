package view.panels;

import controller.DBController;
import controller.DBconfig;
import model.Certificacao;
import model.Equipamento;
import model.Utilizador;
import utils.Session;
import view.frames.AdminMenuFrame;
import view.frames.FabMenuFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class FabPanel extends JPanel implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    Connection conexao = DBconfig.getConnection();
    DBController DB = new DBController(conexao);
    Utilizador user = Session.getUtilizador();

    public FabPanel(String order, CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = new CardLayout();
        this.mainPanel = mainPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        switch (order) {
            case "listar_equip":
                JLabel lblEquip = new JLabel("Equipamentos");
                lblEquip.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(lblEquip);

                Equipamento[] listar_equipamentos = DB.listarEquipamentosInterface(user.getId());
                JList<Equipamento> list = new JList<>(listar_equipamentos);
                JScrollPane scroll = new JScrollPane(list);
                scroll.setPreferredSize(new Dimension(500, 300));
                add(scroll);

                JButton botao_voltar = new JButton("Voltar");
                botao_voltar.setAlignmentX(Component.CENTER_ALIGNMENT);
                botao_voltar.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
                add(Box.createVerticalStrut(20));
                add(botao_voltar);
                break;
            case "pedir_certi":
                JLabel label_titulo_equip = new JLabel("Certificar Equipamentos");
                label_titulo_equip.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(label_titulo_equip);

                Equipamento[] equipamentos = DB.listarEquipamentosInterface(user.getId());
                String[] columnNames_equipamentos = {"ID Equipamento", "ID Fabricante", "Marca", "Modelo", "Setor", "Potencia", "Amperagem", "Codigo Sku", "Numero Modelo", "Data Submissão", "Data Certificação"};
                Object[][] data_equipamentos = new Object[equipamentos.length][columnNames_equipamentos.length];
                for (int i = 0; i < equipamentos.length; i++) {
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

                JButton botao_voltar_ped_cert = new JButton("Voltar");
                botao_voltar_ped_cert.setAlignmentX(Component.CENTER_ALIGNMENT);
                botao_voltar_ped_cert.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
                add(Box.createVerticalStrut(20));
                add(botao_voltar_ped_cert);

                JButton botao_certificar = new JButton("Pedir Certificação");
                botao_certificar.setAlignmentX(Component.CENTER_ALIGNMENT);
                botao_certificar.addActionListener(e -> {
                    if (table_equipamentos.getSelectedRow() == -1) {
                        JOptionPane.showMessageDialog(mainPanel, "Nenhum equipamento selecionado. Selecione um equipamento para certificar.");
                    } else {
                        int id_equipamento = (int) table_equipamentos.getValueAt(table_equipamentos.getSelectedRow(), 0);
                        int resposta = JOptionPane.showConfirmDialog(mainPanel, "Deseja certificar o equipamento de ID: " + id_equipamento + "?", "Certificação", JOptionPane.YES_NO_OPTION);
                        if (resposta == JOptionPane.YES_OPTION) {
                            Certificacao certificacao = new Certificacao(user.getId(), id_equipamento, "Iniciada");
                            DB.enviarCerteficacao(certificacao);
                            DB.setNumero_certificacao();
                            DB.enviarNotificacao(user.getId(), "Certificação de Equipamento - " + id_equipamento, "fabricante", "Gestores");
                            JOptionPane.showMessageDialog(mainPanel, "Pedido de certificação enviado com sucesso!");
                        } else {
                            JOptionPane.showMessageDialog(mainPanel, "Operação cancelada!");
                        }
                    }
                });
                add(botao_certificar);
                break;
        }
    }

    public void actionPerformed(ActionEvent e) {

    }
}
