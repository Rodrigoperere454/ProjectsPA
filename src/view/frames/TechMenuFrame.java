package view.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TechMenuFrame extends JFrame implements ActionListener {

    private JButton[] botoes = new JButton[9]; // Array para os botões do menu
    private String[] labels = {
            "1 - Registar Técnico",
            "2 - Ver Notificações",
            "3 - Remover Conta",
            "4 - Ver Certificações",
            "5 - Inspecionar Equipamento",
            "6 - Aceitar/Negar Certificação",
            "7 - Alterar Minhas Infos",
            "8 - Cancelar Certificação",
            "0 - Sair"
    }; // array de labels para os botões

    public TechMenuFrame() {
        setTitle("Menu Técnico");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Menu Técnico");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(titulo);
        add(Box.createVerticalStrut(20));

        for (int i = 0; i < labels.length; i++) { // Itera sobre os labels
            botoes[i] = new JButton(labels[i]); // Cria um botão para cada label
            botoes[i].setAlignmentX(Component.CENTER_ALIGNMENT); // Alinha o botão ao centro
            botoes[i].setMaximumSize(new Dimension(300, 40)); // Define o tamanho máximo do botão
            botoes[i].addActionListener(this); // Adiciona o ActionListener para cada botão
            add(botoes[i]); // Adiciona o botão ao frame
            add(Box.createVerticalStrut(10)); // Espaço entre os botões
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource(); // Obtém o objeto que disparou o evento

        for (int i = 0; i < botoes.length; i++) { // Itera sobre os botões
            if (source == botoes[i]) { // Verifica qual botão foi pressionado
                switch (i) {
                    case 0: registarTecnico(); break;
                    case 1: verNotificacoes(); break;
                    case 2: removerConta(); break;
                    case 3: verCertificacoes(); break;
                    case 4: inspecionarEquipamento(); break;
                    case 5: aceitarNegarCertificacao(); break;
                    case 6: alterarInformacoes(); break;
                    case 7: cancelarCertificacao(); break;
                    case 8: fechar(); break;
                }
                break;
            }
        }
    }

    private void registarTecnico() {
        JOptionPane.showMessageDialog(this, "Perguica.");
    }

    private void verNotificacoes() {
        JOptionPane.showMessageDialog(this, "Perguica.");
    }

    private void removerConta() {
        JOptionPane.showMessageDialog(this, "Perguica.");
    }

    private void verCertificacoes() {
        JOptionPane.showMessageDialog(this, "Perguica.");
    }

    private void inspecionarEquipamento() {
        JOptionPane.showMessageDialog(this, "Perguica.");
    }

    private void aceitarNegarCertificacao() {
        JOptionPane.showMessageDialog(this, "Perguica.");
    }

    private void alterarInformacoes() {
        JOptionPane.showMessageDialog(this, "Perguica.");
    }

    private void cancelarCertificacao() {
        JOptionPane.showMessageDialog(this, "Perguica.");
    }

    private void fechar() {
        dispose();
    }
}

