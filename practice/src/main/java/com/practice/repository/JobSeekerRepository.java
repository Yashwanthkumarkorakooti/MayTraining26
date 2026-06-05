package com.practice.repository;

import com.practice.model.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobSeekerRepository extends JpaRepository<JobSeeker,Integer> {
    @Query("""
        select jb from JobSeeker jb
        where jb.name = ?1
""")
    JobSeeker findByName(String applicantName);
}
