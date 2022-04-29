# 함수
## 코틀린 함수의 구조
```kotlin
import kotin.math.PI

fun circleArea(radius :Double) :Double {
    return PI * radius * radius
}

fun main() {
    print("Enter radius : ")
    val radius = readLine()!!.toDouble()
    println("Circle area is ${circleArea(radius)}")
}
```
* `fun` 키워드는 컴파일러에게 함수 정의가 뒤따라온다는 사실을 알려준다.
* 변수 이름과 마찬가지로 아무 식별자나 함수 이름으로 쓸 수 있다.
* 괄호로 둘러싸여 있는 콤마(`,`)로 분리한 파라미터 목록이 온다.
* 반환 타입(`:Double`)은 함수를 호출한 쪽에 돌려줄 반환값의 타입이다.
* 함수 본문은 `{}`로 감싼 블록이며, 함수의 구현을 기술한다.

자바 메서드 파라미터는 디폴트가 가변이므로 함수 내부에서 변경하지 못하게 하려면 `final`을 지정해 불변 값으로 바꿔야 하는데, **코틀린 함수 파라미터는 무조건 불변이다**.

코틀린은 값에 의한 호출(call-by-value) 의미론을 사용한다. 이 말은 파라미터 값에 호출하는 쪽의 인자를 복사한다는 뜻이다. 특히 호출 인자로 전달한 변수는 변경해도 호출된 함수 내분의 파라미터 값에는 영향이 없다는 뜻이다.

하지만 파라미터가 참조(예: 배열 타입)라면 호출한 쪽의 데이터는 그대로 남아있고 이 데이터에 대한 참조만 복사된다.

타입 지정을 생략해도 되는 변수와 달리 파라미터에는 항상 타입을 지정해야 한다. 컴파일러는 함수 정의에서 파라미터 타입을 추론하지 못한다.<br>
반면 반환 타입은 함수 파라미터에서 추론이 가능한데도 여전히 명시해야 한다. 그러나 아래의 경우에는 반환 타입을 생략할 수 있다.
* 유닛(Unit) 타입을 반환할 때
  * 유닛은 자바 `void`에 해당하는 코틀린 타입으로, 반환값을 돌려주지 않는다는 뜻이다.
  * `Unit`은 코틀린 내장 타입에 속하는 상수이므로, 아래 두 함수의 정의는 같다.
  ```kotlin
    fun test() { }
    fun test() :Unit { }
  ```
* 식이 본문인 함수일 때
  * 어떤 함수가 단일 식으로만 구현이 될 수 있다면 `return` 키워드와 중괄호를 생략하고 아래와 같은 형태로 함수를 작성해도 된다.
  ```kotlin
    fun circleArea(radius :Double) :Double = PI * radius * radius
  ```
  
## 위치 기반 인자와 이름 붙은 인자
### 위치 기반 인자
함수 호출의 인자를 순서대로 파라미터에 전달하는 방식으로 첫 번째 인자는 첫 번째 파라미터, 두 번째 인자는 두 번째 파라미터라는 식이다.

### 이름 붙은 인자
위치가 아니라 파라미터의 이름을 명시함으로써 인자를 전달하는 방식이다.
```kotlin
fun rectangleArea(width :Int, height :Int) {
    return width * height
}

fun main() {
    rectangleArea(width = 12, height = 10)
    rectangleArea(height = 9, width = 1)
}
```

## 오버로딩과 디폴트 값
### 오버로딩
자바 메서드와 마찬가지로 코틀린 함수도 오버로딩이 가능하다.
다만 컴파일러가 어떤 함수를 호출해야할지 구분할 수 있도록 오버로딩한 함수의 파라미터 타입이 모두 달라야한다.

### 디폴트 값
코틀린에서는 디폴트 파라미터를 제공해 파라미터 뒤에 변수 초기화 식을 추가하면 원하는 파라미터에 디폴트 값을 제공할 수 있다.
```kotlin
fun readInt(radix :Int = 10) = readLine()!!.toInt(radix)

fun main() {
    val decimalInt = readInt()
    val decimalInt2 = readInt(10)
    val hexInt = readInt(16)
}
```

