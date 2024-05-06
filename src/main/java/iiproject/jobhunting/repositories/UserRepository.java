package iiproject.jobhunting.repositories;

import iiproject.jobhunting.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();
    Optional<User> findByEmail(String theEmail);
    Optional<User> findByUserId(int id);
}
