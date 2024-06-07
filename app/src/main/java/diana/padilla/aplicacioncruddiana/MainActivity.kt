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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val registerScreen = Intent(this, activity_register::class.java)
            startActivity(registerScreen)
        }

        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtPassword = findViewById<EditText>(R.id.txtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val ticketsFormScreen = Intent(this, activity_tickets_form::class.java)

            GlobalScope.launch(Dispatchers.IO){
                val objConexion = ClaseConexion().cadenaConexion()

                val checkUser = objConexion?.prepareStatement("select * from Users where email = ? and password = ?")!!
                checkUser.setString(1, txtEmail.text.toString())
                checkUser.setString(2, txtPassword.text.toString())
                val result = checkUser.executeQuery()

                if(txtEmail.text.isEmpty() || txtPassword.text.isEmpty()){
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Please fill out all the data 😮", Toast.LENGTH_LONG).show()
                    }
                }else if(result.next()){
                    startActivity(ticketsFormScreen)
                }else{
                    runOnUiThread {
                        txtEmail.text.clear()
                        txtPassword.text.clear()
                        Toast.makeText(this@MainActivity, "User not found 😪, try again", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}