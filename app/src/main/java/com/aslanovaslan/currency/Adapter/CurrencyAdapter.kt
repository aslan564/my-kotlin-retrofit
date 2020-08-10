package com.aslanovaslan.currency.Adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslanovaslan.currency.Model.CryptoModel
import com.aslanovaslan.currency.R
import kotlinx.android.synthetic.main.crypto_layout_row.view.*

class CurrencyAdapter(
    private val cryptoList: ArrayList<CryptoModel>,
    private val listener: Listener
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>() {
    interface Listener {
        fun onClickItem(cryptoModel: CryptoModel)
    }

    private val colorList: Array<String> = arrayOf(
        "#2E003E",
        "#8FBB96",
        "#58546d",
        "#5472c0",
        "#a991c3",
        "#21de7c",
        "#c3bc12",
        "#069740",
        "#add7b0",
        "#66679a",
        "#1e0027"
    )

    class CurrencyHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindCurrencyHolder(
            cryptoModel: CryptoModel,
            listener: Listener,
            colors: Array<String>,
            positions: Int
        ) {
            itemView.setOnClickListener{
                listener.onClickItem(cryptoModel)
            }
            itemView.currencyText.text = cryptoModel.currency
            itemView.priceText.text = cryptoModel.price
            itemView.setBackgroundColor(Color.parseColor(colors[positions % 11]))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.crypto_layout_row, parent, false)
        return CurrencyHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.bindCurrencyHolder(cryptoList[position],listener,colorList,position)
    }
}