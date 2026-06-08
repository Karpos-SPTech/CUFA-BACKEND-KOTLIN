package cufa.conecta.com.application.dto.response.usuario

data class DashboardResponse(
    val areaPrincipal: String,
    val compatibilidade: Int,
    val salarioMedio: Int,
    val empregabilidade: Int,
    val insight: String,
    val grafico: List<DashboardChartItemDto>
)