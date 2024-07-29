package bg.moviebox.web;

import bg.moviebox.model.dtos.AddProductionDTO;
import bg.moviebox.model.enums.Genre;
import bg.moviebox.model.enums.ProductionType;
import bg.moviebox.service.ProductionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productions")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @ModelAttribute("productionTypes")
    public ProductionType[] productionTypes() {
        return ProductionType.values();
    }

    @ModelAttribute("genreTypes")
    public Genre[] genreTypes() {
        return Genre.values();
    }

    @GetMapping("/add")
    public String newProduction(Model model) {

        if (!model.containsAttribute("addProductionDTO")) {
            model.addAttribute("addProductionDTO", AddProductionDTO.empty());
        }
        model.addAttribute("genres", Genre.values());

        return "/production-add";
    }

    // all errors from input info comes in the bindingResult
    @PostMapping("/add")
    public String createProduction(@Valid AddProductionDTO addProductionDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        // addFlashAttribute exist for short period of time to remembers the info
        // from the current session so can be used in the next session
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addProductionDTO", addProductionDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addProductionDTO", bindingResult);
            return "redirect:/productions/add";
        }

        long newOfferId = productionService.createProduction(addProductionDTO);

        return "redirect:/productions/" + newOfferId;
    }

    @GetMapping("/all")
    public String getAllProductions(Model model) {

        model.addAttribute("allProductions", productionService.getAllProductionsSummary());

        return "/productions";
    }

    @GetMapping("/movies")
    public String movies(Model model) {

        model.addAttribute("allMovieProductions", productionService.getAllMovieProductions());

        return "/movies";
    }

    @GetMapping("/tv")
    public String tv(Model model) {

        model.addAttribute("allTvProductions", productionService.getAllTvProductions());

        return "/tv";
    }

    @GetMapping("/list")
    public String myList(Model model) {

        model.addAttribute("myList");

        return "my-list";
    }



}
