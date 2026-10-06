package ua.kpi.comsys.dop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionProcessorTest {

    private TransactionProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new TransactionProcessor();
    }

    @Test
    @DisplayName("Should format Deposit correctly")
    void testProcessDepositSuccess() {
        Deposit deposit = new Deposit("101", new BigDecimal("150.00"));
        String expected = """
            Transaction Type: DEPOSIT
            Account: 101
            Amount: USD 150.00
            """;

        assertEquals(expected, processor.process(deposit));
    }

    @Test
    @DisplayName("Should format Withdrawal correctly")
    void testProcessWithdrawalSuccess() {
        Withdrawal withdrawal = new Withdrawal("101", new BigDecimal("45.50"), "Category");
        String expected = """
            Transaction Type: WITHDRAWAL
            Account: 101
            Category: Category
            Amount: USD 45.50
            """;

        assertEquals(expected, processor.process(withdrawal));
    }

    @Test
    @DisplayName("Should format Transfer correctly")
    void testProcessTransferSuccess() {
        Transfer transfer = new Transfer("101", "102", new BigDecimal("300.00"));
        String expected = """
            Transaction Type: TRANSFER
            From: 101
            To: 102
            Amount: USD 300.00
            """;

        assertEquals(expected, processor.process(transfer));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when transaction is null")
    void testProcessNullThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> processor.process(null)
        );
        assertEquals("Transaction cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Deposit validation: invalid amount or accountId")
    void testDepositValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Deposit("101", null));
        assertThrows(IllegalArgumentException.class, () -> new Deposit("101", BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> new Deposit("101", new BigDecimal("-10.00")));
        assertThrows(IllegalArgumentException.class, () -> new Deposit(null, new BigDecimal("10.00")));
        assertThrows(IllegalArgumentException.class, () -> new Deposit("", new BigDecimal("10.00")));
        assertThrows(IllegalArgumentException.class, () -> new Deposit("   ", new BigDecimal("10.00")));
    }

    @Test
    @DisplayName("Withdrawal validation: invalid fields")
    void testWithdrawalValidation() {
        BigDecimal validAmount = new BigDecimal("20.00");

        assertThrows(IllegalArgumentException.class, () -> new Withdrawal("101", null, "Category"));
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal("101", BigDecimal.ZERO, "Category"));
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal("101", new BigDecimal("-5.00"), "Category"));

        assertThrows(IllegalArgumentException.class, () -> new Withdrawal(null, validAmount, "Category"));
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal("", validAmount, "Category"));
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal("   ", validAmount, "Category"));

        assertThrows(IllegalArgumentException.class, () -> new Withdrawal("101", validAmount, null));
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal("101", validAmount, ""));
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal("101", validAmount, "   "));
    }

    @Test
    @DisplayName("Transfer validation: invalid fields")
    void testTransferValidation() {
        BigDecimal validAmount = new BigDecimal("50.00");

        assertThrows(IllegalArgumentException.class, () -> new Transfer("101", "102", null));
        assertThrows(IllegalArgumentException.class, () -> new Transfer("101", "102", BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> new Transfer("101", "102", new BigDecimal("-1")));

        assertThrows(IllegalArgumentException.class, () -> new Transfer(null, "102", validAmount));
        assertThrows(IllegalArgumentException.class, () -> new Transfer("", "102", validAmount));
        assertThrows(IllegalArgumentException.class, () -> new Transfer("   ", "102", validAmount));

        assertThrows(IllegalArgumentException.class, () -> new Transfer("101", null, validAmount));
        assertThrows(IllegalArgumentException.class, () -> new Transfer("101", "", validAmount));
        assertThrows(IllegalArgumentException.class, () -> new Transfer("101", "   ", validAmount));
    }
}