package com.tdevred.rentals.entities;

import com.tdevred.rentals.authentication.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "messages")
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "rental_id", referencedColumnName="id")
    private Rental rental;

    @Getter
    @Setter
    @Column(name = "rental_id", updatable = false, insertable = false)
    private int rentalId;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Getter
    @Setter
    @Column(name = "user_id", updatable = false, insertable = false)
    private int userId;

    @Getter
    @Setter
    private String message;

    @CreationTimestamp
    @Getter
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Getter
    private LocalDateTime updatedAt;
}
