package edu.qs.Student_management_System.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.qs.Student_management_System.entity.Student;
import edu.qs.Student_management_System.repository.StudentRepositry;



@Service
public class StudentServiceImpl implements StudentService{
	@Autowired
	private StudentRepositry studentRepositry;
	

	@Override
	public List<Student> getAllStudent() {
		// TODO Auto-generated method stub
		return studentRepositry.findAll();
	}


	@Override
	public void saveStudent(Student student) {
		this.studentRepositry.save(student);
		
	}


	@Override
	public Student getStudentById(long id) {
		Optional<Student> optional = studentRepositry.findById(id);
		Student student = null;
		if(optional.isPresent()) {
			student = optional.get();
		}else {
			throw new RuntimeException("Employee not found for id :: " +id);
		}
		return student;
	}


	@Override
	public void deleteStudentById(long id) {
		this.studentRepositry.deleteById(id);
		
	}


	@Override
	public Page<Student> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		 Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
	            Sort.by(sortField).descending();
	        
	        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
	        return this.studentRepositry.findAll(pageable);
	}


	@Override
	public Page<Student> searchStudents(int pageNo, int pageSize, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Student> page;
        if (keyword != null && !keyword.isEmpty()) {
            page = StudentRepositry.findByFirstNameContainingOrLastNameContaining(keyword, keyword, pageable);
        } else {
            page = studentRepositry.findAll(pageable);
        }

        return page;
	}

}
