package com.machine.entity;

public enum Coin {
	NOCOINE(0), Coine(1),FIVEROOPEE(5),TENRUPEE(10),TWENTYROOPEE(20),FIFTYROOPEE(50),HUNDREDROOPEE(100);
	private int coinValue;
	Coin(int i) {
		this.coinValue = i;
	}
	public int getCoinValue(){
		return this.coinValue;
	}
	
}