## vararg
인자의 개수가 정해지지 않은 함수는 파라미터 정의 앞에 `vararg` 변경자를 붙이면 된다.
```kotlin
fun printSorted(vararg items :Int) {
    item.sort()
    println(items.contentToString())
}

fun main() {
    printSorted(1, 88, 3, 55, 2)
}
```

또한 스프레드 연산자인 `*`를 사용하면 배열을 가변 인자 대신 넘길 수 있다.
```kotlin
val numbers = intArrayOf(9, 3, 44, 2)
printSorted(*numbers)
printSorted(1, 2, *numbers, 9, 28)
```
이 때, 스프레드는 배열을 얕은 복사하며 참조는 참조가 복사된다.
```kotlin
fun main() {
    val a = intArrayOf(7, 4, 6, 2, 1)
    printSorted(*a) // [1, 2, 4, 6, 7]
    println(a.contentToString())    // [7, 4, 6, 2, 1]
}
```

## 함수의 영역과 가시성
코틀린 함수는 정의된 위치에 따라 세 가지로 구분할 수 있다.
* 파일에 직접 선언된 최상위 함수
* 어떤 타입 내부에 선언된 멤버 함수
* 다른 함수 안에 선언된 지역 함수

### 최상위 함수
* 디폴트로 최상위 함수는 공개(public) 함수다.
* 디폴트로 선언된 최상위 함수는 함수가 정의된 파일 내부뿐 아니라 프로젝트 어디에서나 쓰일 수 있다.
* `private`, `internal` 키워드(가시성 변경자)로 함수의 영역을 줄일 수 있다.
* 비공개(`private`)로 정의하면 함수가 정의된 파일 안에서만 해당 함수를 사용할 수 있다.
* `internal` 변경자를 적용하면 함수가 적용된 모듈 내부에서만 함수를 사용할 수 있다.
    * 코틀린에서의 모듈은 기본적으로 함께 컴파일되는 파일 전부를 뜻한다.

### 함수 안에 선언된 지역 함수
* 지역 변수와 같이 함수 내부에 지역 함수를 정의할 수 있으며, 이 함수의 영역은 함수를 감싸는 블록으로 한정된다.
    ```kotlin
    fun main() {
        fun readInt() = readLine()!!.toInt()
        println(readInt() + readInt())
    }
  
    fun readIntPair() = intArrayOf(readInt(), readInt())    // error: unresolved reference: readInt
    ```
* 지역 함수는 자신을 둘러싼 함수, 블록에 선언된 변수나 함수에 접근할 수 있다.
    ```kotlin
    fun main(args: Array<String>) {
        
        fun swap(i :Int, j :Int) :String {
            val chars = args[0].toCharArray()
            val tmp = chars[i]
            chars[i] = chars[j]
            chars[j] = tmp
            return chars.concatToString()
        }
  
        println(swap(0, args[0].lastIndex)) 
    }
    ```
  
---

# 패키지와 임포트
## 임포트 디렉티브 사용하기
서로 다른 패키지에 있는 일부 선언의 이름이 똑같은 경우 alias로 구분할 수 있다. 이 alias는 임포트 디렉티브가 있는 파일 전체의 영역에 유효하다.<br>
예를 들어 `app.util.foo` 와 `app.util.bar` 패키지에 `readInt()` 함수가 선언되어있는 경우 아래와 같이 사용할 수 있다.
```kotlin
import app.util.foo.readInt as fooReadInt
import app.util.bar.readInt as barReadInt

fun main() {
    val a = fooReadInt()
    val b = barReadInt()
}
```

---

# 조건문
## if 문
자바의 `if`과 달리 코틀린에서는 `if`를 식으로 사용할 수 있으며, 이 경우 양 가지(if, else)가 모두 있어야한다.<br>
코틀린에는 자바와 달리 3항 연산자가 없는데, `if`를 대신 사용할 수 있다.
```kotlin
fun max(a :Int, b :Int) = if (a > b) a else b
```

