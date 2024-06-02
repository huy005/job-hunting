package iiproject.jobhunting.repositories;

import iiproject.jobhunting.entities.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvRepository extends JpaRepository<Cv, Integer> {
//   String getFileName(String fileName);
}
