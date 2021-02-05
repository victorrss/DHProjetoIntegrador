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
                "Which of the following movies is most hated by Marvel fans?",
                arrayListOf(
                    Resposta(0, "The incredible Hulk", true),
                    Resposta(0, "Iroman 3"),
                    Resposta(0, "Captain America: The First Avenger"),
                    Resposta(0, "Thor: The Dark World")
                )
            )
        )
        quizzes.add(
            Quiz(
                0,
                "COMPLETE: Marvel got a deal with ******** to produce ********* movies",
                arrayListOf(
                    Resposta(0, "Fox - Hawkeye"),
                    Resposta(0, "Sony Pictures - Venom"),
                    Resposta(0, "Fox - X-Men"),
                    Resposta(0, "Sony Pictures - Spiderman", true)
                )
            )
        )
        quizzes.add(
            Quiz(
                0, "Which was the first jewel of infinite?",
                arrayListOf(
                    Resposta(0, "Time"),
                    Resposta(0, "Reality"),
                    Resposta(0, "Space", true),
                    Resposta(0, "Power")
                )
            )
        )
        quizzes.add(
            Quiz(
                0, "Which first villain was the  of the marvel universe?",
                arrayListOf(
                    Resposta(0, "Red skull", true),
                    Resposta(0, "Ultron"),
                    Resposta(0, "Abutre"),
                    Resposta(0, "Loki")
                )
            )
        )
        quizzes.add(
            Quiz(
                0,
                "Which film was the greatest ticket in marvel?",
                arrayListOf(
                    Resposta(0, "Ironman 3"),
                    Resposta(0, "Venom"),
                    Resposta(0, "Avengers: Age of Ultron"),
                    Resposta(0, "Avengers: Infinity War", true)
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