package view.dialogs;

import controller.DBController;
import controller.DBconfig;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class TypeRegistar extends JDialog implements ActionListener {

    Connection conexao = DBconfig.getConnection();
    DBController DB = new DBController(conexao);

    private JButton botao_registar;
    private JButton botao_cancelar;
    private JTextField field_nome;
    private JTextField field_username;
    private JTextField field_password;
    private JTextField field_email;
    private JTextField field_nif;
    private JTextField field_telefone;
    private JTextField field_morada;
    private JTextField field_area_especializacao;
    private JTextField field_sector_comercial;
    private JTextField field_nivel_certificacao;
    private JTextField field_type;

    public TypeRegistar(String type) {
        setTitle("Registar Type");
        setLayout(null);
        setSize(400, 500);
        setLocationRelativeTo(null);

        JLabel label_nome = new JLabel("Nome:");
        label_nome.setBounds(20, 20, 80, 25);
        add(label_nome);

        field_nome = new JTextField();
        field_nome.setBounds(120, 20, 200, 25);
        add(field_nome);

        JLabel label_username = new JLabel("Username:");
        label_username.setBounds(20, 60, 80, 25);
        add(label_username);

        field_username = new JTextField();
        field_username.setBounds(120, 60, 200, 25);
        add(field_username);

        JLabel label_password = new JLabel("Password:");
        label_password.setBounds(20, 100, 80, 25);
        add(label_password);

        field_password = new JTextField();
        field_password.setBounds(120, 100, 200, 25);
        add(field_password);

        JLabel label_email = new JLabel("Email:");
        label_email.setBounds(20, 140, 80, 25);
        add(label_email);

        field_email = new JTextField();
        field_email.setBounds(120, 140, 200, 25);
        add(field_email);

        if (type.equalsIgnoreCase("tecnico") || type.equalsIgnoreCase("fabricante")) {
            JLabel label_nif = new JLabel("NIF:");
            label_nif.setBounds(20, 180, 80, 25);
            add(label_nif);

            field_nif = new JTextField();
            field_nif.setBounds(120, 180, 200, 25);
            add(field_nif);

            JLabel label_telefone = new JLabel("Telefone:");
            label_telefone.setBounds(20, 220, 80, 25);
            add(label_telefone);

            field_telefone = new JTextField();
            field_telefone.setBounds(120, 220, 200, 25);
            add(field_telefone);

            JLabel label_morada = new JLabel("Morada:");
            label_morada.setBounds(20, 260, 80, 25);
            add(label_morada);

            field_morada = new JTextField();
            field_morada.setBounds(120, 260, 200, 25);
            add(field_morada);
        }

        if (type.equalsIgnoreCase("fabricante")) {
            JLabel label_sector_comercial = new JLabel("Sector Comercial:");
            label_sector_comercial.setBounds(20, 300, 120, 25);
            add(label_sector_comercial);

            field_sector_comercial = new JTextField();
            field_sector_comercial.setBounds(160, 300, 160, 25);
            add(field_sector_comercial);
        }

        if (type.equalsIgnoreCase("tecnico")) {
            JLabel label_area_especializacao = new JLabel("Área Especialização:");
            label_area_especializacao.setBounds(20, 300, 120, 25);
            add(label_area_especializacao);

            field_area_especializacao = new JTextField();
            field_area_especializacao.setBounds(160, 300, 160, 25);
            add(field_area_especializacao);

            JLabel label_nivel_certificacao = new JLabel("Nível Certificação:");
            label_nivel_certificacao.setBounds(20, 340, 120, 25);
            add(label_nivel_certificacao);

            field_nivel_certificacao = new JTextField();
            field_nivel_certificacao.setBounds(160, 340, 160, 25);
            add(field_nivel_certificacao);
        }

        field_type = new JTextField(type);

        botao_registar = new JButton("Registar");
        botao_registar.setBounds(50, 380, 100, 30);
        botao_registar.addActionListener(this);
        add(botao_registar);

        botao_cancelar = new JButton("Cancelar");
        botao_cancelar.setBounds(200, 380, 100, 30);
        botao_cancelar.addActionListener(e -> dispose());
        add(botao_cancelar);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botao_registar) {
            System.out.println(field_type.getText());
            if (field_type.getText().equalsIgnoreCase("tecnico")) {
                String nome = field_nome.getText();
                String username = field_username.getText();
                String password = field_password.getText();
                String email = field_email.getText();
                String nif = field_nif.getText();
                String telefone = field_telefone.getText();
                String morada = field_morada.getText();
                String area_especializacao = field_area_especializacao.getText();
                int nivel_certificacao = Integer.parseInt(field_nivel_certificacao.getText());

                boolean exists = DB.alreadyExists(username, email);

                if (exists) {
                    JOptionPane.showMessageDialog(this, "Utilizador já existe.");
                }else{
                    Tecnico tecnico = new Tecnico(nome, username, password, email, "tecnico", nif, telefone, morada, area_especializacao, nivel_certificacao);
                    boolean sucesso_inserirTecnico = DB.inserirTecnico(tecnico);
                    if (sucesso_inserirTecnico) {
                        JOptionPane.showMessageDialog(this, "Técnico registado com sucesso!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao registar Técnico.");
                    }
                }
            } else if (field_type.getText().equalsIgnoreCase("fabricante")) {
                String nome = field_nome.getText();
                String username = field_username.getText();
                String password = field_password.getText();
                String email = field_email.getText();
                String nif = field_nif.getText();
                String telefone = field_telefone.getText();
                String morada = field_morada.getText();
                String sector_comercial = field_sector_comercial.getText();

                boolean exists = DB.alreadyExists(username, email);
                if (exists) {
                    JOptionPane.showMessageDialog(this, "Utilziador já existe.");
                }else{
                    Fabricante fabricante = new Fabricante(nome, username, password, email, "fabricante", nif, telefone, morada, sector_comercial, java.time.LocalDate.now());
                    boolean secesso_inserirFabricante = DB.inserirFabricante(fabricante);
                    if (secesso_inserirFabricante) {
                        JOptionPane.showMessageDialog(this, "Fabricante registado com sucesso!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao registar Fabricante.");
                    }
                }
            } else {
                System.out.println("Inserir Gestor");
                String nome = field_nome.getText();
                String username = field_username.getText();
                String password = field_password.getText();
                String email = field_email.getText();

                boolean exists = DB.alreadyExists(username, email);
                if (exists) {
                    JOptionPane.showMessageDialog(this, "Utilizador já existe.");
                }else {
                    Utilizador gestor = new Utilizador(nome, username, password, email, "gestor");
                    boolean inserir_sucessoUtilizador = DB.inserirUtilizador(gestor);
                    if (inserir_sucessoUtilizador) {
                        JOptionPane.showMessageDialog(this, "Gestor registado com sucesso!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao registar Gestor.");
                    }
                }

            }
        }

    }
}
