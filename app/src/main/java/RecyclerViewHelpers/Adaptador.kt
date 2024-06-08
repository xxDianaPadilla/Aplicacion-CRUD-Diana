package RecyclerViewHelpers

import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import diana.padilla.aplicacioncruddiana.R
import modelo.Tickets

class Adaptador(var Data: List<Tickets>):RecyclerView.Adapter<ViewHolder>() {
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
    }

}