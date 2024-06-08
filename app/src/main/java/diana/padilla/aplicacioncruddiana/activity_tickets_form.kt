package diana.padilla.aplicacioncruddiana

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modelo.ClaseConexion

class activity_tickets_form : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tickets_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnViewTickets = findViewById<Button>(R.id.btnViewTickets)

        btnViewTickets.setOnClickListener {
            val myTicketsScreen = Intent(this, activity_my_tickets::class.java)
            startActivity(myTicketsScreen)
        }

        val txtTicketNumber = findViewById<EditText>(R.id.txtTicketNumber)
        val txtTitleTicket = findViewById<EditText>(R.id.txtTitleTicket)
        val txtTicketDescription = findViewById<EditText>(R.id.txtTicketDescription)
        val txtAuthorTicket = findViewById<EditText>(R.id.txtAuthorTicket)
        val txtEmailAuthor = findViewById<EditText>(R.id.txtEmailAuthor)
        val txtCreationDate = findViewById<EditText>(R.id.txtCreationDate)
        val txtStatus = findViewById<EditText>(R.id.txtStatus)
        val txtTicketEndDate = findViewById<EditText>(R.id.txtTicketEndDate)
        val btnSendTicket = findViewById<Button>(R.id.btnSendTicket)

        btnSendTicket.setOnClickListener {

            if(txtTicketNumber.text.isEmpty() || txtTitleTicket.text.isEmpty() || txtTicketDescription.text.isEmpty() || txtAuthorTicket.text.isEmpty() || txtAuthorTicket.text.isEmpty() || txtEmailAuthor.text.isEmpty() || txtCreationDate.text.isEmpty() || txtStatus.text.isEmpty() || txtTicketEndDate.text.isEmpty()){
                runOnUiThread {
                    Toast.makeText(this, "Please fill out all the data 😮", Toast.LENGTH_LONG).show()
                }
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    val objConexion = ClaseConexion().cadenaConexion()

                    val addTicket = objConexion?.prepareStatement("insert into Tickets (ticket_number, ticket_title, ticket_description, ticket_author, contact_email, creation_date, ticket_status, completion_date) values (?, ?, ?, ?, ?, ?, ?, ?)")!!

                    addTicket.setString(1, txtTicketNumber.text.toString())
                    addTicket.setString(2, txtTitleTicket.text.toString())
                    addTicket.setString(3, txtTicketDescription.text.toString())
                    addTicket.setString(4, txtAuthorTicket.text.toString())
                    addTicket.setString(5, txtEmailAuthor.text.toString())
                    addTicket.setString(6, txtCreationDate.text.toString())
                    addTicket.setString(7, txtStatus.text.toString())
                    addTicket.setString(8, txtTicketEndDate.text.toString())
                    addTicket.executeQuery()

                    runOnUiThread {
                        txtTicketNumber.text.clear()
                        txtTitleTicket.text.clear()
                        txtTicketDescription.text.clear()
                        txtAuthorTicket.text.clear()
                        txtEmailAuthor.text.clear()
                        txtCreationDate.text.clear()
                        txtStatus.text.clear()
                        txtTicketEndDate.text.clear()
                        Toast.makeText(this@activity_tickets_form, "Ticket sent successfully 🎉", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }
}