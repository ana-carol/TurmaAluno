import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestJUnitTurmaAluno {

	public static final String DRIVER = "org.postgresql.Driver";
	public static final String URL = "jdbc:postgresql://localhost/turmaAluno";
	public static final String DB_USER = "postgres";
	public static final String DB_PASS = "meucavaloehshow";
	
	protected static Connection connection;

	@BeforeClass
	public static void carregarDriverAndConnect() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		connection = java.sql.DriverManager.getConnection(URL, DB_USER, DB_PASS);
	}
	
	@Test
	public void a_createTurma() throws SQLException{
		String query = "insert into Turmas (id, nome) values(?,?)";
		PreparedStatement st = connection.prepareStatement(query);
		st.setInt(1, 1);
		st.setString(2, "Introdução a programação");
		st.executeUpdate();
	}
	
	@Test
	public void b_updateTurma() throws SQLException{
		String query = "update Turmas set nome = ? where(id = ?)";
		PreparedStatement st = connection.prepareStatement(query);
		st.setString(1, "Linguagem de Programação");
		st.setInt(2, 1);
		st.executeUpdate();
	}
	
	@Test
	public void d_selectTurma() throws SQLException{
		int idTurma = 1;
		String query = "select nome, id from turmas where(id = ?)";
		PreparedStatement st = connection.prepareStatement(query);
		st.setInt(1, idTurma);
		ResultSet result =  st.executeQuery();
		if(result.next()){
			Turma turma = new Turma(result.getString("nome"), result.getInt("id"), c_getAllAlunosDaTurma(idTurma));
			System.out.println(turma.getNome());
		}
	}
	
	@Test
	public void e_selectAllTurmas() throws SQLException{
		String query = "select nome, id from Turmas";
		PreparedStatement st = connection.prepareStatement(query);
		ResultSet result =  st.executeQuery();
		while(result.next()){
			Turma turma = new Turma(result.getString("nome"), result.getInt("id"), c_getAllAlunosDaTurma(result.getInt("id")));
			System.out.println(turma.getNome());
		}
	}
	
	@Test
	public void f_createAluno() throws SQLException{
		String query = "insert into Alunos(email, nome, sobrenome, sexo, datanascimento, turma) values(?,?,?,?,?,?)";
		PreparedStatement st = connection.prepareStatement(query);
		st.setString(1, "juciano@gmail.com");
		st.setString(2, "Juciano");
		st.setString(3, "Romão");
		st.setString(4, "M");
		st.setString(5, "17/09/2017");
		st.setInt(6, 1);
		st.executeUpdate();
	}
	
	@Test
	public void g_updateAluno() throws SQLException{
		String query = "update Alunos set nome = ?, sobrenome = ?, sexo = ?, dataNascimento = ?, turma = ? "
				+ "where(email = ?)";
		PreparedStatement st = connection.prepareStatement(query);
		st.setString(1, "Juciano");
		st.setString(2, "Romão da Silva");
		st.setString(3, "M");
		st.setString(4, "17/10/2017");
		st.setInt(5, 1);
		st.setString(6, "juciano@gmail.com");
		st.executeUpdate();
	}
	
	@Test
	public void h_selectAluno() throws SQLException{
		String query = "select nome, sobrenome, sexo, dataNascimento, email, turma from Alunos where(email = ?)";
		PreparedStatement st = connection.prepareStatement(query);
		st.setString(1, "juciano@gmail.com");
		ResultSet result =  st.executeQuery();
		if(result.next()){
			Aluno aluno = new Aluno(result.getString("nome"), result.getString("sobrenome"), result.getString("sexo"), 
					result.getString("dataNascimento"), result.getString("email"), result.getInt("turma"));
			System.out.println(aluno.getNome());
		}
	}
	
	@Test
	public void i_selectAllAlunos() throws SQLException{
		String query = "select nome, sobrenome, sexo, dataNascimento, email, turma from Alunos";
		PreparedStatement st = connection.prepareStatement(query);
		ResultSet result =  st.executeQuery();
		while(result.next()){
			Aluno aluno = new Aluno(result.getString("nome"), result.getString("sobrenome"), result.getString("sexo"), 
					result.getString("datanascimento"), result.getString("email"), result.getInt("turma"));
			System.out.println(aluno.getNome());
		}
	}
	
	@Test
	public void j_deleteAluno() throws SQLException{
		String query = "delete from Alunos where(email=?)";
		PreparedStatement st = connection.prepareStatement(query);
		st.setString(1, "juciano@gmail.com");
		st.executeUpdate();
	}	
	
	@Test
	public void l_deleteAllAlunos() throws SQLException{
		String query = "delete from Alunos where(turma=?)";
		PreparedStatement st = connection.prepareStatement(query);
		st.setInt(1, 1);
		st.executeUpdate();
	}
	
	@Test
	public void m_deleteTurma() throws SQLException{
		String query = "delete from Turmas where(id=?)";
		PreparedStatement st = connection.prepareStatement(query);
		st.setInt(1, 1);
		st.executeUpdate();
	}
	
	private List<Aluno> c_getAllAlunosDaTurma(int idTurma) throws SQLException{
		String query = "select nome, sobrenome, sexo, dataNascimento, email from Alunos where turma = ?";
		PreparedStatement st = connection.prepareStatement(query);
		st.setInt(1, idTurma);
		ResultSet result =  st.executeQuery();
		List<Aluno> alunos = new ArrayList<Aluno>();
		while(result.next()){
			Aluno aluno = new Aluno(result.getString("nome"), result.getString("sobrenome"), result.getString("sexo"), 
					result.getString("dataNascimento"), result.getString("email"), result.getInt("turma"));
			alunos.add(aluno);
		}
		return alunos;
	}
		
}
