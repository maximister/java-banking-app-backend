package ru.maximister.bank.service;


import ru.maximister.bank.dto.ClientPageResponseDTO;
import ru.maximister.bank.dto.ClientRequestDTO;
import ru.maximister.bank.dto.ClientResponseDTO;

public interface ClientService {

    ClientResponseDTO getClientById(String id);
    ClientResponseDTO getClientByCin(String cin);
    ClientPageResponseDTO getAllClients(int page, int size);
    ClientPageResponseDTO searchClients(String keyword, int page, int size);
    ClientResponseDTO createClient(ClientRequestDTO dto);
    ClientResponseDTO updateClient(String id, ClientRequestDTO dto);
    void deleteClientById(String id);
}
