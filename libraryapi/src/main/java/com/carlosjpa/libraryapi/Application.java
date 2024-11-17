package com.carlosjpa.libraryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
//		System.out.println(repository.getClass().getName());
//		exemploSalvarRegistro(repository);

	}

//	public static void exemploSalvarRegistro(AutorRepository autorRepository) {
//		Autor autor = new Autor();
//		autor.setNome("José");
//		autor.setNacionalidade("Brasileira");
//		autor.setDataNascimento(LocalDate.of(1950,1,31));
//
//		var autorSalvo = autorRepository.save(autor);
//		System.out.println("Autor salvo"+ autorSalvo);
//	}

}
