package springboot.usecase.one.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.usecase.one.entity.BankCatalog;

@Repository
public interface BankCatalogRepository extends JpaRepository<BankCatalog, Long> {
	Optional<BankCatalog> findByBankCode(Long code);
}
