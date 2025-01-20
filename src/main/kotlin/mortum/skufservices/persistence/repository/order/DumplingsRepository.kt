package mortum.skufservices.persistence.repository.order

import mortum.skufservices.persistence.model.order.Dumplings
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DumplingsRepository : JpaRepository<Dumplings, String>