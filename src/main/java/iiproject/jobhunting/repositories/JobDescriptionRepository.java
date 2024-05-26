package iiproject.jobhunting.repositories;

import iiproject.jobhunting.dto.CompanyJdDto;
import iiproject.jobhunting.dto.IJobDescriptionCount;
import iiproject.jobhunting.entities.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobDescriptionRepository extends JpaRepository<JobDescription, Integer> {

    @Query("select new iiproject.jobhunting.dto.CompanyJdDto" +
            "(jd.jobDescriptionAddress, jd.title, jd.jobDescriptionType, jd.salary, jd.position, " +
            "jd.description, c.companyName, c.companyAddress, c.companyEmail, c.companyDescription) " +
            "from JobDescription as jd " +
            "inner join jd.company as c " +
            "where c.companyName like :keyword " +
            "or c.companyAddress like :keyword " +
            "or c.companyEmail like :keyword " +
            "or c.companyDescription like :keyword " +
            "or jd.jobDescriptionAddress like :keyword " +
            "or jd.title like :keyword " +
            "or jd.jobDescriptionType like :keyword " +
            "or jd.salary like :keyword " +
            "or jd.position like :keyword " +
            "or jd.description like :keyword ")
    List<CompanyJdDto> searchCompanyJd(@Param("keyword") String keyword);

//    Get the quantity of recruitment by Id
    @Query(value = "SELECT jd.job_description_id AS jobDescriptionId, jd.quantity AS jobDescriptionQuantity " +
            "FROM job_description_db AS jd " +
            "WHERE jd.delete_status = 0 " +
            "ORDER BY jobDescriptionQuantity DESC " +
            "LIMIT 4;", nativeQuery = true)
    List<IJobDescriptionCount> getJobDescriptionQuantityByIdNative();
}
