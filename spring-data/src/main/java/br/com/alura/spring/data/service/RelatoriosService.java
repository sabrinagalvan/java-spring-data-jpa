package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final FuncionarioRepository funcionarioRepository;
	
	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
	
	public void inicial(Scanner scanner) {
		while(system) {
			System.out.println("Qual ação de cargo você deseja executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Qual o nome do funcionário?");
			System.out.println("2 - Qual nome, data da contratação e salário?");
			System.out.println("3 - Qual a data da contratação?");
			System.out.println("4 - Pesquisar funcionário pelo salário");
			
			int action = scanner.nextInt();
			
			switch (action) {
			case 1:
				buscaFuncionarioNome(scanner);
				break;
			case 2:
				buscarFuncionarioNomeSalarioMaiorData(scanner);
				break;
			case 3:
				buscarFuncionarioDataContratacao(scanner);
				break;
			case 4:
				pesquisaFuncionarioSalario();
				break;
			default:
				system = false;
				break;
			}
			
		}
		
	}
	
	private void buscaFuncionarioNome(Scanner scanner) {
		System.out.println("Qual o nome do funcionário?");
		String nome = scanner.next();
		List<Funcionario> list = funcionarioRepository.findByNome(nome);
		list.forEach(System.out::println);
		
	}
	
	private void buscarFuncionarioNomeSalarioMaiorData(Scanner scanner) {
		System.out.println("Qual o nome do funcionário?");
		String nome = scanner.next();
		
		System.out.println("Qual data de contratação?");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		System.out.println("Qual o salário?");
		Double salario = scanner.nextDouble();
		
		List<Funcionario> list = funcionarioRepository
				.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
		list.forEach(System.out::println);
	}
	
	private void buscarFuncionarioDataContratacao(Scanner scanner) {
		System.out.println("Qual data de contratação?");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		List<Funcionario> list = funcionarioRepository.findDataContratacaoMaior(localDate);
		list.forEach(System.out::println);
		
	}
	
	private void pesquisaFuncionarioSalario() {
		List<FuncionarioProjecao> list = funcionarioRepository.findFuncionarioSalario();
		list.forEach(f -> System.out.println("Funcionário: id: " + f.getId()
		+ " | nome: " + f.getNome() + " | salario: " + f.getSalario()));
	}
	
}
