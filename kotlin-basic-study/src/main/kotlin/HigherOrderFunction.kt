fun main() {
    println("---------------------")
    b(::a)
    println("---------------------")

    println("---------------------")
    var c: (String) -> Unit = { str -> println("$str 람다 함수") }
    var lambdaC = { str: String -> println("$str 람다 함수") }
    b(c)
    println("---------------------")
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