public class ItemDeVenda {
    private Produto produto;
    private int quantidade;
    private double vendas;

    ItemDeVenda(Produto produto, int quantidade) {
        this.produto=produto;
        this.quantidade=quantidade;
    }
    public boolean mesmoProduto(Produto p){
        if(this.getProduto()==p){
            return true;
        }
        return false;
    }

    public int getQuntidade() {return quantidade;}
    public void addQuantidade(int qtd){quantidade+=qtd;}
    public Produto getProduto() {return produto;}
    public double getValorVendas(){ 
        vendas=produto.getPrecoUnitario()*quantidade;
        return vendas;
    }
    @Override
    public String toString(){
        String str = "";
        str = produto.toString() + " Quantidade = "+quantidade;
        return str; 
    }
}