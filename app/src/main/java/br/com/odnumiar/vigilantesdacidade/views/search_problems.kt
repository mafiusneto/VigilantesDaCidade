package br.com.odnumiar.vigilantesdacidade.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast

import br.com.odnumiar.vigilantesdacidade.R
import kotlinx.android.synthetic.main.activity_search_problems.*

class search_problems : AppCompatActivity() {

    init{
        //Log.d("DEBUG_VC", "executado init de ConnectionService")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_problems)


        val adapter: ArrayAdapter<CharSequence> =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.states,
                        android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spStates.adapter = adapter

        spStates.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@search_problems,
                        "Selecione", Toast.LENGTH_LONG).show()
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@search_problems,
                        "${spStates.selectedItem}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun TesteClick(v: View){
        Toast.makeText(this@search_problems,"Teste msg:${spStates.selectedItem}", Toast.LENGTH_SHORT).show()
    }

    fun pr_busca_local_proximo(v: View){
        val intent = Intent(this@search_problems, Ac_MapsActivity::class.java)
        startActivity(intent)
    }
}
