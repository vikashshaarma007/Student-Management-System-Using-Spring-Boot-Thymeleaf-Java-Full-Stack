package edu.qs.Student_management_System.controller;

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

import edu.qs.Student_management_System.entity.Student;
import edu.qs.Student_management_System.service.StudentService;




@Controller
public class StudentController {
	@Autowired
	private StudentService studentService;

	
		
	@GetMapping("/")
	public String liststudent(Model model, @Param("keyword") String keyword ) {
		return findPaginated(1, "firstName", "asc",keyword,false, model);
		//model.addAttribute("students",studentService.getAllStudent());
		//return "students";
		
	}
	
	@GetMapping("/showNewStudentFrom")
	public String showNewStudentFrom(Model model) {
		Student Student = new Student();
		model.addAttribute("student", Student);
		return "new_student";
		}
	
	@PostMapping("/saveStudent")
	public String saveStudent(@ModelAttribute("student") Student student) {
		studentService.saveStudent(student);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value ="id")long id, Model model ){
		//get employee from the srvice
		
		Student student = studentService.getStudentById(id);
		
		//set employee as model attribute to pre-popular
		model.addAttribute("student", student);
		return "update_student";
		}
	
	@GetMapping("/deleteStudent/{id}")
	public String deleteEmployee(@PathVariable(value = "id")long id) {
		this.studentService.deleteStudentById(id);
		return "redirect:/";
		
	}
	
	@GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                boolean sort, Model model) {
        int pageSize = 5;

        Page<Student> page;
        if (keyword != null && !keyword.isEmpty()) {
            page = studentService.searchStudents(pageNo, pageSize, sortField, sortDir, keyword);
        } else {
            page = studentService.findPaginated(pageNo, pageSize, sortField, sortDir);
        }
        List<Student> listStudent = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listStudent", listStudent);
        return "students";
    }
}