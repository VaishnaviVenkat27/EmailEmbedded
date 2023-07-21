package com.tableembeddedemailproject.emailembedded.repository;

import com.tableembeddedemailproject.emailembedded.model.MerchantDetailsChargeback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantDetailsChargebackRepository extends JpaRepository<MerchantDetailsChargeback,Long> {
}
