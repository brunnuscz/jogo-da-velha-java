package view;

import java.util.ArrayList;
import java.util.Scanner;

import model.Coordenada;
import model.Jogador;
import model.Jogo;
import model.Tabuleiro;

public class InterfaceUsuario {
	Scanner sc = new Scanner(System.in);
	
	// METODO QUE PEGA AS INFORMAÇÕES DO JOGADOR NO TECLADO
	public Jogador pegarInfo() {
		Jogador jogador = new Jogador();
		System.out.print("\n- Nome do Jogador(a): ");
		jogador.nome = sc.next().toUpperCase();
		System.out.print("- Simbolo do Jogador(a): ");
		jogador.simbolo = sc.next();	
		
		return jogador;
	}
	
	// METODO PARA PEGAR AS COORDENADAS DO TECLADO
	public Coordenada pegarCoordenada(Jogador j, Tabuleiro t) {
		Coordenada coordenada = new Coordenada();
		
		// CONTROLE DE COORDENADAS < 0, > 2
		do{
			System.out.println("\n-> Vez do Jogador(a): "+limitaString(j.nome)+"("+j.simbolo+")");
			System.out.println("-> Linhas e Colunas de (0 a 2)\n");
			System.out.print("- Linha: ");
			coordenada.x = sc.nextInt();
			System.out.print("- Coluna: ");
			coordenada.y = sc.nextInt();
			
			System.out.println();
			if((coordenada.x < 0 || coordenada.x > 2) || (coordenada.y < 0 || coordenada.y > 2)) {
				System.out.println("------- Coordenadas invalidas ------\n");
				t.imprimirTabuleiro();
			}
		}while((coordenada.x < 0 || coordenada.x > 2) || (coordenada.y < 0 || coordenada.y > 2));
		
		return coordenada;
	}
	
	// METODO PARA PEGAR A OPCAO DO MENU
	public int pegarOpcao() {
		menuOpcaoJogador();
		System.out.print("- Digite a opcao: ");
		int op = sc.nextInt();
		System.out.println();
		return op;
	}
	// CALCULAR PONTUACAO
	public void gerarRanking(ArrayList<Jogo> j) {
		
		int[] pontuacoes = new int[j.size()*2];
		Jogador[] jogadores = new Jogador[j.size()*2];
		
		int i = 0;
		int indice = 0;
		while(i<pontuacoes.length) {
			pontuacoes[i] = j.get(indice).pontGeral[0];
			jogadores[i] = j.get(indice).jogadores[0];
	
			pontuacoes[i+1] = j.get(indice).pontGeral[1];
			jogadores[i+1] = j.get(indice).jogadores[1];	
			indice++;
			i = i + 2;
		}
		
		for(int p=0; p<pontuacoes.length; p++) {
			for(int q=0; q<pontuacoes.length; q++) {
				int aux1 = pontuacoes[p];
				Jogador aux2 = jogadores[p];
				if(pontuacoes[p] > pontuacoes[q]) {
					pontuacoes[p] = pontuacoes[q];
					jogadores[p] = jogadores[q];
					
					pontuacoes[q] = aux1;
					jogadores[q] = aux2;
				}
			}
		}
		System.out.println("======== VISUALIZAR RANKING ========\n");
		System.out.println(" Nome                     Pontuacao \n");
		for(int k=0; k<pontuacoes.length; k++) {
			System.out.print(" "+jogadores[k].nome+" ");				
			adicionarPonto(jogadores[k].nome, 5);
			System.out.println(" "+pontuacoes[k]);				
		}
		System.out.println("\n====================================\n");
	}
	
