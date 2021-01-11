package com.meritamerica.assignment6.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.assignment6.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);

}
