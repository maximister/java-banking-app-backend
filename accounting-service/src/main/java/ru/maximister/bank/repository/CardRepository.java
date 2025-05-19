package ru.maximister.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maximister.bank.entity.Card;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с банковскими картами
 */
public interface CardRepository extends JpaRepository<Card, String> {
    /**
     * Поиск карты по ID связанного банковского счета
     * @param accountId ID банковского счета
     * @return Optional с найденной картой или пустой Optional
     */
    Optional<Card> findByAccountId(String accountId);

    List<Card> findByCustomerId(String customerId);

    /**
     * Поиск карты по номеру карты
     * @param cardNumber номер карты (16 цифр)
     * @return Optional с найденной картой или пустой Optional
     */
    Optional<Card> findByCardNumber(String cardNumber);

    /**
     * Проверка существования карты для указанного банковского счета
     * @param accountId ID банковского счета
     * @return true если карта существует, false если нет
     */
    boolean existsByAccountId(String accountId);
} 