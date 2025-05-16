package ru.maximister.bank.util.mappers;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import ru.maximister.bank.dto.ClientPageResponseDTO;
import ru.maximister.bank.dto.ClientRequestDTO;
import ru.maximister.bank.dto.ClientResponseDTO;
import ru.maximister.bank.entity.Client;

import java.util.List;

@UtilityClass
public class Mapper {
    public static @NotNull Client fromCustomer(final @NotNull ClientRequestDTO dto){
        final Client client = new Client();
        client.setFirstname(dto.getFirstname());
        client.setLastname(dto.getLastname());
        client.setPlaceOfBirth(dto.getPlaceOfBirth());
        client.setDateOfBirth(dto.getDateOfBirth());
        client.setGender(dto.getGender());
        client.setNationality(dto.getNationality());
        client.setCin(dto.getCin());
        client.setEmail(dto.getEmail());
        return client;
    }


    public static ClientResponseDTO fromCustomer(final @NotNull Client client){
        return ClientResponseDTO.builder()
                .id(client.getId())
                .firstname(client.getFirstname())
                .lastname(client.getLastname())
                .placeOfBirth(client.getPlaceOfBirth())
                .dateOfBirth(client.getDateOfBirth())
                .gender(client.getGender())
                .nationality(client.getNationality())
                .cin(client.getCin())
                .email(client.getEmail())
                .createdDate(client.getCreatedDate())
                .createdBy(client.getCreatedBy())
                .lastModifiedDate(client.getLastModifiedDate())
                .lastModifiedBy(client.getLastModifiedBy())
                .build();
    }

    public static List<ClientResponseDTO> fromListOfCustomers(final @NotNull List<Client> clients){
        return clients.stream().map(Mapper::fromCustomer).toList();
    }

    public static ClientPageResponseDTO fromPageOfCustomers(final @NotNull Page<Client> pageOfCustomers){
        return ClientPageResponseDTO.builder()
                .customers(fromListOfCustomers(pageOfCustomers.getContent()))
                .totalPages(pageOfCustomers.getTotalPages())
                .totalElements(pageOfCustomers.getTotalElements())
                .size(pageOfCustomers.getSize())
                .numberOfElements(pageOfCustomers.getNumberOfElements())
                .number(pageOfCustomers.getNumber())
                .hasContent(pageOfCustomers.hasContent())
                .isFirst(pageOfCustomers.isFirst())
                .isLast(pageOfCustomers.isLast())
                .hasPrevious(pageOfCustomers.hasPrevious())
                .hasNext(pageOfCustomers.hasNext())
                .build();
    }

    @Contract("_, _ -> param1")
    public static @NotNull Client updateCustomerItems(@NotNull Client client, final @NotNull ClientRequestDTO dto){
        client.setFirstname(dto.getFirstname());
        client.setGender(dto.getGender());
        client.setNationality(dto.getNationality());
        client.setCin(dto.getCin());
        client.setEmail(dto.getEmail());
        client.setLastname(dto.getLastname());
        client.setPlaceOfBirth(dto.getPlaceOfBirth());
        client.setDateOfBirth(dto.getDateOfBirth());
        return client;
    }
}