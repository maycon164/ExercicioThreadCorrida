package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

public class ThreadCorrida extends Thread{
	String carro;
	int [] tempos = new int[3];
	static Map<String, Integer> lista = new TreeMap<String, Integer>();
	Semaphore s;
	
	public ThreadCorrida(String carro, Semaphore s) {
		this.carro = carro;
		this.s = s;
		this.start();
	}

	@Override
	public void run() {
		voltas();
	}
	
	public void voltas() {
		try {
			s.acquire();
			
			for(int x = 0; x < 3; x++) {
				int t = (int) (Math.random() * 40) + 80;
				Thread.sleep(t);
				System.out.println(carro + " realizou a "+ (x+1) + "º volta no tempo de " + t + " segundos");
				tempos[x] = t;
			}
			Arrays.sort(tempos);
			addTempo(carro, tempos[0]);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			s.release();
		}
	}
	
	public synchronized void addTempo(String carro, int tempo) {
		lista.put(carro, tempo);
	}
	
	public static void imprimirRank() {
		//uma lista de Entry
		List<Entry<String, Integer>> rank = new ArrayList<>(lista.entrySet());
		rank.sort(Entry.comparingByValue());
		int posicao = 1;
		for(Entry <String, Integer> dados: rank) {
			System.out.println(posicao + "º " + dados.getKey() + " com a volta de " + dados.getValue() + " segundos");
			posicao += 1;
		}
	}
	
	
}
