fun main() {
    variable()
}

/**
 * var 변경될 수 있는 변수
 * val 값을 변경할 수 없는 변수 (final)
 */

fun variable() {
    var a: Int = 123
    println(a)

    var b: Int? = null
    println(b)
}