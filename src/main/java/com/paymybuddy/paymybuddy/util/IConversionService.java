package com.paymybuddy.paymybuddy.util;

import com.paymybuddy.paymybuddy.dto.PersonConnectionDto;
import com.paymybuddy.paymybuddy.dto.PersonFullDto;
import com.paymybuddy.paymybuddy.dto.TransactionFullDto;
import com.paymybuddy.paymybuddy.model.Person;
import com.paymybuddy.paymybuddy.model.Transaction;

public interface IConversionService {

    PersonFullDto personToFullDto(final Person person);

    Person fullDtoToPerson(final PersonFullDto personFullDto);

    PersonConnectionDto connectionToConnectionDto(final Person connection);

    Transaction fullDtoToTransaction(final TransactionFullDto transactionFullDto);

    TransactionFullDto transactionToFullDto (final Transaction transaction);
}
