package tj.ilhom.trip.ui.excursion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import tj.ilhom.trip.databinding.ExcursionFragmentBinding
import tj.ilhom.trip.models.excurse.Photo
import tj.ilhom.trip.ui.excursion.adapter.PicturesAdapter


class ExcursionFragment : Fragment() {


    private lateinit var viewModel: ExcursionViewModel
    private lateinit var binding: ExcursionFragmentBinding
    private val args: ExcursionFragmentArgs by navArgs()
    private lateinit var pictureAdapter: PicturesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExcursionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    val arrayOfPhoto = arrayListOf<Photo>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ExcursionViewModel::class.java)
        pictureAdapter = PicturesAdapter(this)

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.share.setOnClickListener {
            // TODO DO SHARE LOGIC
        }
        Glide.with(requireActivity()).load(args.excurse.photos[0].medium).into(binding.mainImage)

        if (args.excurse.photos.size > 4) {
            pictureAdapter.showMore = true
            pictureAdapter
            args.excurse.photos.forEachIndexed { index, photo ->
                if (index <= 4){
                    arrayOfPhoto.add(photo)
                }
            }
        } else {
            arrayOfPhoto.addAll(args.excurse.photos)
        }

        pictureAdapter.setData(arrayOfPhoto)



        binding.apply {
            pictures.layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            pictures.setHasFixedSize(true)

            pictures.adapter = pictureAdapter
            excursionTitle.text = args.excurse.title
            reviewRating.text = args.excurse.rating.toString()
            ratingBar.rating = args.excurse.rating.toFloat()
            reviewQty.text = args.excurse.review_count.toString() + " отзыва"
            description.text = args.excurse.tagline
            duration.text = args.excurse.duration.toString() + " часа"
            groupQty.text = "До " + args.excurse.max_persons.toString() + " человек"
            standardPrice.text = args.excurse.price.value.toString() + " ₽"
            if (args.excurse.child_friendly) {
                child.text = "Можно с детьми"
            } else {
                child.text = "Нельзя с детьми"
            }
            when (args.excurse.movement_type) {
                "foot" -> {
                    howItComes.text = "Пешком"
                }
                "car" -> {
                    howItComes.text = "На машине"
                }
                "bicycle" -> {
                    howItComes.text = "На велосипеде"
                }
                "bus" -> {
                    howItComes.text = "На автобусе"
                }
                "motorcycle" -> {
                    howItComes.text = "На мотоцикле"
                }
                "watership" -> {
                    howItComes.text = "На кораблике"
                }
                "other" -> {
                    howItComes.text = "Другое"
                }
            }
        }
    }

}