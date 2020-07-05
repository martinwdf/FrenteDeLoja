import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Relatorios {
    private double valorMedio;
    public GerenteDeVendas gerVendas;
    public Estoque estoque;
    public ControleDeEstoque ctrl;

    @FunctionalInterface
    public interface Interface {
        public void relatorio();
    }

    public Relatorios(GerenteDeVendas gerVendas, Estoque estoque, ControleDeEstoque ctrl) {
        this.ctrl=ctrl;
        this.estoque = estoque;
        this.gerVendas = gerVendas;
    }

    public void menuRelatorios() throws CloneNotSupportedException {
        int menu = 0;
        boolean active = true;
        while (active) {
            Scanner entrada = new Scanner(System.in);
            System.out.println("\n>>>>>>>>>>> Menu Relatorios <<<<<<<<<<<\n");
            System.out.println("Por favor escolha uma das opcoes: ");
            System.out.println("1 -> Faturamento Atual");
            System.out.println("2 -> Ticket médio de vendas");
            System.out.println("3 -> Produtos mais vendidos");
            System.out.println("4 -> Listar vendas canceladas");
            System.out.println("5 -> Sair do menu de realatorios");
            System.out.print("Escolha um item do menu:");
            boolean active2 = true;
            do {
                if (entrada.hasNextInt()) {
                    menu = entrada.nextInt();
                    active2 = false;
                } else {
                    entrada.nextLine();
                    System.out.println("\nDigite SOMENTE números\n");
                }
            } while (active2 == true);
            switch (menu) {
                case 1:
                List<Venda> vendas1 = new LinkedList<Venda>();
                gerVendas.percorreLista((x, venda) -> {
                    if(!venda.getCancelada()){
                        vendas1.add(venda);
                    }
                });
                double faturamentoBruto =vendas1.stream()
                    .filter(z->!z.getCancelada())
                    .mapToDouble(z -> z.valorFinal())
                    .sum();
                double faturamentoLiquido =vendas1.stream()
                .filter(z->!z.getCancelada())
                .mapToDouble(z -> z.getValorVenda())
                .sum();
                System.out.println("Faturamento Bruto: " + faturamentoBruto);
                System.out.println("Faturamento Liquido: " + faturamentoLiquido);
                    break;
                case 2:
                    valorMedio = 0;
                    List<Venda> vendas = new LinkedList<Venda>();
                    gerVendas.percorreLista((x, venda) -> {
                        
                            vendas.add(venda);
                    
                    });
                    double valorMedio2= vendas.stream()
                        .filter(z-> !z.getCancelada())
                        .mapToDouble(z->z.valorFinal())
                        .average()
                        .getAsDouble();
                        

                    System.out.println("\nValor medio das venda: " + valorMedio2);
                    break;
                case 3:
                    List<ItemDeVenda> ordemDecrescente = new LinkedList<ItemDeVenda>();
                    // vai criar colocar todos os item de venda, sem produtos repetidos
                    gerVendas.percorreLista((x, venda) -> {
                        if (!venda.getCancelada()) {
                            Set<ItemDeVenda> list = venda.getItens();
                            list.stream().filter(z -> {
                                for (ItemDeVenda it2 : ordemDecrescente) {
                                    if (it2.getProduto().getCodigo() == z.getProduto().getCodigo()) {
                                        it2.addQuantidade(z.getQuntidade());
                                        return false;
                                    }
                                }
                                return true;
                            }).forEach(y -> ordemDecrescente.add(y));
                        }
                    });
                    Comparator<ItemDeVenda> c1 = (ItemDeVenda it1, ItemDeVenda it2) -> it2.getQuntidade()- it1.getQuntidade();
                    Collections.sort(ordemDecrescente, c1);
                    System.out.println("Produtos mais vendidos");
                    Iterator<ItemDeVenda> iter = ordemDecrescente.iterator();
                    int i = 0;
                    while (iter.hasNext()) {
                        ItemDeVenda it = iter.next();
                        if (i > 5) {
                            break;
                        }
                        System.out.println(i+1 + "->" + it.getProduto().toString() + "Quantidade: " + it.getQuntidade());
                        i++;
                    }
                    try {
                        GerenteDeVendas v2 = new GerenteDeVendas(estoque, ctrl);
                        this.gerVendas=v2;
                     } catch (NumberFormatException | IOException e) {e.printStackTrace();}
                    ordemDecrescente.removeAll(ordemDecrescente);
                    break;
                case 4:
                System.out.println("Lista de Vendas Canceladas!");
                    gerVendas.percorreLista((x,venda) -> {
                        if(venda.getCancelada()){
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
