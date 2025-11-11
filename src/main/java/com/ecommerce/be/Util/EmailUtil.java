package com.ecommerce.be.Util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;


@Service
public class EmailUtil {

    private JavaMailSender mailSender;
    private FileLoaderUtil fileLoader;

    public EmailUtil(JavaMailSender mailSender, FileLoaderUtil fileLoader) {
        this.mailSender = mailSender;
        this.fileLoader = fileLoader;
    }

    @Async
    public void sendVerificationEmailToSeller(String receiver, String otp) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            String htmlMsg = fileLoader.loadHtmlTemplateInString();
            htmlMsg = htmlMsg.replace("{receiver}", receiver);
            htmlMsg = htmlMsg.replace("{otp}", otp);


            mimeMessageHelper.setTo(receiver);
            mimeMessageHelper.setSubject("[BK Artisan] Mã xác nhận trở thành người bán");
            mimeMessageHelper.setText(htmlMsg, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
