package cufa.conecta.com.domain.service.usuario.implementation

import cufa.conecta.com.model.data.usuario.DashboardDados
import org.springframework.stereotype.Service

@Service
class DashboardInsightService {

    fun gerarInsight(
        dados: DashboardDados
    ): String {

        val prompt = """
        Analise os dados:

        Área: ${dados.areaPrincipal}
        Compatibilidade: ${dados.compatibilidade}%
        Salário: ${dados.salarioMedio}
        Empregabilidade: ${dados.empregabilidade}%

        Gere um insight curto.
        """.trimIndent()



        return "Seu perfil apresenta boa aderência ao setor de comércio."
    }
}