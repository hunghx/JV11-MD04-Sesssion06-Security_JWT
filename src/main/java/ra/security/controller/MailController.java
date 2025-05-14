package ra.security.controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ra.security.model.dto.request.MailRequestDto;
import ra.security.service.EmailService;
import ra.security.service.UploadService;

import java.io.IOException;

@RestController
public class MailController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/send-mail")
    public ResponseEntity<?> sendEmailWithAttachment(@ModelAttribute MailRequestDto requestDto) throws MessagingException, IOException {
        emailService.sendEmailWithAttachment(requestDto.getRecipientEmail(),requestDto.getCcEmails(),requestDto.getBccEmails(), requestDto.getSubject(), requestDto.getHtmlContent(), requestDto.getAttachments());
        return new ResponseEntity<>("Gư mail thành công", HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {
        String url = uploadService.uploadFile(file);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

}
