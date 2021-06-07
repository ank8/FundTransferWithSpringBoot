package springboot.usecase.one.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.usecase.one.entity.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
	Optional<CustomerEntity> findByPwdAndEmail(String pwd, String email);

	Optional<CustomerEntity> findByMobileOrEmail(String mobile, String email);

	CustomerEntity findByMobile(String mob);
}
