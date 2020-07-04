///////////////////////////////////
// Aluno: Martin Ferreira
// Cadeira: Programacao orientada a objetos
// Data: 05/07/2020
//
///////////////////////////////////
import java.util.Scanner;
public class App {
    public static void menu(ControleDeEstoque ctrlEstoque, GerenteDeVendas gerVendas, Relatorios relatorios){
        Scanner entrada = new Scanner(System.in);
        int menu= 0;
        while (true){
            System.out.println("\n>>>>>>>>>>> Menu Principal <<<<<<<<<<<\n");
            System.out.println("Por favor escolha uma das opcoes: ");
            System.out.println("1 -> Para visualizar as operacoes com as vendas");
            System.out.println("2 -> Para visualizar as operacoes com o estoque");
            System.out.println("3 -> Para visualizar os relatorios disponiveis");
            System.out.println("4 -> Para encerrar o programa");
            System.out.print("Escolha um item do menu:");
            boolean active2 =true;
            do{
                 if(entrada.hasNextInt()){
                     menu = entrada.nextInt();
                     active2 = false;
                 }else{
                     entrada.nextLine();
                     System.out.println("\nDigite SOMENTE números\n");
                 }
            }while(active2==true);
             switch(menu){

                case 1:
                    gerVendas.menuVendas();
                    break;
                
                case 2:
                    ctrlEstoque.menuEstoque();
                    break;
                case 3:
                    relatorios.menuRelatorios();
                    break;
                case 4:
                    System.out.println("\nPrograma encerrado!\n");
                    entrada.close();
                    System.exit(0);
                default:
                    System.out.println("\nDigite SOMENTE números entre 1 e 4\n");
                    break;


            }
            }
    }
    public static void main(String args[]) {
        
        // Instancia o controle de estoque
        Estoque e = new Estoque();
        // Cadastra um produto
        e.cadastraProduto(new Produto(10, "iPhone", 4000.0), 100);
        e.cadastraProduto(new Produto(2, "Fone de ouvido", 100),  100);
        e.cadastraProduto(new Produto(5, "Teclado", 250),  100);
        GerenteDeVendas gerVendas = new GerenteDeVendas(e);
        ControleDeEstoque ctrlEstoque = new ControleDeEstoque(e);
        Relatorios relatorios = new Relatorios(gerVendas);
        menu(ctrlEstoque,gerVendas, relatorios);
        
        // Obtem um produto
/*        Produto p = e.getProduto(10);1

        Produto p1 = e.getProduto(2);
        Produto p2 = e.getProduto(5);
        System.out.println(p);
        System.out.println(p1);
        System.out.println(p2);
        
        // Imprime quantidade do produto em estoque
        System.out.println("Quantidade de produdos: " + e.getQuantidadeProdutos());
        
        // Imprime quantidade de itens de um dado produto em estoque
        System.out.println("Quantidade de itens em estoque: " + e.getQuantidadeEstoque(10));
        
        // Cria historico de vendas        
        //Cria uma venda
        Venda v = new Venda();
        Venda v1 = new Venda( );
        Venda v2 = new Venda();
        
        //Insere item de venda em uma venda
        v.insereItem(p, 2);
        v.insereItem(p1, 1);
        v.insereItem(p2, 2);
        v1.insereItem(p, 5);
        v1.insereItem(p1, 5);
        v2.insereItem(p, 1);
       


        System.out.println("Retorna o subtotal da venda 1: " + v.getSubtotal());
        System.out.println("Retorna o subtotal da venda 2: " + v1.getSubtotal());
        System.out.println("Retorna o subtotal da venda 3: " + v2.getSubtotal());
        System.out.println(v2.getQtdItens());
        //Cadastra uma venda
    */
    }
}