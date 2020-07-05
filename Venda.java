import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Venda implements Cloneable{
    private double desconto = 0;
    private final double imposto=1.25;
    //private ArrayList<ItemDeVenda> itens;
    private Set<ItemDeVenda> itens;
    private boolean cancelada;

    //construtor
    Venda() {
        itens = new HashSet<ItemDeVenda>();
        cancelada=false;
    }
    //retorna o set se intens de venda
    public Set<ItemDeVenda> getItens(){
        return itens;
    }
    public void setItensDeVenda(Set<ItemDeVenda> it){
        this.itens=it;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        Venda v = new Venda();
        v.setItensDeVenda(itens);
        if(this.getCancelada()){
            v.setCancelada();
        }
        return (Venda)super.clone();
        // Como o Vini disse, é possível usar neste caso (em que se pode usar um "shallow cloning"
        // return super.clone(); que é mais fácil de usar.
    }
    //getters
    public double getImposto(){return imposto;}
    public double getDesconto(){return desconto;}
    public int getQtdItens(){return itens.size();}
    public boolean getCancelada(){return cancelada;}
    public void setCancelada(){cancelada=true;}
    //public int getQuantidadeItens(){return numero;}
    

    public double getSubtotal(){

        double valor=0;
        for(ItemDeVenda it : itens){
            valor += it.getValorVendas();
        }
        return valor;
    }
    //retorna um produto, ou null se nao eh um item de venda
    public Produto getItemDeVenda(int codigo){
        for(ItemDeVenda it: itens){
            if(it.getProduto().getCodigo()==codigo){
                return it.getProduto();
            }
        }
        return null;
    }

    //remove um item de venda
    public boolean removeItemDeVenda(int codigo){
        Iterator<ItemDeVenda> iter = itens.iterator();
        while (iter.hasNext())
        {
            ItemDeVenda it= iter.next();
            if(it.getProduto().getCodigo()==codigo){
                iter.remove();
                return true;
            }
        }
        return false;
    }
    //retorna o valor da venda sem imposto
    public double getValorVenda(){
        Iterator<ItemDeVenda> iter = itens.iterator();
        double valor =0;
        while (iter.hasNext())
        {
            ItemDeVenda it= iter.next();
            valor=it.getProduto().getPrecoUnitario()*it.getQuntidade();
            
        
        }
        return valor;
    }
    //quando item de venda eh repetido
    public void insereRepetido(int ID, int quantidade) {
        for(ItemDeVenda it : itens){
            if(it.getProduto().getCodigo()==ID){
               it.addQuantidade(quantidade);
            }
        }
    }
    //insere itens de venda
    public boolean insereItem(Produto produto, int quantidade){
        ItemDeVenda item;
        item = new ItemDeVenda(produto, quantidade);
        return itens.add(item);
    }
    // public ItemDeVenda getItem(int numero){
    //     return itens.get(numero);
    // }
     @Override 
     public String toString(){
        Iterator<ItemDeVenda> iter = itens.iterator();
        String str="";
        while (iter.hasNext())
        {
            ItemDeVenda it= iter.next();
            str+=it.toString()+ " Valor item: "+it.getValorVendas()+"\n";
            
        
        }
        return str;
     }
     //seta o valor de desconto
    public double aplicaDesconto(int d){
        double des=0;
        if(d<1){
            System.out.println("Desconto = 0");
            return des;
        }
        else if(d>10){
            System.out.println("\nDesconto maximo de 10%, desconto = 0\n");
            return des;
        }
        else{

            double i = d/100.0;
            des = getValorVenda()*(i);
            this.desconto = des;
            System.out.println("\nvalor do desconto: " + des+"\n");
            return des;
        }
    }
    public double valorFinal(){
        return (this.getValorVenda()-desconto)*getImposto();
    }
    //coloca o valor de desconto na venda
    public double aplicaImposto(){
        return (this.getValorVenda()-desconto) *0.25;
    }
    //pega um array de item de venda
    public ItemDeVenda[] toArray(){
       ItemDeVenda[] item = new ItemDeVenda[itens.size()];
       Iterator<ItemDeVenda> iter = itens.iterator();
       int i=0;
       while (iter.hasNext())
       {
           ItemDeVenda it= iter.next();
           item[i]=it;
           i++;
       
       }
       return item;

    }
}