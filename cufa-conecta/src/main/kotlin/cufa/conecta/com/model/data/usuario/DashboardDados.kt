package cufa.conecta.com.model.data.usuario

import cufa.conecta.com.application.dto.response.usuario.DashboardChartItemDto

data class DashboardDados(
    val areaPrincipal: String,
    val compatibilidade: Int,
    val salarioMedio: Int,
    val empregabilidade: Int,
    val grafico: List<DashboardChartItemDto>
)