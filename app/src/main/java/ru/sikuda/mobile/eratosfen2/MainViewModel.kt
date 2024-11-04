package ru.sikuda.mobile.eratosfen2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    private val _text = MutableStateFlow("")
    val text = _text.asStateFlow()

    //run async routing
    fun runCalc(){
        viewModelScope.launch {
            _text.value = "Async calc..."
            val result = withContext( Dispatchers.Default ) {
                return@withContext calculate()
            }
            _text.value = "Async-$result"
        }
    }

    //run sync routing
    fun runCalcSync(){
        _text.value = "Sync calc..."
        _text.value = "Sync-"+calculate()
    }

    //run execution
    private fun calculate(): String {

        val n = 50_000_000
        val array: Array<Int> = Array(n+1) { 1 }
        array[0] = 0
        array[1] = 0

        val timeBegin = System.currentTimeMillis()
        var i = 2
        while ( i <= n ) {
            if (array[i] == 1) {
                val sq: Long = i.toLong() * i
                if (sq <= n) {
                    var m: Int = sq.toInt()
                    while (m <= n) {
                        array[m] = 0
                        m += i
                    }
                }
            }
            i += 1
        }
        val timeEnd = System.currentTimeMillis()
        val diff: Double = (timeEnd.toDouble() - timeBegin) / 1000
        return diff.toString()
    }
}