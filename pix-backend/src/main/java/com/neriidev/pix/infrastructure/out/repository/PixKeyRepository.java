package com.neriidev.pix.infrastructure.out.repository;

import com.neriidev.pix.domain.model.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PixKeyRepository extends JpaRepository<PixKey, Long> {
    Optional<PixKey> findByKey(String key);
}
