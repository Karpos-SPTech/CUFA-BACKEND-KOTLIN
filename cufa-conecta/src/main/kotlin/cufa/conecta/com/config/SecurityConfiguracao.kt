package cufa.conecta.com.config

import cufa.conecta.com.resources.AutenticacaoRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguracao(
    private val autenticacaoRepository: AutenticacaoRepository,
    private val autenticacaoEntryPoint: AutenticacaoEntryPoint
) {
    companion object {
        private val ALLOWED_URLS =
            arrayOf(
                AntPathRequestMatcher("/swagger-ui/**"),
                AntPathRequestMatcher("/swagger-ui.html"),
                AntPathRequestMatcher("/swagger-resources/**"),
                AntPathRequestMatcher("/configuration/ui"),
                AntPathRequestMatcher("/configuration/security"),
                AntPathRequestMatcher("/api/public/**"),
                AntPathRequestMatcher("/webjars/**"),
                AntPathRequestMatcher("/v3/api-docs/**"),
                AntPathRequestMatcher("/actuator/*"),
                AntPathRequestMatcher("/users/**"),
                AntPathRequestMatcher("/roles/**"),
                AntPathRequestMatcher("/error/**"),
                AntPathRequestMatcher("/actuator/**")
            )
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .headers { it.frameOptions { options -> options.disable() } }
            .cors { }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(*ALLOWED_URLS).permitAll()
                    .anyRequest().authenticated()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(autenticacaoEntryPoint)
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun jwtAuthenticationEntryPointBean(): AutenticacaoEntryPoint = AutenticacaoEntryPoint()

    @Bean
    fun jwtAuthenticationFilterBean(): AutenticacaoFilter {
        return AutenticacaoFilter(autenticacaoRepository, jwtAuthenticationUtilBean())
    }

    @Bean
    fun jwtAuthenticationUtilBean(): GerenciadorTokenJwt = GerenciadorTokenJwt()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().applyPermitDefaultValues()
        configuration.allowedMethods =
            listOf(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name(),
                HttpMethod.HEAD.name(),
                HttpMethod.TRACE.name()
            )
        configuration.exposedHeaders = listOf(HttpHeaders.CONTENT_DISPOSITION)

        val urlBasedCorsSource = UrlBasedCorsConfigurationSource()
        urlBasedCorsSource.registerCorsConfiguration("/**", configuration)

        return urlBasedCorsSource
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager
}