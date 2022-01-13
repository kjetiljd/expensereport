package org.nelkinda.training

import java.util.*

sealed class ExpenseType(val text: String, private val limit: Int? = null, val isMealType: Boolean = false) {

    fun isOverLimit(amount: Int) = if (limit == null) false else amount > limit

    object DINNER : ExpenseType("Dinner", 5000, true)
    object BREAKFAST : ExpenseType("Breakfast", 1000, true)
    object CAR_RENTAL : ExpenseType("Car Rental")
}

data class Expense(
    val type: ExpenseType? = null,
    val amount: Int? = null) {

    fun name() = type?.text ?: ""

    fun isOverLimit() = type?.isOverLimit(amount!!) ?: false
    fun isMeal() = type?.isMealType ?: false
}

class ExpenseReport {
    fun printReport(expenses: List<Expense>) {
        var result = ""
        var total = 0

        result += "Expenses " + Date() + "\n"

        val mealExpenses =
            expenses.filter { expense ->
                expense.isMeal() }
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
