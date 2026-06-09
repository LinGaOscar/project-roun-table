package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.Venue;
import com.javaclass.roundtable.service.VenueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/admin/venue")
public class AdminVenueController {

    private final VenueService venueService;

    public AdminVenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public String venueListPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Venue> venuePage = venueService.findAll(PageRequest.of(page, 10));
        model.addAttribute("venueList", venuePage.getContent());
        model.addAttribute("page", venuePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", venuePage.getTotalPages());
        return "admin/venue_table";
    }

    @GetMapping("/add")
    public String addVenuePage(Model model) {
        model.addAttribute("venue", new Venue());
        return "admin/venue_edit";
    }

    @GetMapping("/edit/{id}")
    public String editVenuePage(@PathVariable Long id, Model model) {
        model.addAttribute("venue", venueService.findById(id));
        return "admin/venue_edit";
    }

    @PostMapping("/save")
    public String saveVenue(@Valid @ModelAttribute Venue venue, BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/venue_edit";
        }
        try {
            venueService.save(venue);
            redirectAttributes.addFlashAttribute("successMessage", "Venue saved successfully!");
        } catch (Exception e) {
            log.error("Error saving venue", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving venue: " + e.getMessage());
        }
        return "redirect:/admin/venue";
    }

    @GetMapping("/delete/{id}")
    public String deleteVenue(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            venueService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Venue deleted successfully!");
        } catch (Exception e) {
            log.error("Error deleting venue", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting venue: " + e.getMessage());
        }
        return "redirect:/admin/venue";
    }
}
