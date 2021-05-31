package springboot.usecase.one.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.usecase.one.entity.CustomerAccount;

@Repository
public interface CustomerAccountRepository  extends JpaRepository<CustomerAccount, Long> {
	CustomerAccount findByCustId(Long id);
}
