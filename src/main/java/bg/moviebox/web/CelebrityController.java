package bg.moviebox.web;

import bg.moviebox.model.dtos.AddCelebrityDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/celebrities")
public class CelebrityController {

    @GetMapping("/add")
    public String newCelebrity(Model model) {
        if (!model.containsAttribute("addCelebrityDTO")) {
            model.addAttribute("addCelebrityDTO", AddCelebrityDTO.empty());
        }

        return "/celebrity-add";
    }

    @PostMapping("/add")
    public String addCelebrity(AddCelebrityDTO addCelebrityDTO) {

        return "/celebrity-add";
    }

    @GetMapping("/all")
    public String getAllCelebrities() {
        return "/celebrities";
    }
}
