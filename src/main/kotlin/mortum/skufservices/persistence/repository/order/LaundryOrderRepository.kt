package mortum.skufservices.persistence.repository.order

import mortum.skufservices.persistence.model.order.LaundryOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LaundryOrderRepository : JpaRepository<LaundryOrder, String>