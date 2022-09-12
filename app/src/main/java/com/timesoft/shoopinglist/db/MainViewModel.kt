package com.timesoft.shoopinglist.db

import androidx.lifecycle.*
import com.timesoft.shoopinglist.entities.LibraryItem
import com.timesoft.shoopinglist.entities.NoteItem
import com.timesoft.shoopinglist.entities.ShopListItem
import com.timesoft.shoopinglist.entities.ShopListNameItem
import kotlinx.coroutines.launch

class MainViewModel(database: MainDatabase) : ViewModel() {

    private val dao = database.getDao()
    val libraryItems = MutableLiveData<List<LibraryItem>>()
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    val allShopListNames: LiveData<List<ShopListNameItem>> = dao.getAllShopListNames().asLiveData()
    fun getAllItemsFromList(listId: Int): LiveData<List<ShopListItem>> {
        return dao.getAllShopListItems(listId).asLiveData()
    }fun getAllLibraryItems(name: String) = viewModelScope.launch{
        libraryItems.postValue(dao.getAllLibraryItems(name))
    }
    fun insertNote(noteItem: NoteItem) = viewModelScope.launch {
        dao.insertNote(noteItem)
    }

    fun insertShopItem(shopListItem: ShopListItem) = viewModelScope.launch {
        dao.insertItem(shopListItem)
        if (!isLibraryItemExists(shopListItem.name)) dao.insertLibraryItem(LibraryItem(null, shopListItem.name, ""))
    }

    fun insertShopListName(listName: ShopListNameItem) = viewModelScope.launch {
        dao.insertShopListName(listName)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }

    fun deleteLibraryItem(id: Int) = viewModelScope.launch {
        dao.deleteLibraryItem(id)
    }

    fun deleteShopList(id: Int, deleteList: Boolean) = viewModelScope.launch {
        if (deleteList)dao.deleteShopListName(id)
        dao.deleteShopListItemsByListId(id)
    }

    fun updateNote(noteItem: NoteItem) = viewModelScope.launch {
        dao.updateNote(noteItem)
    }

    fun updateLibraryItem(item: LibraryItem) = viewModelScope.launch {
        dao.updateLibraryItem(item)
    }

    fun updateListItem(shopListItem: ShopListItem) = viewModelScope.launch {
        dao.updateListItem(shopListItem)
    }

    fun updateListName(shopListName: ShopListNameItem) = viewModelScope.launch {
        dao.updateListName(shopListName)
    }
    private suspend fun isLibraryItemExists(name: String): Boolean{
        return dao.getAllLibraryItems(name).isNotEmpty()
    }

}