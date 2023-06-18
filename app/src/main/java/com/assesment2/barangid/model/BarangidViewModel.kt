package com.assesment2.barangid

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.assesment2.barangid.data.Item
import com.assesment2.barangid.data.BarangDao
import kotlinx.coroutines.launch


class InventoryViewModel(private val barangDao: BarangDao) : ViewModel() {


    val allItems: LiveData<List<Item>> = barangDao.getItems().asLiveData()


    fun isStockAvailable(item: Item): Boolean {
        return (item.quantityInStock > 0)
    }


    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice, itemCount)
        updateItem(updatedItem)
    }



    private fun updateItem(item: Item) {
        viewModelScope.launch {
            barangDao.update(item)
        }
    }


    fun sellItem(item: Item) {
        if (item.quantityInStock > 0) {

            val newItem = item.copy(quantityInStock = item.quantityInStock - 1)
            updateItem(newItem)
        }
    }


    fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        insertItem(newItem)
    }


    private fun insertItem(item: Item) {
        viewModelScope.launch {
            barangDao.insert(item)
        }
    }


    fun deleteItem(item: Item) {
        viewModelScope.launch {
            barangDao.delete(item)
        }
    }


    fun retrieveItem(id: Int): LiveData<Item> {
        return barangDao.getItem(id).asLiveData()
    }


    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }


    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }


    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ): Item {
        return Item(
            id = itemId,
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }
}


class InventoryViewModelFactory(private val barangDao: BarangDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(barangDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

