package mortum.skufservices.persistence.repository.order

import mortum.skufservices.persistence.model.order.AltushkaOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AltushkaOrderRepository : JpaRepository<AltushkaOrder, String>