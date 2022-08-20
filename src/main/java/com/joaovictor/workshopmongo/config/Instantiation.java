package com.joaovictor.workshopmongo.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.joaovictor.workshopmongo.domain.Post;
import com.joaovictor.workshopmongo.domain.User;
import com.joaovictor.workshopmongo.dto.AuthorDTO;
import com.joaovictor.workshopmongo.dto.CommentDTO;
import com.joaovictor.workshopmongo.repository.PostRepository;
import com.joaovictor.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {
		userRepository.deleteAll();
		postRepository.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, Instant.parse("2018-03-21T20:42:00Z"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post post2 = new Post(null, Instant.parse("2018-03-23T20:46:00Z"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Boa viagem mano!", Instant.parse("2018-03-21T21:42:00Z"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite", Instant.parse("2018-03-22T22:15:00Z"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Boa viagem mano!", Instant.parse("2018-03-23T13:15:00Z"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().add(c3);
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPost().addAll(Arrays.asList(post1, post2));
		
		userRepository.save(maria);
	}

}
