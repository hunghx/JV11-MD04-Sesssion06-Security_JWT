package ra.security.service;

import ra.security.model.dto.request.CustomerAddDto;
import ra.security.model.dto.request.CustomerUpdateDto;
import ra.security.model.dto.response.PageDto;
import ra.security.model.entity.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> getCustomers();
    PageDto<Customer> getCustomersPagination(int page, int size);
    Customer getCustomersById(Long id);
    Customer addCustomer(CustomerAddDto request);
    Customer updateCustomer(CustomerUpdateDto request, Long id);
    void deleteCustomer(Long id);
}
