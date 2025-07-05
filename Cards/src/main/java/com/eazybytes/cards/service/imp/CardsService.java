package com.eazybytes.cards.service.imp;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entitty.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardsMapper;
import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.service.ICardService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CardsService implements ICardService {
	
	private CardsRepository cardsRepository;
	

	@Override
	public void createCard(String mobileNumber) {
		 Optional<Cards> optionalCards= cardsRepository.findByMobileNumber(mobileNumber);
	        if(optionalCards.isPresent()){
	            throw new CardAlreadyExistException("Card already registered with given mobileNumber "+mobileNumber);
	        }
	        cardsRepository.save(createNewCard(mobileNumber));
		
	}
	
	private Cards createNewCard(String mobileNumber) {
		
		  Cards newCard = new Cards();
	        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
	        newCard.setCardNumber(Long.toString(randomCardNumber));
	        newCard.setMobileNumber(mobileNumber);
	        newCard.setCardType(CardsConstants.CREDIT_CARD);
	        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
	        newCard.setAmountUsed(0);
	        newCard.setAvaliableAmount(CardsConstants.NEW_CARD_LIMIT);
	        return newCard;
		
	}

	@Override
	public CardsDto fetchCard(String mobileNumber) {
		Cards cards=cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
				()->new ResourceNotFoundException("cards","mobileNumber",mobileNumber));
		
		return CardsMapper.mapToCardsDto(cards, new CardsDto()) ;
	}

	@Override
	public boolean updateCard(CardsDto cardsDto) {
		
		Cards cards=cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
			()->new ResourceNotFoundException("Cards", "AccountNumber", cardsDto.getCardNumber().toString()));
		CardsMapper.mapToCards(cards, cardsDto);
		cardsRepository.save(cards);
		return true;
	}

	@Override
	public boolean deleteCard(String mobileNumber) {
		Cards cards=cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
				()-> new ResourceNotFoundException("Cards", "mobileNumber", mobileNumber));
		cardsRepository.deleteById(cards.getCardId());
		return true;
	}

}
