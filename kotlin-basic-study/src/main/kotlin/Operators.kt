fun main() {
    checkTypeInt(123)
    checkTypeInt(456L)

    println("---------------------")
    doWhen(3L)
    println("---------------------")

    println("---------------------")
    var result: Int? = null
    result = returnWhen(3)
    println(result)
    println("---------------------")
}

/**
 * is 연산자
 * 자료형 체크
 */
fun checkTypeInt(a: Any) {
    if (a is Int) {
        println(a.toString() + "는 Int 자료형입니다")
    } else {
        println(a.toString() + "는 Int 자료형이 아닙니다")
    }
}

/**
 * when
 * switch-case 와 유사
 * 등호 또는 부등호는 사용 불가능
 * 다중 조건 부합 시 먼저 부합한 조건이 실행됨
 */
fun doWhen(a: Any) {
    when(a) {
        1 -> println("정수 1입니다")
        "test" -> println("문자열 test 입니다")
        is Long -> println("Long 타입입니다")
        !is String -> println("문자열 타입이 아닙니다")
        else -> println("어떤 조건도 만족하지 않았네요")
    }
}

fun returnWhen(a: Any): Int {
    return when(a) {
        1 -> 1
        "test" -> 2
        is Long -> 3
        !is String -> 4
        else -> 5
    }
}