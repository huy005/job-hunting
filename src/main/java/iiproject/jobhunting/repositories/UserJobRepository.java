package iiproject.jobhunting.repositories;

import iiproject.jobhunting.entities.UserJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJobRepository extends JpaRepository<UserJob, Integer> {
}
