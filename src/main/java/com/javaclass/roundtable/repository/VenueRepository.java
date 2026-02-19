package com.javaclass.roundtable.repository;

import com.javaclass.roundtable.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
