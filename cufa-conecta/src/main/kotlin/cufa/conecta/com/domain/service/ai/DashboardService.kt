package cufa.conecta.com.domain.service.ai

import cufa.conecta.com.application.dto.response.ia.usuario.InsightDashboardDto
import cufa.conecta.com.application.exception.CreateInternalServerError
import cufa.conecta.com.resources.empresa.PublicacaoRepository
import cufa.conecta.com.resources.usuario.ExperienciaRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class DashboardService(
    private val experienciaRepository: ExperienciaRepository,
    private val publicacaoRepository: PublicacaoRepository,
    private val iaGenerativaService: IaGenerativaService
) {

    fun gerarInsightsCandidato(): List<InsightDashboardDto> {
        val auth = SecurityContextHolder.getContext().authentication
            ?: throw CreateInternalServerError("Usuário não autenticado")

        if (!auth.isAuthenticated) throw CreateInternalServerError("Usuário não autenticado")

        val email = auth.name

        val experiencias = experienciaRepository.listarPorUsuario(email!!)
        val vagas = publicacaoRepository.buscarTodas(1, 50).publicacoes

        return iaGenerativaService.gerarInsightsEmpregabilidade(
            experiencias,
            vagas
        )
    }
}