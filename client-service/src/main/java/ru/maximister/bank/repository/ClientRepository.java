package ru.maximister.bank.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.maximister.bank.entity.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {

    Optional<Client> findByCin(String cin);

    boolean existsByCin(String cin);

    boolean existsByEmail(String email);

    boolean existsById(@NotNull String id);

    @Query("select c from Client c where c.firstname like :kw or c.lastname like :kw or c.cin like :kw order by c.firstname asc")
    Page<Client> search(@Param("kw") String keyword, Pageable pageable);
}