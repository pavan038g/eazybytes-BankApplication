package com.eazybytes.cards.entitty;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @ToString
public class BaseEntity {
	
	@CreatedBy
	private String createdBy;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	@LastModifiedBy
	private String updatedBy;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;

}
