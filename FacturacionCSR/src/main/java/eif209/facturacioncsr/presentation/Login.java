package eif209.facturacioncsr.presentation;

import eif209.facturacioncsr.logic.Usuario;
import eif209.facturacioncsr.security.UserDetailsImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        return new Usuario(usuario.getId(), usuario.getNombreUsuario(), null, usuario.getRol()); // Asegúrate de devolver el ID
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
    public Usuario getCurrentUser(@AuthenticationPrincipal UserDetailsImp user) {
        return new Usuario(user.usuario().getId(), user.getUsername(), null, user.usuario().getRol()); // Asegúrate de devolver el ID
    }
}
