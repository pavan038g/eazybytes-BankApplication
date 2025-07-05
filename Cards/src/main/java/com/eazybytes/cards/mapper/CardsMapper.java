package com.eazybytes.cards.mapper;

import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entitty.Cards;

public class CardsMapper {
	
	
	public static Cards mapToCards(Cards cards,CardsDto cardsDto) {
	 cards.setAmountUsed(cardsDto.getAmountUsed());
     cards.setAvaliableAmount(cardsDto.getAvaliableAmount());
   	 cards.setCardNumber(cardsDto.getCardNumber());
   	 cards.setCardType(cardsDto.getCardType());
   	 cards.setMobileNumber(cardsDto.getMobileNumber());
   	 cards.setTotalLimit(cardsDto.getTotalLimit());
   	 return cards;
		
		
	}
	
     public static CardsDto mapToCardsDto(Cards cards,CardsDto cardsDto) {
    	 cardsDto.setAmountUsed(cards.getAmountUsed());
    	 cardsDto.setAvaliableAmount(cards.getAvaliableAmount());
    	 cardsDto.setCardNumber(cards.getCardNumber());
    	 cardsDto.setCardType(cards.getCardType());
    	 cardsDto.setMobileNumber(cards.getMobileNumber());
    	 cardsDto.setTotalLimit(cards.getTotalLimit());
    	 return cardsDto;
		
		
	}

}
