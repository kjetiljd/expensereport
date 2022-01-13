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

        for (expense in expenses) {
            if (expense.type == ExpenseType.DINNER || expense.type == ExpenseType.BREAKFAST) {
                mealExpenses += expense.amount!!
            }

            var expenseName = ""
            when (expense.type) {
                ExpenseType.DINNER -> expenseName  = "Dinner"
                ExpenseType.BREAKFAST -> expenseName = "Breakfast"
                ExpenseType.CAR_RENTAL -> expenseName = "Car Rental"
            }

            val expenseOverLimitMarker = "X"
            val mealOverExpensesMarker = when {
                isOverLimit(expense) -> expenseOverLimitMarker
                else -> " "
            }

            result += expenseName + "\t" + expense.amount + "\t" + mealOverExpensesMarker + "\n"

            total += expense.amount!!
        }

        result += "Meal expenses: $mealExpenses\n"
        result += "Total expenses: $total"
        println(result)
    }

    private fun isOverLimit(expense: Expense) =
        expense.type == ExpenseType.DINNER && expense.amount!! > 5000 || expense.type == ExpenseType.BREAKFAST && expense.amount!! > 1000
}
