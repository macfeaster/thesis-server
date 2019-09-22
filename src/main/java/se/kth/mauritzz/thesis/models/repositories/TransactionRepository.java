package se.kth.mauritzz.thesis.models.repositories;

import org.springframework.data.repository.CrudRepository;
import se.kth.mauritzz.thesis.models.entities.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, String> {
}
