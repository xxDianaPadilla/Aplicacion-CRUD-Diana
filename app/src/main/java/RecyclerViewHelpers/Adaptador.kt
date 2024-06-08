package RecyclerViewHelpers

import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import diana.padilla.aplicacioncruddiana.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.Tickets

class Adaptador(var Data: List<Tickets>):RecyclerView.Adapter<ViewHolder>() {

    fun updateList(newList: List<Tickets>){
        Data = newList
        notifyDataSetChanged()
    }

    fun updateScreen(ticket_number: String, newticket_status: String){
        val index = Data.indexOfFirst { it.ticket_number == ticket_number }
        Data[index].ticket_status = newticket_status
        notifyDataSetChanged()
    }

    fun deleteData(ticket_number: String, position: Int){
        val dataList = Data.toMutableList()
        dataList.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()

            val deleteTicket = objConexion?.prepareStatement("delete from Tickets where ticket_number = ?")!!
            deleteTicket.setString(1, ticket_number)
            deleteTicket.executeQuery()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeQuery()
        }

        Data = dataList.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun updateData(newTicket_status: String, ticket_number: String){
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()

            val updateTicket = objConexion?.prepareStatement("update Tickets set ticket_status = ? where ticket_number = ?")!!
            updateTicket.setString(1, newTicket_status)
            updateTicket.setString(2, ticket_number)
            updateTicket.executeQuery()

            withContext(Dispatchers.Main){
                updateScreen(ticket_number, newTicket_status)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_ticket_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = Data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Data[position]
        holder.txtTicketNum.text = item.ticket_number
        holder.txtTicketTitle.text = item.ticket_title
        holder.txtTicketDes.text = item.ticket_description
        holder.txtTicketAuthor.text = item.ticket_author
        holder.txtAuthorEmail.text = item.contact_email
        holder.txtTicketCreationDate.text = item.creation_date
        holder.txtTicketStatus.text = item.ticket_status
        holder.txtFinalDate.text = item.completion_date

        holder.btnDelete.setOnClickListener {
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
            builder.setMessage("Do you want to delete the ticket?")

            builder.setPositiveButton("Yes"){dialog, which ->
                deleteData(item.ticket_number, position)
            }

            builder.setNeutralButton("No"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        holder.btnEdit.setOnClickListener {
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)

            builder.setTitle("Update")
            builder.setMessage("Do you want to update the ticket status?")

            val textBox = EditText(context)
            textBox.setHint(item.ticket_status)
            builder.setView(textBox)

            builder.setPositiveButton("Update"){dialog, which ->
                updateData(textBox.text.toString(), item.ticket_number)
            }

            builder.setNegativeButton("Cancel"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }
}