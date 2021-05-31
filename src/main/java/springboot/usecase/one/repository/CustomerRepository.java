package springboot.usecase.one.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.usecase.one.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByPwdAndEmail(String pwd, String email);

	Optional<Customer> findByMobileOrEmail(String mobile, String email);

	Customer findByMobile(String mob);
}
