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

    println("---------------------")
    b(::a)
    println("---------------------")

    println("---------------------")
    var c: (String) -> Unit = { str -> println("$str 람다 함수") }
    var lambdaC = { str: String -> println("$str 람다 함수") }
    b(c)
    println("---------------------")

    println("---------------------")
    var bookA = Book("코틀린", 10000).apply {
        name = "[초특가]" + name
        discount()
    }
    println("상품명 : ${bookA.name}, 가격 : ${bookA.price}")
    println("---------------------")

    println("---------------------")
    bookA.run {
        println("상품명 : ${name}, 가격 : ${price}")
    }
    println("---------------------")

    println("---------------------")
    println(Counter.count)
    Counter.countUp()
    Counter.countUp()
    println(Counter.count)
    Counter.clear()
    println(Counter.count)
    println("---------------------")

    println("---------------------")
    var jjajang = FoodPoll("짜장")
    var jjampong = FoodPoll("짬뽕")

    jjajang.vote()
    jjajang.vote()

    jjampong.vote()
    jjampong.vote()
    jjampong.vote()

    println(jjajang.count)
    println(jjampong.count)
    println(FoodPoll.total)
    println("---------------------")

    println("---------------------")
    EventPrinter().start()
    println()
    println("---------------------")

    println("---------------------")
    var drink = Drink()
    drink.drink()

    var cola: Drink = Cola()
    cola.drink()

    if (cola is Cola) {
        cola.washDishes()
    }
    var coco = cola as Cola
    coco.washDishes()
    cola.washDishes()
    println("---------------------")

    println("---------------------")
    UsingGeneric(A()).doShouting()
    UsingGeneric(B()).doShouting()
    UsingGeneric(C()).doShouting()

    doShouting(B())
    println("---------------------")

    println("---------------------")
    var nullableA: String? = null
    println(nullableA?.uppercase())
    println(nullableA?:"null replace".uppercase())
//    println(nullableA!!.uppercase())
    nullableA?.run {
        println(uppercase())
    }
    println("---------------------")

    println("---------------------")
    var prodA = Product("콜라", 1000)
    var prodB = Product("콜라", 1000)
    var prodC = prodA
    var prodD = Product("사이다", 1000)

    println(prodA == prodB)
    println(prodA === prodB)

    println(prodA == prodC)
    println(prodA === prodC)

    println(prodA == prodD)
    println(prodA === prodD)
    println("---------------------")

    println("---------------------")
    deliveryItem("짬뽕")
    deliveryItem("짜장면", 3)
    deliveryItem("탕수육", 2, "학교")
    deliveryItem("선물", destination = "친구집")
    println("---------------------")

    println("---------------------")
    println(6 multiply 4)
    println(6.multiply(4))
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

/**
 * 패키지 스코프
 * public(기본값)  어떤 패키지에서도 접근 가능
 * internal      같은 모듈 내에서만 접근 가능
 * private       같은 파일 내에서만 접근 가능
 * protected 사용 안함
 *
 *
 * 클래스 스코프
 * public(기본값)  클래스 외부에서 늘 접근 가능
 * private       클래스 내부에서만 접근 가능
 * protected     클래스 자신과 상속받은 클래스에서 접근 가능
 * internal 사용 안함
 */

/**
 * 고차 함수
 * 함수를 클래스에서 만들어낸 인스턴스처럼 취급하는 방법
 * 함수를 파라미터로 넘겨줄 수도 있고 결과값으로 반환받을 수도 있는 방법
 */
fun a (str: String) {
    println("$str 함수 a")
}
fun b (function: (String) -> Unit) {
    function("b가 호출한")
}

/**
 * 스코프 함수
 * 클래스의 인스턴스를 스코프 함수에 전달하면 인스턴스의 속성이나 함수를 좀 더 깔끔하게 불러 쓸 수 있음
 * apply : 인스턴스를 생성한 후 변수에 담기 전에 초기화 과정을 수행할 때 많이 쓰임. main 함수와 별도의 스코프에서 인스턴스의 변수와 함수를 조작함.
 * run : run 스코프 안에서 참조 연산자를 사용하지 않아도 됨. 마지막 구문의 결과값을 반환함. 인스턴스가 만들어진 후에 인스턴스의 함수나 속성을 스코프 내에서 사용해야할 때.
 * with : run과 동일하나 인스턴스를 참조 연산자 대신 파라미터로 받음.
 * also : 처리가 끝나면 인스턴스를 반환, it 사용가능
 * let : 처리가 끝나면 마지막 구문의 결과값을 반환. it 사용 가능
 */
