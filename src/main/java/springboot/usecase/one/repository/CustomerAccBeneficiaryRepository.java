package springboot.usecase.one.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.usecase.one.entity.CustomerAccBeneficiaryEntity;
import springboot.usecase.one.entity.CustomerAccEntity;

@Repository
public interface CustomerAccBeneficiaryRepository extends JpaRepository<CustomerAccBeneficiaryEntity, Long> {

	List<CustomerAccBeneficiaryEntity> findByCustomerAccount(CustomerAccEntity customerAcc);

}
