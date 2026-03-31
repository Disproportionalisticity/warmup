package com.disproportionalisticity.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.disproportionalisticity.school.entity.SchoolClass;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long>, JpaSpecificationExecutor<SchoolClass>  {
    
}
