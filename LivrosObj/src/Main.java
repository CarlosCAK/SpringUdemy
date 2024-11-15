public class Main {
    public static void main(String[] args) {


        Livro l1 = new Livro();

        l1.setAutor("carlos");
        l1.setTitulo("a volta dos que nao foram");
        l1.setEditora("Pascal");
        l1.setIsbn("naoseiqporraesssa");

        System.out.println(l1.getAutor());


    }
}