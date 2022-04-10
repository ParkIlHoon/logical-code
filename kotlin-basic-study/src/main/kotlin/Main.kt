fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    println("---------------------")
    variable()
    println("---------------------")

    println("---------------------")
    println(arr.asList().toString())
    println(nullArr.asList().toString())
    nullArr[3] = 456
    println(nullArr.asList().toString())
    println("---------------------")

    println("---------------------")
    println(add(1, 2, 3))
    println("---------------------")

    println("---------------------")
    checkTypeInt(123)
    checkTypeInt(456L)
    println("---------------------")

    println("---------------------")
    doWhen(3L)
    println("---------------------")

    println("---------------------")
    var result: Int? = null
    result = returnWhen(3)
    println(result)
    println("---------------------")

    println("---------------------")
    repeat()
    println()
    repeatStep()
    println()
    repeatDownTo()
    println()
    repeatChar()
    println()
    repeatVarRef()
    println()
    println("---------------------")

    println("---------------------")
    labelLoop()
    println("---------------------")

    println("---------------------")
    var instance1 = OnlyPropClass("홍길동", 12)
    println("이름 : ${instance1.name}")
    println("나이 : ${instance1.age}")
    println("---------------------")

    println("---------------------")
    var instance2 = WithFunClass("하하하", 34)
    instance2.introduce()
    println("---------------------")
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

val arr = arrayOf(1, 2, 3)
val nullArr = arrayOfNulls<Int>(5)


fun add(a: Int, b: Int, c: Int): Int {
    return a + b + c
}

/**
 * 단일 표현식 함수
 * 반환형 생략 가능
 */
fun add2(a: Int, b: Int, c: Int) = a + b + c

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

fun repeat() {
    for (idx in 0..9) {
        print(idx)
    }
}

fun repeatStep() {
    for (idx in 0..9 step 2) {
        print(idx)
    }
}

fun repeatDownTo() {
    for (idx in 10 downTo 0) {
        print(idx)
    }
}

fun repeatChar() {
    for (c in 'a'..'e') {
        print(c)
    }
}

fun repeatVarRef() {
    for (e in arr) {
        print(e.toString())
    }
}

fun labelLoop() {
    label1@
    for (i in 1..10) {
        label2@
        for (j in 1..3) {
            if (i == 2 && j == 3) {
                break@label1
            }
            println("${i} , ${j}")
        }
    }
}

class OnlyPropClass(val name: String, val age: Int)

class WithFunClass(val name: String, val age: Int) {
    fun introduce() {
        println("이름 : ${name}")
        println("나이 : ${age}")
    }
}

