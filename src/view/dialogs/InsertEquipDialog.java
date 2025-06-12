package view.dialogs;

import controller.DBController;
import controller.DBconfig;
import model.Equipamento;
import model.Utilizador;
import utils.Session;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class InsertEquipDialog extends JDialog implements ActionListener {

    Connection conexao = DBconfig.getConnection();
    DBController DB = new DBController(conexao);
    private JButton adicionar_equip_butao;
    private JButton cancelar_butao;
    private JTextField marca_field;
    private JTextField modelo_field;
    private JTextField setor_field;
    private JTextField potencia_field;
    private JTextField amperagem_field;
    private JTextField numero_modelo_field;
    private JFrame parentFrame;
    Utilizador user = Session.getUtilizador();
    public InsertEquipDialog(JFrame parent) {
        super(parent, "MenuFab", true);
        setLayout(null);
        this.parentFrame = parent;
        setSize(400, 400);
        setLocationRelativeTo(parentFrame);


        JLabel label = new JLabel("Adicionar Equipamento");
        label.setBounds(20, 20, 200, 25);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);

        JLabel label_marca = new JLabel("Marca:");
        label_marca.setBounds(20, 60, 80, 25);
        add(label_marca);

        marca_field = new JTextField();
        marca_field.setBounds(100, 60, 200, 25);
        add(marca_field);

        JLabel label_modelo = new JLabel("Modelo:");
        label_modelo.setBounds(20, 100, 80, 25);
        add(label_modelo);

        modelo_field = new JTextField();
        modelo_field.setBounds(100, 100, 200, 25);
        add(modelo_field);

        JLabel label_setor = new JLabel("Setor:");
        label_setor.setBounds(20, 140, 80, 25);
        add(label_setor);

        setor_field = new JTextField();
        setor_field.setBounds(100, 140, 200, 25);
        add(setor_field);

        JLabel label_potencia = new JLabel("Potencia:");
        label_potencia.setBounds(20, 180, 80, 25);
        add(label_potencia);

        potencia_field = new JTextField();
        potencia_field.setBounds(100, 180, 200, 25);
        add(potencia_field);

        JLabel label_amperagem = new JLabel("Amperagem:");
        label_amperagem.setBounds(20, 220, 80, 25);
        add(label_amperagem);

        amperagem_field = new JTextField();
        amperagem_field.setBounds(100, 220, 200, 25);
        add(amperagem_field);

        JLabel label_numero_modelo = new JLabel("Numero Modelo:");
        label_numero_modelo.setBounds(20, 260, 100, 25);
        add(label_numero_modelo);

        numero_modelo_field = new JTextField();
        numero_modelo_field.setBounds(130, 260, 200, 25);
        add(numero_modelo_field);

        adicionar_equip_butao = new JButton("Adicionar");
        adicionar_equip_butao.setBounds(50, 320, 120, 25);        adicionar_equip_butao.addActionListener(this);
        add(adicionar_equip_butao);

        cancelar_butao = new JButton("Cancelar");
        cancelar_butao.setBounds(200, 320, 120, 25);        cancelar_butao.addActionListener(this);
        add(cancelar_butao);


    }


    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancelar_butao) {
            dispose();
        }else if(e.getSource() == adicionar_equip_butao) {
            String marca = marca_field.getText();
            String modelo = modelo_field.getText();
            String setor = setor_field.getText();
            String potencia_value = potencia_field.getText();
            String amperagem_value = amperagem_field.getText();
            String numero_modelo_value = numero_modelo_field.getText();

            int potencia = 0;
            int amperagem = 0;
            int numero_modelo = 0;

            try {
                potencia = Integer.parseInt(potencia_value);
                amperagem = Integer.parseInt(amperagem_value);
                numero_modelo = Integer.parseInt(numero_modelo_value);

            } catch (NumberFormatException er) {
                System.out.println("Por favor, insira apenas números válidos em potência, amperagem e número do modelo.");
            }

            Equipamento equipamento = new Equipamento(user.getId(), marca, modelo, setor, potencia, amperagem, numero_modelo);

            boolean sucesso_add = DB.adicionarEquipamentos(equipamento);
            if(sucesso_add) {
                JOptionPane.showMessageDialog(parentFrame, "Equipamento adicionado com sucesso.");
            }else{
                JOptionPane.showMessageDialog(parentFrame, "Erro ao adicionar equipamento.");
            }
        }

    }
}
