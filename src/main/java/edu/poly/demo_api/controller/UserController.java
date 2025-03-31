package edu.poly.demo_api.controller;
import edu.poly.demo_api.dto.UserDTO;
import edu.poly.demo_api.entity.User;
import edu.poly.demo_api.exception.UserNotFoundException;
import edu.poly.demo_api.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDTO userDTO) {
        try {
            User user = new User();
            BeanUtils.copyProperties(userDTO, user);
            User createdUser = userService.createUser(user);

            UserDTO responseDTO = new UserDTO();
            BeanUtils.copyProperties(createdUser, responseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Tạo User thành công",
                    "user", responseDTO
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("errors", ex.getMessage().split("; ")));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>>  updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            User user = new User();
            BeanUtils.copyProperties(userDTO, user);
            User updatedUser = userService.updateUser(id, user);
            UserDTO responseDTO = new UserDTO();
            BeanUtils.copyProperties(updatedUser, responseDTO);

            return ResponseEntity.ok().body(
                    Map.of(
                            "message", "Cập nhật User thành công",
                            "user", responseDTO
                    )
            );
        }catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", ex.getMessage()
            ));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", ex.getMessage()
            ));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(Map.of("message", "Xóa User thành công"));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("errors", ex.getMessage().split("; ")));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }
}