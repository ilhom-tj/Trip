package tj.ilhom.trip.ui.photosFragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import jp.shts.android.storiesprogressview.StoriesProgressView
import tj.ilhom.trip.R
import tj.ilhom.trip.databinding.PhotosFragmentBinding
import tj.ilhom.trip.ui.excurseList.adapter.ImageSliderAdapter


class PhotosFragment : Fragment(), StoriesProgressView.StoriesListener {

    private lateinit var viewModel: PhotosViewModel
    private lateinit var binding: PhotosFragmentBinding
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private val args: PhotosFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PhotosFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var pressTime = 0L
    private var limit = 500L
    private var counter = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        viewModel = ViewModelProvider(this).get(PhotosViewModel::class.java)
        imageSliderAdapter = ImageSliderAdapter(R.layout.image_slider_solid)
        imageSliderAdapter.submitList(args.photos.toList())


        Log.e("Size", args.photos.size.toString())
        Glide.with(requireContext()).load(args.photos[counter].medium).into(binding.image)

        binding.stories.setStoriesCount(counter)
        binding.stories.setStoriesCount(args.photos.size)
        binding.stories.setStoryDuration(3000L)
        binding.stories.setStoriesListener(this)
        binding.stories.startStories()

        binding.reverse.setOnClickListener {
            binding.stories.reverse()
        }
        binding.reverse.setOnTouchListener(onTouchListener)
        binding.skip.setOnClickListener {
            binding.stories.skip()
        }
        binding.close.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.skip.setOnTouchListener(onTouchListener)
    }

    override fun onNext() {
        Glide.with(requireContext()).load(args.photos[++counter].medium).into(binding.image)
    }

    override fun onPrev() {
        if (counter <= 0) {
            return
        }
        Glide.with(requireActivity()).load(args.photos[--counter].medium).into(binding.image)

    }

    override fun onComplete() {
        findNavController().navigateUp()
    }

    override fun onDestroy() {
        binding.stories.destroy()
        super.onDestroy()

    }

    @SuppressLint("ClickableViewAccessibility")
    private val onTouchListener = OnTouchListener { v, event -> // inside on touch method we are
        // getting action on below line.
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                // on action down when we press our screen
                // the story will pause for specific time.
                pressTime = System.currentTimeMillis()

                // on below line we are pausing our indicator.
                binding.stories.pause()
                return@OnTouchListener false
            }
            MotionEvent.ACTION_UP -> {

                // in action up case when user do not touches
                // screen this method will skip to next image.
                val now = System.currentTimeMillis()

                // on below line we are resuming our progress bar for status.
                binding.stories.resume()

                // on below line we are returning if the limit < now - presstime
                return@OnTouchListener limit < now - pressTime
            }
        }
        false
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

    }

    override fun onDetach() {
        super.onDetach()

        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

}