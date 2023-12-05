package com.shoppingmall.qna;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingmall.users.Users;

@Service
@Transactional
public class QnaService {

	private final QnaRepository qnaRepository;

	@Autowired
	public QnaService(QnaRepository qnaRepository) {
		this.qnaRepository = qnaRepository;
	}

	public Qna saveQna(Qna qna) {
		return qnaRepository.save(qna);
	}

	public List<Qna> getAllQnas() {
		return qnaRepository.findAll();
	}

	public Optional<Qna> getQnaById(Long qnaid) {
		return qnaRepository.findById(qnaid);
	}

	public List<Qna> getQnasByUserId(Long usersid) {
		return qnaRepository.findByUsersid(usersid);
	}

	public List<Qna> getQnasByItemId(Long itemid) {
		return qnaRepository.findByItemid(itemid);
	}

	public void deleteQnaById(Long qnaid) {
		qnaRepository.deleteById(qnaid);
	}

	public void updateQna(Qna qna) {
		qnaRepository.save(qna);
	}


	@Transactional
	public Qna saveAnswer(Long qnaid, String answerContent) {
		try {
			Qna qna = qnaRepository.findById(qnaid).orElse(new Qna());
			qna.setQnaid(qnaid);
			qna.setAnswer(answerContent);
			return qnaRepository.save(qna); 
		} catch (Exception e) {
			e.printStackTrace(); 
			return null; 
		}
	}


	public String getAnswerByQnaId(Long qnaid) {
		Optional<Qna> optionalQna = qnaRepository.findById(qnaid);
		return optionalQna.map(Qna::getAnswer).orElse(null);
	}

	public Qna findQnaById(Long qanid) {
		Optional<Qna> qnaOptional = qnaRepository.findById(qanid);
		return qnaOptional.orElse(null);
	}


}
