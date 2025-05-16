package ru.maximister.bank.service;

import ru.maximister.bank.dto.*;


public interface RoleService {

    RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO);
    RoleResponseDTO updateRole(Long id, RoleRequestDTO roleRequestDTO);
    void deleteRoleById(Long id);
    RoleResponseDTO getRoleById(Long id);
    RoleResponseDTO getRoleByName(String name);
    PageResponseDTO<RoleResponseDTO> getAllRoles(int page, int size);
    PageResponseDTO<RoleResponseDTO> searchRoles(String query, int page, int size);
}
