import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        InsertRecordExample inserir = new InsertRecordExample();
        UpdateRecordExample update = new UpdateRecordExample();
        DeleteRecordExample delete = new DeleteRecordExample();
        JOptionPane.showInputDialog("1. Cadastrar livro \n 2. Pesquisar Livro por preço \n 3. Pesquisar Livro por Titulo \n 4.Excluir livro \n 5.Sair " );
    }
}
