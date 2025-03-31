package edu.poly.demo_api.service;
import edu.poly.demo_api.entity.User;
import edu.poly.demo_api.exception.UserNotFoundException;
import edu.poly.demo_api.respository.UserRespository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRespository userRespository;

    public UserService(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    public List<User> getAllUser(){
        return userRespository.findAll();
    }

    // Tạo User (Cần vì có cập nhật DB)
    @Transactional
    public User createUser(User user){
        validateUser(user);
        return userRespository.save(user);
    }


    @Transactional
    public User updateUser(Long id, User userDetails){
        User user = userRespository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User không tồn tại"));

        validateUser(userDetails);

        updateNonNullProperties(userDetails, user);

        return userRespository.save(user);

    }

    @Transactional
    public void deleteUser(Long id){
        if (!userRespository.existsById(id)) {
            throw new UserNotFoundException("User không tồn tại");
        }
        userRespository.deleteById(id);
    }

    public User getUserById(Long id){
        return userRespository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User không tồn tại"));

    }
    // Kiểm tra null
    private void validateUser(User user) {
        List<String> errors = new ArrayList<>();

        if (user == null) {
            throw new IllegalArgumentException("Thông tin User không được để trống");
        }
        if (!StringUtils.hasText(user.getName())) {
            errors.add("Tên không được để trống");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            errors.add("Email không được để trống");
        }
        if (!StringUtils.hasText(user.getPhone())) {
            errors.add("Số điện thoại không được để trống");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("; ", errors));  // Gộp tất cả lỗi thành một chuỗi
        }
    }

    // Chỉ sao chép nếu giá trị không null
    private void updateNonNullProperties(User source, User target) {
        if (source.getName() != null) target.setName(source.getName());
        if (source.getEmail() != null) target.setEmail(source.getEmail());
        if (source.getPhone() != null) target.setPhone(source.getPhone());
    }
}


