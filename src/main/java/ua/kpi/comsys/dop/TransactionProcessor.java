package ua.kpi.comsys.dop;

public class TransactionProcessor {
    public String process(Transaction transaction){
        if (transaction == null){
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        return switch (transaction){
            case Deposit deposit -> """
                Transaction Type: DEPOSIT
                Account: %s
                Amount: USD %s
                """.formatted(deposit.accountId(), deposit.amount().toPlainString());
            case Withdrawal withdrawal -> """
                Transaction Type: WITHDRAWAL
                Account: %s
                Category: %s
                Amount: USD %s
                """.formatted(withdrawal.accountId(), withdrawal.category(), withdrawal.amount().toPlainString());
            case Transfer transfer -> """
                Transaction Type: TRANSFER
                From: %s
                To: %s
                Amount: USD %s
                """.formatted(transfer.fromAccount(), transfer.toAccount(), transfer.amount().toPlainString());
        };
    }
}
