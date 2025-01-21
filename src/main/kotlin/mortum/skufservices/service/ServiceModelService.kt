package mortum.skufservices.service

import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import mortum.skufservices.dto.GetServiceResponse
import mortum.skufservices.dto.PageWrapper
import mortum.skufservices.mapper.ServiceMapper
import mortum.skufservices.persistence.model.service.ServiceModel
import mortum.skufservices.persistence.model.service.ServiceType
import mortum.skufservices.persistence.repository.service.ServiceRepository
import mortum.skufservices.utils.CommonUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.math.ceil

@Service
class ServiceModelService(
    val serviceMapper: ServiceMapper,
    val entityManager: EntityManager,
    val serviceRepository: ServiceRepository,
) {

    fun getAll(page: Int, search: String?, type: List<ServiceType>?): PageWrapper<GetServiceResponse> {
        val builder = entityManager.criteriaBuilder

        val countQuery = builder.createQuery(Long::class.java)
        var root = countQuery.from(ServiceModel::class.java)
        var predicates = generatePredicates(builder, root, search, type, null)
        countQuery.select(builder.count(root)).where(*predicates)
        val countPage = ceil(entityManager.createQuery(countQuery).singleResult / PAGE_SIZE.toDouble()).toInt()

        val criteriaQuery = builder.createQuery(ServiceModel::class.java)
        root = criteriaQuery.from(ServiceModel::class.java)
        predicates = generatePredicates(builder, root, search, type, null)
        val select = criteriaQuery.select(root).where(*predicates)
        val services = entityManager.createQuery(select).setFirstResult((page - 1) * PAGE_SIZE).setMaxResults(PAGE_SIZE).resultList
        return serviceMapper.mapToGetPageServiceResponse(services, page, countPage)
    }

    fun getByProvider(page: Int, search: String?): PageWrapper<GetServiceResponse> {
        val builder = entityManager.criteriaBuilder
        val userId = CommonUtils.getUserIdFromSecurityContext()

        val countQuery = builder.createQuery(Long::class.java)
        var root = countQuery.from(ServiceModel::class.java)
        var predicates = generatePredicates(builder, root, search, null, userId)
        countQuery.select(builder.count(root)).where(*predicates)
        val countPage = ceil(entityManager.createQuery(countQuery).singleResult / PAGE_SIZE.toDouble()).toInt()

        val criteriaQuery = builder.createQuery(ServiceModel::class.java)
        root = criteriaQuery.from(ServiceModel::class.java)
        predicates = generatePredicates(builder, root, search, null, userId)
        val select = criteriaQuery.select(root).where() .where(*predicates)
        val services = entityManager.createQuery(select).setFirstResult((page - 1) * PAGE_SIZE).setMaxResults(PAGE_SIZE).resultList
        return serviceMapper.mapToGetPageServiceResponse(services, page, countPage)
    }

    fun getById(id: String): GetServiceResponse? {
        return serviceRepository.findByIdOrNull(id)?.let { serviceMapper.mapToGetServiceResponse(it) }
    }

    private fun generatePredicates(
        builder: CriteriaBuilder,
        root: Root<ServiceModel>,
        search: String?,
        type: List<ServiceType>?,
        userId: String?,
    ): Array<Predicate> {
        val predicates = mutableListOf<Predicate?>()
        if (search != null) {
            val predicateTitle = builder.like(root.get("title"), "%$search%")
            val predicateDescription = builder.like(root.get("description"), "%$search%")
            predicates.add(builder.or(predicateTitle, predicateDescription))
        }
        if (type != null) {
            val predicateType = root.get<String>("type").`in`(type)
            predicates.add(predicateType)
        }
        if (userId != null) {
            val predicateUserId = builder.equal(root.get<String>("user").get<String>("id"), userId)
            predicates.add(predicateUserId)
        }

        return predicates.requireNoNulls().toTypedArray()
    }

    companion object {
        const val PAGE_SIZE = 2
    }
}