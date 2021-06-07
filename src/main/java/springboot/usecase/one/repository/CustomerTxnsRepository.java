package springboot.usecase.one.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.usecase.one.entity.CustomerTxnsEntity;

@Repository
public interface CustomerTxnsRepository extends JpaRepository<CustomerTxnsEntity, Long> {
}
