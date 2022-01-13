package org.nelkinda.training

import java.util.*

enum class ExpenseType(val text: String) {
    DINNER("Dinner"),
    BREAKFAST("Breakfast"),
    CAR_RENTAL("Car Rental")
}

data class Expense(
    val type: ExpenseType? = null,
    val amount: Int? = null) {

    fun name() = type?.text ?: ""

    fun isOverLimit() =
        type == ExpenseType.DINNER && amount!! > 5000 || type == ExpenseType.BREAKFAST && amount!! > 1000
}

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

            val expenseOverLimitMarker = "X"
            val mealOverExpensesMarker = if (expense.isOverLimit()) expenseOverLimitMarker else " "

            result += expense.name() + "\t" + expense.amount + "\t" + mealOverExpensesMarker + "\n"

            total += expense.amount!!
        }

        result += "Meal expenses: $mealExpenses\n"
        result += "Total expenses: $total"
        println(result)
    }


}
