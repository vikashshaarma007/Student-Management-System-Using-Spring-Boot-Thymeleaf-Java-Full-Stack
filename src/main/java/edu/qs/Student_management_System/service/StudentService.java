package edu.qs.Student_management_System.service;

import java.util.List;

import org.springframework.data.domain.Page;

import edu.qs.Student_management_System.entity.Student;



public interface StudentService {
	List<Student> getAllStudent();
	void saveStudent(Student student);
	Student getStudentById(long id);
	
     void deleteStudentById(long id);
	
	Page<Student> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

	Page<Student> searchStudents(int pageNo, int pageSize, String sortField, String sortDir, String keyword);

}

	


