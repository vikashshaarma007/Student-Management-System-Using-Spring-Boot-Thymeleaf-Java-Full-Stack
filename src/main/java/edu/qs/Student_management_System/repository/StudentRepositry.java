package edu.qs.Student_management_System.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.qs.Student_management_System.entity.Student;

@Repository
public interface StudentRepositry extends JpaRepository<Student, Long> {

	 static Page<Student> findByFirstNameContainingOrLastNameContaining(String keyword, String keyword2, 
			Pageable pageable){
	return null;
		
	}

}
