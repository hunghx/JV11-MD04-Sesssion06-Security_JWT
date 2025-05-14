package ra.security.controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.security.model.dto.request.MailRequestDto;
import ra.security.service.EmailService;

import java.io.IOException;

@RestController
public class MailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send-mail")
    public ResponseEntity<?> sendEmailWithAttachment(@ModelAttribute MailRequestDto requestDto) throws MessagingException, IOException {
        emailService.sendEmailWithAttachment(requestDto.getRecipientEmail(),requestDto.getCcEmails(),requestDto.getBccEmails(), requestDto.getSubject(), requestDto.getHtmlContent(), requestDto.getAttachments());
        return new ResponseEntity<>("Gư mail thành công", HttpStatus.OK);
    }

}
