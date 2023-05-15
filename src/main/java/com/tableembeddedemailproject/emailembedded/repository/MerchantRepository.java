package com.tableembeddedemailproject.emailembedded.repository;

import com.tableembeddedemailproject.emailembedded.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant,Integer> {

@Query(value = "SELECT * FROM merchant_details",nativeQuery = true)
List<Merchant> getMerchant();


}
