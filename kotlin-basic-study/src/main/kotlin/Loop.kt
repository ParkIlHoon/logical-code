fun main() {
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