package mortum.skufservices.persistence.repository.service

import mortum.skufservices.persistence.model.service.ServiceModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ServiceRepository : JpaRepository<ServiceModel, String>