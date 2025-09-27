package repository

interface Repository<T, ID> {
    fun add(item: T)
    fun findById(id: ID): T?
    fun removeById(id: ID): Boolean
    fun all(): List<T>
    fun printAll()
}