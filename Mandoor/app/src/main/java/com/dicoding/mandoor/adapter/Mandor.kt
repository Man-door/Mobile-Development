import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mandor(
    val img: String?,
    val fullName: String?,
    val ratingUser: Int?,
    val numberProyek: Int?,
    val jangkauan: String?,
    val layananLain: String?
)
 : Parcelable

