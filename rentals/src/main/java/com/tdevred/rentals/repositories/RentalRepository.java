package com.tdevred.rentals.repositories;

import com.tdevred.rentals.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
}
