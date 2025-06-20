package utils;

import jakarta.mail.*;
import jakarta.mail.Session;
import jakarta.mail.internet.*;

import javax.swing.*;
import java.util.Properties;

/**
 * Classe responsável pelo envio de emails.
 * Utiliza a biblioteca Jakarta Mail para enviar emails através do SMTP do Gmail.
 */
public class MailSender {
    /**
     * Função para enviar um email.
     * @param to
     * @param subject
     * @param body
     */
    public static void sendEmail(String to, String subject, String body) {
        final String sender = "avacalho881@gmail.com";
        final String password = "yytp frfw jepy ysrb";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // Porta usada para SMTP com o TLS

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to.trim()));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email enviado com sucesso para " + to);
        } catch (MessagingException e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao enviar email: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
