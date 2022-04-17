fun main() {
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