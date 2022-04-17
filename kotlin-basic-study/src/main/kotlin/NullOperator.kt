fun main() {
    var nullableA: String? = null
    println(nullableA?.uppercase())
    println(nullableA ?: "null replace".uppercase())
//    println(nullableA!!.uppercase())
    nullableA?.run {
        println(uppercase())
    }
}

/**
 * null 연산자
 * null safe operator(?.) : 참조연산자를 실행하기 전에 객체 null 체크 후 null 일 경우 이후 구문을 실행하지 않음
 * elvis operator(?:) : 객체가 null 이라면 연산자 우측 값으로 대체
 * non-null assertion operator(!!.) : 참조연산자를 사용할 때 컴파일 시 null 체크를 하지 않고 런타임에서 NPE 를 유발하도록 방치
 */