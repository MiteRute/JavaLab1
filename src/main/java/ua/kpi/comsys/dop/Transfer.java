package ua.kpi.comsys.dop;

import java.math.BigDecimal;

public record Transfer(String fromAccount, String toAccount, BigDecimal amount) implements Transaction {
    public Transfer {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (fromAccount == null || fromAccount.isBlank()) {
            throw new IllegalArgumentException("From account must not be blank");
        }
        if (toAccount == null || toAccount.isBlank()) {
            throw new IllegalArgumentException("To account must not be blank");
        }
    }
}

