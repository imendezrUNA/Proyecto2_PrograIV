//PARA ENCRIPTAR TODAS LAS CONTRASEÑAS EXISTENTES EN LA DB. (CUIDADO DE NO ENCRIPTAR UNA CONTRASEÑA YA ENCRIPTADA PREVIAMENTE)

/*package eif209.facturacioncsr;

import eif209.facturacioncsr.data.UsuarioRepository;
import eif209.facturacioncsr.logic.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUpdater implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Iterable<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario usuario : usuarios) {
            String encodedPassword = "{bcrypt}"+encoder.encode(usuario.getContrasena());
            usuario.setContrasena(encodedPassword);
            usuarioRepository.save(usuario);
        }
    }
}*/
