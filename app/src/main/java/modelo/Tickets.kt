package modelo

data class Tickets(
    val ticket_number: String,
    var ticket_title: String,
    var ticket_description: String,
    var ticket_author: String,
    var contact_email: String,
    var creation_date: String,
    var ticket_status: String,
    var completion_date: String
)
