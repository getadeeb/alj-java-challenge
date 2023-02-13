package jp.co.axa.apidemo.services;

import javax.persistence.EntityNotFoundException;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /*
    * Retrieving employees will first look in the cache ,
    * if not found, it will taken from db and update the cache
    * */
    @Cacheable("employees")
    public Page<Employee> retrieveEmployees(PageRequest paging) {
        return employeeRepository.findAll(paging);
    }

    /*
    * First look into the cache and if
    * not found fetch from db and update the cache
    * */
    @Cacheable("employee")
    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(
            EntityNotFoundException::new);
    }

    /*
    * 1. If new employee is added , the list employees cache will be cleared
    * 2. new employee will be added to the single employee cache
    * */
    @CacheEvict(value = "employees", allEntries = true)
    @Cacheable("employee")
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /*
    * 1. if an employee is deleted the list employee cache will be cleared
    * 2. individual employee info from the cache will be also deleted
    * */
    @Caching(evict = {
        @CacheEvict(value = "employees", allEntries = true),
        @CacheEvict(value = "employee", key = "#employeeId")})
    public Employee deleteEmployee(Long employeeId) {
        Employee employee = getEmployee(employeeId);
        employeeRepository.deleteById(employee.getId());
        return employee;
    }

    /*
    * 1. if an employee is updated employees cache will be updated
    * 2. individual employee cache will also get updated
    * */
    @Caching(
        put = {
            @CachePut(value = "employees"),
            @CachePut(value = "employee", key = "#employee.getId()")
        }
    )
    @CacheEvict(value = "employees", allEntries = true)
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}