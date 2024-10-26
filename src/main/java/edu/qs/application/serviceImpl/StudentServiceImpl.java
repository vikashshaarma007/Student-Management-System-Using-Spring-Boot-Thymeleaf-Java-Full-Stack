package edu.qs.application.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.qs.application.model.Student;
import edu.qs.application.repository.StudentRepository;
import edu.qs.application.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public void saveStudent(Student student) {
		this.studentRepository.save(student);
	}

	@Override
	public Student getStudentById(long id) {
		Optional<Student> optional = studentRepository.findById(id);
		Student student = null;
		if (optional.isPresent()) {
			student = optional.get();
		} else {
			throw new RuntimeException(" Student not found for id :: " + id);
		}
		return student;
	}

	@Override
	public void deleteStudentById(long id) {
		this.studentRepository.deleteById(id);
	}

	@Override
	public Page<Student> searchStudents(int pageNo, int pageSize, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Student> page;
        if (keyword != null && !keyword.isEmpty()) {
            page = studentRepository.findByFirstNameContainingOrLastNameContaining(keyword, keyword, pageable);
        } else {
            page = studentRepository.findAll(pageable);
        }

        return page;
    }

	@Override
	public Page<Student> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
        
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.studentRepository.findAll(pageable);
		
	}

}
