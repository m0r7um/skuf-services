package mortum.skufservices.persistence.repository.order

import mortum.skufservices.persistence.model.order.WoTOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface WoTOrderRepository : JpaRepository<WoTOrder, UUID>