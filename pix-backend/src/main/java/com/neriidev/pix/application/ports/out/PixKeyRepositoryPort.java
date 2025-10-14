package com.neriidev.pix.application.ports.out;



import com.neriidev.pix.domain.model.PixKey;

import java.util.Optional;

public interface PixKeyRepositoryPort {
    PixKey save(PixKey pixKey);
    Optional<PixKey> findByKey(String key);
}
