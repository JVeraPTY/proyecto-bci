package com.smartjob;
import java.util.UUID;
import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    List<User> findAll();
    <S extends User> S save(S entity);    
}
