package cufa.conecta.com.resources.usuario.impl

import cufa.conecta.com.application.dto.response.usuario.DashboardChartItemDto
import cufa.conecta.com.model.data.usuario.DashboardDados
import org.springframework.stereotype.Repository

@Repository
class DashboardRepository {

    fun buscarDadosDashboard(
        usuarioId: Long
    ): DashboardDados {

        return DashboardDados(
            areaPrincipal = "Comércio",
            compatibilidade = 82,
            salarioMedio = 2100,
            empregabilidade = 64,
            grafico = listOf(
                DashboardChartItemDto("Comércio", 45),
                DashboardChartItemDto("Serviços", 30),
                DashboardChartItemDto("Logística", 15),
                DashboardChartItemDto("Construção", 10)
            )
        )
    }
}