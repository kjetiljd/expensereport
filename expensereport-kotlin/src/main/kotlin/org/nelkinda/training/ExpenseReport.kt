package org.nelkinda.training

import java.util.*

enum class ExpenseType {
    DINNER, BREAKFAST, CAR_RENTAL
}

data class Expense(
    val type: ExpenseType? = null,
    val amount: Int? = null
)

class ExpenseReport {
    fun printReport(expenses: List<Expense>) {
        var result = ""
        var total = 0
        var mealExpenses = 0

        result += "Expenses " + Date() + "\n"

        expenses.forEach { expense ->
            if (expense.type == ExpenseType.DINNER || expense.type == ExpenseType.BREAKFAST) {
                mealExpenses += expense.amount!!
            }

            val expenseName = expenseName(expense)

            val expenseOverLimitMarker = "X"
            val mealOverExpensesMarker = if (isOverLimit(expense)) expenseOverLimitMarker else " "

            result += expenseName + "\t" + expense.amount + "\t" + mealOverExpensesMarker + "\n"

            total += expense.amount!!
        }

        result += "Meal expenses: $mealExpenses\n"
        result += "Total expenses: $total"
        println(result)
    }

    private fun expenseName(expense: Expense): String {
        val expenseName =
            when (expense.type) {
                ExpenseType.DINNER -> "Dinner"
                ExpenseType.BREAKFAST -> "Breakfast"
                ExpenseType.CAR_RENTAL -> "Car Rental"
                null -> ""
            }
        return expenseName
    }

    internal fun isOverLimit(expense: Expense) =
        expense.type == ExpenseType.DINNER && expense.amount!! > 5000 || expense.type == ExpenseType.BREAKFAST && expense.amount!! > 1000
}
