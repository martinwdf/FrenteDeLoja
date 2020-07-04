public class ItemDeEstoque {
    private Produto produto;
    private int quantidade;
 
    ItemDeEstoque(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto(){
        return produto;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public boolean baixaEstoque(int quantidade) {
        if (this.quantidade < quantidade) {
            return false;
        }
        this.quantidade -= quantidade;
        return true;
    }

    public void reposicaoEstoque(int quantidade){
        this.quantidade += quantidade;
    }
    @Override
    public String toString(){
        String str="";
        str = produto.toString() + "Quantidade no estoque:" +String.valueOf(quantidade);
        return str;
    
    }
}