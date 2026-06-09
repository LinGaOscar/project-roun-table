package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.Venue;
import com.javaclass.roundtable.repository.VenueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Page<Venue> findAll(Pageable pageable) {
        return venueRepository.findAll(pageable);
    }

    @Override
    public Venue findById(Long id) {
        return venueRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Venue save(Venue venue) {
        return venueRepository.save(venue);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        venueRepository.deleteById(id);
    }
}
