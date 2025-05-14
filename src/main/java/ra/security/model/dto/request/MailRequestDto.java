package ra.security.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class MailRequestDto {
   private   String recipientEmail;
   private List<String> ccEmails, bccEmails;
   private String subject;
   private String htmlContent;
   private List<MultipartFile> attachments;
}
