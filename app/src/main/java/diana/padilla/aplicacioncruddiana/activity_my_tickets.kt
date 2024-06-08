package diana.padilla.aplicacioncruddiana

import RecyclerViewHelpers.Adaptador
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.Tickets

class activity_my_tickets : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_tickets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnGoBack2 = findViewById<ImageView>(R.id.btnGoBack2)

        btnGoBack2.setOnClickListener {
            val ticketsFormScreen = Intent(this, activity_tickets_form::class.java)
            startActivity(ticketsFormScreen)
        }

        val rcvTickets = findViewById<RecyclerView>(R.id.rcvTickets)

        rcvTickets.layoutManager = LinearLayoutManager(this)

        fun gettingTickets(): List<Tickets>{
            val objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("SELECT * FROM Tickets")!!

            val ticketsList = mutableListOf<Tickets>()

            while (resultSet.next()){
                val ticket_number = resultSet.getString("ticket_number")
                val ticket_title = resultSet.getString("ticket_title")
                val ticket_description = resultSet.getString("ticket_description")
                val ticket_author = resultSet.getString("ticket_author")
                val contact_email = resultSet.getString("contact_email")
                val creation_date = resultSet.getString("creation_date")
                val ticket_status = resultSet.getString("ticket_status")
                val completion_date = resultSet.getString("completion_date")

                val togetherValues = Tickets(ticket_number, ticket_title, ticket_description, ticket_author, contact_email, creation_date, ticket_status, completion_date)

                ticketsList.add(togetherValues)
            }

            return ticketsList
        }

        CoroutineScope(Dispatchers.IO).launch {
            val newTickets = gettingTickets()
            withContext(Dispatchers.IO){
                (rcvTickets.adapter as? Adaptador)?.updateList(newTickets)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val ticketsDB = gettingTickets()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(ticketsDB)
                rcvTickets.adapter = adapter
            }
        }
    }
}