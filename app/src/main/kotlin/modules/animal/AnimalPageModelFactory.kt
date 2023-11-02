package be.alpago.website.modules.animal

import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.repository.CrudRepository

class AnimalPageModelFactory(
    private val animalRepository: CrudRepository<Animal>,
    private val fiberAnalysisRepository: CrudRepository<FiberAnalysis>,
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
