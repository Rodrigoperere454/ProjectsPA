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

    Connection conexao = DBconfig.getConnection();
    DBController DB = new DBController(conexao);

    public FabPanel(JFrame parent, String order) {
        Utilizador user = Session.getUtilizador();

        if(order.equalsIgnoreCase("listar_equip")){
            JLabel lblEquip = new JLabel("Equipamentos");
            lblEquip.setBounds(20, 0, 200, 25);
            lblEquip.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(lblEquip);

            Equipamento[] listar_equipamentos = DB.listarEquipamentosInterface(user.getId());
            JList list = new JList(listar_equipamentos);
            list.setPreferredSize(new Dimension(250, 200));
            JScrollPane scroll = new JScrollPane(list);
            add(scroll);

            JButton botao_voltar = new JButton("Voltar");
            botao_voltar.setAlignmentX(Component.CENTER_ALIGNMENT);

            botao_voltar.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            parent.dispose();
                            new FabMenuFrame().setVisible(true);
                        }
                    }
            );
            add(botao_voltar);


        }else if(order.equalsIgnoreCase("pedir_certi")){
            JLabel label_titulo_equip = new JLabel("Certeficar Equipamentos");
            label_titulo_equip.setBounds(20, 0, 200, 25);
            label_titulo_equip.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(label_titulo_equip);

            Equipamento[] equipamentos = DB.listarEquipamentosInterface(user.getId());
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

            JButton botao_voltar = new JButton("Voltar");
            botao_voltar.setAlignmentX(Component.CENTER_ALIGNMENT);

            botao_voltar.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            parent.dispose();
                            new FabMenuFrame().setVisible(true);
                        }
                    }
            );
            add(botao_voltar);

            JButton botao_certificar = new JButton("Certificar Equipamento");
            botao_certificar.setAlignmentX(Component.CENTER_ALIGNMENT);
            botao_certificar.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if(table_equipamentos.getSelectedRow() == -1){
                                JOptionPane.showMessageDialog(parent, "Nenhum Equipamento selecionado, selecione um equipamento para certificar.");
                            } else {
                                int id_equipamento = (int) table_equipamentos.getValueAt(table_equipamentos.getSelectedRow(), 0);
                                int response = JOptionPane.showConfirmDialog(parent, "Deseja certificar o equipamento de ID: " + id_equipamento + "?", "certificacao", JOptionPane.YES_NO_OPTION);
                                if(response == JOptionPane.YES_OPTION){
                                    Certificacao certificacao = new Certificacao(user.getId(), id_equipamento, "Iniciada");
                                    DB.enviarCerteficacao(certificacao);
                                    DB.setNumero_certificacao();
                                    DB.enviarNotificacao(user.getId(),"Certeficação de Equipamento - " + id_equipamento, "fabricante", "Gestores" );
                                    JOptionPane.showMessageDialog(parent, "Pedido de Certeficação enviado com sucesso!");
                                }else if(response == JOptionPane.NO_OPTION){
                                    JOptionPane.showMessageDialog(parent, "Operacao Cancelada!");
                                }
                            }
                        }
                    }
            );
            add(botao_certificar);
        }
    }

    public void actionPerformed(ActionEvent e) {

    }
}
