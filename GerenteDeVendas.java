import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class GerenteDeVendas {
    private Map<Integer,Venda> vendas;
    int numeroVenda;
    Estoque estoque;
    private RandomAccessFile objeto;
    private String[] sai;
    private int cont;
    private final File arquivoLeitura = new File("Vendas.txt");
    private ControleDeEstoque ctrl;

    public GerenteDeVendas(Estoque estoque, ControleDeEstoque ctrl) throws NumberFormatException, IOException {
        vendas = new LinkedHashMap<Integer, Venda>();
        this.estoque=estoque;
        numeroVenda=-1;
        this.ctrl=ctrl;
        cadastraVendaArquivo(criarVetor());
     }
     //getters
     public Map<Integer,Venda> getVendasMap(){return vendas;}
     public int getVendas(){return vendas.size();}
    public int numeroVenda(){return numeroVenda;}
    public void addNumVenda(){numeroVenda++;}

    ///cria o vetor do arquivo de vendas
    public String[] criarVetor() throws IOException {
        cont = 0;
        long tamanhoArquivo = arquivoLeitura .length();
        FileInputStream fs = new FileInputStream(arquivoLeitura);
        DataInputStream in = new DataInputStream(fs); 
        LineNumberReader lineRead = new LineNumberReader(new InputStreamReader(in));
        lineRead.skip(tamanhoArquivo);
        // conta o numero de linhas do arquivo, começa com zero, por isso adiciona 1
        cont= lineRead.getLineNumber() + 1;
        try {
           File arq = new File("Vendas.txt");
           
           objeto = new RandomAccessFile(arq, "rw");
           sai = new String[(int) objeto.length()]; // inicializa o vetor com o tamanho do arquivo
           for (int i = 0; i < objeto.length(); i++) {
               sai[i] = objeto.readLine();
               }
           return sai;
        } catch (FileNotFoundException ex) { // trata as exceções do tipo FileNotFoundException
           ex.printStackTrace();
        } catch (IOException ex) { // trata as exceções do tipo IOException
           ex.printStackTrace();
        }
        return null; // só retorna null se der algum erro
   }

   //cadastra as vendas do arquivo
   public void cadastraVendaArquivo(String[] s) throws NumberFormatException, IOException {
        String[] linha=s;
         for(int i=0;i<cont;i++){ 
            linha = s[i].split(";");
            if(linha[0].equals("venda")){
                i++;
                addNumVenda();
                Venda venda=new Venda();
                vendas.put(numeroVenda, venda);
                if(linha[1].equals("cancelada")){
                    venda.setCancelada();       
                }
            do{ 
                linha = s[i].split(";");
                if(linha[0].equals("venda")){
                    i--;
                    break;
                }
                    venda.insereItem(estoque.existeProduto(Integer.parseInt(linha[0])), Integer.parseInt(linha[1]));
                i++;
            }while(i<cont);     

            }
        }

}
    //funcao que atualiza o estado do arquivo
    public void atualizaArquivoVendas() throws IOException {
        File arquivo = new File("Vendas.txt");
        FileWriter fw=new FileWriter(arquivo, false);
        BufferedWriter bw;
        bw= new BufferedWriter(fw);
        String s="";
        Set<Integer> keySet =vendas.keySet();
        for(int key : keySet){
            Venda venda = vendas.get(key);
            if(venda.getCancelada()){
                s+="venda;cancelada;Recibo: "+key+"\n";
            }
            else{
                s+="venda;aprovada;Recibo: "+key+"\n";
            }
            for(ItemDeVenda it: venda.getItens()){
                s+= it.getProduto().getCodigo()+";"+it.getQuntidade()+"\n";
            }           
        }
        s = s.substring (0, s.length() - 1);
        bw.write(s);
        bw.close();
    }
    public void menuVendas() throws IOException {
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
            this.atualizaArquivoVendas();
            this.ctrl.atualizaArquivoEstoque();
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
    public void percorreLista(OperacaoRelatorio oper) throws CloneNotSupportedException {
        Set<Integer> keySet =vendas.keySet();
        for(int key : keySet){
            Venda venda = vendas.get(key);
            Venda aux =(Venda)venda.clone();
            oper.operacaoRelatorio(key, aux);
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