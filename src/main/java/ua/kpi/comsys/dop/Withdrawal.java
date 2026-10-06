package ua.kpi.comsys.dop;

import java.math.BigDecimal;

public record Withdrawal(String accountId, BigDecimal amount, String category) implements Transaction {
    public Withdrawal{
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("Account ID must not be blank");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category must not be blank");
        }
    }
}
