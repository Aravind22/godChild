package com.godapp.godapp.model

data class DataModel(
    var title : String,
    var desc : String,
    var image : Int
)
interface CellClickListener {
    fun onCellClickListener(title : String)
}