package be.alpago.website.application

import be.alpago.website.domain.Animal
import java.util.SortedMap

typealias Categories = SortedMap<NavigationModel.Category, MutableSet<Animal>>

class NavigationModel(
    animals: List<Animal>,
) {

    val animalsByCategory: Map<Category, Set<Animal>>

    init {
        val categories: Categories = sortedMapOf(compareBy { it.order })

        animals.forEach { animal ->
            val category = animal.category
            val categoryAnimals = categories[category] ?: sortedSetOf(compareBy { it.dateOfBirth })
            categoryAnimals.add(animal)
            categories[category] = categoryAnimals
        }

        this.animalsByCategory = categories.toMap()
    }

    enum class Category {
        DOG,
        GELDING,
        MARE,
        SOLD,
        STUD,
    }

    private val Animal.Type.category: Category
        get() = when (this) {
            Animal.Type.DOG     -> Category.DOG
            Animal.Type.GELDING -> Category.GELDING
            Animal.Type.MARE    -> Category.MARE
            Animal.Type.STUD    -> Category.STUD
        }

    private val Animal.category: Category
        get() = if (sold) Category.SOLD else type.category

    private val Category.order: Int
        get() = when (this) {
            Category.STUD    -> 0
            Category.MARE    -> 1
            Category.GELDING -> 2
            Category.SOLD    -> 3
            Category.DOG     -> 4
        }
}
