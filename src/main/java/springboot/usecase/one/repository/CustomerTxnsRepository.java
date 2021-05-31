package springboot.usecase.one.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.usecase.one.entity.CustomerTxns;

@Repository
public interface CustomerTxnsRepository extends JpaRepository<CustomerTxns, Long> {
	List<CustomerTxns> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
}
