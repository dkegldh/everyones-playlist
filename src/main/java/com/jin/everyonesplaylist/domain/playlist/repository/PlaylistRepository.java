package com.jin.everyonesplaylist.domain.playlist.repository;

import com.jin.everyonesplaylist.domain.playlist.entity.Playlist;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, UUID> {

}
