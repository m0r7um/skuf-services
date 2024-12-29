package mortum.skufservices.persistence.repository.order

import mortum.skufservices.persistence.model.order.AlcoholDrink
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AlcoholDrinkRepository : JpaRepository<AlcoholDrink, UUID>