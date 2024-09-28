import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password) {
        Student student = studentService.authenticate(username, password);
        if (student != null) {
            return new ModelAndView("grades", "student", student);
        }
        return new ModelAndView("login", "message", "Invalid credentials");
    }

    @GetMapping("/grades")
    public ModelAndView getGrades(@RequestParam int studentId) {
        List<Grade> grades = studentService.getGrades(studentId);
        return new ModelAndView("grades", "grades", grades);
    }
}

