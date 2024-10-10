package com.codigo.api_rest_g7.ejercicio4.controller;

import com.codigo.api_rest_g7.ejercicio4.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @PostMapping
    public String addBook(@RequestBody Book book){
        return "Libro con los siguientes datos: " +
                book.getTitle() + " Autor: "+ book.getAuthor() +
                " Estado del Libro: "+ book.getState();
    }

    @PostMapping("/add1")
    //@ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Book addBook1(@RequestBody Book book,
                         @RequestHeader("datoPrueba") String datoPrueba){
        book.setDato(datoPrueba);
        return book;
    }
}
