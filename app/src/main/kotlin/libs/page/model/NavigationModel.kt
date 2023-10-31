package be.alpago.website.libs.page.model

import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.animal.Type

enum class Category {
    DOG,
    GELDING,
    MARE,
    SOLD,
    STUD,
}

private fun Animal.getCategory() = if (sold) {
    Category.SOLD
} else {
    type.toCategory()
}

private fun Type.toCategory() = when (this) {
    Type.DOG -> Category.DOG
    Type.GELDING -> Category.GELDING
    Type.MARE -> Category.MARE
    Type.STUD -> Category.STUD
}

class NavigationModel(
    animals: List<Animal>,
) {

    val categories: Map<Category, Set<Animal>>

    init {
        val categories = sortedMapOf<Category, MutableSet<Animal>>(CATEGORY_COMPARATOR)
        animals.forEach { animal ->
            val category = animal.getCategory()
            val categoryAnimals = categories.getOrDefault(category, sortedSetOf(ANIMAL_COMPARATOR))
            categoryAnimals.add(animal)
            categories[category] = categoryAnimals
        }
        this.categories = categories.toMap()
    }
}

private fun Category.order() = when (this) {
    Category.STUD -> 0
    Category.MARE -> 1
    Category.GELDING -> 2
    Category.SOLD -> 3
    Category.DOG -> 4
}

private val CATEGORY_COMPARATOR = Comparator<Category> { o1, o2 -> o1.order().compareTo(o2.order()) }

private val ANIMAL_COMPARATOR = Comparator<Animal> { o1, o2 -> o1.dateOfBirth.compareTo(o2.dateOfBirth) }
