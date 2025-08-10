package com.example.school.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl; // e.g., http://localhost:4200 or your deployed frontend

    public void sendSignupLink(String recipientEmail, String token) {
        String subject = "Complete Your Staff Registration";
        String link = frontendUrl + "/complete-registration?token=" + token;

        String content = "<p>Hello,</p>"
                + "<p>You have been invited to join the school system. Please complete your registration by clicking the link below:</p>"
                + "<p><a href=\"" + link + "\">Set your password and activate your account</a></p>"
                + "<br><p>If you did not request this, please ignore this email.</p>";

        sendHtmlEmail(recipientEmail, subject, content);
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
