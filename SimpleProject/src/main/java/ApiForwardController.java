import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiForwardController {

	@GetMapping("hospital")
	public String hospitalPage() {
		return "api/api";
	}
}
