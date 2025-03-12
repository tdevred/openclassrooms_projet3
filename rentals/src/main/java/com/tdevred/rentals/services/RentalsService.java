package com.tdevred.rentals.services;

import com.tdevred.rentals.entities.Rental;
import com.tdevred.rentals.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalsService {

    private final RentalRepository rentalRepository;

    @Autowired
    public RentalsService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }
}
