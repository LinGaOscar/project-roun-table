package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.Venue;
import java.util.List;

public interface VenueService {
    List<Venue> findAll();
    Venue findById(Long id);
    Venue save(Venue venue);
    void delete(Long id);
}
