package springboot.usecase.one.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.usecase.one.entity.BankCatalogEntity;

@Repository
public interface BankCatalogRepository extends JpaRepository<BankCatalogEntity, Long> {
	Optional<BankCatalogEntity> findByBankCode(Long code);
	
	List<BankCatalogEntity> findByActive(String active);
}
