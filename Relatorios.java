import java.util.Scanner;

public class Relatorios {
    public GerenteDeVendas gerVendas;

    @FunctionalInterface
    public interface Interface {
        public void relatorio();    
    }
    public Relatorios(GerenteDeVendas gerVendas){
        this.gerVendas=gerVendas;
    }

    public void menuRelatorios(){
        int menu= 0;
        boolean active=true;
        while (active){
            Scanner entrada = new Scanner(System.in);
            System.out.println("\n>>>>>>>>>>> Menu Relatorios <<<<<<<<<<<\n");
            System.out.println("Por favor escolha uma das opcoes: ");
            System.out.println("1 -> Faturamento Atual");
            System.out.println("2 -> Ticket médio de vendas");
            System.out.println("3 -> Produtos mais vendidos");
            System.out.println("4 -> Listar vendas canceladas");
            System.out.println("5 -> Sair do menu de realatorios");
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
                    break;
                case 2:
                    break;
                case 3:
                        break;
                case 4:
                    gerVendas.percorreLista((x,venda) -> {
                        if(venda.getCancelada()){
                            System.out.println("Venda Cancelada!");
                            System.out.println("\n/////////////////////////////////////////");
                            System.out.println("Recibo de Venda numero:" + x);
                            System.out.println(venda.toString());
                            System.out.println("/////////////////////////////////////////");
                            }
                        });
                    break;
                case 5:
                    System.out.println("\nSaindo do menu relatorios");
                    active=false;
                    break;
                default:
                    System.out.println("\nDigite SOMENTE números entre 1 e 5\n");
                    break;


            }
            }
    }
}
