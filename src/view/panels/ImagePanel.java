package view.panels;

import controller.DBController;
import controller.DBconfig;
import model.Utilizador;
import utils.Session;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;


public class ImagePanel extends JPanel {

    private BufferedImage image;
    private final String username;

    public ImagePanel() {
        Utilizador loggedUser = Session.getUtilizador();
        this.username = loggedUser.getUsername();
        carregarImagemInicial();
        configurarCliqueParaAlterarImagem();
    }

    //
    private void carregarImagemInicial() {
        try {
            Connection connection = DBconfig.getConnection();
            DBController dbController = new DBController(connection);
            //String imagePath = dbController.getUserImage(username);

            //image = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar imagem: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarCliqueParaAlterarImagem() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                escolherNovaImagem();
            }
        });
    }

    //
    private void loadImage(String path) {
        try {
            image = ImageIO.read(new File(path));

            Connection connection = DBconfig.getConnection();
            DBController dbController = new DBController(connection);
            dbController.insertUserImage(username, path); // Atualiza a imagem na BD

            repaint();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar nova imagem: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void escolherNovaImagem() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolher nova imagem de perfil");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png", "gif"));

        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            loadImage(file.getAbsolutePath());
        }
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int x = (getWidth() - image.getWidth()) / 2;
            int y = (getHeight() - image.getHeight()) / 2;
            g.drawImage(image, x, y, this);
        }
    }
}


