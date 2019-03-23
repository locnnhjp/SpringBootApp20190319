package com.myapp.example.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.example.MyData;

public interface MyDataRepository extends JpaRepository<MyData, Long> {
	public Optional<MyData> findById(Long id);
	
}
