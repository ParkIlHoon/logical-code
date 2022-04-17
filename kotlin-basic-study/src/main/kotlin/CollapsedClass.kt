fun main() {
    Outer.Nested().introduce()

    val outer = Outer()
    val inner = outer.Inner()

    inner.introduce()
    inner.introduceOuter()

    outer.text = "changed outer text"
    inner.introduceOuter()
}

class Outer {
    var text = "outer class"

    class Nested {
        fun introduce() {
            println("Nested Class")
        }
    }

    inner class Inner {
        var text = "Inner Class"

        fun introduce() {
            println(text)
        }

        fun introduceOuter() {
            println(this@Outer.text)
        }
    }
}