package springboot.usecase.one.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.entity.CustomerAccEntity;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccEntity, Long> {

	List<CustomerAccEntity> findByCustomer(CustomerEntity customer);
}
