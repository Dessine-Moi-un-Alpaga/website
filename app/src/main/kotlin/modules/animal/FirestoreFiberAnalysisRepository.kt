package be.alpago.website.modules.animal

import be.alpago.website.libs.repository.FirestoreAggregateTransformer

const val FIBER_ANALYSIS_COLLECTION = "fiberAnalyses"

private object FiberAnalysisFields {

    const val animalId = "animalId"
    const val averageFiberDiameter = "averageFiberDiameter"
    const val coefficientOfVariation = "coefficientOfVariation"
    const val comfortFactor = "comfortFactor"
    const val standardDeviation = "standardDeviation"
    const val year = "year"
}

object FirestoreFiberAnalysisTransformer : FirestoreAggregateTransformer<FiberAnalysis> {

    override fun fromDomain(aggregateRoot: FiberAnalysis) = mapOf(
        FiberAnalysisFields.animalId to aggregateRoot.animalId,
        FiberAnalysisFields.averageFiberDiameter to aggregateRoot.averageFiberDiameter,
        FiberAnalysisFields.coefficientOfVariation to aggregateRoot.coefficientOfVariation,
        FiberAnalysisFields.comfortFactor to aggregateRoot.comfortFactor,
        FiberAnalysisFields.standardDeviation to aggregateRoot.standardDeviation,
        FiberAnalysisFields.year to aggregateRoot.year,
    )

    override fun toDomain(fields: Map<String, Any?>) = FiberAnalysis(
        animalId = fields[FiberAnalysisFields.animalId] as String,
        averageFiberDiameter = fields[FiberAnalysisFields.averageFiberDiameter] as String,
        coefficientOfVariation = fields[FiberAnalysisFields.coefficientOfVariation] as String,
        comfortFactor = fields[FiberAnalysisFields.comfortFactor] as String,
        standardDeviation = fields[FiberAnalysisFields.standardDeviation] as String,
        year = fields[FiberAnalysisFields.year] as Int,
    )
}
