package ra.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    @GetMapping
    public ResponseEntity<?> list(){
        throw new RuntimeException("Có lỗi sinh ra");
//        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
