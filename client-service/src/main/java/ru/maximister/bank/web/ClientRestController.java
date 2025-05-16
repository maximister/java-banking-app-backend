package ru.maximister.bank.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.maximister.bank.dto.ClientPageResponseDTO;
import ru.maximister.bank.dto.ClientRequestDTO;
import ru.maximister.bank.dto.ClientResponseDTO;
import ru.maximister.bank.service.ClientService;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class ClientRestController {

    private final ClientService clientService;

    @GetMapping("/get/{id}")
    public ClientResponseDTO getClientById(@PathVariable String id){
        return clientService.getClientById(id);
    }

    @GetMapping("/find/{cin}")
    public ClientResponseDTO getClientByCin(@PathVariable String cin){
        return clientService.getClientByCin(cin);
    }

    @GetMapping("/list")
    public ClientPageResponseDTO getAllClients(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "9") int size) {
        return clientService.getAllClients(page, size);
    }

    @GetMapping("/search")
    public ClientPageResponseDTO searchClients(@RequestParam(defaultValue = "") String keyword,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "9") int size) {
        return clientService.searchClients(keyword, page, size);
    }


    @PostMapping("/create")
    public ClientResponseDTO createClients(@RequestBody ClientRequestDTO dto){
        return clientService.createClient(dto);
    }

    @PutMapping("/update/{id}")
    public ClientResponseDTO updateClients(@PathVariable String id, @RequestBody ClientRequestDTO dto){
        return clientService.updateClient(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClientById(@PathVariable String id){
        clientService.deleteClientById(id);
    }
}
