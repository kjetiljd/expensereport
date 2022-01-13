package org.nelkinda.training

import java.util.*

enum class ExpenseType(val text: String, private val limit: Int? = null, val isMealType: Boolean = false) {
    DINNER("Dinner", 5000, true),
    BREAKFAST("Breakfast", 1000, true),
    LUNCH("Lunch", 2000, true),
    CAR_RENTAL("Car Rental");

    fun isOverLimit(amount: Int) = if (limit == null) false else amount > limit
}


data class Expense(val type: ExpenseType? = null, val amount: Int? = null) {
    val name get() = type?.text ?: ""
    val isOverLimit get() = type?.isOverLimit(amount!!) ?: false
    val isMeal get() = type?.isMealType ?: false
}

private val List<Expense>.mealTotal get() = this.filter(Expense::isMeal).sumOf { it.amount!! }
private val List<Expense>.total get() = this.sumOf { it.amount!! }


class ExpenseReport {
    fun printReport(expenses: List<Expense>) {
        val result = "Expenses ${Date()}\n" +
                expenses.map { expenseLine(it) + "\n" }.joinToString("") +
                "Meal expenses: ${expenses.mealTotal}\n" +
                "Total expenses: ${expenses.total}"
        println(result)
    }

    private fun expenseLine(expense: Expense): String {
        val mealOverExpensesMarker = if (expense.isOverLimit) "X" else " "
        return expense.name + "\t" + expense.amount + "\t" + mealOverExpensesMarker
    }
}
