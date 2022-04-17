fun main() {
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