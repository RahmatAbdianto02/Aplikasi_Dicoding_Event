import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aplikasidicodingevent.R
import com.dicoding.aplikasidicodingevent.RecycleView.ListEventsItem

class EventAdapter(
    private val context: Context,
    private val events: List<ListEventsItem>,
    private val clickListener: (String) -> Unit // Menangani klik dan mengirim eventId
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Referensi ke view dalam item_row_image
        private val imageView: ImageView = itemView.findViewById(R.id.item_Image)
        private val textView: TextView = itemView.findViewById(R.id.item_Text)

        fun bind(event: ListEventsItem) {
            // Mengikat data gambar dan teks ke view
            Glide.with(context)
                .load(event.mediaCover)  // Pastikan URL dari API benar
                .placeholder(R.drawable.ic_launcher_background) // Tambahkan placeholder jika diperlukan
                .error(R.drawable.ic_launcher_background) // Tambahkan gambar error jika gagal memuat
                .into(imageView)

            textView.text = event.name  // Mengikat nama event ke TextView

            // Mengatur klik item untuk mengirim eventId
            itemView.setOnClickListener {
                clickListener(event.id.toString()) // Mengirim eventId
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_row_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size
}
