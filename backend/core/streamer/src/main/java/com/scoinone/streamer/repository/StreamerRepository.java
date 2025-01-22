package com.scoinone.streamer.repository;

import com.scoinone.streamer.entity.StreamerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamerRepository extends JpaRepository<StreamerEntity, String> {
}
