package com.kotlin.marvelgeek.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.dbhelpers.QuizDbHelper
import com.kotlin.marvelgeek.model.Quiz
import kotlinx.android.synthetic.main.fragment_quiz.view.*
import java.io.Serializable

class QuizFragment : Fragment() {
    lateinit var quiz: ArrayList<Quiz>
    var index = 0
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle("Quiz")

        var view = inflater.inflate(R.layout.fragment_quiz, container, false)

        // Consulta ao banco, pega 5 perguntas aleatorias
        val dbQuiz = QuizDbHelper(context)
        quiz = dbQuiz?.getQuiz()

        // Verifica erro no banco, sem retorno
        if (quiz?.size == 0) {
            findNavController().navigateUp()
            return view
        }

        atualizaView(view)

        // CLICK DO BOTÃO PRÓXIMO
        view.quiFraBtnNext.setOnClickListener {
            // VAI PARA A PROX. PERGUNTA SE ESCOLHEU UMA RESPOSTA, SE NÃO PERMANECE E INFORMA
            if (quiz[index].respostaSelecionada != null) {
                index++
                atualizaView(view)
            } else
                Snackbar.make(view, "Select an answer", Snackbar.LENGTH_LONG).show()
        }

        // CLICK DO BOTÃO VOLTAR
        view.quiFraBtnBack.setOnClickListener {
            index--
            atualizaView(view)
        }

        // CLICK DO BOTÃO FINALIZAR
        view.quiFraBtnFinish.setOnClickListener {
            atualizaView(view)
            if (quiz[index].respostaSelecionada != null) {
                val bundle = Bundle()
                bundle.putSerializable("quiz", quiz)
                arguments = bundle
                findNavController().navigate(R.id.action_quizFragment_to_quizResultFragment, bundle)
            } else
                Snackbar.make(view, "Select an answer", Snackbar.LENGTH_LONG).show()
        }

        // CLICK NAS RESPOSTAS
        val clickReposta = View.OnClickListener {
            val indexResposta = it.tag.toString().toIntOrNull()
            if (indexResposta != null)
                quiz[index].respostaSelecionada = quiz[index].respostas[indexResposta]
        }
        view.quiFraRgOption1.setOnClickListener(clickReposta)
        view.quiFraRgOption2.setOnClickListener(clickReposta)
        view.quiFraRgOption3.setOnClickListener(clickReposta)
        view.quiFraRgOption4.setOnClickListener(clickReposta)

        //region SEGURANÇA DO BOTAO BACK, PARA NÃO PERDER O PROGRESSO QUIZ
        val callback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                val alertDialog: AlertDialog? = activity?.let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setPositiveButton("Back",
                            DialogInterface.OnClickListener { dialog, id ->
                                //val navOption = NavOptions.Builder().setPopUpTo(R.id.homeFragment2, false).build()
                                ///findNavController().navigate(R.id.homeFragment2, null, navOption)
                                viewModel.listCharacter.value?.clear()
                                findNavController().popBackStack()
                            })
                        setNegativeButton("Cancel",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User cancelled the dialog
                            })
                        setMessage("Your progress will be lost.")
                        setTitle("Do you want to come back??")
                    }
                    builder.create()
                }
                alertDialog?.show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        //endregion
        return view
    }

    private fun atualizaView(view: View) {
        // LIMPA RADIO BUTTON
        view.quiFraRgOptions.clearCheck()

        // COLOCA RESPOSTA SELECIONADA, SE EXISTIR
        if (quiz[index]?.respostaSelecionada != null) {
            val indexResposta = quiz[index].respostas.indexOf(quiz[index].respostaSelecionada)
            when (indexResposta) {
                0 -> view.quiFraRgOption1.isChecked = true
                1 -> view.quiFraRgOption2.isChecked = true
                2 -> view.quiFraRgOption3.isChecked = true
                3 -> view.quiFraRgOption4.isChecked = true
            }
        }

        // SETA PERGUNTA SELECIONADA
        view.quiFraTvLabelQuestion.text = "Question ${index + 1}"
        view.quiFraTvQuestion.text = quiz[index].pergunta
        view.quiFraRgOption1.text = quiz[index].respostas[0].texto
        view.quiFraRgOption2.text = quiz[index].respostas[1].texto
        view.quiFraRgOption3.text = quiz[index].respostas[2].texto
        view.quiFraRgOption4.text = quiz[index].respostas[3].texto

        // ATUALIZA A PROGRESSBAR
        view.quiFraProgressBar.progress = (index + 1) * 20

        // SÓ MOSTRA O BOTÃO VOLTAR SE ESTIVER NA PRIMEIRA PERGUNTA
        view.quiFraBtnBack.visibility = if (index == 0) View.GONE else View.VISIBLE

        // SÓ MOSTRA O BOTÃO PRÓXIMO SE NÃO ESTIVER NA ÚTLIMA PERGUNTA
        view.quiFraBtnNext.visibility = if (index == quiz.size - 1) View.GONE else View.VISIBLE

        // SÓ MOSTRA O BOTÃO FINALIZAR SE ESTIVER NA ÚTLIMA PERGUNTA
        view.quiFraBtnFinish.visibility = if (index != quiz.size - 1) View.GONE else View.VISIBLE
    }
}