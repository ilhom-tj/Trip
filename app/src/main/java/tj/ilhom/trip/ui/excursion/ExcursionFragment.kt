package tj.ilhom.trip.ui.excursion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import tj.ilhom.trip.databinding.ExcursionFragmentBinding
import tj.ilhom.trip.models.excurse.PerPerson
import tj.ilhom.trip.models.excurse.Photo
import tj.ilhom.trip.models.review.Review
import tj.ilhom.trip.ui.excursion.adapter.PerPersonAdapter
import tj.ilhom.trip.ui.excursion.adapter.PicturesAdapter


class ExcursionFragment : Fragment() {


    private lateinit var viewModel: ExcursionViewModel
    private lateinit var binding: ExcursionFragmentBinding
    private val args: ExcursionFragmentArgs by navArgs()
    private lateinit var pictureAdapter: PicturesAdapter
    private lateinit var perPersonAdapter: PerPersonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExcursionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    val arrayOfPhoto = arrayListOf<Photo>()
    val arrayOfReview = arrayListOf<Review>()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ExcursionViewModel::class.java)

//        viewModel.getReviews(args.excurse.id).observe(viewLifecycleOwner) {
//            Log.e("IT",it.results.size.toString())
//            arrayOfReview.addAll(it.results)
//        }

        viewModel.getExcursion(args.excurse.id).observe(viewLifecycleOwner) { excursion ->
            excursion.let {
                binding.allView.isVisible = true
                binding.progress.isVisible = false
                pictureAdapter = PicturesAdapter(this)
                perPersonAdapter = PerPersonAdapter(this)
                binding.perPersons.layoutManager = LinearLayoutManager(requireContext())
                binding.perPersons.adapter = perPersonAdapter
                if (!excursion.price.per_person.isNullOrEmpty()) {
                    perPersonAdapter.setData(excursion.price.per_person)
                } else if (excursion.price.per_group != null) {
                    val price =
                        excursion.price.per_group.value?.let { it1 ->
                            PerPerson(
                                0.0, excursion.price.unit_string, false,
                                it1
                            )
                        }
                    perPersonAdapter.setData(listOf(price!!))
                } else {
                    val price = PerPerson(0.0, excursion.price.unit_string, false, args.excurse.price.value)
                    perPersonAdapter.setData(listOf(price))
                }
                binding.back.setOnClickListener {
                    findNavController().navigateUp()
                }
                binding.share.setOnClickListener {
                    // TODO DO SHARE LOGIC
                }
                Glide.with(requireActivity()).load(excursion.photos[0].medium).into(binding.mainImage)

                if (args.excurse.photos.size > 4) {
                    pictureAdapter.showMore = true
                    pictureAdapter
                    excursion.photos.forEachIndexed { index, photo ->
                        if (index < 4) {
                            arrayOfPhoto.add(photo)
                        }
                    }
                } else {
                    arrayOfPhoto.addAll(excursion.photos)
                }

                binding.reviewQty.setOnClickListener {
                    val action = ExcursionFragmentDirections.actionExcursionFragmentToReviewFragment(args.excurse.id)
                    findNavController().navigate(action)
                }
                pictureAdapter.setData(arrayOfPhoto)
                Log.e("Excurse", excursion.toString())


                binding.apply {
                    pictures.layoutManager = StaggeredGridLayoutManager(
                        2,
                        StaggeredGridLayoutManager.VERTICAL
                    )
                    pictures.isNestedScrollingEnabled = false
                    pictures.setHasFixedSize(true)

                    pictures.adapter = pictureAdapter
                    excursionTitle.text = excursion.title
                    reviewRating.text = excursion.rating.toString()
                    ratingBar.rating = excursion.rating.toFloat()
                    reviewQty.text = excursion.review_count.toString() + " отзыва"

                    if (excursion.annotation.isNullOrEmpty()) {
                        description.text = excursion.tagline
                    } else {
                        description.text = excursion.annotation
                    }
                    duration.text = excursion.duration.toString() + " часа"
                    groupQty.text = "До " + excursion.max_persons.toString() + " человек"
//            standardPrice.text = args.excurse.price.value.toString() + " ₽"
                    binding.showMore.setOnClickListener {
                        val action =
                            ExcursionFragmentDirections.actionExcursionFragmentToDescriptionFragment(excursion)
                        findNavController().navigate(action)
                    }
                    if (excursion.child_friendly) {
                        child.text = "Можно с детьми"
                    } else {
                        child.text = "Нельзя с детьми"
                    }
                    when (excursion.movement_type) {
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
    }


}