package com.bruno.literAlura;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.bruno.literAlura.dtos.BookResponseDto;
import com.bruno.literAlura.services.BookService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class LiterAluraApplication implements CommandLineRunner {

	private final BookService bookService;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		try (Scanner sc = new Scanner(System.in)) {
			int option;
			String searchAndSave;
			String title;
			String containigTitle;
			String authorName;
			String language;
			Integer birth_year;
			Integer death_year;
			while (true) {

				System.out.println("Escolha uma das opções Abaixo: ");
				System.out.println("+++++++++++++++++++++++++++++++");
				System.out.println("[1] - Procurar um livro e salvar");
				System.out.println("[2] - Procurar um livro salvo, pelo titulo");
				System.out.println("[3] - Procurar um livro salvo, por uma parte do tittulo");
				System.out.println("[4] - Listar todos os livros");
				System.out.println("[5] - Encontrar livro pelo nome do autor");
				System.out.println("[6] - Listar livros pelo idioma");
				System.out.println("[7] - Procurar livros pelo ano de nascimento e ano de morte do autor");
				System.out.println("[0] - SAIR");

				System.out.print("OPÇÃO: ");

				try {

					option = sc.nextInt();
					sc.nextLine();

					if (option < 0 | option > 7) {
						System.out.println("========== OPÇÃO INVALIDA!!! - TENTE NOVAMENTE ==========");
						continue;
					}

					if (option == 0) {
						break;
					}

					switch (option) {
						case 1:
							System.out.print("Informe o titulo do livro: ");
							searchAndSave = sc.nextLine();

							var response = bookService.searchBook(searchAndSave);

							if (response == null) {
								System.out.println("Livro buscado: " + searchAndSave);
								System.out.println("Livro já cadastrado ou não encontrado! Tente novamente");
							} else {
								System.out.println("Livro buscado e salvo!");
								System.out.println("__________#___________");
								System.out.println("+ Nome: " + response.getTitle());
								System.out.println("+ Idiomas disponiveis" + response.getLanguages());
								System.out.println("+ Autor" + response.getAuthor().getName());
								System.out.println("+ Ano de Nascimento" +
										response.getAuthor().getBirthYear());
								System.out.println("+ Ano de morte" + response.getAuthor().getDeathYear());
								System.out.println("+ Nº de downloads" + response.getDownload_count());
								System.out.println("__________#___________");
							}

							break;

						case 2:

							System.out.print("Informe o titulo que deseja buscar: ");
							title = sc.nextLine();

							var response2 = bookService.findByTitle(title);

							if (response2.isEmpty()) {
								System.out.println("Livro: " + title);
								System.out.println("Livro não encontrado! Tente novamente");
							} else {
								System.out.println("Livro encontrado!");
								System.out.println("__________#___________");
								System.out.println("+ Nome: " + response2.get().getTitle());
								System.out.println("+ Idiomas disponiveis" + response2.get().getLanguages());
								System.out.println("+ Autor" + response2.get().getAuthor().getName());
								System.out.println("+ Ano de Nascimento" +
										response2.get().getAuthor().getBirthYear());
								System.out.println("+ Ano de morte" +
										response2.get().getAuthor().getDeathYear());
								System.out.println("+ Nº de downloads" + response2.get().getDownload_count());
								System.out.println("__________#___________");
							}
							break;

						case 3:
							System.out.print("Informe trecho do titulo que deseja buscar: ");
							containigTitle = sc.nextLine();

							List<BookResponseDto> response3 = bookService.findAllByTitle(containigTitle);

							response3.forEach((book) -> {
								System.out.println("__________#___________");
								System.out.println("+ Nome: " + book.title());
								System.out.println("+ Idiomas disponiveis" + book.languages());
								System.out.println("+ Autor" + book.author_name());
								System.out.println("+ Ano de Nascimento" + book.author_birth_year());
								System.out.println("+ Ano de morte" + book.author_death_year());
								System.out.println("+ Nº de downloads" + book.download_count());
							});

							break;

						case 4:
							List<BookResponseDto> response4 = bookService.findAll();

							if (response4.isEmpty()) {
								System.out.println("Nehum livro cadastrado!");
							} else {
								response4.forEach((book) -> {
									System.out.println("__________#___________");
									System.out.println("+ Nome: " + book.title());
									System.out.println("+ Idiomas disponiveis" + book.languages());
									System.out.println("+ Autor" + book.author_name());
									System.out.println("+ Ano de Nascimento" + book.author_birth_year());
									System.out.println("+ Ano de morte" + book.author_death_year());
									System.out.println("+ Nº de downloads" + book.download_count());
								});
							}

							break;

						case 5:
							System.out.print("Nome do autor: ");
							authorName = sc.nextLine();

							var response5 = bookService.findByAuthorName(authorName);

							if (response5.isEmpty()) {
								System.out.println("Nenhum autor encontrado, com o nome fornecido. Tente novamente");
							} else {
								response5.forEach((book) -> {
									System.out.println("__________#___________");
									System.out.println("+ Nome: " + book.title());
									System.out.println("+ Idiomas disponiveis" + book.languages());
									System.out.println("+ Autor" + book.author_name());
									System.out.println("+ Ano de Nascimento" + book.author_birth_year());
									System.out.println("+ Ano de morte" + book.author_death_year());
									System.out.println("+ Nº de downloads" + book.download_count());
								});
							}

							break;
						case 6:
							System.out.print("Idioma: ");
							language = sc.nextLine();

							var response6 = bookService.findAllByLanguages(language);

							if (response6.isEmpty()) {
								System.out.println("Nenhum livro encontrado, com o idioma fornecido. Tente novamente");
							} else {
								response6.forEach((book) -> {
									System.out.println("__________#___________");
									System.out.println("+ Nome: " + book.title());
									System.out.println("+ Idiomas disponiveis" + book.languages());
									System.out.println("+ Autor" + book.author_name());
									System.out.println("+ Ano de Nascimento" + book.author_birth_year());
									System.out.println("+ Ano de morte" + book.author_death_year());
									System.out.println("+ Nº de downloads" + book.download_count());
								});
							}
							break;

						case 7:
							System.out.print("Ano de nascimento: ");
							birth_year = sc.nextInt();
							System.out.print("Ano de morte: ");
							death_year = sc.nextInt();

							var response7 = bookService.findAllByBirthYear_DeathYear(birth_year, death_year);

							if (response7.isEmpty()) {
								System.out.println(
										"Nenhum livro encontrado, com os anos de nascimento e morte fornecidos. Tente novamente");
							} else {
								response7.forEach((book) -> {
									System.out.println("__________#___________");
									System.out.println("+ Nome: " + book.title());
									System.out.println("+ Idiomas disponiveis" + book.languages());
									System.out.println("+ Autor" + book.author_name());
									System.out.println("+ Ano de Nascimento" + book.author_birth_year());
									System.out.println("+ Ano de morte" + book.author_death_year());
									System.out.println("+ Nº de downloads" + book.download_count());
								});
							}
						default:
							break;
					}
				} catch (InputMismatchException e) {
					System.out.println("=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#");
					System.out.println("Valor inserido no formato invalido! Verifique e tente novamente.");
					System.out.println("=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#");
					sc.nextLine();
					continue;

				}

			}
		}

		System.out.println("PROGRAMA FINALIZADO");

	}

}
