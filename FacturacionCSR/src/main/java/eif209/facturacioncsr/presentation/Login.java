package eif209.facturacioncsr.presentation;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import eif209.facturacioncsr.logic.Usuario;
import eif209.facturacioncsr.security.UserDetailsImp;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/login")
public class Login {
    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario form, HttpServletRequest request) {
        try {
            request.login(form.getNombreUsuario(), form.getContrasena());
        } catch (ServletException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Authentication auth = (Authentication) request.getUserPrincipal();
        Usuario usuario = ((UserDetailsImp) auth.getPrincipal()).usuario();
        return new Usuario(usuario.getNombreUsuario(), null, usuario.getRol());
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        try {
            request.logout();
        } catch (ServletException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al cerrar sesión");
        }
    }

    @GetMapping("/current-user")
    //@PreAuthorize("isAuthenticated()") // Asegura que el usuario esté autenticado
    public Usuario getCurrentUser(@AuthenticationPrincipal UserDetailsImp user) {
        return new Usuario(user.usuario().getNombreUsuario(), null, user.usuario().getRol());
    }
}
