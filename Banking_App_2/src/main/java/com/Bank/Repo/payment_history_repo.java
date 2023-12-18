package com.Bank.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Bank.Entity.PaymentHistory;

@Repository
public interface payment_history_repo extends CrudRepository<PaymentHistory, Integer> {
	
	@Query(value = "SELECT * FROM v_payments WHERE user_id = :user_id", nativeQuery = true)
	List<PaymentHistory> getPaymentRecordsById(@Param("user_id") int user_id);
	
}
