import java.util.InputMismatchException;
import java.util.Scanner;

public class ControleDeEstoque {
    Estoque estoque;
    public ControleDeEstoque(Estoque estoque){
        this.estoque=estoque;
    }
     public boolean disponvel(int codigo, int quantidade){
        return estoque.disponivel(codigo, quantidade);    
    }
    public void menuEstoque(){
        int ID=0;
        int menu= 0;
        boolean active=true;
        while (active){
            Scanner entrada = new Scanner(System.in);
            System.out.println("\n>>>>>>>>>>> Menu Controle de Estoque <<<<<<<<<<<\n");
            System.out.println("Por favor escolha uma das opcoes: ");
            System.out.println("1 -> Cadastrar novo produto");
            System.out.println("2 -> Listar todos os produtos");
            System.out.println("3 -> Repor produto em estoque");
            System.out.println("4 -> Para sair do menu de controle de estoque");
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
                    try{

                        System.out.print("\nInforme Codigo do produto:");
                        ID = entrada.nextInt();
                        System.out.print("\nInforme o nome do produto:");
                        String nome = entrada.next();
                        System.out.print("\nInforme o preco unitario do produto:");
                        Double aux = entrada.nextDouble();
                        System.out.print("\nInforme a quantidade inicial desse produto:");
                        int estoqueInicial = entrada.nextInt();
                        Produto produto = new Produto(ID, nome, aux);
                        estoque.cadastraProduto(produto, estoqueInicial);
                    }
                    catch(InputMismatchException e){
                        System.out.println("\nProduto nao foi adicionado!!");
                        System.out.println("\nForneca entradas validas\nCodigo ->int \nnome -> String\nPreco unitario ->Double\nQuantidade inicial -> int\n");
                        break;
                    }
                    break;
                    
                case 2:
                   System.out.println("\n"+estoque.toString());
                    break;
                case 3:
                try{
                    System.out.print("informe o codigo do produto:");
                    ID = entrada.nextInt();
                    System.out.print("informe a quantidade que deseja aumentar:");
                    int quantidade = entrada.nextInt();
                    estoque.addQuantidade(ID, quantidade);
                }catch(InputMismatchException e){
                        System.out.println("Digite Somente numeros!");
                    }
                        break;
                case 4:
                    System.out.println("\nSaindo do menu de controle de estoque\n");
                    active=false; 
                    break;
                default:
                    System.out.println("\nDigite SOMENTE números entre 1 e 4\n");
                    break;


            }
            }
    }

}