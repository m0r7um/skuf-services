package mortum.skufservices.dto

data class GetPageServiceResponse(
    val data: List<GetServiceResponse>,
    val currentPage: Int,
    val totalPage: Int,
)