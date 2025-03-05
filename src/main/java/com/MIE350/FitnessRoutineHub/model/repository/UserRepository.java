package com.MIE350.FitnessRoutineHub.model.repository;


import com.MIE350.FitnessRoutineHub.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findFirstByUsername(String username);
}
