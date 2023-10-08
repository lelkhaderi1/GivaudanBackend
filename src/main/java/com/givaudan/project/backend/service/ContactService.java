package com.givaudan.project.backend.service;

import com.givaudan.project.backend.dao.ContactRepository;
import com.givaudan.project.backend.dto.ContactDto;
import com.givaudan.project.backend.entity.Contact;
import com.givaudan.project.backend.exception.NotFoundException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * couche métier
 */
@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final ModelMapper contactMapper;

    /**
     * recupere et filtre la liste des contact et utilise ModelMapper pour faire le mapping entre les entités et les dtos
     *
     * @param firstName
     * @param lastName
     * @param email
     * @return
     */
    public List<ContactDto> findContacts(String firstName, String lastName, String email) {

        return contactRepository.findAll()
                .stream()
                .filter(c -> c.getFirstName().contains(StringUtils.isNotBlank(firstName) ? firstName : "")
                        && c.getLastName().contains(StringUtils.isNotBlank(lastName) ? lastName : "")
                        && c.getEmail().contains(StringUtils.isNotBlank(email) ? email : ""))
                .map(c -> contactMapper.map(c, ContactDto.class))
                .toList();
    }

    /**
     * retourne contact par ID
     *
     * @param id
     * @return
     */
    public ContactDto findContactById(Long id) {
        return contactMapper.map(contactRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("contact Not Found"))
                , ContactDto.class);
    }

    /**
     * ajoute un contact
     *
     * @param contact
     * @return
     */
    public ContactDto addContact(Contact contact) {
        return contactMapper.map(
                contactRepository.save(contact)
                , ContactDto.class);
    }

    /**
     * supprime un contact par id
     *
     * @param id
     */
    public void deleteContactById(Long id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new NotFoundException("contact not found"));
        contactRepository.deleteById(id);
    }

    /**
     * modifie un contact par id
     *
     * @param id
     * @param contact
     * @return
     */
    public ContactDto updateContactById(Long id, Contact contact) {
        Contact contactFound = contactRepository.findById(id).orElseThrow(() -> new NotFoundException("contact not found"));

        contactFound.setFirstName(contact.getFirstName());
        contactFound.setLastName(contact.getLastName());
        contactFound.setEmail(contact.getEmail());
        contactFound.setPhoneNumber(contact.getPhoneNumber());

        return contactMapper.map(contactRepository.save(contactFound)
                , ContactDto.class);
    }

}
