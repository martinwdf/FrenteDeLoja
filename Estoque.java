import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Estoque {
    //private ArrayList<ItemDeEstoque> itens;
    private Set<ItemDeEstoque> itens;
    private RandomAccessFile objeto;
    private String[] sai;
    private int cont;
    private final File arquivoLeitura = new File("ItensDeEstoque.txt");
    //trocar para hashMap
    public Estoque() throws IOException {
        itens = new HashSet<ItemDeEstoque>();
        cadastraItensArquivo(criarVetor());
    }
    
    // retorna a quantidade de produtos diferentes no estoque
    public int getQuantidadeProdutos(){
        return itens.size();
    }
    public boolean disponivel(int codigo, int quantidade){
        if(getQuantidadeEstoque(codigo)<quantidade){
            return false;
        }
        return true;

    }
    public Set<ItemDeEstoque> getItens(){
        return itens;
    }

    // cadastra um produto no estoque
    public boolean cadastraProduto(Produto produto, int estoqueInicial) throws IOException {
        ItemDeEstoque item = new ItemDeEstoque (produto, estoqueInicial);
        Produto p =existeProduto(produto.getCodigo());
        if(p==null){
            itens.add(item);
            return true;
        }
        System.out.println("\nProduto ja cadastrado!!\n");
        return false;
    }

    //encontra produto com o codigo, retorna null se nao acha
    public Produto existeProduto(int codigo){
        for(ItemDeEstoque it : itens ){
            if(it.getProduto().getCodigo()==codigo){
                return it.getProduto();
            }

        }
        return null;
    }
    // retorna a quantidade de itens de um produto em estoque
    public int getQuantidadeEstoque(int codigo){
        Produto existe = existeProduto(codigo);
        if(existe==null){
            return -1;
        }
        else{
            Iterator<ItemDeEstoque> iter = itens.iterator();
                while (iter.hasNext())
                {
                    ItemDeEstoque it= iter.next();
                    if(it.getProduto()==existe){
                        return it.getQuantidade();
                 }
                // Do something with name
                }
        }
        return 0;
    }
    public void remQuantidade(int codigo, int quantidade){
        Produto p = existeProduto(codigo);
        if(p==null){
            System.out.println("\nNao existe produto com esse codigo");

        }
        else{
            System.out.println(p.toString());
            Iterator<ItemDeEstoque> iter = itens.iterator();
            while (iter.hasNext())
            {
                ItemDeEstoque it= iter.next();
                if(it.getProduto()==p){
                    it.baixaEstoque(quantidade);
             }
            // Do something with name
            }
            
        }       
    }
    //adiciona um quantidade ao produto de codigo fornecido
    public boolean addQuantidade(int codigo, int quantidade){
        Produto p = existeProduto(codigo);
        if(p==null){
            System.out.println("\nNao existe produto com esse codigo");
            return false;
        }
        else{
            Iterator<ItemDeEstoque> iter = itens.iterator();
            while (iter.hasNext())
            {
                ItemDeEstoque it= iter.next();
                if(it.getProduto()==p){
                    it.reposicaoEstoque(quantidade);
                    return true;
             }
            // Do something with name
            }
            return false;
        }

    }
    @Override
    public String toString(){
        String str="";
        Iterator<ItemDeEstoque> iter = itens.iterator();
        while (iter.hasNext())
        {
          str +=  iter.next().toString() + "\n";
         }
         return str;
    }
    
    //cria um vetor de itens de estoque a partir de um arquivo
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
            File arq = new File("ItensDeEstoque.txt");
            
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

    public void cadastraItensArquivo(String[] s) throws NumberFormatException, IOException {
        String[] linha=s;

        for(int i=0;i<cont;i++){ 
            linha = s[i].split(";");
            Produto p = new Produto(Integer.parseInt(linha[0]), linha[1], Double.parseDouble(linha[2]));
            cadastraProduto(p, Integer.parseInt(linha[3]));
            }

    }

}
