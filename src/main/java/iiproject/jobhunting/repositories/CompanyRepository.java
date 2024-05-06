package iiproject.jobhunting.repositories;

import iiproject.jobhunting.entities.Company;
import iiproject.jobhunting.entities.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
