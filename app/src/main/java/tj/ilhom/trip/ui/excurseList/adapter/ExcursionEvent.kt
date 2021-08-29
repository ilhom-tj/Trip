package tj.ilhom.trip.ui.excurseList.adapter

import tj.ilhom.trip.models.excurse.Excurse

interface ExcursionEvent {
    fun excursionClick(excurse: Excurse)
}