package com.kotlin.marvelgeek.dbhelpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.kotlin.marvelgeek.contracts.QuizContract
import com.kotlin.marvelgeek.model.Quiz
import com.kotlin.marvelgeek.model.Resposta

class QuizDbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val SQL_CREATE_PERGUNTA_ENTRY =
        "CREATE TABLE ${QuizContract.QuizPerguntaEntry.NOME_TABELA} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${QuizContract.QuizPerguntaEntry.COLUNA_PERGUNTA} TEXT)"

    private val SQL_CREATE_RESPOSTA_ENTRY =
        "CREATE TABLE ${QuizContract.QuizRespostaEntry.NOME_TABELA} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${QuizContract.QuizRespostaEntry.COLUNA_PERGUNTA_ID} INT," +
                "${QuizContract.QuizRespostaEntry.COLUNA_IS_CORRETA} INT," +
                "${QuizContract.QuizRespostaEntry.COLUNA_RESPOSTA} TEXT)"

    private val SQL_DELETE_PERGUNTA_ENTRY =
        "DROP TABLE IF EXISTS ${QuizContract.QuizPerguntaEntry.NOME_TABELA}"
    private val SQL_DELETE_RESPOSTA_ENTRY =
        "DROP TABLE IF EXISTS ${QuizContract.QuizRespostaEntry.NOME_TABELA}"

    override fun onCreate(db: SQLiteDatabase) {
        db.beginTransaction()
        try {
            db.execSQL(SQL_CREATE_PERGUNTA_ENTRY)
            db.execSQL(SQL_CREATE_RESPOSTA_ENTRY)
            dadosDefault(db)
            db.setTransactionSuccessful()
            Log.i("QuizDBHelper", "SUCESSO CRIOU O BD")
        } catch (e: Exception) {
            Log.e("QuizDBHelper", "Erro ao executar onCreate\n${e.stackTrace}")
        } finally {
            db.endTransaction()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_PERGUNTA_ENTRY)
        db.execSQL(SQL_DELETE_RESPOSTA_ENTRY)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MarvelGeek.db"
    }

    fun drop() {
        val db = this.writableDatabase
        db.execSQL(SQL_DELETE_PERGUNTA_ENTRY)
        db.execSQL(SQL_DELETE_RESPOSTA_ENTRY)
    }

    private fun dadosDefault(db: SQLiteDatabase) {

        val quizzes = ArrayList<Quiz>()
        quizzes.add(
            Quiz(
                0,
                "Qual dos seguintes filmes é o mais odiado pelos fãs da Marvel?",
                arrayListOf(
                    Resposta(0, "O Incrível Hulk", true),
                    Resposta(0, "Homem de Ferro 3"),
                    Resposta(0, "Capitão América: O Primeiro Vingador"),
                    Resposta(0, "Thor: O Mundo Sombrio")
                )
            )
        )
        quizzes.add(
            Quiz(
                0,
                "COMPLETE: A Marvel conseguiu um acordo com a *** para produzir os filmes do ***",
                arrayListOf(
                    Resposta(0, "Fox - Gavião Arqueiro"),
                    Resposta(0, "Sony Pictures - Venom"),
                    Resposta(0, "Fox - X-Men"),
                    Resposta(0, "Sony Pictures - Homem-Aranha", true)
                )
            )
        )
        quizzes.add(
            Quiz(
                0, "QUAL FOI A PRIMEIRA JOIA DO INFINITO",
                arrayListOf(
                    Resposta(0, "Tempo"),
                    Resposta(0, "Realidade"),
                    Resposta(0, "Espaço", true),
                    Resposta(0, "Poder")
                )
            )
        )
        quizzes.add(
            Quiz(
                0, "QUAL FOI O PRIMEIRO VILÃO DO UNIVERSO MARVEL",
                arrayListOf(
                    Resposta(0, "Caveira Vermelha", true),
                    Resposta(0, "Ultron"),
                    Resposta(0, "Abutre"),
                    Resposta(0, "Loki")
                )
            )
        )
        quizzes.add(
            Quiz(
                0,
                "QUAL FOI O FILME COM MAIOR BILHETERIA DA MARVEL",
                arrayListOf(
                    Resposta(0, "HOMEM DE FERRO 3"),
                    Resposta(0, "VENOM"),
                    Resposta(0, "VINGADORES: ERA DE ULTRON"),
                    Resposta(0, "VINGADORES: GUERRA INFINITA", true)
                )
            )
        )

        db.beginTransaction()
        try {
            quizzes.forEach { quiz ->
                val newRowId = db?.insert(QuizContract.QuizPerguntaEntry.NOME_TABELA, null,
                    ContentValues().apply {
                        put(QuizContract.QuizPerguntaEntry.COLUNA_PERGUNTA, quiz.pergunta)
                    })
                quiz.respostas.forEach { resposta ->
                    db.insert(QuizContract.QuizRespostaEntry.NOME_TABELA, null,
                        ContentValues().apply {
                            put(QuizContract.QuizRespostaEntry.COLUNA_PERGUNTA_ID, newRowId)
                            put(QuizContract.QuizRespostaEntry.COLUNA_RESPOSTA, resposta.texto)
                            put(
                                QuizContract.QuizRespostaEntry.COLUNA_IS_CORRETA,
                                if (resposta.isCorreta) 1 else 0
                            )
                        })
                }
            }
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.e("QuizDBHelper", "Erro ao executar dadosDefault()\n${e.stackTrace}")
        } finally {
            db.endTransaction()
            //db.close()
        }
    }

    fun getQuiz(): ArrayList<Quiz> {
        val db = this.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            QuizContract.QuizPerguntaEntry.COLUNA_PERGUNTA
        )

        val sortOrder = "RANDOM() LIMIT 5"

        db.beginTransaction()
        val quizzes = arrayListOf<Quiz>()
        try {
            val cursor = db.query(
                QuizContract.QuizPerguntaEntry.NOME_TABELA,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
            )

            with(cursor) {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                    quizzes.add(
                        Quiz(
                            id = id,
                            pergunta = getString(getColumnIndexOrThrow(QuizContract.QuizPerguntaEntry.COLUNA_PERGUNTA)),
                            respostas = getRespostas(db, id)
                        )
                    )
                }
            }
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.e("QuizDBHelper", "Erro ao executar getPerguntas e getRespostas()\n${e.message}")
        } finally {
            db.endTransaction()
        }
        return quizzes
    }

    private fun getRespostas(db: SQLiteDatabase, perguntaId: Int): ArrayList<Resposta> {
        val projection = arrayOf(
            BaseColumns._ID,
            QuizContract.QuizRespostaEntry.COLUNA_RESPOSTA,
            QuizContract.QuizRespostaEntry.COLUNA_IS_CORRETA
        )

        val selection = "${QuizContract.QuizRespostaEntry.COLUNA_PERGUNTA_ID} = ?"
        val selectionArgs = arrayOf<String>(perguntaId.toString())

        val cursor = db.query(
            QuizContract.QuizRespostaEntry.NOME_TABELA,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val respostas = arrayListOf<Resposta>()
        with(cursor) {
            while (moveToNext()) {
                respostas.add(
                    Resposta(
                        id = getInt(getColumnIndexOrThrow(BaseColumns._ID)),
                        texto = getString(getColumnIndexOrThrow(QuizContract.QuizRespostaEntry.COLUNA_RESPOSTA)),
                        isCorreta = getInt(getColumnIndexOrThrow(QuizContract.QuizRespostaEntry.COLUNA_IS_CORRETA)) == 1
                    )
                )
            }
        }
        return respostas
    }
}