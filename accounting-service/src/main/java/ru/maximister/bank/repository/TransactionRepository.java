package ru.maximister.bank.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.maximister.bank.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByAccountId(String accountId);
    
    Page<Transaction> findByAccountId(String accountId, Pageable pageable);
    
    @Query("{'accountId': ?0, 'createdDate': {$gte: ?1, $lte: ?2}}")
    List<Transaction> findByAccountIdAndDateRange(String accountId, LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("{'accountId': ?0, 'type': ?1}")
    List<Transaction> findByAccountIdAndType(String accountId, String type);
} 