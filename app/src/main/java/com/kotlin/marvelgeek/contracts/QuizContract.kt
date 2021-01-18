package com.kotlin.marvelgeek.contracts

import android.provider.BaseColumns

object QuizContract {
    object QuizPerguntaEntry : BaseColumns {
        const val NOME_TABELA = "quiz_pergunta"
        const val COLUNA_PERGUNTA = "pergunta"
    }
    object QuizRespostaEntry : BaseColumns {
        const val NOME_TABELA = "quiz_resposta"
        const val COLUNA_PERGUNTA_ID = "pergunta_id"
        const val COLUNA_RESPOSTA = "resposta"
        const val COLUNA_IS_CORRETA = "is_correta"
    }
}