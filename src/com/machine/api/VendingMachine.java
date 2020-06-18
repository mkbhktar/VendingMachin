/**
 * 
 */
package com.machine.api;

import java.util.Optional;

import com.machine.entity.Bucket;
import com.machine.entity.Coin;
import com.machine.entity.Product;
import com.machine.exception.ItemNotSelectedException;
import com.machine.exception.NotAChangedException;
import com.machine.exception.NotFullPaidException;
import com.machine.exception.ProductNotFoundExcepton;

/**
 * @author milind.bhuktar
 *
 */
public interface VendingMachine {
	public int selectItemGetPrice(Product product) throws ProductNotFoundExcepton;
	public Optional<Bucket> insertCoin(Coin ...coins) throws ItemNotSelectedException, NotFullPaidException ;
	public Bucket getItemsAndChange(int coinValue) throws NotAChangedException;
}
