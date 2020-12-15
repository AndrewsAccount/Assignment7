package com.meritamerica.assignment6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.assignment6.models.AccountHolder;

public interface AccountHolderRepository extends JpaRepository <AccountHolder, Integer> {

}
