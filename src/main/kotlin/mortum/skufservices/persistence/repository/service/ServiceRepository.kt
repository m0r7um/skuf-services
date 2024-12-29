package mortum.skufservices.persistence.repository.service

import mortum.skufservices.persistence.model.service.Service
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ServiceRepository : JpaRepository<Service, UUID>