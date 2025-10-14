
package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.out.PixKeyRepositoryPort;
import com.neriidev.pix.application.ports.out.WalletRepositoryPort;
import com.neriidev.pix.domain.model.PixKey;
import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;
import com.neriidev.pix.infrastructure.out.persistence.enums.PixKeyType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PixKeyServiceImplTest {

    @Mock
    private PixKeyRepositoryPort pixKeyRepository;

    @Mock
    private WalletRepositoryPort walletRepository;

    @InjectMocks
    private PixKeyServiceImpl pixKeyService;

    @Test
    public void testRegisterKey() {
        Wallet wallet = new Wallet(1L, new BigDecimal("100.00"));
        PixKeyRequest pixKeyRequest = new PixKeyRequest("test@test.com", PixKeyType.EMAIL);

        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        when(pixKeyRepository.save(any(PixKey.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PixKey pixKey = pixKeyService.registerKey(1L, pixKeyRequest);

        assertNotNull(pixKey);
        verify(walletRepository, times(1)).findById(1L);
        verify(pixKeyRepository, times(1)).save(any(PixKey.class));
    }

    @Test
    public void testRegisterKeyWalletNotFound() {
        PixKeyRequest pixKeyRequest = new PixKeyRequest("test@test.com", PixKeyType.EMAIL);

        when(walletRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            pixKeyService.registerKey(1L, pixKeyRequest);
        });

        verify(walletRepository, times(1)).findById(1L);
        verify(pixKeyRepository, never()).save(any());
    }
}
