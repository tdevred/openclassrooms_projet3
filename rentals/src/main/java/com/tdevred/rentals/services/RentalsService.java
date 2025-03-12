package com.tdevred.rentals.services;

import com.tdevred.rentals.authentication.entities.User;
import com.tdevred.rentals.entities.Rental;
import com.tdevred.rentals.presentation.forms.RentalModifyForm;
import com.tdevred.rentals.repositories.RentalRepository;
import com.tdevred.rentals.services.exceptions.NotAuthorizedToModifyRentalException;
import com.tdevred.rentals.services.exceptions.UnknownRentalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RentalsService {

    private final RentalRepository rentalRepository;

    private final StorageService storageService;


    @Autowired
    public RentalsService(RentalRepository rentalRepository,  StorageService storageService) {
        this.rentalRepository = rentalRepository;
        this.storageService = storageService;
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(int id) {
        return rentalRepository.findById(id);
    }

    public void addRental(User user, String name, BigDecimal surface, BigDecimal price, MultipartFile picture, String description) {
        storageService.store(picture);

        Rental rental = new Rental();

        rental.setOwner(user);
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setPicture(storageService.getUrlFor(picture.getOriginalFilename()));
        rental.setDescription(description);

        try {
            rentalRepository.save(rental);
        } catch(RuntimeException e) {
            // if a problem happens in rental, delete the file
            storageService.delete(picture.getName());
        }
    }

    public void updateRental(User user, int rentalId, RentalModifyForm modifications) throws NotAuthorizedToModifyRentalException, UnknownRentalException {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(com.tdevred.rentals.services.exceptions.UnknownRentalException::new);

        if(rental.getOwnerId() != user.getId()) {
            throw new NotAuthorizedToModifyRentalException();
        }

        // go through all modifications and make them
        if(modifications.getDescription() != null) {
            rental.setDescription(modifications.getDescription());
        }

        if(modifications.getName() != null) {
            rental.setName(modifications.getName());
        }

        if(modifications.getPrice() != null) {
            rental.setPrice(modifications.getPrice());
        }

        if(modifications.getDescription() != null) {
            rental.setSurface(modifications.getSurface());
        }

        rentalRepository.save(rental);
    }
}
