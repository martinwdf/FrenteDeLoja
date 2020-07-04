import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Venda {
    private double desconto = 0;
    private final double imposto=1.25;
    //private ArrayList<ItemDeVenda> itens;
    private Set<ItemDeVenda> itens;
    private boolean cancelada;

    Venda() {
        itens = new HashSet<ItemDeVenda>();
        cancelada=false;
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
    public Produto getItemDeVenda(int codigo){
        for(ItemDeVenda it: itens){
            if(it.getProduto().getCodigo()==codigo){
                return it.getProduto();
            }
        }
        return null;
    }
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
    public void insereRepetido(int ID, int quantidade) {
        for(ItemDeVenda it : itens){
            if(it.getProduto().getCodigo()==ID){
               it.addQuantidade(quantidade);
            }
        }
    }
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
    public double aplicaImposto(){
        return (this.getValorVenda()-desconto) *0.25;
    }
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