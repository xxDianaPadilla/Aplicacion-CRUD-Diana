package modelo

data class Tickets(
    val ticket_number: String,
    val ticket_title: String,
    val ticket_description: String,
    val ticket_author: String,
    val contact_email: String,
    val creation_date: String,
    val ticket_status: String,
    val completion_date: String
)
