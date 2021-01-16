package com.meritamerica.assignment6.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meritamerica.assignment6.models.AccountHolder;

@Repository
public interface AccountHolderRepository extends JpaRepository <AccountHolder, Integer> {
	
	Optional<AccountHolder> findById(Integer Id);

}
