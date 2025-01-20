package mortum.skufservices.persistence.repository.order

import mortum.skufservices.persistence.model.order.AlcoholDrink
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlcoholDrinkRepository : JpaRepository<AlcoholDrink, String>