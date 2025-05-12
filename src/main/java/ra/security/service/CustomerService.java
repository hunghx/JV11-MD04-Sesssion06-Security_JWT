package ra.security.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.security.exception.NotFoundException;
import ra.security.model.dto.request.CustomerAddDto;
import ra.security.model.dto.request.CustomerUpdateDto;
import ra.security.model.dto.response.PageDto;
import ra.security.model.entity.Customer;
import ra.security.model.mapper.CustomerMapper;
import ra.security.repository.ICustomerRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService{
    private final ICustomerRepository customerRepository;
    private final ModelMapper mapper;
    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomersById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("ID không tìm thấy"));
    }

    @Override
    public PageDto<Customer> getCustomersPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Customer> pageCus = customerRepository.findAll(pageable);
        // biến đoi thành Page DTO
        PageDto<Customer> pageDto = new PageDto<>();
        pageDto.setPages(pageCus.getTotalPages());
        pageDto.setNext(pageCus.hasNext()?page+1:null);
        pageDto.setPrev(pageCus.hasPrevious()?page-1:null);
        pageDto.setLast(pageCus.getTotalPages()-1);
        pageDto.setFirst(0);
        pageDto.setItems(pageCus.getTotalElements());
        pageDto.setData(pageCus.getContent());
        return pageDto;
    }

    @Override
    public Customer addCustomer(CustomerAddDto request) {
//        Customer cus = new Customer(null, request.getFirstName(), ...)
//        Customer cus = mapper.map(request, Customer.class); // chuyển đối gia trị thuộc tính theo tên thuộc tính
        Customer cus = CustomerMapper.mapCustomerAddDtoToEntity(request);
        return customerRepository.save(cus);
    }

    @Override
    public Customer updateCustomer(CustomerUpdateDto request, Long id) {
        // kiểm tra tồn tại
        Customer oldCustomer =  customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Id không tôn tại"));
//        Customer cus = mapper.map(request, Customer.class);
        CustomerMapper.mapCustomerUpdateDtoToEntity(request,oldCustomer);
        return customerRepository.save(oldCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        // kiểm tra tồn tại
        customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Id không tôn tại"));
        customerRepository.deleteById(id);
    }
}
