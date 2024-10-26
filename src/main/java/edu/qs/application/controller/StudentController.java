package edu.qs.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.qs.application.model.Student;
import edu.qs.application.service.StudentService;


@Controller
public class StudentController {
	@Autowired
	private StudentService studentService ;
	// display list of employees
		@GetMapping("/")
		public String viewHomePage(Model model, @Param("keyword") String keyword) {
			return findPaginated(1, "firstName", "asc", keyword, model);
			//model.addAttribute("listStudent", studentService.getAllStudents());
			//return "index";
			//return findPaginated(1, "firstName", "asc", model);		
		}
		
		@GetMapping("/showNewStudentForm")
		public String showNewStudentForm(Model model) {
			// create model attribute to bind form data
			Student student = new Student();
			model.addAttribute("student", student);
			return "new_student";
		}
		
		@PostMapping("/saveStudent")
		public String saveStudent(@ModelAttribute("student") Student student) {
			// save employee to database
			studentService.saveStudent(student);
			return "redirect:/";
		}
		
		@GetMapping("/showFormForUpdate/{id}")
		public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
			
			// get employee from the service
			Student student = studentService.getStudentById(id);
			
			// set employee as a model attribute to pre-populate the form
			model.addAttribute("student", student);
			return "update_student";
		}
		
		@GetMapping("/deleteStudent/{id}")
		public String deleteStudent(@PathVariable (value = "id") long id) {
			
			// call delete employee method 
			this.studentService.deleteStudentById(id);
			return "redirect:/";
		}
		
		
		@GetMapping("/page/{pageNo}")
	    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
	                                @RequestParam("sortField") String sortField,
	                                @RequestParam("sortDir") String sortDir,
	                                @RequestParam(value = "keyword", required = false) String keyword,
	                                Model model) {
	        int pageSize = 5;

	        Page<Student> page;
	        if (keyword != null && !keyword.isEmpty()) {
	            page = studentService.searchStudents(pageNo, pageSize, sortField, sortDir, keyword);
	        } else {
	            page = studentService.findPaginated(pageNo, pageSize, sortField, sortDir);
	        }
	        List<Student> listStudents = page.getContent();

	        model.addAttribute("currentPage", pageNo);
	        model.addAttribute("totalPages", page.getTotalPages());
	        model.addAttribute("totalItems", page.getTotalElements());

	        model.addAttribute("sortField", sortField);
	        model.addAttribute("sortDir", sortDir);
	        model.addAttribute("keyword", keyword);
	        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

	        model.addAttribute("listStudents", listStudents);
	        return "index";
	    }


}
