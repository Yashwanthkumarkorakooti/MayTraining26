package com.maverickbank.service;

import com.maverickbank.exception.ResourceNotFoundException;
import com.maverickbank.model.Users;
import com.maverickbank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<Users> getAll() {
        return userRepository.findAll();
    }

    public void addUser(Users users) {
        userRepository.save(users);
    }

    public Users getById(int id) {
        return userRepository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Invalid User Id"));
    }

    public void deleteById(int id) {
        getById(id);
        userRepository.deleteById(id);
    }

    public void updateUser(int id, Users user) {
        Users users = getById(id);

        users.setUsername(user.getUsername());
        users.setPassword(user.getPassword());
        users.setEmail(user.getEmail());
        users.setRole(user.getRole());
        users.setStatus(user.getStatus());
        users.setPhone(user.getPhone());

        userRepository.save(users);
    }
}
