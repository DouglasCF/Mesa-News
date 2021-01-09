package br.com.fornaro.mesanews

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("visible")
    fun View.visible(visible: Boolean) {
        visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(value = ["image_url", "placeholder", "center_crop"], requireAll = false)
    fun ImageView.loadImage(
        imageUrl: String?,
        placeholder: Drawable?,
        centerCrop: Boolean = false
    ) {
        imageUrl?.let {
            load(it) {
                placeholder(placeholder)
                crossfade(true)
                error(placeholder)
            }
        } ?: load(placeholder)
    }
}
