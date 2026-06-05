package com.practice.repository;

import com.practice.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Integer> {
    @Query("""
            select j from Job j
""")
    Page<Job> getAllJobs(Pageable pageable);
}
