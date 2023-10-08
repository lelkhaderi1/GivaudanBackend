package com.givaudan.project.backend.rest;

import com.givaudan.project.backend.dto.ContactDto;
import com.givaudan.project.backend.entity.Contact;
import com.givaudan.project.backend.exception.ValidationException;
import com.givaudan.project.backend.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * RestController qui représente notre api contacts
 */
@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class ContactRestController {

    private final ContactService contactService;

    /**
     * retourne la liste et code 200 des contacts et qui permet un filtrage
     * @param firstName
     * @param lastName
     * @param email
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<List<ContactDto>> findContacts(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String email) {
        return ResponseEntity.ok(contactService.findContacts(firstName, lastName, email));
    }

    /**
     * retourne un contact par id et un code 200 Ok
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> findContactById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.findContactById(id));
    }

    /**
     * permet de supprimer un contact par son id ne retourne qu'un 204
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactById(@PathVariable Long id) {
        contactService.deleteContactById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * crée un nouveau contact est retourne son uri d'acces
     * @param contact
     * @param errors
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<String> addContact(@Valid @RequestBody Contact contact, Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException("error validation on", errors);
        }
        ContactDto contactdto = contactService.addContact(contact);
        UriComponentsBuilder b = UriComponentsBuilder.newInstance();
        UriComponents uriComponents =
                b.path("/contacts/{id}").buildAndExpand(contactdto.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    /**
     * modifie un contact avec un PUT et non pas un PATCH !!  et renvoie un accepted
     * @param id
     * @param contact
     * @param errors
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateContact(@PathVariable Long id, @Valid @RequestBody Contact contact, Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException("error validation on", errors);
        }
        contactService.updateContactById(id, contact);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
