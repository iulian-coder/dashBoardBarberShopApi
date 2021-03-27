package com.codecool.barbershop.barbershop.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@JsonIgnoreProperties(
        value = {"createdDate", "updatedDate"}, allowGetters = true)
public abstract class AuditModel implements Serializable {

/*
Implement the Serializable when needed to store a copy of the object, send them to another process which runs on
the same system or over the network.
 */

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column( nullable = false, updatable = false)
    private Date createdDate;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedDate;

}