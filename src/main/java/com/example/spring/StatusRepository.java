package com.example.spring;

import org.springframework.data.jpa.repository.JpaRepository;



public interface StatusRepository  extends JpaRepository<OperationStatus, Long > {

}
