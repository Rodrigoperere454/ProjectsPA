package view.dialogs;
import controller.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

/**
 * Classe que representa o diálogo de registo de utilizadores.
 * Permite ao administrador registar diferentes tipos de utilizadores (Gestor, Fabricante, Técnico).
 */
public class RegistarDialog extends JDialog implements ActionListener {

    private JButton btnType_gestor;
    private JButton btnType_fabricante;
    private JButton btnType_tecnico;


    public RegistarDialog(JFrame parent) {
        super(parent, "Registar Utilizador", true);
        setLayout(null);
        setSize(300, 200);
        setLocationRelativeTo(parent);

        btnType_gestor = new JButton("Registar Gestor");
        btnType_gestor.setBounds(50, 20, 200, 30);
        btnType_gestor.addActionListener(this);
        btnType_gestor.setToolTipText("Registar um utilizador do tipo Gestor");
        add(btnType_gestor);

        btnType_fabricante = new JButton("Registar Fabricante");
        btnType_fabricante.setBounds(50, 60, 200, 30);
        btnType_fabricante.addActionListener(this);
        btnType_fabricante.setToolTipText("Registar um utilizador do tipo Fabricante");
        add(btnType_fabricante);

        btnType_tecnico = new JButton("Registar Técnico");
        btnType_tecnico.setBounds(50, 100, 200, 30);
        btnType_tecnico.setToolTipText("Registar um utilizador do tipo Técnico");
        btnType_tecnico.addActionListener(this);
        add(btnType_tecnico);

    }

    /**
     * Método que trata os eventos de ação dos botões.
     * Dependendo do botão clicado, abre o diálogo de registo correspondente.
     *
     * @param e Evento de ação
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnType_gestor)) {
            TypeRegistar registar_user = new TypeRegistar("Gestor");
            registar_user.setVisible(true);
        } else if (e.getSource().equals(btnType_fabricante)) {
            TypeRegistar registar_user = new TypeRegistar("Fabricante");
            registar_user.setVisible(true);
        } else if (e.getSource().equals(btnType_tecnico)) {
            TypeRegistar registar_user = new TypeRegistar("Tecnico");
            registar_user.setVisible(true);
        }
        dispose();
    }
}
