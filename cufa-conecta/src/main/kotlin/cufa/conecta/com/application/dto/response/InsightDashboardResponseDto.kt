package cufa.conecta.com.application.dto.response

import cufa.conecta.com.application.dto.response.ia.usuario.InsightDashboardDto

data class InsightDashboardResponseDto(
    val insights: List<InsightDashboardDto>
)