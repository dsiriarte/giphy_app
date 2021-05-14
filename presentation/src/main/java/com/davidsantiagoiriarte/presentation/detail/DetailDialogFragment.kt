package com.davidsantiagoiriarte.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.davidsantiagoiriarte.presentation.R
import com.davidsantiagoiriarte.presentation.databinding.FragmentDetailDialogBinding

private const val ARG_GIF_LINK = "ARG_GIF_LINK"
private const val ARG_SHARE_GIF_LINK = "ARG_SHARE_GIF_LINK"
private const val ARG_TITLE = "ARG_TITLE"
const val DETAIL_DIALOG_TAG = "Detail"

class DetailDialogFragment : DialogFragment() {

    private var gifLink: String? = null
    private var shareGifLink: String? = null
    private var title: String? = null

    private var _binding: FragmentDetailDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gifLink = it.getString(ARG_GIF_LINK)
            title = it.getString(ARG_TITLE)
            shareGifLink = it.getString(ARG_SHARE_GIF_LINK)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailDialogBinding.inflate(inflater, container, false)
        bindDialog()
        binding.ivShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareGifLink)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, getString(R.string.share_message))
            startActivity(shareIntent)
        }
        return binding.root
    }

    private fun bindDialog() {
        context?.let {
            Glide.with(it)
                .load(gifLink)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.ivGif)
        }
        binding.tvTitle.text = title
    }

    companion object {

        @JvmStatic
        fun newInstance(gifLink: String, shareGifLink: String, title: String) =
            DetailDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_GIF_LINK, gifLink)
                    putString(ARG_SHARE_GIF_LINK, shareGifLink)
                    putString(ARG_TITLE, title)
                }
            }
    }
}