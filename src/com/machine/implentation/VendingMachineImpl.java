/**
 * 
 */
package com.machine.implentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import com.machine.api.Inventory;
import com.machine.api.VendingMachine;
import com.machine.entity.Bucket;
import com.machine.entity.Coin;
import com.machine.entity.Product;
import com.machine.exception.ItemNotSelectedException;
import com.machine.exception.NotAChangedException;
import com.machine.exception.NotFullPaidException;
import com.machine.exception.ProductNotFoundExcepton;
import com.machine.util.InventoryUtil;

/**
 * @author milind.bhuktar
 *
 */
public class VendingMachineImpl implements VendingMachine {
	
	private Inventory<Product, Integer> itemInventory= new Inventory<Product, Integer>();
	private Inventory<Coin, Integer> cashInventory = new Inventory<Coin, Integer>();
	private Product currentItem;
	private int currentBalance;
	
	public VendingMachineImpl() {
		initialize();
	}

	private void initialize() {
		this.itemInventory.putInventory(new Product("COCK", 66), new Integer(10));
		this.itemInventory.putInventory(new Product("PEPSI", 70), 10);
		this.itemInventory.putInventory(new Product("LAYS", 35), 10);
		this.cashInventory.putInventory(Coin.Coine, 10);
		this.cashInventory.putInventory(Coin.FIFTYROOPEE, 5);
		this.cashInventory.putInventory(Coin.HUNDREDROOPEE,5);
		this.cashInventory.putInventory(Coin.TENRUPEE,10);
		this.cashInventory.putInventory(Coin.TWENTYROOPEE,50);
		this.cashInventory.putInventory(Coin.FIVEROOPEE,10);
		this.setCurrentBalance();
	}

	private void setCurrentBalance() {
		if(this.cashInventory.getInvetory().size() >0){
		List<Integer> cashCoinList = this.cashInventory.getInvetory().entrySet()
				.stream().map(e->e.getKey().getCoinValue()*e.getValue())
				.collect(Collectors.toList());
		Optional<Integer> currentBalance = cashCoinList.stream().reduce(Integer::sum);			
		this.currentBalance = currentBalance.get().intValue();
		}
	}

