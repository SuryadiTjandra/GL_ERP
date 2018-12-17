package ags.goldenlionerp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

	@GetMapping({"/", "/index", "home"})
	public String indexPage() {
		return "index";
	}
}
