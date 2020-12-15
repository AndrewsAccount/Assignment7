package com.meritamerica.assignment6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.assignment6.models.AccountHolderContactDetails;

public interface DetailsRepository extends JpaRepository<AccountHolderContactDetails, Integer>{

}
