package com.BandManagement;

import com.BandManagement.persistence.repositories.BandRepository;
import com.BandManagement.persistence.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class BandManagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BandManagementApplication.class, args);
	}

	@Autowired
	private BandRepository bandRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Override
	public void run(String... args) throws Exception {

		/*Genre genre1 = new Genre();
		genre1.setGenreName("Pop");
		genreRepository.save(genre1);

		Genre genre2 = new Genre();
		genre2.setGenreName("Pop-Rock");
		genreRepository.save(genre2);

		Band band1 = new Band();
		band1.setBandName("Duran Duran");
		band1.setYear(1978);
		genre1 = genreRepository.findByGenreName(String.valueOf(genre1));
		band1.setGenre(genre1);
		bandRepository.save(band1);

		Band band2 = new Band();
		band2.setBandName("Bon Jovi");
		band2.setYear(1983);
		genre2 = genreRepository.findByGenreName(String.valueOf(genre2));
		band2.setGenre(genre2);
		bandRepository.save(band2);*/
	}

}
