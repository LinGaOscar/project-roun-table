package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.Venue;
import com.javaclass.roundtable.repository.VenueRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;

    public VenueServiceImpl(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public List<Venue> findAll() {
        return venueRepository.findAll();
    }

    @Override
    public Venue findById(Long id) {
        return venueRepository.findById(id).orElse(null);
    }

    @Override
    public Venue save(Venue venue) {
        return venueRepository.save(venue);
    }

    @Override
    public void delete(Long id) {
        venueRepository.deleteById(id);
    }
}