	// METODO PARA MOSTRAR OS HISTORICOS PARTIDAS
	public void imprimirHistoricoPartidas(ArrayList<Jogo> j) {
		System.out.println("======== HISTORICO PARTIDAS ========");
		for(int i=0; i<j.size(); i++) {
			System.out.println("____________________________________\n");
				System.out.println("Jogo: "+(i+1));
				System.out.println("Partidas:");
				for(int p=0; p < j.get(i).partidas.size(); p++) {
					System.out.println(" > Partida "+(p+1)+": "+limitaString(j.get(i).jogadores[0].nome)+" "+j.get(i).partidas.get(p).pontDaPartida[0]+" vs "+j.get(i).partidas.get(p).pontDaPartida[1]+" "+limitaString(j.get(i).jogadores[1].nome));
				}
				if(j.get(i).pontGeral[0] > j.get(i).pontGeral[1]) {
					System.out.println(" + Ganhador: "+j.get(i).jogadores[0].nome);
				}else if(j.get(i).pontGeral[0] < j.get(i).pontGeral[1]){
					System.out.println(" + Ganhador: "+j.get(i).jogadores[1].nome);
				}else {
					System.out.println(" + Esta Empate");				
				}
			System.out.println("____________________________________");
		}
		System.out.println("\n====================================\n");
	}
	// METODO PARA MOSTRAR OS HISTORICOS JOGADAS
	public void imprimirHistoricoJogadas(ArrayList<Jogo> j) {
		System.out.println("======== HISTORICO JOGADAS =========");
		for(int i=0; i<j.size(); i++) {
			System.out.println("____________________________________\n");
				System.out.println("Jogo: "+(i+1));
				for(int p=0; p < j.get(i).partidas.size(); p++) {
					System.out.println("\n > Partida "+(p+1)+"        Coordenada[x,y]\n");
					for(int g=0; g < j.get(i).partidas.get(p).jogadas.size(); g++) {
						if(g % 2 == 0) {
							System.out.print(" - Jogada "+(g+1)+": "+limitaString(j.get(i).jogadores[0].nome)+"("+j.get(i).jogadores[0].simbolo+") ");
							adicionarPonto(limitaString(j.get(i).jogadores[0].nome),24);
							System.out.println(" ["+j.get(i).partidas.get(p).jogadas.get(g).coordenada.x+","+j.get(i).partidas.get(p).jogadas.get(g).coordenada.y+"]");							
						}else {
							System.out.print(" - Jogada "+(g+1)+": "+limitaString(j.get(i).jogadores[1].nome)+"("+j.get(i).jogadores[1].simbolo+") ");
							adicionarPonto(limitaString(j.get(i).jogadores[1].nome),24);
							System.out.println(" ["+j.get(i).partidas.get(p).jogadas.get(g).coordenada.x+","+j.get(i).partidas.get(p).jogadas.get(g).coordenada.y+"]");							
						}
					}
				}
			System.out.println("____________________________________");
		}
		System.out.println("\n====================================\n");
	}
	public void adicionarPonto(String n, int valor) {
		int v = 36 - valor - n.length(); // 36 NÚMERO DE CARACTERE, valor SERIA OS SIMBOLOS E ESPACOS, n TAMANHO DO NOME
		for(int i=0; i < v; i++) {
			System.out.print(".");
		}
	}
	public static String limitaString(String texto){
	   if (texto.length() <= 6){
	      return texto+" ";
	   }else{
	      return texto.substring(0, 6)+".";
	   }
	}
	public void imprimirTrofeu() {
		System.out.println("               _______");
		System.out.println(" _\\/_   .``\\  /       \\  /``.  ");
		System.out.println("  /\\    \\   \\|         |/  /  ");
		System.out.println("        /   /| VITORIA |\\  \\  ");
		System.out.println("        `../ |         | \\..`  ");
		System.out.println("              \\       /        ");
		System.out.println("  _\\/_        `\\     /`        ");
		System.out.println("   /\\           `| |`     _\\/_ ");
		System.out.println("                 | |       /\\   ");
		System.out.println("             ___/   \\___        ");
		System.out.println("            /___________\\       \n");
	}
	// METODO PARA MOSTRAR TABULEIRO
	public void imprimirHistoricoTabuleiro(ArrayList<Jogo> j) {
		System.out.println("======= HISTORICO TABULEIROS =======");
		for(int i=0; i<j.size(); i++) {
			System.out.println("____________________________________\n");
				System.out.println("Jogo: "+(i+1));
				System.out.println("> Partidas:");
				for(int p=0; p < j.get(i).partidas.size(); p++) {
					System.out.println("\n    Partida "+(p+1));
					j.get(i).partidas.get(p).tabuleiro.imprimirTabuleiro();
				}
			System.out.println("____________________________________");
		}
		System.out.println("\n====================================\n");
	}
	// MENU DOS JOGADORES
	public void infoJogador(Jogador jogadorUm, Jogador jogadorDois) {
		System.out.println("\n============== Jogadores ===========");
		System.out.println();
		System.out.print(" "+jogadorUm.nome+" ");
		adicionarPonto(jogadorUm.nome,7);
		System.out.println(" ("+jogadorUm.simbolo+") ");

		System.out.print(" "+jogadorDois.nome+" ");
		adicionarPonto(jogadorDois.nome,7);
		System.out.println(" ("+jogadorDois.simbolo+") ");

		System.out.println();
		System.out.println("====================================\n");
	}
	// MENU DE OPÇÕES
	public void menuOpcaoJogador() {
		System.out.println("=============== MENU ===============");
		System.out.println("=                                  =");
		System.out.println("=          1 - Continuar           =");
		System.out.println("=          2 - Novo Jogo           =");
		System.out.println("=     3 - Escolher Jogo da Lista   =");
		System.out.println("=          4 - Ranking             =");
		System.out.println("=     5 - Historico de Partidas    =");
		System.out.println("=    6 - Historico de Tabuleiros   =");
		System.out.println("=      7 - Historico de Jogadas    =");
		System.out.println("=            8 - Sair              =");
		System.out.println("=                                  =");
		System.out.println("====================================\n");
	}
	// LISTA DE JOGOS ANTERIORES E ESCOLHA DO JOGO
	public int menuJogoAnterior(ArrayList<Jogo> j) {
		int op;
		System.out.println("==== LISTA DE JOGOS ANTERIORES =====");
		for(int i=0; i<j.size(); i++) {
			System.out.println("____________________________________\n");
			System.out.println("O Jogo "+(i+1)+" - "+j.get(i).jogadores[0].nome+" "+j.get(i).pontGeral[0]+" vs "+j.get(i).pontGeral[1]+" "+j.get(i).jogadores[1].nome);
		}
		System.out.println("____________________________________");
		
		System.out.print("\n- Escolher jogo: ");
		op = sc.nextInt() - 1;
		System.out.println();
		
		return op;
	}
}