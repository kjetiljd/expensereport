package org.nelkinda.training

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
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

}
