package br.com.odnumiar.vigilantesdacidade.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast

import br.com.odnumiar.vigilantesdacidade.R
import br.com.odnumiar.vigilantesdacidade.models.Evento
import com.example.neto.aula1_1.adapters.MyAdapter
import kotlinx.android.synthetic.main.activity_ac__lista__denuncias.*

class Ac_Lista_Denuncias : AppCompatActivity() {

    init{
        //Log.d("DEBUG_VC", "executado init de ConnectionService")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ac__lista__denuncias)

        rvLista.setHasFixedSize(true)
        rvLista.layoutManager = LinearLayoutManager(this)


        var eventos:ArrayList<Evento> = ArrayList<Evento>()
        for (x in 0..200){
            eventos.add(Evento("Title ${x}", "Desc ${x}"))
        }
        var adapter = MyAdapter(this, eventos){
            Toast.makeText(this@Ac_Lista_Denuncias,
                    it.title + " - "+ it.desc,
                    Toast.LENGTH_SHORT).show()
        }

        rvLista.adapter = adapter
    }
}
