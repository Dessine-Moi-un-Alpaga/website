package be.alpago.website.adapters.persistence.firestore

import be.alpago.website.domain.FiberAnalysis

private const val ANIMAL_ID = "animalId"
private const val AVERAGE_FIBER_DIAMETER = "averageFiberDiameter"
private const val COEFFICIENT_OF_VARIATION = "coefficientOfVariation"
private const val COMFORT_FACTOR = "comfortFactor"
private const val STANDARD_DEVIATION = "standardDeviation"
private const val YEAR = "year"

class FirestoreFiberAnalysisTransformer : FirestoreAggregateTransformer<FiberAnalysis> {

    override fun fromDomain(aggregateRoot: FiberAnalysis) = mapOf(
        ANIMAL_ID to aggregateRoot.animalId,
        AVERAGE_FIBER_DIAMETER to aggregateRoot.averageFiberDiameter,
        COEFFICIENT_OF_VARIATION to aggregateRoot.coefficientOfVariation,
        COMFORT_FACTOR to aggregateRoot.comfortFactor,
        STANDARD_DEVIATION to aggregateRoot.standardDeviation,
        YEAR to aggregateRoot.year,
    )

    override fun toDomain(fields: Map<String, Any?>) = FiberAnalysis(
        animalId = fields[ANIMAL_ID] as String,
        averageFiberDiameter = fields[AVERAGE_FIBER_DIAMETER] as String,
        coefficientOfVariation = fields[COEFFICIENT_OF_VARIATION] as String,
        comfortFactor = fields[COMFORT_FACTOR] as String,
        standardDeviation = fields[STANDARD_DEVIATION] as String,
        year = fields[YEAR] as Int,
    )
}
