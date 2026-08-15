package com.dessinemoiunalpaga.website.application.queries

import com.dessinemoiunalpaga.website.application.AnimalSectionModel
import com.dessinemoiunalpaga.website.application.PageModel
import com.dessinemoiunalpaga.website.application.usecases.ShowAnimalPage
import com.dessinemoiunalpaga.website.domain.Animal
import com.dessinemoiunalpaga.website.domain.FiberAnalysis
import com.dessinemoiunalpaga.website.i18n.Messages
import com.dessinemoiunalpaga.website.libs.domain.ports.persistence.AggregateRootNotFound
import com.dessinemoiunalpaga.website.libs.domain.ports.persistence.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class ShowAnimalPageQuery(
    private val animalRepository: Repository<Animal>,
    private val fiberAnalysisRepository: Repository<FiberAnalysis>,
): ShowAnimalPage {

    override suspend fun execute(id: String) = coroutineScope {
        val fiberAnalyses = async { fiberAnalysisRepository.findBy("animalId", id) }
        val animals = animalRepository.findAll()
        val animal = animals.find { it.id == id } ?: throw AggregateRootNotFound()

        PageModel(
            title = "${Messages.dmua} :: ${animal.name}",
            description = animal.pageDescription,
            animals = animals,
            sections = listOf(
                AnimalSectionModel(
                    animal = animal,
                    fiberAnalyses = fiberAnalyses.await(),
                    id = "animal"
                )
            )
        )
    }
}
