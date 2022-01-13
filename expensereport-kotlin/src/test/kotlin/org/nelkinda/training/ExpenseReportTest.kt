package org.nelkinda.training

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import org.approvaltests.Approvals
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

class ExpenseReportTest {

    @Test
    fun `first line in report has correct format`() {
        val noExpenses = emptyList<Expense>()

        val actual = tapSystemOut {
            ExpenseReport().printReport(noExpenses)
        }

        val expected = """Expenses \w{3} \w{3} \d{1,2} \d\d:\d\d:\d\d [A-Z]{3,4} 20\d\d""".toRegex()
        assertTrue(actual.lines().first().matches(expected))

    }

    @Test
    fun `golden master with no expense of main program output`() {
        val noExpenses = emptyList<Expense>()

        val actual = tapSystemOut {
            ExpenseReport().printReport(noExpenses)
        }

        Approvals.verify(actual.withoutDynamicHeading())
    }

    @Test
    fun `golden master for all known expense types`() {
        val expenses = listOf(
            Expense(type = ExpenseType.DINNER, amount = 800),
            Expense(type = ExpenseType.BREAKFAST, amount = 350),
            Expense(type = ExpenseType.LUNCH, amount = 900),
            Expense(type = ExpenseType.CAR_RENTAL, amount = 1200)
        )

        val actual = tapSystemOut {
            ExpenseReport().printReport(expenses)
        }
        Approvals.verify(actual.withoutDynamicHeading())
    }

    @Test
    fun `golden master for expenses over limit`() {
        val expensesOverLimit = listOf(
            Expense(type = ExpenseType.DINNER, amount = 5001),
            Expense(type = ExpenseType.BREAKFAST, amount = 1001),
            Expense(type = ExpenseType.LUNCH, amount = 2001),
        )

        val actual = tapSystemOut {
            ExpenseReport().printReport(expensesOverLimit)
        }
        Approvals.verify(actual.withoutDynamicHeading())
    }

    @Test
    fun `null value in amount throws NPE`() {
        val nullAmountExpense = listOf(
            Expense(ExpenseType.DINNER, null),
        )

        try{
            ExpenseReport().printReport(nullAmountExpense)
            fail("Expected NullPointerException on null amount")
        } catch (e: NullPointerException) {
            // yay
        }
    }

    @Test
    fun `null value in type returns a report with no type on expense line`() {
        val nullTypeExpense = listOf(
            Expense(null, 666),
        )

        val actual = tapSystemOut {
            ExpenseReport().printReport(nullTypeExpense)
        }

        Approvals.verify(actual.withoutDynamicHeading())
    }

    @Test
    fun `isOverLimit for expense types without limit`() {
        val typesWithoutLimit = listOf(ExpenseType.CAR_RENTAL)

        typesWithoutLimit.forEach { expenseType ->
            assertFalse(Expense(type = expenseType, Integer.MAX_VALUE).isOverLimit())
        }
    }

    @Test
    fun `isOverLimit for expense types with limits`() {
        val typesWithLimit = listOf(ExpenseType.DINNER, ExpenseType.BREAKFAST, ExpenseType.LUNCH)

        typesWithLimit.forEach { expenseType ->
            val expenseClearlyOverLimit = Expense(type = expenseType, Integer.MAX_VALUE)
            val expenseClearlyUnderLimit = Expense(type = expenseType, 0)

            assertTrue(expenseClearlyOverLimit.isOverLimit())
            assertFalse(expenseClearlyUnderLimit.isOverLimit())
        }
    }
    
    @Test
    fun `isOverLimit on a null expense type`() {
        val expenseWithoutType = Expense(type = null, amount = Integer.MAX_VALUE)
        assertFalse(expenseWithoutType.isOverLimit())
    }

    private fun String.withoutDynamicHeading() = this.lines().drop(1).joinToString("\n")
}
