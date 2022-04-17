fun main() {
    var dog = Dog("볼트", 3)
    var cat = Cat("별이", 2)
    dog.bark()
    cat.meow()
    dog.eat()
    cat.eat()
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