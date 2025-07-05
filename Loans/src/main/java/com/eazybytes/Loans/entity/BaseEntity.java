package com.eazybytes.Loans.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
	
	@CreatedBy
	@Column(updatable = false)
	private String createdBy;
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;
	
	@LastModifiedBy
	@Column(insertable = false)
	private String updatedBy;
	
	@Column(insertable = false)
	@LastModifiedDate
	private LocalDateTime updatedAt;

}
