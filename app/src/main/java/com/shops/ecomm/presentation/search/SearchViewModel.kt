package com.shops.ecomm.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shops.ecomm.domain.model.Product
import com.shops.ecomm.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val query: String = "",
    val minPrice: Float = 0f,
    val maxPrice: Float = 10000f,
    val selectedCategory: String = "All",
    val filteredProducts: List<Product> = emptyList(),
    val categories: List<String> = listOf("All", "Clothes", "Electronics", "Furniture", "Shoes", "Beauty"),
    val isLoading: Boolean = false
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    val repository: ProductRepository
) : ViewModel() {

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    
    private val _query = MutableStateFlow("")
    private val _priceRange = MutableStateFlow(0f..10000f)
    private val _selectedCategory = MutableStateFlow("All")

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadAllProducts()
        observeFilters()
    }

    private fun loadAllProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val products = repository.getProducts()
                _allProducts.value = products
            } catch (e: Exception) {
                // handle error
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun observeFilters() {
        viewModelScope.launch {
            combine(_allProducts, _query, _priceRange, _selectedCategory) { products, query, range, category ->
                products.filter { product ->
                    val matchesQuery = product.title.contains(query, ignoreCase = true)
                    val matchesPrice = product.price.toFloat() in range
                    val matchesCategory = (category == "All") || product.description.contains(category, ignoreCase = true)
                    matchesQuery && matchesPrice && matchesCategory
                }
            }.collect { filtered ->
                _uiState.update { 
                    it.copy(
                        filteredProducts = filtered,
                        query = _query.value,
                        minPrice = _priceRange.value.start,
                        maxPrice = _priceRange.value.endInclusive,
                        selectedCategory = _selectedCategory.value
                    )
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        _query.value = query
    }

    fun onPriceRangeChange(range: ClosedFloatingPointRange<Float>) {
        _priceRange.value = range
    }

    fun onCategoryChange(category: String) {
        _selectedCategory.value = category
    }
}
