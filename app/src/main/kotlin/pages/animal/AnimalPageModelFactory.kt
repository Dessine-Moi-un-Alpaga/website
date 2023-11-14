package be.alpago.website.pages.animal

import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.domain.animal.Animal
import be.alpago.website.domain.fiber.FiberAnalysis

class AnimalPageModelFactory(
    private val animalRepository: Repository<Animal>,
    private val fiberAnalysisRepository: Repository<FiberAnalysis>,
) {

    suspend fun create(id: String): PageModel {
        val animals = animalRepository.findAll()
        val animal = animalRepository.get(id)
        val fiberAnalyses = fiberAnalysisRepository.findBy("animalId", animal.id)
        val pageModel = PageModel(
            title = "${Messages.dmua} :: ${animal.name}",
            description = animal.pageDescription,
            animals = animals,
        )
        pageModel.createAnimalSection(animal, fiberAnalyses)

        return pageModel
    }
}
