package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface VenueService {
    List<Venue> findAll();
    Page<Venue> findAll(Pageable pageable);
    Venue findById(Long id);
    Venue save(Venue venue);
    void delete(Long id);
}
