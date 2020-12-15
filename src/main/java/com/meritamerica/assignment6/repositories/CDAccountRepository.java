package com.meritamerica.assignment6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.assignment6.models.CDAccount;

public interface CDAccountRepository extends JpaRepository <CDAccount, Integer>{ 

}
