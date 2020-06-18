package com.machine.util;

import java.util.List;

import com.machine.entity.Coin;

public class InventoryUtil {
	
	
	
	public List<Coin> convertToCoin(List<Coin> returnCoinsArray, int changedValue){
		int reminder=0;
		if(changedValue>=Coin.HUNDREDROOPEE.getCoinValue()){
			reminder = changedValue/Coin.HUNDREDROOPEE.getCoinValue();
			if(reminder>0){
			for(int i=0; i<=reminder-1;i++){
				returnCoinsArray.add(Coin.HUNDREDROOPEE);
			}
			}
			int test  =  changedValue-(reminder*Coin.HUNDREDROOPEE.getCoinValue());
			if(test!=0){
				convertToCoin(returnCoinsArray, test);
			}

		}else if(changedValue>=Coin.FIFTYROOPEE.getCoinValue()){
			reminder = changedValue/Coin.FIFTYROOPEE.getCoinValue();
			if(reminder>0){
			for(int i=0; i<=reminder-1;i++){
				returnCoinsArray.add(Coin.FIFTYROOPEE);
			}
			}
			int test  =  changedValue-(reminder*Coin.FIFTYROOPEE.getCoinValue());
			if(test!=0){
				convertToCoin(returnCoinsArray, test);			}

		}else if(changedValue>=Coin.TWENTYROOPEE.getCoinValue()){
			reminder = changedValue/Coin.TWENTYROOPEE.getCoinValue();
			if(reminder>0){
			for(int i=0; i<=reminder-1;i++){
				returnCoinsArray.add(Coin.TWENTYROOPEE);
			}
			}
			int test  =  changedValue-(reminder*Coin.TWENTYROOPEE.getCoinValue());
			if(test!=0){
				convertToCoin(returnCoinsArray, test);			}

		}else if(changedValue>=Coin.TENRUPEE.getCoinValue()){
			reminder = changedValue/Coin.TENRUPEE.getCoinValue();
			if(reminder>0){
			for(int i=0; i<=reminder-1;i++){
				returnCoinsArray.add(Coin.TENRUPEE);
			}
			}
			int test  =  changedValue-(reminder*Coin.TENRUPEE.getCoinValue());
			if(test!=0){
				convertToCoin(returnCoinsArray, test);			}

		}else if(changedValue>=Coin.FIVEROOPEE.getCoinValue()){
			reminder = changedValue/Coin.FIVEROOPEE.getCoinValue();
			if(reminder>0){
			for(int i=0; i<=reminder-1;i++){
				returnCoinsArray.add(Coin.FIVEROOPEE);
			}
			}
			int test  =  changedValue-(reminder*Coin.FIVEROOPEE.getCoinValue());
			if(test!=0){
				convertToCoin(returnCoinsArray, test);
				}

		}else if(changedValue>=Coin.Coine.getCoinValue()){
			reminder = changedValue/Coin.Coine.getCoinValue();
			if(reminder>0){
			for(int i=0; i<=reminder-1;i++){
				returnCoinsArray.add(Coin.Coine);
			}
			}
			int test  =  changedValue-(reminder*Coin.Coine.getCoinValue());
			if(test!=0){
				convertToCoin(returnCoinsArray, test);
				}

		}
		return returnCoinsArray;
		
		
	}

}
