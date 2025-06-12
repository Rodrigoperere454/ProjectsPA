package view.panels;

import controller.DBController;
import controller.DBconfig;
import model.Utilizador;
import utils.Session;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class ImagePanel extends JPanel {
    private static final String IMAGE_FOLDER = "public/imgs/user/profile/";
    private BufferedImage image;
    private final String username;
    private Image scaledImage;

    public ImagePanel() {
        Utilizador loggedUser = Session.getUtilizador();
        this.username = loggedUser.getUsername();
        carregarImagemInicial();
        configurarCliqueParaAlterarImagem();
    }

    private void carregarImagemInicial() {
        try {
            Connection connection = DBconfig.getConnection();
            DBController dbController = new DBController(connection);
            String imagePath = dbController.getUserImage(username);

            System.out.println(new File(imagePath).getAbsolutePath());

            image = ImageIO.read(new File(imagePath));
            scaledImage = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar imagem de perfil vai ser usada a imagem padrão.", "Erro", JOptionPane.ERROR_MESSAGE);
            // Se não conseguir carregar a imagem, define uma imagem padrão
            carregarImagemDefault();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar imagem de perfil vai ser usada a imagem padrão.", "Erro", JOptionPane.ERROR_MESSAGE);
            // Se ocorrer um erro, tenta carregar a imagem padrão
            carregarImagemDefault();
        }
    }

    private void carregarImagemDefault() {
        try {
            image = ImageIO.read(new File("public/imgs/user/profile/default_profile_img.png"));
            scaledImage = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar imagem padrão: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarImagemComFallback(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.err.println("Ficheiro não existe: " + file.getAbsolutePath());
                throw new Exception("Ficheiro não encontrado: " + path);
            }

            image = ImageIO.read(file);
            scaledImage = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar imagem: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarCliqueParaAlterarImagem() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                escolherNovaImagem();
            }
        });
    }

    //
    private void loadImage(String originalPath) {
        try {
            // Define caminho de destino com nome baseado no username
            String extension = originalPath.substring(originalPath.lastIndexOf("."));
            String newFileName = username + extension;
            String destinationPath = IMAGE_FOLDER + newFileName;

            // Cria diretório se não existir
            new File(IMAGE_FOLDER).mkdirs();

            // Copia o ficheiro para a pasta destino
            Files.copy(new File(originalPath).toPath(), new File(destinationPath).toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Lê a imagem copiada
            image = ImageIO.read(new File(destinationPath));
            scaledImage = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);

            // Atualiza a base de dados com o novo caminho
            Connection connection = DBconfig.getConnection();
            DBController dbController = new DBController(connection);
            dbController.insertUserImage(username, destinationPath);

            repaint();
            JOptionPane.showMessageDialog(this, "Nova imagem carregada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (scaledImage != null) {
            System.out.println("A desenhar imagem redimensionada no painel...");
            int x = (getWidth() - scaledImage.getWidth(this)) / 2;
            int y = (getHeight() - scaledImage.getHeight(this)) / 2;
            g.drawImage(scaledImage, x, y, this);
        } else {
            System.out.println("Imagem está null");
        }
    }
}


