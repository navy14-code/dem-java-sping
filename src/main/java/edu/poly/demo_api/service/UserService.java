package edu.poly.demo_api.service;
import edu.poly.demo_api.entity.User;
import edu.poly.demo_api.respository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRespository userRespository;

    public List<User> getAllUser(){
        return userRespository.findAll();
    }

    public User createUser(User user){
        return userRespository.save(user);
    }

    public User updateUser(Long id, User userDetails){
        User user = userRespository.findById(id)
                .orElseThrow(()->new RuntimeException("User không tồn tại"));
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        return userRespository.save(user);

    }

    public void deleteUser(Long id){
        User user = userRespository.findById(id)
                .orElseThrow(()->new RuntimeException("User không tồn tại"));
        userRespository.deleteById(id);
    }

    public User getUserById(Long id){
        return userRespository.findById(id).orElseThrow(()->new RuntimeException("User không tồn tại"));

    }

}


