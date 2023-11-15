package be.alpago.website.application

import be.alpago.website.domain.Animal
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.libs.domain.ports.Repository

class AnimalPageModelFactory(
    private val animalRepository: Repository<Animal>,
    private val fiberAnalysisRepository: Repository<FiberAnalysis>,
) {

    suspend fun create(id: String): PageModel {
        val animals = animalRepository.findAll()
        val animal = animalRepository.get(id)
        val fiberAnalyses = fiberAnalysisRepository.findBy("animalId", animal.id)

        return PageModel(
            title = "${Messages.dmua} :: ${animal.name}",
            description = animal.pageDescription,
            animals = animals,
            sections = listOf(
                AnimalSectionModel(
                    animal = animal,
                    fiberAnalyses = fiberAnalyses,
                )
            )
        )
    }
}
