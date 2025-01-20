package mortum.skufservices.service

import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import mortum.skufservices.dto.GetPageServiceResponse
import mortum.skufservices.dto.GetServiceResponse
import mortum.skufservices.mapper.ServiceMapper
import mortum.skufservices.persistence.model.service.ServiceModel
import mortum.skufservices.persistence.model.service.ServiceType
import mortum.skufservices.persistence.repository.service.ServiceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.math.ceil

@Service
class ServiceModelService(
    val serviceMapper: ServiceMapper,
    val entityManager: EntityManager,
    val serviceRepository: ServiceRepository,
) {

    fun getAll(page: Int, search: String?, type: List<ServiceType>?): GetPageServiceResponse {
        val builder = entityManager.criteriaBuilder

        val countQuery = builder.createQuery(Long::class.java)
        var root = countQuery.from(ServiceModel::class.java)
        var predicates = generateQuery(builder, root, search, type)
        countQuery.select(builder.count(root)).where(predicates)
        val countPage = ceil(entityManager.createQuery(countQuery).singleResult / PAGE_SIZE.toDouble()).toInt()

        val criteriaQuery = builder.createQuery(ServiceModel::class.java)
        root = criteriaQuery.from(ServiceModel::class.java)
        predicates = generateQuery(builder, root, search, type)
        val select = criteriaQuery.select(root).where(predicates)
        val services = entityManager.createQuery(select).setFirstResult((page - 1) * PAGE_SIZE).setMaxResults(PAGE_SIZE).resultList
        return serviceMapper.mapToGetPageServiceResponse(services, page, countPage)
    }

    fun getById(id: String): GetServiceResponse? {
        return serviceRepository.findByIdOrNull(id)?.let { serviceMapper.mapToGetServiceResponse(it) }
    }

    private fun generateQuery(builder: CriteriaBuilder, root: Root<ServiceModel>, search: String?, type: List<ServiceType>?): Predicate? {
        var predicates: Predicate? = null
        if (search != null) {
            val predicateTitle = builder.like(root.get("title"), "%$search%")
            val predicateDescription = builder.like(root.get("description"), "%$search%")
            predicates = builder.or(predicateTitle, predicateDescription)
        }
        if (type != null) {
            val predicateType = root.get<String>("type").`in`(type)
            predicates = when (predicates) {
                null -> predicateType
                else -> builder.and(predicates, predicateType)
            }
        }
        return predicates
    }

    companion object {
        private const val PAGE_SIZE = 2
    }
}