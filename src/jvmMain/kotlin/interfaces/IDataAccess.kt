package interfaces

interface IDataAccess<T> {
    fun create(entity: T)
    fun getAll(): List<T>
    fun selectById(inicial: String): List<T>
    fun update(entity: T)
    fun delete(inicial: String)
    fun deleteALL()
}