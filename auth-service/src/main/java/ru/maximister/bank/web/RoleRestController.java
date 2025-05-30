package ru.maximister.bank.web;

import ru.maximister.bank.dto.PageResponseDTO;
import ru.maximister.bank.dto.RoleRequestDTO;
import ru.maximister.bank.dto.RoleResponseDTO;
import ru.maximister.bank.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleRestController {

    private final RoleService roleService;

    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @PostMapping("/create")
    public RoleResponseDTO createRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        return roleService.createRole(roleRequestDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @PutMapping("/update/{id}")
    public RoleResponseDTO updateRole(@PathVariable Long id, @RequestBody RoleRequestDTO roleRequestDTO) {
        return roleService.updateRole(id, roleRequestDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteRoleById(@PathVariable Long id) {
        roleService.deleteRoleById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @GetMapping("/get/{id}")
    public RoleResponseDTO getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @GetMapping("/find/{name}")
    public RoleResponseDTO getRoleByName(@PathVariable String name) {
        return roleService.getRoleByName(name);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @GetMapping("/all")
    public PageResponseDTO<RoleResponseDTO> getAllRoles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9") int size) {
        return roleService.getAllRoles(page, size);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @GetMapping("/search")
    public PageResponseDTO<RoleResponseDTO> searchRoles(@RequestParam(defaultValue = " ") String query, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9") int size) {
        return roleService.searchRoles(query, page, size);
    }
}
