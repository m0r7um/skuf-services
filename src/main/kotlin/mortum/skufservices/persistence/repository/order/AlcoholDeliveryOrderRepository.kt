package mortum.skufservices.persistence.repository.order

import mortum.skufservices.persistence.model.order.AlcoholDeliveryOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AlcoholDeliveryOrderRepository : JpaRepository<AlcoholDeliveryOrder, UUID>