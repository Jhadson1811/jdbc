package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import entities.Aluno;
import jdbc.AlunoJDBC;
import jdbc.DB;

public class Programa {

	public static void main(String[] args) throws IOException, SQLException {

		Connection con = DB.getConexao();
		System.out.println("Conexão realizada com sucesso !");
		Aluno a = new Aluno();
		AlunoJDBC acao = new AlunoJDBC();
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
		
		Scanner console = new Scanner(System.in);
		
		int opcao = 0;
		
		try {

			do {
				System.out.print("######## Menu ########" + 
								"\n1- Cadastrar" + 
								"\n2- Listar"    + 
								"\n3- Alterar"   +
								"\n4- Excluir"   + 
								"\n5- Sair"      +
								"\n\tOpção: ");
				opcao = Integer.parseInt(console.nextLine());

				if (opcao == 1) {

					System.out.print("\n*** Cadastrar Aluno ***\n\r");
					System.out.print("Nome: ");
					a.setNome(console.nextLine());
					System.out.print("Sexo: ");
					a.setSexo(console.nextLine());
		
					System.out.print("Data de nascimento (dd/MM/yyyy): ");
					a.setDt_nasc( Date.valueOf( LocalDate.parse( console.nextLine(), formato) ) ) ;
					
					acao.salvar(a, con);
					console.nextLine();
				}
				
				if (opcao == 2) {
					List<Aluno> listaAlunos = acao.listar();
					
					System.out.print("\n*** Listar Alunos ***\n\r");
					for (Aluno aluno : listaAlunos) {
						System.out.println("Id: " + aluno.getId());
						System.out.println("Nome: " + aluno.getNome());
						System.out.println("Sexo: " + aluno.getSexo());
						System.out.println("DT Nasc: " + aluno.getDt_nasc());
						System.out.println("\n*** ------- ***\n");
					}
					
				}
				
				if (opcao == 3) {
					
					List<Aluno> listaAlunos = acao.listar();
					
					System.out.print("\n*** Alterar Aluno ***\n\r");
					System.out.print("\nDigite o ID do aluno: \n\r");
					int idAluno = Integer.parseInt(console.nextLine());
					
				
					for (Aluno aluno : listaAlunos) {
						if (aluno.getId() == idAluno) {
							a = aluno;
						}
					}
					
					System.out.print("Nome: ");
					a.setNome(console.nextLine());
					System.out.print("Sexo: ");
					a.setSexo(console.nextLine());
					
					System.out.print("Data de nascimento (dd/MM/yyyy): ");
					a.setDt_nasc( Date.valueOf( LocalDate.parse( console.nextLine(), formato) ) ) ;
					
					acao.alterar(a);
									
				}
				
				if (opcao == 4) {
					
					System.out.print("\n*** Excluir Aluno ***\n\r");
					System.out.print("\nDigite o ID do aluno: \n\r");
					int idAluno = Integer.parseInt(console.nextLine());
					
					List<Aluno> listaAlunos = acao.listar();
					a.setId(0); 
					
					for (Aluno aluno : listaAlunos) {
						if (aluno.getId() == idAluno) {
							a = aluno;
						}
					}
					
					
					if (a.getId() == 0) {
						System.out.println("Aluno não encontrado!");
					} else {
						acao.apagar(a.getId());
					}
					
					
					
				}
			} while (opcao != 5);

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
		
		DB.fechaConexao();
		System.out.println("Conexão fechada com sucesso !");
	}
}