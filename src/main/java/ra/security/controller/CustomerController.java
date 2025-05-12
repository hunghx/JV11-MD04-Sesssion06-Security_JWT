package ra.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.model.dto.request.CustomerAddDto;
import ra.security.model.dto.request.CustomerUpdateDto;
import ra.security.model.dto.response.PageDto;
import ra.security.model.entity.Customer;
import ra.security.service.ICustomerService;

@RestController // chú thích đây là controller trả về dữ liệu JSON
@RequestMapping("/customers")
@RequiredArgsConstructor
//@CrossOrigin("*")
public class CustomerController {
    private final ICustomerService customerService;
    // các nhóm mã quan trọng : 2xx(200,201,204), 4xx(400, 401, 403, 404), 5xx(lỗi server , pha xử lỹ)
    // api ấy về danh sách
    @GetMapping // gọi là các endpoint
    public ResponseEntity<PageDto<Customer>> getCustomers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "2") int size
    ){

       return new ResponseEntity<>(customerService.getCustomersPagination(page, size), HttpStatus.OK);
    }
    // api ấy về danh sách
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomersById(@PathVariable Long id){
        return new ResponseEntity<>(customerService.getCustomersById(id), HttpStatus.OK);
    }
    // api thêm mới khách hàng
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerAddDto request){
        return new ResponseEntity<>(customerService.addCustomer(request), HttpStatus.CREATED);
    }
    // api cập nhật
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerUpdateDto request, @PathVariable Long id){
        return new ResponseEntity<>(customerService.updateCustomer(request, id),HttpStatus.OK);
    }
    // api xóa
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // BTVN : viết API tìm kiếm , phân trang và sắp xếp (page, size, sortDirection, sortBy, keyword) => trả về thông tin phân trang (page, size, sort(by, direction), keyword, contentList, totalPage)
 }
