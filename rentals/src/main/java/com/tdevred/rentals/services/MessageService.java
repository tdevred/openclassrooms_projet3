package com.tdevred.rentals.services;

import com.tdevred.rentals.authentication.entities.User;
import com.tdevred.rentals.entities.Message;
import com.tdevred.rentals.entities.Rental;
import com.tdevred.rentals.repositories.MessageRepository;
import com.tdevred.rentals.repositories.RentalRepository;
import com.tdevred.rentals.services.exceptions.UnknownRentalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, RentalRepository rentalRepository) {
        this.messageRepository = messageRepository;
        this.rentalRepository = rentalRepository;
    }

    public void sendMessage(User user, String messageText, int rentalId) throws UnknownRentalException {
        Message message = new Message();

        // check if rental exists
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(UnknownRentalException::new);

        message.setMessage(messageText);
        message.setUser(user);
        message.setRental(rental);

        messageRepository.save(message);
    }
}
