package mortum.skufservices.persistence.repository.order

import mortum.skufservices.persistence.model.service.ServiceModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<ServiceModel, String>