package com.maverickbank.controller;

import com.maverickbank.exception.ResourceNotFoundException;
import com.maverickbank.model.Users;
import com.maverickbank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/users/all")
    public List<Users> getAll(){
        return userService.getAll();
    }

    @PostMapping("/api/users/add")
    public void addUser(@RequestBody Users users){
        userService.addUser(users);
    }

    @GetMapping("/api/users/get-one/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id){
        try{
            Users user = userService.getById(id);
            return ResponseEntity
                    .ok(user);
        }catch (ResourceNotFoundException e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/api/users/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable int id){
        try{
            userService.deleteById(id);
            return ResponseEntity
                    .ok()
                    .build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/api/users/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable int id,@RequestBody Users user){
        try{
            userService.updateUser(id,user);
            return ResponseEntity
                    .ok()
                    .build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
