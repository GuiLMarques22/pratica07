import java.sql.*;
import java.util.Scanner;

public class Principal {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public void conectar() {

        String url = "jdbc:postgresql://localhost/BDlivrariaUniversitaria";
        String username = "postgres";
        String password = "123456";

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void desconectar() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarLivro(String titulo, double preco) {
        conectar();

        String query = "INSERT INTO Livros (titulo, vl_preco) VALUES (?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, titulo);
            preparedStatement.setDouble(2, preco);
            preparedStatement.executeUpdate();

            System.out.println("Livro cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            desconectar();
        }
    }

    public void excluirLivro(int id) {
        conectar();

        String query = "DELETE FROM Livros WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Livro excluído com sucesso!");
            } else {
                System.out.println("Nenhum livro encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            desconectar();
        }
    }

    public void buscarLivroPorTitulo(String texto) {
        conectar();

        String query = "SELECT * FROM Livros WHERE titulo LIKE ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, texto + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                double preco = resultSet.getDouble("vl_preco");

                System.out.println("ID: " + id + ", Título: " + titulo + ", Preço: " + preco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            desconectar();
        }
    }

    public void buscarLivroPorPreco(double valor) {
        conectar();

        String query = "SELECT * FROM Livros WHERE vl_preco >= ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, valor);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String preco = null;
                String titulo = resultSet.getString("titulo");
                System.out.println("ID: " + id + ", Título: " + titulo + ", Preço: " + preco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            desconectar();
        }
    }

    public static void main(String[] args) {
        Principal livraria = new Principal();
        Scanner scanner = new Scanner(System.in);

        int opcao = 0;

        while (opcao != 5) {
            System.out.println("<1> Cadastrar Livro");
            System.out.println("<2> Pesquisar Livro por Preço");
            System.out.println("<3> Pesquisar Livro por Título");
            System.out.println("<4> Excluir Livro");
            System.out.println("<5> Sair");

            System.out.print("Digite a opção desejada: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o título do livro: ");
                    String titulo = scanner.next();
                    System.out.print("Digite o preço do livro: ");
                    double preco = scanner.nextDouble();

                    livraria.cadastrarLivro(titulo, preco);
                    break;
                case 2:
                    System.out.print("Digite o valor mínimo do preço: ");
                    double valorMinimo = scanner.nextDouble();

                    livraria.buscarLivroPorPreco(valorMinimo);
                    break;
                case 3:
                    System.out.print("Digite o início do título do livro: ");
                    String inicioTitulo = scanner.next();

                    livraria.buscarLivroPorTitulo(inicioTitulo);
                    break;
                case 4:
                    System.out.print("Digite o ID do livro a ser excluído: ");
                    int idLivro = scanner.nextInt();

                    livraria.excluirLivro(idLivro);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }

            System.out.println();
        }

        scanner.close();
    }
}
