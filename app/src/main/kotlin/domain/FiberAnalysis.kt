package be.alpago.website.domain

import be.alpago.website.libs.domain.AggregateRoot
import kotlinx.serialization.Serializable

@Serializable
data class FiberAnalysis(
    val animalId: String,
    val averageFiberDiameter: String,
    val coefficientOfVariation: String,
    val comfortFactor: String,
    val standardDeviation: String,
    val year: Int,
) : AggregateRoot {

    override val id = "${animalId}-${year}"
}
