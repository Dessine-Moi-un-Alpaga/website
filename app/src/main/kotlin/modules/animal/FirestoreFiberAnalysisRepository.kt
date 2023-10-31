package be.alpago.website.modules.animal

import be.alpago.website.libs.repository.AbstractFirestoreCrudRepository
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Firestore

private const val COLLECTION = "fiberAnalyses"

private object FiberAnalysisFields {
    const val animalId = "animalId"
    const val averageFiberDiameter = "averageFiberDiameter"
    const val coefficientOfVariation = "coefficientOfVariation"
    const val comfortFactor = "comfortFactor"
    const val standardDeviation = "standardDeviation"
    const val year = "year"
}

class FirestoreFiberAnalysisRepository(
    db: Firestore,
    environment: String,
) : AbstractFirestoreCrudRepository<FiberAnalysis>(
    COLLECTION,
    db,
    environment,
) {
    override fun DocumentSnapshot.toDomain() = FiberAnalysis(
        animalId = getString(FiberAnalysisFields.animalId)!!,
        averageFiberDiameter = getString(FiberAnalysisFields.averageFiberDiameter)!!,
        coefficientOfVariation = getString(FiberAnalysisFields.coefficientOfVariation)!!,
        comfortFactor = getString(FiberAnalysisFields.comfortFactor)!!,
        standardDeviation = getString(FiberAnalysisFields.standardDeviation)!!,
        year = getLong(FiberAnalysisFields.year)!!.toInt()
    )

    override suspend fun DocumentReference.fromDomain(aggregateRoot: FiberAnalysis) {
        set(
            mapOf(
                FiberAnalysisFields.animalId to aggregateRoot.animalId,
                FiberAnalysisFields.averageFiberDiameter to aggregateRoot.averageFiberDiameter,
                FiberAnalysisFields.coefficientOfVariation to aggregateRoot.coefficientOfVariation,
                FiberAnalysisFields.comfortFactor to aggregateRoot.comfortFactor,
                FiberAnalysisFields.standardDeviation to aggregateRoot.standardDeviation,
                FiberAnalysisFields.year to aggregateRoot.year.toLong()
            )
        )
    }
}
