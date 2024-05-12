fun main() {
    // You can actually use it as if it's a normal if-else statement
    altIf (true) altThen println("Writing Kotlin is full of fun!")
    
    for (i in 1..4) println(
        altIf (i == 1) {
            "$i is assumed as 1"
        } altElse altIf (i == 3) {
            "$i is assumed as 3"
        } altElse {
            "$i is assumed as something else, not as 1 nor 3"
        }
    )
}

fun altIf(condition: Boolean) = Thennable(condition)

fun <T> altIf(condition: Boolean, block: () -> T) =
    if (condition) ConditionResult.WasTrue(result = block())
    else ConditionResult.WasFalse()

class Thennable(val condition: Boolean) {
    infix fun <T> altThen(result: T) =
        if (condition) ConditionResult.WasTrue(result)
        else ConditionResult.WasFalse()
}

sealed interface ConditionResult<T> {    
    infix fun altElse(block: () -> T): T
    infix fun altElse(elseResult: T): T
    infix fun altElse(elseIf: ConditionResult<T>): ConditionResult<T>
    
    class WasTrue<T>(val result: T) : ConditionResult<T> {
        override infix fun altElse(block: () -> T) = result
        override infix fun altElse(elseResult: T) = result
        override infix fun altElse(
            elseIf: ConditionResult<T>) = ConditionResult.WasTrue(result)
    }
    
    class WasFalse<T> : ConditionResult<T> {
        override infix fun altElse(block: () -> T) = block()
        override infix fun altElse(elseResult: T) = elseResult
        override infix fun altElse(elseIf: ConditionResult<T>) = elseIf
    }
}
