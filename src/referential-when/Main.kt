fun main() {
    val someObject = object { }
    val anotherObject = object { }
    val yetAnotherObject = object { }

    listOf(someObject, anotherObject, yetAnotherObject).forEach { target ->
        whenRef(target) {
            someObject then2 {
                println("Unit: some")
            }
            anotherObject then2 {
                println("Unit: another")
            }
            elseRef then2 {
                println("Unit: else")
            }
        }

        val numLambda = whenRef(target) {
            someObject then2 { 0 }
            anotherObject then2 { 1 }
            elseRef then2 { 2 }
        }

        val numDirect = whenRef(target) {
            someObject then 0
            anotherObject then 1
            elseRef then 2
        }

        println("numLambda: $numLambda")
        println("numDirect: $numDirect")
        println()
    }
}

class ReferentialWhenScope<R>(private val target: Any) {
    private val conditions = mutableMapOf<Any, ThenValue<R>>()

    val elseRef = object { }

    // TODO: merge this function to "then" without ambiguity
    infix fun Any.then2(block: () -> R) {
        conditions[this] = ThenValue.OfBlock(block)
    }

    infix fun Any.then(result: R) {
        conditions[this] = ThenValue.OfResult(result)
    }

    fun judgeConditions(): R = conditions
        .asIterable()
        .find { it.key === target }?.value?.get()
        ?: conditions[elseRef]?.get()
        ?: throw CorrespondingConditionNotFoundWithoutElseBlockException()

    private sealed interface ThenValue<T> {
        fun get(): T

        class OfBlock<T>(val block: () -> T) : ThenValue<T> {
            override fun get() = block()
        }
        class OfResult<T>(val result: T) : ThenValue<T> {
            override fun get() = result
        }
    }
}

fun <R> whenRef(target: Any, block: ReferentialWhenScope<R>.() -> Unit): R =
    ReferentialWhenScope<R>(target)
        .also(block)
        .judgeConditions()

class CorrespondingConditionNotFoundWithoutElseBlockException : Exception()
