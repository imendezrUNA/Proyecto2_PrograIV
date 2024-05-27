package eif209.facturacioncsr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@SpringBootApplication
public class FacturacionCsrApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacturacionCsrApplication.class, args);
    }

	/*@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance(); // Desactivar encriptación de contraseñas
	}*/

    @Bean("securityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login/login").permitAll()
                        .requestMatchers("/api/registro/register").permitAll()
                        .requestMatchers("/api/login/logout").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAnyAuthority("ADMINISTRADOR")
                        .requestMatchers("/api/**").hasAnyAuthority("ADMINISTRADOR", "PROVEEDOR")
                        //.requestMatchers("/api/login/current-user").authenticated() // Permitir el acceso al endpoint de usuario actual
                        .requestMatchers("/**").permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        //.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}