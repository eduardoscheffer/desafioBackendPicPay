package eduardo.picPaySimplificado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class }) // usado pra nao pedir autenticacao dos endpoints - TEMPORARIOS
public class PicPaySimplificadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicPaySimplificadoApplication.class, args);
	}

}
