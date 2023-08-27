package com.iiproject.jobsearch.repositories;

import com.iiproject.jobsearch.dto.IRecruitmentCount;
import com.iiproject.jobsearch.entities.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {
//    @Query(value = "SELECT ap.recruitment_id AS recruitmentId, count(*) AS recruitmentTotal " +
//            "FROM apply_post_db " +
//            "AS ap " +
//            "GROUP BY ap.recruitment_id " +
//            "ORDER BY recruitmentTotal desc " +
//            "LIMIT 3;", nativeQuery = true)
//    List<IRecruitmentCount> countTotalRecruitmentsByIdNative();
    Optional<Recruitment> findById(int theId);

//    Get the quantity of recruitment by Id
    @Query(value = "SELECT rc.recruitment_id AS recruitmentId, rc.quantity AS recruitmentQuantity " +
            "FROM recruitment_db AS rc " +
            "ORDER BY recruitmentQuantity DESC " +
            "LIMIT 4;", nativeQuery = true)
    List<IRecruitmentCount> getRecruitmentQuantityByIdNative();
}
