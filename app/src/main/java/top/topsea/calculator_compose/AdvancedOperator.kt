package top.topsea.calculator_compose

import net.objecthunter.exp4j.operator.Operator
import net.objecthunter.exp4j.function.Function
import kotlin.math.ln
import kotlin.math.log10

val factorial: Operator = object : Operator("!", 1, true, PRECEDENCE_POWER + 1) {
    override fun apply(vararg args: Double): Double {
        val arg = args[0].toInt()
        require(arg.toDouble() == args[0]) { "Operand for factorial has to be an integer" }
        require(arg >= 0) { "The operand of the factorial can not be less than zero" }
        var result = 1.0
        for (i in 1..arg) {
            result *= i.toDouble()
        }
        return result
    }
}
val ln: Function = object : Function("ln", 1) {
    override fun apply(vararg args: Double): Double {
        return ln(args[0])
    }
}
val lg: Function = object : Function("lg", 1) {
    override fun apply(vararg args: Double): Double {
        return log10(args[0])
    }
}