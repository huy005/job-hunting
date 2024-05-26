package iiproject.jobhunting.repositories;

import iiproject.jobhunting.entities.AppliedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppliedJobRepository extends JpaRepository<AppliedJob, Integer> {
}
