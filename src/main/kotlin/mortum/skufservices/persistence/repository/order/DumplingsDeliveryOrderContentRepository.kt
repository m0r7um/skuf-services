package mortum.skufservices.persistence.repository.order

import mortum.skufservices.persistence.model.order.DumplingsDeliveryOrderContent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DumplingsDeliveryOrderContentRepository : JpaRepository<DumplingsDeliveryOrderContent, String>