package com.tdevred.rentals.repositories;

import com.tdevred.rentals.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
