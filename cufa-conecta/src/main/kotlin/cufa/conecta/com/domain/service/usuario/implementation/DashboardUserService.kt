package cufa.conecta.com.domain.service.usuario.implementation

import cufa.conecta.com.application.dto.response.usuario.DashboardResponse
import cufa.conecta.com.domain.service.ai.IaGenerativaService
import cufa.conecta.com.resources.usuario.impl.DashboardRepository
import org.springframework.stereotype.Service

@Service
class DashboardUserService(
    private val dashboardRepository: DashboardRepository,
    private val dashboardInsightService: DashboardInsightService,
    private val iaGenerativaService: IaGenerativaService
) {

    fun buscarDashboard(
        usuarioId: Long
    ): DashboardResponse {

        val dados = dashboardRepository.buscarDadosDashboard(
            usuarioId
        )

        val insight = dashboardInsightService.gerarInsight(
            dados
        )

        return DashboardResponse(
            areaPrincipal = dados.areaPrincipal,
            compatibilidade = dados.compatibilidade,
            salarioMedio = dados.salarioMedio,
            empregabilidade = dados.empregabilidade,
            insight = insight,
            grafico = dados.grafico
        )
    }
}