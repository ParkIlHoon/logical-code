fun main() {
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