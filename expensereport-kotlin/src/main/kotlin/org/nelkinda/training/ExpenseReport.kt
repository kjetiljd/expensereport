package org.nelkinda.training

import java.util.*

enum class ExpenseType(val text: String, val limit: Int? = null) {
    DINNER("Dinner", 5000),
    BREAKFAST("Breakfast", 1000),
    CAR_RENTAL("Car Rental");

    fun isOverLimit(amount: Int) = if (limit == null) false else amount > limit
}

data class Expense(
    val type: ExpenseType? = null,
    val amount: Int? = null) {

    fun name() = type?.text ?: ""

    fun isOverLimit() = type?.isOverLimit(amount!!) ?: false
}

class ExpenseReport {
    fun printReport(expenses: List<Expense>) {
        var result = ""
        var total = 0

        result += "Expenses " + Date() + "\n"

        val mealExpenses =
            expenses.filter { expense ->
                expense.type == ExpenseType.DINNER || expense.type == ExpenseType.BREAKFAST }
                .sumOf { expense -> expense.amount!! }

        expenses.forEach { expense ->
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
