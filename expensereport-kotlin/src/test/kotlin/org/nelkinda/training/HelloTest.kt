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
    fun `golden master test of main program output`() {
        val noExpenses = emptyList<Expense>()

        val actual = tapSystemOut {
            ExpenseReport().printReport(noExpenses)
        }

        val actualWithoutDynamicHeader = actual.lines()
            .filterIndexed { index, _ -> index != 0 }
            .joinToString("\n")
        Approvals.verify(actualWithoutDynamicHeader)
    }

}
