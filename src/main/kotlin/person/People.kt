package person

abstract class People {
    abstract var id: Int
    abstract var name: String
    abstract var age: Int?

    constructor(id: Int, name: String, age: Int?) {
        this.id = id
        this.name = name
        this.age = age
    }

    open fun printAll() {
        println("MSSV: $id")
        println("Họ tên: $name")
        println("Tuổi: $age")
    }
}