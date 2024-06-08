package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import diana.padilla.aplicacioncruddiana.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val txtTicketNum = view.findViewById<TextView>(R.id.txtTicketNum)
    val txtTicketTitle = view.findViewById<TextView>(R.id.txtTicketTitle)
    val txtTicketDes = view.findViewById<TextView>(R.id.txtTicketDes)
    val txtTicketAuthor = view.findViewById<TextView>(R.id.txtTicketAuthor)
    val txtAuthorEmail = view.findViewById<TextView>(R.id.txtAuthorEmail)
    val txtTicketCreationDate = view.findViewById<TextView>(R.id.txtTicketCreationDate)
    val txtTicketStatus = view.findViewById<TextView>(R.id.txtTicketStatus)
    val txtFinalDate = view.findViewById<TextView>(R.id.txtFinalDate)
    val btnDelete = view.findViewById<ImageView>(R.id.btnDelete)
    val btnEdit = view.findViewById<ImageView>(R.id.btnEdit)
}