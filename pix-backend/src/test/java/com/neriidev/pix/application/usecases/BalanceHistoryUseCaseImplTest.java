
package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.out.LedgerRepositoryPort;
import com.neriidev.pix.domain.enums.LedgerEntryType;
import com.neriidev.pix.domain.model.Ledger;
import com.neriidev.pix.domain.model.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BalanceHistoryUseCaseImplTest {

    @Mock
    private LedgerRepositoryPort ledgerRepository;

    @InjectMocks
    private BalanceHistoryUseCaseImpl balanceHistoryUseCase;

    @Test
    public void testGetBalanceAt() {
        Wallet wallet = new Wallet(1L, new BigDecimal("0"));
        LocalDateTime now = LocalDateTime.now();

        Ledger entry1 = new Ledger(1L, wallet, new BigDecimal("100.00"), LedgerEntryType.CREDIT, "e2e1", now.minusDays(2));
        Ledger entry2 = new Ledger(2L, wallet, new BigDecimal("50.00"), LedgerEntryType.DEBIT, "e2e2", now.minusDays(1));
        Ledger entry3 = new Ledger(3L, wallet, new BigDecimal("25.00"), LedgerEntryType.CREDIT, "e2e3", now);

        when(ledgerRepository.findByWalletId(1L)).thenReturn(Arrays.asList(entry1, entry2, entry3));

        BigDecimal balance = balanceHistoryUseCase.getBalanceAt(1L, now.minusDays(1));

        assertEquals(new BigDecimal("50.00"), balance);
        verify(ledgerRepository, times(1)).findByWalletId(1L);
    }

    @Test
    public void testGetBalanceAtNoEntries() {
        when(ledgerRepository.findByWalletId(1L)).thenReturn(Collections.emptyList());

        BigDecimal balance = balanceHistoryUseCase.getBalanceAt(1L, LocalDateTime.now());

        assertEquals(BigDecimal.ZERO, balance);
        verify(ledgerRepository, times(1)).findByWalletId(1L);
    }
}
