package com.BandManagement.persistence.repositories;

import com.BandManagement.persistence.model.Member;
import com.BandManagement.persistence.model.Track;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Component
@Repository
public interface TrackRepository extends JpaRepository<Track, UUID> {

    List<Track> findAll();

    public List<Track> getTrackByAlbumId(UUID album_id);
    List<Track> findAllByAlbumIdOrderByTrackNameAsc(UUID album_id);
}
