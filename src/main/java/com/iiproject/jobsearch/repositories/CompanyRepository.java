package com.iiproject.jobsearch.repositories;

import com.iiproject.jobsearch.dto.ICompanyCount;
import com.iiproject.jobsearch.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
//    @Query(value = "SELECT fc.company_id AS companyId, count(*) AS companyTotal " +
//            "FROM follow_company_db AS fc " +
//            "GROUP BY fc.company_id " +
//            "ORDER BY companyTotal desc " +
//            "LIMIT 3;", nativeQuery = true)
//    List<ICompanyCount> countTotalCompanysByIdNative();
//    Optional<Company> findById(int theId);
}
