package be.alpago.website.libs.page.model

import be.alpago.website.modules.animal.Animal

class NavigationModel(
    animals: List<Animal>,
) {

    val animalsByCategory: Map<Category, Set<Animal>>

    init {
        val categories = sortedMapOf<Category, MutableSet<Animal>>(compareBy { it.order })

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
