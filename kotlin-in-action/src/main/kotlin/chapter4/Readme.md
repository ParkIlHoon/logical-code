# 클래스 정의하기
기본적으로 클래스 선언은 참조 타입(referential type)을 정의한다. 코틀린 1.3부터는 인라인 클래스(inline class)라는 개념이 도입되어 참조 타입이 아닌 타입을 정의할 수 있다.

## 클래스 내부 구조
```kotlin
class Person {
    var firstName: String = ""
    var familyName: String = ""
    var age: Int = 0

    fun fullName() = "$firstName $familyName"
    
    fun showMe() {
        println("${fullName()} : $age")
    }
}
```
자바 필드와 달리, 코틀린에서는 클라이언트 코드를 바꾸지 않아도 원하는 대로 프로퍼티의 구현을 바꿀 수 있기 때문에 코틀린 프로퍼티는 캡슐화에 위배되지 않는다.
예를 들어 커스텀 게터나 세터를 추가해도 클라이언트 소스코드를 바꿀 필요가 없다.
즉, `firstName`이라는 참조는 프로퍼티가 구현되는 방법과 무관하게 항상 올바른 참조로 남을 수 있다.

기본적으로 코틀린 클래스는 공개(`public`) 가시성이다.

## 생성자
생성자는 클래스 인스턴스를 초기화해주고 인스턴스 생성 시 호출되는 특별한 함수다.
```kotlin
class Person(firstName :String, familyName :String) {
    val fullName = "$firstName $familyName"
}

fun main() {
    val person = Person("John", "Doe")
    println(person.fullName)
}
```
클래스 헤더의 파라미터 목록을 주생성자(primary constructor) 선언이라고 부른다. 
주생성자는 함수와 달리 본문이 하나가 아니다.

### 초기화 블록
초기화 블록이란 `init`이라는 키워드가 앞에 붙은 블록이다. 이 블록 안에서 클래스 초기화 시 필요한 간단하지 않은 초기화 로직을 수행할 수 있다.
```kotlin
class Person(firstName :String, familyName :String) {
    val fullName = "$firstName $familyName"

    init {
        println("Created new Person instance : $fullName")
    }
}
```
클래스 안에 `init` 블록이 여럿 들어갈 수 있다. 이런 경우 각 블록은 프로퍼티 초기화와 함께 순서대로 실행된다.
그리고 초기화 블록에는 `return`문이 들어가지 못한다.

### 멤버 프로퍼티 정의
주생성자 파라미터를 프로퍼티 초기화나 init 블록 밖에서 사용할 수는 없다.
예를 들어, 멤버 함수 내부에서는 `firstName`을 사용할 수 없다.
```kotlin
class Person(firstName :String, familyName :String) {
    val fullName = "$firstName $familyName"

    fun printFirstName() {
        println(firstName)  // Error: firstName is not available
    }
}
```
이에 대한 해법은 생성자 파라미터의 값을 저장할 멤버 프로퍼티를 정의하는 것이다.
```kotlin
class Person(val firstName :String, familyName :String) {
    val fullName = "$firstName $familyName"

    fun printFirstName() {
        println(firstName)  // Error: firstName is not available
    }
}

fun main() {
    val person = Person("John", "Doe")
    println(person.firstName)
}
```
생성자 파라미터 앞에 `val`이나 `var` 키워드를 덧붙이면, 자동으로 해당 생성자 파라미터로 초기화되는 (생성자 파라미터와 이름이 같은) 프로퍼티를 정의한다.<br>
이 때 파라미터 이름을 프로퍼티 초기화나 `init` 블록 안에서 참조하면 생성자 파라미터를 가리키고, 다른 위치에서 참조하면 프로퍼티를 가리키게 된다.

함수와 마찬가지로 디폴트 값과 `vararg`를 생성자 파라미터에 사용할 수 있다.
```kotlin
class Person(val firstName: String, val familyName: String = "") {
    fun fullName() = "$firstName $familyName"
}

class Room(vararg val persons :Person) {
    fun showNames() {
        for (person in persons) println(person.fullName())
    }
}

fun main() {
    val room = Room(Person("John"), Person("Jame", "Smith"))
    room.showNames()
}
```

### 부생성자
여러 생성자를 사용해 클래스 인스턴스를 서로 다른 방법으로 초기화하고 싶을 때도 있다. 이런 경우 코틀린에서는 부생성자(secondary constructor)를 사용해 해결할 수 있다.
```kotlin
class Person {
    val fistName :String
    val familyName :String
    
    constructor(firstName :String, familyName :String) {
        this.firstName = firstName
        this.familyName = familyName
    }
    
    constructor(fullName :String) {
        val names = fullName.split(" ")
        if (names.size != 2) {
            throw IllegalArgumentException("Invalid name : $fullName")
        }
        this.firstName = names[0]
        this.familyName = names[1]
    }
}
```
클래스에 주생성자를 선언하지 않은 경우, 모든 부생성자는 자신의 본문을 실행하기 전에 프로퍼티 초기화와 `init` 블록을 실행한다.
이렇게 하면 어떤 부생성자를 호출하든지 공통적인 초기화 코드가 정확히 한 번만 실행되게 보장할 수 있다.