class Book(var name: String, var price: Int) {
    fun discount() {
        price -= 2000
    }
}

/**
 * Object
 * 싱글턴 패턴을 언어 차원에서 지원
 */
object Counter {
    var count = 0

    fun countUp() {
        count++
    }

    fun clear() {
        count = 0
    }
}

/**
 * companion object
 */
class FoodPoll (val name: String) {
    companion object {
        var total = 0
    }

    var count = 0

    fun vote() {
        total++
        count++
    }
}

/**
 * observer 패턴
 */
class EventPrinter: EventListener {
    override fun onEvent(count: Int) {
        print("${count}-")
    }

    fun start() {
        val counter = EventCounter(this)
        counter.count()
    }

    /**
     * EventPrinter 가 EventListener 를 구현하지 않을 경우
     */
//    fun start() {
//        var counter = EventCounter(object: EventListener {
//            override fun onEvent(count: Int) {
//                print("${count}-")
//            }
//        })
//        counter.count()
//    }
}
class EventCounter(var eventListener: EventListener) {
    fun count() {
        for (i in 1..100) {
            if (i % 5 == 0) {
                eventListener.onEvent(i)
            }
        }
    }
}
interface EventListener {
    fun onEvent(count: Int)
}

/**
 * down-casting
 * as : 변수를 호환되는 자료형으로 변환해주는 캐스팅 연산자. 반환값 뿐만 아니라 변수까지 캐스팅됨
 * is : 변수가 자료형에 호환되는지를 체크한 후 변환해주는 캐스팅 연산자 (조건문 내에서만 잠시)
 */
open class Drink {
    var name = "음료"

    open fun drink() {
        println("${name}를 마십니다")
    }
}
class Cola : Drink() {
    var type = "콜라"

    override fun drink() {
        println("${name} 중 ${type}를 마십니다")
    }

    fun washDishes() {
        println("${type}으로 설거지를 합니다")
    }
}

/**
 * generic
 */
open class A {
    open fun shout() {
        println("A 가 소리칩니다")
    }
}
class B: A() {
    override fun shout() {
        println("B 가 소리칩니다")
    }
}
class C: A() {
    override fun shout() {
        println("C 가 소리칩니다")
    }
}
class UsingGeneric<T: A> (val t: T) {
    fun doShouting() {
        t.shout()
    }
}
fun <T: A> doShouting(t: T) {
    t.shout()
}

/**
 * List<out T> : 생성시에 넣은 객체를 대체, 추가, 삭제할 수 없음
 * MutableList<T> : 생성시에 넣은 객체를 대체, 추가, 삭제할 수 있음
 */
var list = listOf("사과", "딸기", "배")
var mutableList = mutableListOf(6, 3, 1)

/**
 * null 연산자
 * null safe operator(?.) : 참조연산자를 실행하기 전에 객체 null 체크 후 null 일 경우 이후 구문을 실행하지 않음
 * elvis operator(?:) : 객체가 null 이라면 연산자 우측 값으로 대체
 * non-null assertion operator(!!.) : 참조연산자를 사용할 때 컴파일 시 null 체크를 하지 않고 런타임에서 NPE 를 유발하도록 방치
 */

/**
 * 객체의 동일성 : ===
 */
class Product(val name: String, val price: Int) {
    override fun equals(other: Any?): Boolean {
        if (other is Product) {
            return other.name == name && other.price == price
        }
        return false
    }
}

/**
 * default args
 */
fun deliveryItem(name: String, count: Int = 1, destination: String = "집") {
    println("${name}, ${count} 개를 ${destination} 에 배달하였습니다.")
}

/**
 * varargs
 */
fun sum(vararg number: Int):Int {
    var sum = 0;
    for (n in number) {
        sum += n
    }
    return sum
}

/**
 * infix function
 */
infix fun Int.multiply(x: Int): Int = this * x