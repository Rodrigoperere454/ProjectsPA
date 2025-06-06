package view.panels;

import controller.DBController;
import controller.DBconfig;
import model.Notificacao;
import model.Utilizador;
import view.frames.AdminMenuFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.Connection;
import java.util.Arrays;

public class GestorPanel extends JPanel implements ActionListener {


    private JButton botao_registar;


    Connection conexao = DBconfig.getConnection();
    DBController DB = new DBController(conexao);


    public GestorPanel(String order, JFrame parent) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        switch (order) {
            case "notificacao":
                JLabel label_notificacao = new JLabel("Notificações");
                label_notificacao.setBounds(20, 0, 200, 25);
                label_notificacao.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(label_notificacao);
                DB.lerNotificacoes("Gestores");


                Notificacao[] notificacoes = DB.listarNotificacoes("Gestores");
                String[] columnNames = {"Data", "Utilizador", "Notificacao", "tipo", "encarregado", "Estado"};
                Object[][] data = new Object[notificacoes.length][6];
                for(int i = 0; i < notificacoes.length; i++) {
                    data[i][0] = notificacoes[i].getDataHora();
                    data[i][1] = notificacoes[i].getIdUtilizador();
                    data[i][2] = notificacoes[i].getDescricao();
                    data[i][3] = notificacoes[i].getTipo();
                    data[i][4] = notificacoes[i].getEncarregado();
                    data[i][5] = notificacoes[i].isLida();
                }

                JTable table_notificacao = new JTable(data, columnNames);
                table_notificacao.setPreferredScrollableViewportSize(new Dimension(500, 70));
                table_notificacao.setFillsViewportHeight(true);
                JScrollPane scrollPaneNotificacao = new JScrollPane(table_notificacao);
                add(scrollPaneNotificacao);

                JButton botao_voltar_notificacao = new JButton("Voltar");
                botao_voltar_notificacao.setAlignmentX(Component.CENTER_ALIGNMENT);
                botao_voltar_notificacao.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                parent.dispose();
                                new AdminMenuFrame().setVisible(true);
                            }
                        }
                );
                add(botao_voltar_notificacao);


                break;
            case "aceitar":
                JLabel label_aceitar = new JLabel("Confirmacao de Registo de Contas");
                label_aceitar.setBounds(20, 0, 200, 25);
                label_aceitar.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(label_aceitar);

                Utilizador[] utilizadores = DB.listarUtilizadoresInterface();
                String[] columnNames_aceitar = {"id", "Nome", "Username", "Email", "Tipo", "Estado"};
                Object[][] data_aceitar = new Object[utilizadores.length][7];
                for (int i = 0; i < utilizadores.length; i++) {
                    data_aceitar[i][0] = utilizadores[i].getId();
                    data_aceitar[i][1] = utilizadores[i].getName();
                    data_aceitar[i][2] = utilizadores[i].getUsername();
                    data_aceitar[i][3] = utilizadores[i].getEmail();
                    data_aceitar[i][4] = utilizadores[i].getType();
                    data_aceitar[i][5] = utilizadores[i].getEstado();

                }

                JTable table_aceitar = new JTable(data_aceitar, columnNames_aceitar);
                table_aceitar.setPreferredScrollableViewportSize(new Dimension(500, 70));
                table_aceitar.setFillsViewportHeight(true);
                JScrollPane scrollPaneAceitar = new JScrollPane(table_aceitar);
                add(scrollPaneAceitar);



                JButton botao_voltar = new JButton("Voltar");
                botao_voltar.setAlignmentX(Component.CENTER_ALIGNMENT);

                botao_voltar.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                parent.dispose();
                                new AdminMenuFrame().setVisible(true);
                            }
                        }
                );
                add(botao_voltar);

                JButton botao_aceitar = new JButton("Aceitar");
                botao_aceitar.setAlignmentX(Component.CENTER_ALIGNMENT);
                botao_aceitar.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                if(table_aceitar.getSelectedRow() == -1){
                                    JOptionPane.showMessageDialog(parent, "Nenhum Utilizador foi selecioando! Selecione para Ativar!");

                                }else{
                                    int id_user = (int) table_aceitar.getValueAt(table_aceitar.getSelectedRow(), 0);
                                    String estado = (String) table_aceitar.getValueAt(table_aceitar.getSelectedRow(), 5);
                                    if(estado.equalsIgnoreCase("ativo")){
                                        int response = JOptionPane.showConfirmDialog(parent, "O Utilizador Seleciona esta Ativo, deseja Desativar?", "Ativar/Remover", JOptionPane.YES_NO_OPTION);
                                        if (response == JOptionPane.YES_OPTION) {
                                            DB.desativarUtilizador(id_user);
                                        }else if(response == JOptionPane.NO_OPTION){
                                            JOptionPane.showMessageDialog(parent, "Operacao Cancelada!");
                                        }
                                    }else if(estado.equalsIgnoreCase("desativo")){
                                        int response = JOptionPane.showConfirmDialog(parent, "O Utilizador Selecionado esta Desativo, deseja Ativar?", "Ativar/Remover", JOptionPane.YES_NO_OPTION);
                                        if (response == JOptionPane.YES_OPTION) {
                                            DB.ativarUtilizador(id_user);
                                        }else if(response == JOptionPane.NO_OPTION){
                                            JOptionPane.showMessageDialog(parent, "Operacao Cancelada!");
                                        }
                                    }


                                }



                            }
                        }
                );
                add(botao_aceitar);

                break;

        }
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {

    }
}
