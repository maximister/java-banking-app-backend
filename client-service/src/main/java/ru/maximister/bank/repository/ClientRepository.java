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

    boolean existsByEmail(String email);

    boolean existsById(@NotNull String id);

    Optional<Client> findByEmail(String email);

    @Query("select c from Client c where c.firstname like :kw or c.lastname like :kw order by c.firstname asc")
    Page<Client> search(@Param("kw") String keyword, Pageable pageable);
}