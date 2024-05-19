package iiproject.jobhunting.repositories;

import iiproject.jobhunting.dto.IJobDescriptionCount;
import iiproject.jobhunting.entities.JobDescription;
import iiproject.jobhunting.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobDescriptionRepository extends JpaRepository<JobDescription, Integer> {
//    @Query(value = "SELECT ap.recruitment_id AS recruitmentId, count(*) AS recruitmentTotal " +
//            "FROM apply_post_db " +
//            "AS ap " +
//            "GROUP BY ap.recruitment_id " +
//            "ORDER BY recruitmentTotal desc " +
//            "LIMIT 3;", nativeQuery = true)
//    List<IJobDescriptionCount> countTotalRecruitmentsByIdNative();
    Optional<JobDescription> findById(int theId);

//    Get the quantity of recruitment by Id
    @Query(value = "SELECT jd.job_description_id AS jobDescriptionId, jd.quantity AS jobDescriptionQuantity " +
            "FROM job_description_db AS jd " +
            "WHERE jd.delete_status = 0 " +
            "ORDER BY jobDescriptionQuantity DESC " +
            "LIMIT 4;", nativeQuery = true)
    List<IJobDescriptionCount> getJobDescriptionQuantityByIdNative();
}
