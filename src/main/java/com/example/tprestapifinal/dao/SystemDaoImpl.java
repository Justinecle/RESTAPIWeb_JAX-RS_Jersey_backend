package com.example.tprestapifinal.dao;

import com.example.tprestapifinal.singleton.DataController;
import jakarta.persistence.*;
import com.example.tprestapifinal.entities.*;
import java.util.*;

public class SystemDaoImpl implements ISystemDAO{
    EntityManager manager = null;
    DataController dataController;
    public SystemDaoImpl() {
        dataController = DataController.getSingleInstance();
        manager = dataController.manager;
    }

    private List<Question> getQuestionsByDifficulty(String difficulty) {
        Query query = manager.createQuery("SELECT q FROM Question q WHERE q.difficulte =:dif");
        query.setParameter("dif", difficulty);
        List<Question> questions = query.getResultList();
        return questions;
    }

    private List<Question> getRandomQuestions(List<Question> list, int N)
    {
        Random rand = new Random();
        // create a temporary list for storing
        // selected element
        List<Question> newList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            // take a random index between 0 to size
            // of given List
            int randomIndex = rand.nextInt(list.size());
            // add element in temporary list
            newList.add(list.get(randomIndex));
            // Remove selected element from original list
            list.remove(randomIndex);
        }
        return newList;
    }

    @Override
    public Quiz addNewQuiz(String titre) {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        try {
            Quiz newQuiz = new Quiz();
            newQuiz.setTitre(titre);
            manager.persist(newQuiz);
            transaction.commit();
            return newQuiz;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Question> addRandomQuestionsForQuiz(int QuizID, int N, String difficulty) {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        try {
            Quiz quiz = manager.find(Quiz.class, QuizID);
            if (quiz != null) {
                List<Question> questions = getQuestionsByDifficulty(difficulty);
                System.out.println();
                if(questions != null && questions.size() > 0) {
                    System.out.println("Il y a " + questions.size());

                    if (N > questions.size())
                        N = questions.size();
                    List<Question> randomQuestions = getRandomQuestions(questions, N);
                    for(Question q : randomQuestions){
                        System.out.println(q.getEnonce());
                    }
                    for (Question question : randomQuestions) {
                        QuizQuestionPK quizQuestionPK = new QuizQuestionPK();
                        quizQuestionPK.setQuizId(quiz.getQuizId());
                        quizQuestionPK.setQuestionId(question.getQuestionId());
                        QuizQuestion quizQuestion = new QuizQuestion();
                        quizQuestion.setId(quizQuestionPK);
                        quizQuestion.setSelectedOptionId(0);
                        quizQuestion.setQuestion(question);
                        quizQuestion.setQuiz(quiz);
                        manager.persist(quizQuestion);
                    }
                    transaction.commit();
                    return randomQuestions;
                }
                System.out.println("Pas de questions");

                return null;
            }

        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Question> getQuestionsForQuiz(int quizId) {
        Query query = manager.createQuery("SELECT qq.question FROM QuizQuestion qq WHERE qq.id.quizId = :quizId");
        query.setParameter("quizId", quizId);
        List<Question> questions = query.getResultList();
        return questions;
    }

    @Override
    public List<Options> optionsForQuestion(int questionId) {
        try {
            // Récupère la question associée à l'ID
            Question question = manager.find(Question.class, questionId);

            // Vérifie si la question existe
            if (question != null) {
                // Renvoie la liste des options associées à la question
                return new ArrayList<>(question.getOptionsByQuestionId());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public QuizQuestion updateQuizQuestion(int quizId, int questionId, int selectedOptionId) {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        try {
            QuizQuestionPK quizQuestionPK = new QuizQuestionPK();
            quizQuestionPK.setQuizId(quizId);
            quizQuestionPK.setQuestionId(questionId);
            QuizQuestion quizQuestion = manager.find(QuizQuestion.class, quizQuestionPK);
            if (quizQuestion != null) {
                quizQuestion.setSelectedOptionId(selectedOptionId);
                transaction.commit();
                return quizQuestion;
            }
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Options rightOptionsForQuestion(int questionId) {
        try {
            // Récupère la question associée à l'ID
            Question question = manager.find(Question.class, questionId);

            // Vérifie si la question existe
            if (question != null) {
                // Renvoie l'option correcte associée à la question
                for (Options option : question.getOptionsByQuestionId()) {
                    if (Boolean.TRUE.equals(option.getEstVrai())) {
                        return option;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Quiz> getUsedQuizzes() {
        List<Quiz> usedQuizzes = new ArrayList<>();
        try {
            String queryStr = "SELECT q FROM Quiz q WHERE q.quizId IN " +
                    "(SELECT DISTINCT qq.quiz.quizId FROM QuizQuestion qq " +
                    "WHERE qq.selectedOptionId IS NOT NULL AND qq.selectedOptionId > 0)";
            TypedQuery<Quiz> query = manager.createQuery(queryStr, Quiz.class);
            usedQuizzes = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usedQuizzes;
    }

    @Override
    public List<Quiz> getNotUsedQuizzes() {
        List<Quiz> notUsedQuizzes = new ArrayList<>();
        try {
            String queryStr = "SELECT q FROM Quiz q WHERE q.quizId NOT IN " +
                    "(SELECT DISTINCT qq.quiz.quizId FROM QuizQuestion qq " +
                    "WHERE qq.selectedOptionId IS NOT NULL AND qq.selectedOptionId > 0)";
            TypedQuery<Quiz> query = manager.createQuery(queryStr, Quiz.class);
            notUsedQuizzes = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notUsedQuizzes;
    }

    @Override
    public QuizQuestion getQuizQuestion(int quizId, int questionId) {
        try {
            QuizQuestionPK quizQuestionPK = new QuizQuestionPK();
            quizQuestionPK.setQuizId(quizId);
            quizQuestionPK.setQuestionId(questionId);
            return manager.find(QuizQuestion.class, quizQuestionPK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
