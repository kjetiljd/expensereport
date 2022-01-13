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

        result += "Expenses " + Date() + "\n"

        val total = total(expenses)

        expenses.forEach { expense ->
            result += expenseLine(expense)
        }

        result += "Meal expenses: ${mealTotal(expenses)}\n"
        result += "Total expenses: $total"
        println(result)
    }

    private fun mealTotal(expenses: List<Expense>) = expenses.filter(Expense::isMeal).sumOf { it.amount!! }

    private fun total(expenses: List<Expense>) = expenses.sumOf { it.amount!! }

    private fun expenseLine(expense: Expense): String {
        val mealOverExpensesMarker = if (expense.isOverLimit()) "X" else " "
        return expense.name() + "\t" + expense.amount + "\t" + mealOverExpensesMarker + "\n"
    }


}