블록의 경우 블록 맨 끝에 있는 식이 블록 전체의 값이 된다.
```kotlin
fun main() {
    val s = readLine()!!
    val i = s.indexOf("/")
    
    // 10/3 같은 문자열을 /을 기준으로 10과 3으로 나누어서 나눗셈을 수행한다.
    val result = if (i >= 0) {
        val a = s.substring(0, i).toInt()
        val b = s.substring(i + 1).toInt()
        (a / b).toString()
    } else ""
    
    println(result)
}
```

## 범위, 진행, 연산
코틀린은 순서가 정해진 값 사이의 수열(interval)을 표현하는 몇가지 타입을 제공하는데 이를 범위(range)라고 한다.

### `..` 연산자
```kotlin
val chars = 'a'..'h'    // a 부터 h 까지의 모든 문자
val twoDigits = 10..99  // 10 부터 99 까지의 모든 수
val zero2One = 0.0..1.0 // 0 부터 1 까지의 모든 부동소수점 수
```

`in` 연산으로 어떤 값이 범위 안에 들어있는지 알 수 있다.
```kotlin
val num = readLine()!!.toInt()
println(num in 10..99)
```

### `until` 연산자
`..` 연산에 의해 만들어지는 범위는 닫혀 있다. 즉, 시작 값과 끝 값이 범위에 포함된다.
끝 값이 제외된 반만 닫힌 범위를 만드는 연산은 정수 타입에만 사용할 수 있고 끝 값보다 1 작은 값까지 들어있는 범위를 만들어낸다.
```kotlin
val twoDigits = 10 until 100    // 10..99 와 동일한 결과
```

끝 값이 시작 값보다 확실히 더 작으면 빈 범위가 된다.
```kotlin
println(5 in 5..5)  // true
println(5 in 5 until 5) // false
println(5 in 10..1) // false
```

### 진행
진행은 정해진 간격만큼 떨어져 있는 정수나 `Char` 값들로 이뤄진 시퀀스를 말한다.<br>
아래와 같이 `downTo` 연산자로 아래로 내려가는 진행을 만들 수 있다.
```kotlin
println(5 in 10 downTo 1)   // true
println(5 in 1 downTo 10)   // false: 빈 진행임
```
그리고 `step` 연산자로 진행의 간격을 지정할 수도 있으며, 위 두 연산자의 값은 양수여야 한다.
```kotlin
1..10 step 3    // 1, 4, 7, 10
15 downTo 9 step 2  // 15, 13, 11, 9
```

## `when` 문과 여럿 중에 하나 선택하기
```kotlin
fun hexDigit(n :Int) :Char {
    when {
        n in 0..9 -> return '0' + n
        n in 10..15 -> return 'A' + n - 10
        else -> return '?'
    }
}
```
* 기본적으로 `when`문은 `when` 키워드 다음에 블록이 온다.
* 블록 안에는 `조건 -> 문` 형태로 된 여러 개의 가지와 `else -> 문` 형태로 된 한 가지가 있을 수 있다.
* `when`문도 `if`처럼 식을 쓸 수 있고, 이 경우 `else` 가지를 반드시 포함시켜야 한다.
* 코틀린의 `when`에서는 임의의 조건을 검사할 수 있지만, 자바의 `switch`는 주어진 식의 여러 가지 값 중 하나만 선택할 수 있다.
* 자바의 `switch`는 폴스루(fall-through)를 지원하지만 코틀린의 `when`은 조건을 만족하는 가지만 실행하고 절대 폴스루하지 않는다.

---

# 루프
## `for`루프와 이터러블
```kotlin
fun main() {
    val a = IntArray(10) { it * it }
    val sum = 0
  
    for (x in a) {
        sum += x
    }
  
    println("Sum : $sum")
}
``` 
* 일반 변수와는 달리 루프 변수에는 `val`이나 `var`을 붙이지 않으며 루프 변수는 자동으로 불변 값이 된다.

## 루프 제어 흐름 변경하기: `break`와 `continue`
* `break` : 즉시 루프를 종료시키고, 실행 흐름이 루프 바로 다음 문으로 이동하게 만든다.
* `continue` : 현재 루프 이터레이션을 마치고 조건 검사로 바로 진행하게 만든다.

## 내포된 루프와 레이블