	@Override
	public int selectItemGetPrice(Product product) throws ProductNotFoundExcepton {
		List<Entry<Product, Integer>> productPrice = this.itemInventory.getInvetory().entrySet().stream().filter(e->e.getKey().getItemName().equals(product.getItemName())).collect(Collectors.toList());
		if(!productPrice.isEmpty()){
		Product selectedProduct = productPrice.get(0).getKey();
		this.currentItem = selectedProduct;
		return (int)selectedProduct.getItemPrice();
		}else{
			throw new ProductNotFoundExcepton("Product Not available");
		}
	}
	public void displayInsertedCoinValue(Coin... coins){
		Optional<Integer> insertedCoinValue = Arrays.asList(coins).stream().map(e->e.getCoinValue()).collect(Collectors.toList()).stream().reduce(Integer::sum);
		int insertedValue = insertedCoinValue.get().intValue();
		System.out.println("Inserted Coin Value: "+insertedValue);
	}
	@Override
	public Optional<Bucket> insertCoin(Coin... coins) throws NotFullPaidException, ItemNotSelectedException {
	
		Bucket bucket= null;
		if(currentItem != null){
			Optional<Integer> insertedCoinValue = Arrays.asList(coins).stream().map(e->e.getCoinValue()).collect(Collectors.toList()).stream().reduce(Integer::sum);
			int insertedValue = insertedCoinValue.get().intValue();
			if(insertedValue < this.currentItem.getItemPrice()){
				bucket=new Bucket(new Product("Not a fullPaid"), Arrays.asList(coins));
			}else{
				try {
					bucket = this.getItemsAndChange(insertedValue);
				} catch (NotAChangedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}else{
			throw new ItemNotSelectedException("Item Not Selected");
		}
		
		Bucket returnBucket =  bucket==null ?new Bucket(new Product("Item not found"),Arrays.asList(coins)):bucket;
		 Optional<Bucket> opt = Optional.ofNullable(returnBucket);
		return opt;
	}

	@Override
	public Bucket getItemsAndChange(int insertedValue) throws NotAChangedException {
		// TODO Auto-generated method stub
		this.addToCashInventory(insertedValue);
		this.setCurrentBalance();
		int changedValue = this.getChanged(insertedValue, (int) this.currentItem.getItemPrice());
		this.substractChangedFromInventory(changedValue);
		this.currentBalance = this.currentBalance-changedValue;
		this.removedItemFromInventory();
		ArrayList<Coin> coins = new ArrayList<Coin>();
		InventoryUtil uitls = new InventoryUtil();
		return new Bucket(this.currentItem, uitls.convertToCoin(new ArrayList<Coin>(),changedValue));
	}
	

	private void removedItemFromInventory(){
		int itemCount= this.itemInventory.getInvetory().get(currentItem);
		this.itemInventory.getInvetory().put(currentItem, itemCount-1);
	}
	private void substractChangedFromInventory(int changedValue){
		
		int reminder = 0;
		if(changedValue>=Coin.HUNDREDROOPEE.getCoinValue()){
			int test =  this.putCoinAndDecrement(Coin.HUNDREDROOPEE, changedValue);
			if(test!=0){
				substractChangedFromInventory(test);
			}

		}else if(changedValue>=Coin.FIFTYROOPEE.getCoinValue()){
			int test = this.putCoinAndDecrement(Coin.FIFTYROOPEE, changedValue);
			if(test!=0){
				substractChangedFromInventory(test);
			}

		}else if(changedValue>=Coin.TWENTYROOPEE.getCoinValue()){
			int test = this.putCoinAndDecrement(Coin.TWENTYROOPEE, changedValue);
			if(test!=0){
				substractChangedFromInventory(test);
			}

		}else if(changedValue>=Coin.TENRUPEE.getCoinValue()){
			int test = this.putCoinAndDecrement(Coin.TENRUPEE, changedValue);
			if(test!=0){
				substractChangedFromInventory(test);
			}

		}else if(changedValue>=Coin.FIVEROOPEE.getCoinValue()){
			int test  = this.putCoinAndDecrement(Coin.FIVEROOPEE, changedValue);
			if(test!=0){
				substractChangedFromInventory(test);
			}

		}else if(changedValue>=Coin.Coine.getCoinValue()){
			int test = this.putCoinAndDecrement(Coin.Coine, changedValue);
			if(test!=0){
				substractChangedFromInventory(test);
			}

		}
		
	}
	
	private int putCoinAndDecrement(Coin coin, int changedValue){
		int reminder = changedValue/coin.getCoinValue();
		int numberOfCoin =this.cashInventory.getInvetory().get(coin);
		if(numberOfCoin>reminder)
			numberOfCoin=numberOfCoin-reminder;
		this.cashInventory.getInvetory().put(coin, numberOfCoin);
		int test  =  changedValue-(reminder*coin.getCoinValue());
		return test;
	}
	
	private int putCoinAndIncreament(Coin coin, int insertedCoinValue){
		int reminder = insertedCoinValue/coin.getCoinValue();
		int numberOfCoin =this.cashInventory.getInvetory().get(coin);
		numberOfCoin=reminder+numberOfCoin;
		this.cashInventory.getInvetory().put(coin, numberOfCoin);
		int test  =  insertedCoinValue-(reminder*coin.getCoinValue());
		return test;
	}
	private void addToCashInventory(int insertedCoinValue){
			if(insertedCoinValue>=Coin.HUNDREDROOPEE.getCoinValue()){
				int test = this.putCoinAndIncreament(Coin.HUNDREDROOPEE, insertedCoinValue);
				if(test!=0){
					addToCashInventory(test);
				}

			}else if(insertedCoinValue>=Coin.FIFTYROOPEE.getCoinValue()){
				int test = this.putCoinAndIncreament(Coin.FIFTYROOPEE, insertedCoinValue);
				if(test!=0){
					addToCashInventory(test);
				}
			}else if(insertedCoinValue>=Coin.TWENTYROOPEE.getCoinValue()){
				int test = this.putCoinAndIncreament(Coin.TWENTYROOPEE, insertedCoinValue);
				if(test!=0){
					addToCashInventory(test);
				}
			}else if(insertedCoinValue>=Coin.TENRUPEE.getCoinValue()){
				int test = this.putCoinAndIncreament(Coin.TWENTYROOPEE, insertedCoinValue);
				if(test!=0){
					addToCashInventory(test);
				}
			}else if(insertedCoinValue>=Coin.FIVEROOPEE.getCoinValue()){
				int test = this.putCoinAndIncreament(Coin.FIVEROOPEE, insertedCoinValue);
				if(test!=0){
					addToCashInventory(test);
				}
			}else if(insertedCoinValue>=Coin.Coine.getCoinValue()){
				int test = this.putCoinAndIncreament(Coin.Coine, insertedCoinValue);
				if(test!=0){
					addToCashInventory(test);
				}
			}
	}

	private int getChanged(int insertedValue, int itemPrice){
		if(insertedValue > itemPrice){
			return insertedValue-itemPrice;
		}else{
			return itemPrice -insertedValue;
		}
	}
	public void reset() {
		this.currentItem = null;
	}

}