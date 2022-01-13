package org.nelkinda.training

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import org.approvaltests.Approvals
import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertTrue

class HelloTest {

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

        val actualWithoutDynamicHeader = reportWithoutHeading(actual)
        Approvals.verify(actualWithoutDynamicHeader)
    }

    @Test
    fun `golden master for all known expense types`() {
        val noExpenses = listOf(
            expense(ExpenseType.DINNER, 800),
            expense(ExpenseType.BREAKFAST, 350),
            expense(ExpenseType.CAR_RENTAL, 1200)
        )

        val actual = tapSystemOut {
            ExpenseReport().printReport(noExpenses)
        }
        Approvals.verify(reportWithoutHeading(actual))
    }

    private fun expense(type: ExpenseType, amount: Int): Expense {
        val dinner = Expense()
        dinner.type = type
        dinner.amount = amount
        return dinner
    }

    private fun reportWithoutHeading(actual: String) = actual.lines().drop(1).joinToString("\n")
}
