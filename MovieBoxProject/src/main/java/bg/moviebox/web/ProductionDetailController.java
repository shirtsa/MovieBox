package bg.moviebox.web;

import bg.moviebox.service.ProductionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productions")
public class ProductionDetailController {

    private final ProductionService productionService;

    public ProductionDetailController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping("/{id}")
    public String productionDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("productionDetails", productionService.getProductionDetails(id));
        return "details";
    }

    @DeleteMapping("/{id}")
    public String deleteProduction(@PathVariable("id") Long id) {
        productionService.deleteProduction(id);
        return "redirect:/productions/all";
    }
}
