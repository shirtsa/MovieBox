package bg.moviebox.web;

import bg.moviebox.model.entities.Production;
import bg.moviebox.model.user.MovieBoxUserDetails;
import bg.moviebox.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/playlist")
public class PlaylistController {

    private final UserService userService;

    public PlaylistController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{id}/add")
    public String addToPlaylist(@PathVariable Long id, Authentication authentication) {
        MovieBoxUserDetails movieBoxUserDetails = (MovieBoxUserDetails) authentication.getPrincipal();
        userService.addToPlaylist(id, movieBoxUserDetails);
        return "redirect:/playlist";
    }

    @PostMapping("/{id}/remove")
    public String removeFromPlaylist(@PathVariable Long id, Authentication authentication) {
        MovieBoxUserDetails movieBoxUserDetails = (MovieBoxUserDetails) authentication.getPrincipal();
        userService.removeFromPlaylist(id, movieBoxUserDetails);
        return "redirect:/playlist";
    }

    @GetMapping
    public String viewPlaylist(Model model, Authentication authentication) {
        MovieBoxUserDetails movieBoxUserDetails = (MovieBoxUserDetails) authentication.getPrincipal();
        Set<Production> playlist = userService.getUserPlaylist(movieBoxUserDetails);
        model.addAttribute("myPlaylist", playlist);
        return "playlist";
    }
}
