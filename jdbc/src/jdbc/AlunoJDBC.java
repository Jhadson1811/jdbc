package jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Aluno;

public class AlunoJDBC {
	
	String sql;
	PreparedStatement pst;
	
	
	public void salvar(Aluno a, Connection con) throws IOException {
		
		try {
			
			sql = "INSERT INTO aluno (nome, sexo, dt_nasc) VALUES (?,  ?, ?)";
			
			pst = con.prepareStatement(sql);
			pst.setString(1, a.getNome());
			pst.setString(2, a.getSexo());
			
			Date dataSql = new Date(a.getDt_nasc().getTime());
			pst.setDate(3, dataSql);
			
			pst.executeUpdate();
			System.out.println("\nCadastro do aluno realizado com sucesso!");
			
		}
		catch (SQLException e) {
			
			System.out.println(e);
		}
		
	}
	
	public List<Aluno> listar() throws IOException, SQLException {
		
		List<Aluno> listaAlunos = new ArrayList<>();
		
		try {
			sql = "SELECT * FROM aluno";
			Connection con = DB.getConexao();
			pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String sexo = rs.getString("sexo");
                Date dt_nasc = rs.getDate("dt_nasc");
                
                Aluno aluno = new Aluno(id, nome, sexo, dt_nasc);
                listaAlunos.add(aluno);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
			return listaAlunos;
	}
	
	public void alterar(Aluno a) {
		
		try {
			sql = "UPDATE aluno SET nome = ?, sexo = ?, dt_nasc = ? WHERE id = ?";
			Connection con = DB.getConexao();
			pst = con.prepareStatement(sql);
			
			
			pst.setString(1, a.getNome());
			pst.setString(2, a.getSexo());
			
			Date dataSql = new Date(a.getDt_nasc().getTime());
			pst.setDate(3, dataSql);
			pst.setInt(4, a.getId());
			
			pst.executeUpdate();	
			System.out.println("\nCadastro do aluno alterado com sucesso!");
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void apagar(int id) {
		
		try {
			sql = "DELETE FROM aluno WHERE ID = ?";
			Connection con = DB.getConexao();
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, id);
			
			pst.executeUpdate();
			System.out.println("\nO aluno foi excluido com sucesso!");
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
}
