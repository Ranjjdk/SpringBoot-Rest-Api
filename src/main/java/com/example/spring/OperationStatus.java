package com.example.spring;



import jakarta.persistence.*;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;

@Entity
@EnableJpaRepositories
public class OperationStatus {

	  
	
    public OperationStatus() {
		
	}
    
    

	public OperationStatus(Long id, String status, LocalDateTime timestamp) {
		
		this.id = id;
		this.status = status;
		this.timestamp = timestamp;
	}



	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private LocalDateTime timestamp;
				
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
