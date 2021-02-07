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

        quizzes.add(
            Quiz(
                0,
                "What was the First Movie released by Marvel?",
                arrayListOf(
                    Resposta(0, "Captain Marvel"),
                    Resposta(0, "Iron man",true),
                    Resposta(0, "Captain America: The First Avenger"),
                    Resposta(0, "The Avengers")
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "When was the first Iron Man movie released?",
                arrayListOf(
                    Resposta(0, "2007"),
                    Resposta(0, "2008",true),
                    Resposta(0, "2010"),
                    Resposta(0, "2009")
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "Which of these characters is not part of the Marvel universe?",
                arrayListOf(
                    Resposta(0, "Captain America"),
                    Resposta(0, "Abmomination"),
                    Resposta(0, "Black Widow"),
                    Resposta(0, "Zatannam",true)
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "Which of these is not a codename used by Clint Barton?",
                arrayListOf(
                    Resposta(0, "Hawkeye"),
                    Resposta(0, "Golias"),
                    Resposta(0, "Ronin"),
                    Resposta(0, "Trick Shot",true)
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "The Venom symbiote is a member of an alien race called ...",
                arrayListOf(
                    Resposta(0, "Brood"),
                    Resposta(0, "Shi'ar"),
                    Resposta(0, "Klyntar",true),
                    Resposta(0, "Badoon")
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "What part of Peter Quill's body was cybernetic?",
                arrayListOf(
                    Resposta(0, "Eye",true),
                    Resposta(0, "Leg"),
                    Resposta(0, "Hand"),
                    Resposta(0, "Brain")
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "The names of Peter Parker's parents are ...",
                arrayListOf(
                    Resposta(0, "Richard and Mary Parker",true),
                    Resposta(0, "Thomas and Martha Parker"),
                    Resposta(0, "Matthew and Lindsay Parker"),
                    Resposta(0, "Ben and May Parker")
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "Before becoming a herald of Galactus, the Silver Surfer was known as ...",
                arrayListOf(
                    Resposta(0, "Norin Radd",true),
                    Resposta(0, "En Sabar Nur"),
                    Resposta(0, "Taa"),
                    Resposta(0, "Peter Parker")
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "Who invented the name of the 'Avengers'?",
                arrayListOf(
                    Resposta(0, "Iron Man"),
                    Resposta(0, "Wasp",true),
                    Resposta(0, "Captain America"),
                    Resposta(0, "Ant-Man")
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "Who did Bruce Banner save when he was hit by the gamma radiation that made him the Hulk?",
                arrayListOf(
                    Resposta(0, "Betty Boss"),
                    Resposta(0, "Amadeus Cho"),
                    Resposta(0, "Rick Jones",true),
                    Resposta(0, "Richard Parker")
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "Which of these is an original member of the Guardians of the Galaxy?",
                arrayListOf(
                    Resposta(0, "Vance Astro",true),
                    Resposta(0, "Groot"),
                    Resposta(0, "Darkhown"),
                    Resposta(0, "Moondragon")
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "Sam Wilson's pet hawk is called ...",
                arrayListOf(
                    Resposta(0, "Washington"),
                    Resposta(0, "Redwind",true),
                    Resposta(0, "America"),
                    Resposta(0, "Darkwing")
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "Who is Thanos' brother?",
                arrayListOf(
                    Resposta(0, "Annihilus"),
                    Resposta(0, "Starfox",true),
                    Resposta(0, "Thane"),
                    Resposta(0, "Titan")
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "Before becoming worthy of Mjolnir, Thor Odinson wielded an ax called ...",
                arrayListOf(
                    Resposta(0, "Surtur"),
                    Resposta(0, "Asgard"),
                    Resposta(0, "Jarnbjorn",true),
                    Resposta(0, "Bjorkun")
                )
            )
        )

        quizzes.add(
            Quiz(
                0,
                "Where the Black Widow received her training",
                arrayListOf(
                    Resposta(0, "Russian Room"),
                    Resposta(0, "Hydra"),
                    Resposta(0, "SHIELD"),
                    Resposta(0, "Red Room")
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