package com.tdevred.rentals.entities;

import com.tdevred.rentals.authentication.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Table(name = "rentals")
@Entity
@Getter
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    private String name;

    @Setter
    private BigDecimal surface;

    @Setter
    private BigDecimal price;

    @Setter
    private String picture;

    @Setter
    private String description;

    @Setter
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @Setter
    @Column(name = "owner_id", updatable = false, insertable = false)
    private int ownerId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
