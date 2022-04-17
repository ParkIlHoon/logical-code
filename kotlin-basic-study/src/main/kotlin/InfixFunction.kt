fun main() {
    println(6 multiply 4)
    println(6.multiply(4))
}

/**
 * infix function
 */
infix fun Int.multiply(x: Int): Int = this * x