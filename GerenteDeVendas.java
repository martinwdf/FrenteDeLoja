import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class GerenteDeVendas {
    private Map<Integer,Venda> vendas;
    int numeroVenda;
    Estoque estoque;
    public GerenteDeVendas(Estoque estoque) {
        vendas = new LinkedHashMap<Integer, Venda>();
        this.estoque=estoque;
        numeroVenda=-1;
     }
     public int getVendas(){return vendas.size();}
    public int numeroVenda(){return numeroVenda;}
    public void addNumVenda(){numeroVenda++;}



    public void menuVendas(){
        Scanner entrada = new Scanner(System.in);
        int ID=0;
        int menu= 0;
        boolean active=true;
        while (active){
            
            System.out.println("\n>>>>>>>>>>> Menu Gerente de Vendas <<<<<<<<<<<\n");
            System.out.println("Por favor escolha uma das opcoes: ");
            System.out.println("1 -> Realizar venda");
            System.out.println("2 -> Cancelar venda");
            System.out.println("3 -> Listar vendas");
            System.out.println("4 -> Imprimir segunda via recibo");
            System.out.println("5 -> Para sair do menu do gerente de vendas");
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
                    Venda venda = new Venda();
                    addNumVenda();
                    vendas.put(numeroVenda, venda);
                    realizarVenda(venda);
                    break;

                case 2:
                    try{

                        System.out.print("\nInforme o numero da venda: ");
                        ID = entrada.nextInt();
                        cancelaVenda(ID);
                    }catch(InputMismatchException e){
                        break;
                    } 
                    break;
                case 3:
                    this.ultimas5();
                    break;
                case 4: 
                    try{

                        System.out.print("\nInforme o numero da venda: ");
                        ID = entrada.nextInt();
                        imprimeRecibo(ID);
                    }catch(InputMismatchException e){
                        break;
                    } 
                    break;
                case 5:
                    System.out.println("\nSaindo do menu de vendas!\n");
                   // entrada1.close();
                    active=false; 
                    break;
                default:
                    System.out.println("\nDigite SOMENTE números entre 1 e 5\n");
                    break;


            }
            }


    }
    //imprime o recibo de uma venda
    public void imprimeRecibo(int key){
        System.out.println("\n/////////////////////////////////////////");
        Venda v = vendas.get(key);
        if(v==null){
            System.out.println("\nNao existe venda com esse numero!!");
        }
        else{
            System.out.print("Recibo de Venda numero: " + key);
            if(v.getCancelada()){
                System.out.println(" Venda Cancelada");    
            }
            else{
                System.out.println();
            }
            System.out.println("\nItens de Venda:\n"+ v.toString());
            System.out.println("Total: " + v.getValorVenda());
            System.out.println("Deconto: " + v.getDesconto());
            System.out.println("Imposto: " +v.aplicaImposto());
            System.out.println("Valor Venda: " +(v.getValorVenda() - v.getDesconto())*v.getImposto());
        }
        System.out.println("/////////////////////////////////////////");

    }
    //Imprime o recibo das ultimas 5 vendas
    public String ultimas5(){
        Set<Integer> keySet =vendas.keySet();
        String str="";
        int i=0;
        for(int key : keySet){
            imprimeRecibo(key);
            if(i==5){
                return str;
            }
            i++;

        }
        return str;
    }
    //funcao que cancela um venda com uma chave
    public void cancelaVenda(int key){
        Venda v = vendas.get(key);
        if(v==null){
            System.out.println("\nNao existe venda com esse numero!!");
        }
        else{
            System.out.println("\nVenda Cancelada!!");
            v.setCancelada();
            ItemDeVenda[] item = v.toArray(); 
            int i=0;
            while(i<v.getQtdItens()){
                estoque.addQuantidade(item[i].getProduto().getCodigo(), item[i].getQuntidade());
                i++;
            }
        }
    }

    //funcao generica para funcoes lambdas fazerem as operacoes
    public void percorreLista(OperacaoRelatorio oper){
        Set<Integer> keySet =vendas.keySet();
        for(int key : keySet){
            Venda venda = vendas.get(key);
            oper.operacaoRelatorio(key, venda);
        }
    }

    //operacoes de realizar venda
    public void realizarVenda(Venda venda){
        Scanner entrada = new Scanner(System.in);
        int ID=0;
        int menu= 0;
        boolean active=true;
        while (active){
    
            System.out.println("\n>>>>>>>>>>> Menu Realizar Vendas <<<<<<<<<<<\n");
            System.out.println("Por favor escolha uma das opcoes: ");
            System.out.println("1 -> Adicionar item de venda");
            System.out.println("2 -> Remover item de venda");
            System.out.println("3 -> Aplicar desconto");
            System.out.println("4 -> Finalizar Venda");
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
                        System.out.print("\nInforme a quantidade desse produto:");
                        int quantidade = entrada.nextInt();
                        if(venda.getItemDeVenda(ID)!=null){
                            System.out.println("Produto ja estava na venda quantidade adicionada!! ");
                            estoque.remQuantidade(ID, quantidade);
                            venda.insereRepetido(ID, quantidade);
                        }
                        else if(estoque.getQuantidadeEstoque(ID)==-1){
                            System.out.println("\nNao existe produto com esse codigo!!\n");
                        }
                        else if(estoque.getQuantidadeEstoque(ID)<quantidade){
                            System.out.println("\nNao tem produtos suficientes!!\n");
                        }
                        else{
                            estoque.remQuantidade(ID, quantidade);
                            venda.insereItem(estoque.existeProduto(ID), quantidade);
                            System.out.println("Item de Venda adiconado");
                        }
                        
                    }
                    catch(InputMismatchException e){
                        System.out.println("Produto nao foi adicionado!!");
                        System.out.println("\nForneca entradas validas\nCodigo ->int \nnome -> String\nPreco unitario ->Double\nQuantidade inicial -> int\n");
                        break;
                    } 
                    break;

                case 2:
                try{

                    System.out.print("\nInforme Codigo do produto que deseja remover:");
                    ID = entrada.nextInt();
                    if(venda.removeItemDeVenda(ID)){
                        System.out.println("\nItemDeVenda Removido!!\n");
                    }
                    else{
                        System.out.println("\n Nao existe um produto com esse codigo!!\n");
                    }

                }catch(InputMismatchException e){
                    System.out.println("Produto nao foi adicionado!!");
                    System.out.println("\nForneca entradas validas\nCodigo ->int \nnome -> String\nPreco unitario ->Double\nQuantidade inicial -> int\n");
                    break;
                }
                    break;
                case 3:
                    if(venda.getValorVenda()<250){
                        System.out.println("descontos so podem ser aplicados para vendas com valores maiores de R$ 250.00");
                        System.out.println("Valor atual: " + venda.getValorVenda());
                    }
                    else{
                        System.out.print("\nInforme quantos por cento de desconto deseja aplicar:");
                        ID = entrada.nextInt();
                        double desconto = venda.aplicaDesconto(ID);
                    }
                    break;
                case 4:
                    System.out.println("\nSaindo do menu de vendas!\n");
                    imprimeRecibo(numeroVenda);
                    active=false; 
                    break;
                default:
                    System.out.println("\nDigite SOMENTE números entre 1 e 5\n");
                    break;


            }
            }
    }
}