package ua.kpi.comsys.dop;

import java.math.BigDecimal;

public record Deposit(String accountId, BigDecimal amount) implements Transaction {
    public Deposit{
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();
        }
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException();
        }
    }
}

