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

    println("---------------------")
    var instance3 = Person("test")
    println("---------------------")

    println("---------------------")
    var dog = Dog("볼트", 3)
    var cat = Cat("별이", 2)
    dog.bark()
    cat.meow()
    dog.eat()
    cat.eat()
    println("---------------------")

    println("---------------------")
    var tree = Tree()
    tree.germination()
    tree.photosynthesis()
    println("---------------------")

    println("---------------------")
    var horse = Horse()
    horse.run()
    horse.eat()
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

class Person(val name: String, val age: Int) {
    /**
     * init : 생성자를 통해 인스턴스가 만들어질 때 호출되는 함수
     */
    init {
        println("${name}은 ${age}살입니다.")
    }
    /**
     * 보조 생성자
     * 반드시 기본 생성자를 통해 속성을 초기화 해줘야함
     */
    constructor(name: String): this(name, 90) {
        println("보조 생성자가 호출되었습니다.")
    }
}

/**
 * 코틀린은 상속 금지가 기본값(open 상태가 아님)
 */
open class Animal(var name: String, var age: Int, var type: String) {
    fun introduce() {
        println("저는 ${type} ${name}이고, ${age}살 입니다.")
    }
    /**
     * 오버라이딩 가능
     */
    open fun eat() {
        println("음식을 먹습니다")
    }
}

/**
 * 서브 클래스는 수퍼 클래스에 존재하는 속성과 같은 이름의 속성을 가질 수 없음
 * 서브 클래스가 생성될 때는 반드시 수퍼클래스의 생성자까지 호출해야함
 */
class Dog(name: String, age: Int): Animal(name, age, "개") {
    fun bark() {
        println("멍멍")
    }

    override fun eat() {
        println("사료를 먹습니다")
    }
}
class Cat(name: String, age: Int): Animal(name, age, "고양이") {
    fun meow() {
        println("야옹")
    }
}

/**
 * 추상클래스
 */
abstract class Plant {
    abstract fun germination()
    fun photosynthesis() {
        println("광합성을 합니다")
    }
}
class Tree: Plant() {
    override fun germination() {
        println("나무가 발아합니다.")
    }
}

/**
 * 인터페이스
 * 속성, 추상함수, 일반함수
 * 생성자 없음
 * 구현부가 있는 함수 -> open 함수
 * 구현부가 없는 함수 -> abstract 함수
 */
interface Runner {
    fun run()
}
interface Eater {
    fun eat() {
        println("음식을 먹습니다")
    }
}
class Horse : Runner, Eater {
    override fun run() {
        println("다그닥 다그닥")
    }

    override fun eat() {
        println("당근을 먹어요")
    }
}