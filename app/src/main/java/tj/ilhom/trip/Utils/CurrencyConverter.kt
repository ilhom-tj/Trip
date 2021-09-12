package tj.ilhom.trip.Utils

object CurrencyConverter {
    fun getCurrencyEmblem(name : String) : String{
        when (name) {
            "EUR" -> {
                return "€"
            }
            "USD" -> {
                return "$"
            }
            "RUB" ->{
                return "₽"
            }
        }
        return ""
    }
}