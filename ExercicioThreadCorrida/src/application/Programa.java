package application;

import java.util.concurrent.Semaphore;

import controller.ThreadCorrida;

public class Programa {
	public static void main(String[] args) {
		String[][] equipes = new String[7][2];
		Semaphore s = new Semaphore(5);
		Thread[] ts = new Thread[14];
		char[] letras = { 'a', 'b', 'c', 'd', 'e', 'f', 'g' };
		
		// preenche o vetor de carros
		for (int x = 0; x < 7; x++) {
			equipes[x][0] = letras[x] + "1";
			equipes[x][1] = letras[x] + "2";
		}
		
		/*sorteia 5 carros para a corrida
		int num = 0;
		while(num < 5) {
			int linha = (int) (Math.random() * 7);
			int coluna = (int) (Math.random() * 101) + 1;
			
			if(coluna % 2 == 0) 
				coluna = 1;
			else
				coluna = 0;
			
			if(equipes[linha][coluna] != null) {
				ts[num] = new ThreadCorrida(equipes[linha][coluna], s);
				equipes[linha][coluna] = null;
				num += 1;
			}
			
		}*/
		
		//Faz a corrida com 14 carros / 5 de cada vez 
		
		for (int x = 0; x < 14; x++) {
			ts[x] = new ThreadCorrida("Carro" + (x+1), s);
		}
		
		
		//Exibe o rank de chegada 
		new Thread(new Runnable() {
			public void run() {

				try {
					for (Thread t : ts) {
						t.join();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println();
				System.out.println("===== Rank =====");
				ThreadCorrida.imprimirRank();
			}
		}).start();
	}
}
