package com.kotlin.marvelgeek.Fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.model.Quiz
import kotlinx.android.synthetic.main.fragment_quiz_result.view.*
import java.io.File
import java.io.FileOutputStream

class QuizResultFragment : Fragment() {
    lateinit var quiz: ArrayList<Quiz>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val navOption = NavOptions.Builder().setPopUpTo(R.id.homeFragment2, false).build()
            findNavController().navigate(R.id.homeFragment2, null, navOption)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle("Marvel Geek")

        var view = inflater.inflate(R.layout.fragment_quiz_result, container, false)
        var mBundle = Bundle()
        try {
            if (mBundle != null)
                mBundle = requireArguments()
        } catch (e: Exception) {
            Log.e("QuizResult", "BUNDLE: " + e.stackTrace.toString())
        }

        try {
            quiz = mBundle?.getSerializable("quiz") as ArrayList<Quiz>
        } catch (e: Exception) {
            Log.e("QuizResult", "QUIZ SERIALIZABLE: " + e.stackTrace.toString())
            return view
        }

        if (quiz?.size == 0) {
            return view
        }
        val acertos = quiz.map { if (it.respostaSelecionada?.isCorreta!!) 1 else 0 }.sum()

        view.ratingBar.rating = acertos.toFloat()
        view.quiFraTvQuestion.text = when (acertos) {
            0 -> "You can improve. "
            1, 2 -> "Good. "
            3, 4 -> "Very good. "
            5 -> "Great!. "
            else -> ""
        } + "You got ${acertos} out of 5 questions about the Marvel Universe!"

        // BOTÃO COMPARTILHAR
        view.btnShare.setOnClickListener { share(view, requireActivity()) }

        // BOTÃO NOVO JOGO
        view.quiResFraBtnNewGame.setOnClickListener {
            findNavController().navigate(R.id.quizFragment)
        }
        return view
    }

    private fun takeScreenshot(view: View): Bitmap {
        val b = Bitmap.createBitmap(
            view.measuredWidth, view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val c = Canvas(b)
        c.translate((-view.scrollX).toFloat(), (-view.scrollY).toFloat())
        c.drawColor(Color.GRAY)
        view.draw(c)
        return b
    }

    private fun share(view: View, activity: FragmentActivity) {
        val bitmap = takeScreenshot(view)

        val file = File(activity.cacheDir.absolutePath, "imagemMarvelGeek.jpg")

        if (file.parentFile?.exists() == false)
            file.parentFile?.mkdirs()

        val uri = FileProvider.getUriForFile(
            activity.applicationContext,
            "com.kotlin.marvelgeek.fileprovider",
            file
        )

        val fOut = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fOut)
        fOut.flush()
        fOut.close()
        file.setReadable(true, false)

        val intent = ShareCompat.IntentBuilder.from(activity)
            .setType("image/jpg")
            .setStream(uri)
            .setChooserTitle("Choose the application")
            .createChooserIntent()
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        activity.startActivity(intent)
    }
}