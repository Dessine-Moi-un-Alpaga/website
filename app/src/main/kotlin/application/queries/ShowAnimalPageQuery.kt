package be.alpago.website.application.queries

import be.alpago.website.application.AnimalSectionModel
import be.alpago.website.application.Messages
import be.alpago.website.application.PageModel
import be.alpago.website.application.usecases.ShowAnimalPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.FiberAnalysis
import be.alpago.website.libs.domain.ports.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class ShowAnimalPageQuery(
    private val animalRepository: Repository<Animal>,
    private val fiberAnalysisRepository: Repository<FiberAnalysis>,
): ShowAnimalPage {

    override suspend fun execute(id: String) = coroutineScope {
        val animals = async { animalRepository.findAll() }
        val fiberAnalyses = async { fiberAnalysisRepository.findBy("animalId", id) }
        val animal = animalRepository.get(id)

        PageModel(
            title = "${Messages.dmua} :: ${animal.name}",
            description = animal.pageDescription,
            animals = animals.await(),
            sections = listOf(
                AnimalSectionModel(
                    animal = animal,
                    fiberAnalyses = fiberAnalyses.await(),
                )
            )
        )
    }
}
