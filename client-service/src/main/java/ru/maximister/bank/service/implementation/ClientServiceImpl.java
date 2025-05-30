package ru.maximister.bank.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.maximister.bank.dto.ClientPageResponseDTO;
import ru.maximister.bank.dto.ClientRequestDTO;
import ru.maximister.bank.dto.ClientResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maximister.bank.entity.Client;
import ru.maximister.bank.excetion.ClientNotFoundException;
import ru.maximister.bank.excetion.FieldValidationException;
import ru.maximister.bank.repository.ClientRepository;
import ru.maximister.bank.service.ClientService;
import ru.maximister.bank.util.mappers.Mapper;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientResponseDTO getClientById(String id) {
        log.info("In getCustomerById()");
        Client client = findClientById(id);
        log.info("Customer with id '{}' found", id);
        return Mapper.fromCustomer(client);
    }

    @Override
    public ClientResponseDTO getClientByEmail(String email) {
        log.info("In getCustomerByCin()");
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new ClientNotFoundException(
                        String.format("Customer with email '%s' not found", email)
                ));
        log.info("Customer with cin '{}' found", email);
        return Mapper.fromCustomer(client);
    }

    @Override
    public ClientPageResponseDTO getAllClients(int page, int size) {
        log.info("In getAllCustomers()");
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> clients = clientRepository.findAll(pageable);
        log.info("{} clients found", clients.getTotalElements());
        return Mapper.fromPageOfCustomers(clients);
    }

    @Override
    public ClientPageResponseDTO searchClients(String keyword, int page, int size) {
        log.info("In searchCustomers()");
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> clients = clientRepository.search("%" + keyword + "%", pageable);
        log.info("{} clients found.", clients.getTotalElements());
        return Mapper.fromPageOfCustomers(clients);
    }

    @Transactional
    @Override
    public ClientResponseDTO createClient(@NotNull ClientRequestDTO dto) {
        validateBeforeCreateClient(dto.getEmail());
        Client client = Mapper.fromCustomer(dto);
        Client savedClient = clientRepository.save(client);
        log.info("Client created with id {}", savedClient.getId());
        return Mapper.fromCustomer(savedClient);
    }


    @Transactional
    @Override
    public ClientResponseDTO updateClient(String id, @NotNull ClientRequestDTO dto) {
        Client client = findClientById(id);
        validationBeforeUpdateUser(client.getEmail(), dto.getEmail());
        Client clientToUpdate = Mapper.updateCustomerItems(client, dto);
        Client clientUpdated = clientRepository.save(clientToUpdate);
        log.info("Client with id {} updated", clientUpdated.getId());
        return Mapper.fromCustomer(clientUpdated);
    }

    @Transactional
    @Override
    public void deleteClientById(String id) {
        log.info("In deleteCustomerById()");
        clientRepository.deleteById(id);
        log.info("Client with id '{}' deleted", id);
    }

    private Client findClientById(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client with id %s not found", id)));
    }

    private void validateBeforeCreateClient(String email) {
        List<String> messages = new ArrayList<>();
        if (clientRepository.existsByEmail(email)) {
            messages.add(String.format("Client with email '%s' already exist", email));
        }
        if (!messages.isEmpty()) {
            throw new FieldValidationException("Invalid data", messages);
        }
    }

    private void validationBeforeUpdateUser(@NotNull String oldEmail, String newEmail) {
        List<String> messages = new ArrayList<>();
        if (!oldEmail.equals(newEmail) && clientRepository.existsByEmail(newEmail)) {
            messages.add(String.format("Client with email '%s' already exist", newEmail));
        }
        if (!messages.isEmpty()) {
            throw new FieldValidationException("Invalid data", messages);
        }
    }
}
