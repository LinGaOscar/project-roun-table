package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.Venue;
import com.javaclass.roundtable.service.VenueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/venueTable")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping()
    public String venueListPage(Model model, HttpSession httpSession) {
        if (Objects.isNull(httpSession.getAttribute("loginCheck"))) {
            return "/login";
        }
        model.addAttribute("venueList", venueService.findAll());
        return "venue_table";
    }

    @GetMapping("/add")
    public String addVenuePage(Model model) {
        model.addAttribute("venue", new Venue());
        return "venue_edit";
    }

    @GetMapping("/edit/{id}")
    public String editVenuePage(@PathVariable Long id, Model model) {
        model.addAttribute("venue", venueService.findById(id));
        return "venue_edit";
    }

    @PostMapping("/save")
    public String saveVenue(@ModelAttribute Venue venue) {
        venueService.save(venue);
        return "redirect:/venueTable";
    }

    @GetMapping("/delete/{id}")
    public String deleteVenue(@PathVariable Long id) {
        venueService.delete(id);
        return "redirect:/venueTable";
    }
}
