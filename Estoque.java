import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Estoque {
    //private ArrayList<ItemDeEstoque> itens;
    private Set<ItemDeEstoque> itens;
    //trocar para hashMap
    public Estoque() {
        itens = new HashSet<ItemDeEstoque>();
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

    // cadastra um produto no estoque
    public boolean cadastraProduto(Produto produto, int estoqueInicial){
        ItemDeEstoque item = new ItemDeEstoque (produto, estoqueInicial);
        if(existeProduto(produto.getCodigo())==null){
            itens.add(item);
           //System.out.println(iter.toString());
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
    
}
