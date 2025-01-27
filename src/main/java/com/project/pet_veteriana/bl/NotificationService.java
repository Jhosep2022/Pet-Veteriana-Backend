package com.project.pet_veteriana.bl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSubscriptionNotification(String toEmail, String sellerName) throws MessagingException {
        String subject = "¡Bienvenido como Vendedor en PetVeterinaria!";
        String htmlContent = buildHtmlContent(sellerName);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // `true` indica que el contenido es HTML

        mailSender.send(mimeMessage);
    }

    private String buildHtmlContent(String sellerName) {
        return """
        <html>
        <head>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 0;
                    padding: 0;
                    background-color: #f4f4f4;
                }
                .container {
                    max-width: 600px;
                    margin: 20px auto;
                    background-color: #ffffff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                }
                .header {
                    text-align: center;
                    padding-bottom: 20px;
                }
                .header img {
                    width: 150px;
                }
                .header h1 {
                    color: #3c7f99;
                }
                .content {
                    font-size: 16px;
                    color: #333333;
                    line-height: 1.6;
                }
                .content h2 {
                    color: #3c7f99;
                }
                .button {
                    display: inline-block;
                    margin-top: 20px;
                    padding: 10px 20px;
                    color: #ffffff; /* Texto blanco */
                    background: linear-gradient(to right, #3c7f99, #00bfa6); /* Degradado */
                    text-decoration: none;
                    border-radius: 5px;
                    font-weight: bold;
                    text-align: center;
                }
                .button:hover {
                    opacity: 0.9;
                }
                .footer {
                    margin-top: 20px;
                    text-align: center;
                    font-size: 12px;
                    color: #888888;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <img src="https://example.com/logo.png" alt="PetVeterinaria">
                    <h1>¡Bienvenido a PetVeterinaria!</h1>
                </div>
                <div class="content">
                    <h2>Hola, %s</h2>
                    <p>
                        Gracias por unirte a nuestra plataforma como vendedor. Estamos emocionados
                        de tenerte a bordo y ayudarte a ofrecer tus productos y servicios.
                    </p>
                    <p>
                        Si tienes alguna pregunta, no dudes en contactarnos. ¡Estamos aquí para ayudarte!
                    </p>
                    <a href="https://petveterinaria.com/login" class="button">Ir al Panel de Vendedor</a>
                </div>
                <div class="footer">
                    <p>&copy; 2025 PetVeterinaria. Todos los derechos reservados.</p>
                </div>
            </div>
        </body>
        </html>
        """.formatted(sellerName);
    }
}