다른 방법으로는 부생성자가 생성자 위임 호출을 사용해 다른 부생성자를 호출하는 것이 있다.
생성자 파라미터 목록 뒤에 콜론(`:`)을 넣고 그 뒤에 일반 함수를 호출하는 것처럼 코드를 작성하되, 함수 이름 대신 `this`를 사용하면 생성자 위임 호출이 된다.
```kotlin
class Person {
    val fullName :String
    
    constructor(firstName :String, familyName :String) : this("$firstName $familyName")
    
    constructor(fullName :String) {
        this.fullName = fullName
    }
}
```

클래스에 주생성자가 있다면, 모든 부생성자는 주생성자에게 위임을 하거나 다른 부생성자에게 위임을 해야한다.
```kotlin
class Person(val fullName :String) {
    constructor(firstName :String, familyName :String) : this("$firstName $familyName")
}
```

부생성자의 파라미터 목록에는 `val`, `var` 키워드를 사용할 수 없다.


## 멤버 가시성
* `public`(공개)
  * 멤버를 어디서나 볼 수 있다.
  * 디폴트 가시성
* `internal`(모듈 내부)
  * 멤버를 멤버가 속한 클래스가 포함된 컴파일 모듈 내부에서만 볼 수 있다.
* `protected`(보호)
  * 멤버를 멤버가 속한 클래스와 멤버가 속한 클래스의 모든 하위 클래스 안에서 볼 수 있다.
* `private`(비공개)
  * 멤버를 멤버가 속한 클래스 내부에서만 볼 수 있다.


## 내포된 클래스
```kotlin
class Person(val id: Id, val age :Int) {
    class Id(val firstName :String, val familyName :String)
    fun showMe() = println("${id.firstName} ${id.familyName}, $age")
}

fun main() {
    val id = Person.Id("John", "Doe")
    val person = Person(id, 25)
    person.showMe()
}
```
자바와 달리 바깥쪽 클래스는 자신에게 내포된 클래스의 비공개 멤버에 접근할 수 없다.
내포된 클래스에 `inner`를 붙이면 자신을 둘러싼 외부 클래스의 현재 인스턴스에 접근할 수 있다.

일반적으로 `this`는 항상 가장 내부의 클래스 인스턴스를 가리킨다. 따라서 내부 클래스 본문에서 `this`는 내부 클래스 자신을 가리킨다.
내부 클래스 본문에서 외부 클래스 인스턴스를 가리켜야한다면 한정시킨(qualified) `this`식을 사용해야 한다.
```kotlin
class Person(val firstName: String, val familyName: String) {
    inner class Possession(val description :String) {
        fun getOwner() = this@Person
    }
} 
```


## 지역 클래스
코틀린에서도 함수 본문에서 클래스를 정의할 수 있다. 이런 지역 클래스는 자신을 둘러싼 코드 블록 안에서만 쓰일 수 있다.
```kotlin
fun main() {
    class Point(val x :Int, val y :Int) {
        fun shift(dx :Int, dy :Int) :Point = Point(x + dx, y + dy)
        override fun toString() = "($x, $y)"
    }
  
    val p = Point(10, 10)
    println(p.shift(-1, 3))
}
```
지역 클래스는 클래스 본문 안에서 자신이 접근할 수 있는 값을 포획(capture)할 수 있고, 심지어는 변경할 수도 있다.
```kotlin
fun main() {
    var x = 1
  
    class Counter {
        fun increment() {
            x++
        }
    }
  
    Counter().increment()
    println(x)  // 2
}
```
하지만 코틀린이 제공하는 포획 변수를 변경하는 기능은 그에 따른 비용을 수반한다.<br>
익명 객체와 이 객체를 둘러싸고 있는 코드 사이에 변수를 공유하기 위해 코틀린 컴파일러는 값을 특별한 래퍼 객체로 둘러싼다.<br>
위 예제를 컴파일한 바이트코드에 해당하는 자바 코드는 아래와 같다.
```java
import kotlin.jvm.internal.Ref.IntRef;

class MainKt {
  public static void main(String[] args) {
    final IntRef x = new IntRef();  // 래퍼 생성
    x.element = 1;
    
    final class Counter {
        public final void increment() {
            x.element++;
        }
    }
    
    (new Counter()).increment();
    System.out.println(x.element);
  }
}
```
내포된 클래스와 달리 지역 클래스에는 가시성 변경자를 붙일 수 없으며, 지역 클래스의 영역은 항상 자기를 둘러싼 블록으로 제한된다.<br>
지역 클래스도 함수, 프로퍼티, 생성자, 내포된 클래스 등 다른 클래스가 포함할 수 있는 모든 멤버를 포함할 수 있따. 하지만 내포된 클래스는 반드시 `inner` 클래스여야만 한다.

---

# 널 가능성
코틀린 타입 시스템에서는 널 값이 될 수 있는 참조 타입과 널 값이 될 수 없는 참조 타입을 확실히 구분해주는 큰 장점이 있다. 
이 기능은 널 발생 여부를 컴파일 시점으로 옮겨주기 때문에 악명 높은 `NullPointerExeption` 예외를 상당 부분 막을 수 있다.

## 널이 될 수 있는 타입





