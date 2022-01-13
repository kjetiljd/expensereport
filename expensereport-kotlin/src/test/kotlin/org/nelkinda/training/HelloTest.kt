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

    @Test @Ignore("WIP")
    fun `first line in report has correct format`() {
        val noExpenses = emptyList<Expense>()

        val actual = tapSystemOut {
            ExpenseReport().printReport(noExpenses)
        }

        // assert first line in report is correct format

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
