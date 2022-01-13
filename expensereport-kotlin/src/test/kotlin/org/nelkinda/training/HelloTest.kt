package org.nelkinda.training

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import org.approvaltests.Approvals
import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HelloTest {

    @Test
    fun dummy() {

    }

    @Test
    fun `capture print output and make sure it is not empty`() {
        val noExpenses = emptyList<Expense>()

        val actual = tapSystemOut {
            ExpenseReport().printReport(noExpenses)
        }

        assertTrue(actual.isNotEmpty())
    }

    @Test
    fun `first line in report has correct format`() {
        val noExpenses = emptyList<Expense>()

        val actual = tapSystemOut {
            ExpenseReport().printReport(noExpenses)
        }

        val expected = """Expenses \w{3} \w{3} \d{1,2} \d\d:\d\d:\d\d [A-Z]{3} 20\d\d""".toRegex()
        assertTrue(actual.lines().first().matches(expected))

    }

    @Test
    @Ignore("Depends on current time")
    fun `golden master test of program output`() {
        val noExpenses = emptyList<Expense>()

        val actual = tapSystemOut {
            ExpenseReport().printReport(noExpenses)
        }
        Approvals.verify(actual)
    }

}
