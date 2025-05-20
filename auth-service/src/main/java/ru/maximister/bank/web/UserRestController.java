package ru.maximister.bank.web;

import lombok.extern.slf4j.Slf4j;
import ru.maximister.bank.dto.*;
import ru.maximister.bank.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserResponseDTO createUser(@RequestBody UserRequestDTO dto) {
        log.info("Got {}", dto);
        return userService.createUser(dto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @PutMapping("/update/{id}")
    public UserResponseDTO updateUser(@PathVariable String id, @RequestBody UserRequestDTO dto) {
        return userService.updateUser(id, dto);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN','SUPER_ADMIN')")
    @PostMapping("/pwd")
    public UserResponseDTO updatePassword(@RequestBody UpdatePasswordRequestDTO dto) {
        String username = getCurrentUsername();
        return userService.updatePassword(username, dto);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN','SUPER_ADMIN')")
    @GetMapping("/status/{id}")
    public UserResponseDTO updateStatus(@PathVariable String id) {
        return userService.updateUserStatus(id);
    }



    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @PostMapping("/roles/add")
    public UserResponseDTO addRoleToUser(@RequestBody UserRoleRequestDTO dto) {
        return userService.addRoleToUser(dto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @PostMapping("/roles/remove")
    public UserResponseDTO removeRoleFromUser(@RequestBody UserRoleRequestDTO dto) {
        return userService.removeRoleFromUser(dto);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN','SUPER_ADMIN')")
    @GetMapping("/get/{id}")
    public UserResponseDTO getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN','SUPER_ADMIN')")
    @GetMapping("/profile")
    public UserResponseDTO getUserByUsername() {
        log.info("aboba");
        String username = getCurrentUsername();
        log.info("{}", username);
        return userService.getUserByUsername(username);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @GetMapping("/all")
    public PageResponseDTO<UserResponseDTO> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return userService.getAllUsers(page, size);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @GetMapping("/search")
    public PageResponseDTO<UserResponseDTO> searchUsers(@RequestParam(defaultValue = " ") String query,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        return userService.searchUsers(query, page, size);
    }


    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("{}", principal);
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return principal.toString();
    }
}
