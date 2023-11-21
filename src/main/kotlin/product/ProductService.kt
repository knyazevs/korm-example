package s.knyazev.example.product

import kotlinx.uuid.UUID
import s.knyazev.Query
import s.knyazev.eq

class ProductService {
    fun get(): List<ProductEntity> {
        return ProductTable.all()
    }

    fun create(create: ProductEntity): ProductEntity {
        ProductTable.new(create)
        return create.id?.let { ProductTable.findById(it) } ?: throw Exception("Product not created")
    }

    fun update(productId: UUID, update: ProductEntity): ProductEntity? {
        ProductTable.update(Query(ProductTable.id eq productId.toString()), update)
        return ProductTable.findById(productId)
    }

    fun delete(productId: UUID): Boolean {
        ProductTable.deleteWhere(Query(ProductTable.id eq productId.toString()))
        return ProductTable.findById(productId) == null
    }
}