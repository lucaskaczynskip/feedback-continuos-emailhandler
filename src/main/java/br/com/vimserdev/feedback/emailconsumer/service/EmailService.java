package br.com.vimserdev.feedback.emailconsumer.service;

import br.com.vimserdev.feedback.emailconsumer.dto.EmailHandlerDTO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender emailSender;
    private File dirPath = new File("src/main/resources/templates");

    public void sendEmail(EmailHandlerDTO emailHandlerDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(emailHandlerDTO.getTo());
            mimeMessageHelper.setSubject("Você recebeu 1 novo feedback!");
            mimeMessageHelper.setText(geContentFromTemplate(emailHandlerDTO), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String geContentFromTemplate(EmailHandlerDTO emailHandlerDTO) throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();
        data.put("from", emailHandlerDTO.getFrom());
        fmConfiguration.setDirectoryForTemplateLoading(dirPath);
        Template template = fmConfiguration.getTemplate("/feedback-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        return html;
    }
}
