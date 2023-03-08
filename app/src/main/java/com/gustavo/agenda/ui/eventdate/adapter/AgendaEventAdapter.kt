package com.gustavo.agenda.ui.eventdate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.databinding.AgendaEventItemBinding

class AgendaEventAdapter: RecyclerView.Adapter<AgendaEventAdapter.AgendaEventViewHolder>() {

    private val agendaEvents = mutableListOf<AgendaEvent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendaEventViewHolder {
        val binding = AgendaEventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgendaEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AgendaEventViewHolder, position: Int) {
        holder.bind(agendaEvents[position])
    }

    override fun getItemCount(): Int = agendaEvents.size

    fun update(agendaEvents: List<AgendaEvent>){
        this.agendaEvents.clear()
        this.agendaEvents.addAll(agendaEvents)
        notifyDataSetChanged()
    }

    inner class AgendaEventViewHolder(
        private val binding: AgendaEventItemBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(agendaEvent: AgendaEvent) {
            binding.agendaEventTitle.text = agendaEvent.eventDetail.name
            binding.agendaEventDescription.text = agendaEvent.eventDetail.description

            binding.agendaEventDateTitle.text = "${agendaEvent.eventDate.day}/${agendaEvent.eventDate.month}/${agendaEvent.eventDate.year} as ${agendaEvent.eventTime.hour}:${agendaEvent.eventTime.minute}"
        }
    }

}