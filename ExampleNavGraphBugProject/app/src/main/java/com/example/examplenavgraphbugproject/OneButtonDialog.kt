
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.examplenavgraphbugproject.databinding.DialogOneButtonBinding

class OneButtonDialog : DialogFragment() {

    var listener: Listener? = null

    private var _binding: DialogOneButtonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogOneButtonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        arguments?.let {
            binding.titleTextView.text = it.getCharSequence(ARG_TITLE)
            binding.bodyTextView.text = it.getCharSequence(ARG_BODY)

            it.getCharSequence(ARG_CTA_COPY)?.let {
                binding.ctaButton.text = it
            }
        }

        binding.ctaButton.setOnClickListener {
            listener?.onCtaClicked()
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_BODY = "arg_body"
        private const val ARG_CTA_COPY = "arg_cta_copy"

        fun newInstance(
            title: CharSequence,
            body: CharSequence,
            ctaCopy: CharSequence? = null
        ): OneButtonDialog {
            val dialogFragment = OneButtonDialog()

            val args = Bundle()
            args.putCharSequence(ARG_TITLE, title)
            args.putCharSequence(ARG_BODY, body)
            args.putCharSequence(ARG_CTA_COPY, ctaCopy)
            dialogFragment.arguments = args
            return dialogFragment
        }
    }

    interface Listener {

        fun onCtaClicked()

    }
}