package edu.qs.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.qs.application.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	Page<Student> findByFirstNameContainingOrLastNameContaining(String keyword, String keyword2, Pageable pageable);


}
